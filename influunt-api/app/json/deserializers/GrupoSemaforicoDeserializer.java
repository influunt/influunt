package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import models.*;

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
        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }

        if (node.has("anel")) {
            grupoSemaforico.setAnel(getAnel(node.get("anel"), grupoSemaforico));
        }

        if (node.has("transicoes") ) {
            List<Transicao> transicoes = new ArrayList<Transicao>();
            for (JsonNode nodeTransicao : node.get("transicoes")) {
                transicoes.add(Json.fromJson(nodeTransicao, Transicao.class));
            }
            grupoSemaforico.setTransicoes(transicoes);
        }

        return grupoSemaforico;
    }

    private Anel getAnel(JsonNode node, GrupoSemaforico grupoSemaforico) {
        Anel anel = new Anel();
        anel.setAtivo(node.get("ativo").asBoolean());

        if (anel.isAtivo()) {
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
                List<Estagio> estagios = new ArrayList<Estagio>();
                Estagio estagio = null;
                for (JsonNode estagioNode : node.get("estagios")) {
                    estagio = Json.fromJson(estagioNode, Estagio.class);
                    estagio.setAnel(anel);
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
            if (node.has("quantidadeGrupoPedestre")) {
                anel.setQuantidadeGrupoPedestre(node.get("quantidadeGrupoPedestre").asInt());
            }
            if (node.has("quantidadeGrupoVeicular")) {
                anel.setQuantidadeGrupoVeicular(node.get("quantidadeGrupoVeicular").asInt());
            }
            if (node.has("quantidadeDetectorPedestre")) {
                anel.setQuantidadeDetectorPedestre(node.get("quantidadeDetectorPedestre").asInt());
            }
            if (node.has("quantidadeDetectorVeicular")) {
                anel.setQuantidadeDetectorVeicular(node.get("quantidadeDetectorVeicular").asInt());
            }

        }

        return anel;
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
