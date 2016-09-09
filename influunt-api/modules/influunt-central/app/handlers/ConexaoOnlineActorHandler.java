package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.StatusConexaoControlador;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConexaoOnlineActorHandler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONTROLADOR_ONLINE)) {
                log.info("O controlador: {} esta online", envelope.getIdControlador());
                StatusConexaoControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), true);
            }
        }
    }
}
