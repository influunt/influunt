package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Fabricante;
import models.ModeloControlador;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 6/19/16.
 */
public class FabricanteSerializer extends JsonSerializer<Fabricante> {

    @Override
    public void serialize(Fabricante fabricante, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", fabricante.getId().toString());
        if (fabricante.getNome() != null) {
            jgen.writeStringField("nome", fabricante.getNome());
        }
        if (fabricante.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(fabricante.getDataCriacao()));
        }
        if (fabricante.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(fabricante.getDataAtualizacao()));
        }

        jgen.writeArrayFieldStart("modelos");
        for (ModeloControlador modelo : fabricante.getModelos()) {
            jgen.writeStartObject();
            jgen.writeStringField("id", modelo.getId().toString());
            jgen.writeStringField("descricao", modelo.getDescricao());
            jgen.writeObjectField("configuracao", modelo.getConfiguracao());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();

    }
}
