package json;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import org.joda.time.LocalTime;

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
    public static final String EVENTOS = "eventos";
    public static final String ENDERECOS = "enderecos";
    public static final String CIDADES = "cidades";
    public static final String AREAS = "areas";
    public static final String IMAGENS = "imagens";
    private Controlador controlador = new Controlador();
    private Map<String, Map<String, Object>> models;

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
    private Map<String, GrupoSemaforicoPlano> gruposSemaforicosPlanosCache;
    private Map<String, EstagioPlano> estagiosPlanosCache;
    private Map<String, Evento> eventosCache;
    private Map<String, Endereco> enderecosCache;
    private Map<String, Area> areasCache;
    private Map<String, Cidade> cidadesCache;
    private Map<String, Imagem> imagensCache;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Consumer<Map<String, Map>>> consumers = new ArrayList<Consumer<Map<String, Map>>>();
    private Map<String, Map> caches = new HashMap<String, Map>();


    public ControladorCustomDeserializer() {
        aneisCache = new HashMap<String, Anel>();
        caches.put(ANEIS, aneisCache);

        estagiosCache = new HashMap<String, Estagio>();
        caches.put(ESTAGIOS, estagiosCache);

        gruposSemaforicosCache = new HashMap<String, GrupoSemaforico>();
        caches.put(GRUPOS_SEMAFORICOS, gruposSemaforicosCache);

        detectoresCache = new HashMap<String, Detector>();
        caches.put(DETECTORES, detectoresCache);

        transicaoProibidaCache = new HashMap<String, TransicaoProibida>();
        caches.put(TRANSICAO_PROIBIDA, transicaoProibidaCache);

        estagioGrupoSemaforicoCache = new HashMap<String, EstagioGrupoSemaforico>();
        caches.put(ESTAGIO_GRUPO_SEMAFORICO, estagioGrupoSemaforicoCache);

        verdesConflitantesCache = new HashMap<String, VerdesConflitantes>();
        caches.put(VERDES_CONFLITANTES, verdesConflitantesCache);

        transicaoCache = new HashMap<String, Transicao>();
        caches.put(TRANSICAO, transicaoCache);

        tabelasEntreVerdesCache = new HashMap<String, TabelaEntreVerdes>();
        caches.put(TABELAS_ENTRE_VERDES, tabelasEntreVerdesCache);

        tabelaEntreVerdesTransicaoCache = new HashMap<String, TabelaEntreVerdesTransicao>();
        caches.put(TABELA_ENTRE_VERDES_TRANSICAO, tabelaEntreVerdesTransicaoCache);

        planosCache = new HashMap<String, Plano>();
        caches.put(PLANOS, planosCache);

        gruposSemaforicosPlanosCache = new HashMap<String, GrupoSemaforicoPlano>();
        caches.put(GRUPOS_SEMAFORICOS_PLANOS, gruposSemaforicosPlanosCache);

        estagiosPlanosCache = new HashMap<String, EstagioPlano>();
        caches.put(ESTAGIOS_PLANOS, estagiosPlanosCache);

        eventosCache = new HashMap<String, Evento>();
        caches.put(EVENTOS, eventosCache);

        areasCache = new HashMap<String, Area>();
        caches.put(AREAS, areasCache);

        cidadesCache = new HashMap<String, Cidade>();
        caches.put(CIDADES, cidadesCache);

        enderecosCache = new HashMap<String, Endereco>();
        caches.put(ENDERECOS, enderecosCache);

        imagensCache = new HashMap<String, Imagem>();
        caches.put(IMAGENS, imagensCache);

        atrasoDeGrupoCache = new HashMap<String, AtrasoDeGrupo>();
        caches.put(ATRASO_DE_GRUPO, atrasoDeGrupoCache);
    }

    public Controlador getControladorFromJson(JsonNode node) {
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
        parseTabelaHoraria(node);
        parseEventos(node);
        parseImagens(node);
        parseDadosBasicos(node);
        parseEnderecos(node);
        parseCidades(node);
        parseAreas(node);
        parseAtrasosDeGrupo(node);

        consumers.stream().forEach(c -> {
            c.accept(caches);
        });

        controlador.deleteAnelSeNecessario();
        deleteGruposSemaforicos(controlador);
        deleteVerdesConflitantes(controlador);
        deleteEstagiosGruposSemaforicos(controlador);
        deleteTransicoesProibidas(controlador);
        deleteTabelasEntreVerdes(controlador);

        return controlador;
    }

    private void deleteTabelasEntreVerdes(Controlador c) {
        if (c.getId() != null) {
            Controlador controladorAux = Controlador.find.byId(c.getId());
            if (controladorAux != null) {
                controladorAux.getAneis().forEach(anel -> {
                    anel.getGruposSemaforicos().forEach(grupo -> {
                        grupo.getTabelasEntreVerdes().forEach(Model::delete);
                    });
                });
            }
        }
    }

    private void deleteVerdesConflitantes(Controlador c) {
        if (c.getId() != null) {
            Controlador controladorAux = Controlador.find.byId(c.getId());
            controladorAux.getAneis().forEach(anel -> {
                anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                    grupoSemaforico.getVerdesConflitantes().forEach(verdesConflitantes -> verdesConflitantes.delete());
                });
            });
        }
    }

    private void deleteGruposSemaforicos(Controlador controlador) {
        for (GrupoSemaforico grupoSemaforico : controlador.getGruposSemaforicos()) {
            if (grupoSemaforico.isDestroy()) {
                grupoSemaforico.delete();
            }
        }
    }

    private void deleteEstagiosGruposSemaforicos(Controlador controlador) {
        if (controlador.getId() != null) {
            Controlador controladorAux = Controlador.find.byId(controlador.getId());
            if (controladorAux != null) {
                controladorAux.getAneis().stream()
                        .map(Anel::getGruposSemaforicos)
                        .flatMap(Collection::stream)
                        .map(GrupoSemaforico::getEstagiosGruposSemaforicos)
                        .flatMap(Collection::stream)
                        .forEach(EstagioGrupoSemaforico::delete);

                controlador.getAneis().stream()
                        .map(Anel::getGruposSemaforicos)
                        .flatMap(Collection::stream)
                        .map(GrupoSemaforico::getEstagiosGruposSemaforicos)
                        .flatMap(Collection::stream)
                        .forEach(estagioGrupoSemaforico -> estagioGrupoSemaforico.setId(null));
            }
        }
    }
    private void deleteTransicoesProibidas(Controlador controlador) {
        if (controlador.getId() != null) {
            Controlador controladorAux = Controlador.find.byId(controlador.getId());
            if (controladorAux != null) {

                controladorAux.getAneis().stream()
                        .map(Anel::getEstagios)
                        .flatMap(Collection::stream)
                        .forEach(estagio -> {
                            estagio.getOrigemDeTransicoesProibidas().forEach(TransicaoProibida::delete);
                            estagio.getDestinoDeTransicoesProibidas().forEach(TransicaoProibida::delete);
                            estagio.getAlternativaDeTransicoesProibidas().forEach(TransicaoProibida::delete);
                        });

                controlador.getAneis().stream()
                        .map(Anel::getEstagios)
                        .flatMap(Collection::stream)
                        .forEach(estagio -> {
                            estagio.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setId(null));
                            estagio.getDestinoDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setId(null));
                            estagio.getAlternativaDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setId(null));
                        });
            }
        }
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
                estagiosCache.put(estagio.getIdJson().toString(), estagio);
            }
        }
    }
    private void parseGruposSemaforicos(JsonNode node) {
        if (node.has("gruposSemaforicos")) {
            List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
            for (JsonNode nodeGrupoSemaforico : node.get("gruposSemaforicos")) {
                GrupoSemaforico grupoSemaforico = parseGrupoSemaforico(nodeGrupoSemaforico);
                gruposSemaforicosCache.put(grupoSemaforico.getIdJson().toString(), grupoSemaforico);
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
                detectoresCache.put(detector.getIdJson().toString(), detector);
                detectores.add(detector);
            }

            controlador.setDetectores(detectores);
        }
    }

    private void parseTransicoesProibidas(JsonNode node) {
        if (node.has("transicoesProibidas")) {
            for (JsonNode innerNode : node.get("transicoesProibidas")) {
                TransicaoProibida transicaoProibida = parseTransicaoProibida(innerNode);
                transicaoProibidaCache.put(transicaoProibida.getIdJson().toString(), transicaoProibida);
            }
        }
    }

    private void parseEstagiosGruposSemaforicos(JsonNode node) {
        if (node.has("estagiosGruposSemaforicos")) {
            for (JsonNode innerNode : node.get("estagiosGruposSemaforicos")) {
                EstagioGrupoSemaforico estagioGrupoSemaforico = parseEstagioGrupoSemaforico(innerNode);
                estagioGrupoSemaforicoCache.put(estagioGrupoSemaforico.getIdJson().toString(), estagioGrupoSemaforico);
            }
        }
    }

    private void parseVerdesConflitantes(JsonNode node) {
        if (node.has("verdesConflitantes")) {
            for (JsonNode innerNode : node.get("verdesConflitantes")) {
                VerdesConflitantes verdesConflitantes = parseVerdesConflitante(innerNode);
                if (!verdesConflitantes.isDestroy()) {
                    verdesConflitantesCache.put(verdesConflitantes.getIdJson(), verdesConflitantes);
                }
            }
        }
    }

    private void parseTabelasEntreVerdes(JsonNode node) {
        if (node.has("tabelasEntreVerdes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdes")) {
                TabelaEntreVerdes tabelaEntreVerdes = parsetTabelaEntreVerdes(innerNode);
                if (!tabelaEntreVerdes.isDestroy()) {
                    tabelasEntreVerdesCache.put(tabelaEntreVerdes.getIdJson(), tabelaEntreVerdes);
                }
            }
        }
    }

    private void parseTransicoes(JsonNode node) {
        if (node.has("transicoes")) {
            for (JsonNode innerNode : node.get("transicoes")) {
                Transicao transicao = parseTransicao(innerNode);
                transicaoCache.put(transicao.getIdJson().toString(), transicao);
            }
        }

        if (node.has("transicoesComPerdaDePassagem")) {
            for (JsonNode innerNode : node.get("transicoesComPerdaDePassagem")) {
                Transicao transicao = parseTransicao(innerNode);
                transicaoCache.put(transicao.getIdJson().toString(), transicao);
            }
        }
    }

    private void parseTabelasEntreVerdesTransicoes(JsonNode node) {
        if (node.has("tabelasEntreVerdesTransicoes")) {
            for (JsonNode innerNode : node.get("tabelasEntreVerdesTransicoes")) {
                TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = parsetTabelaEntreVerdesTransicao(innerNode);
                tabelaEntreVerdesTransicaoCache.put(tabelaEntreVerdesTransicao.getIdJson().toString(), tabelaEntreVerdesTransicao);
            }
        }
    }

    private void parsePlanos(JsonNode node) {
        if (node.has("planos")) {
            for (JsonNode nodePlano : node.get("planos")) {
                if ((nodePlano.has("configurado") && nodePlano.get("configurado").asBoolean()) || !nodePlano.has("configurado")) {
                    Plano plano = parsePlano(nodePlano);
                    planosCache.put(plano.getIdJson().toString(), plano);
                }
            }
        }
    }

    private void parseGruposSemaforicosPlanos(JsonNode node) {
        if (node.has("gruposSemaforicosPlanos")) {
            for (JsonNode nodeGrupoSemaforicoPlano : node.get("gruposSemaforicosPlanos")) {
                GrupoSemaforicoPlano grupoSemaforicoPlano = parseGruposSemaforicosPlano(nodeGrupoSemaforicoPlano);
                gruposSemaforicosPlanosCache.put(grupoSemaforicoPlano.getIdJson().toString(), grupoSemaforicoPlano);
            }
        }
    }

    private void parseEstagiosPlanos(JsonNode node) {
        if (node.has("estagiosPlanos")) {
            for (JsonNode nodeEstagioPlano : node.get("estagiosPlanos")) {
                EstagioPlano estagioPlano = parseEstagiosPlano(nodeEstagioPlano);
                estagiosPlanosCache.put(estagioPlano.getIdJson().toString(), estagioPlano);
            }
        }
    }

    private void parseEventos(JsonNode node) {
        if (node.has("eventos")) {
            for (JsonNode nodeEvento : node.get("eventos")) {
                Evento evento = parseEvento(nodeEvento);
                eventosCache.put(evento.getIdJson().toString(), evento);
            }
        }
    }


    private void parseEnderecos(JsonNode node) {
        if (node.has("todosEnderecos")) {
            for (JsonNode innerNode : node.get("todosEnderecos")) {
                Endereco endereco = parseEndereco(innerNode);
                enderecosCache.put(endereco.getIdJson().toString(), endereco);
            }
        }
    }

    private void parseAreas(JsonNode node) {
        if (node.has("areas")) {
            for (JsonNode innerNode : node.get("areas")) {
                Area area = parseArea(innerNode);
                areasCache.put(area.getIdJson().toString(), area);
            }
        }
    }

    private void parseCidades(JsonNode node) {
        if (node.has("cidades")) {
            for (JsonNode innerNode : node.get("cidades")) {
                Cidade cidade = parseCidade(innerNode);
                cidadesCache.put(cidade.getIdJson().toString(), cidade);
            }
        }
    }

    private void parseImagens(JsonNode node) {
        if (node.has("imagens")) {
            for (JsonNode innerNode : node.get("imagens")) {
                Imagem imagem = parseImagem(innerNode);
                imagensCache.put(imagem.getIdJson().toString(), imagem);
            }
        }
    }

    private void parseAtrasosDeGrupo(JsonNode node) {
        if (node.has("atrasosDeGrupo")) {
            for (JsonNode innerNode : node.get("atrasosDeGrupo")) {
                AtrasoDeGrupo atrasoDeGrupo = parseAtrasoDeGrupo(innerNode);
                atrasoDeGrupoCache.put(atrasoDeGrupo.getIdJson().toString(), atrasoDeGrupo);
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
            anel.setCroqui(Imagem.find.byId(UUID.fromString(node.get("croqui").get("id").asText())));
        }

        List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
        parseCollection("gruposSemaforicos", node, grupoSemaforicos, GRUPOS_SEMAFORICOS, ANEIS);
        anel.setGruposSemaforicos(grupoSemaforicos);

        List<Detector> detectores = new ArrayList<Detector>();
        parseCollection("detectores", node, detectores, DETECTORES, ANEIS);
        anel.setDetectores(detectores);

        List<Plano> planos = new ArrayList<Plano>();
        parseCollection("planos", node, planos, PLANOS, ANEIS);
        anel.setPlanos(planos);

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
        parseCollection("transicoesComPerdaDePassagem", node, transicoes, TRANSICAO, GRUPOS_SEMAFORICOS);
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

        verdesConflitantes.setId(null);

        if (node.has("_destroy")) {
            verdesConflitantes.setDestroy(node.get("_destroy").asBoolean());
        }

        if (verdesConflitantes.isDestroy()) {
            return verdesConflitantes;
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

        return transicao;
    }

    private TabelaEntreVerdes parsetTabelaEntreVerdes(JsonNode node) {
        TabelaEntreVerdes tabelaEntreVerdes = new TabelaEntreVerdes();

        if (node.has("_destroy")) {
            tabelaEntreVerdes.setDestroy(node.get("_destroy").booleanValue());
        }

        tabelaEntreVerdes.setId(null);

        if (node.has("idJson")) {
            tabelaEntreVerdes.setIdJson(node.get("idJson").asText());
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
        if (node.get("tempoAtrasoGrupo") != null) {
            tabelaEntreVerdesTransicao.setTempoAtrasoGrupo(node.get("tempoAtrasoGrupo").asInt());
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

        if (node.has("agrupamento") && node.get("agrupamento").get("id") != null) {
            plano.setAgrupamento(Agrupamento.find.byId(UUID.fromString(node.get("agrupamento").get("id").asText())));
        }

        if (node.has("anel")) {
            final String anelId = node.get("anel").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(ANEIS);
                plano.setAnel((Anel) map.get(anelId));
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
        if (node.has("estagioQueRecebeEstagioDispensavel")) {
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
                estagioPlano.setEstagio((Estagio) map.get(estagioId));
            };

            runLater(c);
        }

        return estagioPlano;
    }

    private void parseTabelaHoraria(JsonNode nodeRoot) {
        if (nodeRoot.has("tabelaHoraria")) {
            JsonNode node = nodeRoot.get("tabelaHoraria");
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

            tabelaHoraria.setControlador(controlador);
            controlador.setTabelaHoraria(tabelaHoraria);

            List<Evento> eventos = new ArrayList<>();
            parseCollection("eventos", node, eventos, EVENTOS, null);
            tabelaHoraria.setEventos(eventos);
        }
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
                evento.setData(formatter.parse((node.get("data").asText())));
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

        if (node.has("tabelaHoraria")) {
            evento.setTabelaHorario(controlador.getTabelaHoraria());
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

        if (node.has("area") && node.get("area").get("id") != null) {
            controlador.setArea(Area.find.byId(UUID.fromString(node.get("area").get("id").asText())));
        }

        if (node.has("area")) {
            final String areaId = node.get("area").get("idJson").asText();
            Consumer<Map<String, Map>> c = (caches) -> {
                Map map = caches.get(AREAS);
                controlador.setArea((Area) map.get(areaId));
            };
            runLater(c);
        }

        if (node.has("subarea") && node.get("subarea").get("id") != null) {
            controlador.setSubarea(Subarea.find.byId(UUID.fromString(node.get("subarea").get("id").asText())));
        }

        if (node.has("modelo") && node.get("modelo").get("id") != null) {
            controlador.setModelo(ModeloControlador.find.byId(UUID.fromString(node.get("modelo").get("id").asText())));
        }

        controlador.setNumeroSMEE(node.get("numeroSMEE") != null ? node.get("numeroSMEE").asText() : null);
        controlador.setNumeroSMEEConjugado1(node.get("numeroSMEEConjugado1") != null ? node.get("numeroSMEEConjugado1").asText() : null);
        controlador.setNumeroSMEEConjugado2(node.get("numeroSMEEConjugado2") != null ? node.get("numeroSMEEConjugado2").asText() : null);
        controlador.setNumeroSMEEConjugado3(node.get("numeroSMEEConjugado3") != null ? node.get("numeroSMEEConjugado3").asText() : null);
        controlador.setFirmware(node.get("firmware") != null ? node.get("firmware").asText() : null);
        controlador.setStatusControlador(node.get("statusControlador") != null ? StatusControlador.valueOf(node.get("statusControlador").asText()) : null);
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
    }

    private void parseCollection(String collection, JsonNode node, List list, final String cacheContainer, final String callie) {
        if (node.has(collection)) {
            for (JsonNode innerNode : node.get(collection)) {
                if (innerNode.has("idJson")) {
                    final String id = innerNode.get("idJson").asText();
                    Consumer<Map<String, Map>> c = (caches) -> {
                        assert cacheContainer != null;
                        Map map = caches.get(cacheContainer);
                        assert map.containsKey(id);
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
