package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ResponseHeader;
import models.Cidade;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authenticator;
import services.CidadeCrudService;

import static play.mvc.Results.badRequest;

@Api(value = "/api/cidades")
public class CidadesController extends Controller {

    @Inject
    private CidadeCrudService cidadeService;

    @ApiOperation(value = "Criar uma nova cidade", httpMethod = "POST", authorizations = {
            @Authorization(value = "basic", scopes = {}) })

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A cidade foi criada ", response = Usuario.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Cidade cidade = Json.fromJson(json, Cidade.class);
            cidadeService.save(cidade);
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }

    }

}