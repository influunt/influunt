package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemLiberarImposicao;
import status.Transacao;

import java.util.List;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoLiberarImposicaoActorHandler extends TransacaoActorHandler {

    public TransacaoLiberarImposicaoActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload);
        if (isLiberarImposicaoOk(payloadJson)) {
            storage.setTempData(transacao.transacaoId, "numerosAneis", payloadJson.get("numerosAneis").toString());
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        Envelope envelopeLiberarImposicao = MensagemLiberarImposicao.getMensagem(
            idControlador,
            Json.fromJson(Json.parse(storage.getTempData(transacao.transacaoId, "numerosAneis")), List.class));

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeLiberarImposicao, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
        storage.clearTempData(transacao.transacaoId);
    }

    private boolean isLiberarImposicaoOk(JsonNode payload) {
        List<Integer> numerosAneis = Json.fromJson(payload.get("numerosAneis"), List.class);
        return numerosAneis.stream().allMatch(numeroAnel -> isAnelAtivo(storage.getControlador(), numeroAnel));
    }

    private boolean isAnelAtivo(Controlador controlador, int numeroAnel) {
        return controlador.getAneisAtivos().stream().anyMatch(anel -> anel.getPosicao().equals(numeroAnel));
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
        storage.clearTempData(transacao.transacaoId);
    }

}
