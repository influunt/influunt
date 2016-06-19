package models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;

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
            controlador.setId(id.asLong());
        }
        controlador.setDescricao(node.get("descricao") != null ? node.get("descricao").asText() : null);
        controlador.setNumeroSMEE(node.get("numeroSMEE") != null ? node.get("numeroSMEE").asText() : null);
        controlador.setNumeroSMEEConjugado1(node.get("NumeroSMEEConjugado1") != null ? node.get("NumeroSMEEConjugado1").asText() : null);
        controlador.setNumeroSMEEConjugado2(node.get("NumeroSMEEConjugado2") != null ? node.get("NumeroSMEEConjugado2").asText() : null);
        controlador.setNumeroSMEEConjugado3(node.get("NumeroSMEEConjugado3") != null ? node.get("NumeroSMEEConjugado3").asText() : null);
        controlador.setFirmware(node.get("firmware") != null ? node.get("firmware").asText() : null);
        controlador.setLatitude(node.get("latitude") != null ? node.get("latitude").asDouble() : null);
        controlador.setLongitude(node.get("longitude") != null ? node.get("longitude").asDouble() : null);

        Area area = new Area();
        area.setId(UUID.fromString(node.get("area").get("id").asText()));
        controlador.setArea(area);

        controlador.setModelo(ModeloControlador.find.findList().get(0));

        if (node.has("aneis")) {
            List<Anel> aneis = new ArrayList<Anel>();
            for (JsonNode nodeAnel : node.get("aneis")) {
                aneis.add(Json.fromJson(nodeAnel, Anel.class));
            }
            controlador.setAneis(aneis);
        }

        return controlador;
    }
}
