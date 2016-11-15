package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.ModoOperacaoPlano;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import status.ModoOperacaoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
public class ModoOperacaoControladorTest extends WithInfluuntApplicationNoAuthentication {

    private PlayJongo jongo;

    @Before
    public void setUp() throws InterruptedException {

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        ModoOperacaoControlador.jongo = jongo;

        jongo.getCollection(ModoOperacaoControlador.COLLECTION).drop();

        ModoOperacaoControlador.log("1", System.currentTimeMillis(), ModoOperacaoPlano.APAGADO);
        Thread.sleep(10);
        ModoOperacaoControlador.log("1", System.currentTimeMillis(), ModoOperacaoPlano.ATUADO);
        Thread.sleep(10);
        ModoOperacaoControlador.log("1", System.currentTimeMillis(), ModoOperacaoPlano.INTERMITENTE);
        Thread.sleep(10);
        ModoOperacaoControlador.log("2", System.currentTimeMillis(), ModoOperacaoPlano.MANUAL);
        Thread.sleep(10);
        ModoOperacaoControlador.log("2", System.currentTimeMillis(), ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        Thread.sleep(10);
        ModoOperacaoControlador.log("2", System.currentTimeMillis(), ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);

    }

    @Test
    public void testUltimoModoOperacaoDosControlador() {
        HashMap<String, ModoOperacaoPlano> usc = ModoOperacaoControlador.ultimoModoOperacaoDosControladores();

        assertEquals(2, usc.size());
        assertEquals(ModoOperacaoPlano.INTERMITENTE, usc.get("1"));
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, usc.get("2"));
    }

    @Test
    public void testModoDeOperacaoDeUmControlador() {
        assertEquals(ModoOperacaoPlano.INTERMITENTE, ModoOperacaoControlador.ultimoModoOperacao("1").modoOperacao);
    }

    @Test
    public void testHistoricoModoOperacaoControlador() throws InterruptedException {
        jongo.getCollection(ModoOperacaoControlador.COLLECTION).drop();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            if (i < 50) {
                ModoOperacaoControlador.log("1", System.currentTimeMillis(), ModoOperacaoPlano.APAGADO);
            } else {
                ModoOperacaoControlador.log("1", System.currentTimeMillis(), ModoOperacaoPlano.ATUADO);
            }

        }

        List<ModoOperacaoControlador> modoControlador = ModoOperacaoControlador.historico("1", 0, 50);
        assertEquals(50, modoControlador.size());
        assertEquals(ModoOperacaoPlano.ATUADO, modoControlador.get(0).modoOperacao);

        modoControlador = ModoOperacaoControlador.historico("1", 1, 50);
        assertEquals(50, modoControlador.size());
        assertEquals(ModoOperacaoPlano.APAGADO, modoControlador.get(0).modoOperacao);

    }

    @Test
    public void testModoOperacaoControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ModoOperacaoControladorController.findOne("1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap> status = Json.fromJson(json, List.class);

        assertEquals(3, status.size());
        assertEquals(ModoOperacaoPlano.INTERMITENTE.toString(), status.get(0).get("modoOperacao").toString());

    }


    @Test
    public void testUltimoModoOperacaoDeTodosControladoresApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ModoOperacaoControladorController.ultimoModoOperacaoDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());

        assertEquals(ModoOperacaoPlano.INTERMITENTE.toString(), json.get("1").asText());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), json.get("2").asText());

    }

    @Test
    public void testUltimoStatusDeUmControladorApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ModoOperacaoControladorController.ultimoModoOperacaoControlador("2").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), json.get("modoOperacao").asText());
    }

    @Test
    public void testHistoricoApi() {

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ModoOperacaoControladorController.historico("1", "0", "1").url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(ModoOperacaoPlano.INTERMITENTE.toString(), json.get(0).get("modoOperacao").asText());


        postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ModoOperacaoControladorController.historico("1", "1", "1").url());
        postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(ModoOperacaoPlano.ATUADO.toString(), json.get(0).get("modoOperacao").asText());

    }
}
