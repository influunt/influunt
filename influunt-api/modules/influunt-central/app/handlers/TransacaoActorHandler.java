package handlers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import protocol.*;
import server.conn.CentralMessageBroker;
import status.Transacao;
import utils.AtoresCentral;

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

        start();
    }

    private void start() {
        transacao.updateStatus(EtapaTransacao.PREPARE_TO_COMMIT);

        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO,
            transacao.idControlador,
            DestinoControlador.transacao(transacao.idControlador),
            QoS.EXACTLY_ONCE,
            transacao.toJson().toString(),
            null);

        getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
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
                        transacao.updateStatus(EtapaTransacao.COMMIT);
                        break;

                    case PREPARE_FAIL:
                        transacao.updateStatus(EtapaTransacao.ABORT);
                        publishTransactionStatus(transacao, StatusTransacao.ERRO);
                        break;

                    case COMMITED:
                        transacao.updateStatus(EtapaTransacao.COMPLETED);
                        publishTransactionStatus(transacao, StatusTransacao.OK);
                        break;

                    case ABORTED:
                        transacao.updateStatus(EtapaTransacao.FAILED);
                        publishTransactionStatus(transacao, StatusTransacao.ERRO);
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

    private void publishTransactionStatus(Transacao transacao, StatusTransacao status) {
        Envelope envelope = MensagemStatusTransacao.getMensagem(transacao, status);
        log.info("CENTRAL ENVIANDO STATUS TRANSAÇÃO: {}", status);
        getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
    }

}
