package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.*;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class TabelaHorarioSerializer extends JsonSerializer<TabelaHorario> {

    @Override
    public void serialize(TabelaHorario tabelaHorario, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (tabelaHorario.getId() != null) {
            jgen.writeStringField("id", tabelaHorario.getId().toString());
        }
        if (tabelaHorario.getIdJson() != null) {
            jgen.writeStringField("idJson", tabelaHorario.getIdJson().toString());
        }
        if (tabelaHorario.getEventos() != null) {
            jgen.writeArrayFieldStart("eventos");
            for (Evento evento : tabelaHorario.getEventos()) {
                jgen.writeObject(evento);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
