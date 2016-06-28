package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.EstagioGrupoSemaforico;
import models.GrupoSemaforico;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class GrupoSemaforicoSerializer extends JsonSerializer<GrupoSemaforico> {
    @Override
    public void serialize(GrupoSemaforico grupoSemaforico, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (grupoSemaforico.getId() != null) {
            jgen.writeStringField("id", grupoSemaforico.getId().toString());
        }
        if (grupoSemaforico.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(grupoSemaforico.getDataCriacao()));
        }
        if (grupoSemaforico.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(grupoSemaforico.getDataAtualizacao()));
        }
        if (grupoSemaforico.getTipo() != null) {
            jgen.writeStringField("tipo", grupoSemaforico.getTipo().toString());
        }
        if(grupoSemaforico.getPosicao() != null) {
            jgen.writeNumberField("posicao", grupoSemaforico.getPosicao());
        }
        if (grupoSemaforico.getDescricao() != null) {
            jgen.writeStringField("descricao", grupoSemaforico.getDescricao());
        }

        jgen.writeArrayFieldStart("estagioGrupoSemaforicos");
        for (EstagioGrupoSemaforico estagio : grupoSemaforico.getEstagioGrupoSemaforicos()) {
            jgen.writeObject(estagio);
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
