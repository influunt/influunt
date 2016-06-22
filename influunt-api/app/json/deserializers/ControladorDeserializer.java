package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import models.Anel;
import models.Area;
import models.Controlador;
import models.ModeloControlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class ControladorDeserializer extends JsonDeserializer<Controlador> {
    @Override
    public Controlador deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Controlador controlador = new Controlador();
        JsonNode id = node.get("id");
        if (id != null) {
            controlador.setId(UUID.fromString(id.asText()));
        }
        controlador.setLocalizacao(node.get("descricao") != null ? node.get("descricao").asText() : null);
        controlador.setNumeroSMEE(node.get("numeroSMEE") != null ? node.get("numeroSMEE").asText() : null);
        controlador.setNumeroSMEEConjugado1(node.get("numeroSMEEConjugado1") != null ? node.get("numeroSMEEConjugado1").asText() : null);
        controlador.setNumeroSMEEConjugado2(node.get("numeroSMEEConjugado2") != null ? node.get("numeroSMEEConjugado2").asText() : null);
        controlador.setNumeroSMEEConjugado3(node.get("numeroSMEEConjugado3") != null ? node.get("numeroSMEEConjugado3").asText() : null);
        controlador.setFirmware(node.get("firmware") != null ? node.get("firmware").asText() : null);
        controlador.setLatitude(node.get("latitude") != null ? node.get("latitude").asDouble() : null);
        controlador.setLongitude(node.get("longitude") != null ? node.get("longitude").asDouble() : null);

        if (node.has("area") && node.get("area").get("id") != null) {
            controlador.setArea(Area.find.byId(UUID.fromString(node.get("area").get("id").asText())));
        }
        if (node.has("modelo") && node.get("modelo").get("id") != null) {
            controlador.setModelo(ModeloControlador.find.byId(UUID.fromString(node.get("modelo").get("id").asText())));
        }

        if (node.has("aneis") ) {
            List<Anel> aneis = new ArrayList<Anel>();
            for (JsonNode nodeAnel : node.get("aneis")) {
                aneis.add(Json.fromJson(nodeAnel, Anel.class));
            }
            controlador.setAneis(aneis);
        }

        return controlador;
    }
}
