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
public class TransacaoInativarControladorActorHandler extends TransacaoActorHandler {

    public TransacaoInativarControladorActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        if (storage.getStatus().equals(StatusDevice.ATIVO)
            || storage.getStatus().equals(StatusDevice.EM_MANUTENCAO)
            || storage.getStatus().equals(StatusDevice.CONFIGURADO)) {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        storage.setStatus(StatusDevice.INATIVO);

        transacao.etapaTransacao = EtapaTransacao.COMMITED;
        Envelope envelopeStatus = MudancaStatusControlador.getMensagem(idControlador, storage.getStatus(), storage.getStatusAneis());
        getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelopeStatus, getSelf());
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
    }
}
