package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import models.ControladorFisico;
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
public class AlarmeFalhaActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.ALARME_FALHA)) {
                log.info("Alarme falha recebida de: {0} esta offline", envelope.getIdControlador());

                final JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());

                String idAnel = null;
                if (jsonConteudo.has("params") && jsonConteudo.get("params").has(0)) {
                    idAnel = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()))
                        .getControladorAtivo()
                        .findAnelByPosicao(jsonConteudo.get("params").get(0).asInt()).getId().toString();
                }

                AlarmesFalhasControlador.log(envelope.getCarimboDeTempo(),
                    envelope.getIdControlador(),
                    idAnel,
                    jsonConteudo);

                // enviar msg APP alarmes e falhas.
                envelope.setDestino(DestinoApp.alarmesEFalhas());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
