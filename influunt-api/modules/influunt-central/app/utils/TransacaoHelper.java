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
        Transacao transacao = new Transacao(controlador.getId().toString(), pacotePlanosJson, TipoTransacao.PACOTE_PLANO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String enviarConfiguracaoCompleta(Controlador controlador) {
        String controladorId = controlador.getId().toString();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);
        JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
        Transacao transacao = new Transacao(controladorId, configuracaoJson, TipoTransacao.CONFIGURACAO_COMPLETA);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String enviarTabelaHoraria(Controlador controlador, boolean imediato) {
        JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
//        ((ObjectNode))
        ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
        Transacao transacao = new Transacao(controlador.getId().toString(), pacoteTabelaHoraria, TipoTransacao.PACOTE_TABELA_HORARIA);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String imporModoOperacao(Controlador controlador, ModoOperacaoPlano modoOperacao, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getId().toString();
        String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao.toString(), numeroAnel, horarioEntrada, duracao)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_MODO_OPERACAO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String imporPlano(Controlador controlador, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        int posicaoPlanoTemporario = controlador.getModelo().getLimitePlanos() + 1;
        if (posicaoPlano == posicaoPlanoTemporario) {
            // plano temporário
            return imporPlanoTemporario(controlador, posicaoPlano, numeroAnel, horarioEntrada, duracao);
        }

        String controladorId = controlador.getId().toString();
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, horarioEntrada, duracao)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    private String imporPlanoTemporario(Controlador controlador, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getId().toString();
        String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numeroAnel, horarioEntrada, duracao).toJson().toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String liberarImposicao(Controlador controlador, int numeroAnel) {
        String controladorId = controlador.getId().toString();
        String payload = Json.toJson(new MensagemLiberarImposicao(numeroAnel)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }


    private void sendTransaction(Transacao transacao, QoS qos) {
        String transacaoJson = transacao.toJson().toString();
        String destinoTX = DestinoCentral.transacao(transacao.transacaoId);
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, transacao.idControlador, destinoTX, qos, transacaoJson, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
    }


}
