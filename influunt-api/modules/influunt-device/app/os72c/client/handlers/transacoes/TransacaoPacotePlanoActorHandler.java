package os72c.client.handlers.transacoes;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import json.ControladorCustomDeserializer;
import logger.InfluuntLogger;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import models.StatusDevice;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.*;
import status.Transacao;

import java.util.Iterator;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoPacotePlanoActorHandler extends TransacaoActorHandler {

    public TransacaoPacotePlanoActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        Controlador controlador = Controlador.isPacotePlanosValido(storage.getControladorJson(), transacao.payload);
        if (controlador != null) {
            storage.setPlanos(Json.parse(transacao.payload.toString()));
            storage.setControlador(controlador);
            storage.setStatus(StatusDevice.ATIVO);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
//        storage.setPlanos(Json.parse(transacao.payload.toString()));
//        storage.setControlador(controlador);
//        storage.setStatus(StatusDevice.ATIVO);
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
    }

    @Override
    protected void executeAbort(Transacao transacao) {

    }

}
