package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
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

@SuppressWarnings({"unchecked"})
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

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  transação ID -> controladores IDd
        Map<String, List<String>> ids = enviarPacotesPlanos(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> configuracaoCompleta() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  controlador ID -> transação ID
        Map<String, List<String>> ids = enviarConfiguracoesCompletas(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> tabelaHoraria() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        boolean imediato = json.get("imediato").asBoolean();
        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  controlador ID -> transação ID
        Map<String, List<String>> ids = enviarTabelaHoraria(Json.fromJson(json.get("aneisIds"), List.class), imediato, timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> modoOperacao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ModoOperacaoPlano modoOperacao = ModoOperacaoPlano.valueOf(json.get("modoOperacao").asText());
        int duracao = json.get("duracao").asInt();
        long horarioEntrada = json.get("horarioEntrada").asLong();
        List<String> aneisIds = Json.fromJson(json.get("aneisIds"), List.class);
        long timeout = json.get("timeout").asInt() * 1000L;

        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = imporModoOperacao(aneisIds, modoOperacao, duracao, horarioEntrada, timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> plano() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        int posicaoPlano = json.get("posicaoPlano").asInt();
        int duracao = json.get("duracao").asInt();
        Long horarioEntrada = json.get("horarioEntrada").asLong();
        List<String> aneisIds = Json.fromJson(json.get("aneisIds"), List.class);
        long timeout = json.get("timeout").asInt() * 1000L;

        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = imporPlano(aneisIds, posicaoPlano, duracao, horarioEntrada, timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> liberar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = liberarImposicao(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> colocarManutencao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = colocarControladorManutencao(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> inativar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = inativarControlador(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    @Transactional
    public CompletionStage<Result> ativar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        Map<String, List<String>> ids = ativarControlador(Json.fromJson(json.get("aneisIds"), List.class), timeout);
        return CompletableFuture.completedFuture(ok(Json.toJson(ids)));
    }

    private Map<String, List<String>> enviarPacotesPlanos(List<String> aneis, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.enviarPacotePlanos(controladores, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> enviarConfiguracoesCompletas(List<String> aneis, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.enviarConfiguracaoCompleta(controladores, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> enviarTabelaHoraria(List<String> aneis, boolean imediato, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.enviarTabelaHoraria(controladores, imediato, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> imporModoOperacao(List<String> aneisIds, ModoOperacaoPlano modoOperacao, int duracao, Long horarioEntrada, Long timeout) {
        List<Anel> aneis = Anel.find.fetch("controlador").where().in("id", aneisIds).findList();
        String transacaoId = transacaoHelper.imporModoOperacao(aneis, modoOperacao, horarioEntrada, duracao, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, aneis.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> imporPlano(List<String> aneisIds, int posicaoPlano, int duracao, Long horarioEntrada, Long timeout) {
        List<Anel> aneis = Anel.find.fetch("controlador").fetch("controlador.modelo").where().in("id", aneisIds).findList();
        String transacaoId = transacaoHelper.imporPlano(aneis, posicaoPlano, horarioEntrada, duracao, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, aneis.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> liberarImposicao(List<String> aneisIds, Long timeout) {
        List<Anel> aneis = Anel.find.fetch("controlador").fetch("controlador.modelo").where().in("id", aneisIds).findList();
        String transacaoId = transacaoHelper.liberarImposicao(aneis, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, aneis.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));
        return ids;
    }


    private Map<String, List<String>> colocarControladorManutencao(List<String> aneis, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.colocarControladorManutencao(controladores, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> inativarControlador(List<String> aneis, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.inativarControlador(controladores, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }

    private Map<String, List<String>> ativarControlador(List<String> aneis, Long timeout) {
        List<Controlador> controladores = getControladores(aneis);
        String transacaoId = transacaoHelper.ativarControlador(controladores, timeout);

        Map<String, List<String>> ids = new HashMap<>();
        ids.put(transacaoId, controladores.stream().map(c -> c.getId().toString()).collect(Collectors.toList()));
        return ids;
    }


    private List<Controlador> getControladores(List<String> aneis) {
        return aneis.stream()
            .map(anelId -> Anel.find.byId(UUID.fromString(anelId)))
            .map(Anel::getControlador)
            .distinct()
            .collect(Collectors.toList());
    }
}
