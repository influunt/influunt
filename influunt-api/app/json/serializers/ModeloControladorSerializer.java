package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Fabricante;
import models.ModeloControlador;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 6/19/16.
 */
public class ModeloControladorSerializer extends JsonSerializer<ModeloControlador> {

    @Override
    public void serialize(ModeloControlador modeloControlador, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (modeloControlador.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", modeloControlador.getId().toString());
        }
        if (modeloControlador.getDescricao() != null) {
            jgen.writeStringField("descricao", modeloControlador.getDescricao());
        }
        if (modeloControlador.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(modeloControlador.getDataCriacao()));
        }
        if (modeloControlador.getDataCriacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(modeloControlador.getDataCriacao()));
        }
        if (modeloControlador.getFabricante() != null) {
            Fabricante fabricanteAux = ObjectUtils.clone(modeloControlador.getFabricante());
            fabricanteAux.setModelos(null);
            jgen.writeObjectField("fabricante", fabricanteAux);
        }

        jgen.writeEndObject();

    }
}
