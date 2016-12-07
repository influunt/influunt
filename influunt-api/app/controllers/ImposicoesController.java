package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.TransacaoHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ImposicoesController extends Controller {

    @Inject
    private TransacaoHelper transacaoHelper;

    @Transactional
    public CompletionStage<Result> pacotePlano() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        // Map  controlador ID -> transação ID
        Map<String, String> transacoesIds = enviarPacotesPlanos(Json.fromJson(json, List.class));
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> configuracaoCompleta() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        // Map  controlador ID -> transação ID
        Map<String, String> transacoesIds = enviarConfiguracoesCompletas(Json.fromJson(json, List.class));
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> tabelaHoraria() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        boolean imediato = json.get("imediato").asBoolean();
        // Map  controlador ID -> transação ID
        Map<String, String> transacoesIds = enviarTabelaHoraria(Json.fromJson(json.get("aneis"), List.class), imediato);
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> modoOperacao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        // Map  anel ID -> transação ID
        Map<String, String> transacoesIds = imporModoOperacao(json);
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> plano() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        // Map  anel ID -> transação ID
        Map<String, String> transacoesIds = imporPlano(json);
        return CompletableFuture.completedFuture(ok(Json.toJson(transacoesIds)));
    }

    @Transactional
    public CompletionStage<Result> liberar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        // Map  anel ID -> transação ID
        Map<String, String> transacaoId = liberarImposicao(json.get("anelId").asText());
        if (transacaoId == null) {
            return CompletableFuture.completedFuture(notFound("Anel não encontrado"));
        }
        return CompletableFuture.completedFuture(ok(Json.toJson(transacaoId)));
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

    private Map<String, String> enviarTabelaHoraria(List<String> aneis, boolean imediato) {
        List<Controlador> controladores = getControladores(aneis);
        Map<String, String> transacoesIds = new HashMap<>();
        controladores.forEach(controlador ->
            transacoesIds.put(controlador.getId().toString(), transacaoHelper.enviarTabelaHoraria(controlador, imediato))
        );
        return transacoesIds;
    }

    private Map<String, String> imporModoOperacao(JsonNode params) {
        ModoOperacaoPlano modoOperacao = ModoOperacaoPlano.valueOf(params.get("modoOperacao").asText());
        int duracao = params.get("duracao").asInt();
        Long horarioEntrada = params.get("horarioEntrada").asLong();
        List<String> aneisIds = Json.fromJson(params.get("aneis"), List.class);
        List<Anel> aneis = Anel.find.fetch("controlador").where().in("id", aneisIds).findList();

        Map<String, String> transacoesIds = new HashMap<>();
        aneis.forEach(anel ->
            transacoesIds.put(anel.getId().toString(), transacaoHelper.imporModoOperacao(anel.getControlador(), modoOperacao, anel.getPosicao(), horarioEntrada, duracao))
        );
        return transacoesIds;
    }

    private Map<String, String> imporPlano(JsonNode params) {
        int posicaoPlano = params.get("posicaoPlano").asInt();
        int duracao = params.get("duracao").asInt();
        Long horarioEntrada = params.get("horarioEntrada").asLong();

        List<String> aneisIds = Json.fromJson(params.get("aneis"), List.class);
        List<Anel> aneis = Anel.find.fetch("controlador").fetch("controlador.modelo").where().in("id", aneisIds).findList();

        Map<String, String> transacoesIds = new HashMap<>();
        aneis.forEach(anel ->
            transacoesIds.put(anel.getId().toString(), transacaoHelper.imporPlano(anel.getControlador(), posicaoPlano, anel.getPosicao(), horarioEntrada, duracao))
        );
        return transacoesIds;
    }

    private Map<String, String> liberarImposicao(String anelId) {
        Anel anel = Anel.find.fetch("controlador").where().eq("id", anelId).findUnique();
        if (anel != null) {
            Map<String, String> transacoesIds = new HashMap<>();
            transacoesIds.put(anel.getId().toString(), transacaoHelper.liberarImposicao(anel.getControlador(), anel.getPosicao()));
            return transacoesIds;
        }
        return null;
    }


    private List<Controlador> getControladores(List<String> aneis) {
        return aneis.stream()
            .map(anelId -> Anel.find.byId(UUID.fromString(anelId)))
            .map(Anel::getControlador)
            .distinct()
            .collect(Collectors.toList());
    }
}
