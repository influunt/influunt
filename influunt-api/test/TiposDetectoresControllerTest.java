import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.TipoDetector;
import org.junit.Assert;
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

public class TiposDetectoresControllerTest extends WithApplication {

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
    public void testCriarNovoTipoDetector() {
        TipoDetector tipoDetector = new TipoDetector();
        tipoDetector.setDescricao("CTA 1");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.TiposDetectoresController.create().url()).bodyJson(Json.toJson(tipoDetector));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        TipoDetector tipoDetectorRetornado = Json.fromJson(json, TipoDetector.class);

        assertEquals(200, postResult.status());
        assertEquals("CTA 1", tipoDetectorRetornado.getDescricao());
        assertNotNull(tipoDetectorRetornado.getId());
    }

    @Test
    public void testAtualizarTipoDetectorExistente() {
        TipoDetector tipoDetector = new TipoDetector();
        tipoDetector.setDescricao("CTA 1");
        tipoDetector.save();

        UUID tipoDetectorId = tipoDetector.getId();
        assertNotNull(tipoDetectorId);

        TipoDetector tipoDetector1 = new TipoDetector();
        tipoDetector1.setDescricao("Teste atualizar");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.TiposDetectoresController.update(tipoDetectorId.toString()).url())
                .bodyJson(Json.toJson(tipoDetector1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        TipoDetector tipoDetectorRetornado = Json.fromJson(json, TipoDetector.class);

        assertEquals(200, result.status());
        assertEquals("Teste atualizar", tipoDetectorRetornado.getDescricao());
        assertNotNull(tipoDetectorRetornado.getId());
    }

    @Test
    public void testAtualizarTipoDetectorNaoExistente() {
        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposDetectoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(request);
        assertEquals(404, result.status());
    }

    @Test
    public void testApagarTipoDetectorExistente() {
        TipoDetector tipoDetector = new TipoDetector();
        tipoDetector.setDescricao("Teste");
        tipoDetector.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposDetectoresController.delete(tipoDetector.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(TipoDetector.find.byId(tipoDetector.getId()));
    }

    @Test
    public void testApagarTipoDetectorNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposDetectoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarTiposDetectores() {
        TipoDetector tipoDetector = new TipoDetector();
        tipoDetector.setDescricao("CTA-1");
        tipoDetector.save();

        TipoDetector tipoDetector1 = new TipoDetector();
        tipoDetector1.setDescricao("CTA-2");
        tipoDetector1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.TiposDetectoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<TipoDetector> tipoDetectores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, tipoDetectores.size());
    }

    @Test
    public void testBuscarDadosTipoDetector() {
        TipoDetector tipoDetector = new TipoDetector();
        tipoDetector.setDescricao("Teste");
        tipoDetector.save();
        UUID tipoDetectorId = tipoDetector.getId();
        Assert.assertNotNull(tipoDetectorId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.TiposDetectoresController.findOne(tipoDetectorId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        TipoDetector tipoDetectorRetornado = Json.fromJson(json, TipoDetector.class);

        assertEquals(200, result.status());
        assertEquals(tipoDetectorId, tipoDetectorRetornado.getId());
    }

}
