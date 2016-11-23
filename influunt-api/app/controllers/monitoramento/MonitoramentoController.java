package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.jetbrains.annotations.NotNull;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.AlarmesFalhasControlador;
import status.StatusConexaoControlador;
import status.StatusControladorFisico;
import status.TrocaDePlanoControlador;

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


    public CompletionStage<Result> ultimoStatusDosControladores() {

        Map<String, String[]> params = ctx().request().queryString();
        Integer limiteQueryFalhas = params.containsKey("limite_alarmes_falhas") ? Integer.parseInt(params.get("limite_alarmes_falhas")[0]) : null;

        HashMap<String, StatusDevice> status = StatusControladorFisico.ultimoStatusDosControladores();
        HashMap<String, Boolean> onlines = StatusConexaoControlador.ultimoStatusDosControladores();
        List<Map> erros = AlarmesFalhasControlador.ultimosAlarmesFalhasControladores(limiteQueryFalhas);
        HashMap<String, ModoOperacaoPlano> modosOperacoes = TrocaDePlanoControlador.ultimoModoOperacaoDosControladores();
        HashMap<String, Boolean> imposicaoPlanos = TrocaDePlanoControlador.ultimoStatusPlanoImposto();

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("status", Json.toJson(status));
        retorno.set("onlines", Json.toJson(onlines));
        retorno.set("erros", errosToJson(erros));
        retorno.set("modosOperacoes", Json.toJson(modosOperacoes));
        retorno.set("imposicaoPlanos", Json.toJson(imposicaoPlanos));

        return CompletableFuture.completedFuture(ok(Json.toJson(retorno)));
    }

    public CompletionStage<Result> controladoresOnline() {
        HashMap<String, Object> onlines = StatusConexaoControlador.ultimoStatusDosControladoresOnlines();
        return CompletableFuture.completedFuture(ok(Json.toJson(controladoresToJson(onlines))));
    }

    public CompletionStage<Result> controladoresOffline() {
        HashMap<String, Object> offlines = StatusConexaoControlador.ultimoStatusDosControladoresOfflines();
        return CompletableFuture.completedFuture(ok(Json.toJson(controladoresToJson(offlines))));
    }

    public CompletableFuture<Result> detalheControlador(String id) {
        Controlador controlador = Controlador.find.select("id, nomeEndereco").fetch("area", "descricao").fetch("subArea", "numero").where().eq("id", id).findUnique();
        List<StatusConexaoControlador> status = StatusConexaoControlador.findByIdControlador(id);
        Collections.reverse(status);
        Integer totalOnline = StatusConexaoControlador.tempoOnline(status);
        Integer totalOffline = StatusConexaoControlador.tempoOffline(status);

        Long percentual = (totalOnline != 0) ? (totalOnline * 100) / (totalOnline + totalOffline) * 1L : 0L;
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.put("percentualOnline", percentual).put("clc", controlador.getCLC()).put("endereco", controlador.getNomeEndereco())
            .put("totalOnline", totalOnline).put("totalOffline", totalOffline).putPOJO("historico", status);
        return CompletableFuture.completedFuture(ok(Json.toJson(retorno)));
    }

    private ArrayNode errosToJson(List<Map> erros) {
        List<String> ids =  erros.stream().map(erro -> erro.get("idControlador").toString()).distinct().collect(Collectors.toList());
        List<Controlador> controladores = Controlador.find.select("id, nomeEndereco").fetch("aneis.endereco").fetch("area", "descricao").fetch("subArea", "numero").fetch("endereco").where().in("id", ids).findList();
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();

        erros.forEach(erro -> {
            String idControlador = erro.get("idControlador").toString();
            Controlador controlador;
            Anel anel = null;
            controlador = controladores.stream().filter(c -> Objects.equals(String.valueOf(c.getId()), idControlador)).collect(Collectors.toList()).get(0);
            if (erro.get("idAnel") != null) {
                String idAnel = erro.get("idAnel").toString();
                anel = controlador.getAneis().stream().filter(a -> (a.isAtivo() && a.getId().toString().equals(idAnel))).findFirst().orElse(null);
            }

            Endereco endereco = (anel != null) ? anel.getEndereco() : controlador.getEndereco();
            itens.addObject()
                .put("idControlador", controlador.getId().toString())
                .put("idAnel", anel != null ? anel.getId().toString() : null)
                .put("clc", controlador.getCLC())
                .put("cla", anel != null ? anel.getCLA() : null)
                .putPOJO("endereco", Json.toJson(endereco))
                .put("data", Long.parseLong(erro.get("timestamp").toString()))
                .put("motivoFalha", ((HashMap)erro.get("conteudo")).get("descricaoEvento").toString());
        });

        return itens;
    }

    @NotNull
    private ObjectNode controladoresToJson(HashMap<String, Object> controladoresStatus) {
        List<Controlador> controladores = Controlador.find.select("id, nomeEndereco").fetch("area", "descricao").fetch("subArea", "numero").where().in("id", controladoresStatus.keySet()).findList();
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
            itens.addObject().put("id", controlador.getId().toString()).put("clc", controlador.getCLC()).put("endereco", controlador.getNomeEndereco()).put("data", timestamp).put("motivoFalha", motivoFalhaControlador);
        });
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.putArray("data").addAll(itens);
        return retorno;
    }

}
