package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Cidade;
import models.Controlador;
import models.StatusAnel;
import models.StatusDevice;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.StatusControladorFisico;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.*;

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

        HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();
        statusAneis.put(1, StatusAnel.NORMAL);
        statusAneis.put(2, StatusAnel.COM_FALHA);

        StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.NOVO, statusAneis);
        Thread.sleep(10);
        StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.CONFIGURADO, statusAneis);
        Thread.sleep(10);
        StatusControladorFisico.log("2", System.currentTimeMillis(), StatusDevice.NOVO, statusAneis);
        Thread.sleep(10);
        StatusControladorFisico.log("2", System.currentTimeMillis(), StatusDevice.ATIVO, statusAneis);

    }

    @Test
    public void testUltimoStatusDosControlador() {
        ArrayList<String> controladoresIds = new ArrayList<>();
        controladoresIds.add("1");
        controladoresIds.add("2");
        HashMap<String, HashMap> usc = StatusControladorFisico.ultimoStatusDosControladores(controladoresIds);
        assertEquals(2, usc.size());

        assertEquals(StatusDevice.CONFIGURADO, StatusDevice.valueOf(usc.get("1").get("statusDevice").toString()));
        assertEquals(StatusDevice.ATIVO, StatusDevice.valueOf(usc.get("2").get("statusDevice").toString()));
    }

    @Test
    public void testStatusDeUmControlador() {
        assertEquals(StatusDevice.CONFIGURADO, StatusControladorFisico.ultimoStatus("1").getStatusDevice());
    }

    @Test
    public void testHistoricoStatusControlador() throws InterruptedException {
        jongo.getCollection(StatusControladorFisico.COLLECTION).drop();
        HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();
        statusAneis.put(1, StatusAnel.NORMAL);
        statusAneis.put(2, StatusAnel.COM_FALHA);

        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.NOVO, statusAneis);
            } else {
                StatusControladorFisico.log("1", System.currentTimeMillis(), StatusDevice.ATIVO, statusAneis);
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
        HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();
        statusAneis.put(1, StatusAnel.NORMAL);
        statusAneis.put(2, StatusAnel.COM_FALHA);

        HashMap<Integer, StatusAnel> statusAneis1 = new HashMap<>();
        statusAneis1.put(1, StatusAnel.AMARELO_INTERMITENTE_POR_FALHA);
        statusAneis1.put(2, StatusAnel.MANUAL);

        Controlador c1 = new ControladorHelper().getControlador();
        c1.getVersaoControlador().getControladorFisico().setControladorSincronizado(c1);
        c1.getVersaoControlador().getControladorFisico().save();
        c1.save();
        StatusControladorFisico.log(c1.getControladorFisicoId(), System.currentTimeMillis(), StatusDevice.CONFIGURADO, statusAneis);

        Controlador c2 = new ControladorHelper().getControlador();
        c2.getVersaoControlador().getControladorFisico().setControladorSincronizado(c2);
        c2.getVersaoControlador().getControladorFisico().save();
        c2.save();
        StatusControladorFisico.log(c2.getControladorFisicoId(), System.currentTimeMillis(), StatusDevice.ATIVO, statusAneis1);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.StatusControladorFisicoController.ultimoStatusDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());

        assertEquals(StatusDevice.CONFIGURADO, StatusDevice.valueOf(json.get(c1.getControladorFisicoId()).get("statusDevice").asText()));
        assertEquals(StatusDevice.ATIVO, StatusDevice.valueOf(json.get(c2.getControladorFisicoId()).get("statusDevice").asText()));

        assertEquals(StatusAnel.NORMAL, StatusAnel.valueOf(json.get(c1.getControladorFisicoId()).get("statusAneis").get("1").asText()));
        assertEquals(StatusAnel.COM_FALHA, StatusAnel.valueOf(json.get(c1.getControladorFisicoId()).get("statusAneis").get("2").asText()));

        assertEquals(StatusAnel.AMARELO_INTERMITENTE_POR_FALHA, StatusAnel.valueOf(json.get(c2.getControladorFisicoId()).get("statusAneis").get("1").asText()));
        assertEquals(StatusAnel.MANUAL, StatusAnel.valueOf(json.get(c2.getControladorFisicoId()).get("statusAneis").get("2").asText()));
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
