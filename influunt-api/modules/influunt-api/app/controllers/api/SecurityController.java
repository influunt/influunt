package controllers.api;

import checks.Erro;
import com.fasterxml.jackson.databind.JsonNode;
import excpetions.InfluuntNoMatchException;
import models.Sessao;
import models.Usuario;
import org.joda.time.DateTime;
import play.Configuration;
import play.api.Play;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.AuthToken;
import security.Authenticator;
import utils.InfluuntEmailService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class SecurityController extends Controller {

    @Inject
    private Authenticator authenticator;

    @Inject
    private InfluuntEmailService influuntEmailService;

    @Inject
    private Configuration configuration;

    @Transactional
    public CompletionStage<Result> login() {
        JsonNode json = request().body().asJson();
        if (json != null && json.has("login") && json.has("senha")) {
            String login = json.get("login").asText();
            String senha = json.get("senha").asText();
            final Usuario usuario = (Usuario) authenticator.getSubjectByCredentials(login, senha);
            if (usuario != null) {
                response().setHeader(AuthToken.TOKEN, authenticator.createSession(usuario, request().remoteAddress()));
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            }
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("login", "usuário ou senha inválidos", "")))));
        } else {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("login", "usuário ou senha inválidos", "")))));
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

    @Transactional
    public CompletionStage<Result> recuperarSenha() {
        JsonNode json = request().body().asJson();
        if (json != null && json.has("email")) {
            Usuario usuario = Usuario.find.where().eq("email", json.get("email").asText()).findUnique();
            if (usuario != null) {
                String token = UUID.randomUUID().toString().replace("-", "");
                String link = String.format("%s?token=%s/#/redefinir_senha", configuration.getString("influuntUrl"), token);
                usuario.setResetPasswordToken(token);
                usuario.setPasswordTokenExpiration(DateTime.now().plusHours(24));
                usuario.update();
                if (!Play.current().isTest()) {
                    influuntEmailService.enviarEmailRecuperarSenha(usuario.getNome(), link, usuario.getEmail());
                }
                return CompletableFuture.completedFuture(ok());
            }
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson("Email não cadastrado.")));
    }

    @Transactional
    public CompletionStage<Result> checkResetPasswordToken(String token) {
        Usuario usuario = Usuario.find.where().eq("reset_password_token", token).findUnique();
        if (usuario == null || !usuario.isResetPasswordTokenValid(token)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson("Este link de redefinição de senha não é mais válido.")));
        }

        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> redefinirSenha() {
        JsonNode json = request().body().asJson();
        if (json != null && json.has("senha") && json.has("novaSenha")) {
            Usuario usuario = Usuario.find.where().eq("reset_password_token", json.get("token").asText()).findUnique();
            if (usuario != null) {
                try {
                    usuario.redefinirSenha(json.get("senha").asText(), json.get("novaSenha").asText());
                } catch (InfluuntNoMatchException e) {
                    return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson("Senhas diferentes!")));
                }
                usuario.update();
                return CompletableFuture.completedFuture(ok());
            }
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson("Email não cadastrado.")));
    }

}
