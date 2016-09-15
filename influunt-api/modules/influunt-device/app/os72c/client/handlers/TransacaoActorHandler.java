package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.*;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoActorHandler extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private String idControlador;

    public TransacaoActorHandler(String idControlador) {
        this.idControlador = idControlador;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                JsonNode transacaoJson = play.libs.Json.parse(envelope.getConteudo().toString());
                Transacao transacao = (Transacao) Json.fromJson(transacaoJson, Transacao.class);
                log.info("DEVICE - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao){
                    case PREPARE_TO_COMMIT:
                        transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    case COMMIT:
                        transacao.etapaTransacao = EtapaTransacao.COMMITED;
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    case ABORT:
                        transacao.etapaTransacao = EtapaTransacao.ABORTED;
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;
                }
                log.info("DEVICE - TX Enviada: {}", transacao);
                envelope.setConteudo(Json.toJson(transacao).toString());
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
            }
        }
    }
}
