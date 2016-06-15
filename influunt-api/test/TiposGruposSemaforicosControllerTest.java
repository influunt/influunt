import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.TipoGrupoSemaforico;
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

public class TiposGruposSemaforicosControllerTest extends WithApplication {

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
    public void testCriarNovoTipoGrupoSemaforico() {
        TipoGrupoSemaforico tipoGrupoSemaforico = new TipoGrupoSemaforico();
        tipoGrupoSemaforico.setDescricao("Veicular");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.TiposGruposSemaforicosController.create().url()).bodyJson(Json.toJson(tipoGrupoSemaforico));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        TipoGrupoSemaforico tipoGrupoSemaforicoRetornado = Json.fromJson(json, TipoGrupoSemaforico.class);

        assertEquals(200, postResult.status());
        assertEquals("Veicular", tipoGrupoSemaforicoRetornado.getDescricao());
        assertNotNull(tipoGrupoSemaforicoRetornado.getId());
    }

    @Test
    public void testAtualizarTipoGrupoSemaforicoExistente() {
        TipoGrupoSemaforico tipoGrupoSemaforico = new TipoGrupoSemaforico();
        tipoGrupoSemaforico.setDescricao("Veicula");
        tipoGrupoSemaforico.save();

        UUID tipoGrupoSemaforicoId = tipoGrupoSemaforico.getId();
        assertNotNull(tipoGrupoSemaforicoId);

        TipoGrupoSemaforico tipoGrupoSemaforico1 = new TipoGrupoSemaforico();
        tipoGrupoSemaforico1.setDescricao("Pedestre");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.TiposGruposSemaforicosController.update(tipoGrupoSemaforicoId.toString()).url())
                .bodyJson(Json.toJson(tipoGrupoSemaforico1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        TipoGrupoSemaforico tipoGrupoSemaforicoRetornado = Json.fromJson(json, TipoGrupoSemaforico.class);

        assertEquals(200, result.status());
        assertEquals("Pedestre", tipoGrupoSemaforicoRetornado.getDescricao());
        assertNotNull(tipoGrupoSemaforicoRetornado.getId());
    }

    @Test
    public void testAtualizarTipoGrupoSemaforicoNaoExistente() {
        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposGruposSemaforicosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(request);
        assertEquals(404, result.status());
    }

    @Test
    public void testApagarTipoGrupoSemaforicoExistente() {
        TipoGrupoSemaforico tipoGrupoSemaforico = new TipoGrupoSemaforico();
        tipoGrupoSemaforico.setDescricao("Veicular");
        tipoGrupoSemaforico.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposGruposSemaforicosController.delete(tipoGrupoSemaforico.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(TipoGrupoSemaforico.find.byId(tipoGrupoSemaforico.getId()));
    }

    @Test
    public void testApagarTipoGrupoSemaforicoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.TiposGruposSemaforicosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarTiposGruposSemaforicos() {
        TipoGrupoSemaforico tipoGrupoSemaforico = new TipoGrupoSemaforico();
        tipoGrupoSemaforico.setDescricao("Veicular");
        tipoGrupoSemaforico.save();

        TipoGrupoSemaforico tipoGrupoSemaforico2 = new TipoGrupoSemaforico();
        tipoGrupoSemaforico2.setDescricao("Pedestre");
        tipoGrupoSemaforico2.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.TiposGruposSemaforicosController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<TipoGrupoSemaforico> tipoGruposSemaforicos = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, tipoGruposSemaforicos.size());
    }

    @Test
    public void testBuscarDadosTipoGrupoSemaforico() {
        TipoGrupoSemaforico tipoGrupoSemaforico = new TipoGrupoSemaforico();
        tipoGrupoSemaforico.setDescricao("Veicular");
        tipoGrupoSemaforico.save();

        UUID tipoGrupoSemaforicoId = tipoGrupoSemaforico.getId();
        assertNotNull(tipoGrupoSemaforicoId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri(routes.TiposGruposSemaforicosController.findOne(tipoGrupoSemaforicoId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        TipoGrupoSemaforico tipoGrupoSemaforicoRetornado =  Json.fromJson(json, TipoGrupoSemaforico.class);
        assertEquals(200, result.status());
        assertEquals(tipoGrupoSemaforicoId, tipoGrupoSemaforicoRetornado.getId());
    }

}
