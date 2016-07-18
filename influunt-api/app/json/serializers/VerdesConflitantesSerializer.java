package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.GrupoSemaforico;
import models.VerdesConflitantes;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/7/16.
 */
public class VerdesConflitantesSerializer extends JsonSerializer<VerdesConflitantes> {

    @Override
    public void serialize(VerdesConflitantes verdesConflitantes, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (verdesConflitantes.getId() != null) {
            jgen.writeStringField("id", verdesConflitantes.getId().toString());
        }
        if (verdesConflitantes.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(verdesConflitantes.getDataCriacao()));
        }
        if (verdesConflitantes.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(verdesConflitantes.getDataAtualizacao()));
        }
        if (verdesConflitantes.getOrigem() != null) {
            GrupoSemaforico origemAux = ObjectUtils.clone(verdesConflitantes.getOrigem());
            origemAux.setVerdesConflitantesOrigem(null);
            origemAux.setVerdesConflitantesDestino(null);
            jgen.writeObjectField("origem", origemAux);

        }
        if (verdesConflitantes.getDestino() != null) {
            GrupoSemaforico destinoAux = ObjectUtils.clone(verdesConflitantes.getDestino());
            destinoAux.setVerdesConflitantesOrigem(null);
            destinoAux.setVerdesConflitantesDestino(null);
            jgen.writeObjectField("destino", destinoAux);
        }
        jgen.writeEndObject();
    }
}
