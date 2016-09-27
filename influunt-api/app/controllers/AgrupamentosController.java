package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Agrupamento;
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
public class AgrupamentosController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Agrupamento agrupamento = Json.fromJson(json, Agrupamento.class);
        List<Erro> erros = new InfluuntValidator<Agrupamento>().validate(agrupamento);

        if (erros.isEmpty()) {
            agrupamento.save();
            JsonNode agrupamentoJson = Json.toJson(agrupamento);
            return CompletableFuture.completedFuture(ok(agrupamentoJson));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Agrupamento agrupamento = Agrupamento.find.byId(UUID.fromString(id));
        if (agrupamento == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(agrupamento)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Agrupamento.class, request().queryString()).query());
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Agrupamento agrupamento = Agrupamento.find.byId(UUID.fromString(id));
        if (agrupamento == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        agrupamento.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Agrupamento agrupamentoExistente = Agrupamento.find.byId(UUID.fromString(id));
        if (agrupamentoExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Agrupamento agrupamento = Json.fromJson(json, Agrupamento.class);
        agrupamento.setId(agrupamentoExistente.getId());
        List<Erro> erros = new InfluuntValidator<Agrupamento>().validate(agrupamento);

        if (erros.isEmpty()) {
            agrupamento.update();
            agrupamento.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(agrupamento)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

    }

}
