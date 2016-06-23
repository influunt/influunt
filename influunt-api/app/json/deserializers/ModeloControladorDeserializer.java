package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.ConfiguracaoControlador;
import models.Fabricante;
import models.ModeloControlador;

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
            Fabricante fabricante = new Fabricante();
            fabricante.setId(UUID.fromString(node.get("fabricante").get("id").asText()));
            modeloControlador.setFabricante(fabricante);
        }

        if (node.has("configuracao") && !node.get("configuracao").isNull()) {
            ConfiguracaoControlador configuracao = new ConfiguracaoControlador();
            configuracao.setId(UUID.fromString(node.get("configuracao").get("id").asText()));
            modeloControlador.setConfiguracao(configuracao);
        }

        return modeloControlador;
    }
}
