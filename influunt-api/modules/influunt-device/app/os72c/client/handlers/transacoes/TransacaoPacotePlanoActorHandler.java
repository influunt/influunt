package os72c.client.handlers.transacoes;

import logger.InfluuntLogger;
import models.Controlador;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.Sinal;
import protocol.TipoMensagem;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoPacotePlanoActorHandler extends TransacaoActorHandler {

    public TransacaoPacotePlanoActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        Controlador controlador = Controlador.isPacotePlanosValido(storage.getControladorJson(), transacao.payload);
        if (controlador != null) {
//            storage.setPlanos(Json.parse(transacao.payload.toString()));
//            storage.setControlador(controlador);
//            storage.setStatus(StatusDevice.ATIVO);
            storage.setControladorStaging(controlador);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        InfluuntLogger.log("[TRANSACAO HANDLER] onTrocarPlanos -- entrada");
        Envelope envelopeSinal = Sinal.getMensagem(TipoMensagem.TROCAR_PLANOS, idControlador, null);
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeSinal, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }

}
