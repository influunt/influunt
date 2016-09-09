package controllers.monitoramento;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import models.Cidade;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;
import status.StatusConexaoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.*;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusConexaoTest extends WithApplication {

    private Cidade cidade;

    private Http.Context context;

    private Optional<String> tokenComAcesso;
    private PlayJongo jongo;

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

    @Before
    public void setUp() throws InterruptedException {

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        StatusConexaoControlador.jongo = jongo;

        jongo.getCollection("status_conexao_controladores").drop();

        StatusConexaoControlador.log("1", System.currentTimeMillis(), true);
        Thread.sleep(10);
        StatusConexaoControlador.log("1", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("2", System.currentTimeMillis(), false);
        Thread.sleep(10);
        StatusConexaoControlador.log("2", System.currentTimeMillis(), true);

    }

    @Test
    public void testUltimoStatusDosControlador() {
        HashMap<String, Boolean> usc = StatusConexaoControlador.ultimoStatusDosControladores();
        assertEquals(2, usc.size());

        assertFalse(usc.get("1"));
        assertTrue(usc.get("2"));


    }

    @Test
    public void testStatusDeUmControlador() {
        assertFalse(StatusConexaoControlador.ultimoStatus("1").conectado);
    }

    @Test
    public void testHistoricoStatusControlador() throws InterruptedException {
        jongo.getCollection("status_conexao_controladores").drop();
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
        assertFalse(statusControlador.get(0).conectado);

        statusControlador = StatusConexaoControlador.historico("1", 1, 50);
        assertEquals(50, statusControlador.size());
        assertTrue(statusControlador.get(0).conectado);

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

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(controllers.monitoramento.routes.StatusControladorController.ultimoStatusDosControladores().url());
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(2, json.size());
        System.out.println(json.toString());
        assertFalse(json.get("1").asBoolean());
        assertTrue(json.get("2").asBoolean());

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


}
