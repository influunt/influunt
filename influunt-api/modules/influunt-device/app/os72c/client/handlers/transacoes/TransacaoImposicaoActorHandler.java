package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import models.Anel;
import models.Controlador;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.storage.Storage;
import play.libs.Json;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public abstract class TransacaoImposicaoActorHandler extends TransacaoActorHandler {

    public TransacaoImposicaoActorHandler(String idControlador, Storage storage) {
        super(idControlador,storage);
    }

    private boolean isPlanoConfigurado(Controlador controlador, int numeroAnel, int posicaoPlano) {
        Anel anel = controlador.getAneis().stream().filter(a -> a.getPosicao() == numeroAnel).findFirst().orElse(null);
        return anel != null && anel.getPlanos().stream().anyMatch(plano -> plano.getPosicao() == posicaoPlano);
    }

    protected boolean isImposicaoPlanoOk(Controlador controlador, Transacao transacao) {
        JsonNode payload = Json.parse(transacao.payload.toString());
        int posicaoPlano = payload.get("posicaoPlano").asInt();
        int numeroAnel = payload.get("numeroAnel").asInt();
        Long horarioEntrada = payload.get("horarioEntrada").asLong();
        int duracao = payload.get("duracao").asInt();
        boolean planoConfigurado = isPlanoConfigurado(controlador, numeroAnel, posicaoPlano);

        return posicaoPlano >= 0 && numeroAnel >= 1 && duracao >= 15 && duracao <= 600 && planoConfigurado && horarioEntrada > System.currentTimeMillis();
    }

    protected boolean isImposicaoPlanoTemporarioOk(JsonNode controladorJson, Transacao transacao) {
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
        return isImposicaoPlanoOk(controlador, transacao);
    }

}
