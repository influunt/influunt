package test.controllers.monitoramento;

import checks.Erro;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.api.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.StatusAtualControlador;
import status.StatusTodosControladores;
import test.controllers.AbstractInfluuntControladorTest;
import utils.RangeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class StatusTodosControladoresTest extends AbstractInfluuntControladorTest {

    @Test
    public void testStatusDeTodosControladores() throws IOException {

        StatusAtualControlador statusAtualControlador = new StatusAtualControlador();
        statusAtualControlador.deleteAll();

        // Cria os controladores
        Controlador c1 = controladorTestUtils.getControladorAneis();
        c1.update();

        Controlador c2 = controladorTestUtils.getControladorTabelaHorario();
        c2.update();
        VersaoControlador vc2 = c2.getVersaoControlador();
        vc2.setStatusVersao(StatusVersao.SINCRONIZADO);
        vc2.update();

        Controlador c3 = controladorTestUtils.getControladorTabelaHorario();
        c3.update();
        VersaoControlador vc3 = c3.getVersaoControlador();
        vc3.setStatusVersao(StatusVersao.EDITANDO);
        vc3.update();

        Controlador c4 = controladorTestUtils.getControladorTabelaHorario();
        c4.update();

        // Requisicao de monitoramento (cria documento no mongo e retorna)
        Http.RequestBuilder requestMonitoramento = new Http.RequestBuilder().method("GET")
            .uri(controllers.api.monitoramento.routes.MonitoramentoController.ultimoStatusDosControladores().url());
        Result resultMonitoramento = route(requestMonitoramento);
        assertEquals(OK, resultMonitoramento.status());

        Map<String, Map<String, Float>> statusTodosControladores = StatusTodosControladores.getStatusTodosControladores(statusAtualControlador);
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nAneis"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nAneis"), new Float(2.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nAneis"), new Float(2.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nAneis"), new Float(2.0));

        // Altera aneis no controlador c3
        Anel ativandoAnel = c3.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();

        ativandoAnel.setDescricao("Anel 2");
        ativandoAnel.setAtivo(Boolean.TRUE);
        List<Estagio> estagios = Arrays.asList(new Estagio(1), new Estagio(2), new Estagio(3), new Estagio(4));
        estagios.forEach(estagio -> {
            estagio.setTempoMaximoPermanencia(100);
            estagio.setAnel(ativandoAnel);
        });
        ativandoAnel.setEstagios(estagios);

        Endereco endNovoAnel = new Endereco(1.0, 1.0, "Av. Paulista");
        endNovoAnel.setAnel(ativandoAnel);
        endNovoAnel.setLocalizacao2("R. Bela Cintra");

        ativandoAnel.setEndereco(endNovoAnel);

        // Aneis - doStep
        Http.RequestBuilder requestAddNovoAnel = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.aneis().url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(c3, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result resultAddNovoAnel = route(requestAddNovoAnel);
        assertEquals(OK, resultAddNovoAnel.status());

        statusTodosControladores = StatusTodosControladores.getStatusTodosControladores(new StatusAtualControlador());
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nAneis"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nAneis"), new Float(2.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nAneis"), new Float(3.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nAneis"), new Float(2.0));

        // Muda status do c4
        Http.RequestBuilder requestAlteraStatus = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(c4.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(c4, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result resultAlteraStatus = route(requestAlteraStatus);
        assertEquals(OK, resultAlteraStatus.status());

        statusTodosControladores = StatusTodosControladores.getStatusTodosControladores(new StatusAtualControlador());
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nAneis"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nControladores"), new Float(0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nAneis"), new Float(0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nControladores"), new Float(2.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nAneis"), new Float(5.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nAneis"), new Float(2.0));

        JsonNode json = Json.parse(Helpers.contentAsString(resultAlteraStatus));
        Controlador controladorC4Clonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        // Altera aneis no controlador c4
        Anel ativandoAnelOutroControlador = controladorC4Clonado.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();

        ativandoAnelOutroControlador.setDescricao("Anel 2");
        ativandoAnelOutroControlador.setAtivo(Boolean.TRUE);
        List<Estagio> estagiosOutroControlador = Arrays.asList(new Estagio(1), new Estagio(2), new Estagio(3), new Estagio(4));
        estagiosOutroControlador.forEach(estagio -> {
            estagio.setTempoMaximoPermanencia(100);
            estagio.setAnel(ativandoAnelOutroControlador);
        });
        ativandoAnelOutroControlador.setEstagios(estagios);

        Endereco endAnelOutroControlador = new Endereco(1.0, 1.0, "Av. Paulista");
        endAnelOutroControlador.setAnel(ativandoAnelOutroControlador);
        endAnelOutroControlador.setLocalizacao2("R. Bela Cintra");

        ativandoAnelOutroControlador.setEndereco(endAnelOutroControlador);

        // Aneis - doStep
        Http.RequestBuilder requestAddAnelOutroControlador = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.aneis().url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorC4Clonado, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result resultAddOutroControlador = route(requestAddAnelOutroControlador);
        assertEquals(OK, resultAddOutroControlador.status());

        statusTodosControladores = StatusTodosControladores.getStatusTodosControladores(new StatusAtualControlador());
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("EM_CONFIGURACAO").get("nAneis"), new Float(1.0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nControladores"), new Float(0));
        assertEquals(statusTodosControladores.get("CONFIGURADO").get("nAneis"), new Float(0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nControladores"), new Float(2.0));
        assertEquals(statusTodosControladores.get("EDITANDO").get("nAneis"), new Float(6.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nControladores"), new Float(1.0));
        assertEquals(statusTodosControladores.get("SINCRONIZADO").get("nAneis"), new Float(2.0));

        // Requisicao de monitoramento
        requestMonitoramento = new Http.RequestBuilder().method("GET")
            .uri(controllers.api.monitoramento.routes.MonitoramentoController.ultimoStatusDosControladores().url());
        resultMonitoramento = route(requestMonitoramento);
        assertEquals(OK, resultMonitoramento.status());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return null;
    }
}
