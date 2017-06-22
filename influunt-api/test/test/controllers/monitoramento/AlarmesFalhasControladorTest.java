package test.controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.api.monitoramento.routes;
import engine.EventoMotor;
import engine.TipoEvento;
import models.GrupoSemaforico;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.AlarmesFalhasControlador;
import status.LogControlador;
import test.config.WithInfluuntApplicationNoAuthentication;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
public class AlarmesFalhasControladorTest extends WithInfluuntApplicationNoAuthentication {

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {
        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        AlarmesFalhasControlador.jongo = jongo;
        LogControlador.jongo = jongo;

        jongo.getCollection(AlarmesFalhasControlador.COLLECTION).drop();

        AlarmesFalhasControlador.log(System.currentTimeMillis(),
            "1",
            "2",
            Json.toJson(new EventoMotor(new DateTime(),
                TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,
                new Pair<Integer, TipoDetector>(1, TipoDetector.PEDESTRE),
                2))
        );

        Thread.sleep(10);
        AlarmesFalhasControlador.log(System.currentTimeMillis(),
            "1",
            "2",
            Json.toJson(new EventoMotor(new DateTime(), TipoEvento.FALHA_VERDES_CONFLITANTES, 2)));

        Thread.sleep(10);
        GrupoSemaforico grupoSemaforico = new GrupoSemaforico();
        grupoSemaforico.setPosicao(1);
        AlarmesFalhasControlador.log(System.currentTimeMillis(),
            "2",
            "2",
            Json.toJson(new EventoMotor(new DateTime(),
                TipoEvento.FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA,
                grupoSemaforico,
                2
            ))
        );

        Thread.sleep(10);
        AlarmesFalhasControlador.log(System.currentTimeMillis(),
            "2",
            "2",
            Json.toJson(new EventoMotor(new DateTime(), TipoEvento.FALHA_SEQUENCIA_DE_CORES, 2)));

        Thread.sleep(10);
    }

    @Test
    public void testUltimosAlarmesFalhasDosControladores() {
        HashMap<String, TipoEvento> usc = AlarmesFalhasControlador.ultimosAlarmesFalhas();

        assertEquals(2, usc.size());
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES, usc.get("1"));
        assertEquals(TipoEvento.FALHA_SEQUENCIA_DE_CORES, usc.get("2"));
    }

    @Test
    public void testUltimoAlarmeFalhaControlador() {
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES, AlarmesFalhasControlador.ultimoAlarmeFalhaControlador("1").getTipoEvento());
    }

    @Test
    public void testHistoricoAlarmesEFalhasControlador() throws InterruptedException {
        jongo.getCollection(AlarmesFalhasControlador.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                AlarmesFalhasControlador.log(System.currentTimeMillis(),
                    "1",
                    null,
                    Json.toJson(new EventoMotor(new DateTime(), TipoEvento.FALHA_VERDES_CONFLITANTES, 2)));
            } else {
                AlarmesFalhasControlador.log(System.currentTimeMillis(),
                    "1",
                    null,
                    Json.toJson(new EventoMotor(new DateTime(),
                        TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO,
                        new Pair<Integer, TipoDetector>(1, TipoDetector.PEDESTRE),
                        2))
                );
            }

        }

        List<AlarmesFalhasControlador> statusControlador = AlarmesFalhasControlador.historico("1", 0, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO, statusControlador.get(0).getTipoEvento());

        statusControlador = AlarmesFalhasControlador.historico("1", 1, 50);
        assertEquals(50, statusControlador.size());
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES, statusControlador.get(0).getTipoEvento());
    }

    @Test
    public void testAlarmeFalhaControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.AlarmesEFalhasControladorController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(2, status.size());
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES.toString(), ((Map) status.get(0).get("tipoEvento")).get("tipo").toString());
    }


    @Test
    public void testUltimoErrosDeTodosControladoresApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.AlarmesEFalhasControladorController.ultimosAlarmesEFalhasDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());

        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES.toString(), json.get("1").get("tipo").asText());
        assertEquals(TipoEvento.FALHA_SEQUENCIA_DE_CORES.toString(), json.get("2").get("tipo").asText());

    }

    @Test
    public void testUltimoErroDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.AlarmesEFalhasControladorController.ultimoAlarmeFalhaControlador("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(TipoEvento.FALHA_SEQUENCIA_DE_CORES.toString(), json.get("tipoEvento").get("tipo").asText());
    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.AlarmesEFalhasControladorController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES.toString(),
            json.get(0).get("tipoEvento").get("tipo").asText());


        postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.AlarmesEFalhasControladorController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO.toString(), json.get(0).get("tipoEvento").get("tipo").asText());
    }

    @Test
    public void testUltimaFalhaControladorAposRemocao() {
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES,
            AlarmesFalhasControlador.ultimaFalhaControlador("1", "2").getTipoEvento());

        AlarmesFalhasControlador.logRemocao(System.currentTimeMillis(),
            "1",
            "2",
            Json.toJson(new EventoMotor(new DateTime(), TipoEvento.REMOCAO_FALHA_VERDES_CONFLITANTES, 2)));

        assertEquals(TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,
            AlarmesFalhasControlador.ultimaFalhaControlador("1", "2").getTipoEvento());
    }
}
