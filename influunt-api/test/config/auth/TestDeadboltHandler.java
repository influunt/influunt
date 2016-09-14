package config.auth;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Subject;
import models.Usuario;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import security.AllowAllAuthenticator;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by pedropires on 9/13/16.
 */
public class TestDeadboltHandler implements DeadboltHandler {
    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(Http.Context context) {
        Usuario u = (Usuario) new AllowAllAuthenticator().getSubjectByToken("");
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(u));
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content) {
        return CompletableFuture.completedFuture(Results.forbidden());
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context) {
        return CompletableFuture.supplyAsync(() -> Optional.of(new AllowAllDynamicResourceHandler()));
    }
}
