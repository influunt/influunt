package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemImposicaoPlano;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoImposicaoPlanoActorHandler extends TransacaoImposicaoActorHandler {

    public TransacaoImposicaoPlanoActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        if (isImposicaoPlanoOk(storage.getControlador(), transacao)) {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
            idControlador,
            payloadJson.get("posicaoPlano").asInt(),
            payloadJson.get("numeroAnel").asInt(),
            payloadJson.get("horarioEntrada").asLong(),
            payloadJson.get("duracao").asInt());
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
