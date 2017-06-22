package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.joda.time.LocalTime;
import play.libs.Json;
import utils.RangeUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by rodrigosol on 7/29/16.
 */
public class ControladorCustomDeserializer {
    public static final String ANEIS = "aneis";

    public static final String ESTAGIOS = "estagios";

    public static final String GRUPOS_SEMAFORICOS = "gruposSemaforicos";

    public static final String VERSOES_PLANOS = "versoesPlanos";

    public static final String VERSOES_TABELAS_HORARIAS = "versoesTabelasHorarias";

    public static final String DETECTORES = "detectores";

    public static final String TRANSICAO_PROIBIDA = "transicaoProibida";

    public static final String ESTAGIO_GRUPO_SEMAFORICO = "estagioGrupoSemaforico";

    public static final String VERDES_CONFLITANTES = "verdesConflitantes";

    public static final String TRANSICAO = "transicao";

    public static final String ATRASO_DE_GRUPO = "atrasoDeGrupo";

    public static final String TABELAS_ENTRE_VERDES = "tabelasEntreVerdes";

    public static final String TABELA_ENTRE_VERDES_TRANSICAO = "tabelaEntreVerdesTransicao";

    public static final String PLANOS = "planos";

    public static final String GRUPOS_SEMAFORICOS_PLANOS = "gruposSemaforicosPlanos";

    public static final String ESTAGIOS_PLANOS = "estagiosPlanos";

    public static final String TABELAS_HORARIAS = "tabelasHorarias";

    public static final String EVENTOS = "eventos";

    public static final String ENDERECOS = "enderecos";

    public static final String CIDADES = "cidades";

    public static final String AREAS = "areas";

    public static final String IMAGENS = "imagens";

    private Controlador controlador = new Controlador();

    private Map<String, Anel> aneisCache;

    private Map<String, Estagio> estagiosCache;

    private Map<String, GrupoSemaforico> gruposSemaforicosCache;

    private Map<String, Detector> detectoresCache;

    private Map<String, TransicaoProibida> transicaoProibidaCache;

    private Map<String, EstagioGrupoSemaforico> estagioGrupoSemaforicoCache;

    private Map<String, VerdesConflitantes> verdesConflitantesCache;

    private Map<String, Transicao> transicaoCache;

    private Map<String, AtrasoDeGrupo> atrasoDeGrupoCache;

    private Map<String, TabelaEntreVerdes> tabelasEntreVerdesCache;

    private Map<String, TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicaoCache;

    private Map<String, Plano> planosCache;

    private Map<String, VersaoPlano> versoesPlanosCache;

    private Map<String, VersaoTabelaHoraria> versoesTabelasHorariasCache;

    private Map<String, GrupoSemaforicoPlano> gruposSemaforicosPlanosCache;

    private Map<String, EstagioPlano> estagiosPlanosCache;

    private Map<String, TabelaHorario> tabelasHorariasCache;

    private Map<String, Evento> eventosCache;

    private Map<String, Endereco> enderecosCache;

    private Map<String, Area> areasCache;

    private Map<String, Cidade> cidadesCache;

    private Map<String, Imagem> imagensCache;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<Consumer<Map<String, Map>>> consumers = new ArrayList<>();

    private Map<String, Map> caches = new HashMap<>();


    public ControladorCustomDeserializer() {
        aneisCache = new HashMap<>();
        caches.put(ANEIS, aneisCache);

        estagiosCache = new HashMap<>();
        caches.put(ESTAGIOS, estagiosCache);

        gruposSemaforicosCache = new HashMap<>();
        caches.put(GRUPOS_SEMAFORICOS, gruposSemaforicosCache);

        detectoresCache = new HashMap<>();
        caches.put(DETECTORES, detectoresCache);

        transicaoProibidaCache = new HashMap<>();
        caches.put(TRANSICAO_PROIBIDA, transicaoProibidaCache);

        estagioGrupoSemaforicoCache = new HashMap<>();
        caches.put(ESTAGIO_GRUPO_SEMAFORICO, estagioGrupoSemaforicoCache);

        verdesConflitantesCache = new HashMap<>();
        caches.put(VERDES_CONFLITANTES, verdesConflitantesCache);

        transicaoCache = new HashMap<>();
        caches.put(TRANSICAO, transicaoCache);

        tabelasEntreVerdesCache = new HashMap<>();
        caches.put(TABELAS_ENTRE_VERDES, tabelasEntreVerdesCache);

        tabelaEntreVerdesTransicaoCache = new HashMap<>();
        caches.put(TABELA_ENTRE_VERDES_TRANSICAO, tabelaEntreVerdesTransicaoCache);

        versoesPlanosCache = new HashMap<>();
        caches.put(VERSOES_PLANOS, versoesPlanosCache);

        versoesTabelasHorariasCache = new HashMap<>();
        caches.put(VERSOES_TABELAS_HORARIAS, versoesTabelasHorariasCache);

        planosCache = new HashMap<>();
        caches.put(PLANOS, planosCache);

        gruposSemaforicosPlanosCache = new HashMap<>();
        caches.put(GRUPOS_SEMAFORICOS_PLANOS, gruposSemaforicosPlanosCache);

        estagiosPlanosCache = new HashMap<>();
        caches.put(ESTAGIOS_PLANOS, estagiosPlanosCache);

        tabelasHorariasCache = new HashMap<>();
        caches.put(TABELAS_HORARIAS, tabelasHorariasCache);

        eventosCache = new HashMap<>();
        caches.put(EVENTOS, eventosCache);

        areasCache = new HashMap<>();
        caches.put(AREAS, areasCache);

        cidadesCache = new HashMap<>();
        caches.put(CIDADES, cidadesCache);

        enderecosCache = new HashMap<>();
        caches.put(ENDERECOS, enderecosCache);

        imagensCache = new HashMap<>();
        caches.put(IMAGENS, imagensCache);

        atrasoDeGrupoCache = new HashMap<>();
        caches.put(ATRASO_DE_GRUPO, atrasoDeGrupoCache);
    }

    public Controlador getControladorFromJson(JsonNode node) {
        return getControladorFromJson(node, null);
    }

    public Controlador getControladorFromJson(JsonNode node, Usuario usuario) {
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
        parsePlanos(node);
        parseGruposSemaforicosPlanos(node);
        parseEstagiosPlanos(node);
        parseTabelasHorarias(node);
        parseEventos(node);
        parseImagens(node);
        parseDadosBasicos(node);
        parseEnderecos(node);
        parseCidades(node);
        parseAreas(node);
        parseAtrasosDeGrupo(node);
        parseVersoesPlanos(node, usuario);
        parseVersoesTabelasHorarias(node, usuario);

        consumers.forEach(c -> c.accept(caches));

        return controlador;
    }


    public Controlador getPacotePlanosFromJson(JsonNode configuracaoControladorJson, JsonNode pacotePlanosJson) {
        JsonNode controladorJson = buildControladorJsonFromPacotePlanos(configuracaoControladorJson, pacotePlanosJson);
        return getControladorFromJson(controladorJson);
    }

    private JsonNode buildControladorJsonFromPacotePlanos(JsonNode configuracaoControladorJson, JsonNode pacotePlanosJson) {
        ObjectNode controladorJson = (ObjectNode) configuracaoControladorJson;

        for (JsonNode versaoPlanoJson : pacotePlanosJson.get("versoesPlanos")) {
            if (versaoPlanoJson.has("anel") && versaoPlanoJson.get("anel").has("idJson")) {
                for (JsonNode anelNode : controladorJson.get("aneis")) {
                    if (anelNode.get("idJson").asText().equals(versaoPlanoJson.get("anel").get("idJson").asText())) {
                        ((ObjectNode) anelNode.get("versaoPlano")).set("idJson", versaoPlanoJson.get("idJson"));
                    }
                }

            }
        }
        controladorJson.set("versoesPlanos", pacotePlanosJson.get("versoesPlanos"));
        controladorJson.set("planos", pacotePlanosJson.get("planos"));
        controladorJson.set("gruposSemaforicosPlanos", pacotePlanosJson.get("gruposSemaforicosPlanos"));
        controladorJson.set("estagiosPlanos", pacotePlanosJson.get("estagiosPlanos"));

        return controladorJson;
    }

    public Controlador getPacoteTabelaHorariaFromJson(JsonNode configuracaoControladorJson, JsonNode pacoteTabelaHorariaJson) {
        return getPacoteTabelaHorariaFromJson(configuracaoControladorJson, pacoteTabelaHorariaJson, null);
    }

    public Controlador getPacoteTabelaHorariaFromJson(JsonNode configuracaoControladorJson, JsonNode pacoteTabelaHorariaJson, Usuario usuario) {
        ObjectNode controladorJson = (ObjectNode) configuracaoControladorJson;
        controladorJson.set("versoesTabelasHorarias", pacoteTabelaHorariaJson.get("versoesTabelasHorarias"));
        controladorJson.set("tabelasHorarias", pacoteTabelaHorariaJson.get("tabelasHorarias"));
        controladorJson.set("eventos", pacoteTabelaHorariaJson.get("eventos"));

        return getControladorFromJson(controladorJson, usuario);
    }

    public Controlador getPacoteConfiguracaoCompletaFromJson(JsonNode configuracaoControladorJson, JsonNode pacotePlanosJson, JsonNode pacoteTabelaHorariaJson) {
        ObjectNode controladorJson = (ObjectNode) buildControladorJsonFromPacotePlanos(configuracaoControladorJson, pacotePlanosJson);

        controladorJson.set("versoesTabelasHorarias", pacoteTabelaHorariaJson.get("versoesTabelasHorarias"));
        controladorJson.set("tabelasHorarias", pacoteTabelaHorariaJson.get("tabelasHorarias"));
        controladorJson.set("eventos", pacoteTabelaHorariaJson.get("eventos"));

        return getControladorFromJson(controladorJson);
    }

    private void parseAneis(JsonNode node) {
        if (node.has("aneis")) {
            List<Anel> aneis = new ArrayList<Anel>();
            for (JsonNode nodeAnel : node.get("aneis")) {
                Anel anel = parseAnel(nodeAnel);
                aneisCache.put(anel.getIdJson(), anel);
                aneis.add(anel);
            }
            controlador.setAneis(aneis);
        }
    }

    private void parseEstagios(JsonNode node) {
        if (node.has("estagios")) {
            for (JsonNode innerNode : node.get("estagios")) {
                Estagio estagio = parseEstagio(innerNode);
                estagiosCache.put(estagio.getIdJson(), estagio);
            }
        }
    }

    private void parseGruposSemaforicos(JsonNode node) {
        if (node.has("gruposSemaforicos")) {
            List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
            for (JsonNode nodeGrupoSemaforico : node.get("gruposSemaforicos")) {
                GrupoSemaforico grupoSemaforico = parseGrupoSemaforico(nodeGrupoSemaforico);
                gruposSemaforicosCache.put(grupoSemaforico.getIdJson(), grupoSemaforico);
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
                detectoresCache.put(detector.getIdJson(), detector);
                detectores.add(detector);
            }
        }
    }

    private void parseVersoesTabelasHorarias(JsonNode node, Usuario usuario) {
        if (node.has("versoesTabelasHorarias")) {
            List<VersaoTabelaHoraria> versoesTabelaHorarias = new ArrayList<VersaoTabelaHoraria>();
            for (JsonNode nodeVersaoTabelaHoraria : node.get("versoesTabelasHorarias")) {
                VersaoTabelaHoraria versaoTabelaHoraria = parseVersaoTabelaHoraria(nodeVersaoTabelaHoraria, usuario);
                versoesTabelasHorariasCache.put(versaoTabelaHoraria.getIdJson(), versaoTabelaHoraria);
                versoesTabelaHorarias.add(versaoTabelaHoraria);
            }
            controlador.setVersoesTabelasHorarias(versoesTabelaHorarias);
        }
    }

    private void parseTransicoesProibidas(JsonNode node) {
        if (node.has("transicoesProibidas")) {
            for (JsonNode innerNode : node.get("transicoesProibidas")) {
                TransicaoProibida transicaoProibida = parseTransicaoProibida(innerNode);
                transicaoProibidaCache.put(transicaoProibida.getIdJson(), transicaoProibida);
            }
        }
    }

    private void parseEstagiosGruposSemaforicos(JsonNode node) {
        if (node.has("estagiosGruposSemaforicos")) {
            for (JsonNode innerNode : node.get("estagiosGruposSemaforicos")) {
                EstagioGrupoSemaforico estagioGrupoSemaforico = parseEstagioGrupoSemaforico(innerNode);
                estagioGrupoSemaforicoCache.put(estagioGrupoSemaforico.getIdJson(), estagioGrupoSemaforico);
            }
        }
    }

    private void parseVerdesConflitantes(JsonNode node) {
        if (node.has("verdesConflitantes")) {
            for (JsonNode innerNode : node.get("verdesConflitantes")) {
                VerdesConflitantes verdesConflitantes = parseVerdesConflitante(innerNode);
                verdesConflitantesCache.put(verdesConflitantes.getIdJson(), verdesConflitantes);
            }
        }
    }

    private void parseTabelasEntreVerdes(JsonNode node) {
        if (node.has("tabelasEntreVerdes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdes")) {
                TabelaEntreVerdes tabelaEntreVerdes = parsetTabelaEntreVerdes(innerNode);
                tabelasEntreVerdesCache.put(tabelaEntreVerdes.getIdJson(), tabelaEntreVerdes);
            }
        }
    }

    private void parseTransicoes(JsonNode node) {
        if (node.has("transicoes")) {
            for (JsonNode innerNode : node.get("transicoes")) {
                Transicao transicao = parseTransicao(innerNode);
                transicaoCache.put(transicao.getIdJson(), transicao);
            }
        }

        if (node.has("transicoesComGanhoDePassagem")) {
            for (JsonNode innerNode : node.get("transicoesComGanhoDePassagem")) {
                Transicao transicao = parseTransicao(innerNode);
                transicaoCache.put(transicao.getIdJson(), transicao);
            }
        }
    }

    private void parseTabelasEntreVerdesTransicoes(JsonNode node) {
        if (node.has("tabelasEntreVerdesTransicoes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdesTransicoes")) {
                TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = parsetTabelaEntreVerdesTransicao(innerNode);
                tabelaEntreVerdesTransicaoCache.put(tabelaEntreVerdesTransicao.getIdJson(), tabelaEntreVerdesTransicao);
            }
        }
    }

    private void parseVersoesPlanos(JsonNode node, Usuario usuario) {
        if (node.has("versoesPlanos")) {
            for (JsonNode innerNode : node.get("versoesPlanos")) {
                VersaoPlano versaoPlano = parseVersaoPlano(innerNode, usuario);
                versoesPlanosCache.put(versaoPlano.getIdJson(), versaoPlano);
            }
        }
    }

    private void parsePlanos(JsonNode node) {
        if (node.has("planos")) {
            for (JsonNode nodePlano : node.get("planos")) {
                if (!nodePlano.has("configurado") || nodePlano.get("configurado").asBoolean()) {
                    Plano plano = parsePlano(nodePlano);
                    planosCache.put(plano.getIdJson(), plano);
                }
            }
        }
    }

    private void parseGruposSemaforicosPlanos(JsonNode node) {
        if (node.has("gruposSemaforicosPlanos")) {
            for (JsonNode nodeGrupoSemaforicoPlano : node.get("gruposSemaforicosPlanos")) {
                GrupoSemaforicoPlano grupoSemaforicoPlano = parseGruposSemaforicosPlano(nodeGrupoSemaforicoPlano);
                gruposSemaforicosPlanosCache.put(grupoSemaforicoPlano.getIdJson(), grupoSemaforicoPlano);
            }
        }
    }


    private void parseEstagiosPlanos(JsonNode node) {
        if (node.has("estagiosPlanos")) {
            for (JsonNode nodeEstagioPlano : node.get("estagiosPlanos")) {
                EstagioPlano estagioPlano = parseEstagiosPlano(nodeEstagioPlano);
                estagiosPlanosCache.put(estagioPlano.getIdJson(), estagioPlano);
            }
        }
    }

    private void parseEventos(JsonNode node) {
        if (node.has("eventos")) {
            for (JsonNode nodeEvento : node.get("eventos")) {
                Evento evento = parseEvento(nodeEvento);
                eventosCache.put(evento.getIdJson(), evento);
            }
        }
    }

    private void parseTabelasHorarias(JsonNode node) {
        if (node.has("tabelasHorarias")) {
            for (JsonNode nodeTabelaHoraria : node.get("tabelasHorarias")) {
                TabelaHorario tabelaHoraria = parseTabelaHoraria(nodeTabelaHoraria);
                tabelasHorariasCache.put(tabelaHoraria.getIdJson(), tabelaHoraria);
            }
        }
    }

    private void parseEnderecos(JsonNode node) {
        if (node.has("todosEnderecos")) {
            for (JsonNode innerNode : node.get("todosEnderecos")) {
                Endereco endereco = parseEndereco(innerNode);
                enderecosCache.put(endereco.getIdJson(), endereco);
            }
        }
    }

    private void parseAreas(JsonNode node) {
        if (node.has("areas")) {
            for (JsonNode innerNode : node.get("areas")) {
                Area area = parseArea(innerNode);
                areasCache.put(area.getIdJson(), area);
            }
        }
    }

    private void parseCidades(JsonNode node) {
        if (node.has("cidades")) {
            for (JsonNode innerNode : node.get("cidades")) {
                Cidade cidade = parseCidade(innerNode);
                cidadesCache.put(cidade.getIdJson(), cidade);
            }
        }
    }

    private void parseImagens(JsonNode node) {
        if (node.has("imagens")) {
            for (JsonNode innerNode : node.get("imagens")) {
                Imagem imagem = parseImagem(innerNode);
                imagensCache.put(imagem.getIdJson(), imagem);
            }
        }
    }

    private void parseAtrasosDeGrupo(JsonNode node) {
        if (node.has("atrasosDeGrupo")) {
            for (JsonNode innerNode : node.get("atrasosDeGrupo")) {
                AtrasoDeGrupo atrasoDeGrupo = parseAtrasoDeGrupo(innerNode);
                atrasoDeGrupoCache.put(atrasoDeGrupo.getIdJson(), atrasoDeGrupo);
            }
        }
    }


    private Anel parseAnel(JsonNode node) {
        Anel anel = new Anel();
        anel.setControlador(controlador);

        if (node.has("id")) {
            anel.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("ativo")) {
            anel.setAtivo(node.get("ativo").asBoolean());
        }
        if (node.has("aceitaModoManual")) {
            anel.setAceitaModoManual(node.get("aceitaModoManual").asBoolean());
        }
        if (node.has("idJson")) {
            anel.setIdJson(node.get("idJson").asText());
        }
        if (node.has("numeroSMEE")) {
            anel.setNumeroSMEE(node.get("numeroSMEE").asText());
        }
        if (node.has("descricao")) {
            anel.setDescricao(node.get("descricao").asText());
        }

        List<Estagio> estagios = new ArrayList<Estagio>();
        parseCollection("estagios", node, estagios, ESTAGIOS, ANEIS);
        anel.setEstagios(estagios);

        if (node.has("posicao")) {
            anel.setPosicao(node.get("posicao").asInt());
        }

        if (node.has("croqui") && node.get("croqui").get("id") != null) {
            final String imageId = node.get("croqui").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(IMAGENS);
                anel.setCroqui((Imagem) map.get(imageId));
            };
            runLater(c);
        }

        if (node.has("versaoPlano") && node.get("versaoPlano").has("idJson")) {
            final String versaoPlanoId = node.get("versaoPlano").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(VERSOES_PLANOS);
                anel.addVersaoPlano((VersaoPlano) map.get(versaoPlanoId));
            };
            runLater(c);
        }

        List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
        parseCollection("gruposSemaforicos", node, grupoSemaforicos, GRUPOS_SEMAFORICOS, ANEIS);
        anel.setGruposSemaforicos(grupoSemaforicos);

        List<Detector> detectores = new ArrayList<Detector>();
        parseCollection("detectores", node, detectores, DETECTORES, ANEIS);
        anel.setDetectores(detectores);

        if (anel.isAtivo()) {
            if (node.has("endereco")) {
                final String enderecoId = node.get("endereco").get("idJson").asText();
                Consumer<Map<String, Map>> c = (caches) -> {
                    Map map = caches.get(ENDERECOS);
                    anel.setEndereco((Endereco) map.get(enderecoId));
                };
                runLater(c);
            }
        }

        if (node.has("_destroy")) {
            anel.setDestroy(node.get("_destroy").asBoolean());
        }

        return anel;
    }

    private Estagio parseEstagio(JsonNode node) {
        Estagio estagio = new Estagio();
        JsonNode id = node.get("id");
        if (id != null) {
            estagio.setId(UUID.fromString(id.asText()));
        }
        if (node.get("idJson") != null) {
            estagio.setIdJson(node.get("idJson").asText());
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
        if (node.get("tempoVerdeDemandaPrioritaria") != null) {
            estagio.setTempoVerdeDemandaPrioritaria(node.get("tempoVerdeDemandaPrioritaria").asInt());
        }
        if (node.get("posicao") != null) {
            estagio.setPosicao(node.get("posicao").asInt());
        }

        if (node.has("imagem")) {
            final String imageId = node.get("imagem").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(IMAGENS);
                estagio.setImagem((Imagem) map.get(imageId));
            };
            runLater(c);

        }

        if (node.has("detector") && node.get("detector").has("idJson")) {
            final String detectorId = node.get("detector").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(DETECTORES);
                estagio.setDetector((Detector) map.get(detectorId));
            };
            runLater(c);

        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                estagio.setAnel((Anel) map.get(anelId));
            };
            runLater(c);

        }

        List<EstagioGrupoSemaforico> estagiosGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagiosGruposSemaforicos", node, estagiosGrupoSemaforicos, ESTAGIO_GRUPO_SEMAFORICO, ESTAGIOS);
        estagio.setEstagiosGruposSemaforicos(estagiosGrupoSemaforicos);

        List<EstagioPlano> estagiosPlanos = new ArrayList<>();
        parseCollection("estagiosPlanos", node, estagiosPlanos, ESTAGIOS_PLANOS, ESTAGIOS);
        estagio.setEstagiosPlanos(estagiosPlanos);

        List<TransicaoProibida> origens = new ArrayList<>();
        parseCollection("origemDeTransicoesProibidas", node, origens, TRANSICAO_PROIBIDA, ESTAGIOS);
        estagio.setOrigemDeTransicoesProibidas(origens);

        List<TransicaoProibida> destinos = new ArrayList<>();
        parseCollection("destinoDeTransicoesProibidas", node, destinos, TRANSICAO_PROIBIDA, ESTAGIOS);
        estagio.setDestinoDeTransicoesProibidas(destinos);

        List<TransicaoProibida> alternativas = new ArrayList<>();
        parseCollection("alternativaDeTransicoesProibidas", node, alternativas, TRANSICAO_PROIBIDA, ESTAGIOS);
        estagio.setAlternativaDeTransicoesProibidas(alternativas);

        return estagio;
    }

    private GrupoSemaforico parseGrupoSemaforico(JsonNode node) {
        GrupoSemaforico grupoSemaforico = new GrupoSemaforico();

        if (node.has("id")) {
            grupoSemaforico.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            grupoSemaforico.setIdJson(node.get("idJson").asText());
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
        if (node.has("tempoVerdeSeguranca")) {
            grupoSemaforico.setTempoVerdeSeguranca(node.get("tempoVerdeSeguranca").asInt());
        }

        List<EstagioGrupoSemaforico> estagiosGrupoSemaforicos = new ArrayList<>();
        parseCollection("estagiosGruposSemaforicos", node, estagiosGrupoSemaforicos, ESTAGIO_GRUPO_SEMAFORICO, GRUPOS_SEMAFORICOS);
        grupoSemaforico.setEstagiosGruposSemaforicos(estagiosGrupoSemaforicos);


        List<VerdesConflitantes> verdesConflitantes = new ArrayList<>();
        parseCollection("verdesConflitantesOrigem", node, verdesConflitantes, VERDES_CONFLITANTES, GRUPOS_SEMAFORICOS);
        grupoSemaforico.setVerdesConflitantesOrigem(verdesConflitantes);


        List<VerdesConflitantes> verdesConflitantesDestino = new ArrayList<>();
        parseCollection("verdesConflitantesDestino", node, verdesConflitantesDestino, VERDES_CONFLITANTES, GRUPOS_SEMAFORICOS);
        grupoSemaforico.setVerdesConflitantesDestino(verdesConflitantesDestino);


        List<TabelaEntreVerdes> tabelasEntreVerdes = new ArrayList<>();
        parseCollection("tabelasEntreVerdes", node, tabelasEntreVerdes, TABELAS_ENTRE_VERDES, GRUPOS_SEMAFORICOS);
        grupoSemaforico.setTabelasEntreVerdes(tabelasEntreVerdes);


        List<Transicao> transicoes = new ArrayList<>();
        parseCollection("transicoes", node, transicoes, TRANSICAO, GRUPOS_SEMAFORICOS);
        parseCollection("transicoesComGanhoDePassagem", node, transicoes, TRANSICAO, GRUPOS_SEMAFORICOS);
        grupoSemaforico.setTransicoes(transicoes);


        if (node.has("descricao")) {
            grupoSemaforico.setDescricao(node.get("descricao").asText());
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                grupoSemaforico.setAnel((Anel) map.get(anelId));
            };
            runLater(c);
        }

        if (node.has("_destroy")) {
            grupoSemaforico.setDestroy(node.get("_destroy").asBoolean());
        }

        return grupoSemaforico;
    }

    private Detector parseDetector(JsonNode node) {
        Detector detector = new Detector();

        if (node.has("id")) {
            detector.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            detector.setIdJson(node.get("idJson").asText());
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
        if (node.has("monitorado")) {
            detector.setMonitorado(node.get("monitorado").asBoolean());
        }
        if (node.has("tempoAusenciaDeteccao")) {
            detector.setTempoAusenciaDeteccao(node.get("tempoAusenciaDeteccao").asInt());
        }
        if (node.has("tempoDeteccaoPermanente")) {
            detector.setTempoDeteccaoPermanente(node.get("tempoDeteccaoPermanente").asInt());
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                detector.setAnel((Anel) map.get(anelId));
            };
            runLater(c);
        }

        if (node.has("estagio") && node.get("estagio").has("idJson")) {
            final String estagioId = node.get("estagio").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
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

        if (node.has("idJson")) {
            transicaoProibida.setIdJson(node.get("idJson").asText());
        }
        if (node.has("_destroy")) {
            transicaoProibida.setDestroy(node.get("_destroy").asBoolean());
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicaoProibida.setOrigem((Estagio) map.get(origemId));
            };
            runLater(c);
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicaoProibida.setDestino((Estagio) map.get(destinoId));
            };
            runLater(c);

        }

        if (node.has("alternativo")) {
            final String alternativoId = node.get("alternativo").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
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
        if (node.has("idJson")) {
            estagioGrupoSemaforico.setIdJson(node.get("idJson").asText());
        }
        if (node.has("_destroy")) {
            estagioGrupoSemaforico.setDestroy(node.get("_destroy").asBoolean());
        }

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                estagioGrupoSemaforico.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };
            runLater(c);

        }

        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("idJson").asText();

            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                estagioGrupoSemaforico.setEstagio((Estagio) map.get(estagioId));
            };
            runLater(c);

        }

        return estagioGrupoSemaforico;
    }

    private VerdesConflitantes parseVerdesConflitante(JsonNode node) {
        VerdesConflitantes verdesConflitantes = new VerdesConflitantes();

        if (node.has("id") && node.get("id") != null) {
            verdesConflitantes.setId(UUID.fromString(node.get("id").asText()));
        }

        if (node.has("_destroy")) {
            verdesConflitantes.setDestroy(node.get("_destroy").asBoolean());
        }

        if (node.has("idJson")) {
            verdesConflitantes.setIdJson(node.get("idJson").asText());
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("idJson").asText();

            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                verdesConflitantes.setOrigem((GrupoSemaforico) map.get(origemId));
            };
            runLater(c);
        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("idJson").asText();

            Consumer<Map<String, Map>> c = (caches) -> {
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

        if (node.has("idJson")) {
            transicao.setIdJson(node.get("idJson").asText());
        }

        if (node.has("tipo")) {
            transicao.setTipo(TipoTransicao.valueOf(node.get("tipo").asText()));
        }

        if (node.has("origem")) {
            final String origemId = node.get("origem").get("idJson").asText();

            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicao.setOrigem((Estagio) map.get(origemId));
            };

            runLater(c);

        }

        if (node.has("destino")) {
            final String destinoId = node.get("destino").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                transicao.setDestino((Estagio) map.get(destinoId));
            };

            runLater(c);

        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<>();
        parseCollection("tabelaEntreVerdesTransicoes", node, tabelaEntreVerdesTransicoes, TABELA_ENTRE_VERDES_TRANSICAO, TRANSICAO);
        transicao.setTabelaEntreVerdesTransicoes(tabelaEntreVerdesTransicoes);

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                transicao.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };

            runLater(c);

        }

        if (node.has("atrasoDeGrupo")) {
            final String atrasoDeGrupoId = node.get("atrasoDeGrupo").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ATRASO_DE_GRUPO);
                AtrasoDeGrupo atrasoDeGrupo = (AtrasoDeGrupo) map.get(atrasoDeGrupoId);
                atrasoDeGrupo.setTransicao(transicao);
                transicao.setAtrasoDeGrupo(atrasoDeGrupo);
            };
            runLater(c);
        }

        if (node.has("modoIntermitenteOuApagado")) {
            transicao.setModoIntermitenteOuApagado(node.get("modoIntermitenteOuApagado").asBoolean());
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

        if (node.has("idJson")) {
            tabelaEntreVerdes.setIdJson(node.get("idJson").asText());
        }
        if (node.has("_destroy")) {
            tabelaEntreVerdes.setDestroy(node.get("_destroy").asBoolean());
        }
        tabelaEntreVerdes.setDescricao(node.get("descricao") != null ? node.get("descricao").asText() : null);
        tabelaEntreVerdes.setPosicao(node.get("posicao") != null ? node.get("posicao").asInt() : null);

        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                tabelaEntreVerdes.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };

            runLater(c);

        }

        List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes = new ArrayList<TabelaEntreVerdesTransicao>();
        parseCollection("tabelaEntreVerdesTransicoes", node, tabelaEntreVerdesTransicoes, TABELA_ENTRE_VERDES_TRANSICAO, TABELAS_ENTRE_VERDES);
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
        if (node.has("idJson")) {
            tabelaEntreVerdesTransicao.setIdJson(node.get("idJson").asText());
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

        if (node.has("transicao")) {
            final String transicaoId = node.get("transicao").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(TRANSICAO);
                tabelaEntreVerdesTransicao.setTransicao((Transicao) map.get(transicaoId));
            };
            runLater(c);
        }

        if (node.has("tabelaEntreVerdes")) {
            final String tabelaEntreVerdesId = node.get("tabelaEntreVerdes").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(TABELAS_ENTRE_VERDES);
                tabelaEntreVerdesTransicao.setTabelaEntreVerdes((TabelaEntreVerdes) map.get(tabelaEntreVerdesId));
            };
            runLater(c);
        }

        return tabelaEntreVerdesTransicao;
    }

    private VersaoPlano parseVersaoPlano(JsonNode node, Usuario usuario) {
        VersaoPlano versaoPlano;
        if (usuario == null) {
            versaoPlano = new VersaoPlano();
        } else {
            versaoPlano = new VersaoPlano(usuario);
        }

        if (node.has("id")) {
            versaoPlano.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            versaoPlano.setIdJson(node.get("idJson").asText());
        }
        if (node.has("descricao")) {
            versaoPlano.setDescricao(node.get("descricao").asText());
        }
        if (node.has("statusVersao")) {
            versaoPlano.setStatusVersao(StatusVersao.valueOf(node.get("statusVersao").asText()));
        }
        if (node.has("anel")) {
            final String anelId = node.get("anel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                versaoPlano.setAnel((Anel) map.get(anelId));
            };

            runLater(c);
        }

        List<Plano> planos = new ArrayList<Plano>();
        parseCollection("planos", node, planos, PLANOS, VERSOES_PLANOS);
        versaoPlano.setPlanos(planos);

        return versaoPlano;
    }

    private Plano parsePlano(JsonNode node) {
        Plano plano = new Plano();

        if (node.has("id")) {
            plano.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            plano.setIdJson(node.get("idJson").asText());
        }
        if (node.has("posicao")) {
            plano.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("descricao")) {
            plano.setDescricao(node.get("descricao").asText());
        }
        if (node.has("tempoCiclo")) {
            plano.setTempoCiclo(node.get("tempoCiclo").asInt());
        }
        if (node.has("defasagem")) {
            plano.setDefasagem(node.get("defasagem").asInt());
        }
        if (node.has("posicaoTabelaEntreVerde")) {
            plano.setPosicaoTabelaEntreVerde(node.get("posicaoTabelaEntreVerde").asInt());
        }
        if (node.has("modoOperacao")) {
            plano.setModoOperacao(ModoOperacaoPlano.valueOf(node.get("modoOperacao").asText()));
        }
        if (node.has("cicloDuplo")) {
            plano.setCicloDuplo(node.get("cicloDuplo").asBoolean());
        }
        if (node.has("tempoCicloDuplo")) {
            plano.setTempoCicloDuplo(node.get("tempoCicloDuplo").asInt());
        }

        if (node.has("versaoPlano") && node.get("versaoPlano").has("idJson")) {
            final String versaoPlanoId = node.get("versaoPlano").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(VERSOES_PLANOS);
                plano.setVersaoPlano((VersaoPlano) map.get(versaoPlanoId));
            };
            runLater(c);
        }

        List<GrupoSemaforicoPlano> grupoSemaforicosPlanos = new ArrayList<>();
        parseCollection("gruposSemaforicosPlanos", node, grupoSemaforicosPlanos, GRUPOS_SEMAFORICOS_PLANOS, PLANOS);
        plano.setGruposSemaforicosPlanos(grupoSemaforicosPlanos);

        List<EstagioPlano> estagioPlanos = new ArrayList<>();
        parseCollection("estagiosPlanos", node, estagioPlanos, ESTAGIOS_PLANOS, PLANOS);
        plano.setEstagiosPlanos(estagioPlanos);

        return plano;
    }

    private GrupoSemaforicoPlano parseGruposSemaforicosPlano(JsonNode node) {
        GrupoSemaforicoPlano grupoSemaforicoPlano = new GrupoSemaforicoPlano();
        if (node.has("id")) {
            grupoSemaforicoPlano.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            grupoSemaforicoPlano.setIdJson(node.get("idJson").asText());
        }
        if (node.has("ativado")) {
            grupoSemaforicoPlano.setAtivado(node.get("ativado").asBoolean());
        }
        if (node.has("destroy")) {
            grupoSemaforicoPlano.setDestroy(node.get("destroy").asBoolean());
        }
        if (node.has("plano")) {
            final String planoId = node.get("plano").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(PLANOS);
                grupoSemaforicoPlano.setPlano((Plano) map.get(planoId));
            };

            runLater(c);
        }
        if (node.has("grupoSemaforico")) {
            final String grupoSemaforicoId = node.get("grupoSemaforico").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(GRUPOS_SEMAFORICOS);
                grupoSemaforicoPlano.setGrupoSemaforico((GrupoSemaforico) map.get(grupoSemaforicoId));
            };

            runLater(c);
        }

        return grupoSemaforicoPlano;
    }


    private EstagioPlano parseEstagiosPlano(JsonNode node) {
        EstagioPlano estagioPlano = new EstagioPlano();
        if (node.has("id")) {
            estagioPlano.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            estagioPlano.setIdJson(node.get("idJson").asText());
        }

        if (node.has("posicao")) {
            estagioPlano.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("tempoVerde")) {
            estagioPlano.setTempoVerde(node.get("tempoVerde").asInt());
        }
        if (node.has("tempoVerdeMinimo")) {
            estagioPlano.setTempoVerdeMinimo(node.get("tempoVerdeMinimo").asInt());
        }
        if (node.has("tempoVerdeMaximo")) {
            estagioPlano.setTempoVerdeMaximo(node.get("tempoVerdeMaximo").asInt());
        }
        if (node.has("tempoVerdeIntermediario")) {
            estagioPlano.setTempoVerdeIntermediario(node.get("tempoVerdeIntermediario").asInt());
        }
        if (node.has("tempoExtensaoVerde")) {
            estagioPlano.setTempoExtensaoVerde(node.get("tempoExtensaoVerde").asDouble());
        }
        if (node.has("dispensavel")) {
            estagioPlano.setDispensavel(node.get("dispensavel").asBoolean());
        }
        if (node.has("destroy")) {
            estagioPlano.setDestroy(node.get("destroy").asBoolean());
        }
        if (node.has("estagioQueRecebeEstagioDispensavel") && node.get("estagioQueRecebeEstagioDispensavel").has("idJson")) {
            final String estagioPlanoId = node.get("estagioQueRecebeEstagioDispensavel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS_PLANOS);
                estagioPlano.setEstagioQueRecebeEstagioDispensavel((EstagioPlano) map.get(estagioPlanoId));
            };

            runLater(c);
        }
        if (node.has("plano")) {
            final String planoId = node.get("plano").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(PLANOS);
                estagioPlano.setPlano((Plano) map.get(planoId));
            };

            runLater(c);
        }
        if (node.has("estagio")) {
            final String estagioId = node.get("estagio").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ESTAGIOS);
                Estagio estagio = (Estagio) map.get(estagioId);
                estagioPlano.setEstagio(estagio);
                estagio.addEstagioPlano(estagioPlano);
            };

            runLater(c);
        }

        return estagioPlano;
    }

    private VersaoTabelaHoraria parseVersaoTabelaHoraria(JsonNode node, Usuario usuario) {
        VersaoTabelaHoraria versaoTabelaHoraria;
        if (usuario == null) {
            versaoTabelaHoraria = new VersaoTabelaHoraria();
        } else {
            versaoTabelaHoraria = new VersaoTabelaHoraria(usuario);
        }

        if (node.has("id")) {
            versaoTabelaHoraria.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("idJson")) {
            versaoTabelaHoraria.setIdJson(node.get("idJson").asText());
        }
        if (node.has("descricao")) {
            versaoTabelaHoraria.setDescricao(node.get("descricao").asText());
        }
        if (node.has("statusVersao")) {
            versaoTabelaHoraria.setStatusVersao(StatusVersao.valueOf(node.get("statusVersao").asText()));
        }
        if (node.has("tabelaHorariaOrigem") && node.get("tabelaHorariaOrigem").has("idJson")) {
            final String tabelaHorariaOrigemId = node.get("tabelaHorariaOrigem").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                versaoTabelaHoraria.setTabelaHorariaOrigem((TabelaHorario) caches.get(TABELAS_HORARIAS).get(tabelaHorariaOrigemId));
            };
            runLater(c);
        }
        if (node.has("tabelaHoraria") && node.get("tabelaHoraria").has("idJson")) {
            final String tabelaHorariaId = node.get("tabelaHoraria").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(TABELAS_HORARIAS);
                versaoTabelaHoraria.setTabelaHoraria((TabelaHorario) map.get(tabelaHorariaId));
            };
            runLater(c);
        }

        versaoTabelaHoraria.setControlador(controlador);

        return versaoTabelaHoraria;
    }

    private TabelaHorario parseTabelaHoraria(JsonNode node) {
        TabelaHorario tabelaHoraria = new TabelaHorario();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                tabelaHoraria.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            tabelaHoraria.setIdJson(node.get("idJson").asText());
        }

        if (node.has("versaoTabelaHoraria") && node.get("versaoTabelaHoraria").has("idJson")) {
            final String versaoTabelaHorariaId = node.get("versaoTabelaHoraria").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(VERSOES_TABELAS_HORARIAS);
                tabelaHoraria.setVersaoTabelaHoraria((VersaoTabelaHoraria) map.get(versaoTabelaHorariaId));
            };
            runLater(c);
        }

        List<Evento> eventos = new ArrayList<>();
        parseCollection("eventos", node, eventos, EVENTOS, null);
        tabelaHoraria.setEventos(eventos);

        return tabelaHoraria;
    }

    private Evento parseEvento(JsonNode node) {
        Evento evento = new Evento();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                evento.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            evento.setIdJson(node.get("idJson").asText());
        }

        if (node.has("posicao")) {
            evento.setPosicao(node.get("posicao").asInt());
        }

        if (node.has("tipo")) {
            evento.setTipo(TipoEvento.valueOf(node.get("tipo").asText()));
        }

        if (node.has("diaDaSemana")) {
            evento.setDiaDaSemana(DiaDaSemana.get(node.get("diaDaSemana").asText()));
        }

        if (node.has("nome")) {
            evento.setNome(node.get("nome").asText());
        }

        if (node.has("data")) {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                evento.setData(formatter.parse(node.get("data").asText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (node.has("horario")) {
            evento.setHorario(LocalTime.parse(node.get("horario").asText()));
        }

        if (node.has("posicaoPlano")) {
            evento.setPosicaoPlano(node.get("posicaoPlano").asInt());
        }

        if (node.has("tabelaHoraria") && node.get("tabelaHoraria").has("idJson")) {
            final String tabelaHorarioId = node.get("tabelaHoraria").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(TABELAS_HORARIAS);
                evento.setTabelaHorario((TabelaHorario) map.get(tabelaHorarioId));
            };
            runLater(c);
        }

        if (node.has("_destroy")) {
            evento.setDestroy(node.get("_destroy").asBoolean());
        }

        return evento;
    }

    private Endereco parseEndereco(JsonNode node) {
        Endereco endereco = new Endereco();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                endereco.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            endereco.setIdJson(node.get("idJson").asText());
        }

        endereco.setLocalizacao(node.get("localizacao") != null ? node.get("localizacao").asText() : null);
        endereco.setLatitude((node.get("latitude") != null && node.get("latitude").isNumber()) ? node.get("latitude").asDouble() : null);
        endereco.setLongitude((node.get("longitude") != null && node.get("longitude").isNumber()) ? node.get("longitude").asDouble() : null);

        if (node.has("localizacao2")) {
            endereco.setLocalizacao2(node.get("localizacao2").asText());
        }
        if (node.has("alturaNumerica")) {
            endereco.setAlturaNumerica(node.get("alturaNumerica").asInt());
        }
        if (node.has("referencia")) {
            endereco.setReferencia(node.get("referencia").asText());
        }

        return endereco;
    }

    private Area parseArea(JsonNode node) {
        Area area = new Area();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                area.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            area.setIdJson(node.get("idJson").asText());
        }

        if (node.has("descricao")) {
            area.setDescricao(node.get("descricao").asInt());
        }

        if (node.has("cidade")) {
            final String cidadeId = node.get("cidade").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(CIDADES);
                area.setCidade((Cidade) map.get(cidadeId));
            };

            runLater(c);
        }

        return area;
    }

    private Cidade parseCidade(JsonNode node) {
        Cidade cidade = new Cidade();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                cidade.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            cidade.setIdJson(node.get("idJson").asText());
        }

        if (node.has("nome")) {
            cidade.setNome(node.get("nome").asText());
        }

        List<Area> areas = new ArrayList<>();
        parseCollection("areas", node, areas, AREAS, CIDADES);
        cidade.setAreas(areas);


        return cidade;
    }

    private Imagem parseImagem(JsonNode node) {

        Imagem imagem = new Imagem();

        if (node.has("id")) {
            JsonNode id = node.get("id");
            if (!id.isNull()) {
                imagem.setId(UUID.fromString(id.asText()));
            }
        }

        if (node.has("idJson")) {
            imagem.setIdJson(node.get("idJson").asText());
        }

        if (node.get("fileName") != null) {
            imagem.setFilename(node.get("fileName").asText());
        }

        if (node.get("contentType") != null) {
            imagem.setFilename(node.get("contentType").asText());
        }

        return imagem;
    }

    private AtrasoDeGrupo parseAtrasoDeGrupo(JsonNode node) {
        AtrasoDeGrupo atrasoDeGrupo = new AtrasoDeGrupo();

        if (node.get("id") != null) {
            atrasoDeGrupo.setId(UUID.fromString(node.get("id").asText()));
        }

        if (node.get("idJson") != null) {
            atrasoDeGrupo.setIdJson(node.get("idJson").asText());
        }

        if (node.get("atrasoDeGrupo") != null) {
            atrasoDeGrupo.setAtrasoDeGrupo(node.get("atrasoDeGrupo").asInt());
        }

        return atrasoDeGrupo;
    }

    private void parseDadosBasicos(JsonNode node) {
        JsonNode id = node.get("id");
        if (id != null) {
            controlador.setId(UUID.fromString(id.asText()));
        }

        if (node.has("area")) {
            final String areaId = node.get("area").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(AREAS);
                controlador.setArea((Area) map.get(areaId));
            };
            runLater(c);
        }

        if (node.has("exclusivoParaTeste")) {
            controlador.setExclusivoParaTeste(node.get("exclusivoParaTeste").asBoolean());
        }

        if (node.has("subarea") && !node.get("subarea").get("id").isNull()) {
            JsonNode subareaNode = node.get("subarea");
            Subarea subarea = new Subarea();
            subarea.setId(UUID.fromString(subareaNode.get("id").asText()));

            if (subareaNode.has("idJson")) {
                subarea.setIdJson(subareaNode.get("idJson").asText());
            }
            if (subareaNode.has("nome")) {
                subarea.setNome(subareaNode.get("nome").asText());
            }
            if (subareaNode.has("numero")) {
                subarea.setNumero(subareaNode.get("numero").asInt());
            }
            if (subareaNode.has("tempoCiclo")) {
                subarea.setTempoCiclo(Json.fromJson(subareaNode.get("tempoCiclo"), HashMap.class));
            }
            if (subareaNode.has("area") && subareaNode.get("area").get("idJson") != null) {
                final String areaIdJson = subareaNode.get("area").get("idJson").asText();
                Consumer<Map<String, Map>> c = (caches) -> {
                    Map map = caches.get(AREAS);
                    subarea.setArea((Area) map.get(areaIdJson));
                };
                runLater(c);
            }
            controlador.setSubarea(subarea);
            subarea.addControlador(controlador);
        }

        if (node.has("modelo") && node.get("modelo").get("id") != null) {
            JsonNode modeloNode = node.get("modelo");
            ModeloControlador modelo = new ModeloControlador();
            modelo.setId(UUID.fromString(modeloNode.get("id").asText()));

            if (modeloNode.has("idJson")) {
                modelo.setIdJson(modeloNode.get("idJson").asText());
            }

            if (modeloNode.has("descricao")) {
                modelo.setDescricao(modeloNode.get("descricao").asText());
            }

            if (modeloNode.has("fabricante") && modeloNode.get("fabricante").has("id")) {
                JsonNode fabricanteNode = modeloNode.get("fabricante");
                Fabricante fabricante = new Fabricante();
                fabricante.setId(UUID.fromString(fabricanteNode.get("id").asText()));

                if (fabricanteNode.has("nome")) {
                    fabricante.setNome(fabricanteNode.get("nome").asText());
                }
                modelo.setFabricante(fabricante);
            }

            if (modeloNode.has("limiteAnel")) {
                modelo.setLimiteAnel(modeloNode.get("limiteAnel").asInt());
            } else if (node.has("limiteAnel")) {
                modelo.setLimiteAnel(node.get("limiteAnel").asInt());
            }

            if (modeloNode.has("limiteDetectorPedestre")) {
                modelo.setLimiteDetectorPedestre(modeloNode.get("limiteDetectorPedestre").asInt());
            } else if (node.has("limiteDetectorPedestre")) {
                modelo.setLimiteDetectorPedestre(node.get("limiteDetectorPedestre").asInt());
            }

            if (modeloNode.has("limiteDetectorVeicular")) {
                modelo.setLimiteDetectorVeicular(modeloNode.get("limiteDetectorVeicular").asInt());
            } else if (node.has("limiteDetectorVeicular")) {
                modelo.setLimiteDetectorVeicular(node.get("limiteDetectorVeicular").asInt());
            }

            if (modeloNode.has("limiteEstagio")) {
                modelo.setLimiteEstagio(modeloNode.get("limiteEstagio").asInt());
            } else if (node.has("limiteEstagio")) {
                modelo.setLimiteEstagio(node.get("limiteEstagio").asInt());
            }

            if (modeloNode.has("limiteGrupoSemaforico")) {
                modelo.setLimiteGrupoSemaforico(modeloNode.get("limiteGrupoSemaforico").asInt());
            } else if (node.has("limiteGrupoSemaforico")) {
                modelo.setLimiteGrupoSemaforico(node.get("limiteGrupoSemaforico").asInt());
            }

            if (modeloNode.has("limitePlanos")) {
                modelo.setLimitePlanos(modeloNode.get("limitePlanos").asInt());
            } else if (node.has("limitePlanos")) {
                modelo.setLimitePlanos(node.get("limitePlanos").asInt());
            }

            if (modeloNode.has("limiteTabelasEntreVerdes")) {
                modelo.setLimiteTabelasEntreVerdes(modeloNode.get("limiteTabelasEntreVerdes").asInt());
            } else if (node.has("limiteTabelasEntreVerdes")) {
                modelo.setLimiteTabelasEntreVerdes(node.get("limiteTabelasEntreVerdes").asInt());
            }


            controlador.setModelo(modelo);
        }

        if (node.has("croqui") && node.get("croqui").get("id") != null) {
            final String imageId = node.get("croqui").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(IMAGENS);
                controlador.setCroqui((Imagem) map.get(imageId));
            };
            runLater(c);
        }

        if (node.has("versaoControlador") && !node.get("versaoControlador").get("id").isNull()) {
            VersaoControlador versao = new VersaoControlador();
            JsonNode versaoNode = node.get("versaoControlador");
            versao.setId(UUID.fromString(versaoNode.get("id").asText()));

            if (versaoNode.has("idJson") && !versaoNode.get("idJson").isNull()) {
                versao.setIdJson(versaoNode.get("idJson").asText());
            }

            if (versaoNode.has("descricao")) {
                versao.setDescricao(versaoNode.get("descricao").asText());
            }

            if (versaoNode.has("statusVersao")) {
                versao.setStatusVersao(StatusVersao.valueOf(versaoNode.get("statusVersao").asText()));
            }

            if (versaoNode.has("usuario")) {
                JsonNode usuarioNode = versaoNode.get("usuario");
                Usuario usuario = new Usuario();

                if (usuarioNode.has("id") && !usuarioNode.get("id").isNull()) {
                    usuario.setId(UUID.fromString(usuarioNode.get("id").asText()));
                }

                if (usuarioNode.has("nome")) {
                    usuario.setNome(usuarioNode.get("nome").asText());
                }

                if (usuarioNode.has("login")) {
                    usuario.setLogin(usuarioNode.get("login").asText());
                }

                if (usuarioNode.has("email")) {
                    usuario.setEmail(usuarioNode.get("email").asText());
                }

                if (usuarioNode.has("area") && !usuarioNode.get("area").get("idJson").isNull()) {
                    final String areaIdJson = usuarioNode.get("area").get("idJson").asText();
                    Consumer<Map<String, Map>> c = (caches) -> {
                        Map map = caches.get(AREAS);
                        usuario.setArea((Area) map.get(areaIdJson));
                    };
                    runLater(c);
                }

                versao.setUsuario(usuario);
            }

            if (versaoNode.has("controladorOrigem") && versaoNode.get("controladorOrigem").get("id") != null) {
                Controlador controladorOrigem = new Controlador();
                controladorOrigem.setId(UUID.fromString(versaoNode.get("controladorOrigem").get("id").asText()));
                versao.setOrigem(controladorOrigem);
            }

            if (versaoNode.has("controladorFisico") && versaoNode.get("controladorFisico").get("id") != null) {
                ControladorFisico controladorFisico = new ControladorFisico();
                controladorFisico.setId(UUID.fromString(versaoNode.get("controladorFisico").get("id").asText()));
                versao.setControladorFisico(controladorFisico);
            }
            versao.setControlador(controlador);
            controlador.setVersaoControlador(versao);
        }

        if (node.get("bloqueado") != null) {
            controlador.setBloqueado(node.get("bloqueado").asBoolean());
        }

        if (node.get("planosBloqueado") != null) {
            controlador.setPlanosBloqueado(node.get("planosBloqueado").asBoolean());
        }

        controlador.setNumeroSMEE(node.get("numeroSMEE") != null ? node.get("numeroSMEE").asText() : null);
        controlador.setNumeroSMEEConjugado1(node.get("numeroSMEEConjugado1") != null ? node.get("numeroSMEEConjugado1").asText() : null);
        controlador.setNumeroSMEEConjugado2(node.get("numeroSMEEConjugado2") != null ? node.get("numeroSMEEConjugado2").asText() : null);
        controlador.setNumeroSMEEConjugado3(node.get("numeroSMEEConjugado3") != null ? node.get("numeroSMEEConjugado3").asText() : null);
        controlador.setFirmware(node.get("firmware") != null ? node.get("firmware").asText() : null);
        controlador.setSequencia(node.get("sequencia") != null ? node.get("sequencia").asInt() : null);
        controlador.setNomeEndereco(node.get("nomeEndereco") != null ? node.get("nomeEndereco").asText() : null);

        if (node.has("endereco")) {
            final String enderecoId = node.get("endereco").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ENDERECOS);
                controlador.setEndereco((Endereco) map.get(enderecoId));
            };
            runLater(c);
        }

        FaixasDeValores valores = parseFaixasDeValores(node);
        controlador.setRangeUtils(RangeUtils.getInstance(valores));
    }

    private FaixasDeValores parseFaixasDeValores(JsonNode node) {
        FaixasDeValores valores = new FaixasDeValores();

        if (node.has("verdeMin")) {
            valores.setTempoVerdeMin(node.get("verdeMin").asInt());
        }
        if (node.has("verdeMax")) {
            valores.setTempoVerdeMax(node.get("verdeMax").asInt());
        }
        if (node.has("verdeMinimoMin")) {
            valores.setTempoVerdeMinimoMin(node.get("verdeMinimoMin").asInt());
        }
        if (node.has("verdeMinimoMax")) {
            valores.setTempoVerdeMinimoMax(node.get("verdeMinimoMax").asInt());
        }
        if (node.has("verdeMaximoMin")) {
            valores.setTempoVerdeMaximoMin(node.get("verdeMaximoMin").asInt());
        }
        if (node.has("verdeMaximoMax")) {
            valores.setTempoVerdeMaximoMax(node.get("verdeMaximoMax").asInt());
        }
        if (node.has("extensaoVerdeMin")) {
            valores.setTempoExtensaoVerdeMin(node.get("extensaoVerdeMin").asDouble());
        }
        if (node.has("extensaoVerdeMax")) {
            valores.setTempoExtensaoVerdeMax(node.get("extensaoVerdeMax").asDouble());
        }
        if (node.has("verdeIntermediarioMin")) {
            valores.setTempoVerdeIntermediarioMin(node.get("verdeIntermediarioMin").asInt());
        }
        if (node.has("verdeIntermediarioMax")) {
            valores.setTempoVerdeIntermediarioMax(node.get("verdeIntermediarioMax").asInt());
        }
        if (node.has("defasagemMin")) {
            valores.setTempoDefasagemMin(node.get("defasagemMin").asInt());
        }
        if (node.has("defasagemMax")) {
            valores.setTempoDefasagemMax(node.get("defasagemMax").asInt());
        }
        if (node.has("amareloMin")) {
            valores.setTempoAmareloMin(node.get("amareloMin").asInt());
        }
        if (node.has("amareloMax")) {
            valores.setTempoAmareloMax(node.get("amareloMax").asInt());
        }
        if (node.has("vermelhoIntermitenteMin")) {
            valores.setTempoVermelhoIntermitenteMin(node.get("vermelhoIntermitenteMin").asInt());
        }
        if (node.has("vermelhoIntermitenteMax")) {
            valores.setTempoVermelhoIntermitenteMax(node.get("vermelhoIntermitenteMax").asInt());
        }
        if (node.has("vermelhoLimpezaVeicularMin")) {
            valores.setTempoVermelhoLimpezaVeicularMin(node.get("vermelhoLimpezaVeicularMin").asInt());
        }
        if (node.has("vermelhoLimpezaVeicularMax")) {
            valores.setTempoVermelhoLimpezaVeicularMax(node.get("vermelhoLimpezaVeicularMax").asInt());
        }
        if (node.has("vermelhoLimpezaPedestreMin")) {
            valores.setTempoVermelhoLimpezaPedestreMin(node.get("vermelhoLimpezaPedestreMin").asInt());
        }
        if (node.has("vermelhoLimpezaPedestreMax")) {
            valores.setTempoVermelhoLimpezaPedestreMax(node.get("vermelhoLimpezaPedestreMax").asInt());
        }
        if (node.has("atrasoGrupoMin")) {
            valores.setTempoAtrasoGrupoMin(node.get("atrasoGrupoMin").asInt());
        }
        if (node.has("atrasoGrupoMax")) {
            valores.setTempoAtrasoGrupoMax(node.get("atrasoGrupoMax").asInt());
        }
        if (node.has("verdeSegurancaVeicularMin")) {
            valores.setTempoVerdeSegurancaVeicularMin(node.get("verdeSegurancaVeicularMin").asInt());
        }
        if (node.has("verdeSegurancaVeicularMax")) {
            valores.setTempoVerdeSegurancaVeicularMax(node.get("verdeSegurancaVeicularMax").asInt());
        }
        if (node.has("verdeSegurancaPedestreMin")) {
            valores.setTempoVerdeSegurancaPedestreMin(node.get("verdeSegurancaPedestreMin").asInt());
        }
        if (node.has("verdeSegurancaPedestreMax")) {
            valores.setTempoVerdeSegurancaPedestreMax(node.get("verdeSegurancaPedestreMax").asInt());
        }
        if (node.has("maximoPermanenciaEstagioMin")) {
            valores.setTempoMaximoPermanenciaEstagioMin(node.get("maximoPermanenciaEstagioMin").asInt());
        }
        if (node.has("maximoPermanenciaEstagioMax")) {
            valores.setTempoMaximoPermanenciaEstagioMax(node.get("maximoPermanenciaEstagioMax").asInt());
        }
        if (node.has("defaultMaximoPermanenciaEstagioVeicular")) {
            valores.setDefaultTempoMaximoPermanenciaEstagioVeicular(node.get("defaultMaximoPermanenciaEstagioVeicular").asInt());
        }
        if (node.has("cicloMin")) {
            valores.setTempoCicloMin(node.get("cicloMin").asInt());
        }
        if (node.has("cicloMax")) {
            valores.setTempoCicloMax(node.get("cicloMax").asInt());
        }
        if (node.has("ausenciaDeteccaoMin")) {
            valores.setTempoAusenciaDeteccaoMin(node.get("ausenciaDeteccaoMin").asInt());
        }
        if (node.has("ausenciaDeteccaoMax")) {
            valores.setTempoAusenciaDeteccaoMax(node.get("ausenciaDeteccaoMax").asInt());
        }
        if (node.has("deteccaoPermanenteMin")) {
            valores.setTempoDeteccaoPermanenteMin(node.get("deteccaoPermanenteMin").asInt());
        }
        if (node.has("deteccaoPermanenteMax")) {
            valores.setTempoDeteccaoPermanenteMax(node.get("deteccaoPermanenteMax").asInt());
        }
        return valores;
    }

    private void parseCollection(String collection, JsonNode node, List list, final String cacheContainer, final String callie) {
        if (node.has(collection)) {
            for (JsonNode innerNode : node.get(collection)) {
                if (innerNode.has("idJson")) {
                    final String id = innerNode.get("idJson").asText();
                    Consumer<Map<String, Map>> c = (caches) -> {
                        assert cacheContainer != null;
                        Map map = caches.get(cacheContainer);
                        if (cacheContainer != "planos") {
                            // o APP envia TODOS os planos para a API ao criar um novo plano.
                            // Ao fazer um teste com um JSON idêntico ao enviado pelo APP, esse
                            // assert falhou (fica desligado em DEV e PROD), por isso esse `if`.
                            assert map.containsKey(id);
                        }

                        list.add(map.get(id));
                    };

                    runLater(c);
                } else {
                    throw new RuntimeException(innerNode.toString() + " - Não contem idJson");
                }
            }
        }
    }

    private void runLater(Consumer<Map<String, Map>> c) {
        consumers.add(c);
    }

}
