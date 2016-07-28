package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import models.ConfiguracaoControlador;
import models.Fabricante;
import models.ModeloControlador;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class ModelosControladoresControllerTest extends WithApplication {

    private Fabricante fabricante;
    private ConfiguracaoControlador configuracaoControlador;

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
    public void setUp() {
        fabricante = new Fabricante();
        fabricante.setNome("Raro Labs");
        fabricante.save();

        configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.setDescricao("Teste");
        configuracaoControlador.setLimiteDetectorPedestre(1);
        configuracaoControlador.setLimiteAnel(1);
        configuracaoControlador.setLimiteEstagio(1);
        configuracaoControlador.setLimiteGrupoSemaforico(1);
        configuracaoControlador.save();
    }

    @Test
    public void testListarModelosControladores() {
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("CTA-1");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);

        modeloControlador.save();

        ModeloControlador modeloControlador1 = new ModeloControlador();
        modeloControlador1.setDescricao("CTA-2");
        modeloControlador1.setFabricante(fabricante);
        modeloControlador1.setConfiguracao(configuracaoControlador);

        modeloControlador1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ModelosControladoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<ModeloControlador> modelosControladores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, modelosControladores.size());
    }

    @Test
    public void testCriarNovoModeloControlador() {
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("CTA 1");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);


        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ModelosControladoresController.create().url()).bodyJson(Json.toJson(modeloControlador));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        ModeloControlador modeloControladorRetornado = Json.fromJson(json, ModeloControlador.class);

        assertEquals(200, postResult.status());
        assertEquals("CTA 1", modeloControladorRetornado.getDescricao());
        assertNotNull(modeloControladorRetornado.getId());
    }

    @Test
    public void testAtualizarModeloControladorNaoExistente() {
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("CTA 1");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ModelosControladoresController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(modeloControlador));
        Result result = route(request);
        assertEquals(404, result.status());
    }

    @Test
    public void testAtualizarModeloControladorExistente() {


        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("CTA 1");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);
        modeloControlador.save();

        UUID modeloControladorId = modeloControlador.getId();
        assertNotNull(modeloControladorId);

        ModeloControlador novoModeloControlador = new ModeloControlador();
        novoModeloControlador.setFabricante(fabricante);
        novoModeloControlador.setConfiguracao(configuracaoControlador);
        novoModeloControlador.setDescricao("Teste atualizar");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ModelosControladoresController.update(modeloControladorId.toString()).url())
                .bodyJson(Json.toJson(novoModeloControlador));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ModeloControlador modeloControladorRetornado = Json.fromJson(json, ModeloControlador.class);

        assertEquals(200, result.status());
        assertEquals("Teste atualizar", modeloControladorRetornado.getDescricao());

        assertNotNull(modeloControladorRetornado.getId());
    }

    @Test
    public void testBuscarDadosModeloControlador() {
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("Teste");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);
        modeloControlador.save();

        UUID modeloControladorId = modeloControlador.getId();
        assertNotNull(modeloControladorId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ModelosControladoresController.findOne(modeloControladorId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        ModeloControlador modeloControladorRetornado = Json.fromJson(json, ModeloControlador.class);

        assertEquals(200, result.status());
        assertEquals(modeloControladorId, modeloControladorRetornado.getId());
    }

    @Test
    public void testApagarModeloControladorExistente() {
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setDescricao("Teste");
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);

        modeloControlador.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ModelosControladoresController.delete(modeloControlador.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(ModeloControlador.find.byId(modeloControlador.getId()));
    }

    @Test
    public void testApagarModeloControladorNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ModelosControladoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
}
