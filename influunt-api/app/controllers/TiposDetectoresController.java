package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import exceptions.EntityNotFound;
import models.TipoDetector;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TipoDetectorCrudService;

public class TiposDetectoresController extends Controller {

    @Inject
    private TipoDetectorCrudService tipoDetectorService;

    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetectorService.findAll())));
    }

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

    public CompletionStage<Result> findOne(String id) {
        TipoDetector tipoDetector = tipoDetectorService.findOne(id);
        if (tipoDetector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoDetector)));
        }
    }

    public CompletionStage<Result> delete(String id) {
        try {
            tipoDetectorService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

}