package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import models.StatusDevice;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.StatusControladorFisico;

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
public class StatusControladorFisicoController {

    public CompletionStage<Result> findOne(String id) {
        List<StatusControladorFisico> status = StatusControladorFisico.findByIdControlador(id);
        if (status == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(status)));
        }
    }

    public CompletionStage<Result> ultimoStatusDosControladores() {
        HashMap<String, StatusDevice> map = StatusControladorFisico.ultimoStatusDosControladores();
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


}
