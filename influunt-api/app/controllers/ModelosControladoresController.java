package controllers;

import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.ModeloControlador;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ModelosControladoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(ModeloControlador.class, request().queryString()).fetch(Arrays.asList("fabricante")).query());
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        ModeloControlador modeloControlador = Json.fromJson(json, ModeloControlador.class);
        List<Erro> erros = new InfluuntValidator<ModeloControlador>().validate(modeloControlador, javax.validation.groups.Default.class);
        if (erros.size() > 0) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

        modeloControlador.save();
        return CompletableFuture.completedFuture(ok(Json.toJson(modeloControlador)));
    }


    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ModeloControlador modeloControlador = ModeloControlador.findById(UUID.fromString(id));
        if (modeloControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        modeloControlador = Json.fromJson(json, ModeloControlador.class);
        modeloControlador.setId(UUID.fromString(id));
        modeloControlador.update();
        return CompletableFuture.completedFuture(ok(Json.toJson(modeloControlador)));
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        ModeloControlador modeloControlador = ModeloControlador.findById(UUID.fromString(id));
        if (modeloControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok(Json.toJson(modeloControlador)));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        ModeloControlador modeloControlador = ModeloControlador.find.byId(UUID.fromString(id));
        if (modeloControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        modeloControlador.delete();
        return CompletableFuture.completedFuture(ok());
    }

}
