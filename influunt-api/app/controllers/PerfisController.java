package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.Perfil;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class PerfisController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Perfil perfil = Json.fromJson(json, Perfil.class);
            perfil.save();
            perfil.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Perfil perfil = Perfil.find.byId(UUID.fromString(id));
        if (perfil == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            perfil.getPermissoes();
            return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Perfil.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Perfil perfil = Perfil.find.byId(UUID.fromString(id));
        if(perfil == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        perfil.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Perfil perfilExistente = Perfil.find.byId(UUID.fromString(id));
        if (perfilExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Perfil perfil = Json.fromJson(json, Perfil.class);
        perfil.setId(UUID.fromString(id));
        perfil.update();
        return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
    }

}
