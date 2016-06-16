import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.ConfiguracaoControlador;
import models.Fabricante;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;


public class ConfiguracoesControladoresControllerTest extends WithApplication {

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
    @SuppressWarnings("unchecked")
    public void testListarConfiguracoesControladores() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.save();

        ConfiguracaoControlador configuracaoControlador1 = new ConfiguracaoControlador();
        configuracaoControlador1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ConfiguracoesControladoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<ConfiguracaoControlador> configuracoesControladores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, configuracoesControladores.size());
    }

    @Test
    public void testCriarNovaConfiguracaoControlador() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ConfiguracoesControladoresController.create().url()).bodyJson(Json.toJson(configuracaoControlador));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ConfiguracaoControlador configuracaoControladorRetornada = Json.fromJson(json, ConfiguracaoControlador.class);

        assertEquals(200, result.status());
        assertNotNull(configuracaoControladorRetornada.getId());
    }

    @Test
    public void testAtualizarConfiguracaoControladorExistente() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.save();

        UUID configuracaoControladorId = configuracaoControlador.getId();
        assertNotNull(configuracaoControladorId);

        ConfiguracaoControlador configuracaoControlador1 = new ConfiguracaoControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ConfiguracoesControladoresController.update(configuracaoControladorId.toString()).url())
                .bodyJson(Json.toJson(configuracaoControlador1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ConfiguracaoControlador fabricanteRetornado = Json.fromJson(json, ConfiguracaoControlador.class);

        assertEquals(200, result.status());
        assertNotNull(fabricanteRetornado.getId());
    }

    @Test
    public void testAtualizarConfiguracaoControladorNaoExistente() {
        ConfiguracaoControlador fabricante = new ConfiguracaoControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ConfiguracoesControladoresController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(fabricante));
        Result result = route(request);
        assertEquals(404, result.status());
    }

    @Test
    public void testBuscarDadosConfiguracaoControlador() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.save();
        UUID configuracaoControladorId = configuracaoControlador.getId();
        assertNotNull(configuracaoControladorId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ConfiguracoesControladoresController.findOne(configuracaoControladorId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ConfiguracaoControlador configuracaoControladorRetornada = Json.fromJson(json, ConfiguracaoControlador.class);

        assertEquals(200, result.status());
        assertEquals(configuracaoControladorId, configuracaoControladorRetornada.getId());
    }

    @Test
    public void testApagarConfiguracaoControladorExistente() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ConfiguracoesControladoresController.delete(configuracaoControlador.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Fabricante.find.byId(configuracaoControlador.getId()));
    }

    @Test
    public void testApagarConfiguracaoControladorNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ConfiguracoesControladoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
}
