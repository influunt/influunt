package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import helpers.ControladorHelper;
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
public class HelpersController extends Controller {

    @Transactional
    public CompletionStage<Result> controladorHelper() {
        return CompletableFuture.completedFuture(ok(Json.toJson(new ControladorHelper())));
    }

}
