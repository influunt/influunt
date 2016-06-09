package controllers;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ResponseHeader;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import security.Authenticator;

@Api(value = "/api/login" )

public class SecurityController extends Controller {

    @Inject
    private Authenticator authenticator;

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    @ApiOperation(value = "Efetua o login do usuário", 
	          notes = "Dados do usuário", 
	          httpMethod = "POST",
	          authorizations = { @Authorization(value = "basic", scopes = {})})

	          
@ApiResponses(value = { 
	      @ApiResponse(code = 200, message = "A sessão do usuário foi criada ",
		           response = Usuario.class,
	                   responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
	      @ApiResponse(code = 401, message = "Usuário ou senha inválidos") })
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