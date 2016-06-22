package models.deserializers;

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
public class GrupoSemaforicoDeserializer extends JsonDeserializer<GrupoSemaforico>
{
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
                estagioGrupoSemaforicos.add(getEstagioGrupoSemaforico(estagioGSNode, grupoSemaforico));
            }
            grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);
        }

        return grupoSemaforico;
    }

    private EstagioGrupoSemaforico getEstagioGrupoSemaforico(JsonNode node, GrupoSemaforico grupoSemaforico) {
        EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico();

        if (node.has("id")) {
            estagioGrupoSemaforico.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("ativo")) {
            estagioGrupoSemaforico.setAtivo(node.get("ativo").asBoolean());
        }
        if (node.has("grupoSemaforico")) {
            estagioGrupoSemaforico.setGrupoSemaforico(grupoSemaforico);
        }
        if (node.has("estagio")) {
            estagioGrupoSemaforico.setEstagio(getEstagio(node.get("estagio"), estagioGrupoSemaforico));
        }

        return estagioGrupoSemaforico;
    }

    private Estagio getEstagio(JsonNode node, EstagioGrupoSemaforico estagioGrupoSemaforico) {
        Estagio estagio = new Estagio();
        if (node.has("id")) {
            estagio = Estagio.find.byId(UUID.fromString(node.get("id").asText()));
            if (estagio == null) {
                estagio = new Estagio();
            }
        }
        if (node.has("imagem")) {
            Imagem imagem = Json.fromJson(node.get("imagem"), Imagem.class);
            estagio.setImagem(imagem);
        }
        if (node.has("descricao")) {
            estagio.setDescricao(node.get("descricao").asText());
        }
        if (node.has("tempoMaximoPermanencia")) {
            estagio.setTempoMaximoPermanencia(node.get("tempoMaximoPermanencia").asInt());
        }
        if (node.has("demandaPrioritaria")) {
            estagio.setDemandaPrioritaria(node.get("demandaPrioritaria").asBoolean());
        }

        estagio.addEstagioGrupoSemaforico(estagioGrupoSemaforico);
        return estagio;
    }
}
