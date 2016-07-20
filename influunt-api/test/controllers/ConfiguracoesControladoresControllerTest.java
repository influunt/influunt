package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import models.ConfiguracaoControlador;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;


public class ConfiguracoesControladoresControllerTest extends WithApplication {

    private final String DESCRICAO = "Configuracao 4 Controladores";

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
    public void testCriarConfiguracaoInvalida() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.setDescricao(DESCRICAO);
        configuracaoControlador.setLimiteTabelasEntreVerdes(0);
        configuracaoControlador.setLimiteAnel(0);
        configuracaoControlador.setLimiteGrupoSemaforico(0);
        configuracaoControlador.setLimiteDetectorPedestre(0);
        configuracaoControlador.setLimiteDetectorVeicular(0);
        configuracaoControlador.setLimiteEstagio(0);

        int totalConfiguracoes = ConfiguracaoControlador.find.findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ConfiguracoesControladoresController.create().url()).bodyJson(Json.toJson(configuracaoControlador));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Map<String, String>> erros = Json.fromJson(json, List.class);

        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        assertEquals(6, erros.size());
        assertEquals(totalConfiguracoes, ConfiguracaoControlador.find.findRowCount());
    }

    @Test
    public void testCriarNovaConfiguracaoControlador() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.setDescricao(DESCRICAO);

        int totalConfiguracoes = ConfiguracaoControlador.find.findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ConfiguracoesControladoresController.create().url()).bodyJson(Json.toJson(configuracaoControlador));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ConfiguracaoControlador configuracaoControladorRetornada = Json.fromJson(json, ConfiguracaoControlador.class);

        assertEquals(200, result.status());
        assertEquals(DESCRICAO, configuracaoControlador.getDescricao());
        assertNotNull(configuracaoControladorRetornada.getId());
        assertEquals(totalConfiguracoes+1, ConfiguracaoControlador.find.findRowCount());
    }

    @Test
    public void testAtualizarConfiguracaoControladorExistente() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.setDescricao(DESCRICAO);
        configuracaoControlador.save();

        UUID configuracaoControladorId = configuracaoControlador.getId();
        assertEquals(DESCRICAO, configuracaoControlador.getDescricao());
        assertNotNull(configuracaoControladorId);

        ConfiguracaoControlador configuracaoControlador1 = new ConfiguracaoControlador();
        configuracaoControlador1.setDescricao("NOVA");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ConfiguracoesControladoresController.update(configuracaoControladorId.toString()).url())
                .bodyJson(Json.toJson(configuracaoControlador1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ConfiguracaoControlador configuracaoControladorRetornado = Json.fromJson(json, ConfiguracaoControlador.class);

        assertEquals(200, result.status());
        assertEquals("NOVA", configuracaoControladorRetornado.getDescricao());
        assertNotNull(configuracaoControladorRetornado.getId());
    }

    @Test
    public void testAtualizarConfiguracaoControladorNaoExistente() {
        ConfiguracaoControlador configuracaoControlador = new ConfiguracaoControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ConfiguracoesControladoresController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(configuracaoControlador));
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
        assertNull(ConfiguracaoControlador.find.byId(configuracaoControlador.getId()));
    }

    @Test
    public void testApagarConfiguracaoControladorNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ConfiguracoesControladoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
}
