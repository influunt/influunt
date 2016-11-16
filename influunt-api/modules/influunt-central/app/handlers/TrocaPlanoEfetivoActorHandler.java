package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import engine.TipoEvento;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.AlarmesFalhasControlador;

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

                //TODO: Salvar no Mongo
                //AlarmesFalhasControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,"");
            }
        }
    }
}
