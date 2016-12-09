package os72c.client.handlers.transacoes;

import models.StatusDevice;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MudancaStatusControlador;
import status.Transacao;

/**
 * Created by lesiopinheiro on 08/12/16.
 */
public class TransacaoAtivarControladorActorHandler extends TransacaoActorHandler {

    public TransacaoAtivarControladorActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        if (!storage.getStatus().equals(StatusDevice.ATIVO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        storage.setStatus(StatusDevice.ATIVO);
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
        Envelope envelopeStatus = MudancaStatusControlador.getMensagem(idControlador, storage.getStatus());
        getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelopeStatus, getSelf());
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }
}
