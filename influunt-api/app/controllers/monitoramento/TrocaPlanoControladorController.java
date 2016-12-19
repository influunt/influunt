package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import models.ControladorFisico;
import models.ModoOperacaoPlano;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.TrocaDePlanoControlador;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class TrocaPlanoControladorController extends Controller {

    public CompletionStage<Result> findOne(String id) {
        List<TrocaDePlanoControlador> trocas = TrocaDePlanoControlador.findByIdControlador(id);
        if (trocas == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(trocas)));
        }
    }

    public CompletionStage<Result> ultimoModoOperacaoDosControladores() {
        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        List<String> controladores = ControladorFisico.getControladorPorUsuario(usuario).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());

        HashMap<String, ModoOperacaoPlano> map = TrocaDePlanoControlador.ultimoModoOperacaoDosControladores(controladores);
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoTrocaDePlanoDoControlador(String id) {
        TrocaDePlanoControlador trocaDePlanoControlador = TrocaDePlanoControlador.ultimaTrocaDePlanoDoControlador(id);
        if (trocaDePlanoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(trocaDePlanoControlador)));
        }
    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<TrocaDePlanoControlador> trocaDePlanoControlador = TrocaDePlanoControlador.historico(id, Integer.valueOf(pagina),
            Integer.valueOf(tamanho));
        if (trocaDePlanoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(trocaDePlanoControlador)));
        }

    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }
}
