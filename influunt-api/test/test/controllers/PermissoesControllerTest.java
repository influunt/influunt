package test.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Permissao;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import test.config.WithInfluuntApplicationNoAuthentication;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class PermissoesControllerTest extends WithInfluuntApplicationNoAuthentication {

    @NotNull
    private Permissao getPermissao() {
        Permissao permissao = new Permissao();
        permissao.setDescricao("Deus");
        permissao.setChave("*");
        return permissao;
    }

    @Test
    public void testCriarNovaPermissao() {
        Permissao permissao = getPermissao();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.PermissoesController.create().url()).bodyJson(Json.toJson(permissao));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Permissao permissaoRetornada = Json.fromJson(json, Permissao.class);
        Logger.debug(json.toString());

        assertEquals(200, postResult.status());
        assertNotNull(permissaoRetornada.getId());
        assertEquals("Deus", permissao.getDescricao());
        assertEquals("*", permissao.getChave());

    }


    @Test
    public void testAtualizarPermissaoNaoExistente() {
        Permissao permissao = getPermissao();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
            .uri(routes.PermissoesController.update(UUID.randomUUID().toString()).url())
            .bodyJson(Json.toJson(permissao));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarPermissaoExistente() {
        Permissao permissao = getPermissao();
        permissao.save();

        Permissao novaPermissao = getPermissao();
        novaPermissao.setDescricao("God");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
            .uri(routes.PermissoesController.update(permissao.getId().toString()).url())
            .bodyJson(Json.toJson(novaPermissao));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Permissao permissaoRetornada = Json.fromJson(json, Permissao.class);

        assertNotNull(permissaoRetornada.getId());
        assertEquals(permissao.getId(), permissaoRetornada.getId());
        assertEquals("God", permissaoRetornada.getDescricao());
    }

    @Test
    public void testApagarPermissaoExistente() {

        Permissao permissao = getPermissao();
        permissao.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.PermissoesController.delete(permissao.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Permissao.find.byId(permissao.getId()));
    }

    @Test
    public void testApagarPermissaoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.PermissoesController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarPermissoes() {
        getPermissao().save();
        getPermissao().save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.PermissoesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Permissao> permissoes = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, permissoes.size());
    }

    @Test
    public void testBuscarDadosPermissao() {

        Permissao permissao = getPermissao();
        permissao.save();


        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.PermissoesController.findOne(permissao.getId().toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Permissao permissaoRetornada = Json.fromJson(json, Permissao.class);

        assertEquals(200, result.status());
        assertEquals(permissao.getId(), permissaoRetornada.getId());
        assertEquals("Deus", permissaoRetornada.getDescricao());
        assertEquals("*", permissaoRetornada.getChave());
    }

}
