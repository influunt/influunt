package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class UsuariosControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Area area;

    private Perfil perfil;

    @Before
    public void setup() {
        Cidade cidade = new Cidade();
        cidade.setNome("BH");
        cidade.save();

        area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        perfil = new Perfil();
        perfil.setNome("Root");
        perfil.save();

        Permissao permissao = new Permissao();
        permissao.setDescricao("Deus");
        permissao.setChave("*");
        permissao.save();

        perfil.setPermissoes(Arrays.asList(permissao));
        perfil.update();
    }

    @NotNull
    private Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setLogin("admin");
        usuario.setSenha("1234");
        usuario.setNome("Admin");
        usuario.setEmail("admin@influunt.com.br");
        usuario.setArea(area);
        usuario.setPerfil(perfil);
        return usuario;
    }


    @Test
    public void testCriarNovoUsuario() {
        Usuario usuario = getUsuario();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.UsuariosController.create().url()).bodyJson(Json.toJson(usuario));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Usuario usuarioRetornado = Json.fromJson(json, Usuario.class);
        Logger.debug(json.toString());

        assertEquals(200, postResult.status());
        assertNotNull(usuarioRetornado.getLogin());
        assertEquals("admin", usuario.getLogin());
        assertFalse(usuarioRetornado.isRoot());
        assertEquals(Integer.valueOf(1), usuario.getArea().getDescricao());
        assertNotNull(usuarioRetornado.getPerfil());
        assertEquals(1, usuarioRetornado.getPerfil().getPermissoes().size());

    }


    @Test
    public void testAtualizarUsuarioNaoExistente() {
        Usuario usuario = getUsuario();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.UsuariosController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(usuario));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarUsuarioExistente() {
        Usuario usuario = getUsuario();
        usuario.save();

        Usuario novoUsuario = getUsuario();
        novoUsuario.setNome("Root");

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.UsuariosController.update(usuario.getId().toString()).url())
                .bodyJson(Json.toJson(novoUsuario));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Usuario usuarioRetornado = Json.fromJson(json, Usuario.class);

        assertNotNull(usuarioRetornado.getLogin());
        assertEquals("Root", usuarioRetornado.getNome());
    }

    @Test
    public void testApagarUsuarioExistente() {

        Usuario usuario = getUsuario();
        usuario.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.UsuariosController.delete(usuario.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Usuario.find.byId(usuario.getId()));
    }

    @Test
    public void testApagarUsuarioNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.UsuariosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarUsuarios() {
        Usuario usuario1 = getUsuario();
        usuario1.save();
        Usuario usuario2 = getUsuario();
        usuario2.setLogin("admin2");
        usuario2.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.UsuariosController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Usuario> usuarios = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, usuarios.size());
    }

    @Test
    public void testBuscarDadosUsuario() {

        Usuario usuario = getUsuario();
        usuario.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.UsuariosController.findOne(usuario.getId().toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Usuario usuarioRetornado = Json.fromJson(json, Usuario.class);

        assertEquals(200, result.status());
        assertEquals(usuario.getLogin(), usuarioRetornado.getLogin());
    }

}
