package controllers.api.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import models.ControladorFisico;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.StatusConexaoControlador;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 9/8/16.
 */

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class StatusControladorController extends Controller {

    public CompletionStage<Result> findOne(String id) {
        List<StatusConexaoControlador> status = StatusConexaoControlador.findByIdControlador(id);
        if (status == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(status)));
        }
    }

    public CompletionStage<Result> ultimoStatusDosControladores() {
        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        List<String> controladores = ControladorFisico.getControladoresSincronizadosPorUsuario(usuario).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());
        Map<String, Boolean> map = StatusConexaoControlador.ultimoStatusDosControladores(controladores);
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


    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }

}
