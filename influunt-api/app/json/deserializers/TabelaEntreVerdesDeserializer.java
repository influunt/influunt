package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.GrupoSemaforico;
import models.TabelaEntreVerdes;
import models.TabelaEntreVerdesTransicao;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/8/16.
 */
public class TabelaEntreVerdesDeserializer extends JsonDeserializer<TabelaEntreVerdes> {
    @Override
    public TabelaEntreVerdes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

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
        tabelaEntreVerdes.setPosicao(node.get("posicao") != null ? node.get("posicao").asInt() : null);
        if (node.has("grupoSemaforico")) {
            tabelaEntreVerdes.setGrupoSemaforico(Json.fromJson(node.get("grupoSemaforico"), GrupoSemaforico.class));
        }

        if (node.has("tabelaEntreVerdesTransicoes")) {
            List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<TabelaEntreVerdesTransicao>();
            for (JsonNode nodeTevTransicao : node.get("tabelaEntreVerdesTransicoes")) {
                TabelaEntreVerdesTransicao tevTransicao = Json.fromJson(nodeTevTransicao, TabelaEntreVerdesTransicao.class);
                tevTransicao.setTabelaEntreVerdes(tabelaEntreVerdes);
                tabelaEntreVerdesTransicoes.add(tevTransicao);
            }
            tabelaEntreVerdes.setTabelaEntreVerdesTransicoes(tabelaEntreVerdesTransicoes);
        }

        return tabelaEntreVerdes;
    }
}
