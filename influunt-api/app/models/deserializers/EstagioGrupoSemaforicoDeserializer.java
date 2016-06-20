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
import java.util.UUID;

import static play.libs.Json.fromJson;

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
            GrupoSemaforico grupo = fromJson(node.get("grupoSemaforico"), GrupoSemaforico.class);
            if (node.get("grupoSemaforico").has("tipo")) {
                grupo.setTipo(TipoGrupoSemaforico.valueOf(node.get("grupoSemaforico").get("tipo").asText()));
            }
            estagioGrupoSemaforico.setGrupoSemaforico(grupo);
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
//            estagioGrupoSemaforico.setEstagio();
        }

        return estagioGrupoSemaforico;
    }
}
