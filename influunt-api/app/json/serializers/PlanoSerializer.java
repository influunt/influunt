package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.*;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/15/16.
 */
public class PlanoSerializer extends JsonSerializer<Plano> {

    @Override
    public void serialize(Plano plano, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (plano.getId() != null) {
            jgen.writeStringField("id", plano.getId().toString());
        }
        if (plano.getPosicao() != null) {
            jgen.writeNumberField("posicao", plano.getPosicao());
        }
        if (plano.getTempoCiclo() != null) {
            jgen.writeNumberField("tempoCiclo", plano.getTempoCiclo());
        }
        if (plano.getDefasagem() != null) {
            jgen.writeNumberField("defasagem", plano.getDefasagem());
        }
        if (plano.getPosicaoTabelaEntreVerde() != null) {
            jgen.writeNumberField("posicaoTabelaEntreVerde", plano.getPosicaoTabelaEntreVerde());
        }
        if (plano.getModoOperacao() != null) {
            jgen.writeStringField("modoOperacao", plano.getModoOperacao().toString());
        }
        if (plano.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(plano.getDataCriacao()));
        }
        if (plano.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(plano.getDataAtualizacao()));
        }
        if (plano.getAnel() != null) {
            Anel anel = ObjectUtils.clone(plano.getAnel());
            anel.setPlanos(null);
            //anel.setGruposSemaforicos(null);
            anel.setDetectores(null);
            anel.setControlador(null);
            jgen.writeObjectField("anel", anel);
        }
        if (plano.getAgrupamento() != null) {
            Agrupamento agrupamento = ObjectUtils.clone(plano.getAgrupamento());
            agrupamento.setControladores(null);
            jgen.writeObjectField("agrupamento", agrupamento);
        }
        if (plano.getEstagiosPlanos() != null) {
            jgen.writeArrayFieldStart("estagiosPlanos");
            for (EstagioPlano estagioPlano : plano.getEstagiosPlanos()) {
                EstagioPlano estagioPlanoAux = ObjectUtils.clone(estagioPlano);
                Plano planoAux = ObjectUtils.clone(estagioPlanoAux.getPlano());
                planoAux.setGruposSemaforicosPlanos(null);
                planoAux.setAnel(null);
                planoAux.setEstagiosPlanos(null);
                estagioPlanoAux.setPlano(planoAux);

                Estagio estagioAux = ObjectUtils.clone(estagioPlanoAux.getEstagio());
                estagioPlanoAux.setEstagio(estagioAux);
                jgen.writeObject(estagioPlanoAux);
            }
            jgen.writeEndArray();
        }
        if (plano.getGruposSemaforicosPlanos() != null) {
            jgen.writeArrayFieldStart("gruposSemaforicosPlanos");
            for (GrupoSemaforicoPlano grupoSemaforicoPlano : plano.getGruposSemaforicosPlanos()) {
                GrupoSemaforicoPlano grupoSemaforicoPlanoAux = ObjectUtils.clone(grupoSemaforicoPlano);
                Plano planoAux = ObjectUtils.clone(grupoSemaforicoPlanoAux.getPlano());
                planoAux.setGruposSemaforicosPlanos(null);
                planoAux.setAnel(null);
                planoAux.setEstagiosPlanos(null);
                grupoSemaforicoPlanoAux.setPlano(planoAux);
                jgen.writeObject(grupoSemaforicoPlanoAux);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
