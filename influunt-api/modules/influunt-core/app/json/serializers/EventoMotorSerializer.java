package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import engine.EventoMotor;
import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by pedropires on 6/19/16.
 */
public class EventoMotorSerializer extends JsonSerializer<EventoMotor> {

    @Override
    public void serialize(EventoMotor eventoMotor, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (eventoMotor.getTimestamp() != null) {
            jgen.writeStringField("timestamp", InfluuntDateTimeSerializer.parse(eventoMotor.getTimestamp()));
        }

        if (eventoMotor.getTipoEvento() != null) {
            jgen.writeObjectField("tipoEvento", eventoMotor.getTipoEvento());

            if (eventoMotor.getParams() != null) {
                jgen.writeObjectField("descricaoEvento", eventoMotor.getTipoEvento().getMessage(eventoMotor.getStringParams()));
            }
        }

        if (eventoMotor.getParams() != null) {
            jgen.writeObjectField("params", Arrays.stream(eventoMotor.getParams()).filter(p -> ClassUtils.isPrimitiveOrWrapper(p.getClass())).collect(Collectors.toList()));
        }

        jgen.writeEndObject();
    }
}
