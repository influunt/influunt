package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.Usuario;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AppDynamicResourceHandler implements DynamicResourceHandler {

    @Override
    public CompletionStage<Boolean> checkPermission(String name,
                                                    Optional<String> meta,
                                                    DeadboltHandler deadboltHandler,
                                                    Http.Context ctx) {

        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    @Override
    public CompletionStage<Boolean> isAllowed(String permissionValue,
                                              Optional<String> meta,
                                              DeadboltHandler deadboltHandler,
                                              Http.Context ctx) {

        if (permissionValue.equals("Influunt")) {
            Usuario u = (Usuario) ctx.args.get("user");
            if (u == null) {
                return CompletableFuture.completedFuture(Boolean.FALSE);
            } else if (u.isRoot()) {
                return CompletableFuture.completedFuture(Boolean.TRUE);
            } else {
                String chave = ctx.args.get("ROUTE_VERB").toString() + " " + ctx.args.get("ROUTE_PATTERN").toString();
                if (u.isAllowed(chave)) {
                    return CompletableFuture.completedFuture(Boolean.TRUE);
                } else {
                    return CompletableFuture.completedFuture(Boolean.FALSE);
                }
            }
        }
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }

}
