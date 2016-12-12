package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.DestinoApp;
import protocol.Envelope;
import protocol.LerDadosControlador;
import protocol.TipoMensagem;
import utils.AtoresCentral;

/**
 * Created by lesiopinheiro on 06/12/16.
 */
public class LerDadosActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.LER_DADOS_CONTROLADOR) && (envelope.getEmResposta() == null)) {
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(LerDadosControlador.getMensagem(envelope), getSelf());
            } else {
                envelope.setDestino(DestinoApp.dadosControlador(envelope.getIdControlador()));
                envelope.setCriptografado(false);
                log.info("CENTRAL ENVIANDO DADOS DO CONTROLADOR PARA APP...");
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
