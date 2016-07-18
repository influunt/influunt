package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.google.inject.Inject;
import models.Controlador;
import play.data.FormFactory;
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

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ControladoresController extends Controller {


    @Inject
    FormFactory formFactory;

    @Transactional
    public CompletionStage<Result> dadosBasicos() {
        return doStep(javax.validation.groups.Default.class);
    }

    @Transactional
    public CompletionStage<Result> aneis() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    public CompletionStage<Result> transicoesProibidas() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);
    }

    @Transactional
    public CompletionStage<Result> entreVerdes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

    @Transactional
    public CompletionStage<Result> associacaoDetectores() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Controlador.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    private CompletionStage<Result> doStep(Class<?>... validationGroups) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }

        Controlador controlador = Json.fromJson(request().body().asJson(), Controlador.class);

        boolean checkIfExists = controlador.getId() != null;
        if (checkIfExists && Controlador.find.byId(controlador.getId()) == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, validationGroups);
            if (erros.size() > 0) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            } else {
                if (checkIfExists) {
                    controlador.update();
                } else {
                    controlador.save();
                }
                return CompletableFuture.completedFuture(ok(Json.toJson(Controlador.find.byId(controlador.getId()))));
            }
        }
    }


}
