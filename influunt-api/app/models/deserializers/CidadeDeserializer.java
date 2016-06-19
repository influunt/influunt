package models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Cidade;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class CidadeDeserializer extends JsonDeserializer<Cidade> {

    @Override
    public Cidade deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Cidade cidade = new Cidade();
        JsonNode id = node.get("id");
        if (id != null) {
            cidade.setId(UUID.fromString(id.asText()));
        }
        cidade.setNome(node.get("nome").asText());

        return cidade;
    }
}
