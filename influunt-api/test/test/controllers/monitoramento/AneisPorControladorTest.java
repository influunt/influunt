package test.controllers.monitoramento;

import checks.Erro;
import controllers.api.routes;
import json.ControladorCustomSerializer;
import models.*;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import status.AneisControlador;
import test.controllers.AbstractInfluuntControladorTest;
import utils.RangeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class AneisPorControladorTest extends AbstractInfluuntControladorTest {

    @Test
    public void testAneisPorControlador() {

        AneisControlador aneisControlador = new AneisControlador();
        aneisControlador.deleteAll();

        // Cria os controladores
        Controlador c1 = controladorTestUtils.getControladorAneis();
        c1.update();

        Controlador c2 = controladorTestUtils.getControladorTabelaHorario();
        c2.update();

        // Requisicao de monitoramento (cria documento no mongo e retorna)
        Http.RequestBuilder requestMonitoramento = new Http.RequestBuilder().method("GET")
            .uri(controllers.api.monitoramento.routes.MonitoramentoController.ultimoStatusDosControladores().url());
        Result resultMonitoramento = route(requestMonitoramento);
        assertEquals(OK, resultMonitoramento.status());

        String query = "{"+c1.getControladorFisicoId() + " : " + "1}";
        Map aneisPorControlador = aneisControlador.findOne(query).as(Map.class);

        assertEquals(aneisPorControlador.get(c1.getControladorFisicoId()), 1);
        // Altera aneis no controlador c2
        Anel ativandoAnel = c2.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();

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
            .bodyJson(new ControladorCustomSerializer().getControladorJson(c2, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result resultAddNovoAnel = route(requestAddNovoAnel);
        assertEquals(OK, resultAddNovoAnel.status());

        String queryC2 = "{"+c2.getControladorFisicoId() + " : " + "3}";
        aneisPorControlador = aneisControlador.findOne(queryC2).as(Map.class);

        assertEquals(aneisPorControlador.get(c2.getControladorFisicoId()), 3);
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return null;
    }
}
