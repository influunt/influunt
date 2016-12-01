package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.Envelope;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class EchoActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getEmResposta() == null) {
                log.info("Respondendo echo para controlador: {}", envelope.getConteudo().toString());
                Envelope envelope1 = envelope.replayWithSameMessage("controlador/" + envelope.getIdControlador() + "/echo");

                getContext().actorSelection("akka://application/user/servidor/CentralMQTT").tell(envelope1, getSelf());
            } else {
                log.info("Controlador respondeu o echo: {}", envelope.getConteudo().toString());
            }
        }
    }
}
