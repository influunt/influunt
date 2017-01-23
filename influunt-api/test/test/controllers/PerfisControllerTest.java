package test.controllers;

import controllers.api.routes;

import com.fasterxml.jackson.databind.JsonNode;
import models.Perfil;
import models.Permissao;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import test.config.WithInfluuntApplicationNoAuthentication;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class PerfisControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Permissao permissao;

    @Before
    public void setUp() {
        permissao = new Permissao();
        permissao.setDescricao("Deus");
        permissao.setChave("*");
        permissao.save();
    }

    @NotNull
    private Perfil getPerfil() {
        Perfil perfil = new Perfil();
        perfil.setNome("Root");
        perfil.setPermissoes(Arrays.asList(permissao));
        return perfil;
    }


    @Test
    public void testCriarNovoPerfil() {
        Perfil perfil = getPerfil();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.PerfisController.create().url()).bodyJson(Json.toJson(perfil));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Perfil perfilRetornado = Json.fromJson(json, Perfil.class);
        Logger.debug(json.toString());

        assertEquals(200, postResult.status());
        assertNotNull(perfilRetornado.getId());
        assertEquals("Root", perfil.getNome());
        assertEquals("Deus", perfil.getPermissoes().get(0).getDescricao());

    }


    @Test
    public void testAtualizarPerfilNaoExistente() {
        Perfil perfil = getPerfil();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
            .uri(routes.PerfisController.update(UUID.randomUUID().toString()).url())
            .bodyJson(Json.toJson(perfil));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarPerfilExistente() {
        Perfil perfil = getPerfil();
        perfil.save();

        Perfil novoPerfil = getPerfil();
        novoPerfil.setNome("Admin");
        novoPerfil.setPermissoes(null);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
            .uri(routes.PerfisController.update(perfil.getId().toString()).url())
            .bodyJson(Json.toJson(novoPerfil));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Perfil perfilRetornado = Json.fromJson(json, Perfil.class);

        assertNotNull(perfilRetornado.getId());
        assertEquals(perfil.getId(), perfilRetornado.getId());
        assertEquals("Admin", perfilRetornado.getNome());
        assertEquals(0, perfilRetornado.getPermissoes().size());

    }

    public void testAdicionarPermissao() {
        Perfil perfil = getPerfil();
        perfil.save();

        Perfil novoPerfil = getPerfil();
        novoPerfil.setNome("Admin");
        Permissao permissao1 = new Permissao();
        permissao1.setDescricao("Paje");
        permissao1.setChave("-");
        permissao1.save();
        novoPerfil.getPermissoes().add(permissao1);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
            .uri(routes.PerfisController.update(perfil.getId().toString()).url())
            .bodyJson(Json.toJson(novoPerfil));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Perfil perfilRetornado = Json.fromJson(json, Perfil.class);

        assertNotNull(perfilRetornado.getId());
        assertEquals(perfil.getId(), perfilRetornado.getId());
        assertEquals("Admin", perfilRetornado.getNome());
        assertEquals(2, perfilRetornado.getPermissoes().size());

    }

    @Test
    public void testApagarPerfilExistente() {

        Perfil perfil = getPerfil();
        perfil.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.PerfisController.delete(perfil.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Permissao.find.byId(perfil.getId()));
    }

    @Test
    public void testApagarPerfilNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.PerfisController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarPerfis() {
        getPerfil().save();
        getPerfil().save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.PerfisController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Perfil> perfis = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, perfis.size());
    }

    @Test
    public void testBuscarDadosPerfil() {
        Perfil perfil = getPerfil();
        perfil.save();

        UUID permissaoId = perfil.getPermissoes().get(0).getId();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.PerfisController.findOne(perfil.getId().toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Perfil perfilRetornado = Json.fromJson(json, Perfil.class);

        assertEquals(200, result.status());
        assertEquals(perfil.getId(), perfilRetornado.getId());
        assertEquals("Root", perfilRetornado.getNome());
        assertEquals(permissaoId, perfilRetornado.getPermissoes().get(0).getId());
    }

}
