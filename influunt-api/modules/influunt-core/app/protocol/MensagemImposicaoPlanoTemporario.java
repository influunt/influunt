package protocol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.ControladorCustomSerializer;
import models.Plano;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MensagemImposicaoPlanoTemporario {

    public int posicaoPlano;
    public int numeroAnel;
    public Long horarioEntrada;
    public int duracao;
    public Plano planoTemporario;

    public MensagemImposicaoPlanoTemporario(String controladorId, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        this.posicaoPlano = posicaoPlano;
        this.numeroAnel = numeroAnel;
        this.duracao = duracao;
        this.horarioEntrada = horarioEntrada;
        planoTemporario = Plano.find.fetch("versaoPlano.anel.controlador").where().eq("versaoPlano.anel.controlador.id", controladorId).eq("posicao", posicaoPlano).findUnique();
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("posicaoPlano", posicaoPlano);
        json.put("numeroAnel", numeroAnel);
        json.put("horarioEntrada", horarioEntrada);
        json.put("duracao", duracao);
        json.set("plano", new ControladorCustomSerializer().getPlanoCompletoJson(planoTemporario));
        return json;
    }

    public static Envelope getMensagem(String controladorId, int posicaoPlano, int numeroAnel, Long horarioEntrada, int duracao) {
        String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numeroAnel, horarioEntrada, duracao).toJson().toString();
        Envelope envelope = new Envelope(TipoMensagem.IMPOSICAO_DE_PLANO_TEMPORARIO, controladorId, null, QoS.EXACTLY_ONCE, payload, null);
        envelope.setCriptografado(false);
        return envelope;
    }
}
