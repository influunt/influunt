package json.serializers2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.TransicaoProibida;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/5/16.
 */
public class TransicaoProibidaSerializer extends JsonSerializer<TransicaoProibida> {
    @Override
    public void serialize(TransicaoProibida transicaoProibida, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (transicaoProibida.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", transicaoProibida.getId().toString());
        }
        if (transicaoProibida.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(transicaoProibida.getDataCriacao()));
        }
        if (transicaoProibida.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(transicaoProibida.getDataAtualizacao()));
        }

        if(transicaoProibida.getOrigem() != null) {
            jgen.writeObjectFieldStart("origem");
            jgen.writeStringField("id", transicaoProibida.getOrigem().getId().toString());
            jgen.writeEndObject();
        }

        if(transicaoProibida.getDestino() != null) {
            jgen.writeObjectFieldStart("destino");
            jgen.writeStringField("id", transicaoProibida.getDestino().getId().toString());
            jgen.writeEndObject();
        }

        if(transicaoProibida.getAlternativo() != null) {
            jgen.writeObjectFieldStart("alternativo");
            jgen.writeStringField("id", transicaoProibida.getAlternativo().getId().toString());
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}

