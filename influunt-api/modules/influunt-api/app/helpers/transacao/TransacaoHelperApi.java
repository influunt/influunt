package helpers.transacao;

import akka.actor.ActorSelection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import json.ControladorCustomSerializer;
import models.Anel;
import models.Cidade;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.fusesource.mqtt.client.QoS;
import play.Configuration;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import protocol.*;
import security.AuthToken;
import status.PacoteTransacao;
import status.Transacao;
import utils.RangeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by pedropires on 1/23/17.
 */
public class TransacaoHelperApi {

    @Inject
    private WSClient http;

    @Inject
    private Configuration configuration;

    public CompletionStage<WSResponse> enviarPacotePlanos(List<Controlador> controladores, long timeout, String authToken) {
        PacoteTransacao pacotePlanos = TransacaoBuilder.pacotePlanos(controladores, timeout);
        return sendTransaction(pacotePlanos, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> enviarConfiguracaoCompleta(List<Controlador> controladores, long timeout, String authToken) {
        PacoteTransacao configuracao = TransacaoBuilder.configuracaoCompleta(controladores, timeout);
        return sendTransaction(configuracao, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> enviarTabelaHoraria(List<Controlador> controladores, boolean imediato, long timeout, String authToken) {
        PacoteTransacao tabelaHoraria = TransacaoBuilder.tabelaHoraria(controladores, imediato, timeout);
        return sendTransaction(tabelaHoraria, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> imporModoOperacao(List<Anel> aneis, ModoOperacaoPlano modoOperacao, Long horarioEntrada, int duracao, long timeout, String authToken) {
        PacoteTransacao imposicaoModo = TransacaoBuilder.modoOperacao(aneis, modoOperacao, horarioEntrada, duracao, timeout);
        return sendTransaction(imposicaoModo, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> imporPlano(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout, String authToken) {
        int posicaoPlanoTemporario = aneis.get(0).getControlador().getModelo().getLimitePlanos() + 1;
        if (posicaoPlano == posicaoPlanoTemporario) {
            // plano temporário
            return imporPlanoTemporario(aneis, posicaoPlano, horarioEntrada, duracao, timeout, authToken);
        }

        PacoteTransacao imposicaoPlano = TransacaoBuilder.plano(aneis, posicaoPlano, horarioEntrada, duracao, timeout);
        return sendTransaction(imposicaoPlano, QoS.EXACTLY_ONCE, authToken);
    }

    private CompletionStage<WSResponse> imporPlanoTemporario(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout, String authToken) {
        PacoteTransacao imposicaoPlanoTemporario = TransacaoBuilder.planoTemporario(aneis, posicaoPlano, horarioEntrada, duracao, timeout);
        return sendTransaction(imposicaoPlanoTemporario, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> liberarImposicao(List<Anel> aneis, long timeout, String authToken) {
        PacoteTransacao liberar = TransacaoBuilder.liberarImposicao(aneis, timeout);
        return sendTransaction(liberar, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> colocarControladorManutencao(List<Controlador> controladores, long timeout, String authToken) {
        PacoteTransacao manutencao = TransacaoBuilder.colocarManutencao(controladores, timeout);
        return sendTransaction(manutencao, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> inativarControlador(List<Controlador> controladores, long timeout, String authToken) {
        PacoteTransacao inativar = TransacaoBuilder.inativar(controladores, timeout);
        return sendTransaction(inativar, QoS.EXACTLY_ONCE, authToken);
    }


    public CompletionStage<WSResponse> ativarControlador(List<Controlador> controladores, long timeout, String authToken) {
        PacoteTransacao ativar = TransacaoBuilder.ativar(controladores, timeout);
        return sendTransaction(ativar, QoS.EXACTLY_ONCE, authToken);
    }


    public CompletionStage<JsonNode> lerDados(Controlador controlador, String authToken) {
        JsonNode json = Json.newObject().put("controladorFisicoId", controlador.getControladorFisicoId());
        String url = getCentralUrl() + "/ler_dados";
        return http.url(url).setHeader(AuthToken.TOKEN, authToken).post(json)
            .thenApply(response -> Json.toJson(response.getBody()));
    }



    private CompletionStage<WSResponse> sendTransaction(PacoteTransacao pacoteTransacao, QoS qos, String authToken) {
        pacoteTransacao.create();
        JsonNode json = Json.newObject()
            .put("qos", qos.toString())
            .set("pacoteTransacao", pacoteTransacao.toJson());
        String url = getCentralUrl() + "/transacoes";

        return http.url(url).setHeader(AuthToken.TOKEN, authToken).post(json);
    }

    private String getCentralUrl() {
        return configuration.getConfig("central").getString("baseUrl");
    }
}
