package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/5/16.
 */
public class TransicaoDeserializer extends JsonDeserializer<Transicao> {

    @Override
    public Transicao deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Transicao transicao = new Transicao();
        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                transicao.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("origem")) {
            transicao.setOrigem(Json.fromJson(node.get("origem"), Estagio.class));
        }

        if (node.has("destino")) {
            transicao.setDestino(Json.fromJson(node.get("destino"), Estagio.class));
        }

        if (node.has("grupoSemaforico")) {
            transicao.setGrupoSemaforico(Json.fromJson(node.get("grupoSemaforico"), GrupoSemaforico.class));
        }

        if (node.has("tabelaEntreVerdes")) {
            List<TabelaEntreVerdesTransicao> tabelaEntreVerdes = new ArrayList<TabelaEntreVerdesTransicao>();
            for (JsonNode tabelaEntreVerdesNode : node.get("tabelaEntreVerdes")) {
                tabelaEntreVerdes.add(Json.fromJson(tabelaEntreVerdesNode, TabelaEntreVerdesTransicao.class));
            }
            transicao.setTabelaEntreVerdes(tabelaEntreVerdes);
        }

        return transicao;
    }
}
