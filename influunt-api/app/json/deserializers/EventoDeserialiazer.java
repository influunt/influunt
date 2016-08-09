package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Cidade;
import models.DiaDaSemana;
import models.Evento;
import models.TabelaHorario;
import org.joda.time.LocalTime;
import play.libs.Json;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/25/16.
 */
public class EventoDeserialiazer extends JsonDeserializer<Evento> {

    @Override
    public Evento deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Evento evento = new Evento();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                evento.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            evento.setIdJson(node.get("idJson").asText());
        }

        if (node.has("numero")) {
            evento.setNumero(node.get("numero").asText());
        }

        if (node.has("diaDaSemana")) {
            evento.setDiaDaSemana(DiaDaSemana.valueOf(node.get("diaDaSemana").asText()));
        }

        if (node.has("horario")) {
            evento.setHorario(LocalTime.parse(node.get("horario").asText()));
        }

        return evento;
    }
}
