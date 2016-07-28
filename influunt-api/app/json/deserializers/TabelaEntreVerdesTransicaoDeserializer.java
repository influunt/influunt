package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.TabelaEntreVerdes;
import models.TabelaEntreVerdesTransicao;
import models.Transicao;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/5/16.
 */
public class TabelaEntreVerdesTransicaoDeserializer extends JsonDeserializer<TabelaEntreVerdesTransicao> {

    @Override
    public TabelaEntreVerdesTransicao deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = new TabelaEntreVerdesTransicao();
        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaEntreVerdesTransicao.setId(UUID.fromString(id.asText()));
            }
        }
        if (node.get("tempoAmarelo") != null) {
            tabelaEntreVerdesTransicao.setTempoAmarelo(node.get("tempoAmarelo").asInt());
        }
        if (node.get("tempoVermelhoIntermitente") != null) {
            tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(node.get("tempoVermelhoIntermitente").asInt());
        }
        if (node.get("tempoVermelhoLimpeza") != null) {
            tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(node.get("tempoVermelhoLimpeza").asInt());
        }
        if (node.get("tempoAtrasoGrupo") != null) {
            tabelaEntreVerdesTransicao.setTempoAtrasoGrupo(node.get("tempoAtrasoGrupo").asInt());
        }

        if (node.has("transicao")) {
            tabelaEntreVerdesTransicao.setTransicao(Json.fromJson(node.get("transicao"), Transicao.class));
        }

        if (node.has("tabelaEntreVerdes")) {
            tabelaEntreVerdesTransicao.setTabelaEntreVerdes(Json.fromJson(node.get("tabelaEntreVerdes"), TabelaEntreVerdes.class));
        }


        return tabelaEntreVerdesTransicao;
    }
}
