package controllers;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.*;

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
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

    @Before
    public void setup() {
        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);
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
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        cidade.save();
        UUID cidadeId = cidade.getId();
        assertNotNull(cidade.getId());

        Cidade novaCidade = new Cidade();
        novaCidade.setNome("Teste atualizar");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.CidadesController.update(cidadeId.toString()).url())
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
                .uri(routes.CidadesController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(cidade));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarCidadeExistente() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        cidade.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete(cidade.getId().toString()).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        assertNull(Cidade.find.byId(cidade.getId()));
    }

    @Test
    public void testApagarCidadeNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListarCidades() {
        Cidade cidade = new Cidade();
        cidade.setNome("Cidade 1");
        cidade.save();

        Cidade cidade1 = new Cidade();
        cidade1.setNome("Cidade 2");
        cidade1.setId(null);
        cidade1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, cidades.size());
    }

    @Test
    public void testBuscarDadosCidade() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        cidade.save();
        UUID cidadeId = cidade.getId();
        assertNotNull(cidadeId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findOne(cidadeId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);

        assertEquals(200, result.status());
        assertEquals(cidadeId, cidadeRetornada.getId());
    }

    @Test
    public void testCriarCidadeNomeDuplicado() {
        Cidade cidade = new Cidade();
        cidade.setNome("Teste");
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidade));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);

        assertEquals(OK, postResult.status());
        assertEquals("Teste", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());

        Cidade cidadeDuplicadaLowerCase = new Cidade();
        cidadeDuplicadaLowerCase.setNome("teste");

        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidadeDuplicadaLowerCase));
        postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

    }

    @Test
    public void testAtualizarCidadeMesmoNome() {
        Cidade cidade = new Cidade();
        cidade.setNome("TESTE");
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.CidadesController.create().url()).bodyJson(Json.toJson(cidade));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);

        assertEquals(OK, postResult.status());
        assertEquals("TESTE", cidadeRetornada.getNome());
        assertNotNull(cidadeRetornada.getId());

        cidadeRetornada.setNome("teste");

        postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.CidadesController.update(cidadeRetornada.getId().toString()).url()).bodyJson(Json.toJson(cidadeRetornada));
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        assertEquals("teste", cidadeRetornada.getNome());

    }
}
