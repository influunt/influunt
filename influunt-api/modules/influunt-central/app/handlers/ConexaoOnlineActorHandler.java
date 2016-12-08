package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.DestinoApp;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.StatusConexaoControlador;
import utils.AtoresCentral;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConexaoOnlineActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message)  {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONTROLADOR_ONLINE)) {
                log.info("O controlador: {} esta online", envelope.getIdControlador());

                // enviar msg APP controlador online
                envelope.setDestino(DestinoApp.controladorOnline());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());

                StatusConexaoControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), true);
            }
        }
    }
}
