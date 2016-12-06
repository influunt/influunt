package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import play.libs.Json;
import protocol.DestinoApp;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.AlarmesFalhasControlador;
import utils.AtoresCentral;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class RemocaoFalhaActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.REMOCAO_FALHA)) {
                log.info("Remoção de falha recebida de: {0} - {1}", envelope.getIdControlador(), envelope.getTipoMensagem().toString());

                final JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());

                String idAnel = null;
                if (jsonConteudo.has("params") && jsonConteudo.get("params").has(0)) {
                    idAnel = Controlador.find.byId(UUID.fromString(envelope.getIdControlador()))
                        .findAnelByPosicao(jsonConteudo.get("params").get(0).asInt()).getId().toString();
                }

                AlarmesFalhasControlador.logRemocao(envelope.getCarimboDeTempo(),
                    envelope.getIdControlador(),
                    idAnel,
                    jsonConteudo);

                // enviar msg APP de remocao de alarmes e falhas.
                envelope.setDestino(DestinoApp.alarmesEFalhas());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
