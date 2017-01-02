package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import models.StatusDevice;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public abstract class TransacaoActorHandler extends UntypedActor {
    protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    protected String idControlador;

    protected Storage storage;

    public TransacaoActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    protected abstract void executePrepareToCommit(Transacao transacao);

    protected abstract void executeCommit(Transacao transacao);

    protected abstract void executeAbort(Transacao transacao);


    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.COMUNICACAO, transacao);
                switch (transacao.etapaTransacao) {
                    case PREPARE_TO_COMMIT:
                        executePrepareToCommit(transacao);
                        break;

                    case COMMIT:
                        executeCommit(transacao);
                        break;

                    case ABORT:
                        executeAbort(transacao);
                        break;

                    default:
                        break;
                }

                envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                envelope.setConteudo(transacao.toJson().toString());
                InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.INICIALIZACAO, transacao);
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
            }
        }
    }
}
