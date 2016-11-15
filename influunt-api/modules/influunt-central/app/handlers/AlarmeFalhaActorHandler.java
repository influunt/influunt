package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
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

                AlarmesFalhasControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,"");
            }
        }
    }
}
