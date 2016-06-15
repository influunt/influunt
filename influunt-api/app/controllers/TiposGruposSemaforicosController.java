package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.TipoDetector;
import models.TipoGrupoSemaforico;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class TiposGruposSemaforicosController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(TipoGrupoSemaforico.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            TipoGrupoSemaforico tipoGrupoSemaforico = Json.fromJson(json, TipoGrupoSemaforico.class);
            tipoGrupoSemaforico.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoGrupoSemaforico)));
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        TipoGrupoSemaforico tipoGrupoSemaforico = TipoGrupoSemaforico.find.byId(UUID.fromString(id));
        if (tipoGrupoSemaforico == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            tipoGrupoSemaforico = Json.fromJson(json, TipoGrupoSemaforico.class);
            tipoGrupoSemaforico.setId(UUID.fromString(id));
            tipoGrupoSemaforico.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoGrupoSemaforico)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        TipoGrupoSemaforico tipoGrupoSemaforico = TipoGrupoSemaforico.find.byId(UUID.fromString(id));
        if (tipoGrupoSemaforico == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(tipoGrupoSemaforico)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        TipoGrupoSemaforico tipoGrupoSemaforico = TipoGrupoSemaforico.find.byId(UUID.fromString(id));
        if (tipoGrupoSemaforico == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            tipoGrupoSemaforico.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

}
