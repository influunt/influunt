package models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import models.Movimento;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class AnelDeserializer extends JsonDeserializer<Anel> {
    @Override
    public Anel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Anel anel = new Anel();
        anel.setAtivo(node.get("ativo").asBoolean());

        if (anel.isAtivo()) {
            JsonNode id = node.get("id");
            if (id != null) {
                anel.setId(UUID.fromString(node.get("id").asText()));
            }
            anel.setDescricao(node.get("descricao").asText());
            anel.setAtivo(node.get("ativo").asBoolean());
            anel.setPosicao(node.get("posicao").asInt());
            anel.setLatitude(node.get("latitude").asDouble());
            anel.setLongitude(node.get("longitude").asDouble());
            anel.setQuantidadeGrupoPedestre(node.get("quantidadeGrupoPedestre").asInt());
            anel.setQuantidadeGrupoVeicular(node.get("quantidadeGrupoVeicular").asInt());
            anel.setQuantidadeDetectorPedestre(node.get("quantidadeDetectorPedestre").asInt());
            anel.setQuantidadeDetectorVeicular(node.get("quantidadeDetectorVeicular").asInt());

            if (node.has("movimentos")) {
                List<Movimento> movimentos = new ArrayList<Movimento>();
                for (JsonNode movimentoNode : node.get("movimentos")) {
                    Movimento mov = Json.fromJson(movimentoNode, Movimento.class);
                    movimentos.add(mov);
                }
                anel.setMovimentos(movimentos);
            }
        }


        return anel;
    }
}
