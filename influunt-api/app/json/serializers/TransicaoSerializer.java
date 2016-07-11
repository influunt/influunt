package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.TabelaEntreVerdesTransicao;
import models.Transicao;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/7/16.
 */
public class TransicaoSerializer extends JsonSerializer<Transicao> {

    @Override
    public void serialize(Transicao transicao, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (transicao.getId() != null) {
            jgen.writeStringField("id", transicao.getId().toString());
        }
        if (transicao.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(transicao.getDataCriacao()));
        }
        if (transicao.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(transicao.getDataAtualizacao()));
        }

        if (transicao.getOrigem() != null) {
            jgen.writeObjectFieldStart("origem");
            jgen.writeStringField("id", transicao.getOrigem().getId().toString());
            jgen.writeEndObject();
        }

        if (transicao.getDestino() != null) {
            jgen.writeObjectFieldStart("destino");
            jgen.writeStringField("id", transicao.getDestino().getId().toString());
            jgen.writeEndObject();
        }

        if (transicao.getTabelaEntreVerdes() != null) {
            jgen.writeArrayFieldStart("tabelaEntreVerdes");
            for (TabelaEntreVerdesTransicao tabelaEntreVerdes : transicao.getTabelaEntreVerdes()) {
                jgen.writeObject(tabelaEntreVerdes);
            }
            jgen.writeEndArray();
        }

        if (transicao.getGrupoSemaforico() != null) {
            jgen.writeObjectFieldStart("grupoSemaforico");
            jgen.writeStringField("id", transicao.getGrupoSemaforico().getId().toString());
            jgen.writeStringField("tipo", transicao.getGrupoSemaforico().getTipo().toString());
            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
