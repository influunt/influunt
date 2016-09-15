package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import play.libs.Json;
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
                JsonNode transacaoJson = play.libs.Json.parse(envelope.getConteudo().toString());
                Transacao transacao = (Transacao) Json.fromJson(transacaoJson, Transacao.class);
                log.info("CENTRAL - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao){
                    case NEW:
                        transacao.updateStatus(EtapaTransacao.PREPARE_TO_COMMIT);
                        transacao.setPayload(Controlador.getPacotePlanos(envelope.getIdControlador()));
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
                log.info("CENTRAL - TX Enviada: {}", transacao);
                envelope.setConteudo(Json.toJson(transacao).toString());
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        }
    }
}
