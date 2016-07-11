package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.*;

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
        if (grupoSemaforico.getPosicao() != null) {
            jgen.writeNumberField("posicao", grupoSemaforico.getPosicao());
        }
        if (grupoSemaforico.getDescricao() != null) {
            jgen.writeStringField("descricao", grupoSemaforico.getDescricao());
        }
        if (grupoSemaforico.getAnel() != null) {
            Anel anel = null;
            try {
                anel = (Anel) grupoSemaforico.getAnel().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            anel.setGruposSemaforicos(null);
            jgen.writeObjectField("anel", anel);
        }

        if (grupoSemaforico.getVerdesConflitantesOrigem() != null) {
            jgen.writeArrayFieldStart("verdesConflitantesOrigem");
            for (VerdesConflitantes verdesConflitantes : grupoSemaforico.getVerdesConflitantesOrigem()) {
                jgen.writeObject(verdesConflitantes);
            }
            jgen.writeEndArray();
        }

        if (grupoSemaforico.getVerdesConflitantesDestino() != null) {
            jgen.writeArrayFieldStart("verdesConflitantesDestino");
            for (VerdesConflitantes verdesConflitantes : grupoSemaforico.getVerdesConflitantesDestino()) {
                jgen.writeObject(verdesConflitantes);
            }
            jgen.writeEndArray();
        }

        if (grupoSemaforico.getEstagioGrupoSemaforicos() != null) {
            jgen.writeArrayFieldStart("estagioGrupoSemaforicos");
            for (EstagioGrupoSemaforico estagio : grupoSemaforico.getEstagioGrupoSemaforicos()) {
                EstagioGrupoSemaforico estagioAux = null;
                try {
                    estagioAux = (EstagioGrupoSemaforico) estagio.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                estagioAux.setGrupoSemaforico(null);
                jgen.writeObject(estagioAux);
            }
            jgen.writeEndArray();
        }

        if (grupoSemaforico.getTransicoes() != null) {
            jgen.writeArrayFieldStart("transicoes");
            for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                Transicao transicaoAux = null;
                try {
                    transicaoAux = (Transicao) transicao.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                transicaoAux.setGrupoSemaforico(null);
                jgen.writeObject(transicaoAux);
            }
            jgen.writeEndArray();
        }

        if (grupoSemaforico.getTabelasEntreVerdes() != null) {
            jgen.writeArrayFieldStart("tabelasEntreVerdes");
            for (TabelaEntreVerdes tabelaEntreVerdes : grupoSemaforico.getTabelasEntreVerdes()) {
                TabelaEntreVerdes tabelaEntreVerdesAux = null;
                try {
                    tabelaEntreVerdesAux = (TabelaEntreVerdes) tabelaEntreVerdes.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                tabelaEntreVerdesAux.setGrupoSemaforico(null);
                jgen.writeObject(tabelaEntreVerdesAux);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
