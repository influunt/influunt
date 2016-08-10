package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Evento;
import models.TabelaHorario;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class EventoSerializer extends JsonSerializer<Evento> {

    @Override
    public void serialize(Evento evento, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (evento.getId() != null) {
            jgen.writeStringField("id", evento.getId().toString());
        }
        if (evento.getIdJson() != null) {
            jgen.writeStringField("idJson", evento.getIdJson().toString());
        }
        if (evento.getNumero() != null) {
            jgen.writeStringField("numero", evento.getNumero().toString());
        }
        if (evento.getDiaDaSemana() != null) {
            jgen.writeStringField("diaDaSemana", evento.getDiaDaSemana().toString());
        }
        if (evento.getHorario() != null) {
            jgen.writeStringField("horario", evento.getHorario().toString());
        }

        jgen.writeEndObject();
    }
}
