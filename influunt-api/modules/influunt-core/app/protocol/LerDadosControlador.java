package protocol;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.GerenciadorDeEstagios;
import engine.Motor;
import logger.InfluuntLogAppender;
import models.Controlador;
import models.Plano;
import models.StatusDevice;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;

/**
 * Created by lesiopinheiro on 06/12/16.
 */
public class LerDadosControlador {

    private LerDadosControlador() {
    }

    public static Envelope getMensagem(Envelope envelope) {
        return new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR,
            envelope.getIdControlador(),
            DestinoControlador.leituraDadosControlador(envelope.getIdControlador()),
            QoS.AT_LEAST_ONCE,
            null,
            null);
    }

    public static Envelope retornoLeituraDados(Envelope envelope, Motor motor, StatusDevice statusDevice) {
        ObjectNode controladorJson = play.libs.Json.newObject();
        Controlador controlador = motor.getControlador();
        controladorJson.put("clc", controlador.getCLC());
        controladorJson.put("relogio", DateTime.now().getMillis());
        controladorJson.put("status", statusDevice.toString());

        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        controlador.getAneisAtivos().forEach(anel -> {
            GerenciadorDeEstagios gerenciador = motor.getEstagios().get(anel.getPosicao() - 1);
            Plano plano = gerenciador.getPlano();
            itens.addObject().put("cla", anel.getCLA())
                .put("modoOperacao", plano.getModoOperacao().toString())
                .put("posicaoPlano", gerenciador.getPosicaoPlano())
                .put("impostoPorFalha", plano.isImpostoPorFalha())
                .put("imposto", plano.isImposto())
                .put("estagioAtual", gerenciador.getEstagioAtual())
                .put("tempoRestanteDoEstagio", gerenciador.getTempoRestanteDoEstagio())
                .put("tempoRestanteDoCiclo", gerenciador.getTempoRestanteDoCiclo())
                .put("momentoCiclo", gerenciador.getContadorTempoCicloEmSegundos());
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
