package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AneisController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Anel anel = Json.fromJson(json, Anel.class);
            anel.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(anel)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Anel anel = Anel.find.byId(UUID.fromString(id));
        if (anel == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(anel)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Anel.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Anel anel = Anel.find.byId(UUID.fromString(id));
        if(anel == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        anel.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Anel anelExistente = Anel.find.byId(UUID.fromString(id));
        if (anelExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Anel anel = Json.fromJson(json, Anel.class);
        anel.setId(UUID.fromString(id));
        anel.update();
        return CompletableFuture.completedFuture(ok(Json.toJson(anel)));
    }

}
