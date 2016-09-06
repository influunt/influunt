package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Auditoria;
import security.Secured;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class AuditoriaController extends Controller {

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Auditoria auditoria = Auditoria.findById(id);
        if (auditoria == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(auditoria)));
        }
    }

    public CompletionStage<Result> findAll() {

        if (!request().queryString().isEmpty()) {
            String login = (request().queryString().get("login") != null) ? request().queryString().get("login")[0] : null;
            String tabela = (request().queryString().get("table") != null) ? request().queryString().get("table")[0] : null;
            if (login != null && tabela != null) {
                return CompletableFuture.completedFuture(ok(Json.toJson(Auditoria.findAllByLoginAndTable(login, tabela))));
            } else if (login != null) {
                return CompletableFuture.completedFuture(ok(Json.toJson(Auditoria.findAllByLogin(login))));
            } else if (tabela != null) {
                return CompletableFuture.completedFuture(ok(Json.toJson(Auditoria.findAllByTable(tabela))));
            }
            return CompletableFuture.completedFuture(ok(Json.toJson(Auditoria.findAll())));
        }
        return CompletableFuture.completedFuture(ok(Json.toJson(Auditoria.findAll())));
    }


}
