package json.deserializers;

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
 * Created by lesiopinheiro on 7/25/16.
 */
public class TabelaHorarioDeserialiazer extends JsonDeserializer<TabelaHorario> {

    @Override
    public TabelaHorario deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        TabelaHorario tabelaHorario = new TabelaHorario();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaHorario.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            tabelaHorario.setIdJson(node.get("idJson").asText());
        }

        if (node.get("eventos") != null) {
            List<Evento> eventos = new ArrayList<Evento>();
            for (JsonNode nodeEvento : node.get("eventos")) {
                eventos.add(Json.fromJson(nodeEvento, Evento.class));
            }
            tabelaHorario.setEventos(eventos);
        }

        return tabelaHorario;
    }
}
