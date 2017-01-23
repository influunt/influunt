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
import status.StatusControladorFisico;

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
public class StatusControladorFisicoController extends Controller {

    public CompletionStage<Result> findOne(String id) {
        List<StatusControladorFisico> status = StatusControladorFisico.findByIdControlador(id);
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

        Map<String, Map> map = StatusControladorFisico.ultimoStatusDosControladores(controladores);
        return CompletableFuture.completedFuture(ok(Json.toJson(map)));
    }

    public CompletionStage<Result> ultimoStatus(String id) {
        StatusControladorFisico statusControladorFisico = StatusControladorFisico.ultimoStatus(id);
        if (statusControladorFisico == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(statusControladorFisico)));
        }

    }

    public CompletionStage<Result> historico(String id, String pagina, String tamanho) {
        List<StatusControladorFisico> statusControladorFisico = StatusControladorFisico.historico(id, Integer.valueOf(pagina),
            Integer.valueOf(tamanho));
        if (statusControladorFisico == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(statusControladorFisico)));
        }

    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }

}
