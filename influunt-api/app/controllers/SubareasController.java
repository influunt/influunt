package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import checks.SubareasCheck;
import com.fasterxml.jackson.databind.JsonNode;
import models.Subarea;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class SubareasController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Subarea subarea = Json.fromJson(json, Subarea.class);
            List<Erro> erros = new InfluuntValidator<Subarea>().validate(subarea, Default.class, SubareasCheck.class);

            if (erros.isEmpty()) {
                subarea.save();
                subarea.refresh();
                return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Subarea subarea = Subarea.find.fetch("area").fetch("area.cidade").where().eq("id", id).findUnique();
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(new InfluuntQueryBuilder(Subarea.class, request().queryString()).fetch(Arrays.asList("area", "area.cidade")).query())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Subarea subarea = Subarea.find.byId(UUID.fromString(id));
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        subarea.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Subarea subareaExistente = Subarea.find.byId(UUID.fromString(id));
        if (subareaExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Subarea subarea = Json.fromJson(json, Subarea.class);
        subarea.setId(subareaExistente.getId());
        List<Erro> erros = new InfluuntValidator<Subarea>().validate(subarea, Default.class, SubareasCheck.class);

        if (erros.isEmpty()) {
            subarea.update();
            subarea.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

    }

}
