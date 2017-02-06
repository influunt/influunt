package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import models.StatusAnel;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemImposicaoPlano;
import status.Transacao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoImposicaoPlanoActorHandler extends TransacaoImposicaoActorHandler {

    public TransacaoImposicaoPlanoActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload);
        List<Integer> numerosAneis = Json.fromJson(payloadJson.get("numerosAneis"), List.class);
        List<StatusAnel> statusAneis = storage.getStatusAneis().entrySet().stream()
            .filter(entry -> numerosAneis.contains(entry.getKey()))
            .map(entry -> entry.getValue())
            .collect(Collectors.toList());
        if (isImposicaoPlanoOk(storage.getControlador(), transacao, statusAneis)) {
            storage.setTempData(transacao.transacaoId, "posicaoPlano", payloadJson.get("posicaoPlano").asText());
            storage.setTempData(transacao.transacaoId, "numerosAneis", payloadJson.get("numerosAneis").toString());
            storage.setTempData(transacao.transacaoId, "horarioEntrada", payloadJson.get("horarioEntrada").asText());
            storage.setTempData(transacao.transacaoId, "duracao", payloadJson.get("duracao").asText());
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
            idControlador,
            Integer.parseInt(storage.getTempData(transacao.transacaoId, "posicaoPlano")),
            Json.fromJson(Json.parse(storage.getTempData(transacao.transacaoId, "numerosAneis")), List.class),
            Long.parseLong(storage.getTempData(transacao.transacaoId, "horarioEntrada")),
            Integer.parseInt(storage.getTempData(transacao.transacaoId, "duracao")));

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
        storage.clearTempData(transacao.transacaoId);
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
        storage.clearTempData(transacao.transacaoId);
    }

}
