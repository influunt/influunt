package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.GrupoSemaforico;
import models.TabelaEntreVerdes;
import models.TabelaEntreVerdesTransicao;
import models.Transicao;
import org.apache.commons.lang3.ObjectUtils;

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
        if (tabelaEntreVerdesTransicao.getTabelaEntreVerdes() != null) {
            TabelaEntreVerdes tabelaEntreVerdesAux = ObjectUtils.clone(tabelaEntreVerdesTransicao.getTabelaEntreVerdes());
            tabelaEntreVerdesAux.setTransicoes(null);
            tabelaEntreVerdesAux.setGrupoSemaforico(null);
            jgen.writeObjectField("tabelaEntreVerdes", tabelaEntreVerdesAux);
        }
        if (tabelaEntreVerdesTransicao.getTransicao() != null) {
            Transicao transicaoAux = ObjectUtils.clone(tabelaEntreVerdesTransicao.getTransicao());
            GrupoSemaforico grupoSemaforicoAux = new GrupoSemaforico();
            grupoSemaforicoAux.setId(transicaoAux.getGrupoSemaforico().getId());
            grupoSemaforicoAux.setTipo(transicaoAux.getGrupoSemaforico().getTipo());
            transicaoAux.setTabelaEntreVerdes(null);
            transicaoAux.setGrupoSemaforico(grupoSemaforicoAux);
            jgen.writeObjectField("transicao", transicaoAux);
        }
        jgen.writeEndObject();
    }
}
