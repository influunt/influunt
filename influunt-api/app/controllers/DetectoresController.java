package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import models.Detector;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class DetectoresController extends Controller {


    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Detector.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Detector detector = Json.fromJson(json, Detector.class);
        detector.save();
        return CompletableFuture.completedFuture(ok(Json.toJson(detector)));
    }


    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Detector detector = Detector.find.byId(UUID.fromString(id));
        if (detector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            detector = Json.fromJson(json, Detector.class);
            detector.setId(UUID.fromString(id));
            detector.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(detector)));
        }

    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Detector detector = Detector.find.byId(UUID.fromString(id));
        if (detector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(detector)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Detector detector = Detector.find.byId(UUID.fromString(id));
        if (detector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            detector.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }


}
