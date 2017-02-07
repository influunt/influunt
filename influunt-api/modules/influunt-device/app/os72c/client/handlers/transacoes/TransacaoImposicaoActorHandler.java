package os72c.client.handlers.transacoes;

import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import models.Anel;
import models.Controlador;
import models.StatusAnel;
import org.joda.time.DateTime;
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

    protected boolean isImposicaoPlanoOk(Controlador controlador, Transacao transacao,
                                         List<StatusAnel> statusAneis) {
        JsonNode payload = Json.parse(transacao.payload.toString());
        int posicaoPlano = payload.get("posicaoPlano").asInt();
        Long horarioEntrada = payload.get("horarioEntrada").asLong();
        int duracao = payload.get("duracao").asInt();

        List<Integer> numerosAneis = Json.fromJson(payload.get("numerosAneis"), List.class);
        boolean numerosAneisOk = numerosAneis.stream().allMatch(numeroAnel -> numeroAnel >= 1);
        boolean planosConfigurados = numerosAneis.stream().allMatch(numeroAnel -> isPlanoConfigurado(controlador, numeroAnel, posicaoPlano));

        return planosConfigurados && numerosAneisOk &&
            duracao >= 15 && duracao <= 600 &&
            horarioEntrada > DateTime.now().getMillis() &&
            statusAneis.stream().allMatch(entry -> !entry.equals(StatusAnel.IMPOSICAO));
    }

    protected boolean isImposicaoPlanoTemporarioOk(JsonNode controladorJson, Transacao transacao,
                                                   List<StatusAnel> statusAneis) {
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
        return isImposicaoPlanoOk(controlador, transacao, statusAneis);
    }

}
