package utils;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.fusesource.mqtt.client.QoS;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.TipoMensagem;

/**
 * Created by pedropires on 11/18/16.
 */
public class TransacaoHelperCentral {

    @Inject
    private ActorSystem context;

    public String sendTransaction(JsonNode pacoteTransacao, QoS qos) {
        Envelope envelope = new Envelope(TipoMensagem.PACOTE_TRANSACAO, pacoteTransacao.get("id").asText(), null, qos, pacoteTransacao.toString(), null);
        sendToBroker(envelope);
        return pacoteTransacao.get("id").asText();
    }

    public String lerDados(String controladorFisicoId) {
        Envelope envelope = new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, controladorFisicoId, DestinoCentral.leituraDadosControlador(), QoS.AT_LEAST_ONCE, null, null);
        sendToBroker(envelope);
        return controladorFisicoId;
    }

    private void sendToBroker(Envelope envelope) {
        ActorSelection centralBroker = context.actorSelection(AtoresCentral.messageBroker());
        centralBroker.tell(envelope, null);
    }
}
