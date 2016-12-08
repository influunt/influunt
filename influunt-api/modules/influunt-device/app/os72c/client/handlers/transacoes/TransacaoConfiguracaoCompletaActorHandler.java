package os72c.client.handlers.transacoes;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import logger.InfluuntLogger;
import models.Controlador;
import models.StatusDevice;
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
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        JsonNode planoJson = payloadJson.get("pacotePlanos");
        //TODO: Validar com o Pedro
        JsonNode tabelaHorariaJson = payloadJson.get("pacoteTabelaHoraria");
        JsonNode controladorJson = payloadJson.get("pacoteConfiguracao");
        Controlador controlador = Controlador.isPacoteCompletoValido(controladorJson, planoJson, tabelaHorariaJson);
        if (controlador != null) {
            storage.setControlador(controlador);
            storage.setPlanos(planoJson);
            storage.setStatus(StatusDevice.ATIVO);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {

    }

}
