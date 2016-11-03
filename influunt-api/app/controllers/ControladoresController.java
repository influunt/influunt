package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.apache.commons.lang3.StringUtils;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import services.ControladorService;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

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
        return doStep(javax.validation.groups.Default.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> aneis() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> gruposSemaforicos() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> transicoesProibidas() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> entreVerdes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> atrasoDeGrupo() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAtrasoDeGrupoCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoDetectores() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAtrasoDeGrupoCheck.class, ControladorAssociacaoDetectoresCheck.class);
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
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (StatusVersao.EDITANDO.equals(controlador.getVersaoControlador().getStatusVersao()) && !controlador.podeSerEditadoPorUsuario(usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando controlador!", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "controlador não pode ser clonado", "")))));
        }

        Controlador controladorEdicao = controladorService.criarCloneControlador(controlador, usuario);
        return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controladorEdicao)));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarPlanos(String id) {
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EDITANDO) && !usuarioPodeEditarControlador(controlador, usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "plano não pode ser clonado", "")))));
        }

        if (controladorService.criarClonePlanos(controlador, usuario)) {
            controlador.refresh();
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "erro ao clonar planos", "")))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarTabelaHoraria(String id) {
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.fetch("versaoControlador").where().eq("id", id).findUnique();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EDITANDO) && !usuarioPodeEditarControlador(controlador, usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "tabela horária não pode ser clonada", "")))));
        }

        if (controladorService.criarCloneTabelaHoraria(controlador, getUsuario())) {
            controlador.refresh();
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador)));
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "erro ao clonar tabela horária", "")))));
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, request().queryString()).fetch(Arrays.asList("versaoControlador", "modelo")).query());
            return CompletableFuture.completedFuture(ok(result.toJson()));
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            Map<String, String[]> params = new HashMap<>();
            params.putAll(ctx().request().queryString());
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Arrays.asList("area", "versaoControlador", "modelo")).query());
            return CompletableFuture.completedFuture(ok(result.toJson()));
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
            controladoresFisicos.stream().forEach(controladorFisico -> {
                Controlador controlador = controladorFisico.getControladorConfiguradoOuAtivoOuEditando();
                if (controlador != null) {
                    controladores.add(controlador);
                }
            });
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresForMapas(controladores)));
        }

        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresForAgrupamentos() {
        Usuario u = getUsuario();
        Map<String, String[]> params = new HashMap<>();
        params.putAll(ctx().request().queryString());
        String[] status = {"[CONFIGURADO,ATIVO]"};
        params.put("versaoControlador.statusVersao_in", status);

        if (u.isRoot()) {
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Collections.singletonList("aneis")).query());
            return CompletableFuture.completedFuture(ok(result.toJson("agrupamentos")));
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Arrays.asList("area", "aneis")).query());
            return CompletableFuture.completedFuture(ok(result.toJson("agrupamentos")));
        }
        return CompletableFuture.completedFuture(forbidden());
    }

    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladorForSimulacao(String id) {
        Controlador controlador = Controlador.find.fetch("aneis").fetch("aneis.detectores").fetch("aneis.versoesPlanos").fetch("aneis.versoesPlanos.planos").where().eq("id", id).findUnique();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorSimulacao(controlador)));
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
        }

        if (controlador.podeEditar(getUsuario())) {
            return CompletableFuture.completedFuture(ok());
        }

        return CompletableFuture.completedFuture(forbidden(Json.toJson(
            Collections.singletonList(new Erro("controlador", "Controlador em edição com o usuário: " + controlador.getVersaoControlador().getUsuario().getNome() + "", "")))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> finalizar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            if (request().body().asJson() != null) {
                String descricao = request().body().asJson().get("descricao").asText();
                if (StringUtils.isEmpty(descricao.trim())) {
                    return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY,
                        Json.toJson(Collections.singletonList(new Erro("controlador", "Informe uma descrição para finalizar a configuração", "")))));
                }
                VersaoControlador versaoControlador = controlador.getVersaoControlador();
                if (versaoControlador != null) {
                    versaoControlador.setDescricao(descricao);
                    versaoControlador.update();
                }
            }
            controlador.finalizar();
            return CompletableFuture.completedFuture(ok());
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
        }

        if (controladorService.cancelar(controlador)) {
            return CompletableFuture.completedFuture(ok());
        }
        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY));
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

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> removerPlanosTabelasHorarios(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.removerPlanosTabelasHorarios();
            return CompletableFuture.completedFuture(ok());
        }
    }

    private CompletionStage<Result> doStep(Class<?>... validationGroups) {
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
                    controlador.update();
                } else {
                    // Criar a primeira versão e o controlador físico
                    ControladorFisico controladorFisico = new ControladorFisico();
                    VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
                    versaoControlador.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
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
