package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.Configuracao;
import protocol.Envelope;
import utils.AtoresCentral;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getEmResposta() == null) {
                Envelope envelope1 = Configuracao.getMensagem(envelope);
                log.info("[CENTRAL] - ENVIANDO CONFIGURACAO");
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope1, getSelf());
            } else {
                log.info("[CENTRAL] - Controlador confirmando o recebimento da configuração: {}", envelope.getConteudo().toString());
            }
        }
    }
}
