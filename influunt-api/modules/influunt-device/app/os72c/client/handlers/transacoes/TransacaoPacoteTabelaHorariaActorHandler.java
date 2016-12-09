package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
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
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        Controlador controlador = Controlador.isPacoteTabelaHorariaValido(storage.getControladorJson(), transacao.payload);
        if (controlador != null) {
            JsonNode payloadJson = Json.parse(transacao.payload);

            storage.setControladorStaging(controlador);
            storage.setTempData("imediato", payloadJson.get("imediato").asText());

            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        boolean imediato = Boolean.parseBoolean(storage.getTempData("imediato"));

        Envelope envelopeTH;
        if (imediato) {
            envelopeTH = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA_IMEDIATAMENTE, idControlador, null);
        } else {
            envelopeTH = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA, idControlador, null);
        }

        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeTH, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
