package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import models.MotivoFalhaControlador;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.ErrosControlador;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ErrosControladorController {

    public CompletionStage<Result> findOne(String id) {
        List<ErrosControlador> errosControladores = ErrosControlador.findByIdControlador(id);
        if (errosControladores == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(errosControladores)));
        }
    }

    public CompletionStage<Result> ultimosErrosDosControladores() {
        HashMap<String, MotivoFalhaControlador> map = ErrosControlador.ultimosErrosDosControladores();
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoErroControlador(String id) {
        ErrosControlador errosControlador = ErrosControlador.ultimoErroControlador(id);
        if (errosControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(errosControlador)));
        }

    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<ErrosControlador> errosControladores = ErrosControlador.historico(id, Integer.valueOf(pagina),
            Integer.valueOf(tamanho));
        if (errosControladores == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(errosControladores)));
        }

    }
}
