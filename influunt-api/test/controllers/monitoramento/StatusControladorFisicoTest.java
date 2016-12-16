package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.Cidade;
import models.StatusDevice;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.StatusControladorFisico;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusControladorFisicoTest extends WithInfluuntApplicationNoAuthentication {

    private Cidade cidade;

    private Http.Context context;

    private Optional<String> tokenComAcesso;

    private PlayJongo jongo;


    @Before
    public void setUp() throws InterruptedException {

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        StatusControladorFisico.jongo = jongo;

        jongo.getCollection(StatusControladorFisico.COLLECTION).drop();

        StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.NOVO);
        Thread.sleep(10);
        StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.CONFIGURADO);
        Thread.sleep(10);
        StatusControladorFisico.log("2", System.currentTimeMillis(), StatusDevice.NOVO);
        Thread.sleep(10);
        StatusControladorFisico.log("2", System.currentTimeMillis(), StatusDevice.ATIVO);

    }

    @Test
    public void testUltimoStatusDosControlador() {
        HashMap<String, StatusDevice> usc = StatusControladorFisico.ultimoStatusDosControladores();
        assertEquals(2, usc.size());

        assertEquals(StatusDevice.CONFIGURADO, usc.get("1"));
        assertEquals(StatusDevice.ATIVO, usc.get("2"));
    }

    @Test
    public void testStatusDeUmControlador() {
        assertEquals(StatusDevice.CONFIGURADO, StatusControladorFisico.ultimoStatus("1").getStatusDevice());
    }

    @Test
    public void testHistoricoStatusControlador() throws InterruptedException {
        jongo.getCollection(StatusControladorFisico.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.NOVO);
            } else {
                StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.ATIVO);
            }

        }

        List<StatusControladorFisico> statusControlador = StatusControladorFisico.historico("1", 0, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(StatusDevice.ATIVO, statusControlador.get(0).getStatusDevice());

        statusControlador = StatusControladorFisico.historico("1", 1, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(StatusDevice.NOVO, statusControlador.get(0).getStatusDevice());
    }

    @Test
    public void testStatusControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(2, status.size());
        assertEquals(StatusDevice.CONFIGURADO.toString(), status.get(0).get("statusDevice").toString());
    }


    @Test
    public void testUltimoStatusDeTodosControladoresApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.ultimoStatusDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());

        assertEquals(StatusDevice.CONFIGURADO.toString(), json.get("1").asText());
        assertEquals(StatusDevice.ATIVO.toString(), json.get("2").asText());
    }

    @Test
    public void testUltimoStatusDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.ultimoStatus("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(StatusDevice.ATIVO.toString(), json.get("statusDevice").asText());
    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(StatusDevice.CONFIGURADO.toString(), json.get(0).get("statusDevice").asText());


        postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(StatusDevice.NOVO.toString(), json.get(0).get("statusDevice").asText());
    }


}
