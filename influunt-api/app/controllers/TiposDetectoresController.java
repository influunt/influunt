package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import exceptions.EntityNotFound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import models.Cidade;
import models.TipoDetector;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TipoDetectorCrudService;

@Api(tags = { "Tipo Detector" })
public class TiposDetectoresController extends Controller {

    @Inject
    private TipoDetectorCrudService tipoDetectorService;

    @ApiOperation(value = "Listar todos os tipos de detectores", httpMethod = "GET", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de tipos de detctores", response = TipoDetector.class),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetectorService.findAll())));
    }

    @ApiOperation(value = "Criar um novo tipo de detector", httpMethod = "POST", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tipo de detector", dataType = "models.TipoDetector", required = true, paramType = "body", value = "TipoDetector") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo de detector criado com sucesso", response = TipoDetector.class),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            TipoDetector tipoDetector = Json.fromJson(json, TipoDetector.class);
            tipoDetectorService.save(tipoDetector);
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    @ApiOperation(value = "Atualizar os dados de um tipo de detector", httpMethod = "PUT", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tipo de detector", dataType = "models.TipoDetector", required = true, paramType = "body", value = "TipoDetector") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "O tipo de detector foi autalizado", response = TipoDetector.class),
            @ApiResponse(code = 400, message = "Request mal formado"),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        TipoDetector tipoDetector = Json.fromJson(json, TipoDetector.class);
        tipoDetector = tipoDetectorService.update(tipoDetector, id);
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
    }

    @ApiOperation(value = "Buscar dados de um tipo de detector", httpMethod = "GET", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "O tipo de detector foi retornado com sucesso", response = TipoDetector.class),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "O tipo de detector não foi encontrado") })
    public CompletionStage<Result> findOne(String id) {
        TipoDetector tipoDetector = tipoDetectorService.findOne(id);
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    @ApiOperation(value = "Deletar um tipo de detector", httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Tipo de detector apagado com sucesso"),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "A cidade não foi encontrada") })
    public CompletionStage<Result> delete(String id) {
        try {
            tipoDetectorService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

}