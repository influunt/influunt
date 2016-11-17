package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import engine.AgendamentoTrocaPlano;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class AgendamentoTrocaPlanoDeserializer extends JsonDeserializer<AgendamentoTrocaPlano> {

    @Override
    public AgendamentoTrocaPlano deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        return deserialize(node);
    }

    public AgendamentoTrocaPlano deserialize(JsonNode node) {
        AgendamentoTrocaPlano agendamento = new AgendamentoTrocaPlano();

        if (node.has("impostoPorFalha")) {
            agendamento.setImpostoPorFalha(node.get("impostoPorFalha").asBoolean());
        }

        if (node.has("plano")) {
            Plano plano = new Plano();
            plano.setPosicao(node.get("plano").get("posicao").asInt());
            plano.setModoOperacao(ModoOperacaoPlano.valueOf(node.get("plano").get("modoOperacao").asText()));
            plano.setDescricao(node.get("plano").get("descricao").asText());
            agendamento.setPlano(plano);
        }

        if (node.has("anel")) {
            agendamento.setAnel(node.get("anel").get("posicao").asInt());
        }

        return agendamento;
    }
}
