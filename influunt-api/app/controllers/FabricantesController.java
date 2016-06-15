package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Fabricante;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class FabricantesController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Fabricante fabricante = Json.fromJson(json, Fabricante.class);
        fabricante.save();
        return CompletableFuture.completedFuture(ok(Json.toJson(fabricante)));
    }


    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Fabricante.find.findList())));
    }


    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Fabricante fabricante = Fabricante.find.byId(UUID.fromString(id));
        if (fabricante == null) {
            return CompletableFuture.completedFuture(notFound());
        }else{
            fabricante = Json.fromJson(json, Fabricante.class);
            fabricante.setId(UUID.fromString(id));
            fabricante.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(fabricante)));
        }

    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Fabricante fabricante = Fabricante.find.byId(UUID.fromString(id));
        if (fabricante == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(fabricante)));
        }
    }


    @Transactional
    public CompletionStage<Result> delete(String id) {
        Fabricante fabricante = Fabricante.find.byId(UUID.fromString(id));
        if (fabricante == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            fabricante.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }


}
