package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ConfiguracaoControlador;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ConfiguracoesControladoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(ConfiguracaoControlador.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ConfiguracaoControlador configuracaoControlador = Json.fromJson(json, ConfiguracaoControlador.class);
        configuracaoControlador.save();
        return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }else{
            configuracaoControlador = Json.fromJson(json, ConfiguracaoControlador.class);
            configuracaoControlador.setId(UUID.fromString(id));
            configuracaoControlador.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            configuracaoControlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }
}
