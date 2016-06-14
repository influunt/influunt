package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.EntityNotFound;
import models.Cidade;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CidadeCrudService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CidadesController extends Controller {

    @Inject
    private CidadeCrudService cidadeService;

    @Transactional
    public CompletionStage<Result> create() {

        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Cidade cidade = Json.fromJson(json, Cidade.class);
        cidadeService.save(cidade);
        return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Cidade cidade = cidadeService.findOne(id);
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(cidadeService.findAll())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        try {
            cidadeService.delete(id);
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
        }
        Cidade cidade = Json.fromJson(json, Cidade.class);
        cidade = cidadeService.update(cidade, id);
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
    }

}