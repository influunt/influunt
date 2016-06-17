import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.Anel;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class AneisControllerTest extends WithApplication {

    static Double LATITUDE = 1.0;
    static Double LONGITUDE = 1.0;

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(TestAuthenticator.class)).in(Mode.TEST).build();
    }

    @Test
    public void testCriarNovoAnel() {
        Anel anel = new Anel();
        anel.setDescricao("Anel 1");
        anel.setIdAnel("1234560000");
        anel.setNumeroSMEE("123");
        anel.setLatitude(LATITUDE);
        anel.setLongitude(LONGITUDE);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AneisController.create().url()).bodyJson(Json.toJson(anel));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Anel anelRetornado = Json.fromJson(json, Anel.class);

        assertEquals(200, postResult.status());
        assertEquals("Anel 1", anelRetornado.getDescricao());
        assertEquals("1234560000", anelRetornado.getIdAnel());
        assertEquals("123", anelRetornado.getNumeroSMEE());
        assertEquals(LATITUDE, anelRetornado.getLatitude());
        assertEquals(LONGITUDE, anelRetornado.getLongitude());
        assertNotNull(anelRetornado.getId());
    }

    @Test
    public void testAtualizarAnelNaoExistente() {
        Anel anel = new Anel("Anel 1");

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AneisController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(anel));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarAnelExistente() {
        Anel anel = new Anel("Anel 1");
        anel.save();

        UUID anelId = anel.getId();
        assertNotNull(anelId);
        assertNull(anel.getLatitude());
        assertNull(anel.getLongitude());

        Anel novoAnel = new Anel();
        novoAnel.setDescricao("Anel atualizado");
        novoAnel.setLatitude(LATITUDE);
        novoAnel.setLongitude(LONGITUDE);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.AneisController.update(anelId.toString()).url())
                .bodyJson(Json.toJson(novoAnel));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Anel anelRetornado = Json.fromJson(json, Anel.class);

        assertEquals("Anel atualizado", anelRetornado.getDescricao());
        assertEquals(LATITUDE, anelRetornado.getLatitude());
        assertEquals(LONGITUDE, anelRetornado.getLongitude());
        assertNotNull(anelRetornado.getId());
    }

    @Test
    public void testApagarAnelExistente() {
        Anel anel = new Anel("Anel 1");
        anel.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AneisController.delete(anel.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Anel.find.byId(anel.getId()));
    }

    @Test
    public void testApagarAnelNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AneisController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarAneis() {
        Anel anel1 = new Anel("Anel 1");
        anel1.save();

        Anel anel2 = new Anel("Anel 2");
        anel2.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.AneisController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Anel> aneis = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, aneis.size());
    }

    @Test
    public void testBuscarDadosAnel() {
        Anel anel = new Anel("Anel 1");
        anel.save();
        UUID anelId = anel.getId();
        assertNotNull(anelId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.AneisController.findOne(anelId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Anel anelRetornado = Json.fromJson(json, Anel.class);

        assertEquals(200, result.status());
        assertEquals(anelId, anelRetornado.getId());
    }

}
