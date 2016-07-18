package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/15/16.
 */
public class PlanoDeserializer extends JsonDeserializer<Plano> {

    @Override
    public Plano deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Plano plano = new Plano();

        if (node.has("id")) {
            plano.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("posicao")) {
            plano.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("tempoCiclo")) {
            plano.setTempoCiclo(node.get("tempoCiclo").asInt());
        }
        if (node.has("defasagem")) {
            plano.setDefasagem(node.get("defasagem").asInt());
        }
        if (node.has("posicaoTabelaEntreVerde")) {
            plano.setPosicaoTabelaEntreVerde(node.get("posicaoTabelaEntreVerde").asInt());
        }
        if (node.has("modoOperacao")) {
            plano.setModoOperacao(ModoOperacaoPlano.valueOf(node.get("modoOperacao").asText()));
        }
        if (node.has("agrupamento")) {
            plano.setAgrupamento(Json.fromJson(node.get("agrupamento"), Agrupamento.class));
        }
        if (node.has("anel")) {
            plano.setAnel(Json.fromJson(node.get("anel"), Anel.class));
        }
        if (node.has("estagiosPlanos")) {
            List<EstagioPlano> estagioPlanos = new ArrayList<EstagioPlano>();
            for (JsonNode estagioGSNode : node.get("estagiosPlanos")) {
                estagioPlanos.add(Json.fromJson(estagioGSNode, EstagioPlano.class));
            }
            plano.setEstagiosPlanos(estagioPlanos);
        }
        if (node.has("gruposSemaforicosPlanos")) {
            List<GrupoSemaforicoPlano> grupoSemaforicos = new ArrayList<GrupoSemaforicoPlano>();
            for (JsonNode grupoNode : node.get("gruposSemaforicosPlanos")) {
                grupoSemaforicos.add(Json.fromJson(grupoNode, GrupoSemaforicoPlano.class));
            }
            plano.setGruposSemaforicosPlanos(grupoSemaforicos);
        }
        return plano;
    }
}
