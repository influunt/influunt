package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationAuthenticated;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.route;

public class SecurityControllerTest extends WithInfluuntApplicationAuthenticated {

    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setNome("Admin");
        usuario.setLogin("admin");
        usuario.setSenha("1234");
        usuario.setRoot(true);
        usuario.setEmail("root@influunt.com.br");
        usuario.save();
    }

    @Test
    public void testUnauthorizedLogin() throws InterruptedException, ExecutionException {
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url());
        Result postResult = route(postRequest);
        assertEquals(UNAUTHORIZED, postResult.status());
    }

    @Test
    public void testAuthorizedLogin() throws InterruptedException, ExecutionException {
        JsonNode jsonUsuario = Json.parse("{\"login\":\"admin\",\"senha\":\"1234\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url()).bodyJson(jsonUsuario);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        usuario = Json.fromJson(json, Usuario.class);
        assertEquals("admin", usuario.getLogin());
        assertEquals("Admin", usuario.getNome());

    }

    @Test
    public void deveriaGerarTokenParaRecuperarSenha() throws InterruptedException, ExecutionException {
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRecuperarSenha = Json.parse("{\"email\":\"root@influunt.com.br\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.recuperarSenha().url()).bodyJson(jsonRecuperarSenha);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        usuario.refresh();
        assertNotNull(usuario.getResetPasswordToken());
        assertNotNull(usuario.getPasswordTokenExpiration());

    }

    @Test
    public void naoDeveriaGerarTokenParaRecuperarSenhaEmailNaoCadastrado() throws InterruptedException, ExecutionException {
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRecuperarSenha = Json.parse("{\"email\":\"teste@teste.com.br\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.recuperarSenha().url()).bodyJson(jsonRecuperarSenha);
        Result postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        usuario.refresh();
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

    }

    @Test
    public void deveriaValidarTokenRedefinirSenha() throws InterruptedException, ExecutionException {
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRecuperarSenha = Json.parse("{\"email\":\"root@influunt.com.br\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.recuperarSenha().url()).bodyJson(jsonRecuperarSenha);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        usuario.refresh();
        assertNotNull(usuario.getResetPasswordToken());
        assertNotNull(usuario.getPasswordTokenExpiration());

        postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.SecurityController.checkResetPasswordToken(usuario.getResetPasswordToken()).url());
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
    }

    @Test
    public void naoDeveriaValidarTokenRedefinirSenha() throws InterruptedException, ExecutionException {
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.SecurityController.checkResetPasswordToken(UUID.randomUUID().toString()).url());
        Result postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());
    }

    @Test
    public void deveriaRedefinirSenha() throws InterruptedException, ExecutionException {
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRecuperarSenha = Json.parse("{\"email\":\"root@influunt.com.br\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.recuperarSenha().url()).bodyJson(jsonRecuperarSenha);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        usuario.refresh();
        assertNotNull(usuario.getResetPasswordToken());
        assertNotNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRedefinirSenha = Json.parse("{\"token\":\"" + usuario.getResetPasswordToken() + "\",\"senha\":\"abc\",\"novaSenha\":\"abc\"}");
        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.redefinirSenha().url()).bodyJson(jsonRedefinirSenha);
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        usuario.refresh();
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());
    }

    @Test
    public void naoDeveriaRedefinirSenhaDiferente() throws InterruptedException, ExecutionException {
        assertNull(usuario.getResetPasswordToken());
        assertNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRecuperarSenha = Json.parse("{\"email\":\"root@influunt.com.br\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.recuperarSenha().url()).bodyJson(jsonRecuperarSenha);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        usuario.refresh();
        assertNotNull(usuario.getResetPasswordToken());
        assertNotNull(usuario.getPasswordTokenExpiration());

        JsonNode jsonRedefinirSenha = Json.parse("{\"token\":\"" + usuario.getResetPasswordToken() + "\",\"senha\":\"teste\",\"novaSenha\":\"mundo\"}");
        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.redefinirSenha().url()).bodyJson(jsonRedefinirSenha);
        postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        usuario.refresh();
        assertNotNull(usuario.getResetPasswordToken());
        assertNotNull(usuario.getPasswordTokenExpiration());

    }

}
