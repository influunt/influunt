package utils;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import json.ControladorCustomSerializer;
import models.Cidade;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import protocol.*;
import server.conn.CentralMessageBroker;
import status.PacoteTransacao;
import status.Transacao;

import java.util.ArrayList;
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
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String enviarConfiguracaoCompleta(List<Controlador> controladores) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
            transacoes.add(new Transacao(controladorId, configuracaoJson.toString(), TipoTransacao.CONFIGURACAO_COMPLETA));
        });

        return sendTransaction(transacoes, QoS.EXACTLY_ONCE);
    }

    public String enviarTabelaHoraria(Controlador controlador, boolean imediato) {
        JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
        ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
        Transacao transacao = new Transacao(controlador.getControladorFisicoId(), pacoteTabelaHoraria.toString(), TipoTransacao.PACOTE_TABELA_HORARIA);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String imporModoOperacao(Controlador controlador, ModoOperacaoPlano modoOperacao, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getControladorFisicoId();
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

        String controladorId = controlador.getControladorFisicoId();
        String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, horarioEntrada, duracao)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    private String imporPlanoTemporario(Controlador controlador, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        String controladorId = controlador.getControladorFisicoId();
        String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numeroAnel, horarioEntrada, duracao).toJson().toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String liberarImposicao(Controlador controlador, int numeroAnel) {
        String controladorId = controlador.getControladorFisicoId();
        String payload = Json.toJson(new MensagemLiberarImposicao(numeroAnel)).toString();
        Transacao transacao = new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String lerDados(Controlador controlador) {
        Envelope envelope = new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, controlador.getControladorFisicoId(), DestinoCentral.leituraDadosControlador(), QoS.AT_LEAST_ONCE, null, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
        return controlador.getControladorFisicoId();
    }

    public String colocarControladorManutencao(Controlador controlador) {
        String controladorId = controlador.getControladorFisicoId();
        Transacao transacao = new Transacao(controladorId, null, TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }


    public String inativarControlador(Controlador controlador) {
        String controladorId = controlador.getControladorFisicoId();
        Transacao transacao = new Transacao(controladorId, null, TipoTransacao.INATIVAR_CONTROLADOR);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }

    public String ativarControlador(Controlador controlador) {
        String controladorId = controlador.getControladorFisicoId();
        Transacao transacao = new Transacao(controladorId, null, TipoTransacao.ATIVAR_CONTROLADOR);
        sendTransaction(transacao, QoS.EXACTLY_ONCE);
        return transacao.transacaoId;
    }


    private String sendTransaction(TipoTransacao tipoTransacao, long tempoMaximo, List<Transacao> transacoes, QoS qos) {
        PacoteTransacao pacoteTransacao = new PacoteTransacao(tipoTransacao, tempoMaximo, transacoes);
        pacoteTransacao.create();
        
        String pacoteTransacaoJson = pacoteTransacao.toJson().toString();

        Envelope envelope = new Envelope(TipoMensagem.PACOTE_TRANSACAO, pacoteTransacao.getId(), null, qos, pacoteTransacaoJson, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);

        return  pacoteTransacao.getId();
    }
}
