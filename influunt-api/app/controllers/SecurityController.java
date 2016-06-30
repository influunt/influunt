package controllers;

import checks.Erro;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import security.Authenticator;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class SecurityController extends Controller {

    @Inject
    private Authenticator authenticator;

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    @Transactional
    public CompletionStage<Result> login() {
        final String authorization = request().getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {

            final String base64Credentials = authorization.substring("Basic".length()).trim();
            final String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));

            final String[] values = credentials.split(":", 2);
            final Usuario usuario = (Usuario) authenticator.getSubjectByCredentials(values[0], values[1]);
            if (usuario != null) {
                response().setHeader(AUTH_TOKEN, authenticator.createSession(usuario));
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            }
        }
        return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("login","usuário ou senha inválidos","")))));
    }

}