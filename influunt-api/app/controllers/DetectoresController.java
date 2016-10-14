package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import models.Detector;
import play.db.ebean.Transactional;
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
public class DetectoresController extends Controller {

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Detector detector = Detector.find.byId(UUID.fromString(id));
        if (detector == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            if (detector.delete()) {
                return CompletableFuture.completedFuture(ok());
            } else {
                return CompletableFuture.completedFuture(badRequest());
            }
        }
    }
}
