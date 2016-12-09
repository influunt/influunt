package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemLiberarImposicao;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoLiberarImposicaoActorHandler extends TransacaoActorHandler {

    public TransacaoLiberarImposicaoActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        if (storage.getControlador().getAneisAtivos().stream().anyMatch(anel -> anel.getPosicao().equals(payloadJson.get("numeroAnel").asInt()))) {
            storage.setTempData("numeroAnel", payloadJson.get("numeroAnel").asText());
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        Envelope envelopeLiberarImposicao = MensagemLiberarImposicao.getMensagem(
            idControlador,
            Integer.parseInt(storage.getTempData("numeroAnel")));

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeLiberarImposicao, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
