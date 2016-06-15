package controllers;

import helpers.ControladorHelper;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class HelpersController extends Controller {


    @Transactional
    public CompletionStage<Result> controladorHelper() {
        return CompletableFuture.completedFuture(ok(Json.toJson(new ControladorHelper())));
    }



}