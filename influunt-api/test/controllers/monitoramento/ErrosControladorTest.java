package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.MotivoFalhaControlador;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.ErrosControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
public class ErrosControladorTest extends WithInfluuntApplicationNoAuthentication {

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {
        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        ErrosControlador.jongo = jongo;

        jongo.getCollection(ErrosControlador.COLLECTION).drop();

        ErrosControlador.log("1", System.currentTimeMillis(), MotivoFalhaControlador.AMARELO_INTERMITENTE_POR_FALHA);
        Thread.sleep(10);
        ErrosControlador.log("1", System.currentTimeMillis(), MotivoFalhaControlador.QUEDA_ENERGIA);
        Thread.sleep(10);
        ErrosControlador.log("2", System.currentTimeMillis(), MotivoFalhaControlador.FALHA_ACERTO_RELOGIO_GPS);
        Thread.sleep(10);
        ErrosControlador.log("2", System.currentTimeMillis(), MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA);

    }

    @Test
    public void testUltimosErrosDosControladores() {
        HashMap<String, MotivoFalhaControlador> usc = ErrosControlador.ultimosErrosDosControladores();

        assertEquals(2, usc.size());
        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA, usc.get("1"));
        assertEquals(MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA, usc.get("2"));
    }

    @Test
    public void testUltimoErroControlador() {
        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA, ErrosControlador.ultimoErroControlador("1").motivoFalhaControlador);
    }

    @Test
    public void testHistoricoErrosControlador() throws InterruptedException {
        jongo.getCollection(ErrosControlador.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                ErrosControlador.log("1", System.currentTimeMillis(), MotivoFalhaControlador.QUEDA_ENERGIA);
            } else {
                ErrosControlador.log("1", System.currentTimeMillis(), MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA);
            }

        }

        List<ErrosControlador> statusControlador = ErrosControlador.historico("1", 0, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA, statusControlador.get(0).motivoFalhaControlador);

        statusControlador = ErrosControlador.historico("1", 1, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA, statusControlador.get(0).motivoFalhaControlador);
    }

    @Test
    public void testErrosControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ErrosControladorController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(2, status.size());
        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA.toString(), status.get(0).get("motivoFalhaControlador").toString());
    }


    @Test
    public void testUltimoErrosDeTodosControladoresApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ErrosControladorController.ultimosErrosDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());

        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA.toString(), json.get("1").asText());
        assertEquals(MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA.toString(), json.get("2").asText());

    }

    @Test
    public void testUltimoErroDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ErrosControladorController.ultimoErroControlador("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(MotivoFalhaControlador.FASE_PEDESTRE_QUEIMADA.toString(), json.get("motivoFalhaControlador").asText());
    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ErrosControladorController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(MotivoFalhaControlador.QUEDA_ENERGIA.toString(), json.get(0).get("motivoFalhaControlador").asText());


        postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ErrosControladorController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(MotivoFalhaControlador.AMARELO_INTERMITENTE_POR_FALHA.toString(), json.get(0).get("motivoFalhaControlador").asText());
    }

}
