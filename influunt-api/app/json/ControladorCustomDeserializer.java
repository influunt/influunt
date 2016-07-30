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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.sun.corba.se.impl.util.RepositoryId.cache;
import static com.sun.tools.doclint.Entity.para;

/**
 * Created by rodrigosol on 7/29/16.
 */
public class ControladorCustomDeserializer {
    public static final String ANEIS = "aneis";
    public static final String ESTAGIOS = "estagios";
    public static final String GRUPOS_SEMAFORICOS = "gruposSemaforicos";
    public static final String DETECTORES = "detectores";
    public static final String TRANSICAO_PROIBIDA = "transicaoProibida";
    public static final String ESTAGIO_GRUPO_SEMAFORICO = "estagioGrupoSemaforico";
    public static final String VERDES_CONFLITANTES = "verdesConflitantes";
    public static final String TRANSICAO = "transicao";
    public static final String TABELAS_ENTRE_VERDES = "tabelasEntreVerdes";
    public static final String TABELA_ENTRE_VERDES_TRANSICAO = "tabelaEntreVerdesTransicao";
    public static final String IMAGENS = "imagens";
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
    private List<Consumer<Map<String, Map>>> consumers = new ArrayList<Consumer<Map<String, Map>>>();
    private Map<String, Map> caches = new HashMap<String,Map>();


    public ControladorCustomDeserializer(){


        aneisCache = new HashMap<String,Anel>();
        caches.put(ANEIS,aneisCache);

        estagiosCache = new HashMap<String,Estagio>();
        caches.put(ESTAGIOS,estagiosCache);

        gruposSemaforicosCache = new HashMap<String,GrupoSemaforico>();
        caches.put(GRUPOS_SEMAFORICOS,gruposSemaforicosCache);

        detectoresCache = new HashMap<String,Detector>();
        caches.put(DETECTORES,detectoresCache);

        transicaoProibidaCache = new HashMap<String,TransicaoProibida>();
        caches.put(TRANSICAO_PROIBIDA,transicaoProibidaCache);

        estagioGrupoSemaforicoCache = new HashMap<String,EstagioGrupoSemaforico>();
        caches.put(ESTAGIO_GRUPO_SEMAFORICO,estagioGrupoSemaforicoCache);

        verdesConflitantesCache = new HashMap<String,VerdesConflitantes>();
        caches.put(VERDES_CONFLITANTES,verdesConflitantesCache);

        transicaoCache = new HashMap<String,Transicao>();
        caches.put(TRANSICAO,transicaoCache);

        tabelasEntreVerdesCache = new HashMap<String,TabelaEntreVerdes>();
        caches.put(TABELAS_ENTRE_VERDES,tabelasEntreVerdesCache);

        tabelaEntreVerdesTransicaoCache = new HashMap<String,TabelaEntreVerdesTransicao>();
        caches.put(TABELA_ENTRE_VERDES_TRANSICAO,tabelaEntreVerdesTransicaoCache);

        imagensCache = new HashMap<String,Imagem>();
        caches.put(IMAGENS,imagensCache);


    }

    public Controlador getControladorFromJson(JsonNode node){

        System.out.println(node.toString());
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
        parseDadosBasicos(node);




        consumers.stream().forEach(c -> {
            try {
                c.accept(caches);
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
            List<Detector> detectores = new ArrayList<>();

            for (JsonNode innerNode : node.get("detectores")) {
                Detector detector = parseDetector(innerNode);
                detectoresCache.put(detector.getId().toString(),detector);
                detectores.add(detector);
            }

            controlador.setDetectores(detectores);
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
        parseCollection("estagios",node,estagios,ESTAGIOS);
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
        parseCollection("gruposSemaforicos",node,grupoSemaforicos,GRUPOS_SEMAFORICOS);
        anel.setGruposSemaforicos(grupoSemaforicos);

        List<Detector> detectores = new ArrayList<Detector>();
        parseCollection("detectores",node,detectores,DETECTORES);
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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(IMAGENS);
                estagio.setImagem((Imagem) map.get(imageId));
            };
            runLater(c);

        }

        if (node.has("detector")) {
            final String detectorId = node.get("detector").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(DETECTORES);
                estagio.setDetector((Detector) map.get(detectorId));
            };
            runLater(c);

        }

        List<EstagioGrupoSemaforico> estagiosGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagiosGruposSemaforicos",node,estagiosGrupoSemaforicos,ESTAGIO_GRUPO_SEMAFORICO);
        estagio.setEstagiosGruposSemaforicos(estagiosGrupoSemaforicos);


        List<TransicaoProibida> origens = new ArrayList<>();
        parseCollection("origemDeTransicoesProibidas",node,origens,TRANSICAO_PROIBIDA);
        estagio.setOrigemDeTransicoesProibidas(origens);

        List<TransicaoProibida> destinos = new ArrayList<>();
        parseCollection("destinoDeTransicoesProibidas",node,destinos,TRANSICAO_PROIBIDA);
        estagio.setDestinoDeTransicoesProibidas(destinos);

        List<TransicaoProibida> alternativas = new ArrayList<>();
        parseCollection("alternativaDeTransicoesProibidas",node,alternativas,TRANSICAO_PROIBIDA);
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
        parseCollection("estagiosGruposSemaforicos",node,estagiosGrupoSemaforicos,ESTAGIO_GRUPO_SEMAFORICO);
        grupoSemaforico.setEstagioGrupoSemaforicos(estagiosGrupoSemaforicos);


        List<VerdesConflitantes> verdesConflitantes = new ArrayList<>();
        parseCollection("verdesConflitantesOrigem",node,verdesConflitantes,VERDES_CONFLITANTES);
        grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantes);


        List<VerdesConflitantes> verdesConflitantesDestino = new ArrayList<>();
        parseCollection("verdesConflitantesDestino",node,verdesConflitantesDestino,VERDES_CONFLITANTES);
        grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantesDestino);


        List<TabelaEntreVerdes> tabelasEntreVerdes = new ArrayList<>();
        parseCollection("tabelasEntreVerdes",node,tabelasEntreVerdes,TABELAS_ENTRE_VERDES);
        grupoSemaforico.setTabelasEntreVerdes(tabelasEntreVerdes);


        List<Transicao> transicoes = new ArrayList<>();
        parseCollection("transicoes",node,transicoes,TRANSICAO);
        grupoSemaforico.setTransicoes(transicoes);


        List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagioGrupoSemaforicos",node,estagioGrupoSemaforicos,ESTAGIO_GRUPO_SEMAFORICO);
        grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);

        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("id").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                grupoSemaforico.setAnel((Anel) map.get(anelId));
            };
            runLater(c);
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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                detector.setAnel((Anel) map.get(anelId));
            };
            runLater(c);
        }

        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                detector.setEstagio((Estagio) map.get(estagioId));
            };
            runLater(c);
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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicaoProibida.setOrigem((Estagio) map.get(origemId));
            };
            runLater(c);
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicaoProibida.setDestino((Estagio) map.get(destinoId));
            };
            runLater(c);

        }

        if (node.has("alternativo")) {
            final String alternativoId = node.get("alternativo").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicaoProibida.setAlternativo((Estagio) map.get(alternativoId));
            };
            runLater(c);

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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                estagioGrupoSemaforico.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };
            runLater(c);

        }

        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("id").asText();

            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                estagioGrupoSemaforico.setEstagio((Estagio) map.get(estagioId));
            };
            runLater(c);

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

            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                verdesConflitantes.setOrigem((GrupoSemaforico) map.get(origemId));
            };
            runLater(c);
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();

            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                verdesConflitantes.setDestino((GrupoSemaforico) map.get(destinoId));
            };

            runLater(c);
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

            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicao.setOrigem((Estagio) map.get(origemId));
            };

            runLater(c);

        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicao.setDestino((Estagio) map.get(destinoId));
            };

            runLater(c);

        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<>();
        parseCollection("tabelaEntreVerdesTransicoes",node,tabelaEntreVerdesTransicoes,TABELA_ENTRE_VERDES_TRANSICAO);
        transicao.setTabelaEntreVerdesTransicoes(tabelaEntreVerdesTransicoes);

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                transicao.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };

            runLater(c);

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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                tabelaEntreVerdes.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };

            runLater(c);

        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<TabelaEntreVerdesTransicao>();
        parseCollection("tabelaEntreVerdesTransicoes",node,tabelaEntreVerdesTransicoes,TABELA_ENTRE_VERDES_TRANSICAO);
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
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(TRANSICAO);
                tabelaEntreVerdesTransicao.setTransicao((Transicao) map.get(transicaoId));
            };

            runLater(c);

        }

        if (node.has("tabelaEntreVerdes")) {
            final String tabelaEntreVerdesId = node.get("tabelaEntreVerdes").get("id").asText();
            Consumer<Map<String,Map>> c = (caches) -> {
                Map map = caches.get(TABELAS_ENTRE_VERDES);
                tabelaEntreVerdesTransicao.setTabelaEntreVerdes((TabelaEntreVerdes) map.get(tabelaEntreVerdesId));
            };

            runLater(c);

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



    private void parseDadosBasicos(JsonNode node) {
        JsonNode id = node.get("id");
        if (id != null) {
            controlador.setId(UUID.fromString(id.asText()));
        }

        if (node.has("area") && node.get("area").get("id") != null) {
            controlador.setArea(Area.find.byId(UUID.fromString(node.get("area").get("id").asText())));
        }
        if (node.has("modelo") && node.get("modelo").get("id") != null) {
            controlador.setModelo(ModeloControlador.find.byId(UUID.fromString(node.get("modelo").get("id").asText())));
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
        controlador.setSequencia(node.get("sequencia") != null ? node.get("sequencia").asInt() : null);
    }

    private void parseCollection(String collection, JsonNode node,List list, final String cacheContainer){

        if (node.has(collection)) {
            for (JsonNode innerNode : node.get(collection)) {
                if(innerNode.has("id")){
                    final String id = innerNode.get("id").asText();

                    Consumer<Map<String,Map>> c = (caches) -> {
                        assert cacheContainer != null;
                        Map map = caches.get(cacheContainer);
                        assert map.containsKey(id);
                        list.add(map.get(id));
                    };

                    runLater(c);
                }
            }
        }
    }

    private void runLater(Consumer<Map<String, Map>> c) {
        consumers.add(c);
    }

    public JsonNode getControladorFromJsonFoo(JsonNode jsonControlador) {
        return jsonControlador;
    }
}
