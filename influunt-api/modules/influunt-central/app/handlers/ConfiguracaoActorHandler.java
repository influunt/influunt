package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.Configuracao;
import protocol.Envelope;
import protocol.TipoMensagem;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getEmResposta() == null) {
                Envelope envelope1 = Configuracao.getMensagem(envelope);
                log.info("ENVIANDO CONFIGURACAO");
                getContext().actorSelection("akka://application/user/servidor/CentralMQTT").tell(envelope1, getSelf());
            } else {
                log.info("Controlador confirmando o recebindo da configuração: {}", envelope.getConteudo().toString());
            }
        }
    }
}
