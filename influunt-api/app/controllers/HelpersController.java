package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.ControladorHelper;
import models.Cidade;
import play.Logger;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class HelpersController extends Controller {


    @Transactional
    public CompletionStage<Result> controladorHelper() {
        return CompletableFuture.completedFuture(ok(Json.toJson(new ControladorHelper())));
    }



}