package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.TabelaEntreVerdesTransicao;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/7/16.
 */
public class TabelaEntreVerdesTransicaoSerializer extends JsonSerializer<TabelaEntreVerdesTransicao> {

    @Override
    public void serialize(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (tabelaEntreVerdesTransicao.getId() != null) {
            jgen.writeStringField("id", tabelaEntreVerdesTransicao.getId().toString());
        }
        if (tabelaEntreVerdesTransicao.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(tabelaEntreVerdesTransicao.getDataCriacao()));
        }
        if (tabelaEntreVerdesTransicao.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(tabelaEntreVerdesTransicao.getDataAtualizacao()));
        }
        if (tabelaEntreVerdesTransicao.getTempoAmarelo() != null) {
            jgen.writeStringField("tempoAmarelo", tabelaEntreVerdesTransicao.getTempoAmarelo().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoVermelhoIntermitente() != null) {
            jgen.writeStringField("tempoVermelhoIntermitente", tabelaEntreVerdesTransicao.getTempoVermelhoIntermitente().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoVermelhoLimpeza() != null) {
            jgen.writeStringField("tempoVermelhoLimpeza", tabelaEntreVerdesTransicao.getTempoVermelhoLimpeza().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoAtrasoGrupo() != null) {
            jgen.writeStringField("tempoAtrasoGrupo", tabelaEntreVerdesTransicao.getTempoAtrasoGrupo().toString());
        }
        if (tabelaEntreVerdesTransicao.getTransicao() != null) {
            jgen.writeObjectFieldStart("transicao");
            jgen.writeStringField("id", tabelaEntreVerdesTransicao.getTransicao().getId().toString());
            if (tabelaEntreVerdesTransicao.getTransicao().getGrupoSemaforico() != null) {
                jgen.writeObjectFieldStart("grupoSemaforico");
                jgen.writeStringField("id", tabelaEntreVerdesTransicao.getTransicao().getGrupoSemaforico().getId().toString());
                jgen.writeStringField("tipo", tabelaEntreVerdesTransicao.getTransicao().getGrupoSemaforico().getTipo().toString());
                jgen.writeEndObject();
            }
            jgen.writeEndObject();
        }
        if (tabelaEntreVerdesTransicao.getTabelaEntreVerdes() != null) {
            jgen.writeObjectField("tabelaEntreVerdes", tabelaEntreVerdesTransicao.getTabelaEntreVerdes());
        }
        jgen.writeEndObject();
    }
}
