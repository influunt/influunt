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
            JsonNode payloadJson = Json.parse(transacao.payload.toString());
            storage.clearTempData();
            storage.setTempData("posicaoPlano", payloadJson.get("posicaoPlano").asText());
            storage.setTempData("numeroAnel", payloadJson.get("numeroAnel").asText());
            storage.setTempData("horarioEntrada", payloadJson.get("horarioEntrada").asText());
            storage.setTempData("duracao", payloadJson.get("duracao").asText());

            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
            idControlador,
            Integer.parseInt(storage.getTempData("posicaoPlano")),
            Integer.parseInt(storage.getTempData("numeroAnel")),
            Long.parseLong(storage.getTempData("horarioEntrada")),
            Integer.parseInt(storage.getTempData("duracao")));

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
