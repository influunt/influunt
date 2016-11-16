package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import engine.TipoEvento;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.AlarmesFalhasControlador;
import status.StatusConexaoControlador;

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

                JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());

                AlarmesFalhasControlador.log(envelope.getIdControlador(),
                    envelope.getCarimboDeTempo(),
                    TipoEvento.valueOf(jsonConteudo.get("tipoEvento").get("tipo").asText()),
                    jsonConteudo.get("descricaoEvento").asText(),
                    envelope.getConteudo().toString());
            }
        }
    }
}
