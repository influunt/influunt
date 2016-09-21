package controllers.monitoramento;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Controlador;
import models.StatusDevice;
import org.jetbrains.annotations.NotNull;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.StatusConexaoControlador;
import status.StatusControladorFisico;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/16/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class MonitoramentoController extends Controller {


    public CompletionStage<Result> ultimoStatusDosControladores() {
        HashMap<String, StatusDevice> status = StatusControladorFisico.ultimoStatusDosControladores();
        HashMap<String, Boolean> onlines = StatusConexaoControlador.ultimoStatusDosControladores();

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("status", Json.toJson(status));
        retorno.set("onlines", Json.toJson(onlines));

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

    @NotNull
    private ObjectNode controladoresToJson(HashMap<String, Object> controladoresStauts) {
        List<Controlador> controladores = Controlador.find.select("id, nomeEndereco").fetch("area", "descricao").fetch("subArea", "numero").where().in("id", controladoresStauts.keySet()).findList();
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        controladores.forEach(controlador -> {
            LinkedHashMap status = ((LinkedHashMap) controladoresStauts.get(controlador.getId().toString()));
            Long timestamp = 0L;
            if (status != null) {
                timestamp = (Long) status.get("timestamp");
            }
            itens.addObject().put("id", controlador.getId().toString()).put("clc", controlador.getCLC()).put("endereco", controlador.getNomeEndereco()).put("data", timestamp);
        });
        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.putArray("data").addAll(itens);
        return retorno;
    }

}