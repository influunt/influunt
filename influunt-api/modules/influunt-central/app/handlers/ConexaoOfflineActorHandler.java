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
public class ConexaoOfflineActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message)  {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONTROLADOR_OFFLINE)) {
                log.info("O controlador: {} esta offline", envelope.getIdControlador());
                StatusConexaoControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), false);

                // enviar msg APP controlador offline
                envelope.setDestino(DestinoApp.controladorOffline());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }

    public void postStop() throws Exception {
        System.out.println(" ConexaoOfflineActorHandler morreu" );
        super.postStop();
    }

}
