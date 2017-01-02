package os72c.client.conn;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import os72c.client.storage.Storage;
import protocol.Envelope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 12/16/16.
 */
public class DeadLettersActor extends UntypedActor {

    private final Storage storage;

    public DeadLettersActor(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter) message;
            if (deadLetter.message() instanceof Envelope) {
                Envelope envelope = (Envelope) deadLetter.message();
                switch (envelope.getTipoMensagem()) {
                    case ALARME_FALHA:
                    case REMOCAO_FALHA:
                    case MUDANCA_STATUS_CONTROLADOR:
                    case TROCA_DE_PLANO:
                        storage.storeEnvelope(envelope);
                        break;
                }
            }
        } else if (message instanceof String && "VERIFICAR_DEADLETTER".equals(message)) {
            List<Envelope> lista = new ArrayList<>(storage.getEnvelopes());
            storage.clearEnvelopes();
            lista.stream().forEach(envelope -> {
                InfluuntLogger.log(NivelLog.SUPERDETALHADO, TipoLog.COMUNICACAO, "Reenviado: " + envelope.toString());
                getSender().tell(envelope, getSelf());
            });

        }
    }
}
