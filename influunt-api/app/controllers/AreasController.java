package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import exceptions.EntityNotFound;
import models.Area;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.AreaCrudService;

public class AreasController extends Controller {

    @Inject
    private AreaCrudService aeraService;

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

    public CompletionStage<Result> findOne(String id) {
        Area area = aeraService.findOne(id);
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(aeraService.findAll())));
    }

    public CompletionStage<Result> delete(String id) {
        try {
            aeraService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

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