package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import services.ControladorService;
import utils.InfluuntQueryBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static models.VersaoControlador.usuarioPodeEditarControlador;

@Security.Authenticated(Secured.class)
public class ControladoresController extends Controller {

    @Inject
    private ControladorService controladorService;

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(bodyArea)")
    public CompletionStage<Result> dadosBasicos() {
        return doStep(false, javax.validation.groups.Default.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> aneis() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> gruposSemaforicos() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> transicoesProibidas() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> atrasoDeGrupo() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> entreVerdes() {
        return doStep(false, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoDetectores() {
        return doStep(true, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> findOne(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
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
                Controlador controladorEdicao = controladorService.criarCloneControlador(controlador, getUsuario());
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controladorEdicao)));
            }

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarPlanos(String id) {

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

                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("clonar", "não é possível editar planos", "")))));
            }

            if (controlador.getStatusControlador().equals(StatusControlador.EM_EDICAO) && !usuarioPodeEditarControlador(controlador, getUsuario())) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
            }

            if (controlador.getStatusControlador() == StatusControlador.ATIVO) {
                controladorService.criarClonePlanos(controlador, getUsuario());
                controlador.refresh();
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
            }

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarTabelaHoraria(String id) {

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

                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("clonar", "não é possível editar planos", "")))));
            }

            if (controlador.getStatusControlador().equals(StatusControlador.EM_EDICAO) && !usuarioPodeEditarControlador(controlador, getUsuario())) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Arrays.asList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
            }

            if (controlador.getStatusControlador() == StatusControlador.ATIVO) {
                controladorService.criarCloneTabelaHoraria(controlador, getUsuario());
                controlador.refresh();
                return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
            }

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        if (u.isRoot()) {
            return CompletableFuture.completedFuture(ok(new InfluuntQueryBuilder(Controlador.class, request().queryString()).fetch(Arrays.asList("versaoControlador", "modelo")).query()));
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            Map<String, String[]> params = new HashMap<>();
            params.putAll(ctx().request().queryString());
            if(params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            return CompletableFuture.completedFuture(ok(new InfluuntQueryBuilder(Controlador.class, params).fetch(Arrays.asList("area", "versaoControlador", "modelo")).query()));
        }
        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresForMapa() {
        Usuario u = getUsuario();
        List<ControladorFisico> controladoresFisicos = null;
        if (u.isRoot()) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").findList();
        } else if (u.getArea() != null) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").where().eq("area_id", u.getArea().getId()).findList();
        }

        if (controladoresFisicos != null) {
            List<Controlador> controladores = new ArrayList<Controlador>();
            controladoresFisicos.stream().forEach(controladorFisico -> controladores.add(controladorFisico.getControladorAtivoOuEditando()));
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresForMapas(controladores)));
        }

        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
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
    @Dynamic(value = "ControladorAreaAuth(path)")
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
    @Dynamic(value = "ControladorAreaAuth(path)")
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
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> ativar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.ativar();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> cancelarEdicao(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controladorService.cancelar(controlador);
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> atualizarDescricao(String id) {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Controlador controlador = Controlador.find.byId(UUID.fromString(id));
            if (controlador == null) {
                return CompletableFuture.completedFuture(notFound());
            } else {
                VersaoControlador versaoControlador = controlador.getVersaoControlador();
                if (versaoControlador != null) {
                    versaoControlador.setDescricao(json.get("descricao").asText());
                    versaoControlador.update();
                }
                return CompletableFuture.completedFuture(ok());
            }
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
                    // Criar a prmieira versao e o controlador fisico
                    ControladorFisico controladorFisico = new ControladorFisico();
                    VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
                    controladorFisico.addVersaoControlador(versaoControlador);
                    controladorFisico.setArea(controlador.getArea());
                    controlador.save();
                    controladorFisico.save();
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
