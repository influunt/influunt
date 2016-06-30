package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import models.Perfil;
import models.Permissao;
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
public class PermissoesController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Permissao permissao = Json.fromJson(json, Permissao.class);
            permissao.save();
            permissao.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(permissao)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Permissao permissao = Permissao.find.byId(UUID.fromString(id));
        if (permissao == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(permissao)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Permissao.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Permissao permissao = Permissao.find.byId(UUID.fromString(id));
        if(permissao == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        permissao.delete();
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Permissao permissaoExistente = Permissao.find.byId(UUID.fromString(id));
        if (permissaoExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Permissao permissao = Json.fromJson(json, Permissao.class);
        permissao.setId(UUID.fromString(id));
        permissao.update();
        return CompletableFuture.completedFuture(ok(Json.toJson(permissao)));
    }

}
