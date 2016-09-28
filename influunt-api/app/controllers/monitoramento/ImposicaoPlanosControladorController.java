package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.ImposicaoPlanosControlador;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ImposicaoPlanosControladorController extends Controller {

    public CompletionStage<Result> findOne(String id) {
        List<ImposicaoPlanosControlador> status = ImposicaoPlanosControlador.findByIdControlador(id);
        if (status == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(status)));
        }
    }

    public CompletionStage<Result> ultimoStatusPlanoImpostoDosControladores() {
        HashMap<String, Boolean> map = ImposicaoPlanosControlador.ultimoStatusPlanoImpostoDosControladores();
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoStatusPlanoImposto(String id) {
        ImposicaoPlanosControlador imposicaoPlanosControlador = ImposicaoPlanosControlador.ultimoStatusPlanoImposto(id);
        if (imposicaoPlanosControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(imposicaoPlanosControlador)));
        }

    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<ImposicaoPlanosControlador> imposicaoPlanosControladores = ImposicaoPlanosControlador.historico(id, Integer.valueOf(pagina),
                Integer.valueOf(tamanho));
        if (imposicaoPlanosControladores == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(imposicaoPlanosControladores)));
        }

    }
}
