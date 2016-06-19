package controllers;

import checks.ControladorAneisCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.ControladorVerdesConflitantesCheck;
import checks.InfluuntValidator;
import com.google.inject.Inject;
import models.Controlador;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ControladoresController extends Controller {


    @Inject
    FormFactory formFactory;

    @Transactional
    public CompletionStage<Result> dadosBasicos() {
        return doStep(false, javax.validation.groups.Default.class);
    }

    @Transactional
    public CompletionStage<Result> aneis() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(true, javax.validation.groups.Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class,ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    public CompletionStage<Result> findOne(Long id) {
        Controlador controlador = Controlador.find.byId(id);
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
    public CompletionStage<Result> delete(Long id) {
        Controlador controlador = Controlador.find.byId(id);
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    private CompletionStage<Result> doStep(Boolean THOR, Class<?>... validatiosGroups) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }

        Controlador controlador = Json.fromJson(request().body().asJson(), Controlador.class);
        boolean checkIfExists = controlador.getId() != null;
        if (checkIfExists && Controlador.find.byId(controlador.getId()) == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            //TODO: Remover esse THOR!
            if(THOR){
                controlador.getAneis().stream().filter(anel -> anel.isAtivo())
                        .forEach(anel -> anel.getGruposSemaforicos().stream()
                                .filter(grupoSemaforico -> grupoSemaforico.getEstagioGrupoSemaforicos() != null)
                                .forEach(grupoSemaforico ->
                                {
                                    grupoSemaforico.update();
                                    grupoSemaforico.getEstagioGrupoSemaforicos()
                                            .stream()
                                            .forEach(estagioGrupoSemaforico -> estagioGrupoSemaforico.save());

                                }));
                controlador.refresh();
            }
            List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, validatiosGroups);
            if (erros.size() > 0) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            } else {
                if (checkIfExists) {
                    controlador.update();
                } else {
                    controlador.save();
                }
//                controlador.refresh();
                return CompletableFuture.completedFuture(ok(Json.toJson(Controlador.find.byId(controlador.getId()))));
            }

        }
    }


}
