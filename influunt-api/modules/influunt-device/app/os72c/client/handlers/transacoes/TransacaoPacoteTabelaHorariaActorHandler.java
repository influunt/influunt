package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import logger.InfluuntLogger;
import models.Controlador;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.Sinal;
import protocol.TipoMensagem;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoPacoteTabelaHorariaActorHandler extends TransacaoActorHandler {

    public TransacaoPacoteTabelaHorariaActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        Controlador controlador = Controlador.isPacoteTabelaHorariaValido(storage.getControladorJson(), transacao.payload);
        if (controlador != null) {
            storage.setControladorStaging(controlador);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        // swefo rimediato, colocar 0
        boolean imediato = payloadJson.get("imediato").asBoolean();
        InfluuntLogger.log("[TRANSACAO HANDLER] onMudancaTabelaHoraria -- entrada");

        if (imediato) {
            Envelope envelopeSinal = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA_IMEDIATAMENTE, idControlador, null);
            getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeSinal, getSelf());
        }
        transacao.etapaTransacao = EtapaTransacao.COMMITED;

    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
