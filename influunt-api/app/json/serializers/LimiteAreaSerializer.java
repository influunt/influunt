package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.LimiteArea;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/7/16.
 */
public class LimiteAreaSerializer extends JsonSerializer<LimiteArea> {
    @Override
    public void serialize(LimiteArea limiteArea, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (limiteArea.getId() == null) {
            jgen.writeStringField("id", null);
        } else {
            jgen.writeStringField("id", limiteArea.getId().toString());
        }
        if (limiteArea.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(limiteArea.getDataCriacao()));
        }
        if (limiteArea.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(limiteArea.getDataAtualizacao()));
        }
        if (limiteArea.getLatitude() != null) {
            jgen.writeStringField("latitude", limiteArea.getLatitude().toString());
        }
        if (limiteArea.getLongitude() != null) {
            jgen.writeStringField("longitude", limiteArea.getLongitude().toString());
        }

        jgen.writeEndObject();
    }
}
