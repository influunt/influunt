package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Estagio;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class EstagioSerializer extends JsonSerializer<Estagio> {
    @Override
    public void serialize(Estagio estagio, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", estagio.getId().toString());
        if (estagio.getImagem() != null) {
            jgen.writeObjectField("imagem", estagio.getImagem());
        }
        if (estagio.getDescricao() != null) {
            jgen.writeStringField("descricao", estagio.getDescricao());
        }
        if (estagio.getTempoMaximoPermanencia() != null) {
            jgen.writeNumberField("tempoMaximoPermanencia", estagio.getTempoMaximoPermanencia());
        }
        if (estagio.getDemandaPrioritaria() != null) {
            jgen.writeBooleanField("demandaPrioritaria", estagio.getDemandaPrioritaria());
        }
        if (estagio.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(estagio.getDataAtualizacao()));
        }
        if (estagio.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(estagio.getDataAtualizacao()));
        }

//        jgen.writeArrayFieldStart("areas");
//        for (Area area : estagio.getAreas()) {
//            jgen.writeStartObject();
//            jgen.writeStringField("id", area.getId().toString());
//            jgen.writeNumberField("descricao", area.getDescricao());
//            jgen.writeEndObject();
//        }
//        jgen.writeEndArray();


        jgen.writeEndObject();
    }
}
