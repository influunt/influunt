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
import io.swagger.annotations.ResponseHeader;
import models.Area;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.AreaCrudService;

@Api(tags = { "Area" })
public class AreasController extends Controller {

    @Inject
    private AreaCrudService aeraService;

    @ApiOperation(value = "Criar uma nova area", httpMethod = "POST", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area", dataType = "models.Area", required = true, paramType = "body", value = "Area") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Area criada com sucesso", response = Area.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Area area = Json.fromJson(json, Area.class);
            aeraService.save(area);
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @ApiOperation(value = "Buscar dados de uma area", httpMethod = "GET", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A area foi retornada com sucesso", response = Area.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "A area não foi encontrada") })
    public CompletionStage<Result> findOne(String id) {
        Area area = aeraService.findOne(id);
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @ApiOperation(value = "Listar todas as areas", httpMethod = "GET", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Areas ", response = Usuario.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(aeraService.findAll())));
    }

    @ApiOperation(value = "Deletar uma area", httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Area apagada com sucesso"),
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "A area não foi encontrada") })
    public CompletionStage<Result> delete(String id) {
        try {
            aeraService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

    @ApiOperation(value = "Atualizar os dados de uma area", httpMethod = "PUT", authorizations = {
            @Authorization(value = "basic", scopes = {}) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area", dataType = "models.Area", required = true, paramType = "body", value = "Area") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A area foi atualizada ", response = Usuario.class, responseHeaders = @ResponseHeader(name = "authToken", description = "Cookie com o token de sessão", response = String.class)),
            @ApiResponse(code = 401, message = "Não autorizado") })
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Area area = Json.fromJson(json, Area.class);
            if (area.getId() == null) {
                return CompletableFuture.completedFuture(notFound());
            }
            aeraService.update(area, id);
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

}