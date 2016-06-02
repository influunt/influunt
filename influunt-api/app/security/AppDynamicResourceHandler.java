package security;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.mvc.Http.Context;

public class AppDynamicResourceHandler implements DynamicResourceHandler {

    @Override
    public CompletionStage<Boolean> checkPermission(final String arg0, Optional<String> arg1, DeadboltHandler arg2,
	    Context arg3) {
	return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    @Override
    public CompletionStage<Boolean> isAllowed(final String arg0, Optional<String> arg1, DeadboltHandler arg2,
	    Context arg3) {
	return CompletableFuture.completedFuture(Boolean.FALSE);
    }

}
