package models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Estagio;
import models.EstagioGrupoSemaforico;
import models.GrupoSemaforico;
import models.TipoGrupoSemaforico;
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
                EstagioGrupoSemaforico grp = new EstagioGrupoSemaforico();


//                        Json.fromJson(estagioGSNode, EstagioGrupoSemaforico.class);
//                grp.setGrupoSemaforico(grupoSemaforico);
//                estagioGrupoSemaforicos.add(grp);
            }
            grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);
        }

        return grupoSemaforico;
    }

    private EstagioGrupoSemaforico getEstagioGrupoSemafgorico(JsonNode node, GrupoSemaforico grupoSemaforico) {
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
//            Estagio estagio = new Estagio();
//            JsonNode estagioNode = node.get("estagio");
//            if (estagioNode.has("descricao")) {
//                estagio.setDescricao(estagioNode.get("descricao").asText());
//            }
//            if (estagioNode.has("tempoMaximoPermanencia")) {
//                estagio.setTempoMaximoPermanencia(estagioNode.get("tempoMaximoPermanencia").asInt());
//            }
//            if (estagioNode.has("demandaPrioritaria")) {
//                estagio.setDemandaPrioritaria(estagioNode.get("demandaPrioritaria").asBoolean());
//            }
//            if (estagioNode.has("imagem")) {
//                Imagem img = new Imagem();
//                img.setId(UUID.fromString(estagioNode.get("imagem").get("id").asText()));
//                estagio.setImagem(img);
//            }
            Estagio estagio = Json.fromJson(node.get("estagio"), Estagio.class);
        }

        return estagioGrupoSemaforico;
    }
}
