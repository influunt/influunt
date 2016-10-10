package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusVersao;
import models.VersaoTabelaHoraria;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.DBUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Security.Authenticated(Secured.class)
public class TabelaHorariosController extends Controller {

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(request().body().asJson());
            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, TabelaHorariosCheck.class);

            if (erros.isEmpty()) {
                controlador.update();
                Controlador controlador1 = Controlador.find.byId(controlador.getId());
                controlador1.getVersoesTabelasHorarias();
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador1)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> timeline(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));

        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<VersaoTabelaHoraria> versoes = controlador.getVersoesTabelasHorarias();
            return CompletableFuture.completedFuture(ok(Json.toJson(versoes)));
        }
    }


    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> cancelarEdicao(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        boolean success = DBUtils.executeWithTransaction(() -> {
            VersaoTabelaHoraria versaoTabelaHoraria = controlador.getVersaoTabelaHorariaEmEdicao();
            if (controlador.getVersoesTabelasHorarias().size() > 1 && versaoTabelaHoraria != null) {
                VersaoTabelaHoraria versaoTabelaHorariaOrigem = versaoTabelaHoraria.getTabelaHorariaOrigem().getVersaoTabelaHoraria();
                versaoTabelaHorariaOrigem.setStatusVersao(StatusVersao.CONFIGURADO);
                versaoTabelaHorariaOrigem.update();
                versaoTabelaHoraria.delete();
            }
        });

        if (success) {
            return CompletableFuture.completedFuture(ok());
        }
        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY));
    }

}
