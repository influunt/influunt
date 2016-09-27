package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.Area;
import models.Controlador;
import models.Subarea;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class SubareaSerializer extends JsonSerializer<Subarea> {

    @Override
    public void serialize(Subarea subarea, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (subarea.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", subarea.getId().toString());
        }
        if (subarea.getIdJson() == null) {
            jgen.writeNullField("idJson");
        } else {
            jgen.writeStringField("idJson", subarea.getIdJson().toString());
        }
        if (subarea.getNome() != null) {
            jgen.writeStringField("nome", subarea.getNome());
        }
        if (subarea.getNumero() != null) {
            jgen.writeNumberField("numero", subarea.getNumero());
        }
        if (subarea.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(subarea.getDataCriacao()));
        }
        if (subarea.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(subarea.getDataAtualizacao()));
        }
        if (subarea.getArea() != null) {
            Area area = ObjectUtils.clone(subarea.getArea());
            area.setSubareas(null);
            jgen.writeObjectField("area", area);
        }

        jgen.writeArrayFieldStart("controladores");
        for (Controlador controlador : subarea.getControladores()) {
            jgen.writeStartObject();
            jgen.writeStringField("id", controlador.getId().toString());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
