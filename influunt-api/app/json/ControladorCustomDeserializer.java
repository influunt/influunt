package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import play.libs.Json;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by rodrigosol on 7/29/16.
 */
public class ControladorCustomDeserializer {
    private Controlador controlador = new Controlador();
    private Map<String,Map<String,Object>> models;

    private Map<String,Anel> aneisCache;
    private Map<String,Estagio> estagiosCache;
    private Map<String,GrupoSemaforico> gruposSemaforicosCache;
    private Map<String,Detector> detectoresCache;
    private Map<String,TransicaoProibida> transicaoProibidaCache;
    private Map<String,EstagioGrupoSemaforico> estagioGrupoSemaforicoCache;
    private Map<String,VerdesConflitantes> verdesConflitantesCache;
    private Map<String,Transicao> transicaoCache;
    private Map<String,TabelaEntreVerdes> tabelasEntreVerdesCache;
    private Map<String,TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicaoCache;
    private Map<String,Imagem> imagensCache;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Callable<Void>> callables = new ArrayList<Callable<Void>>();

    public ControladorCustomDeserializer(){


        aneisCache = new HashMap<String,Anel>();
        estagiosCache = new HashMap<String,Estagio>();
        gruposSemaforicosCache = new HashMap<String,GrupoSemaforico>();
        detectoresCache = new HashMap<String,Detector>();
        transicaoProibidaCache = new HashMap<String,TransicaoProibida>();
        estagioGrupoSemaforicoCache = new HashMap<String,EstagioGrupoSemaforico>();
        verdesConflitantesCache = new HashMap<String,VerdesConflitantes>();
        transicaoCache = new HashMap<String,Transicao>();
        tabelasEntreVerdesCache = new HashMap<String,TabelaEntreVerdes>();
        tabelaEntreVerdesTransicaoCache = new HashMap<String,TabelaEntreVerdesTransicao>();
        imagensCache = new HashMap<String,Imagem>();

    }

    public Controlador getControladorFromJson(JsonNode node){

        parseDadosBasicos(node);
        parseAneis(node);
        parseEstagios(node);
        parseGruposSemaforicos(node);
        parseDetectores(node);
        parseTransicoesProibidas(node);
        parseEstagiosGruposSemaforicos(node);
        parseVerdesConflitantes(node);
        parseTransicoes(node);
        parseTabelasEntreVerdes(node);
        parseTabelasEntreVerdesTransicoes(node);
        parseImagens(node);



        callables.stream().forEach(c -> {
            try {
                c.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return controlador;
    }

    private void parseAneis(JsonNode node) {
        if (node.has("aneis")) {
            List<Anel> aneis = new ArrayList<Anel>();
            for (JsonNode nodeAnel : node.get("aneis")) {
                Anel anel = parseAnel(nodeAnel);
                aneisCache.put(anel.getId().toString(),anel);
                aneis.add(anel);
            }
            controlador.setAneis(aneis);
        }
    }

    private void parseEstagios(JsonNode node) {
        if (node.has("estagios")) {
            for (JsonNode innerNode : node.get("estagios")) {
                Estagio estagio = parseEstagio(innerNode);
                estagiosCache.put(estagio.getId().toString(),estagio);
            }
        }
    }

    private void parseGruposSemaforicos(JsonNode node) {
        if (node.has("gruposSemaforicos")) {
            List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
            for (JsonNode nodeGrupoSemaforico : node.get("gruposSemaforicos")) {
                GrupoSemaforico grupoSemaforico = parseGrupoSemaforico(nodeGrupoSemaforico);
                gruposSemaforicosCache.put(grupoSemaforico.getId().toString(),grupoSemaforico);
                grupoSemaforicos.add(grupoSemaforico);
            }
            controlador.setGruposSemaforicos(grupoSemaforicos);
        }
    }

    private void parseDetectores(JsonNode node) {
        if (node.has("detectores")) {
            for (JsonNode innerNode : node.get("detectores")) {
                Detector detector = parseDetector(innerNode);
                detectoresCache.put(detector.getId().toString(),detector);
            }
        }
    }

    private void parseTransicoesProibidas(JsonNode node) {
        if (node.has("transicoesProibidas")) {
            for (JsonNode innerNode : node.get("transicoesProibidas")) {
                TransicaoProibida transicaoProibida = parseTransicaoProibida(innerNode);
                transicaoProibidaCache.put(transicaoProibida.getId().toString(),transicaoProibida);
            }
        }
    }

    private void parseEstagiosGruposSemaforicos(JsonNode node) {
        if (node.has("estagiosGruposSemaforicos")) {
            for (JsonNode innerNode : node.get("estagiosGruposSemaforicos")) {
                EstagioGrupoSemaforico estagioGrupoSemaforico = parseEstagioGrupoSemaforico(innerNode);
                estagioGrupoSemaforicoCache.put(estagioGrupoSemaforico.getId().toString(),estagioGrupoSemaforico);
            }
        }
    }

    private void parseVerdesConflitantes(JsonNode node) {
        if (node.has("verdesConflitantes")) {
            for (JsonNode innerNode : node.get("verdesConflitantes")) {
                VerdesConflitantes verdesConflitantes = parseVerdesConflitante(innerNode);
                verdesConflitantesCache.put(verdesConflitantes.getId().toString(),verdesConflitantes);
            }
        }
    }

    private void parseTabelasEntreVerdes(JsonNode node) {
        if (node.has("tabelasEntreVerdes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdes")) {
                TabelaEntreVerdes tabelaEntreVerdes = parsetTabelaEntreVerdes(innerNode);
                tabelasEntreVerdesCache.put(tabelaEntreVerdes.getId().toString(),tabelaEntreVerdes);
            }
        }
    }

    private void parseTransicoes(JsonNode node) {
        if (node.has("transicoes")) {
            for (JsonNode innerNode : node.get("transicoes")) {
                Transicao transicao = parseTransicao(innerNode);
                transicaoCache.put(transicao.getId().toString(),transicao);
            }
        }
    }

    private void parseTabelasEntreVerdesTransicoes(JsonNode node) {
        if (node.has("tabelasEntreVerdesTransicoes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdesTransicoes")) {
                TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = parsetTabelaEntreVerdesTransicao(innerNode);
                tabelaEntreVerdesTransicaoCache.put(tabelaEntreVerdesTransicao.getId().toString(),tabelaEntreVerdesTransicao);
            }
        }
    }

    private void parseImagens(JsonNode node) {
        if (node.has("imagens")) {
            for (JsonNode innerNode : node.get("imagens")) {
                Imagem imagem = parseImagem(innerNode);
                imagensCache.put(imagem.getId().toString(),imagem);
            }
        }
    }



    private Anel parseAnel(JsonNode node) {
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

        List<Estagio> estagios = new ArrayList<Estagio>();
        parseCollection("estagios",node,estagios,estagiosCache);
        anel.setEstagios(estagios);


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
        anel.setControlador(controlador);

        List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
        parseCollection("gruposSemaforicos",node,grupoSemaforicos,gruposSemaforicosCache);
        anel.setGruposSemaforicos(grupoSemaforicos);

        List<Detector> detectores = new ArrayList<Detector>();
        parseCollection("detectores",node,detectores,detectoresCache);
        anel.setDetectores(detectores);

        return anel;
    }

    private Estagio parseEstagio(JsonNode node) {
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
        if (node.get("tempoMaximoPermanenciaAtivado") != null) {
            estagio.setTempoMaximoPermanenciaAtivado(node.get("tempoMaximoPermanenciaAtivado").asBoolean());
        }
        if (node.get("demandaPrioritaria") != null) {
            estagio.setDemandaPrioritaria(node.get("demandaPrioritaria").asBoolean());
        }
        if (node.get("posicao") != null) {
            estagio.setPosicao(node.get("posicao").asInt());
        }

        if (node.has("imagem")) {
            final String imageId = node.get("imagem").get("id").asText();
            runLater(()->{
                estagio.setImagem(imagensCache.get(imageId));
                return null;
            });
        }

        if (node.has("detector")) {
            final String detectorId = node.get("detector").get("id").asText();
            runLater(()->{
                estagio.setDetector(detectoresCache.get(detectorId));
                return null;
            });
        }

        List<EstagioGrupoSemaforico> estagiosGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagiosGruposSemaforicos",node,estagiosGrupoSemaforicos,estagioGrupoSemaforicoCache);
        estagio.setEstagiosGruposSemaforicos(estagiosGrupoSemaforicos);


        List<TransicaoProibida> origens = new ArrayList<>();
        parseCollection("origemDeTransicoesProibidas",node,origens,transicaoProibidaCache);
        estagio.setOrigemDeTransicoesProibidas(origens);

        List<TransicaoProibida> destinos = new ArrayList<>();
        parseCollection("destinoDeTransicoesProibidas",node,destinos,transicaoProibidaCache);
        estagio.setDestinoDeTransicoesProibidas(destinos);

        List<TransicaoProibida> alternativas = new ArrayList<>();
        parseCollection("alternativaDeTransicoesProibidas",node,alternativas,transicaoProibidaCache);
        estagio.setAlternativaDeTransicoesProibidas(alternativas);

        return estagio;
    }

    private GrupoSemaforico parseGrupoSemaforico(JsonNode node) {
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
        if (node.has("faseVermelhaApagadaAmareloIntermitente")) {
            grupoSemaforico.setFaseVermelhaApagadaAmareloIntermitente(node.get("faseVermelhaApagadaAmareloIntermitente").asBoolean());
        }

        List<EstagioGrupoSemaforico> estagiosGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagiosGruposSemaforicos",node,estagiosGrupoSemaforicos,estagioGrupoSemaforicoCache);
        grupoSemaforico.setEstagioGrupoSemaforicos(estagiosGrupoSemaforicos);


        List<VerdesConflitantes> verdesConflitantes = new ArrayList<>();
        parseCollection("verdesConflitantesOrigem",node,verdesConflitantes,verdesConflitantesCache);
        grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantes);


        List<VerdesConflitantes> verdesConflitantesDestino = new ArrayList<>();
        parseCollection("verdesConflitantesDestino",node,verdesConflitantesDestino,verdesConflitantesCache);
        grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantesDestino);


        List<TabelaEntreVerdes> tabelasEntreVerdes = new ArrayList<>();
        parseCollection("tabelasEntreVerdes",node,tabelasEntreVerdes,tabelasEntreVerdesCache);
        grupoSemaforico.setTabelasEntreVerdes(tabelasEntreVerdes);


        List<Transicao> transicoes = new ArrayList<>();
        parseCollection("transicoes",node,transicoes,transicaoCache);
        grupoSemaforico.setTransicoes(transicoes);


        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("id").asText();
            runLater(()->{
                grupoSemaforico.setAnel(aneisCache.get(anelId));
                return null;
            });
        }
        return grupoSemaforico;
    }

    private Detector parseDetector(JsonNode node) {
        Detector detector = new Detector();

        if (node.has("id")) {
            detector.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("posicao")) {
            detector.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("tipo")) {
            detector.setTipo(TipoDetector.valueOf(node.get("tipo").asText()));
        }
        if (node.has("descricao")) {
            detector.setDescricao(node.get("descricao").asText());
        }
        if (node.has("tempoAusenciaDeteccaoMinima")) {
            detector.setTempoAusenciaDeteccaoMinima(node.get("tempoAusenciaDeteccaoMinima").asInt());
        }
        if (node.has("tempoAusenciaDeteccaoMaxima")) {
            detector.setTempoAusenciaDeteccaoMaxima(node.get("tempoAusenciaDeteccaoMaxima").asInt());
        }
        if (node.has("tempoDeteccaoPermanenteMinima")) {
            detector.setTempoDeteccaoPermanenteMinima(node.get("tempoDeteccaoPermanenteMinima").asInt());
        }
        if (node.has("tempoDeteccaoPermanenteMaxima")) {
            detector.setTempoDeteccaoPermanenteMaxima(node.get("tempoDeteccaoPermanenteMaxima").asInt());
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("id").asText();
            runLater(()->{
                detector.setAnel(aneisCache.get(anelId));
                return null;
            });
        }

        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("id").asText();
            runLater(()->{
                detector.setEstagio(estagiosCache.get(estagioId));
                return null;
            });
        }

        return detector;
    }

    private TransicaoProibida parseTransicaoProibida(JsonNode node) {
        TransicaoProibida transicaoProibida = new TransicaoProibida();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                transicaoProibida.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("id").asText();
            runLater(()->{
                transicaoProibida.setOrigem(estagiosCache.get(origemId));
                return null;
            });
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();
            runLater(()->{
                transicaoProibida.setDestino(estagiosCache.get(destinoId));
                return null;
            });
        }

        if (node.has("alternativo")) {
            final String alternativoId = node.get("alternativo").get("id").asText();
            runLater(()->{
                transicaoProibida.setAlternativo(estagiosCache.get(alternativoId));
                return null;
            });
        }

        return transicaoProibida;
    }

    private EstagioGrupoSemaforico parseEstagioGrupoSemaforico(JsonNode node) {
        EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico();
        if (node.has("id")) {
            estagioGrupoSemaforico.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("ativo")) {
            estagioGrupoSemaforico.setAtivo(node.get("ativo").asBoolean());
        }

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("id").asText();
            runLater(()->{
                estagioGrupoSemaforico.setGrupoSemaforico(gruposSemaforicosCache.get(grupoSemaforicoId));
                return null;
            });
        }

        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("id").asText();
            runLater(()->{
                estagioGrupoSemaforico.setEstagio(estagiosCache.get(estagioId));
                return null;
            });
        }

        return estagioGrupoSemaforico;
    }

    private VerdesConflitantes parseVerdesConflitante(JsonNode node) {
        VerdesConflitantes verdesConflitantes = new VerdesConflitantes();

        if (node.has("id")) {
            verdesConflitantes.setId(UUID.fromString(node.get("id").asText()));
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("id").asText();
            runLater(()->{
                verdesConflitantes.setOrigem(gruposSemaforicosCache.get(origemId));
                return null;
            });
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();
            runLater(()->{
                verdesConflitantes.setDestino(gruposSemaforicosCache.get(destinoId));
                return null;
            });
        }

        return verdesConflitantes;
    }

    private Transicao parseTransicao(JsonNode node) {
        Transicao transicao = new Transicao();

        if (node.has("id")) {
            transicao.setId(UUID.fromString(node.get("id").asText()));
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("id").asText();
            runLater(()->{
                transicao.setOrigem(estagiosCache.get(origemId));
                return null;
            });
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();
            runLater(()->{
                transicao.setDestino(estagiosCache.get(destinoId));
                return null;
            });
        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<>();
        parseCollection("tabelaEntreVerdesTransicoes",node,tabelaEntreVerdesTransicoes,tabelaEntreVerdesTransicaoCache);
        transicao.setTabelaEntreVerdesTransicoes(tabelaEntreVerdesTransicoes);

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("id").asText();
            runLater(()->{
                transicao.setGrupoSemaforico(gruposSemaforicosCache.get(grupoSemaforicoId));
                return null;
            });
        }

        return transicao;
    }

    private TabelaEntreVerdes parsetTabelaEntreVerdes(JsonNode node) {
        TabelaEntreVerdes tabelaEntreVerdes = new TabelaEntreVerdes();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaEntreVerdes.setId(UUID.fromString(id.asText()));
            }
        }
        tabelaEntreVerdes.setDescricao(node.get("descricao") != null ? node.get("descricao").asText() : null);
        tabelaEntreVerdes.setPosicao(node.get("posicao") != null ? node.get("posicao").asInt() : null);

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("id").asText();
            runLater(()->{
                tabelaEntreVerdes.setGrupoSemaforico(gruposSemaforicosCache.get(grupoSemaforicoId));
                return null;
            });
        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<TabelaEntreVerdesTransicao>();
        parseCollection("tabelaEntreVerdesTransicoes",node,tabelaEntreVerdesTransicoes,tabelasEntreVerdesCache);
        tabelaEntreVerdes.setTabelaEntreVerdesTransicoes(tabelaEntreVerdesTransicoes);


        return tabelaEntreVerdes;
    }

    private TabelaEntreVerdesTransicao parsetTabelaEntreVerdesTransicao(JsonNode node) {

        TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = new TabelaEntreVerdesTransicao();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaEntreVerdesTransicao.setId(UUID.fromString(id.asText()));
            }
        }
        if (node.get("tempoAmarelo") != null) {
            tabelaEntreVerdesTransicao.setTempoAmarelo(node.get("tempoAmarelo").asInt());
        }
        if (node.get("tempoVermelhoIntermitente") != null) {
            tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(node.get("tempoVermelhoIntermitente").asInt());
        }
        if (node.get("tempoVermelhoLimpeza") != null) {
            tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(node.get("tempoVermelhoLimpeza").asInt());
        }
        if (node.get("tempoAtrasoGrupo") != null) {
            tabelaEntreVerdesTransicao.setTempoAtrasoGrupo(node.get("tempoAtrasoGrupo").asInt());
        }

        if (node.has("transicao")) {
            final String transicaoId = node.get("transicao").get("id").asText();
            runLater(()->{
                tabelaEntreVerdesTransicao.setTransicao(transicaoCache.get(transicaoId));
                return null;
            });
        }

        if (node.has("tabelaEntreVerdes")) {
            final String tabelaEntreVerdesId = node.get("tabelaEntreVerdes").get("id").asText();
            runLater(()->{
                tabelaEntreVerdesTransicao.setTabelaEntreVerdes(tabelasEntreVerdesCache.get(tabelaEntreVerdesId));
                return null;
            });
        }


        return tabelaEntreVerdesTransicao;
    }

    private Imagem parseImagem(JsonNode node) {

        Imagem imagem = new Imagem();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                imagem.setId(UUID.fromString(id.asText()));
            }
        }
        if (node.get("fileName") != null) {
            imagem.setFilename(node.get("fileName").asText());
        }

        if (node.get("contentType") != null) {
            imagem.setFilename(node.get("contentType").asText());
        }

        return imagem;
    }

    private void runLater(Callable<Void> callable) {
        callables.add(callable);
    }

    private void parseDadosBasicos(JsonNode node) {
        JsonNode id = node.get("id");
        if (id != null) {
            controlador.setId(UUID.fromString(id.asText()));
        }
        controlador.setLocalizacao(node.get("localizacao") != null ? node.get("localizacao").asText() : null);
        controlador.setNumeroSMEE(node.get("numeroSMEE") != null ? node.get("numeroSMEE").asText() : null);
        controlador.setNumeroSMEEConjugado1(node.get("numeroSMEEConjugado1") != null ? node.get("numeroSMEEConjugado1").asText() : null);
        controlador.setNumeroSMEEConjugado2(node.get("numeroSMEEConjugado2") != null ? node.get("numeroSMEEConjugado2").asText() : null);
        controlador.setNumeroSMEEConjugado3(node.get("numeroSMEEConjugado3") != null ? node.get("numeroSMEEConjugado3").asText() : null);
        controlador.setFirmware(node.get("firmware") != null ? node.get("firmware").asText() : null);
        controlador.setLatitude(node.get("latitude") != null ? node.get("latitude").asDouble() : null);
        controlador.setLongitude(node.get("longitude") != null ? node.get("longitude").asDouble() : null);
        controlador.setStatusControlador(node.get("statusControlador") != null ? StatusControlador.valueOf(node.get("statusControlador").asText()) : null);

        if (node.has("area") && node.get("area").get("id") != null) {
            controlador.setArea(Area.find.byId(UUID.fromString(node.get("area").get("id").asText())));
        }
        if (node.has("modelo") && node.get("modelo").get("id") != null) {
            controlador.setModelo(ModeloControlador.find.byId(UUID.fromString(node.get("modelo").get("id").asText())));
        }
    }

    private void parseCollection(String collection, JsonNode node,List list, Map cache){
        if (node.has(collection)) {
            for (JsonNode innerNode : node.get(collection)) {
                if(innerNode.has("id")){
                    final String id = node.get("id").asText();
                    runLater(()->{
                        list.add(cache.get(id));
                        return null;
                    });
                }
            }
        }
    }


}
