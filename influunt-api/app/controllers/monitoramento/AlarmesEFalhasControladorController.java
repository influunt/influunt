package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import engine.TipoEvento;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.AlarmesFalhasControlador;


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
public class AlarmesEFalhasControladorController {

    public CompletionStage<Result> findOne(String id) {
        List<AlarmesFalhasControlador> alarmesFalhasControlador = AlarmesFalhasControlador.findByIdControlador(id);
        if (alarmesFalhasControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(alarmesFalhasControlador)));
        }
    }

    public CompletionStage<Result> ultimosAlarmesEFalhasDosControladores() {
        HashMap<String, TipoEvento> map = AlarmesFalhasControlador.ultimosAlarmesFalhas();
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoAlarmeFalhaControlador(String id) {
        AlarmesFalhasControlador alarmesFalhasControlador = AlarmesFalhasControlador.ultimoAlarmeFalhaControlador(id);
        if (alarmesFalhasControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(alarmesFalhasControlador)));
        }

    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<AlarmesFalhasControlador> alarmesFalhasControlador = AlarmesFalhasControlador.historico(id, Integer.valueOf(pagina),
            Integer.valueOf(tamanho));
        if (alarmesFalhasControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(alarmesFalhasControlador)));
        }

    }
}
