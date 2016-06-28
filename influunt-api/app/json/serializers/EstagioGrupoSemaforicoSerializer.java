package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.EstagioGrupoSemaforico;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class EstagioGrupoSemaforicoSerializer extends JsonSerializer<EstagioGrupoSemaforico> {
    @Override
    public void serialize(EstagioGrupoSemaforico estagioGrupo, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (estagioGrupo.getId() != null) {
            jgen.writeStringField("id", estagioGrupo.getId().toString());
        }
        if (estagioGrupo.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(estagioGrupo.getDataAtualizacao()));
        }
        if (estagioGrupo.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(estagioGrupo.getDataAtualizacao()));
        }
        if (estagioGrupo.getAtivo() != null) {
            jgen.writeBooleanField("ativo", estagioGrupo.getAtivo());
        }
        if (estagioGrupo.getEstagio() != null) {
            jgen.writeObjectField("estagio", estagioGrupo.getEstagio());
        }

        if (estagioGrupo.getGrupoSemaforico() != null) {
            jgen.writeObjectFieldStart("grupoSemaforico");
            jgen.writeStringField("id", estagioGrupo.getGrupoSemaforico().getId().toString());
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
