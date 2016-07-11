package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.Cidade;
import models.LimiteArea;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class AreaDeserializer extends JsonDeserializer<Area> {

    @Override
    public Area deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Area area = new Area();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                area.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("descricao")) {
            area.setDescricao(node.get("descricao").asInt());
        }

        if (node.has("cidade")) {
            area.setCidade(Json.fromJson(node.get("cidade"), Cidade.class));
        }

        if (node.has("limites") && node.get("limites") != null) {
            List<LimiteArea> limites = new ArrayList<LimiteArea>();
            for (JsonNode estagioNode : node.get("limites")) {
                limites.add(Json.fromJson(estagioNode, LimiteArea.class));
            }
            area.setLimitesGeograficos(limites);
        }

        return area;
    }
}
