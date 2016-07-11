package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class AreasController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Area area = Json.fromJson(json, Area.class);
            List<Erro> erros = new InfluuntValidator<Area>().validate(area);

            if(erros.isEmpty()) {
                area.save();
                area.refresh();
                return CompletableFuture.completedFuture(ok(Json.toJson(area)));
            }else{
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
       }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Area area = Area.find.byId(UUID.fromString(id));
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            area.getLimitesGeograficos();
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Area.find.fetch("cidade").findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Area area = Area.find.byId(UUID.fromString(id));
        if(area == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        area.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Area areaExistente = Area.find.byId(UUID.fromString(id));
        if (areaExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Area area = Json.fromJson(json, Area.class);
        area.setId(areaExistente.getId());
        List<Erro> erros = new InfluuntValidator<Area>().validate(area);

        if(erros.isEmpty()) {
            area.update();
            area.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }else{
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

    }

}
