package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.Subarea;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class SubareaDeserializer extends JsonDeserializer<Subarea> {

    @Override
    public Subarea deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Subarea subarea = new Subarea();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                subarea.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("nome")) {
            subarea.setNome(node.get("nome").asText());
        }

        if (node.has("numero")) {
            subarea.setNumero(node.get("numero").asInt());
        }

        if (node.has("area")) {
            subarea.setArea(Json.fromJson(node.get("area"), Area.class));
        }

        return subarea;
    }
}
