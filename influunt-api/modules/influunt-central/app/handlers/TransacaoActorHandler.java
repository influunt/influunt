package handlers;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Transacao transacao;

    private ActorRef manager;

    public TransacaoActorHandler(Transacao transacao, ActorRef manager) {
        this.transacao = transacao;
        this.manager = manager;
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                log.info("CENTRAL - TX Recebida: {}", transacao);

                switch (transacao.etapaTransacao) {
                    case PREPARE_OK:
                        transacao.updateEtapaTransacao(EtapaTransacao.COMMIT);
                        break;

                    case PREPARE_FAIL:
                        transacao.updateEtapaTransacao(EtapaTransacao.ABORT);
                        break;

                    case COMMITED:
                        transacao.updateEtapaTransacao(EtapaTransacao.COMPLETED);
                        break;

                    case ABORTED:
                        transacao.updateEtapaTransacao(EtapaTransacao.ABORTED);
                        break;

                    default:
                        break;
                }

                log.info("CENTRAL - TX Enviada: {}", transacao);
                envelope.setConteudo(transacao.toJson().toString());
                manager.tell(transacao, getSelf());
            }
        }
    }

}
