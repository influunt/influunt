package controllers.api;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.google.inject.Inject;
import com.google.inject.Provider;
import models.Estagio;
import play.Application;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Security.Authenticated(Secured.class)
public class EstagiosController extends Controller {

    @Inject
    private Provider<Application> provider;

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> delete(String id) {
        Estagio estagio = Estagio.find.byId(UUID.fromString(id));
        if (estagio == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            if (estagio.delete(provider.get().path())) {
                estagio.getAnel().getControlador().removerPlanosTabelasHorarios();
                return CompletableFuture.completedFuture(ok());
            } else {
                return CompletableFuture.completedFuture(badRequest());
            }
        }
    }
}
