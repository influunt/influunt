package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import models.FaixasDeValores;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class FaixasDeValoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findOne() {
        FaixasDeValores valores = FaixasDeValores.getInstance();
        if (valores == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(valores)));
        }
    }

    @Transactional
    public CompletionStage<Result> update() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        FaixasDeValores valores = FaixasDeValores.getInstance();
        if (valores == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        FaixasDeValores valoresParsed = Json.fromJson(json, FaixasDeValores.class);
        valoresParsed.setId(valores.getId());
        valoresParsed.update();
        return CompletableFuture.completedFuture(ok(Json.toJson(valoresParsed)));
    }

}
