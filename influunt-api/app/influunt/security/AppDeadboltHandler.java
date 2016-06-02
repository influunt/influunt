package influunt.security;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Subject;
import influunt.models.Usuario;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;

public class AppDeadboltHandler implements DeadboltHandler {

	@Override
	public CompletionStage<Optional<Result>> beforeAuthCheck(Context arg0) {
		 return CompletableFuture.completedFuture(Optional.empty());
	}

	@Override
	public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Context arg0) {
		return CompletableFuture.supplyAsync(() -> Optional.of(new AppDynamicResourceHandler()));
	}

	@Override
	public CompletionStage<Optional<? extends Subject>> getSubject(Context context) {
		Usuario u = new Usuario();
		u.setLogin("rodrigosol@gmail.com");
		return CompletableFuture.supplyAsync(() -> Optional.ofNullable(u));
		
	}

	@Override
	public CompletionStage<Result> onAuthFailure(Context arg0, Optional<String> arg1) {
		  return CompletableFuture.completedFuture(Results.forbidden());
	}

}
