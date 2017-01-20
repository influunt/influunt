package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import checks.SubareasCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.ControladorCustomSerializer;
import models.*;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.DBUtils;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class SubareasController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Subarea subarea = Json.fromJson(json, Subarea.class);
            List<Erro> erros = new InfluuntValidator<Subarea>().validate(subarea, Default.class, SubareasCheck.class);

            if (erros.isEmpty()) {
                subarea.save();
                subarea.refresh();
                setSubareaForControladores(subarea, json);

                return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Subarea subarea = Subarea.find.fetch("area").fetch("area.cidade").where().eq("id", id).findUnique();
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        InfluuntResultBuilder result;
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Subarea.class, request().queryString()).fetch(Arrays.asList("area", "area.cidade")).query());
        } else {
            String[] areaId = {u.getArea().getId().toString()};
            Map<String, String[]> params = new HashMap<>();
            params.putAll(ctx().request().queryString());
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Subarea.class, params).fetch(Arrays.asList("area", "area.cidade")).query());
        }
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Subarea subarea = Subarea.find.byId(UUID.fromString(id));
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (subarea.getControladores().isEmpty()) {
            subarea.delete();
            return CompletableFuture.completedFuture(ok());
        } else {
            Erro erro = new Erro("Subarea", "Essa subárea não pode ser removida, pois existe(m) controlador(es) vinculado(s) à mesma.", "");
            return CompletableFuture.completedFuture(
                status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(erro)))
            );
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Subarea subareaExistente = Subarea.find.byId(UUID.fromString(id));
        if (subareaExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Subarea subarea = Json.fromJson(json, Subarea.class);
        subarea.setId(subareaExistente.getId());
        List<Erro> erros = new InfluuntValidator<Subarea>().validate(subarea, Default.class, SubareasCheck.class);

        if (erros.isEmpty()) {
            subarea.update();
            subarea.refresh();

            setSubareaForControladores(subarea, json);
            return CompletableFuture.completedFuture(ok(Json.toJson(subarea)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    public CompletionStage<Result> buscarTabelaHoraria(String id) {
        Subarea subarea = Subarea.find.byId(UUID.fromString(id));
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Controlador controlador = Controlador.find
            .fetch("versoesTabelasHorarias")
            .fetch("versoesTabelasHorarias.tabelaHoraria")
            .fetch("versaoControlador")
            .where().eq("subarea_id", id)
            .ne("versaoControlador.statusVersao", StatusVersao.ARQUIVADO.toString())
            .setMaxRows(1).findUnique();
        if (controlador != null) {
            JsonNode tabelaHorariaJson = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);

            tabelaHorariaJson.get("versoesTabelasHorarias").forEach(vth -> {
                ((ObjectNode) vth).remove("id");
            });
            tabelaHorariaJson.get("tabelasHorarias").forEach(th -> {
                ((ObjectNode) th).remove("id");
            });
            tabelaHorariaJson.get("eventos").forEach(e -> {
                ((ObjectNode) e).remove("id");
            });

            return CompletableFuture.completedFuture(ok(Json.toJson(tabelaHorariaJson)));
        }

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> salvarTabelaHoraria(String id) {
        Subarea subarea = Subarea.find.byId(UUID.fromString(id));
        if (subarea == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        List<Erro> erros = new ArrayList<>();

        boolean saved = DBUtils.executeWithTransaction(() -> {
            subarea.getControladores().forEach(c1 -> {
                ControladorFisico controladorFisico = c1.getVersaoControlador().getControladorFisico();
                Controlador controlador = controladorFisico.getControladorConfiguradoOuAtivoOuEditando();
                if (controlador == null) {
                    controlador = controladorFisico.getControladorConfiguradoOuSincronizado();
                }
                if (controlador != null) {
                    TabelaHorario tabelaAntiga = null;
                    VersaoTabelaHoraria versaoAntiga = c1.getVersaoTabelaHoraria();
                    if (versaoAntiga != null) {
                        tabelaAntiga = versaoAntiga.getTabelaHoraria();
                        versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
                        versaoAntiga.update();
                    }

                    JsonNode controladorJson = new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null));
                    Object controladorOuErros = Controlador.checkConfiguracaoTabelaHoraria(controladorJson, json, getUsuario());
                    if (controladorOuErros instanceof Controlador) {
                        Controlador c2 = (Controlador) controladorOuErros;
                        VersaoTabelaHoraria novaVersao = c2.getVersaoTabelaHoraria();
                        if (tabelaAntiga != null) {
                            novaVersao.setTabelaHorariaOrigem(tabelaAntiga);
                        }
                        novaVersao.setStatusVersao(StatusVersao.CONFIGURADO);
                        novaVersao.setIdJson(UUID.randomUUID().toString());
                        novaVersao.getTabelaHoraria().setIdJson(UUID.randomUUID().toString());
                        novaVersao.getTabelaHoraria().getEventos().forEach(e -> e.setIdJson(UUID.randomUUID().toString()));
                        controlador.addVersaoTabelaHoraria(novaVersao);
                        controlador.update();
                    } else {
                        Controlador finalControlador = controlador;
                        ((List<Erro>) controladorOuErros).forEach(erro -> {
                            erro.root = "Controlador (" + finalControlador.getNomeEndereco() + ")";
                            erros.add(erro);
                        });
                    }
                }
            });

            if (!erros.isEmpty()) {
                throw new RuntimeException("Erro de validação: rollback transaction!");
            }
        });

        if (!saved) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
        return CompletableFuture.completedFuture(ok());
    }

    private void setSubareaForControladores(Subarea subarea, JsonNode json) {
        if (json.has("controladoresAssociados") && json.get("controladoresAssociados").isArray()) {
            ArrayNode controladoresJson = (ArrayNode) json.get("controladoresAssociados");
            controladoresJson.forEach(controladorJson -> {
                Controlador controlador = Controlador.find.byId(UUID.fromString(controladorJson.get("id").asText()));
                if (controlador != null) {
                    controlador.setSubarea(subarea);
                    controlador.save();
                }
            });
        }
    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }
}
