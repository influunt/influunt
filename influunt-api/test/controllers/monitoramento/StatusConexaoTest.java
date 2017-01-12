package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Cidade;
import models.Controlador;
import models.ControladorFisico;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.LogControlador;
import status.StatusConexaoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.*;

import static org.junit.Assert.*;
import static play.test.Helpers.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusConexaoTest extends WithInfluuntApplicationNoAuthentication {

    private Cidade cidade;

    private Http.Context context;

    private Optional<String> tokenComAcesso;

    private ArrayList<String> controladoresIds;

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        StatusConexaoControlador.jongo = jongo;
        LogControlador.jongo = jongo;

        jongo.getCollection(StatusConexaoControlador.COLLECTION).drop();

        StatusConexaoControlador.log("1", System.currentTimeMillis(), true);
        Thread.sleep(10);
        StatusConexaoControlador.log("1", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("2", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("2", System.currentTimeMillis(), true);

        controladoresIds = new ArrayList<>();
        controladoresIds.add("1");
        controladoresIds.add("2");
    }

    @Test
    public void testUltimoStatusDosControladores() {
        Map<String, Boolean> usc = StatusConexaoControlador.ultimoStatusDosControladores(controladoresIds);
        assertEquals(2, usc.size());

        assertFalse(usc.get("1"));
        assertTrue(usc.get("2"));
    }

    @Test
    public void testStatusDeUmControlador() {
        assertFalse(StatusConexaoControlador.ultimoStatus("1").isConectado());
    }

    @Test
    public void testHistoricoStatusControlador() throws InterruptedException {
        jongo.getCollection(StatusConexaoControlador.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                StatusConexaoControlador.log("1", System.currentTimeMillis(), true);
            } else {
                StatusConexaoControlador.log("1", System.currentTimeMillis(), false);
            }

        }

        List<StatusConexaoControlador> statusControlador = StatusConexaoControlador.historico("1", 0, 50);
        assertEquals(50, statusControlador.size());
        assertFalse(statusControlador.get(0).isConectado());

        statusControlador = StatusConexaoControlador.historico("1", 1, 50);
        assertEquals(50, statusControlador.size());
        assertTrue(statusControlador.get(0).isConectado());

    }

    @Test
    public void testStatusControladorApi() {
        System.out.println(controllers.monitoramento.routes.StatusControladorController.findOne("1").url());
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.StatusControladorController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(2, status.size());
        assertFalse(Boolean.valueOf(status.get(0).get("conectado").toString()));
    }


    @Test
    public void testUltimoStatusDeTodosControladoresApi() {
        Controlador c1 = new ControladorHelper().getControlador();
        c1.getVersaoControlador().getControladorFisico().setControladorSincronizado(c1);
        c1.getVersaoControlador().getControladorFisico().save();
        c1.save();
        StatusConexaoControlador.log(c1.getControladorFisicoId(), System.currentTimeMillis(), false);

        Controlador c2 = new ControladorHelper().getControlador();
        c2.getVersaoControlador().getControladorFisico().setControladorSincronizado(c2);
        c2.getVersaoControlador().getControladorFisico().save();
        c2.save();
        StatusConexaoControlador.log(c2.getControladorFisicoId(), System.currentTimeMillis(), true);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.StatusControladorController.ultimoStatusDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());
        System.out.println(json.toString());
        assertFalse(json.get(c1.getControladorFisicoId()).asBoolean());
        assertTrue(json.get(c2.getControladorFisicoId()).asBoolean());

    }

    @Test
    public void testUltimoStatusDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.StatusControladorController.ultimoStatus("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertTrue(json.get("conectado").asBoolean());

    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.StatusControladorController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertFalse(json.get(0).get("conectado").asBoolean());

        postRequest = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.StatusControladorController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertTrue(json.get(0).get("conectado").asBoolean());

    }

    @Test
    public void testDetalheControladorApi() {
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        ControladorFisico controladorFisico = controlador.getVersaoControlador().getControladorFisico();
        controladorFisico.setControladorSincronizado(controlador);
        controladorFisico.update();

        jongo.getCollection(StatusConexaoControlador.COLLECTION).drop();
        DateTime now = DateTime.now();
        String cfId = controladorFisico.getId().toString();
        StatusConexaoControlador.log(cfId, now.minusDays(90).getMillis(), true);
        StatusConexaoControlador.log(cfId, now.minusDays(40).getMillis(), false);
        StatusConexaoControlador.log(cfId, now.minusDays(30).plusSeconds(20).getMillis(), true);
        StatusConexaoControlador.log(cfId, now.minusDays(30).plusSeconds(30).getMillis(), false);
        StatusConexaoControlador.log(cfId, now.minusDays(20).getMillis(), true);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(controllers.monitoramento.routes.MonitoramentoController.detalheControlador(cfId).url());
        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));

        // somente os status dos últimos 30 dias são retornados
        assertEquals(json.get("historico").size(), 3);
        assertEquals(json.get("percentualOnline").asInt(), 66);


        // -1 porque somente as horas completas são contabilizadas, os 30 segundos a menos retiraram a última hora.
        int horasOffline = Hours.hoursBetween(new DateTime(now.minusDays(30).getMillis()), new DateTime(now.minusDays(20).getMillis())).getHours() - 1;
        int horasOnline = Hours.hoursBetween(new DateTime(now.minusDays(20).getMillis()), DateTime.now()).getHours();
        assertEquals(horasOnline, json.get("totalOnline").asInt());
        assertEquals(horasOffline, json.get("totalOffline").asInt());
    }

    @Test
    public void testUltimoStatusPorSituacao() throws InterruptedException {
        StatusConexaoControlador.log("3", System.currentTimeMillis(), true);
        Thread.sleep(10);
        StatusConexaoControlador.log("3", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("4", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("4", System.currentTimeMillis(), true);

        assertStatus(true, Arrays.asList("2", "4"), Arrays.asList("1", "3"));
        assertStatus(false, Arrays.asList("1", "3"), Arrays.asList("2", "4"));
    }

    private void assertStatus(boolean online, List<String> idsIn, List<String> idsOut) {
        List<StatusConexaoControlador> statuses = StatusConexaoControlador.ultimoStatusPorSituacao(online);
        assertEquals(idsIn.size(), statuses.size());
        statuses.stream().forEach(status -> {
            assertEquals(online, status.isConectado());
            assertTrue(idsIn.contains(status.getIdControlador()));
            assertFalse(idsOut.contains(status.getIdControlador()));
        });
    }
}
