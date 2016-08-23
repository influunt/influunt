package models;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

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

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[0].estagios[0].associadoDetectorCasoDemandaPrioritaria"),
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();

        for (int i = 0; i < 5; i++) {
            criarDetector(anelCom2Estagios, TipoDetector.PEDESTRE, i + 1);
        }

        erros = getErros(controlador);

        assertEquals(9, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "A quantidade de detectores não deve ultrapassar a quantidade de estágios definidas no modelo do controlador.", "aneis[1]"),
                new Erro("Controlador", "Numero total de detectores de pedestre informado individualmente nos aneis excede o limite do controlador", ""),
                new Erro("Controlador", "O detector deve estar associado a pelo menos um estagio.", "aneis[1].detectores[0].associadoAoMenosUmEstagio"),
                new Erro("Controlador", "O detector deve estar associado a pelo menos um estagio.", "aneis[1].detectores[1].associadoAoMenosUmEstagio"),
                new Erro("Controlador", "O detector deve estar associado a pelo menos um estagio.", "aneis[1].detectores[2].associadoAoMenosUmEstagio"),
                new Erro("Controlador", "O detector deve estar associado a pelo menos um estagio.", "aneis[1].detectores[3].associadoAoMenosUmEstagio"),
                new Erro("Controlador", "O detector deve estar associado a pelo menos um estagio.", "aneis[1].detectores[4].associadoAoMenosUmEstagio"),
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[0].estagios[0].associadoDetectorCasoDemandaPrioritaria"),
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));

        anelCom2Estagios.setDetectores(null);

        for (int i = 0; i < 8; i++) {
            criarDetector(anelCom2Estagios, TipoDetector.VEICULAR, i + 1);
        }

        anelCom2Estagios.setDetectores(null);

        erros = getErros(controlador);

        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[0].estagios[0].associadoDetectorCasoDemandaPrioritaria"),
                new Erro("Controlador", "Esse estagio deve estar associado a pelo menos um detector.", "aneis[1].estagios[0].associadoDetectorCasoDemandaPrioritaria")
        ));

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
        Estagio estagio4 = anelCom4Estagios.findEstagioByDescricao("E4");

        Detector detector1 = anelCom4Estagios.findDetectorByDescricao("D1");
        Detector detector2 = anelCom4Estagios.findDetectorByDescricao("D2");
        Detector detector3 = anelCom4Estagios.findDetectorByDescricao("D3");
        Detector detector4 = anelCom4Estagios.findDetectorByDescricao("D4");

        assertEquals("Estagio 1 está associado Detector 1", estagio1.getDetector(), detector1);
        assertEquals("Estagio 2 está associado Detector 2", estagio2.getDetector(), detector2);
        assertEquals("Estagio 3 está associado Detector 3", estagio3.getDetector(), detector3);
        assertEquals("Estagio 4 está associado Detector 4", estagio4.getDetector(), detector4);
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));

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
        assertEquals("Estagio 2 está associado Detector 2", estagio2.getDetector(), detector2);
        assertEquals("Estagio 3 está associado Detector 3", estagio3.getDetector(), detector3);
        assertEquals("Estagio 4 está associado Detector 4", estagio4.getDetector(), detector4);

    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoDetectores().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(2, json.size());
    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorAssociacaoDetectores();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoDetectores().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertNotNull(controladorRetornado.getId());
        assertEquals(StatusControlador.CONFIGURADO, controladorRetornado.getStatusControlador());
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Toda de detectores", 4, anelCom4Estagios.getDetectores().size());

        Estagio estagio1 = anelCom4Estagios.findEstagioByDescricao("E1");
        Estagio estagio2 = anelCom4Estagios.findEstagioByDescricao("E2");
        Estagio estagio3 = anelCom4Estagios.findEstagioByDescricao("E3");
        Estagio estagio4 = anelCom4Estagios.findEstagioByDescricao("E4");

        Detector detector1 = anelCom4Estagios.findDetectorByDescricao("D1");
        Detector detector2 = anelCom4Estagios.findDetectorByDescricao("D2");
        Detector detector3 = anelCom4Estagios.findDetectorByDescricao("D3");
        Detector detector4 = anelCom4Estagios.findDetectorByDescricao("D4");

        assertEquals("Estagio 1 está associado Detector 1", detector1.getEstagio(), estagio1);
        assertEquals("Estagio 2 está associado Detector 2", detector2.getEstagio(), estagio2);
        assertEquals("Estagio 3 está associado Detector 3", detector3.getEstagio(), estagio3);
        assertEquals("Estagio 4 está associado Detector 4", detector4.getEstagio(), estagio4);
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
    }

}
