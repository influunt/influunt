package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Fabricante;
import models.ModeloControlador;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class ModeloControladorDeserializer extends JsonDeserializer<ModeloControlador> {

    @Override
    public ModeloControlador deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ModeloControlador modeloControlador = new ModeloControlador();
        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                modeloControlador.setId(UUID.fromString(id.asText()));
            }
        }
        if (node.has("descricao")) {
            modeloControlador.setDescricao(node.get("descricao").asText());
        }

        if (node.has("fabricante") && !node.get("fabricante").isNull()) {
            modeloControlador.setFabricante(Json.fromJson(node.get("fabricante"), Fabricante.class));
        }

        if (node.has("limiteEstagio")) {
            modeloControlador.setLimiteEstagio(node.get("limiteEstagio").asInt());
        }
        if (node.has("limiteGrupoSemaforico")) {
            modeloControlador.setLimiteGrupoSemaforico(node.get("limiteGrupoSemaforico").asInt());
        }
        if (node.has("limiteAnel")) {
            modeloControlador.setLimiteAnel(node.get("limiteAnel").asInt());
        }
        if (node.has("limiteDetectorPedestre")) {
            modeloControlador.setLimiteDetectorPedestre(node.get("limiteDetectorPedestre").asInt());
        }
        if (node.has("limiteDetectorVeicular")) {
            modeloControlador.setLimiteDetectorVeicular(node.get("limiteDetectorVeicular").asInt());
        }
        if (node.has("limiteTabelasEntreVerdes")) {
            modeloControlador.setLimiteTabelasEntreVerdes(node.get("limiteTabelasEntreVerdes").asInt());
        }

        return modeloControlador;
    }
}
