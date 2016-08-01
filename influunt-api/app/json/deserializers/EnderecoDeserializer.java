package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import models.Endereco;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/29/16.
 */
public class EnderecoDeserializer extends JsonDeserializer<Endereco> {
    @Override
    public Endereco deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Endereco endereco = new Endereco();
        JsonNode id = node.get("id");
        if (id != null) {
            endereco.setId(UUID.fromString(id.asText()));
        }
        endereco.setLocalizacao(node.get("localizacao") != null ? node.get("localizacao").asText() : null);
        endereco.setLatitude((node.get("latitude") != null && node.get("latitude").isNumber()) ? node.get("latitude").asDouble() : null);
        endereco.setLongitude((node.get("longitude") != null && node.get("longitude").isNumber()) ? node.get("longitude").asDouble() : null);
        if (node.has("controlador")) {
            endereco.setControlador(Json.fromJson(node.get("controlador"), Controlador.class));
        }

        return endereco;
    }
}
