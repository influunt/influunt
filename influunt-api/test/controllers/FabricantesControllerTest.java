package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.Fabricante;
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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;


public class FabricantesControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration).in(Mode.TEST).build();
    }

    @Test
    public void testCriarNovoFabricante() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Teste");
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.FabricantesController.create().url()).bodyJson(Json.toJson(fabricante));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Fabricante fabricanteRetornado = Json.fromJson(json, Fabricante.class);

        assertEquals(200, postResult.status());
        assertEquals("Teste", fabricanteRetornado.getNome());
        assertNotNull(fabricanteRetornado.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListarFabricantes() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Fabricante 1");
        fabricante.save();

        Fabricante fabricante1 = new Fabricante();
        fabricante1.setNome("Fabricante 2");
        fabricante1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.FabricantesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Fabricante> fabricantes = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, fabricantes.size());
    }


    @Test
    public void testAtualizarFabricanteExistente() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Teste");
        fabricante.save();

        UUID fabricanteId = fabricante.getId();
        Assert.assertNotNull(fabricanteId);

        Fabricante fabricante1 = new Fabricante();
        fabricante1.setNome("Teste atualizar");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.FabricantesController.update(fabricanteId.toString()).url())
                .bodyJson(Json.toJson(fabricante1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Fabricante fabricanteRetornado = Json.fromJson(json, Fabricante.class);

        assertEquals(200, result.status());
        assertEquals("Teste atualizar", fabricanteRetornado.getNome());
        Assert.assertNotNull(fabricanteRetornado.getId());
    }

    @Test
    public void testAtualizarFabricanteNaoExistente() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Teste");

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.FabricantesController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(fabricante));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarFabricanteExistente() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Teste");
        fabricante.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.FabricantesController.delete(fabricante.getId().toString()).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        assertNull(Fabricante.find.byId(fabricante.getId()));
    }

    @Test
    public void testApagarFabricanteNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.FabricantesController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testBuscarDadosFabricante() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Teste");
        fabricante.save();
        UUID fabricanteId = fabricante.getId();
        Assert.assertNotNull(fabricanteId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.FabricantesController.findOne(fabricanteId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Fabricante fabricanteRetornado = Json.fromJson(json, Fabricante.class);

        assertEquals(200, result.status());
        assertEquals(fabricanteId, fabricanteRetornado.getId());
    }

}
