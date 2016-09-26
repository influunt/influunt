package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import models.ModoOperacaoPlano;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.ModoOperacaoControlador;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ModoOperacaoControladorController extends Controller {

    public CompletionStage<Result> findOne(String id) {
        List<ModoOperacaoControlador> modos = ModoOperacaoControlador.findByIdControlador(id);
        if (modos == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(modos)));
        }
    }

    public CompletionStage<Result> ultimoModoOperacaoDosControladores() {
        HashMap<String, ModoOperacaoPlano> map = ModoOperacaoControlador.ultimoModoOperacaoDosControladores();
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoModoOperacaoControlador(String id) {
        ModoOperacaoControlador modoOperacaoControlador = ModoOperacaoControlador.ultimoModoOperacao(id);
        if (modoOperacaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(modoOperacaoControlador)));
        }
    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<ModoOperacaoControlador> modoOperacaoControlador = ModoOperacaoControlador.historico(id, Integer.valueOf(pagina),
                Integer.valueOf(tamanho));
        if (modoOperacaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(modoOperacaoControlador)));
        }

    }
}
