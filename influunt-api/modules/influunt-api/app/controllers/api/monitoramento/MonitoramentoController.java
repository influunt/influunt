package controllers.api.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.jongo.MongoCursor;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.*;
import utils.InfluuntStatusControllers;

import javax.persistence.Id;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 9/16/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class MonitoramentoController extends Controller {


    public CompletionStage<Result> ultimoStatusDosControladores() throws IOException {
        Map<String, String[]> params = ctx().request().queryString();
        Integer limiteQueryFalhas = params.containsKey("limite_alarmes_falhas") ? Integer.parseInt(params.get("limite_alarmes_falhas")[0]) : null;
        Long inicioIntervalo = params.containsKey("inicio_intervalo") ? Long.parseLong(params.get("inicio_intervalo")[0]) : null;
        Long fimIntervalo = params.containsKey("fim_intervalo") ? Long.parseLong(params.get("fim_intervalo")[0]) : null;

        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não encontrado", "")))));
        }

        StatusAtualControlador statusAtualControlador = new StatusAtualControlador();
        if(statusAtualControlador.count() == 0){
            InfluuntStatusControllers influuntStatusControllers = new InfluuntStatusControllers();
        }

//        List<ControladorFisico> todosControladores = ControladorFisico.getControladoresPorUsuario(usuario);
        List<ControladorFisico> controladoresSincronizados = ControladorFisico.getControladoresSincronizadosPorUsuario(usuario);
        List<String> controladoresIds = controladoresSincronizados.stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());

        Map<String, Map> status = StatusControladorFisico.ultimoStatusDosControladores(controladoresIds);
        Map<String, Boolean> onlines = StatusConexaoControlador.ultimoStatusDosControladores(controladoresIds);
        List<AlarmesFalhasControlador> erros = AlarmesFalhasControlador.ultimosAlarmesFalhasControladores(limiteQueryFalhas, null, controladoresIds, inicioIntervalo, fimIntervalo);
        Map<String, Map> modosOperacoes = TrocaDePlanoControlador.ultimoModoOperacaoDosControladoresPorAneis(controladoresIds);
        HashMap<String, Boolean> imposicaoPlanos = TrocaDePlanoControlador.ultimoStatusPlanoImposto(controladoresIds);

        long start = System.currentTimeMillis();
        Map<String, Map<String, Float>> statusTodosControladoresLogicos = getStatusTodosControladores();
        long elapsed = System.currentTimeMillis() - start;

//        long start_quant = System.currentTimeMillis();
//        HashMap<String, Integer> quantidadeDeAneisPorControlador = getQuantidadeDeAneisPorControlador(todosControladores);
//        long elapsed_quant = System.currentTimeMillis() - start_quant;

//        long start = System.currentTimeMillis();
//        HashMap<String, String> statusControladoresLogicos = getStatusControladoresLogicos(todosControladores);
//        long elapsed = System.currentTimeMillis() - start;

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("status", Json.toJson(status));
        retorno.set("onlines", Json.toJson(onlines));
        retorno.set("erros", errosToJson(erros));
        retorno.set("modosOperacoes", Json.toJson(modosOperacoes));
        retorno.set("imposicaoPlanos", Json.toJson(imposicaoPlanos));
//        retorno.set("aneisPorControlador", Json.toJson(quantidadeDeAneisPorControlador));
//        retorno.set("statusControladoresLogicos", Json.toJson(statusControladoresLogicos));
//        retorno.set("tempoBuscarQuantidadeAneis", Json.toJson(elapsed_quant));
        retorno.set("statusControladoresLogicos", Json.toJson(statusTodosControladoresLogicos));
        retorno.set("tempoBuscarStatusControladoresLogicos", Json.toJson(elapsed));

        return CompletableFuture.completedFuture(ok(retorno));
    }

    public CompletionStage<Result> ultimoStatusDosAneis() {
        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        List<String> controladores = ControladorFisico.getControladoresSincronizadosPorUsuario(usuario).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());

        Map<String, Map> status = StatusControladorFisico.ultimoStatusDosControladores(controladores);
        Map<String, Boolean> onlines = StatusConexaoControlador.ultimoStatusDosControladores(controladores);
        List<AlarmesFalhasControlador> erros = AlarmesFalhasControlador.ultimosAlarmesFalhasControladores(null, null, controladores, null, null);

        List<HashMap> statusPlanosPorAnel = TrocaDePlanoControlador.ultimoStatusPlanoPorAnel(controladores);
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("status", Json.toJson(status));
        retorno.set("onlines", Json.toJson(onlines));
        retorno.set("erros", errosToJson(erros));
        retorno.set("statusPlanos", Json.toJson(statusPlanosPorAnel));

        return CompletableFuture.completedFuture(ok(Json.toJson(retorno)));
    }

    public CompletionStage<Result> controladoresOnline() {
        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        List<String> controladores = ControladorFisico.getControladoresSincronizadosPorUsuario(usuario).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());
        Map<String, Object> onlines = StatusConexaoControlador.ultimoStatusDosControladoresOnlines(controladores);
        return CompletableFuture.completedFuture(ok(Json.toJson(controladoresToJson(onlines))));
    }

    public CompletionStage<Result> controladoresOffline() {
        Usuario usuario = getUsuario();

        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        List<String> controladores = ControladorFisico.getControladoresSincronizadosPorUsuario(usuario).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());

        Map<String, Object> offlines = StatusConexaoControlador.ultimoStatusDosControladoresOfflines(controladores);
        return CompletableFuture.completedFuture(ok(Json.toJson(controladoresToJson(offlines))));
    }

    public CompletableFuture<Result> detalheControlador(String id) {
        ControladorFisico controladorFisico = ControladorFisico.find.fetch("controladorSincronizado.area", "descricao").fetch("controladorSincronizado.subarea", "numero").where().eq("id", id).findUnique();
        Controlador controlador = controladorFisico.getControladorSincronizado();
        List<StatusConexaoControlador> status = StatusConexaoControlador.findByIdControladorUltimos30Dias(id);
        Integer totalOnline = StatusConexaoControlador.tempoOnlineOffline(status, true);
        Integer totalOffline = StatusConexaoControlador.tempoOnlineOffline(status, false);

        Long percentual = (totalOnline != 0) ? (totalOnline * 100) / (totalOnline + totalOffline) * 1L : 0L;
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.put("percentualOnline", percentual).put("clc", controlador.getCLC()).put("endereco", controlador.getNomeEndereco())
            .put("totalOnline", totalOnline).put("totalOffline", totalOffline).putPOJO("historico", status);
        return CompletableFuture.completedFuture(ok(Json.toJson(retorno)));
    }

    private HashMap<String, Integer> getQuantidadeDeAneisPorControlador(List<ControladorFisico> controladoresFisicos) {
        HashMap<String, Integer> aneisPorControlador = new HashMap<>();

        controladoresFisicos.stream().forEach(controladorFisico -> {
            aneisPorControlador.put(
                controladorFisico.getId().toString(),
                controladorFisico.getVersaoAtualControlador().getAneisAtivos().size()
            );
        });

        return aneisPorControlador;
    }

    private HashMap<String, String> getStatusControladoresLogicos(List<ControladorFisico> controladoresFisicos) {
        HashMap<String, String> controladores = new HashMap<>();

        controladoresFisicos.stream().forEach(controladorFisico -> {
            controladores.put(
                controladorFisico.getId().toString(),
                controladorFisico.getVersaoAtualControlador().getStatusControladorReal().toString()
            );
        });

        return controladores;
    }

    private static class StatusTodosControladores {
        public Map<String, Map<String, Float>> status;
    }

    private Map getStatusTodosControladores() throws IOException {

        Map<String, Map<String, Float>> status = null;

        StatusAtualControlador statusAtualControlador = new StatusAtualControlador();
        MongoCursor<StatusTodosControladores> statusTodosControladoresObj = statusAtualControlador.find().as(StatusTodosControladores.class);
        while(statusTodosControladoresObj.hasNext()){
            status = statusTodosControladoresObj.next().status;
        }
        statusTodosControladoresObj.close();
        return status;
    }

    private ArrayNode errosToJson(List<AlarmesFalhasControlador> erros) {
        List<String> ids = erros.stream().map(erro -> erro.getIdControlador()).distinct().collect(Collectors.toList());
        List<ControladorFisico> controladores = ControladorFisico.find
            .fetch("controladorSincronizado")
            .fetch("controladorSincronizado.aneis.endereco")
            .fetch("controladorSincronizado.aneis")
            .fetch("controladorSincronizado.area", "descricao")
            .fetch("controladorSincronizado.subarea", "numero")
            .fetch("controladorSincronizado.endereco")
            .where()
            .in("id", ids)
            .findList();
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();


        erros.forEach(erro -> {
            String idControlador = erro.getIdControlador();
            Controlador controlador;
            Anel anel = null;
            controlador = controladores.stream().filter(c -> Objects.equals(String.valueOf(c.getId()), idControlador)).map(ControladorFisico::getControladorSincronizado).findFirst().orElse(null);
            if (controlador != null) {
                if (erro.getIdAnel() != null) {
                    String idAnel = erro.getIdAnel();
                    anel = controlador.getAneis().stream().filter(a -> a.isAtivo() && a.getId().toString().equals(idAnel)).findFirst().orElse(null);
                }

                Endereco endereco = (anel != null) ? anel.getEndereco() : controlador.getEndereco();
                itens.addObject()
                    .put("idControlador", controlador.getId().toString())
                    .put("idAnel", anel != null ? anel.getId().toString() : null)
                    .put("clc", controlador.getCLC())
                    .put("cla", anel != null ? anel.getCLA() : null)
                    .putPOJO("endereco", Json.toJson(endereco))
                    .put("data", Long.parseLong(erro.getTimestamp().toString()))
                    .put("descricaoEvento", erro.getConteudo().get("descricaoEvento").asText())
                    .put("tipo", erro.getConteudo().get("tipoEvento").get("tipo").asText())
                    .put("tipoEventoControlador", erro.getConteudo().get("tipoEvento").get("tipoEventoControlador").asText());
            }
        });

        return itens;
    }

    @NotNull
    private ObjectNode controladoresToJson(Map<String, Object> controladoresStatus) {
        List<ControladorFisico> controladores = ControladorFisico.find.fetch("controladorSincronizado.area", "descricao").fetch("controladorSincronizado.subarea", "numero").where().in("id", controladoresStatus.keySet()).findList();
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        controladores.forEach(controlador -> {
            LinkedHashMap status = ((LinkedHashMap) controladoresStatus.get(controlador.getId().toString()));
            Long timestamp = 0L;
            String motivoFalhaControlador = "";
            if (status != null) {
                timestamp = (Long) status.get("timestamp");
                if (status.get("motivoFalhaControlador") != null) {
                    motivoFalhaControlador = status.get("motivoFalhaControlador").toString();
                }
            }
            itens.addObject().put("id", controlador.getId().toString()).put("clc", controlador.getControladorSincronizado().getCLC()).put("endereco", controlador.getControladorSincronizado().getNomeEndereco()).put("data", timestamp).put("motivoFalha", motivoFalhaControlador);
        });
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.putArray("data").addAll(itens);
        return retorno;
    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }

}
