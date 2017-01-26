package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import models.StatusAnel;
import models.StatusDevice;
import org.joda.time.DateTime;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.Transacao;

import java.util.List;
import java.util.stream.Collectors;

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

                envelope.setDestino(DestinoCentral.transacao(idControlador, transacao.transacaoId));
                envelope.setConteudo(transacao.toJson().toString());
                InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.COMUNICACAO, transacao);
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
            }
        }
    }

    protected boolean isImposicaoOk(JsonNode payload) {
        List<Integer> numerosAneis = Json.fromJson(payload.get("numerosAneis"), List.class);
        List<StatusAnel> statusAneis = storage.getStatusAneis().entrySet().stream()
            .filter(entry -> numerosAneis.contains(entry.getKey()))
            .map(entry -> entry.getValue())
            .collect(Collectors.toList());

        Long horarioEntrada = payload.get("horarioEntrada").asLong();
        int duracao = payload.get("duracao").asInt();
        boolean numerosAneisOk = numerosAneis.stream().allMatch(numeroAnel -> numeroAnel >= 1);

        return numerosAneisOk &&
            duracao >= 15 && duracao <= 600 &&
            horarioEntrada > DateTime.now().getMillis() &&
            statusAneis.stream().allMatch(entry -> !entry.equals(StatusAnel.IMPOSICAO));
    }
}
