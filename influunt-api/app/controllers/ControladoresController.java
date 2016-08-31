package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import helpers.ControladorUtil;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import play.Application;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static models.VersaoControlador.usuarioPodeEditarControlador;

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
    public CompletionStage<Result> edit(String id) {
        if (getUsuario() == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {

            if (controlador.getStatusControlador() != StatusControlador.ATIVO
                    && controlador.getStatusControlador() != StatusControlador.EM_CONFIGURACAO
                    && controlador.getStatusControlador() != StatusControlador.EM_EDICAO) {

                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("clonar", "não é possível editar controlador", "")))));
            }

            if (controlador.getStatusControlador().equals(StatusControlador.EM_EDICAO) && !usuarioPodeEditarControlador(controlador, getUsuario())) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("editar", "usuário diferente do que está editando controlador!", "")))));
            }

            if (controlador.getStatusControlador() == StatusControlador.ATIVO) {
                Controlador controladorEdicao = new ControladorUtil().provider(provider).deepClone(controlador);
                VersaoControlador novaVersao = new VersaoControlador(controlador, controladorEdicao, getUsuario());
                novaVersao.setDescricao("Controlador clonado pelo usuário: " + getUsuario().getNome());
                novaVersao.save();

                controlador.setStatusControlador(StatusControlador.CLONADO);
                controlador.save();

                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controladorEdicao)));
            }

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresJson(Controlador.find.where().ne("status_controlador", StatusControlador.CLONADO).findList())));
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

    @Transactional
    public CompletionStage<Result> timeline(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<VersaoControlador> versoes = VersaoControlador.versoes(controlador);
            return CompletableFuture.completedFuture(ok(Json.toJson(versoes)));
        }
    }

    @Transactional
    public CompletionStage<Result> podeEditar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            VersaoControlador versaoControlador = VersaoControlador.findByControlador(controlador);
            if (versaoControlador != null && getUsuario().equals(versaoControlador.getUsuario())) {
                return CompletableFuture.completedFuture(ok());
            } else {
                return CompletableFuture.completedFuture(forbidden(Json.toJson(
                        Arrays.asList(new Erro("controlador", "Controlador em edição com o usuário: " + versaoControlador.getUsuario().getNome() + "", "")))));
            }
        }
    }

    @Transactional
    public CompletionStage<Result> ativar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.setStatusControlador(StatusControlador.ATIVO);
            controlador.save();
            return CompletableFuture.completedFuture(ok());
        }
    }

    private CompletionStage<Result> doStep(boolean finalizaConfiguracaoSeSucesso, Class<?>... validationGroups) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }

        if (getUsuario() == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("criar", "usuário não econtrado", "")))));
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
                    VersaoControlador versaoControlador = new VersaoControlador(null, controlador, getUsuario());
                    versaoControlador.setDescricao("Controlador criado pelo usuário: " + getUsuario().getNome());
                    controlador.save();
                    versaoControlador.save();
                }
                Controlador controlador1 = Controlador.find.byId(controlador.getId());

                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador1)));
            }
        }
    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }

}
