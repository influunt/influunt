package protocol;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.GerenciadorDeTabelaHoraria;
import logger.InfluuntLogAppender;
import models.Controlador;
import models.Evento;
import models.Plano;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;

/**
 * Created by lesiopinheiro on 06/12/16.
 */
public class LerDadosControlador {

    private LerDadosControlador() {
    }

    public static Envelope getMensagem(Envelope envelope) {
        return new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, envelope.getIdControlador(), DestinoControlador.leituraDadosControlador(envelope.getIdControlador()), QoS.AT_MOST_ONCE, null, null);
    }

    public static Envelope retornoLeituraDados(Envelope envelope, Controlador controlador) {
        ObjectNode controladorJson = play.libs.Json.newObject();
        controladorJson.put("clc", controlador.getCLC());
        controladorJson.put("relogio", System.currentTimeMillis());
        GerenciadorDeTabelaHoraria gerenciadorDeTabelaHoraria = new GerenciadorDeTabelaHoraria();
        gerenciadorDeTabelaHoraria.addEventos(controlador.getTabelaHoraria().getEventos());
        Evento evento = gerenciadorDeTabelaHoraria.eventoAtual(new DateTime());
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        controlador.getAneisAtivos().forEach(anel -> {
            Plano plano = evento.getPlano(anel.getPosicao());
            itens.addObject().put("cla", anel.getCLA()).put("modoOperacao", plano.getModoOperacao().toString()).put("posicaoPlano", plano.getPosicao().toString());
        });
        ArrayNode dados = JsonNodeFactory.instance.arrayNode();
        InfluuntLogAppender.evictingQueue.forEach(erro -> {
            dados.addObject().put("texto", erro.toString());
        });
        controladorJson.putPOJO("planos", itens);
        controladorJson.putPOJO("dados", dados);

        return new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, envelope.getIdControlador(), DestinoCentral.leituraDadosControlador(), QoS.AT_MOST_ONCE, controladorJson.toString(), envelope.getIdMensagem());
    }
}
