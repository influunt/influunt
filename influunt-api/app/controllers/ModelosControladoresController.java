package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.ModeloControlador;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ModelosControladoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(ModeloControlador.find.findList())));
    }


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ModeloControlador modeloControlador = Json.fromJson(json, ModeloControlador.class);
        List<Erro> erros = new InfluuntValidator<ModeloControlador>().validate(modeloControlador);

        if (erros.isEmpty()) {
            modeloControlador.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(modeloControlador)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }


    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ModeloControlador modeloControlador = ModeloControlador.find.byId(UUID.fromString(id));
        if (modeloControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        modeloControlador = Json.fromJson(json, ModeloControlador.class);
        modeloControlador.setId(UUID.fromString(id));
        List<Erro> erros = new InfluuntValidator<ModeloControlador>().validate(modeloControlador);

        if (erros.isEmpty()) {
            modeloControlador.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(modeloControlador)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        ModeloControlador modeloControlador = ModeloControlador.find.byId(UUID.fromString(id));
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
