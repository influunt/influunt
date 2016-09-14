package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protocol.*;
import status.Transacao;
import utils.AtoresCentral;

import static protocol.EtapaTransacao.COMMITED;
import static protocol.EtapaTransacao.PREPARE_FAIL;
import static protocol.EtapaTransacao.PREPARE_OK;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoActorHandler extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                Transacao transacao = (Transacao) envelope.getConteudo();
                log.info("NOVA TRANSACAO FOI CRIADA: {}", transacao);
                switch (transacao.etapaTransacao){

                    case NEW:
                        transacao.updateStatus(EtapaTransacao.PREPARE_TO_COMMIT);
                        envelope.setDestino(DestinoControlador.transacao(envelope.getIdControlador()));
                        break;

                    case PREPARE_OK:
                        transacao.updateStatus(EtapaTransacao.COMMIT);
                        envelope.setDestino(DestinoControlador.transacao(envelope.getIdControlador()));
                        break;

                    case PREPARE_FAIL:
                        transacao.updateStatus(EtapaTransacao.FAILD);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        break;

                    case COMMITED:
                        transacao.updateStatus(EtapaTransacao.COMPLETED);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        break;

                    case ABORTED:
                        transacao.updateStatus(EtapaTransacao.FAILD);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        break;
                }
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
