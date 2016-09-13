package controllers;

import checks.Erro;
import com.fasterxml.jackson.databind.JsonNode;
import models.Sessao;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authenticator;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class SecurityController extends Controller {

    public static final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";

    public static final String AUTH_TOKEN = "authToken";

    @Inject
    private Authenticator authenticator;

    @Transactional
    public CompletionStage<Result> login() {
        JsonNode json = request().body().asJson();
        if (json != null && json.has("login") && json.has("senha")) {
            String login = json.get("login").asText();
            String senha = json.get("senha").asText();
            final Usuario usuario = (Usuario) authenticator.getSubjectByCredentials(login, senha);
            if (usuario != null) {
                response().setHeader(AUTH_TOKEN, authenticator.createSession(usuario));
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            }
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("login", "usuário ou senha inválidos", "")))));
        } else {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("login", "usuário ou senha inválidos", "")))));
        }
    }


    @Transactional
    public CompletionStage<Result> logout(String id) {
        Sessao sessao = Sessao.find.byId(UUID.fromString(id));
        if (sessao == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        sessao.desativar();
        return CompletableFuture.completedFuture(ok());
    }

}
