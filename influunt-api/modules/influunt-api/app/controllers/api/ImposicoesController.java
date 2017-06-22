package controllers.api;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import helpers.transacao.TransacaoHelperApi;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.InfluuntContextManager;
import security.Secured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ImposicoesController extends Controller {

    @Inject
    private TransacaoHelperApi transacaoHelper;

    @Inject
    private InfluuntContextManager contextManager;


    @Transactional
    public CompletionStage<Result> pacotePlano() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        return enviarPacotesPlanos(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> configuracaoCompleta() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        return enviarConfiguracoesCompletas(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> tabelaHoraria() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        boolean imediato = json.get("imediato").asBoolean();
        long timeout = json.get("timeout").asInt() * 1000L;

        return enviarTabelaHoraria(Json.fromJson(json.get("aneisIds"), List.class), imediato, timeout)
            .thenApply(result -> ok(Json.toJson(result)));
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
        return imporModoOperacao(aneisIds, modoOperacao, duracao, horarioEntrada, timeout)
            .thenApply(result -> ok(Json.toJson(result)));
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
        return imporPlano(aneisIds, posicaoPlano, duracao, horarioEntrada, timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> liberar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        return liberarImposicao(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> colocarManutencao() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        return colocarControladorManutencao(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> inativar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        return inativarControlador(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }

    @Transactional
    public CompletionStage<Result> ativar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        long timeout = json.get("timeout").asInt() * 1000L;
        // Map  anel ID -> transação ID
        return ativarControlador(Json.fromJson(json.get("aneisIds"), List.class), timeout)
            .thenApply(result -> ok(Json.toJson(result)));
    }


    private CompletionStage<Map<String, List<String>>> enviarPacotesPlanos(List<String> aneis, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.enviarPacotePlanos(controladores, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));
    }

    private CompletionStage<Map<String, List<String>>> enviarConfiguracoesCompletas(List<String> aneis, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.enviarConfiguracaoCompleta(controladores, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));
    }

    private CompletionStage<Map<String, List<String>>> enviarTabelaHoraria(List<String> aneis, boolean imediato, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.enviarTabelaHoraria(controladores, imediato, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));

    }

    private CompletionStage<Map<String, List<String>>> imporModoOperacao(List<String> aneisIds, ModoOperacaoPlano modoOperacao, int duracao, Long horarioEntrada, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Anel> aneis = Anel.find.fetch("controlador").where().in("id", aneisIds).findList();
        return transacaoHelper.imporModoOperacao(aneis, modoOperacao, horarioEntrada, duracao, timeout, authToken)
            .thenApply(wsResponseMapFunction(aneis));

    }

    private CompletionStage<Map<String, List<String>>> imporPlano(List<String> aneisIds, int posicaoPlano, int duracao, Long horarioEntrada, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Anel> aneis = Anel.find.fetch("controlador").fetch("controlador.modelo").where().in("id", aneisIds).findList();
        return transacaoHelper.imporPlano(aneis, posicaoPlano, horarioEntrada, duracao, timeout, authToken)
            .thenApply(wsResponseMapFunction(aneis));
    }

    private CompletionStage<Map<String, List<String>>> liberarImposicao(List<String> aneisIds, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Anel> aneis = Anel.find.fetch("controlador").fetch("controlador.modelo").where().in("id", aneisIds).findList();
        return transacaoHelper.liberarImposicao(aneis, timeout, authToken)
            .thenApply(wsResponseMapFunction(aneis));

    }

    private CompletionStage<Map<String, List<String>>> colocarControladorManutencao(List<String> aneis, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.colocarControladorManutencao(controladores, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));
    }

    private CompletionStage<Map<String, List<String>>> inativarControlador(List<String> aneis, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.inativarControlador(controladores, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));

    }

    private CompletionStage<Map<String, List<String>>> ativarControlador(List<String> aneis, Long timeout) {
        String authToken = contextManager.getAuthToken(ctx());
        List<Controlador> controladores = getControladores(aneis);
        return transacaoHelper.ativarControlador(controladores, timeout, authToken)
            .thenApply(wsResponseMapFunction(controladores));
    }


    private List<Controlador> getControladores(List<String> aneis) {
        return aneis.stream()
            .map(anelId -> Anel.find.byId(UUID.fromString(anelId)))
            .map(Anel::getControlador)
            .distinct()
            .collect(Collectors.toList());
    }

    private Function<WSResponse, Map<String, List<String>>> wsResponseMapFunction(List<? extends Model> objetos) {
        return response -> {
            String pacoteTransacaoId = response.getBody();
            Map<String, List<String>> ids = new HashMap<>();
            ids.put(pacoteTransacaoId, objetos.stream().map(c -> {
                if (c instanceof Controlador) {
                    return ((Controlador) c).getId().toString();
                } else {
                    return ((Anel) c).getId().toString();
                }
            }).collect(Collectors.toList()));
            return ids;
        };
    }
}
