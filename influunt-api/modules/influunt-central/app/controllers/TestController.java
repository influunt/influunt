package controllers;

import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by pedropires on 1/18/17.
 */
public class TestController extends Controller {
    @Transactional
    public CompletionStage<Result> test() {
        return CompletableFuture.completedFuture(ok("Teste OK!"));
    }
}

