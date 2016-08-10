package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Area;
import models.Controlador;
import models.TabelaHorario;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class TabelaHorariosController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(request().body().asJson());
            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, TabelaHorariosCheck.class);

            if (erros.isEmpty()) {
                controlador.update();
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(Controlador.find.byId(controlador.getId()))));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }
}