package os72c.client.handlers.transacoes;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemImposicaoPlano;
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
        if (payloadJson.get("numeroAnel").asInt() < 1) {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        Envelope envelopeLiberarImposicao = MensagemLiberarImposicao.getMensagem(
            idControlador,
            payloadJson.get("numeroAnel").asInt());
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeLiberarImposicao, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {

    }

}
