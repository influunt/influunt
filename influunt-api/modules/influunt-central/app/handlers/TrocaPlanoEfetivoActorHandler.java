package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import play.libs.Json;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.TrocaDePlanoControlador;

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
                log.info("Troca de plano recebida de: {0}", envelope.getIdControlador());

                JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());

                String idAnel = null;
                if (jsonConteudo.has("anel") && jsonConteudo.get("anel").has("posicao")) {
                    idAnel = Controlador.find.byId(UUID.fromString(envelope.getIdControlador()))
                        .findAnelByPosicao(jsonConteudo.get("anel").get("posicao").asInt()).getId().toString();
                }

                TrocaDePlanoControlador.log(System.currentTimeMillis(),
                    envelope.getIdControlador(),
                    idAnel,
                    jsonConteudo);
            }
        }
    }
}
