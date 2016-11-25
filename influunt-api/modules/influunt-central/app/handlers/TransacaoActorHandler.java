package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import protocol.*;
import status.Transacao;
import utils.AtoresCentral;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                log.info("CENTRAL - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao) {
                    case NEW:
                        transacao.updateStatus(EtapaTransacao.PREPARE_TO_COMMIT);
                        envelope.setDestino(DestinoControlador.transacao(envelope.getIdControlador()));
                        break;

                    case PREPARE_OK:
                        transacao.updateStatus(EtapaTransacao.COMMIT);
                        envelope.setDestino(DestinoControlador.transacao(envelope.getIdControlador()));
                        break;

                    case PREPARE_FAIL:
                        transacao.updateStatus(EtapaTransacao.FAILED);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        publishTransactionStatus(transacao, StatusTransacao.ERRO);
                        break;

                    case COMMITED:
                        transacao.updateStatus(EtapaTransacao.COMPLETED);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        publishTransactionStatus(transacao, StatusTransacao.OK);
                        break;

                    case ABORTED:
                        transacao.updateStatus(EtapaTransacao.FAILED);
                        envelope.setDestino(DestinoApp.transacao(transacao.transacaoId));
                        publishTransactionStatus(transacao, StatusTransacao.ERRO);
                        break;

                    default:
                        break;
                }

                log.info("CENTRAL - TX Enviada: {}", transacao);
                envelope.setConteudo(transacao.toJson().toString());
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }

    private void publishTransactionStatus(Transacao transacao, StatusTransacao status) {
        Envelope envelope = MensagemStatusTransacao.getMensagem(transacao, status);
        log.info("CENTRAL ENVIANDO STATUS TRANSAÇÃO: {}", status);
        getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
    }

}
