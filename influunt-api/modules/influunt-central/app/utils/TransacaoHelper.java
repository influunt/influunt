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
import java.util.Collections;
import java.util.List;

/**
 * Created by pedropires on 11/18/16.
 */
public class TransacaoHelper {

    @Inject
    private ActorSystem context;

    public String enviarPacotePlanos(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
            transacoes.add(new Transacao(controladorId, pacotePlanosJson.toString(), TipoTransacao.PACOTE_PLANO));
        });
        return sendTransaction(TipoTransacao.PACOTE_PLANO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String enviarConfiguracaoCompleta(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
            transacoes.add(new Transacao(controladorId, configuracaoJson.toString(), TipoTransacao.CONFIGURACAO_COMPLETA));
        });

        return sendTransaction(TipoTransacao.CONFIGURACAO_COMPLETA, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String enviarTabelaHoraria(List<Controlador> controladores, boolean imediato, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
            ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
            transacoes.add(new Transacao(controlador.getControladorFisicoId(), pacoteTabelaHoraria.toString(), TipoTransacao.PACOTE_TABELA_HORARIA));
        });
        return sendTransaction(TipoTransacao.PACOTE_TABELA_HORARIA, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String imporModoOperacao(List<Controlador> controladores, ModoOperacaoPlano modoOperacao, int numeroAnel, Long horarioEntrada, int duracao, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao.toString(), numeroAnel, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_MODO_OPERACAO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_MODO_OPERACAO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String imporPlano(List<Controlador> controladores, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao, long timeout) {
        int posicaoPlanoTemporario = controladores.get(0).getModelo().getLimitePlanos() + 1;
        if (posicaoPlano == posicaoPlanoTemporario) {
            // plano temporário
            return imporPlanoTemporario(controladores, posicaoPlano, numeroAnel, horarioEntrada, duracao, timeout);
        }

        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numeroAnel, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_PLANO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    private String imporPlanoTemporario(List<Controlador> controladores, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numeroAnel, horarioEntrada, duracao).toJson().toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO));
        });

        return sendTransaction(TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String liberarImposicao(List<Controlador> controladores, int numeroAnel, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            String payload = Json.toJson(new MensagemLiberarImposicao(numeroAnel)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO));
        });

        return sendTransaction(TipoTransacao.LIBERAR_IMPOSICAO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String lerDados(Controlador controlador) {
        Envelope envelope = new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, controlador.getControladorFisicoId(), DestinoCentral.leituraDadosControlador(), QoS.AT_LEAST_ONCE, null, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
        return controlador.getControladorFisicoId();
    }

    public String colocarControladorManutencao(Controlador controlador, long timeout) {
        String controladorId = controlador.getControladorFisicoId();
        List<Transacao> transacoes = Collections.singletonList(new Transacao(controladorId, null, TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO));
        return sendTransaction(TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO, timeout, transacoes, QoS.EXACTLY_ONCE);
    }


    public String inativarControlador(Controlador controlador, long timeout) {
        String controladorId = controlador.getControladorFisicoId();
        List<Transacao> transacoes = Collections.singletonList(new Transacao(controladorId, null, TipoTransacao.INATIVAR_CONTROLADOR));
        return sendTransaction(TipoTransacao.INATIVAR_CONTROLADOR, timeout, transacoes, QoS.EXACTLY_ONCE);
    }

    public String ativarControlador(Controlador controlador, long timeout) {
        String controladorId = controlador.getControladorFisicoId();
        List<Transacao> transacoes = Collections.singletonList(new Transacao(controladorId, null, TipoTransacao.ATIVAR_CONTROLADOR));
        return sendTransaction(TipoTransacao.ATIVAR_CONTROLADOR, timeout, transacoes, QoS.EXACTLY_ONCE);
    }


    private String sendTransaction(TipoTransacao tipoTransacao, long tempoMaximo, List<Transacao> transacoes, QoS qos) {
        PacoteTransacao pacoteTransacao = new PacoteTransacao(tipoTransacao, tempoMaximo, transacoes);
        String pacoteTransacaoJson = pacoteTransacao.toJson().toString();
        String destinoTX = DestinoCentral.transacao(pacoteTransacao.getId());
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, "", destinoTX, qos, pacoteTransacaoJson, null);
        ActorRef centralBroker = context.actorOf(Props.create(CentralMessageBroker.class));
        centralBroker.tell(envelope, null);
        return pacoteTransacao.getId();
    }
}
