package helpers;

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
        List<Transacao> transacoes = new ArrayList<>();

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
            transacoes.add(new Transacao(controladorId, pacotePlanosJson.toString(), TipoTransacao.PACOTE_PLANO));
        });
        return sendTransaction(TipoTransacao.PACOTE_PLANO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> enviarConfiguracaoCompleta(List<Controlador> controladores, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
            transacoes.add(new Transacao(controladorId, configuracaoJson.toString(), TipoTransacao.CONFIGURACAO_COMPLETA));
        });
        return sendTransaction(TipoTransacao.CONFIGURACAO_COMPLETA, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> enviarTabelaHoraria(List<Controlador> controladores, boolean imediato, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
            ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
            transacoes.add(new Transacao(controlador.getControladorFisicoId(), pacoteTabelaHoraria.toString(), TipoTransacao.PACOTE_TABELA_HORARIA));
        });
        return sendTransaction(TipoTransacao.PACOTE_TABELA_HORARIA, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> imporModoOperacao(List<Anel> aneis, ModoOperacaoPlano modoOperacao, Long horarioEntrada, int duracao, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();

        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao.toString(), numerosAneis, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_MODO_OPERACAO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_MODO_OPERACAO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> imporPlano(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout, String authToken) {
        int posicaoPlanoTemporario = aneis.get(0).getControlador().getModelo().getLimitePlanos() + 1;
        if (posicaoPlano == posicaoPlanoTemporario) {
            // plano temporário
            return imporPlanoTemporario(aneis, posicaoPlano, horarioEntrada, duracao, timeout, authToken);
        }

        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> java.util.Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numerosAneis, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_PLANO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    private CompletionStage<WSResponse> imporPlanoTemporario(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numerosAneis, horarioEntrada, duracao).toJson().toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> liberarImposicao(List<Anel> aneis, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> java.util.Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemLiberarImposicao(numerosAneis)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO));
        });

        return sendTransaction(TipoTransacao.LIBERAR_IMPOSICAO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> colocarControladorManutencao(List<Controlador> controladores, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO));
        });
        return sendTransaction(TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }

    public CompletionStage<WSResponse> inativarControlador(List<Controlador> controladores, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.INATIVAR_CONTROLADOR));
        });
        return sendTransaction(TipoTransacao.INATIVAR_CONTROLADOR, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }


    public CompletionStage<WSResponse> ativarControlador(List<Controlador> controladores, long timeout, String authToken) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.ATIVAR_CONTROLADOR));
        });
        return sendTransaction(TipoTransacao.ATIVAR_CONTROLADOR, timeout, transacoes, QoS.EXACTLY_ONCE, authToken);
    }


    public CompletionStage<JsonNode> lerDados(Controlador controlador, String authToken) {
        JsonNode json = Json.newObject().put("controladorFisicoId", controlador.getControladorFisicoId());
        String url = getCentralUrl() + "/ler_dados";
        return http.url(url).setHeader(AuthToken.TOKEN, authToken).post(json)
            .thenApply(response -> Json.toJson(response.getBody()));
    }

    private CompletionStage<WSResponse> sendTransaction(TipoTransacao tipoTransacao, long timeout, List<Transacao> transacoes, QoS qos, String authToken) {
        PacoteTransacao pacoteTransacao = new PacoteTransacao(tipoTransacao, timeout, transacoes);
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
