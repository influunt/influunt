package controllers;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import security.Authenticator;


public class SecurityController extends Controller {

    @Inject
    private Authenticator authenticator;

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    public CompletionStage<Result> login() {
        final String authorization = request().getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {

            final String base64Credentials = authorization.substring("Basic".length()).trim();
            final String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));

            final String[] values = credentials.split(":", 2);
            final Usuario usuario = (Usuario) authenticator.getSubjectByCredentials(values[0], values[1]);
            if (usuario != null) {
                response().setCookie(Http.Cookie.builder(AUTH_TOKEN, authenticator.createSession(usuario))
                        .withSecure(ctx().request().secure()).build());
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            }
        }
        return CompletableFuture.completedFuture(unauthorized());
    }

}