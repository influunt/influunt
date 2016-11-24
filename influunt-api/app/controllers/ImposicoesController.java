package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.fusesource.mqtt.client.QoS;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.TransacaoHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ImposicoesController extends Controller {

    @Inject
    private TransacaoHelper transacaoHelper;

    @Transactional
    public CompletionStage<Result> plano() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Map<String, String> transacoesIds = enviarPacotesPlanos(Json.fromJson(json, List.class));
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> configuracao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Map<String, String> transacoesIds = enviarConfiguracoesCompletas(Json.fromJson(json, List.class));
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> modoOperacao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Map<String, String> transacoesIds = imporModoOperacao(json);
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }


    private Map<String, String> enviarPacotesPlanos(List<String> aneis) {
        List<Controlador> controladores = getControladores(aneis);
        Map<String, String> transacoesIds = new HashMap<>();
        controladores.forEach(controlador ->
            transacoesIds.put(controlador.getId().toString(), transacaoHelper.enviarPacotePlanos(controlador))
        );
        return transacoesIds;
    }

    private Map<String, String> enviarConfiguracoesCompletas(List<String> aneis) {
        List<Controlador> controladores = getControladores(aneis);
        Map<String, String> transacoesIds = new HashMap<>();
        controladores.forEach(controlador ->
            transacoesIds.put(controlador.getId().toString(), transacaoHelper.enviarConfiguracaoCompleta(controlador))
        );
        return transacoesIds;
    }

    private Map<String, String>  imporModoOperacao(JsonNode params) {
        ModoOperacaoPlano modoOperacao = ModoOperacaoPlano.valueOf(params.get("modoOperacao").asText());
        int duracao = params.get("duracao").asInt();
        List<String> aneisIds = Json.fromJson(params.get("aneis"), List.class);
        List<Anel> aneis = Anel.find.fetch("controlador").where().in("id", aneisIds).findList();

        Map<String, String> transacoesIds = new HashMap<>();
        aneis.forEach(anel ->
            transacoesIds.put(anel.getId().toString(), transacaoHelper.imporModoOperacao(anel.getControlador(), modoOperacao, anel.getPosicao(), duracao))
        );
        return transacoesIds;
    }

    private List<Controlador> getControladores(List<String> aneis) {
        return aneis.stream()
            .map(anelId -> Anel.find.byId(UUID.fromString(anelId)))
            .map(Anel::getControlador)
            .distinct()
            .collect(Collectors.toList());
    }
}
