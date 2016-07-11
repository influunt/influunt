package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Area;
import models.Cidade;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class CidadeSerializer extends JsonSerializer<Cidade> {


    @Override
    public void serialize(Cidade cidade, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (cidade.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", cidade.getId().toString());
        }
        if (cidade.getNome() != null) {
            jgen.writeStringField("nome", cidade.getNome());
        }
        if (cidade.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(cidade.getDataCriacao()));
        }
        if (cidade.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(cidade.getDataAtualizacao()));
        }
        jgen.writeArrayFieldStart("areas");
        for (Area area : cidade.getAreas()) {
            jgen.writeObject(area);
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
