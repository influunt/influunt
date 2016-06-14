package influunt.test.integration.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;
import models.Cidade;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;
import services.CidadeCrudService;

public class CidadesControllerTest extends WithApplication {

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
    public void testCriarNovaCidade() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidade));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);
        
        assertEquals(200, postResult.status());
        assertEquals("Teste", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
    }

    @Test
    public void testAtualizarCidadeExistente() {
        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        cidadeService.save(cidade);

        String cidadeId = cidade.getId();
        Cidade novaCidade = new Cidade();
        novaCidade.setNome("Teste atualizar");
        
        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.CidadesController.update(cidadeId).url())
                .bodyJson(Json.toJson(novaCidade));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);
        
        assertEquals(200, result.status());
        assertEquals("Teste atualizar", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
    }
    
    @Test
    public void testAtualizarCidadeNaoExistente() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        
        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.CidadesController.update("xxxx").url())
                .bodyJson(Json.toJson(cidade));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarCidadeExistente() {
        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        cidadeService.save(cidade);
        String cidadeId = cidade.getId();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete(cidadeId).url());
        Result result = route(deleteRequest);
           
        assertEquals(200, result.status());
        cidade = cidadeService.findOne(cidadeId);
        assertNull(cidade);
    }
    
    @Test
    public void testApagarCidadeNaoExistente() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete("1234").url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testListarCidades() {
        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
        
        Cidade cidade = new Cidade();
        cidade.setNome("Cidade 1");
        cidadeService.save(cidade);
        cidade.setNome("Cidade 2");
        cidade.setId(null);
        cidadeService.save(cidade);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json, List.class);  
        
        assertEquals(200, result.status());
        assertEquals(2, cidades.size());
    }
    
}
