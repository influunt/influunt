package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Fabricante;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class FabricantesController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Fabricante fabricante = Json.fromJson(json, Fabricante.class);
        List<Erro> erros = new InfluuntValidator<Fabricante>().validate(fabricante);

        if (erros.isEmpty()) {
            fabricante.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(fabricante)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Fabricante.class, request().queryString()).query());
        return CompletableFuture.completedFuture(ok(result.toJson()));
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
        } else {
            fabricante = Json.fromJson(json, Fabricante.class);
            fabricante.setId(UUID.fromString(id));
            List<Erro> erros = new InfluuntValidator<Fabricante>().validate(fabricante);

            if (erros.isEmpty()) {
                fabricante.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(fabricante)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
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
