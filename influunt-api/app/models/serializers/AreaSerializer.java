package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Area;
import models.LimiteArea;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AreaSerializer extends JsonSerializer<Area> {

    @Override
    public void serialize(Area area, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", area.getId().toString());
        jgen.writeNumberField("descricao", area.getDescricao());
        jgen.writeStringField("dataCriacao", area.getDataCriacao().toString());
        jgen.writeStringField("dataAtualizacao", area.getDataCriacao().toString());
        jgen.writeObjectField("cidade", area.getCidade());
        jgen.writeArrayFieldStart("limites");
        for (LimiteArea limite : area.getLimitesGeograficos()) {
            jgen.writeStartObject();
            jgen.writeStringField("id", limite.getId().toString());
            jgen.writeStringField("latitude", limite.getLatitude().toString());
            jgen.writeStringField("longitude", limite.getLongitude().toString());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
