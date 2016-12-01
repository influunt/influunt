package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.DisparoAlarme;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import services.FalhasEAlertasService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class AlarmesEFalhasController extends Controller {

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> findAll() {
        ObjectNode response = Json.newObject();
        response.set("falhas", Json.toJson(FalhasEAlertasService.getFalhas()));
        response.set("alarmes", Json.toJson(FalhasEAlertasService.getAlarmes()));

        return CompletableFuture.completedFuture(ok(response));
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> create(String usuarioId) {
        JsonNode json = request().body().asJson();
        Usuario usuario = Usuario.find.byId(UUID.fromString(usuarioId));

        if (usuario == null) {
            return CompletableFuture.completedFuture(forbidden());
        }

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        usuario.getDisparoAlarmes().forEach(Model::delete);
        json.fields().forEachRemaining(field -> {
            if (field.getValue().asBoolean()) {
                DisparoAlarme disparoAlarme = new DisparoAlarme();
                disparoAlarme.setUsuario(usuario);
                disparoAlarme.setChave(field.getKey());
                disparoAlarme.save();
            }
        });

        usuario.refresh();
        return CompletableFuture.completedFuture(ok(Json.toJson(usuario.getDisparoAlarmes())));
    }

    @Transactional
    public CompletionStage<Result> getAlarmesEFalhasDoUsuario(String usuarioId) {
        Usuario usuario = Usuario.find.byId(UUID.fromString(usuarioId));

        if (usuario == null) {
            return CompletableFuture.completedFuture(forbidden());
        }

        return CompletableFuture.completedFuture(ok(Json.toJson(usuario.getDisparoAlarmes())));
    }


}
