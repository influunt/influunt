package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.TipoDetector;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class TiposDetectoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(TipoDetector.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            TipoDetector tipoDetector = Json.fromJson(json, TipoDetector.class);
            tipoDetector.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        TipoDetector tipoDetector = TipoDetector.find.byId(UUID.fromString(id));
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            tipoDetector = Json.fromJson(json, TipoDetector.class);
            tipoDetector.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        TipoDetector tipoDetector = TipoDetector.find.byId(UUID.fromString(id));
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        TipoDetector tipoDetector = TipoDetector.find.byId(UUID.fromString(id));
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            tipoDetector.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

}