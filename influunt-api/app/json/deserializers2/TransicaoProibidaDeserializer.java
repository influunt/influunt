package json.deserializers2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Estagio;
import models.TransicaoProibida;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/5/16.
 */
public class TransicaoProibidaDeserializer extends JsonDeserializer<TransicaoProibida> {

    @Override
    public TransicaoProibida deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        TransicaoProibida transicaoProibida = new TransicaoProibida();
        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                transicaoProibida.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("origem")) {
            Estagio estagioAux = new Estagio();
            estagioAux.setId(UUID.fromString(node.get("origem").get("id").asText()));
            transicaoProibida.setOrigem(estagioAux);
        }

        if (node.has("destino")) {
            Estagio estagioAux = new Estagio();
            estagioAux.setId(UUID.fromString(node.get("destino").get("id").asText()));
            transicaoProibida.setDestino(estagioAux);
        }

        if (node.has("alternativo")) {
            Estagio estagioAux = new Estagio();
            estagioAux.setId(UUID.fromString(node.get("alternativo").get("id").asText()));
            transicaoProibida.setAlternativo(estagioAux);
        }

        return transicaoProibida;
    }
}
