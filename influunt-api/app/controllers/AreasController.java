package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import exceptions.EntityNotFound;
import models.Area;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.AreaCrudService;

public class AreasController extends Controller {

    @Inject
    private AreaCrudService aeraService;

    @Transactional
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

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Area area = aeraService.findOne(id);
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(aeraService.findAll())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        try {
            aeraService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
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