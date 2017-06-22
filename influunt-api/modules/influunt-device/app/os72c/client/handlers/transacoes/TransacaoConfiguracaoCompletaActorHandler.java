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
public class TransacaoConfiguracaoCompletaActorHandler extends TransacaoActorHandler {
    public TransacaoConfiguracaoCompletaActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        JsonNode planoJson = payloadJson.get("pacotePlanos");
        JsonNode pacoteTabelaHorariaJson = payloadJson.get("pacoteTabelaHoraria");
        JsonNode controladorJson = payloadJson.get("pacoteConfiguracao");
        Controlador controlador = Controlador.isPacoteConfiguracaoCompletaValido(controladorJson, planoJson, pacoteTabelaHorariaJson);
        if (controlador != null) {
            storage.setControladorStaging(controlador);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        Envelope envelopeConfig = Sinal.getMensagem(TipoMensagem.ATUALIZAR_CONFIGURACAO, idControlador, null);
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeConfig, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
