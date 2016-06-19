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
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by pedropires on 6/19/16.
 */
public class EstagioGrupoSemaforicoDeserializer extends JsonDeserializer<EstagioGrupoSemaforico> {
    @Override
    public EstagioGrupoSemaforico deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico();

        if (node.has("id")) {
            estagioGrupoSemaforico.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("ativo")) {
            estagioGrupoSemaforico.setAtivo(node.get("ativo").asBoolean());
        }
        if (node.has("grupoSemaforico")) {
            estagioGrupoSemaforico.setGrupoSemaforico(Json.fromJson(node.get("grupoSemaforico"), GrupoSemaforico.class));
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
            estagioGrupoSemaforico.setEstagio(Json.fromJson(node.get("estagio"), Estagio.class));
        }

        return estagioGrupoSemaforico;
    }
}
