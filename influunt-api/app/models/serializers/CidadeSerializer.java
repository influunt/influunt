package models.serializers;

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
        jgen.writeStringField("id", cidade.getId().toString());
        if (cidade.getNome() != null) {
            jgen.writeStringField("nome", cidade.getNome());
        }
        if (cidade.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", cidade.getDataCriacao().toString());
        }
        if (cidade.getDataCriacao() != null) {
            jgen.writeStringField("dataAtualizacao", cidade.getDataCriacao().toString());
        }
        jgen.writeArrayFieldStart("areas");
        for (Area area : cidade.getAreas()) {
            jgen.writeStartObject();
            jgen.writeStringField("id", area.getId().toString());
            jgen.writeNumberField("descricao", area.getDescricao());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
