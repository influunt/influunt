package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AreasController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Area area = Json.fromJson(json, Area.class);
            area.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Area area = Area.find.byId(UUID.fromString(id));
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Area.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Area area = Area.find.byId(UUID.fromString(id));
        if(area == null) {
            return CompletableFuture.completedFuture(notFound());
        }else {
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            if (Area.find.byId(UUID.fromString(id)) == null) {
                return CompletableFuture.completedFuture(notFound());
            }else{
                Area area = Json.fromJson(json, Area.class);
                area.setId(UUID.fromString(id));
                area.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(area)));
            }
        }
    }

}