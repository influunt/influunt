package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Subject;
import models.Usuario;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AppDeadboltHandler implements DeadboltHandler {

    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(final Context context) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Context arg0) {
        return CompletableFuture.supplyAsync(() -> Optional.of(new AppDynamicResourceHandler()));
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(final Context context) {
        Usuario u = (Usuario) context.args.get("user");
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(u));

    }

    @Override
    public CompletionStage<Result> onAuthFailure(final Context arg0, Optional<String> arg1) {
        return CompletableFuture.completedFuture(Results.forbidden());
    }

}
