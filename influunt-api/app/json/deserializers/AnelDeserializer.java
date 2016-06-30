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
import java.util.*;

/**
 * Created by pedropires on 6/19/16.
 */
public class AnelDeserializer extends JsonDeserializer<Anel> {

    private ArrayList<Estagio> estagios = new ArrayList<Estagio>();

    @Override
    public Anel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

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
                Estagio estagio = null;
                for (JsonNode estagioNode : node.get("estagios")) {
                    estagio =  getEstagio(estagioNode);
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

            if (node.has("gruposSemaforicos")) {
                List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
                for (JsonNode grupoSemaforicoNode : node.get("gruposSemaforicos")) {
                    grupoSemaforicos.add(getGrupoSemaforico(grupoSemaforicoNode, anel));
                }
                anel.setGruposSemaforicos(grupoSemaforicos);
            }
        }


        return anel;
    }

    public GrupoSemaforico getGrupoSemaforico(JsonNode node, Anel anel) {
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
        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }
        if(node.has("verdesConflitantes")) {
            List<GrupoSemaforico> verdesConflitantes = new ArrayList<GrupoSemaforico>();
            for (JsonNode verdesConflitantesGSNode : node.get("verdesConflitantes")) {
                verdesConflitantes.add(getGrupoSemaforico(verdesConflitantesGSNode, anel));
            }
            grupoSemaforico.setVerdesConflitantes(verdesConflitantes);
        }
        if (node.has("estagioGrupoSemaforicos")) {
            List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<EstagioGrupoSemaforico>();
            for (JsonNode estagioGSNode : node.get("estagioGrupoSemaforicos")) {
                estagioGrupoSemaforicos.add(getEstagioGrupoSemaforico(estagioGSNode, grupoSemaforico));
            }
            grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);
        }
        grupoSemaforico.setAnel(anel);
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
            Estagio estagio = getEstagio(node.get("estagio"));
            estagio.addEstagioGrupoSemaforico(estagioGrupoSemaforico);
            estagioGrupoSemaforico.setEstagio(estagio);
        }

        return estagioGrupoSemaforico;
    }

    private Estagio getEstagio(JsonNode node) {
        Estagio estagio = new Estagio();
        if (node.has("id")) {
            Optional estagioOptional = estagios.stream().filter(estagioAux -> estagioAux.getId().equals(UUID.fromString(node.get("id").asText()))).findFirst();
            if(estagioOptional.isPresent()) {
                estagio = (Estagio) estagioOptional.get();
                return  estagio;
            }

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
        estagios.add(estagio);
        return estagio;
    }
}
