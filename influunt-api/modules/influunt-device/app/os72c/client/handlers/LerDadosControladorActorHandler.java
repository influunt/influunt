package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Controlador;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import protocol.Envelope;
import protocol.LerDadosControlador;
import protocol.TipoMensagem;

/**
 * Created by lesiopinheiro on 06/12/16.
 */
public class LerDadosControladorActorHandler extends UntypedActor {

    private final Storage storage;

    private final String idControlador;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public LerDadosControladorActorHandler(String idControlador, Storage storage) {
        this.storage = storage;
        this.idControlador = idControlador;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.LER_DADOS_CONTROLADOR)) {

                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelope, getSelf());
            }
        }

    }
}
