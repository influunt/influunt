package test.models;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.api.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/11/16.
 */
public class ControladorAssociacaoDetectoresTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        List<Erro> erros = getErros(controlador);

        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Esse estágio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));


        for (int i = 0; i < 5; i++) {
            criarDetector(anelCom2Estagios, TipoDetector.PEDESTRE, i + 1, false);
        }

        erros = getErros(controlador);

        assertEquals(8, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "A quantidade de detectores não deve ultrapassar a quantidade de estágios definidas no modelo do controlador.", "aneis[1]"),
            new Erro(CONTROLADOR, "Número total de detectores de pedestre informado individualmente nos anéis excede o limite do controlador", ""),
            new Erro(CONTROLADOR, "O detector deve estar associado a pelo menos um estágio.", "aneis[1].detectores[0].associadoAoMenosUmEstagio"),
            new Erro(CONTROLADOR, "O detector deve estar associado a pelo menos um estágio.", "aneis[1].detectores[1].associadoAoMenosUmEstagio"),
            new Erro(CONTROLADOR, "O detector deve estar associado a pelo menos um estágio.", "aneis[1].detectores[2].associadoAoMenosUmEstagio"),
            new Erro(CONTROLADOR, "O detector deve estar associado a pelo menos um estágio.", "aneis[1].detectores[3].associadoAoMenosUmEstagio"),
            new Erro(CONTROLADOR, "O detector deve estar associado a pelo menos um estágio.", "aneis[1].detectores[4].associadoAoMenosUmEstagio"),
            new Erro(CONTROLADOR, "Esse estágio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));

        anelCom2Estagios.setDetectores(null);

        erros = getErros(controlador);

        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Esse estágio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));

        criarDetector(anelCom2Estagios, TipoDetector.PEDESTRE, 1, false);
        Detector detector = anelCom2Estagios.getDetectores().get(0);
        Estagio estagio = anelCom2Estagios.getEstagios().get(0);
        detector.setEstagio(estagio);
        estagio.setDetector(detector);


        criarDetector(anelCom4Estagios, TipoDetector.VEICULAR, 1, true);
        Detector detector2 = anelCom4Estagios.getDetectores().get(0);
        Estagio estagio2 = anelCom4Estagios.getEstagios().get(0);
        detector2.setEstagio(estagio2);
        estagio2.setDetector(detector2);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "O detector de pedestre deve estar associado a um estágio com grupo semafórico de pedestre.", "aneis[1].detectores[0].associadoAoMenosUmEstagioPedestre"),
            new Erro(CONTROLADOR, "O detector veicular deve estar associado a um estágio com grupo semafórico veicular.", "aneis[0].detectores[0].associadoAoMenosUmEstagioVeicular"),
            new Erro(CONTROLADOR, "O tempo de ausência de detecção deve estar entre {min} e {max}.", "aneis[0].detectores[0].tempoAusenciaDeteccaoEstaDentroDaFaixa"),
            new Erro(CONTROLADOR, "O tempo de detecção permanente deve estar entre {min} e {max}.", "aneis[0].detectores[0].tempoDeteccaoPermanenteEstaDentroDaFaixa")
        ));

        detector.setTipo(TipoDetector.VEICULAR);
        detector2.setTipo(TipoDetector.PEDESTRE);

        detector2.setTempoAusenciaDeteccao(5801);
        detector2.setTempoDeteccaoPermanente(11);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de ausência de detecção deve estar entre {min} e {max}.", "aneis[0].detectores[0].tempoAusenciaDeteccaoEstaDentroDaFaixa"),
            new Erro(CONTROLADOR, "O tempo de detecção permanente deve estar entre {min} e {max}.", "aneis[0].detectores[0].tempoDeteccaoPermanenteEstaDentroDaFaixa")
        ));

        detector2.setTempoAusenciaDeteccao(1500);
        detector2.setTempoDeteccaoPermanente(1400);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de detecção permanente deve estar entre {min} e {max}.", "aneis[0].detectores[0].tempoDeteccaoPermanenteEstaDentroDaFaixa")
        ));

        detector2.setTempoAusenciaDeteccao(5800);
        detector2.setTempoDeteccaoPermanente(10);

        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());

    }


    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();
        assertNotNull(controlador.getId());

        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Estagio estagio1 = anelCom4Estagios.findEstagioByDescricao("E1");
        Estagio estagio2 = anelCom4Estagios.findEstagioByDescricao("E2");
        Estagio estagio3 = anelCom4Estagios.findEstagioByDescricao("E3");

        Detector detector1 = anelCom4Estagios.findDetectorByDescricao("D1");
        Detector detector2 = anelCom4Estagios.findDetectorByDescricao("D2");
        Detector detector3 = anelCom4Estagios.findDetectorByDescricao("D3");

        assertEquals("Estagio 1 está associado Detector 1", estagio1.getDetector(), detector1);
        assertEquals("Estagio 2 está associado Detector 3", estagio2.getDetector(), detector3);
        assertEquals("Estagio 3 está associado Detector 2", estagio3.getDetector(), detector2);
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Estagio estagio1 = anelCom4Estagios.findEstagioByDescricao("E1");
        Estagio estagio2 = anelCom4Estagios.findEstagioByDescricao("E2");
        Estagio estagio3 = anelCom4Estagios.findEstagioByDescricao("E3");
        Estagio estagio4 = anelCom4Estagios.findEstagioByDescricao("E4");

        Detector detector1 = anelCom4Estagios.findDetectorByDescricao("D1");
        Detector detector2 = anelCom4Estagios.findDetectorByDescricao("D2");
        Detector detector3 = anelCom4Estagios.findDetectorByDescricao("D3");
        Detector detector4 = anelCom4Estagios.findDetectorByDescricao("D4");

        assertEquals("Estagio 1 está associado Detector 1", estagio1.getDetector(), detector1);
        assertEquals("Estagio 2 está associado Detector 3", estagio2.getDetector(), detector3);
        assertEquals("Estagio 3 está associado Detector 2", estagio3.getDetector(), detector2);
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.associacaoDetectores().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(1, json.size());
    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorAssociacaoDetectores();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.associacaoDetectores().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertNotNull(controladorRetornado.getId());
        assertEquals(StatusVersao.EM_CONFIGURACAO, controladorRetornado.getVersaoControlador().getStatusVersao());
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de detectores", 3, anelCom4Estagios.getDetectores().size());

        Estagio estagio1 = anelCom4Estagios.findEstagioByDescricao("E1");
        Estagio estagio2 = anelCom4Estagios.findEstagioByDescricao("E2");
        Estagio estagio3 = anelCom4Estagios.findEstagioByDescricao("E3");

        Detector detector1 = anelCom4Estagios.findDetectorByDescricao("D1");
        Detector detector2 = anelCom4Estagios.findDetectorByDescricao("D2");
        Detector detector3 = anelCom4Estagios.findDetectorByDescricao("D3");

        assertEquals("Estagio 1 está associado Detector 1", detector1.getEstagio(), estagio1);
        assertEquals("Estagio 2 está associado Detector 3", detector3.getEstagio(), estagio2);
        assertEquals("Estagio 3 está associado Detector 2", detector2.getEstagio(), estagio3);
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class,
            ControladorTabelaEntreVerdesCheck.class, ControladorAssociacaoDetectoresCheck.class);
    }

}
