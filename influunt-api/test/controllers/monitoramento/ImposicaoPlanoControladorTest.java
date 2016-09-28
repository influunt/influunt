package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.ImposicaoPlanosControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
public class ImposicaoPlanoControladorTest extends WithInfluuntApplicationNoAuthentication {

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        ImposicaoPlanosControlador.jongo = jongo;

        jongo.getCollection(ImposicaoPlanosControlador.COLLECTION).drop();

        ImposicaoPlanosControlador.log("1", System.currentTimeMillis(), true);
        Thread.sleep(10);
        ImposicaoPlanosControlador.log("1", System.currentTimeMillis(), false);
        Thread.sleep(10);
        ImposicaoPlanosControlador.log("2", System.currentTimeMillis(), false);
        Thread.sleep(10);
        ImposicaoPlanosControlador.log("2", System.currentTimeMillis(), true);

    }

    @Test
    public void testUltimoStatusPlanoImpostoDosControlador() {
        HashMap<String, Boolean> usc = ImposicaoPlanosControlador.ultimoStatusPlanoImpostoDosControladores();

        assertEquals(2, usc.size());
        assertFalse(usc.get("1"));
        assertTrue(usc.get("2"));
    }

    @Test
    public void testStatusPlanoImpostoDeUmControlador() {
        assertFalse(ImposicaoPlanosControlador.ultimoStatusPlanoImposto("1").planoImposto);
    }

    @Test
    public void testHistoricoPlanoImpostoControlador() throws InterruptedException {
        jongo.getCollection(ImposicaoPlanosControlador.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                ImposicaoPlanosControlador.log("1", System.currentTimeMillis(), true);
            } else {
                ImposicaoPlanosControlador.log("1", System.currentTimeMillis(), false);
            }

        }

        List<ImposicaoPlanosControlador> imposicaoPlanosControlador = ImposicaoPlanosControlador.historico("1", 0, 50);
        assertEquals(50, imposicaoPlanosControlador.size());
        assertFalse(imposicaoPlanosControlador.get(0).isPlanoImposto());

        imposicaoPlanosControlador = ImposicaoPlanosControlador.historico("1", 1, 50);
        assertEquals(50, imposicaoPlanosControlador.size());
        assertTrue(imposicaoPlanosControlador.get(0).isPlanoImposto());

    }

    @Test
    public void testStatusPlanoImpostoControladorApi() {
        System.out.println(controllers.monitoramento.routes.ImposicaoPlanosControladorController.findOne("1").url());
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.ImposicaoPlanosControladorController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(2, status.size());
        assertFalse(Boolean.valueOf(status.get(0).get("planoImposto").toString()));

    }


    @Test
    public void testUltimoStatusPlanoImpostoDeTodosControladoresApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.ImposicaoPlanosControladorController.ultimoStatusPlanoImpostoDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());
        System.out.println(json.toString());
        assertFalse(json.get("1").asBoolean());
        assertTrue(json.get("2").asBoolean());

    }

    @Test
    public void testUltimoStatusPlanoImpostoDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.ImposicaoPlanosControladorController.ultimoStatusPlanoImposto("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertTrue(json.get("planoImposto").asBoolean());

    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.ImposicaoPlanosControladorController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertFalse(json.get(0).get("planoImposto").asBoolean());

        postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.ImposicaoPlanosControladorController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertTrue(json.get(0).get("planoImposto").asBoolean());

    }


}