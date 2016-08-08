package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import com.google.inject.Provider;
import helpers.ControladorUtil;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusControlador;
import play.Application;
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
    Provider<Application> provider;


    @Transactional
    public CompletionStage<Result> dadosBasicos() {
        return doStep(false, javax.validation.groups.Default.class);
    }

    @Transactional
    public CompletionStage<Result> aneis() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    public CompletionStage<Result> gruposSemaforicos() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class);
    }

    @Transactional
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    public CompletionStage<Result> transicoesProibidas() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class);
    }

    @Transactional
    public CompletionStage<Result> atrasoDeGrupo() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class);
    }

    @Transactional
    public CompletionStage<Result> entreVerdes() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

    @Transactional
    public CompletionStage<Result> associacaoDetectores() {
        return doStep(true, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }


    @Transactional
    public CompletionStage<Result> copiar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        Controlador controladorClone = new ControladorUtil().provider(provider).deepClone(controlador);
        if (controladorClone == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            Ebean.update(controladorClone);
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controladorClone)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresJson(Controlador.find.findList())));
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

    private CompletionStage<Result> doStep(boolean finalizaConfiguracaoSeSucesso, Class<?>... validationGroups) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(request().body().asJson());

        boolean checkIfExists = controlador.getId() != null;
        if (checkIfExists && Controlador.find.byId(controlador.getId()) == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, validationGroups);
            if (erros.size() > 0) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            } else {
                if (checkIfExists) {
                    if (finalizaConfiguracaoSeSucesso) {
                        controlador.setStatusControlador(StatusControlador.CONFIGURADO);
                    }
                    controlador.update();
                } else {
                    controlador.save();
                }
                Controlador controlador1 = Controlador.find.byId(controlador.getId());

                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador1)));
            }
        }
    }
}
