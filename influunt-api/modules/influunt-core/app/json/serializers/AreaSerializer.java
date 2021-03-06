package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Area;
import models.Cidade;
import models.LimiteArea;
import models.Subarea;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AreaSerializer extends JsonSerializer<Area> {

    @Override
    public void serialize(Area area, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (area.getId() == null) {
            jgen.writeStringField("id", null);
        } else {
            jgen.writeStringField("id", area.getId().toString());
        }
        if (area.getIdJson() == null) {
            jgen.writeStringField("idJson", null);
        } else {
            jgen.writeStringField("idJson", area.getIdJson().toString());
        }
        if (area.getDescricao() != null) {
            jgen.writeNumberField("descricao", area.getDescricao());
        }
        if (area.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(area.getDataCriacao()));
        }
        if (area.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(area.getDataAtualizacao()));
        }
        if (area.getCidade() != null) {
            Cidade cidade = ObjectUtils.clone(area.getCidade());
            cidade.setAreas(null);
            jgen.writeObjectField("cidade", cidade);
        }
        if (area.getLimitesGeograficos() != null) {
            jgen.writeArrayFieldStart("limites");
            for (LimiteArea limite : area.getLimitesGeograficos()) {
                jgen.writeObject(limite);
            }
            jgen.writeEndArray();
        }
        if (area.getSubareas() != null) {
            jgen.writeArrayFieldStart("subareas");
            for (Subarea subarea : area.getSubareas()) {
                jgen.writeObject(subarea);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
