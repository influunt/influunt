package controllers.api;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Auditoria;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class AuditoriaController extends Controller {

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Auditoria auditoria = Auditoria.findById(id);
        if (auditoria == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(auditoria)));
        }
    }

    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Auditoria.class, request().queryString()).auditoriaQuery());
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }


}
