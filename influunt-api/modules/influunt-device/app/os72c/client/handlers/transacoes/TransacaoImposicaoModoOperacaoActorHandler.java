package os72c.client.handlers.transacoes;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import models.ModoOperacaoPlano;
import models.StatusDevice;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemImposicaoModoOperacao;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoImposicaoModoOperacaoActorHandler extends TransacaoActorHandler {

    public TransacaoImposicaoModoOperacaoActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        if (isImposicaoModoOperacaoOk(transacao)) {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());

        Envelope envelopeImposicaoModo = MensagemImposicaoModoOperacao.getMensagem(
            idControlador,
            payloadJson.get("modoOperacao").asText(),
            payloadJson.get("numeroAnel").asInt(),
            payloadJson.get("horarioEntrada").asLong(),
            payloadJson.get("duracao").asInt());
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoModo, getSelf());

        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {

    }

    private boolean isImposicaoModoOperacaoOk(Transacao transacao) {
        try {
            JsonNode payload = Json.parse(transacao.payload.toString());
            ModoOperacaoPlano.valueOf(payload.get("modoOperacao").asText());
            int numeroAnel = payload.get("numeroAnel").asInt();
            Long horarioEntrada = payload.get("horarioEntrada").asLong();
            int duracao = payload.get("duracao").asInt();
            return numeroAnel >= 1 && duracao >= 15 && duracao <= 600 && horarioEntrada > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

}
