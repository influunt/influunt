package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import models.ModoOperacaoPlano;
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
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        if (isImposicaoModoOperacaoOk(payloadJson)) {
            storage.clearTempData();
            storage.setTempData("modoOperacao", payloadJson.get("modoOperacao").asText());
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
        Envelope envelopeImposicaoModo = MensagemImposicaoModoOperacao.getMensagem(
            idControlador,
            storage.getTempData("modoOperacao"),
            Integer.parseInt(storage.getTempData("numeroAnel")),
            Long.parseLong(storage.getTempData("horarioEntrada")),
            Integer.parseInt(storage.getTempData("duracao")));

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoModo, getSelf());

        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

    private boolean isImposicaoModoOperacaoOk(JsonNode payload) {
        try {
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
