package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.StatusConexaoControlador;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 * Created by rodrigosol on 9/8/16.
 */

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class StatusControladorController {

    public CompletionStage<Result> findOne(String id) {
        List<StatusConexaoControlador> status = StatusConexaoControlador.findByIdControlador(id);
        if (status == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(status)));
        }
    }

    public CompletionStage<Result> ultimoStatusDosControladores() {
        HashMap<String, Boolean> map = StatusConexaoControlador.ultimoStatusDosControladores();
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoStatus(String id) {
        StatusConexaoControlador statusConexaoControlador = StatusConexaoControlador.ultimoStatus(id);
        if (statusConexaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(statusConexaoControlador)));
        }

    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<StatusConexaoControlador> statusConexaoControlador = StatusConexaoControlador.historico(id, Integer.valueOf(pagina),
                Integer.valueOf(tamanho));
        if (statusConexaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(statusConexaoControlador)));
        }

    }


}
