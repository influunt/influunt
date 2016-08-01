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
public class AnelDeserializer extends JsonDeserializer<Anel> {

    private ArrayList<Estagio> estagios;

    @Override
    public Anel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Anel anel = new Anel();

        if (node.has("ativo")) {
            anel.setAtivo(node.get("ativo").asBoolean());
        }

        if (node.has("id")) {
            anel.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("numeroSMEE")) {
            anel.setNumeroSMEE(node.get("numeroSMEE").asText());
        }
        if (node.has("descricao")) {
            anel.setDescricao(node.get("descricao").asText());
        }
        if (node.has("estagios")) {
            List<Estagio> estagios = new ArrayList<>();
            for (JsonNode estagioNode : node.get("estagios")) {
                estagios.add(Json.fromJson(estagioNode, Estagio.class));
            }
            anel.setEstagios(estagios);
        }
        if (node.has("ativo")) {
            anel.setAtivo(node.get("ativo").asBoolean());
        }
        if (node.has("posicao")) {
            anel.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("latitude")) {
            anel.setLatitude(node.get("latitude").asDouble());
        }
        if (node.has("longitude")) {
            anel.setLongitude(node.get("longitude").asDouble());
        }
        if (node.has("controlador")) {
            anel.setControlador(Json.fromJson(node.get("controlador"), Controlador.class));
        }
        if (node.has("croqui")) {
            anel.setCroqui(Json.fromJson(node.get("croqui"), Imagem.class));
        }
        if (node.has("gruposSemaforicos")) {
            List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
            for (JsonNode grupoNode : node.get("gruposSemaforicos")) {
                grupoSemaforicos.add(Json.fromJson(grupoNode, GrupoSemaforico.class));
            }
            anel.setGruposSemaforicos(grupoSemaforicos);
        }
        if (node.has("detectores")) {
            List<Detector> detectores = new ArrayList<Detector>();
            for (JsonNode detectorNode : node.get("detectores")) {
                detectores.add(Json.fromJson(detectorNode, Detector.class));
            }
            anel.setDetectores(detectores);
        }
        if (node.has("planos")) {
            List<Plano> planos = new ArrayList<Plano>();
            for (JsonNode planoNode : node.get("planos")) {
                planos.add(Json.fromJson(planoNode, Plano.class));
            }
            anel.setPlanos(planos);
        }

        return anel;
    }
}
