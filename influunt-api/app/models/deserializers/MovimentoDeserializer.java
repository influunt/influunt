package models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Estagio;
import models.Imagem;
import models.Movimento;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class MovimentoDeserializer extends JsonDeserializer<Movimento> {
    @Override
    public Movimento deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Movimento movimento = new Movimento();
        JsonNode id = node.get("id");
        if (id != null) {
            movimento.setId(UUID.fromString(id.asText()));
        }
        if (node.get("descricao") != null) {
            movimento.setDescricao(node.get("descricao").asText());
        }
        if (node.has("imagem")) {
            Imagem img = new Imagem();
            img.setId(UUID.fromString(node.get("imagem").get("id").asText()));
            movimento.setImagem(img);
        }

        if (node.has("estagio")) {
            Estagio estagio = Json.fromJson(node.get("estagio"), Estagio.class);
            movimento.setEstagio(estagio);
        }

        return movimento;
    }
}
