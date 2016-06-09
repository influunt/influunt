package influunt.test.integration.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import controllers.routes;
import models.Cidade;
import play.Application;
import play.Mode;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceInjectorBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;
import services.CidadeCrudService;
import services.CrudService;

public class CidadesControllerTest extends WithApplication {

//    @Inject
//    CidadeCrudService cidadeService;
    
    @Override
    protected Application provideApplication() {
        return getApplication(inMemoryDatabase());
    }

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
        assertEquals("Teste", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
    }

    @Test
    public void testAtualizarCidade() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        
        // TODO Verificar com injetar o CidadeCrudService para criar a cidade ao invés de fazer a requisição.
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidade));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);
        assertEquals("Teste", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
        
        cidadeRetornada.setNome("Teste Atualizada");
        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.CidadesController.update(cidadeRetornada.getId()).url()).bodyJson(Json.toJson(cidadeRetornada));
        Result putResult = route(putRequest);
        json = Json.parse(Helpers.contentAsString(putResult));
        cidadeRetornada = Json.fromJson(json, Cidade.class);
        assertEquals("Teste Atualizada", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
    }
    
    @Test
    public void testApagarCidade() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        
        // TODO Verificar com injetar o CidadeCrudService para criar a cidade ao in vés de fazer a requisição.
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidade));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);
        assertEquals("Teste", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());
        
        String cidadeId = cidadeRetornada.getId();
        
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete(cidadeId).url()).bodyJson(Json.toJson(cidade));
        route(deleteRequest);
     
        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
        
        cidade = cidadeService.findOne(cidadeId);
        assertNull(cidade);
    }

}
