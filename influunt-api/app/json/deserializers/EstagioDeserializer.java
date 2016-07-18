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
public class EstagioDeserializer extends JsonDeserializer<Estagio> {
    @Override
    public Estagio deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Estagio estagio = new Estagio();
        JsonNode id = node.get("id");
        if (id != null) {
            estagio.setId(UUID.fromString(id.asText()));
        }
        if (node.get("descricao") != null) {
            estagio.setDescricao(node.get("descricao").asText());
        }
        if (node.get("tempoMaximoPermanencia") != null) {
            estagio.setTempoMaximoPermanencia(node.get("tempoMaximoPermanencia").asInt());
        }
        if (node.get("demandaPrioritaria") != null) {
            estagio.setDemandaPrioritaria(node.get("demandaPrioritaria").asBoolean());
        }
        if (node.get("posicao") != null) {
            estagio.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("imagem")) {
            Imagem img = new Imagem();
            img.setId(UUID.fromString(node.get("imagem").get("id").asText()));
            estagio.setImagem(img);
        }
        if (node.has("detector")) {
            estagio.setDetector(Json.fromJson(node.get("detector"), Detector.class));
        }
        if (node.has("estagiosGruposSemaforicos")) {
            List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<EstagioGrupoSemaforico>();
            for (JsonNode estagioGSNode : node.get("estagiosGruposSemaforicos")) {
                estagioGrupoSemaforicos.add(Json.fromJson(estagioGSNode, EstagioGrupoSemaforico.class));
            }
            estagio.setEstagiosGruposSemaforicos(estagioGrupoSemaforicos);
        }

        if (node.has("origemDeTransicoesProibidas")) {
            List<TransicaoProibida> origens = new ArrayList<TransicaoProibida>();
            for (JsonNode origemGSNode : node.get("origemDeTransicoesProibidas")) {
                origens.add(Json.fromJson(origemGSNode, TransicaoProibida.class));
            }
            estagio.setOrigemDeTransicoesProibidas(origens);
        }

        if (node.has("destinoDeTransicoesProibidas")) {
            List<TransicaoProibida> destinos = new ArrayList<TransicaoProibida>();
            for (JsonNode destinoGSNode : node.get("destinoDeTransicoesProibidas")) {
                destinos.add(Json.fromJson(destinoGSNode, TransicaoProibida.class));
            }
            estagio.setDestinoDeTransicoesProibidas(destinos);
        }

        if (node.has("alternativaDeTransicoesProibidas")) {
            List<TransicaoProibida> alternativos = new ArrayList<TransicaoProibida>();
            for (JsonNode alternativoGSNode : node.get("alternativaDeTransicoesProibidas")) {
                alternativos.add(Json.fromJson(alternativoGSNode, TransicaoProibida.class));
            }
            estagio.setAlternativaDeTransicoesProibidas(alternativos);
        }

        return estagio;
    }
}
