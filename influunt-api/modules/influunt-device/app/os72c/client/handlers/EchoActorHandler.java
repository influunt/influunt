package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.Envelope;
import protocol.TipoMensagem;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class EchoActorHandler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.ECHO)) {

                if (envelope.getEmResposta() == null) {
                    log.info("Respondento echo para central: {}", envelope.getConteudo().toString());
                    Envelope envelope1 = envelope.replayWithSameMessage("central/echo");
                    getSender().tell(envelope1, getSelf());
                } else {
                    log.info("Central respondeu o echo: {}", envelope.getConteudo().toString());
                }
            }
        }
    }
}
