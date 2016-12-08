package utils;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.avaje.ebean.config.JsonConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import json.ControladorCustomSerializer;
import models.Cidade;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;
import play.libs.Json;
import protocol.*;
import server.conn.CentralMessageBroker;
import status.Transacao;

import java.util.List;

/**
 * Created by pedropires on 11/18/16.
 */
public class TransacaoHelper {

    @Inject
    private ActorSystem context;

    public String enviarPacotePlanos(Controlador controlador) {
        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Transacao transacao = new Transacao(controlador.getControladorFisicoId(), pacotePlanosJson.toString(), TipoTransacao.PACOTE_PLANO);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String enviarConfiguracaoCompleta(Controlador controlador) {
        String controladorId = controlador.getControladorFisicoId();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);
        JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
        Transacao transacao = new Transacao(controladorId, configuracaoJson.toString(), TipoTransacao.CONFIGURACAO_COMPLETA);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String enviarTabelaHoraria(Controlador controlador, boolean imediato) {
        JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
        ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
        Transacao transacao = new Transacao(controlador.getControladorFisicoId(), pacoteTabelaHoraria.toString(), TipoTransacao.PACOTE_TABELA_HORARIA);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String imporModoOperacao(Controlador controlador, ModoOperacaoPlano modoOperacao, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getControladorFisicoId();
        String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao.toString(), numeroAnel, horarioEntrada, duracao)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_MODO_OPERACAO);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String imporPlano(Controlador controlador, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        int posicaoPlanoTemporario = controlador.getModelo().getLimitePlanos() + 1;
        if (posicaoPlano == posicaoPlanoTemporario) {
            // plano temporário
            return imporPlanoTemporario(controlador, posicaoPlano, numeroAnel, horarioEntrada, duracao);
        }

        String controladorId = controlador.getControladorFisicoId();
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, horarioEntrada, duracao)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    private String imporPlanoTemporario(Controlador controlador, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getControladorFisicoId();
        String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numeroAnel, horarioEntrada, duracao).toJson().toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String liberarImposicao(Controlador controlador, int numeroAnel) {
        String controladorId = controlador.getControladorFisicoId();
        String payload = Json.toJson(new MensagemLiberarImposicao(numeroAnel)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO);
        transacao.create();

        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String lerDados(Controlador controlador) {
        Envelope envelope = new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, controlador.getControladorFisicoId(), DestinoCentral.leituraDadosControlador(), QoS.AT_LEAST_ONCE, null, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
        return controlador.getControladorFisicoId();
    }


    private void sendTransaction(Transacao transacao, QoS qos) {
        String transacaoJson = transacao.toJson().toString();
        String destinoTX = DestinoCentral.transacao(transacao.transacaoId);
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, transacao.idControladores.get(0), destinoTX, qos, transacaoJson, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
    }
}
