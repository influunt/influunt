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
 * Created by pedropires on 6/19/16.
 */
public class GrupoSemaforicoDeserializer extends JsonDeserializer<GrupoSemaforico> {
    @Override
    public GrupoSemaforico deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        GrupoSemaforico grupoSemaforico = new GrupoSemaforico();

        if (node.has("id")) {
            grupoSemaforico.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("posicao")) {
            grupoSemaforico.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("tipo")) {
            grupoSemaforico.setTipo(TipoGrupoSemaforico.valueOf(node.get("tipo").asText()));
        }

        if (node.has("estagioGrupoSemaforicos")) {
            List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<EstagioGrupoSemaforico>();
            for (JsonNode estagioGSNode : node.get("estagioGrupoSemaforicos")) {
                estagioGrupoSemaforicos.add(Json.fromJson(estagioGSNode, EstagioGrupoSemaforico.class));
            }
            grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);
        }

        if (node.has("verdesConflitantesOrigem")) {
            List<VerdesConflitantes> verdesConflitantes = new ArrayList<VerdesConflitantes>();
            for (JsonNode verdeNode : node.get("verdesConflitantesOrigem")) {
                verdesConflitantes.add(Json.fromJson(verdeNode, VerdesConflitantes.class));
            }
            grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantes);
        }

        if (node.has("verdesConflitantesDestino")) {
            List<VerdesConflitantes> verdesConflitantes = new ArrayList<VerdesConflitantes>();
            for (JsonNode verdeNode : node.get("verdesConflitantesDestino")) {
                verdesConflitantes.add(Json.fromJson(verdeNode, VerdesConflitantes.class));
            }
            grupoSemaforico.setVerdesConflitantesDestino(verdesConflitantes);
        }

        if (node.has("tabelasEntreVerdes")) {
            List<TabelaEntreVerdes> tabelasEntreVerdes = new ArrayList<TabelaEntreVerdes>();
            for (JsonNode tabelasEntreVerdesNode : node.get("tabelasEntreVerdes")) {
                tabelasEntreVerdes.add(Json.fromJson(tabelasEntreVerdesNode, TabelaEntreVerdes.class));
            }
            grupoSemaforico.setTabelasEntreVerdes(tabelasEntreVerdes);
        }

        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }

        if (node.has("anel")) {
            grupoSemaforico.setAnel(Json.fromJson(node.get("anel"), Anel.class));
        }

        if (node.has("transicoes")) {
            List<Transicao> transicoes = new ArrayList<Transicao>();
            for (JsonNode nodeTransicao : node.get("transicoes")) {
                transicoes.add(Json.fromJson(nodeTransicao, Transicao.class));
            }
            grupoSemaforico.setTransicoes(transicoes);
        }

        return grupoSemaforico;
    }
}
