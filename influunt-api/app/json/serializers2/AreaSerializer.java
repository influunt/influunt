package json.serializers2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Area;
import models.Cidade;
import models.LimiteArea;

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
        jgen.writeNumberField("descricao", area.getDescricao());
        if (area.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(area.getDataCriacao()));
        }
        if (area.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(area.getDataAtualizacao()));
        }
        if (area.getCidade() != null) {
            Cidade cidade = null;
            try {
                cidade = (Cidade) area.getCidade().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
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
        jgen.writeEndObject();
    }
}
