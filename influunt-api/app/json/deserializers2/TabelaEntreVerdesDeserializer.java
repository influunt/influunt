package json.deserializers2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.GrupoSemaforico;
import models.TabelaEntreVerdes;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/8/16.
 */
public class TabelaEntreVerdesDeserializer extends JsonDeserializer<TabelaEntreVerdes> {
    @Override
    public TabelaEntreVerdes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        TabelaEntreVerdes tabelaEntreVerdes = new TabelaEntreVerdes();
        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaEntreVerdes.setId(UUID.fromString(id.asText()));
            }
        }

        tabelaEntreVerdes.setDescricao(node.get("descricao") != null ? node.get("descricao").asText() : null);

        if (node.has("grupoSemaforico")) {
            tabelaEntreVerdes.setGrupoSemaforico(Json.fromJson(node.get("grupoSemaforico"), GrupoSemaforico.class));
        }

//        if (node.has("transicoes")) {
//            List<TabelaEntreVerdesTransicao> transicoes = new ArrayList<TabelaEntreVerdesTransicao>();
//            for (JsonNode tabelaEntreVerdesNode : node.get("transicoes")) {
//                transicoes.add(Json.fromJson(tabelaEntreVerdesNode, TabelaEntreVerdesTransicao.class));
//            }
//            tabelaEntreVerdes.setTransicoes(transicoes);
//        }


        return tabelaEntreVerdes;
    }
}
