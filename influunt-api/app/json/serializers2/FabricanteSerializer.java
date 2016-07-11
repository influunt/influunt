package json.serializers2;

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

        if (fabricante.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", fabricante.getId().toString());
        }
        if (fabricante.getNome() != null) {
            jgen.writeStringField("nome", fabricante.getNome());
        }
        if (fabricante.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(fabricante.getDataCriacao()));
        }
        if (fabricante.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(fabricante.getDataAtualizacao()));
        }
        if (fabricante.getModelos() != null) {
            jgen.writeArrayFieldStart("modelos");
            for (ModeloControlador modelo : fabricante.getModelos()) {
                ModeloControlador modeloAux = null;
                try {
                    modeloAux = (ModeloControlador) modelo.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                modeloAux.setFabricante(null);
                jgen.writeObject(modeloAux);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();

    }
}
