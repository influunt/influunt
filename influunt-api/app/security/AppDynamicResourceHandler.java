package security;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.Usuario;
import play.mvc.Http;
import play.mvc.Http.Context;

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

        if(permissionValue.equals("Influunt")) {
            Usuario u = (Usuario) ctx.args.get("user");
            if(u.isRoot()){
                return CompletableFuture.completedFuture(Boolean.TRUE);
            }else {
                String chave = ctx.args.get("ROUTE_VERB").toString() + " " + ctx.args.get("ROUTE_PATTERN").toString();
                if (u == null || !u.isAllowed(chave)) {
                    return CompletableFuture.completedFuture(Boolean.FALSE);
                } else {
                    return CompletableFuture.completedFuture(Boolean.TRUE);
                }
            }
        }
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }

}
