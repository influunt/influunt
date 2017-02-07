package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import models.Anel;
import models.Controlador;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import play.libs.Json;
import status.Transacao;

import java.util.List;

/**
 * Created by rodrigosol on 9/6/16.
 */
public abstract class TransacaoImposicaoActorHandler extends TransacaoActorHandler {

    public TransacaoImposicaoActorHandler(String idControlador, Storage storage) {
        super(idControlador, storage);
    }

    private boolean isPlanoConfigurado(Controlador controlador, int numeroAnel, int posicaoPlano) {
        Anel anel = controlador.getAneis().stream().filter(a -> a.getPosicao() == numeroAnel).findFirst().orElse(null);
        return anel != null && anel.getPlanos().stream().anyMatch(plano -> plano.getPosicao() == posicaoPlano);
    }

    protected boolean isImposicaoPlanoOk(Controlador controlador, Transacao transacao) {

        JsonNode payload = Json.parse(transacao.payload.toString());
        int posicaoPlano = payload.get("posicaoPlano").asInt();
        List<Integer> numerosAneis = Json.fromJson(payload.get("numerosAneis"), List.class);

        boolean planosConfigurados = numerosAneis.stream().allMatch(numeroAnel -> isPlanoConfigurado(controlador, numeroAnel, posicaoPlano));

        return planosConfigurados && isImposicaoOk(payload);
    }

    protected boolean isImposicaoPlanoTemporarioOk(JsonNode controladorJson, Transacao transacao) {
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
        return isImposicaoPlanoOk(controlador, transacao);
    }

}
