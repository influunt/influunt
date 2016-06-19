package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Cidade;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CidadesController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {

        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Cidade cidade = Json.fromJson(json, Cidade.class);
        cidade.save();
        return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Cidade.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            cidade.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            cidade = Json.fromJson(json, Cidade.class);
            cidade.setId(UUID.fromString(id));
            cidade.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }

    }

}
