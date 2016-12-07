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
import status.TrocaDePlanoControlador;
import utils.AtoresCentral;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TrocaPlanoEfetivoActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TROCA_DE_PLANO)) {
                log.info("Troca de plano recebida de: {}", envelope.getIdControlador());

                JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());

                String idAnel = null;
                if (jsonConteudo.has("anel") && jsonConteudo.get("anel").has("posicao")) {
                    idAnel = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()))
                        .getControladorAtivo()
                        .findAnelByPosicao(jsonConteudo.get("anel").get("posicao").asInt()).getId().toString();
                }

                TrocaDePlanoControlador.log(System.currentTimeMillis(),
                    envelope.getIdControlador(),
                    idAnel,
                    jsonConteudo);


                // enviar msg APP troca de plano.
                envelope.setDestino(DestinoApp.trocaPlano());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
