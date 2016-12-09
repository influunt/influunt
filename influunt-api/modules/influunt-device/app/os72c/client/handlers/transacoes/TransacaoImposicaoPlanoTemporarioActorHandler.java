package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import json.ControladorCustomDeserializer;
import models.Controlador;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.MensagemImposicaoPlano;
import status.Transacao;

import java.util.Iterator;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoImposicaoPlanoTemporarioActorHandler extends TransacaoImposicaoActorHandler {

    public TransacaoImposicaoPlanoTemporarioActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    @Override
    protected void executePrepareToCommit(Transacao transacao) {
        JsonNode controladorJson = savePlanoTemporario(storage.getControladorJson(), transacao);
        if (isImposicaoPlanoTemporarioOk(controladorJson, transacao)) {
            JsonNode payloadJson = Json.parse(transacao.payload.toString());

            storage.setTempData(transacao.transacaoId,"posicaoPlano", payloadJson.get("posicaoPlano").asText());
            storage.setTempData(transacao.transacaoId,"numeroAnel", payloadJson.get("numeroAnel").asText());
            storage.setTempData(transacao.transacaoId,"horarioEntrada", payloadJson.get("horarioEntrada").asText());
            storage.setTempData(transacao.transacaoId,"duracao", payloadJson.get("duracao").asText());

            Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
            storage.setControladorStaging(controlador);
            transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
        } else {
            transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
        }
    }

    @Override
    protected void executeCommit(Transacao transacao) {
        storage.setControlador(storage.getControladorStaging());

        Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
            idControlador,
            Integer.parseInt(storage.getTempData(transacao.transacaoId,"posicaoPlano")),
            Integer.parseInt(storage.getTempData(transacao.transacaoId,"numeroAnel")),
            Long.parseLong(storage.getTempData(transacao.transacaoId,"horarioEntrada")),
            Integer.parseInt(storage.getTempData(transacao.transacaoId,"duracao")));
        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
        transacao.etapaTransacao = EtapaTransacao.COMMITED;
        storage.clearTempData(transacao.transacaoId);
    }

    @Override
    protected void executeAbort(Transacao transacao) {
        transacao.etapaTransacao = EtapaTransacao.ABORTED;
        storage.clearTempData(transacao.transacaoId);
    }

    private JsonNode savePlanoTemporario(JsonNode controladorJson, Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        JsonNode planoTemporarioJson = payloadJson.get("plano");

        // add estagios planos
        for (JsonNode epJson : planoTemporarioJson.get("estagiosPlanos")) {
            ((ArrayNode) controladorJson.get("estagiosPlanos")).add(epJson);
        }

        // add grupos semaforicos planos
        for (JsonNode gspJson : planoTemporarioJson.get("gruposSemaforicosPlanos")) {
            ((ArrayNode) controladorJson.get("gruposSemaforicosPlanos")).add(gspJson);
        }

        //  add versão plano
        ((ArrayNode) controladorJson.get("versoesPlanos")).add(planoTemporarioJson.get("versaoPlano"));

        // add plano
        for (Iterator<JsonNode> it = controladorJson.get("planos").iterator(); it.hasNext(); ) {
            JsonNode plano = it.next();
            boolean mesmaPosicao = plano.get("posicao").asInt() == planoTemporarioJson.get("posicao").asInt();
            boolean mesmoAnel = plano.get("anel").get("idJson").asText().equals(planoTemporarioJson.get("anel").get("idJson").asText());
            if (mesmaPosicao && mesmoAnel) {
                it.remove();
                break;
            }
        }
        ((ArrayNode) controladorJson.get("planos")).add(planoTemporarioJson);

        return controladorJson;
    }

}
