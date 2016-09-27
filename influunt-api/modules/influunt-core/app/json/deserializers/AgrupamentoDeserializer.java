package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Agrupamento;
import models.Anel;
import models.DiaDaSemana;
import models.TipoAgrupamento;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class AgrupamentoDeserializer extends JsonDeserializer<Agrupamento> {

    @Override
    public Agrupamento deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        return deserialize(node);
    }

    public Agrupamento deserialize(JsonNode node) {
        Agrupamento agrupamento = new Agrupamento();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                agrupamento.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("nome")) {
            agrupamento.setNome(node.get("nome").asText());
        }

        if (node.has("numero")) {
            agrupamento.setNumero(node.get("numero").asText());
        }

        if (node.has("descricao")) {
            agrupamento.setDescricao(node.get("descricao").asText());
        }

        if (node.has("tipo")) {
            agrupamento.setTipo(TipoAgrupamento.valueOf(node.get("tipo").asText()));
        }

        if (node.has("diaDaSemana")) {
            agrupamento.setDiaDaSemana(DiaDaSemana.get(node.get("diaDaSemana").asText()));
        }

        if (node.has("posicaoPlano")) {
            agrupamento.setPosicaoPlano(node.get("posicaoPlano").asInt());
        }

        if (node.has("horario")) {
            agrupamento.setHorario(LocalTime.parse(node.get("horario").asText()));
        }

        if (node.has("aneis")) {
            for (JsonNode anelJson : node.get("aneis")) {
                if (anelJson.has("id")) {
                    Anel anel = new Anel();
                    anel.setId(UUID.fromString(anelJson.get("id").asText()));
                    if (anelJson.get("ativo") != null) {
                        anel.setAtivo(anelJson.get("ativo").asBoolean());
                    }
                    agrupamento.addAnel(anel);
                }
            }
        }

        return agrupamento;
    }
}
