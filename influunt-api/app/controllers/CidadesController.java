package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import exceptions.EntityNotFound;
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
import services.CidadeCrudService;

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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A cidade foi retornada com sucesso", response = Cidade.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "A cidade não foi encontrada") })
    public CompletionStage<Result> findOne(String id) {
        Cidade cidade = cidadeService.findOne(id);
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }
    }

    @ApiOperation(value = "Listar todas as cidades", httpMethod = "GET", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Cidades ", response = Usuario.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(cidadeService.findAll())));
    }

    @ApiOperation(value = "Deletar uma cidade", httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Cidade apagada com sucesso"),
            @ApiResponse(code = 401, message = "Não autorizado") })
    @ApiResponse(code = 404, message = "A cidade não foi encontrada")
    public CompletionStage<Result> delete(String id) {
        try {
            cidadeService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

    @ApiOperation(value = "Atualizar os dados de uma cidade", httpMethod = "PUT", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A cidade foi atualizada ", response = Usuario.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Cidade cidade = Json.fromJson(json, Cidade.class);
            cidadeService.update(cidade, id);
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }
    }

}