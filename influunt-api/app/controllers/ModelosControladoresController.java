package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ModeloControlador;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.InfluuntQueryBuilder;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ModelosControladoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(new InfluuntQueryBuilder(ModeloControlador.class, request().queryString()).fetch(Arrays.asList("fabricante")).query()));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        ModeloControlador modeloControlador = Json.fromJson(json, ModeloControlador.class);
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
