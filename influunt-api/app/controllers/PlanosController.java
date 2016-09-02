package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
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
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class PlanosController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(json);
            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, PlanosCheck.class);

            if (erros.isEmpty()) {
                controlador.update();
                Controlador controlador1 = Controlador.find.byId(controlador.getId());
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador1)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

    @Transactional
    public CompletionStage<Result> timeline(String id) {
        Anel anel = Anel.find.byId(UUID.fromString(id));

        if (anel == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<VersaoPlano> versoes = anel.getVersoesPlanos();
            return CompletableFuture.completedFuture(ok(Json.toJson(versoes)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Plano plano = Plano.find.byId(UUID.fromString(id));
        if (plano == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            if (plano.delete()) {
                return CompletableFuture.completedFuture(ok());
            } else {
                return CompletableFuture.completedFuture(badRequest());
            }
        }
    }

    @Transactional
    public CompletionStage<Result> cancelarEdicao(String id) {
        Plano plano = Plano.find.fetch("versaoPlano").fetch("versaoPlano.versaoAnterior").where().eq("id", id).findUnique();
        if (plano == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        boolean success = DBUtils.executeWithTransaction(() -> {
            plano.getAnel().getControlador().getAneis().forEach(anel -> {
                VersaoPlano versaoAtual = anel.getVersaoPlanoEmEdicao();
                if (versaoAtual != null && versaoAtual.getStatusVersao() == StatusVersao.EDITANDO) {
                    VersaoPlano versaoAnterior = versaoAtual.getVersaoAnterior();
                    if (versaoAnterior != null) {
                        versaoAnterior.setStatusVersao(StatusVersao.ATIVO);
                        versaoAnterior.save();
                        versaoAtual.delete();
                    }
                }
            });
        });

        if (success) {
            return CompletableFuture.completedFuture(ok());
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY));
    }
}
