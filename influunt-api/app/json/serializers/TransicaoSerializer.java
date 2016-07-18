package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Estagio;
import models.GrupoSemaforico;
import models.TabelaEntreVerdesTransicao;
import models.Transicao;
import org.apache.commons.lang3.ObjectUtils;

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
            Estagio origem = transicao.getOrigem();
            jgen.writeObjectFieldStart("origem");
            jgen.writeStringField("id", origem.getId().toString());
            jgen.writeStringField("descricao", origem.getDescricao());
            if (origem.getImagem() != null) {
                jgen.writeObjectFieldStart("imagem");
                jgen.writeStringField("id", origem.getImagem().getId().toString());
                jgen.writeEndObject();
            }
            if (origem.getPosicao() != null) {
                jgen.writeNumberField("posicao", origem.getPosicao());
            }
            jgen.writeEndObject();
        }

        if (transicao.getDestino() != null) {
            Estagio destino = transicao.getDestino();
            jgen.writeObjectFieldStart("destino");
            jgen.writeStringField("id", destino.getId().toString());
            jgen.writeStringField("descricao", destino.getDescricao());
            if (destino.getImagem() != null) {
                jgen.writeObjectFieldStart("imagem");
                jgen.writeStringField("id", destino.getImagem().getId().toString());
                jgen.writeEndObject();
            }
            if (destino.getPosicao() != null) {
                jgen.writeNumberField("posicao", destino.getPosicao());
            }
            jgen.writeEndObject();
        }

        if (transicao.getTabelaEntreVerdes() != null) {
            jgen.writeArrayFieldStart("tabelaEntreVerdesTransicoes");
            for (TabelaEntreVerdesTransicao tabelaEntreVerdes : transicao.getTabelaEntreVerdes()) {
                jgen.writeObject(tabelaEntreVerdes);
            }
            jgen.writeEndArray();
        }

        if (transicao.getGrupoSemaforico() != null) {
            GrupoSemaforico grupo = ObjectUtils.clone(transicao.getGrupoSemaforico());
            grupo.setTransicoes(null);
            grupo.setEstagioGrupoSemaforicos(null);
            jgen.writeObjectField("grupoSemaforico", grupo);
        }

        jgen.writeEndObject();
    }
}
