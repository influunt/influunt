package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import org.joda.time.format.DateTimeFormat;
import play.libs.Json;
import utils.RangeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ControladorCustomSerializer {

    private static final String ID_JSON = "idJson";

    private static final String DATA_CRIACAO = "dataCriacao";

    private static final String DATA_ATUALIZACAO = "dataAtualizacao";

    private static final String NOME = "nome";

    private Map<String, Estagio> estagiosMap = new HashMap<>();

    private Map<String, GrupoSemaforico> gruposSemaforicosMap = new HashMap<>();

    private Map<String, Detector> detectoresMap = new HashMap<>();

    private Map<String, Imagem> imagensMap = new HashMap<>();

    private Map<String, TransicaoProibida> transicoesProibidasMap = new HashMap<>();

    private Map<String, EstagioGrupoSemaforico> estagiosGruposSemaforicosMap = new HashMap<>();

    private Map<String, VerdesConflitantes> verdesConflitantesMap = new HashMap<>();

    private Map<String, Transicao> transicoesMap = new HashMap<>();

    private Map<String, Transicao> transicoesComPerdaDePassagemMap = new HashMap<>();

    private Map<String, TabelaEntreVerdes> entreVerdesMap = new HashMap<>();

    private Map<String, TabelaEntreVerdesTransicao> entreVerdesTransicoesMap = new HashMap<>();

    private Map<String, VersaoPlano> versoesPlanosMap = new HashMap<>();

    private Map<String, Plano> planosMap = new HashMap<>();

    private Map<String, GrupoSemaforicoPlano> grupoSemaforicoPlanoMap = new HashMap<>();

    private Map<String, EstagioPlano> estagioPlanoMap = new HashMap<>();

    private Map<String, VersaoTabelaHoraria> versoesTabelasHorariasMap = new HashMap<>();

    private Map<String, TabelaHorario> tabelasHorariasMap = new HashMap<>();

    private Map<String, Evento> eventosMap = new HashMap<>();

    private Map<String, Endereco> enderecosMap = new HashMap<>();

    private Map<String, Area> areasMap = new HashMap<>();

    private Map<String, LimiteArea> limitesMap = new HashMap<>();

    private Map<String, AtrasoDeGrupo> atrasosDeGrupoMap = new HashMap<>();

    public JsonNode getControladorJson(Controlador controlador, List<Cidade> cidades, RangeUtils rangeUtils) {
        if (rangeUtils != null) {
            controlador.setRangeUtils(rangeUtils);
        }

        ObjectNode root = Json.newObject();
        putControladorDadosBasicos(controlador, root);
        putControladorModelo(controlador.getModelo(), root);
        putControladorSubarea(controlador, controlador.getSubarea(), root);
        putControladorAneis(controlador.getAneis(), root);
        putControladorEstagios(root);
        putControladorGruposSemaforicos(root);
        putControladorDetector(root);

        putControladorTransicoesProibidas(root);
        putControladorEstagiosGruposSemaforicos(root);
        putControladorVerdesConflitantes(root);
        putControladorTransicoes(root);
        putControladorTransicoesComPerdaDePassagem(root);
        putControladorTabelaEntreVerdes(root);
        putControladorTabelaEntreVerdesTransicoes(root);

        putControladorPlano(root);
        putControladorGruposSemaforicosPlanos(root);
        putControladorEstagiosPlanos(root);

        putControladorCidades(root, cidades);
        putControladorAreas(root);
        putControladorLimites(root);
        putControladorEnderecos(root);
        putControladorImagens(root);
        putControladorAtrasosDeGrupo(root);

        putControladorVersao(controlador.getVersaoControlador(), root);
        putControladorVersoesPlanos(root);
        putControladorVersoesTabelasHorarias(root);
        putControladorTabelasHorarias(root);
        putControladorEventos(root);

        putInformacoesControlador(controlador, root);
        return root;
    }

    private void putInformacoesControlador(Controlador controlador, ObjectNode root) {
        if (controlador.isConfigurado()) {
            root.put("controladorConfigurado", true);
            Anel anel = controlador.getAneisAtivos().stream().findFirst().orElse(null);
            if (anel != null && anel.getVersaoPlano() != null && controlador.isPlanoConfigurado()) {
                root.put("planoConfigurado", true);
                if (controlador.getVersaoTabelaHoraria() != null && controlador.isTabelaHorariaConfigurado()) {
                    root.put("tabelaHorariaConfigurado", true);
                } else {
                    root.put("tabelaHorariaConfigurado", false);
                }
            } else {
                root.put("planoConfigurado", false);
                root.put("tabelaHorariaConfigurado", false);
            }
        } else {
            root.put("controladorConfigurado", false);
            root.put("planoConfigurado", false);
            root.put("tabelaHorariaConfigurado", false);
        }

        root.put("exclusivoParaTeste", controlador.isExclusivoParaTeste());
    }

    private JsonNode getControladorBasicoJson(Controlador controlador) {
        ObjectNode root = Json.newObject();
        putControladorDadosIndex(controlador, root);

        putInformacoesControlador(controlador, root);

        if (root.get("planoConfigurado").asBoolean()) {
            root.put("planoConfigurado", controlador.isPlanoCentralConfigurado());
        }

        return root;
    }

    public JsonNode getControladoresJson(List<Controlador> controladores) {
        ArrayNode controladoresJson = Json.newArray();
        for (Controlador controlador : controladores) {
            inicializaMaps();
            controladoresJson.add(getControladorBasicoJson(controlador));
        }

        return controladoresJson;
    }

    public JsonNode getControladoresForMapas(List<Controlador> controladores) {
        ArrayNode controladoresJson = Json.newArray();
        for (Controlador controlador : controladores) {
            inicializaMaps();
            ObjectNode root = Json.newObject();
            putControladorMapa(controlador, root);

            RangeUtils rangeUtils = RangeUtils.getInstance(null);
            root.put("verdeMin", rangeUtils.TEMPO_VERDE.getMin().toString());
            root.put("verdeMax", rangeUtils.TEMPO_VERDE.getMax().toString());
            root.put("verdeMinimoMin", rangeUtils.TEMPO_VERDE_MINIMO.getMin().toString());
            root.put("cicloMax", rangeUtils.TEMPO_CICLO.getMax().toString());

            List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
            putControladorAneis(aneis, root);
            putControladorEnderecos(root);
            putControladorPlano(root);
            putControladorGruposSemaforicos(root);
            putControladorEstagios(root);
            putControladorEstagiosPlanos(root);
            putControladorEstagiosGruposSemaforicos(root);
            putControladorTransicoes(root);
            putControladorGruposSemaforicosPlanos(root);
            putControladorTabelaEntreVerdes(root);
            putControladorTabelaEntreVerdesTransicoes(root);
            controladoresJson.add(root);
        }

        return controladoresJson;
    }

    public JsonNode getControladoresAgrupamentos(List<Controlador> controladores) {
        ArrayNode controladoresJson = Json.newArray();
        for (Controlador controlador : controladores) {
            inicializaMaps();
            ObjectNode root = Json.newObject();
            putControladorAgrupamentos(controlador, root);
            controladoresJson.add(root);
        }

        return controladoresJson;
    }

    public JsonNode getControladoresForImposicao(List<Controlador> controladores) {
        ArrayNode controladoresJson = Json.newArray();
        for (Controlador controlador : controladores) {
            ObjectNode root = controladoresJson.addObject();
            putControladorImposicoes(controlador, root);
        }
        return controladoresJson;
    }

    public JsonNode getControladorSimulacao(Controlador controlador) {
        ObjectNode root = Json.newObject();

        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
        }

        if (controlador.getNomeEndereco() != null) {
            root.put("nomeEndereco", controlador.getNomeEndereco());
        }

        ArrayNode gruposJson = root.putArray("gruposSemaforicos");
        ArrayNode aneisJson = root.putArray("aneis");
        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
            ObjectNode anelJson = aneisJson.addObject();
            anelJson.put("id", anel.getId().toString());
            anelJson.put("posicao", anel.getPosicao());

            ArrayNode detectoresJson = anelJson.putArray("detectores");
            anel.getDetectores().forEach(detector -> {
                ObjectNode detectorJson = detectoresJson.addObject();
                detectorJson.put("id", detector.getId().toString());
                detectorJson.put("tipo", detector.getTipo().toString());
                detectorJson.put("posicao", detector.getPosicao());
                detectorJson.put("monitorado", detector.isMonitorado());
            });

            ArrayNode planosJson = anelJson.putArray("planos");
            anel.getPlanos().forEach(plano -> {
                ObjectNode planoJson = planosJson.addObject();
                planoJson.put("id", plano.getId().toString());
                planoJson.put("posicao", plano.getPosicao());
                planoJson.put("descricao", plano.getDescricao());
                planoJson.put("modoOperacao", plano.getModoOperacao().toString());
                planoJson.put("isManual", plano.isManual());
            });
            anel.getGruposSemaforicos().stream().forEach(grupoSemaforico -> {
                ObjectNode grupoJson = gruposJson.addObject();
                grupoJson.put("id", grupoSemaforico.getId().toString());
                grupoJson.put("descricao", "G" + grupoSemaforico.getPosicao());
                grupoJson.put("posicao", grupoSemaforico.getPosicao());
            });
        });
        return root;
    }

    public JsonNode getPacoteConfiguracaoJson(Controlador controlador, List<Cidade> cidades, RangeUtils rangeUtils) {
        controlador.setVersoesTabelasHorarias(null);
        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
            anel.setVersoesPlanos(null);
            anel.setVersaoPlanoAtivo(new VersaoPlano());
        });
        return getControladorJson(controlador, cidades, rangeUtils);
    }

    public JsonNode getPacotePlanosJson(Controlador controlador) {
        ObjectNode root = Json.newObject();

        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
            if (anel.getVersaoPlano() != null) {
                versoesPlanosMap.put(anel.getVersaoPlano().getIdJson(), anel.getVersaoPlano());
            }
        });
        putControladorVersoesPlanos(root);
        putControladorPlano(root);
        putControladorGruposSemaforicosPlanos(root);
        putControladorEstagiosPlanos(root);

        return root;
    }

    public JsonNode getPacoteTabelaHorariaJson(Controlador controlador) {
        ObjectNode root = Json.newObject();

        if (controlador.getVersaoTabelaHoraria() != null) {
            versoesTabelasHorariasMap.put(controlador.getVersaoTabelaHoraria().getIdJson(), controlador.getVersaoTabelaHoraria());
            if (controlador.getVersaoTabelaHoraria().getTabelaHoraria() != null) {
                tabelasHorariasMap.put(controlador.getVersaoTabelaHoraria().getTabelaHoraria().getIdJson(), controlador.getVersaoTabelaHoraria().getTabelaHoraria());
                if (controlador.getVersaoTabelaHoraria().getTabelaHoraria().getEventos() != null) {
                    controlador.getVersaoTabelaHoraria().getTabelaHoraria().getEventos().forEach(evento -> {
                        eventosMap.put(evento.getIdJson(), evento);
                    });
                }
            }
        }

        putControladorVersoesTabelasHorarias(root);
        putControladorTabelasHorarias(root);
        putControladorEventos(root);

        return root;
    }

    public JsonNode getPacoteConfiguracaoCompletaJson(Controlador controlador, List<Cidade> cidades, RangeUtils rangeUtils) {
        ObjectNode root = Json.newObject();
        root.set("pacotePlanos", getPacotePlanosJson(controlador));
        root.set("pacoteTabelaHoraria", getPacoteTabelaHorariaJson(controlador));
        // pacoteConfiguracao seta alguns relacionamentos p/ null, portanto
        // deve ser executado por último.
        root.set("pacoteConfiguracao", getPacoteConfiguracaoJson(controlador, cidades, rangeUtils));
        return root;
    }

    private void putControladorVersoesPlanos(ObjectNode root) {
        ArrayNode versaoPlanoJson = Json.newArray();
        versoesPlanosMap.values().stream().forEach(versaoPlano -> {
            versaoPlanoJson.add(getVersaoPlanoJson(versaoPlano));
        });
        root.set("versoesPlanos", versaoPlanoJson);
    }

    private void putControladorVersoesTabelasHorarias(ObjectNode root) {
        ArrayNode versaoTabelaHorariaJson = Json.newArray();
        versoesTabelasHorariasMap.values().stream().forEach(versaoTabelaHoraria -> {
            versaoTabelaHorariaJson.add(getVersaoTabelaHorariaJson(versaoTabelaHoraria));
        });
        root.set("versoesTabelasHorarias", versaoTabelaHorariaJson);
    }

    private void putControladorPlano(ObjectNode root) {
        ArrayNode planoJson = Json.newArray();
        planosMap.values().stream().forEach(plano -> {
            planoJson.add(getPlanoJson(plano));
        });
        root.set("planos", planoJson);
    }

    private void putControladorGruposSemaforicosPlanos(ObjectNode root) {
        ArrayNode grupoSemaforicoPlanoJson = Json.newArray();
        grupoSemaforicoPlanoMap.values().stream().forEach(grupoSemaforicoPlano -> {
            grupoSemaforicoPlanoJson.add(getGrupoSemaforicoPlanoJson(grupoSemaforicoPlano));
        });
        root.set("gruposSemaforicosPlanos", grupoSemaforicoPlanoJson);
    }

    private void putControladorEstagiosPlanos(ObjectNode root) {
        ArrayNode estagiosPlanoJson = Json.newArray();
        estagioPlanoMap.values().stream().forEach(estagioPlano -> {
            estagiosPlanoJson.add(getEstagioPlanoJson(estagioPlano));
        });
        root.set("estagiosPlanos", estagiosPlanoJson);
    }

    private JsonNode getVersaoTabelaHorariaJson(VersaoTabelaHoraria versaoTabelaHoraria) {
        ObjectNode versaoTabelaHorariaJson = Json.newObject();

        if (versaoTabelaHoraria.getId() != null) {
            versaoTabelaHorariaJson.put("id", versaoTabelaHoraria.getId().toString());
        }
        if (versaoTabelaHoraria.getIdJson() != null) {
            versaoTabelaHorariaJson.put(ID_JSON, versaoTabelaHoraria.getIdJson());
        }
        if (versaoTabelaHoraria.getStatusVersao() != null) {
            versaoTabelaHorariaJson.put("statusVersao", versaoTabelaHoraria.getStatusVersao().toString());
        }
        if (versaoTabelaHoraria.getControlador() != null && versaoTabelaHoraria.getControlador().getIdJson() != null) {
            versaoTabelaHorariaJson.putObject("controlador").put(ID_JSON, versaoTabelaHoraria.getControlador().getIdJson());
        }
        if (versaoTabelaHoraria.getTabelaHorariaOrigem() != null && versaoTabelaHoraria.getTabelaHorariaOrigem().getIdJson() != null) {
            versaoTabelaHorariaJson.putObject("tabelaHorariaOrigem").put(ID_JSON, versaoTabelaHoraria.getTabelaHorariaOrigem().getIdJson());
            tabelasHorariasMap.put(versaoTabelaHoraria.getTabelaHorariaOrigem().getIdJson(), versaoTabelaHoraria.getTabelaHorariaOrigem());
        }
        if (versaoTabelaHoraria.getTabelaHoraria() != null && versaoTabelaHoraria.getTabelaHoraria().getIdJson() != null) {
            versaoTabelaHorariaJson.putObject("tabelaHoraria").put(ID_JSON, versaoTabelaHoraria.getTabelaHoraria().getIdJson());
            tabelasHorariasMap.put(versaoTabelaHoraria.getTabelaHoraria().getIdJson(), versaoTabelaHoraria.getTabelaHoraria());
        }
        if (versaoTabelaHoraria.getDataCriacao() != null) {
            versaoTabelaHorariaJson.put(DATA_CRIACAO, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(versaoTabelaHoraria.getDataCriacao()));
        }
        if (versaoTabelaHoraria.getDataAtualizacao() != null) {
            versaoTabelaHorariaJson.put(DATA_ATUALIZACAO, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(versaoTabelaHoraria.getDataAtualizacao()));
        }
        if (versaoTabelaHoraria.getUsuario() != null) {
            Usuario usuario = versaoTabelaHoraria.getUsuario();
            versaoTabelaHorariaJson.putObject("usuario").put(NOME, usuario.getNome());
        }

        return versaoTabelaHorariaJson;
    }

    private void putControladorTabelasHorarias(ObjectNode root) {
        ArrayNode tabelaHorariaJson = Json.newArray();
        tabelasHorariasMap.values().stream().forEach(tabelaHoraria -> {
            tabelaHorariaJson.add(getTabelaHorariaJson(tabelaHoraria));
        });
        root.set("tabelasHorarias", tabelaHorariaJson);
    }

    private void putControladorEventos(ObjectNode root) {
        ArrayNode eventoJson = Json.newArray();
        eventosMap.values().stream().forEach(evento -> {
            eventoJson.add(getEventoJson(evento));
        });
        root.set("eventos", eventoJson);
    }

    private void putControladorDadosIndex(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
            root.put("controladorFisicoId", controlador.getControladorFisicoId());
        }
        if (controlador.getIdJson() != null) {
            root.put(ID_JSON, controlador.getIdJson());
        }
        if (controlador.getNomeEndereco() != null) {
            root.put("nomeEndereco", controlador.getNomeEndereco());
        }
        root.put(DATA_CRIACAO, InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));
        root.put(DATA_ATUALIZACAO, InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));
        root.put("CLC", controlador.getCLC());
        if (controlador.getVersaoControlador().getStatusVersao() != null) {
            root.put("statusControlador", controlador.getVersaoControlador().getStatusVersao().toString());
        }
        if (controlador.getVersaoControlador().getUsuario() != null) {
            ObjectNode usuarioNode = root.putObject("versaoControlador").putObject("usuario");
            usuarioNode.put("id", controlador.getVersaoControlador().getUsuario().getId().toString());
            usuarioNode.put(NOME, controlador.getVersaoControlador().getUsuario().getNome());
        }
        root.put("statusControladorReal", controlador.getStatusControladorReal().toString());
    }

    private void putControladorDadosBasicos(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
            refVersoesTabelasHorarias("versoesTabelasHorarias", controlador.getVersoesTabelasHorarias(), root);

            root.put("controladorFisicoId", controlador.getControladorFisicoId());
        }

        if (controlador.getIdJson() != null) {
            root.put(ID_JSON, controlador.getIdJson());
        }
        if (controlador.getNumeroSMEE() != null) {
            root.put("numeroSMEE", controlador.getNumeroSMEE());
        }
        if (controlador.getNumeroSMEEConjugado1() != null) {
            root.put("numeroSMEEConjugado1", controlador.getNumeroSMEEConjugado1());
        }
        if (controlador.getNumeroSMEEConjugado2() != null) {
            root.put("numeroSMEEConjugado2", controlador.getNumeroSMEEConjugado2());
        }
        if (controlador.getNumeroSMEEConjugado3() != null) {
            root.put("numeroSMEEConjugado3", controlador.getNumeroSMEEConjugado3());
        }
        if (controlador.getFirmware() != null) {
            root.put("firmware", controlador.getFirmware());
        }

        if (controlador.getCroqui() != null) {
            ObjectNode croquiJson = Json.newObject();
            Imagem croqui = controlador.getCroqui();

            if (croqui.getId() != null) {
                croquiJson.put("id", croqui.getId().toString());
            }

            if (croqui.getIdJson() != null) {
                croquiJson.put(ID_JSON, croqui.getIdJson().toString());
            }

            root.set("croqui", croquiJson);
            imagensMap.put(croqui.getIdJson(), croqui);
        }
        if (controlador.getSequencia() != null) {
            root.put("sequencia", controlador.getSequencia());
        }
        if (controlador.getModelo() != null) {
            if (controlador.getModelo().getLimiteEstagio() != null) {
                root.put("limiteEstagio", controlador.getModelo().getLimiteEstagio());
            }
            if (controlador.getModelo().getLimiteGrupoSemaforico() != null) {
                root.put("limiteGrupoSemaforico", controlador.getModelo().getLimiteGrupoSemaforico());
            }
            if (controlador.getModelo().getLimiteAnel() != null) {
                root.put("limiteAnel", controlador.getModelo().getLimiteAnel());
            }
            if (controlador.getModelo().getLimiteDetectorPedestre() != null) {
                root.put("limiteDetectorPedestre", controlador.getModelo().getLimiteDetectorPedestre());
            }
            if (controlador.getModelo().getLimiteDetectorVeicular() != null) {
                root.put("limiteDetectorVeicular", controlador.getModelo().getLimiteDetectorVeicular());
            }
            if (controlador.getModelo().getLimiteTabelasEntreVerdes() != null) {
                root.put("limiteTabelasEntreVerdes", controlador.getModelo().getLimiteTabelasEntreVerdes());
            }
            if (controlador.getModelo().getLimitePlanos() != null) {
                root.put("limitePlanos", controlador.getModelo().getLimitePlanos());
            }
        }
        if (controlador.getNomeEndereco() != null) {
            root.put("nomeEndereco", controlador.getNomeEndereco());
        }

        root.put(DATA_CRIACAO, InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));

        root.put(DATA_ATUALIZACAO, InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));

        root.put("CLC", controlador.getCLC());

        root.put("bloqueado", controlador.isBloqueado());
        root.put("planosBloqueado", controlador.isPlanosBloqueado());

        RangeUtils rangeUtils = controlador.getRangeUtils();
        if (rangeUtils != null) {
            root.put("verdeMin", rangeUtils.TEMPO_VERDE.getMin().toString());
            root.put("verdeMax", rangeUtils.TEMPO_VERDE.getMax().toString());
            root.put("verdeMinimoMin", rangeUtils.TEMPO_VERDE_MINIMO.getMin().toString());
            root.put("verdeMinimoMax", rangeUtils.TEMPO_VERDE_MINIMO.getMax().toString());
            root.put("verdeMaximoMin", rangeUtils.TEMPO_VERDE_MAXIMO.getMin().toString());
            root.put("verdeMaximoMax", rangeUtils.TEMPO_VERDE_MAXIMO.getMax().toString());
            root.put("extensaoVerdeMin", rangeUtils.TEMPO_EXTENSAO_VERDE.getMin().toString());
            root.put("extensaoVerdeMax", rangeUtils.TEMPO_EXTENSAO_VERDE.getMax().toString());
            root.put("verdeIntermediarioMin", rangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMin().toString());
            root.put("verdeIntermediarioMax", rangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMax().toString());
            root.put("defasagemMin", rangeUtils.TEMPO_DEFASAGEM.getMin().toString());
            root.put("defasagemMax", rangeUtils.TEMPO_DEFASAGEM.getMax().toString());
            root.put("amareloMin", rangeUtils.TEMPO_AMARELO.getMin().toString());
            root.put("amareloMax", rangeUtils.TEMPO_AMARELO.getMax().toString());
            root.put("vermelhoIntermitenteMin", rangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMin().toString());
            root.put("vermelhoIntermitenteMax", rangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMax().toString());
            root.put("vermelhoLimpezaVeicularMin", rangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMin().toString());
            root.put("vermelhoLimpezaVeicularMax", rangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMax().toString());
            root.put("vermelhoLimpezaPedestreMin", rangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMin().toString());
            root.put("vermelhoLimpezaPedestreMax", rangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMax().toString());
            root.put("atrasoGrupoMin", rangeUtils.TEMPO_ATRASO_GRUPO.getMin().toString());
            root.put("atrasoGrupoMax", rangeUtils.TEMPO_ATRASO_GRUPO.getMax().toString());
            root.put("verdeSegurancaVeicularMin", rangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMin().toString());
            root.put("verdeSegurancaVeicularMax", rangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMax().toString());
            root.put("verdeSegurancaPedestreMin", rangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMin().toString());
            root.put("verdeSegurancaPedestreMax", rangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMax().toString());
            root.put("maximoPermanenciaEstagioMin", rangeUtils.TEMPO_MAXIMO_PERMANENCIA_ESTAGIO.getMin().toString());
            root.put("maximoPermanenciaEstagioMax", rangeUtils.TEMPO_MAXIMO_PERMANENCIA_ESTAGIO.getMax().toString());
            root.put("defaultMaximoPermanenciaEstagioVeicular", rangeUtils.getDefaultMaximoPermanenciaEstagioVeicular());
            root.put("cicloMin", rangeUtils.TEMPO_CICLO.getMin().toString());
            root.put("cicloMax", rangeUtils.TEMPO_CICLO.getMax().toString());
            root.put("ausenciaDeteccaoMin", rangeUtils.TEMPO_AUSENCIA_DETECCAO.getMin().toString());
            root.put("ausenciaDeteccaoMax", rangeUtils.TEMPO_AUSENCIA_DETECCAO.getMax().toString());
            root.put("deteccaoPermanenteMin", rangeUtils.TEMPO_DETECCAO_PERMANENTE.getMin().toString());
            root.put("deteccaoPermanenteMax", rangeUtils.TEMPO_DETECCAO_PERMANENTE.getMax().toString());
            root.put("ausenciaDeteccaoPedestreMin", rangeUtils.TEMPO_AUSENCIA_DETECCAO_PEDESTRE.getMin().toString());
            root.put("ausenciaDeteccaoPedestreMax", rangeUtils.TEMPO_AUSENCIA_DETECCAO_PEDESTRE.getMax().toString());
            root.put("deteccaoPermanentePedestreMin", rangeUtils.TEMPO_DETECCAO_PERMANENTE_PEDESTRE.getMin().toString());
            root.put("deteccaoPermanentePedestreMax", rangeUtils.TEMPO_DETECCAO_PERMANENTE_PEDESTRE.getMax().toString());
        }

        if (controlador.getVersaoControlador() != null) {
            root.put("statusControlador", controlador.getVersaoControlador().getStatusVersao().toString());
            root.put("statusControladorReal", controlador.getStatusControladorReal().toString());
        }

        if (controlador.getArea() != null && controlador.getArea().getIdJson() != null) {
            root.putObject("area").put(ID_JSON, controlador.getArea().getIdJson());
        }

        if (controlador.getSubarea() != null && controlador.getSubarea().getIdJson() != null) {
            root.putObject("subarea").put(ID_JSON, controlador.getSubarea().getIdJson());
        }

        refEndereco("endereco", controlador.getEndereco(), root);
    }

    private void putControladorMapa(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());

            root.put("controladorFisicoId", controlador.getControladorFisicoId());
        }

        if (controlador.getArea() != null && controlador.getArea().getIdJson() != null) {
            root.putObject("area").put(ID_JSON, controlador.getArea().getIdJson());
            if (controlador.getArea().getCidade() != null && controlador.getArea().getCidade().getId() != null) {
                root.putObject("cidade").put("id", controlador.getArea().getCidade().getId().toString());
            }
        }

        if (controlador.getSubarea() != null && controlador.getSubarea().getIdJson() != null) {
            root.putObject("subarea").put(ID_JSON, controlador.getSubarea().getIdJson());
        }


        root.put("CLC", controlador.getCLC());
        refEndereco("endereco", controlador.getEndereco(), root);
    }

    private void putControladorAgrupamentos(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
        }

        if (controlador.getNomeEndereco() != null) {
            root.put("nomeEndereco", controlador.getNomeEndereco());
        }

        root.put("CLC", controlador.getCLC());

        if (controlador.getVersaoControlador() != null) {
            root.put("statusControlador", controlador.getVersaoControlador().getStatusVersao().toString());
        }
        root.put("statusControladorReal", controlador.getStatusControladorReal().toString());

        ArrayNode aneisJson = root.putArray("aneis");
        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
            Map<String, Object> anelMap = new HashMap<>();
            anelMap.put("id", anel.getId().toString());
            anelMap.put("CLA", anel.getCLA());
            anelMap.put("ativo", anel.isAtivo());
            anelMap.put("posicao", anel.getPosicao());
            aneisJson.add(Json.toJson(anelMap));
        });
    }

    private void putControladorImposicoes(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
        }

        if (controlador.getVersaoControlador() != null) {
            root.put("statusControlador", controlador.getVersaoControlador().getStatusVersao().toString());
            root.put("statusControladorReal", controlador.getStatusControladorReal().toString());
        }

        ArrayNode aneisJson = root.putArray("aneis");
        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
            ObjectNode anelJson = aneisJson.addObject();
            anelJson.put("id", anel.getId().toString());
            anelJson.put("CLA", anel.getCLA());
            anelJson.put("ativo", anel.isAtivo());
            anelJson.put("posicao", anel.getPosicao());
            ObjectNode controladorJson = anelJson.putObject("controlador");
            controladorJson.put("statusControlador", controlador.getVersaoControlador().getStatusVersao().toString());
            controladorJson.put("statusControladorReal", controlador.getStatusControladorReal().toString());
            controladorJson.put("id", controlador.getId().toString());
            if (anel.getEndereco() != null) {
                Endereco endereco = anel.getEndereco();
                ObjectNode enderecoJson = anelJson.putObject("endereco");
                if (endereco.getLocalizacao() != null) {
                    enderecoJson.put("localizacao", endereco.getLocalizacao());
                }
                if (endereco.getLocalizacao2() != null) {
                    enderecoJson.put("localizacao2", endereco.getLocalizacao2());
                }
                if (endereco.getAlturaNumerica() != null) {
                    enderecoJson.put("alturaNumerica", endereco.getAlturaNumerica());
                }
                if (endereco.getReferencia() != null) {
                    enderecoJson.put("referencia", endereco.getReferencia());
                }
                if (endereco.getLatitude() != null) {
                    enderecoJson.put("latitude", endereco.getLatitude());
                }
                if (endereco.getLongitude() != null) {
                    enderecoJson.put("longitude", endereco.getLongitude());
                }
            }
        });
    }

    private void putControladorModelo(ModeloControlador modeloControlador, ObjectNode root) {
        if (modeloControlador == null) {
            return;
        }
        ObjectNode modeloJson = root.putObject("modelo");
        if (modeloControlador.getId() == null) {
            modeloJson.putNull("id");
        } else {
            modeloJson.put("id", modeloControlador.getId().toString());
        }

        if (modeloControlador.getIdJson() == null) {
            modeloJson.putNull(ID_JSON);
        } else {
            modeloJson.put(ID_JSON, modeloControlador.getIdJson().toString());
        }

        if (modeloControlador.getDescricao() != null) {
            modeloJson.put("descricao", modeloControlador.getDescricao());
        }

        if (modeloControlador.getFabricante() != null) {
            ObjectNode fabricanteJson = modeloJson.putObject("fabricante");
            Fabricante fabricante = modeloControlador.getFabricante();

            if (fabricante.getId() == null) {
                fabricanteJson.putNull("id");
            } else {
                fabricanteJson.put("id", fabricante.getId().toString());
            }

            if (fabricante.getNome() != null) {
                fabricanteJson.put(NOME, fabricante.getNome());
            }
        }
    }

    private void putControladorSubarea(Controlador controlador, Subarea subarea, ObjectNode root) {
        if (subarea == null) {
            return;
        }
        ObjectNode subareaJson = Json.newObject();
        if (subarea.getId() == null) {
            subareaJson.putNull("id");
        } else {
            subareaJson.put("id", subarea.getId().toString());
        }

        if (subarea.getIdJson() == null) {
            subareaJson.putNull(ID_JSON);
        } else {
            subareaJson.put(ID_JSON, subarea.getIdJson());
        }

        if (subarea.getNome() != null) {
            subareaJson.put(NOME, subarea.getNome());
        }

        if (subarea.getNumero() != null) {
            subareaJson.put("numero", subarea.getNumero());
        }

        if (subarea.getArea() != null) {
            subareaJson.putObject("area").put(ID_JSON, subarea.getArea().getIdJson());
        }

        subareaJson.set("tempoCiclo", Json.toJson(subarea.tempoCicloDaRede(controlador)));

        root.set("subarea", subareaJson);
    }

    private void putControladorVersao(VersaoControlador versaoControlador, ObjectNode root) {
        if (versaoControlador == null) {
            return;
        }

        ObjectNode versaoJson = root.putObject("versaoControlador");
        if (versaoControlador.getId() == null) {
            versaoJson.putNull("id");
        } else {
            versaoJson.put("id", versaoControlador.getId().toString());
        }

        if (versaoControlador.getIdJson() == null) {
            versaoJson.putNull(ID_JSON);
        } else {
            versaoJson.put(ID_JSON, versaoControlador.getIdJson());
        }

        if (versaoControlador.getDescricao() != null) {
            versaoJson.put("descricao", versaoControlador.getDescricao());
        }

        if (versaoControlador.getStatusVersao() != null) {
            versaoJson.put("statusVersao", versaoControlador.getStatusVersao().toString());
            root.put("statusVersao", versaoControlador.getStatusVersao().toString());
        }

        if (versaoControlador.getControladorOrigem() != null && versaoControlador.getControladorOrigem().getId() != null) {
            versaoJson.putObject("controladorOrigem").put("id", versaoControlador.getControladorOrigem().getId().toString());
        }

        if (versaoControlador.getControlador() != null && versaoControlador.getControlador().getId() != null) {
            versaoJson.putObject("controlador").put("id", versaoControlador.getControlador().getId().toString());
        }

        if (versaoControlador.getControladorFisico() != null && versaoControlador.getControladorFisico().getId() != null) {
            versaoJson.putObject("controladorFisico").put("id", versaoControlador.getControladorFisico().getId().toString());
        }

        if (versaoControlador.getUsuario() != null) {
            ObjectNode usuarioJson = versaoJson.putObject("usuario");
            Usuario usuario = versaoControlador.getUsuario();

            if (usuario.getId() == null) {
                usuarioJson.putNull("id");
            } else {
                usuarioJson.put("id", usuario.getId().toString());
            }

            if (usuario.getNome() != null) {
                usuarioJson.put(NOME, usuario.getNome());
            }

            if (usuario.getLogin() != null) {
                usuarioJson.put("login", usuario.getLogin());
            }

            if (usuario.getEmail() != null) {
                usuarioJson.put("email", usuario.getEmail());
            }

            if (usuario.getArea() != null && usuario.getArea().getIdJson() != null) {
                usuarioJson.putObject("area").put(ID_JSON, usuario.getArea().getIdJson());
            }
        }
    }

    private JsonNode getAreaJson(Area area) {
        ObjectNode areaJson = Json.newObject();
        if (area.getId() != null) {
            areaJson.put("id", area.getId().toString());
        }
        if (area.getIdJson() != null) {
            areaJson.put(ID_JSON, area.getIdJson());
        }
        areaJson.put("descricao", area.getDescricao());
        if (area.getCidade() != null && area.getCidade().getIdJson() != null) {
            areaJson.putObject("cidade").put(ID_JSON, area.getCidade().getIdJson());
        }

        if (area.getLimitesGeograficos() != null) {
            refLimites("limites", area.getLimitesGeograficos(), areaJson);
        }
        if (area.getSubareas() != null) {
            refSubareas("subareas", area.getSubareas(), areaJson);
        }
        return areaJson;
    }

    private JsonNode getLimiteJson(LimiteArea limite) {
        ObjectNode limiteJson = Json.newObject();
        if (limite.getId() != null) {
            limiteJson.put("id", limite.getId().toString());
        }
        if (limite.getIdJson() != null) {
            limiteJson.put(ID_JSON, limite.getIdJson());
        }
        limiteJson.put("latitude", limite.getLatitude());
        limiteJson.put("longitude", limite.getLongitude());
        limiteJson.put("posicao", limite.getPosicao());

        return limiteJson;
    }

    private JsonNode getCidadeJson(Cidade cidade) {
        ObjectNode cidadeJson = Json.newObject();

        if (cidade.getId() != null) {
            cidadeJson.put("id", cidade.getId().toString());
        }
        if (cidade.getIdJson() != null) {
            cidadeJson.put(ID_JSON, cidade.getIdJson());
        }
        cidadeJson.put(NOME, cidade.getNome());

        refAreas("areas", cidade.getAreas(), cidadeJson);

        return cidadeJson;
    }

    private void putControladorEnderecos(ObjectNode root) {
        ArrayNode enderecosJson = Json.newArray();
        enderecosMap.values().stream().forEach(endereco -> {
            enderecosJson.add(getEnderecoJson(endereco));
        });
        root.set("todosEnderecos", enderecosJson);
    }

    private void putControladorCidades(ObjectNode root, List<Cidade> cidades) {
        ArrayNode cidadesJson = Json.newArray();
        if (cidades != null) {
            cidades.forEach(cidade -> cidadesJson.add(getCidadeJson(cidade)));
        }
        root.set("cidades", cidadesJson);
    }

    private void putControladorAreas(ObjectNode root) {
        ArrayNode areasJson = Json.newArray();
        areasMap.values().stream().forEach(area -> {
            areasJson.add(getAreaJson(area));
        });
        root.set("areas", areasJson);
    }

    private void putControladorLimites(ObjectNode root) {
        ArrayNode limitesJson = Json.newArray();
        limitesMap.values().stream().forEach(limite -> {
            limitesJson.add(getLimiteJson(limite));
        });
        root.set("limites", limitesJson);
    }

    private void putControladorAneis(List<Anel> aneis, ObjectNode root) {
        ArrayNode aneisJson = Json.newArray();
        for (Anel anel : aneis) {
            aneisJson.add(putAnel(anel));
        }
        root.set("aneis", aneisJson);
    }

    private void putControladorEstagios(ObjectNode root) {
        ArrayNode estagiosJson = Json.newArray();
        estagiosMap.values().stream().forEach(estagio -> {
            estagiosJson.add(getEstagioJson(estagio));
        });
        root.set("estagios", estagiosJson);

    }

    private void putControladorGruposSemaforicos(ObjectNode root) {
        ArrayNode grupoSemaforicoJson = Json.newArray();
        gruposSemaforicosMap.values().stream().forEach(grupoSemaforico -> {
            grupoSemaforicoJson.add(getGrupoSemaforicoJson(grupoSemaforico));
        });
        root.set("gruposSemaforicos", grupoSemaforicoJson);

    }

    private void putControladorDetector(ObjectNode root) {
        ArrayNode detectoresJson = Json.newArray();
        detectoresMap.values().stream().forEach(detector -> {
            detectoresJson.add(getDetectorJson(detector));
        });
        root.set("detectores", detectoresJson);

    }

    private void putControladorTransicoesProibidas(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        transicoesProibidasMap.values().stream().forEach(transicaoProibida -> {
            arrayJson.add(getTransicaoProibidaJson(transicaoProibida));
        });
        root.set("transicoesProibidas", arrayJson);

    }

    private void putControladorEstagiosGruposSemaforicos(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        estagiosGruposSemaforicosMap.values().stream().forEach(estagioGrupoSemaforico -> {
            arrayJson.add(getEstagioGrupoSemaforicoJson(estagioGrupoSemaforico));
        });
        root.set("estagiosGruposSemaforicos", arrayJson);

    }

    private void putControladorVerdesConflitantes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        verdesConflitantesMap.values().stream().forEach(verdesConflitantes -> {
            arrayJson.add(getVerdeConflitante(verdesConflitantes));
        });
        root.set("verdesConflitantes", arrayJson);

    }

    private void putControladorTransicoes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        transicoesMap.values().stream().forEach(transicao -> {
            arrayJson.add(getTransicao(transicao));
        });
        root.set("transicoes", arrayJson);

    }

    private void putControladorTransicoesComPerdaDePassagem(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        transicoesComPerdaDePassagemMap.values().stream().forEach(transicao -> {
            arrayJson.add(getTransicao(transicao));
        });
        root.set("transicoesComGanhoDePassagem", arrayJson);
    }

    private void putControladorImagens(ObjectNode root) {
        ArrayNode imagensJson = Json.newArray();
        imagensMap.values().stream().forEach(imagem -> {
            imagensJson.add(getImagemJson(imagem));
        });
        root.set("imagens", imagensJson);

    }

    private void putControladorTabelaEntreVerdes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        entreVerdesMap.values().stream().forEach(entreVerdes -> {
            arrayJson.add(getTabelaEntreVerde(entreVerdes));
        });
        root.set("tabelasEntreVerdes", arrayJson);

    }

    private void putControladorTabelaEntreVerdesTransicoes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        entreVerdesTransicoesMap.values().stream().forEach(entreVerdes -> {
            arrayJson.add(getTabelaEntreVerdeTransicoes(entreVerdes));
        });
        root.set("tabelasEntreVerdesTransicoes", arrayJson);

    }

    private void putControladorAtrasosDeGrupo(ObjectNode root) {
        ArrayNode atrasosDeGrupoJson = Json.newArray();
        atrasosDeGrupoMap.values().stream().forEach(atrasoDeGrupo -> {
            atrasosDeGrupoJson.add(getAtrasoDeGrupoJson(atrasoDeGrupo));
        });
        root.set("atrasosDeGrupo", atrasosDeGrupoJson);
    }


    private void inicializaMaps() {
        estagiosMap = new HashMap<String, Estagio>();
        gruposSemaforicosMap = new HashMap<String, GrupoSemaforico>();
        detectoresMap = new HashMap<String, Detector>();
        imagensMap = new HashMap<String, Imagem>();
        transicoesProibidasMap = new HashMap<String, TransicaoProibida>();
        estagiosGruposSemaforicosMap = new HashMap<String, EstagioGrupoSemaforico>();
        verdesConflitantesMap = new HashMap<String, VerdesConflitantes>();
        transicoesMap = new HashMap<String, Transicao>();
        transicoesComPerdaDePassagemMap = new HashMap<String, Transicao>();
        entreVerdesMap = new HashMap<String, TabelaEntreVerdes>();
        entreVerdesTransicoesMap = new HashMap<String, TabelaEntreVerdesTransicao>();
        versoesPlanosMap = new HashMap<String, VersaoPlano>();
        planosMap = new HashMap<String, Plano>();
        grupoSemaforicoPlanoMap = new HashMap<String, GrupoSemaforicoPlano>();
        estagioPlanoMap = new HashMap<String, EstagioPlano>();
        versoesTabelasHorariasMap = new HashMap<String, VersaoTabelaHoraria>();
        tabelasHorariasMap = new HashMap<String, TabelaHorario>();
        eventosMap = new HashMap<String, Evento>();
        enderecosMap = new HashMap<String, Endereco>();
        areasMap = new HashMap<String, Area>();
        limitesMap = new HashMap<String, LimiteArea>();
        atrasosDeGrupoMap = new HashMap<String, AtrasoDeGrupo>();
    }

    private JsonNode getVersaoPlanoJson(VersaoPlano versaoPlano) {
        ObjectNode versaoPlanoJson = Json.newObject();

        if (versaoPlano.getId() != null) {
            versaoPlanoJson.put("id", versaoPlano.getId().toString());
        }

        if (versaoPlano.getIdJson() != null) {
            versaoPlanoJson.put(ID_JSON, versaoPlano.getIdJson());
        }

        if (versaoPlano.getStatusVersao() != null) {
            versaoPlanoJson.put("statusVersao", versaoPlano.getStatusVersao().toString());
        }

        if (versaoPlano.getAnel() != null && versaoPlano.getAnel().getIdJson() != null) {
            versaoPlanoJson.putObject("anel").put(ID_JSON, versaoPlano.getAnel().getIdJson());
        }

        refPlanos("planos", versaoPlano.getPlanos(), versaoPlanoJson);
        return versaoPlanoJson;
    }

    private JsonNode getPlanoJson(Plano plano) {
        ObjectNode planoJson = Json.newObject();

        if (plano.getId() != null) {
            planoJson.put("id", plano.getId().toString());
        }
        if (plano.getIdJson() != null) {
            planoJson.put(ID_JSON, plano.getIdJson());
        }
        if (plano.getPosicao() != null) {
            planoJson.put("posicao", plano.getPosicao());
        }
        if (plano.getDescricao() != null) {
            planoJson.put("descricao", plano.getDescricao());
        }
        if (plano.getTempoCiclo() != null) {
            planoJson.put("tempoCiclo", plano.getTempoCiclo());
        }
        planoJson.put("cicloDuplo", plano.isCicloDuplo());
        if (plano.getTempoCicloDuplo() != null) {
            planoJson.put("tempoCicloDuplo", plano.getTempoCicloDuplo());
        }
        if (plano.getDefasagem() != null) {
            planoJson.put("defasagem", plano.getDefasagem());
        }
        if (plano.getPosicaoTabelaEntreVerde() != null) {
            planoJson.put("posicaoTabelaEntreVerde", plano.getPosicaoTabelaEntreVerde());
        }
        if (plano.getModoOperacao() != null) {
            planoJson.put("modoOperacao", plano.getModoOperacao().toString());
            planoJson.put("isManual", plano.isManual());
            planoJson.put("isTemporario", plano.isTemporario());
        }
        if (plano.getDataCriacao() != null) {
            planoJson.put(DATA_CRIACAO, InfluuntDateTimeSerializer.parse(plano.getDataCriacao()));
        }
        if (plano.getDataAtualizacao() != null) {
            planoJson.put(DATA_ATUALIZACAO, InfluuntDateTimeSerializer.parse(plano.getDataAtualizacao()));
        }
        if (plano.getAnel() != null && plano.getAnel().getIdJson() != null) {
            planoJson.putObject("anel").put(ID_JSON, plano.getAnel().getIdJson());
        }

        refVersoesPlanos("versaoPlano", plano.getVersaoPlano(), planoJson);
        refEstagiosPlanos("estagiosPlanos", plano.getEstagiosPlanos(), planoJson);
        refGruposSemaforicosPlanos("gruposSemaforicosPlanos", plano.getGruposSemaforicosPlanos(), planoJson);

        return planoJson;
    }

    public JsonNode getPlanoCompletoJson(Plano plano) {
        ObjectNode planoJson = Json.newObject();

        if (plano.getId() != null) {
            planoJson.put("id", plano.getId().toString());
        }
        if (plano.getIdJson() != null) {
            planoJson.put(ID_JSON, plano.getIdJson());
        }
        if (plano.getPosicao() != null) {
            planoJson.put("posicao", plano.getPosicao());
        }
        if (plano.getDescricao() != null) {
            planoJson.put("descricao", plano.getDescricao());
        }
        if (plano.getTempoCiclo() != null) {
            planoJson.put("tempoCiclo", plano.getTempoCiclo());
        }
        if (plano.getDefasagem() != null) {
            planoJson.put("defasagem", plano.getDefasagem());
        }
        if (plano.getPosicaoTabelaEntreVerde() != null) {
            planoJson.put("posicaoTabelaEntreVerde", plano.getPosicaoTabelaEntreVerde());
        }
        if (plano.getModoOperacao() != null) {
            planoJson.put("modoOperacao", plano.getModoOperacao().toString());
        }

        planoJson.put("cicloDuplo", plano.isCicloDuplo());
        if (plano.getTempoCicloDuplo() != null) {
            planoJson.put("tempoCicloDuplo", plano.getTempoCicloDuplo());
        }
        if (plano.getDataCriacao() != null) {
            planoJson.put(DATA_CRIACAO, InfluuntDateTimeSerializer.parse(plano.getDataCriacao()));
        }
        if (plano.getDataAtualizacao() != null) {
            planoJson.put(DATA_ATUALIZACAO, InfluuntDateTimeSerializer.parse(plano.getDataAtualizacao()));
        }
        if (plano.getAnel() != null && plano.getAnel().getIdJson() != null) {
            planoJson.putObject("anel").put(ID_JSON, plano.getAnel().getIdJson());
        }

        if (plano.getEstagiosPlanos() != null) {
            ArrayNode epJson = planoJson.putArray("estagiosPlanos");
            plano.getEstagiosPlanos().forEach(estagioPlano -> epJson.add(getEstagioPlanoJson(estagioPlano)));
        }

        if (plano.getGruposSemaforicosPlanos() != null) {
            ArrayNode gspJson = planoJson.putArray("gruposSemaforicosPlanos");
            plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> gspJson.add(getGrupoSemaforicoPlanoJson(grupoSemaforicoPlano)));
        }

        if (plano.getVersaoPlano() != null) {
            planoJson.set("versaoPlano", getVersaoPlanoJson(plano.getVersaoPlano()));
        }

        return planoJson;
    }

    private JsonNode getGrupoSemaforicoPlanoJson(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        ObjectNode grupoSemaforicoPlanoJson = Json.newObject();

        if (grupoSemaforicoPlano.getId() != null) {
            grupoSemaforicoPlanoJson.put("id", grupoSemaforicoPlano.getId().toString());
        }
        if (grupoSemaforicoPlano.getIdJson() != null) {
            grupoSemaforicoPlanoJson.put(ID_JSON, grupoSemaforicoPlano.getIdJson());
        }

        if (grupoSemaforicoPlano.getPlano() != null && grupoSemaforicoPlano.getPlano().getIdJson() != null) {
            grupoSemaforicoPlanoJson.putObject("plano").put(ID_JSON, grupoSemaforicoPlano.getPlano().getIdJson());
        }

        if (grupoSemaforicoPlano.getGrupoSemaforico() != null && grupoSemaforicoPlano.getGrupoSemaforico().getIdJson() != null) {
            grupoSemaforicoPlanoJson.putObject("grupoSemaforico").put(ID_JSON, grupoSemaforicoPlano.getGrupoSemaforico().getIdJson());
        }

        grupoSemaforicoPlanoJson.put("ativado", grupoSemaforicoPlano.isAtivado());

        return grupoSemaforicoPlanoJson;
    }

    private JsonNode getEstagioPlanoJson(EstagioPlano estagioPlano) {
        ObjectNode estagioPlanoJson = Json.newObject();

        if (estagioPlano.getId() != null) {
            estagioPlanoJson.put("id", estagioPlano.getId().toString());
        }
        if (estagioPlano.getIdJson() != null) {
            estagioPlanoJson.put(ID_JSON, estagioPlano.getIdJson());
        }
        if (estagioPlano.getPosicao() != null) {
            estagioPlanoJson.put("posicao", estagioPlano.getPosicao());
        }
        if (estagioPlano.getTempoVerde() != null) {
            estagioPlanoJson.put("tempoVerde", estagioPlano.getTempoVerde());
        }
        if (estagioPlano.getTempoVerdeMinimo() != null) {
            estagioPlanoJson.put("tempoVerdeMinimo", estagioPlano.getTempoVerdeMinimo());
        }
        if (estagioPlano.getTempoVerdeMaximo() != null) {
            estagioPlanoJson.put("tempoVerdeMaximo", estagioPlano.getTempoVerdeMaximo());
        }
        if (estagioPlano.getTempoVerdeIntermediario() != null) {
            estagioPlanoJson.put("tempoVerdeIntermediario", estagioPlano.getTempoVerdeIntermediario());
        }
        if (estagioPlano.getTempoExtensaoVerde() != null) {
            estagioPlanoJson.put("tempoExtensaoVerde", estagioPlano.getTempoExtensaoVerde());
        }
        estagioPlanoJson.put("dispensavel", estagioPlano.isDispensavel());

        if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null && estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson() != null) {
            estagioPlanoJson.putObject("estagioQueRecebeEstagioDispensavel").put(ID_JSON, estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson());
        }

        if (estagioPlano.getPlano() != null && estagioPlano.getPlano().getIdJson() != null) {
            estagioPlanoJson.putObject("plano").put(ID_JSON, estagioPlano.getPlano().getIdJson());
        }

        if (estagioPlano.getEstagio() != null && estagioPlano.getEstagio().getIdJson() != null) {
            estagioPlanoJson.putObject("estagio").put(ID_JSON, estagioPlano.getEstagio().getIdJson());
        }

        return estagioPlanoJson;
    }

    private JsonNode getTabelaHorariaJson(TabelaHorario tabelaHoraria) {
        ObjectNode tabelaHorariaJson = Json.newObject();
        if (tabelaHoraria.getId() == null) {
            tabelaHorariaJson.putNull("id");
        } else {
            tabelaHorariaJson.put("id", tabelaHoraria.getId().toString());
        }

        if (tabelaHoraria.getIdJson() == null) {
            tabelaHorariaJson.putNull(ID_JSON);
        } else {
            tabelaHorariaJson.put(ID_JSON, tabelaHoraria.getIdJson());
        }

        if (tabelaHoraria.getVersaoTabelaHoraria() != null && tabelaHoraria.getVersaoTabelaHoraria().getIdJson() != null) {
            tabelaHorariaJson.putObject("versaoTabelaHoraria").put(ID_JSON, tabelaHoraria.getVersaoTabelaHoraria().getIdJson());
        }

        refEventos("eventos", tabelaHoraria.getEventos(), tabelaHorariaJson);
        return tabelaHorariaJson;
    }

    private JsonNode getEventoJson(Evento evento) {
        ObjectNode eventoJson = Json.newObject();
        if (evento.getId() != null) {
            eventoJson.put("id", evento.getId().toString());
        }
        if (evento.getIdJson() != null) {
            eventoJson.put(ID_JSON, evento.getIdJson());
        }
        if (evento.getPosicao() != null) {
            eventoJson.put("posicao", evento.getPosicao().toString());
        }
        if (evento.getTipo() != null) {
            eventoJson.put("tipo", evento.getTipo().toString());
        }
        if (evento.getDiaDaSemana() != null) {
            eventoJson.put("diaDaSemana", evento.getDiaDaSemana().toString());
        }
        if (evento.getNome() != null) {
            eventoJson.put(NOME, evento.getNome());
        }
        if (evento.getData() != null) {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            eventoJson.put("data", formatter.format(evento.getData()));
        }
        if (evento.getHorario() != null) {
            eventoJson.put("horario", evento.getHorario().toString());
        }
        if (evento.getPosicaoPlano() != null) {
            eventoJson.put("posicaoPlano", evento.getPosicaoPlano().toString());
        }
        if (evento.getTabelaHorario() != null && evento.getTabelaHorario().getIdJson() != null) {
            eventoJson.putObject("tabelaHoraria").put(ID_JSON, evento.getTabelaHorario().getIdJson());
        }

        return eventoJson;
    }

    private JsonNode getGrupoSemaforicoJson(GrupoSemaforico grupoSemaforico) {
        ObjectNode grupoSemaforicoJson = Json.newObject();

        if (grupoSemaforico.getId() != null) {
            grupoSemaforicoJson.put("id", grupoSemaforico.getId().toString());
        }

        if (grupoSemaforico.getIdJson() != null) {
            grupoSemaforicoJson.put(ID_JSON, grupoSemaforico.getIdJson().toString());
        }

        if (grupoSemaforico.getTipo() != null) {
            grupoSemaforicoJson.put("tipo", grupoSemaforico.getTipo().toString());
        }

        if (grupoSemaforico.getPosicao() != null) {
            grupoSemaforicoJson.put("posicao", grupoSemaforico.getPosicao());
        }

        if (grupoSemaforico.getDescricao() != null) {
            grupoSemaforicoJson.put("descricao", grupoSemaforico.getDescricao());
        }

        if (grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente() != null) {
            grupoSemaforicoJson.put("faseVermelhaApagadaAmareloIntermitente", grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente());
        }

        if (grupoSemaforico.getTempoVerdeSeguranca() != null) {
            grupoSemaforicoJson.put("tempoVerdeSeguranca", grupoSemaforico.getTempoVerdeSeguranca());
        }

        if (grupoSemaforico.getAnel() != null && grupoSemaforico.getAnel().getIdJson() != null) {
            grupoSemaforicoJson.putObject("anel").put(ID_JSON, grupoSemaforico.getAnel().getIdJson().toString());
        }

        refVerdesConflitantes("verdesConflitantesOrigem", grupoSemaforico.getVerdesConflitantesOrigem(), grupoSemaforicoJson);
        refVerdesConflitantes("verdesConflitantesDestino", grupoSemaforico.getVerdesConflitantesDestino(), grupoSemaforicoJson);
        refEstagiosGruposSemaforicos("estagiosGruposSemaforicos", grupoSemaforico.getEstagiosGruposSemaforicos(), grupoSemaforicoJson);
        refTransicoes("transicoes", grupoSemaforico.getTransicoesComPerdaDePassagem(), grupoSemaforicoJson);
        refTransicoesComGanhoDePassagem("transicoesComGanhoDePassagem", grupoSemaforico.getTransicoesComGanhoDePassagem(), grupoSemaforicoJson);
        refEntreVerdes("tabelasEntreVerdes", grupoSemaforico.getTabelasEntreVerdes(), grupoSemaforicoJson);

        return grupoSemaforicoJson;
    }

    private JsonNode getEstagioJson(Estagio estagio) {
        ObjectNode estagioJson = Json.newObject();
        if (estagio.getId() != null) {
            estagioJson.put("id", estagio.getId().toString());
        }

        if (estagio.getIdJson() != null) {
            estagioJson.put(ID_JSON, estagio.getIdJson().toString());
        }

        if (estagio.getDescricao() != null) {
            estagioJson.put("descricao", estagio.getDescricao());
        }
        if (estagio.getTempoMaximoPermanencia() != null) {
            estagioJson.put("tempoMaximoPermanencia", estagio.getTempoMaximoPermanencia());
        }
        if (estagio.isTempoMaximoPermanenciaAtivado() != null) {
            estagioJson.put("tempoMaximoPermanenciaAtivado", estagio.isTempoMaximoPermanenciaAtivado());
        }
        if (estagio.getDemandaPrioritaria() != null) {
            estagioJson.put("demandaPrioritaria", estagio.getDemandaPrioritaria());
        }
        if (estagio.getTempoVerdeDemandaPrioritaria() != null) {
            estagioJson.put("tempoVerdeDemandaPrioritaria", estagio.getTempoVerdeDemandaPrioritaria());
        }
        if (estagio.getPosicao() != null) {
            estagioJson.put("posicao", estagio.getPosicao());
        }

        if (estagio.getAnel() != null && estagio.getAnel().getIdJson() != null) {
            estagioJson.putObject("anel").put(ID_JSON, estagio.getAnel().getIdJson());
        }

        if (estagio.getImagem() != null) {
            estagioJson.putObject("imagem").put(ID_JSON, estagio.getImagem().getIdJson());
            imagensMap.put(estagio.getImagem().getIdJson(), estagio.getImagem());
        }

        if (estagio.getDetector() != null && estagio.getDetector().getIdJson() != null) {
            estagioJson.putObject("detector").put(ID_JSON, estagio.getDetector().getIdJson());
            detectoresMap.put(estagio.getDetector().getIdJson(), estagio.getDetector());
        }

        refTransicoesProibidas("origemDeTransicoesProibidas", estagio.getOrigemDeTransicoesProibidas(), estagioJson);
        refTransicoesProibidas("destinoDeTransicoesProibidas", estagio.getDestinoDeTransicoesProibidas(), estagioJson);
        refTransicoesProibidas("alternativaDeTransicoesProibidas", estagio.getAlternativaDeTransicoesProibidas(), estagioJson);
        refEstagiosGruposSemaforicos("estagiosGruposSemaforicos", estagio.getEstagiosGruposSemaforicos(), estagioJson);
        refEstagiosPlanos("estagiosPlanos", estagio.getEstagiosPlanos(), estagioJson);

        return estagioJson;
    }

    private JsonNode getDetectorJson(Detector detector) {
        ObjectNode detectorJson = Json.newObject();

        if (detector.getId() != null) {
            detectorJson.put("id", detector.getId().toString());
        }

        if (detector.getIdJson() != null) {
            detectorJson.put(ID_JSON, detector.getIdJson().toString());
        }

        if (detector.getTipo() != null) {
            detectorJson.put("tipo", detector.getTipo().toString());
        }
        if (detector.getPosicao() != null) {
            detectorJson.put("posicao", detector.getPosicao());
        }
        if (detector.getDescricao() != null) {
            detectorJson.put("descricao", detector.getDescricao());
        }

        detectorJson.put("monitorado", detector.isMonitorado());

        if (detector.getTempoAusenciaDeteccao() != null) {
            detectorJson.put("tempoAusenciaDeteccao", detector.getTempoAusenciaDeteccao());
        }
        if (detector.getTempoDeteccaoPermanente() != null) {
            detectorJson.put("tempoDeteccaoPermanente", detector.getTempoDeteccaoPermanente());
        }
        if (detector.getAnel() != null && detector.getAnel().getIdJson() != null) {
            detectorJson.putObject("anel").put(ID_JSON, detector.getAnel().getIdJson().toString());
        }
        if (detector.getEstagio() != null && detector.getEstagio().getIdJson() != null) {
            detectorJson.putObject("estagio").put(ID_JSON, detector.getEstagio().getIdJson().toString());
        }

        return detectorJson;
    }

    private JsonNode getImagemJson(Imagem imagem) {
        ObjectNode imagemJson = Json.newObject();

        if (imagem.getId() != null) {
            imagemJson.put("id", imagem.getId().toString());
        }

        if (imagem.getIdJson() != null) {
            imagemJson.put(ID_JSON, imagem.getIdJson().toString());
        }

        if (imagem.getFilename() != null) {
            imagemJson.put("fileName", imagem.getFilename());
        }
        if (imagem.getContentType() != null) {
            imagemJson.put("contentType", imagem.getContentType());
        }
        return imagemJson;
    }

    private JsonNode getTransicaoProibidaJson(TransicaoProibida transicaoProibida) {
        ObjectNode objectJson = Json.newObject();

        if (transicaoProibida.getId() != null) {
            objectJson.put("id", transicaoProibida.getId().toString());
        }

        if (transicaoProibida.getIdJson() != null) {
            objectJson.put(ID_JSON, transicaoProibida.getIdJson().toString());
        }

        if (transicaoProibida.getOrigem() != null && transicaoProibida.getOrigem().getIdJson() != null) {
            objectJson.putObject("origem").put(ID_JSON, transicaoProibida.getOrigem().getIdJson().toString());
        }

        if (transicaoProibida.getDestino() != null && transicaoProibida.getDestino().getIdJson() != null) {
            objectJson.putObject("destino").put(ID_JSON, transicaoProibida.getDestino().getIdJson().toString());
        }

        if (transicaoProibida.getAlternativo() != null && transicaoProibida.getAlternativo().getIdJson() != null) {
            objectJson.putObject("alternativo").put(ID_JSON, transicaoProibida.getAlternativo().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getEstagioGrupoSemaforicoJson(EstagioGrupoSemaforico estagioGrupo) {
        ObjectNode objectJson = Json.newObject();

        if (estagioGrupo.getId() != null) {
            objectJson.put("id", estagioGrupo.getId().toString());
        }

        if (estagioGrupo.getIdJson() != null) {
            objectJson.put(ID_JSON, estagioGrupo.getIdJson().toString());
        }

        if (estagioGrupo.getEstagio() != null && estagioGrupo.getEstagio().getIdJson() != null) {
            objectJson.putObject("estagio").put(ID_JSON, estagioGrupo.getEstagio().getIdJson().toString());
        }

        if (estagioGrupo.getGrupoSemaforico() != null && estagioGrupo.getGrupoSemaforico().getIdJson() != null) {
            objectJson.putObject("grupoSemaforico").put(ID_JSON, estagioGrupo.getGrupoSemaforico().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getVerdeConflitante(VerdesConflitantes verdesConflitantes) {
        ObjectNode objectJson = Json.newObject();

        if (verdesConflitantes.getId() != null) {
            objectJson.put("id", verdesConflitantes.getId().toString());
        }

        if (verdesConflitantes.getIdJson() != null) {
            objectJson.put(ID_JSON, verdesConflitantes.getIdJson().toString());
        }

        if (verdesConflitantes.getOrigem() != null && verdesConflitantes.getOrigem().getIdJson() != null) {
            objectJson.putObject("origem").put(ID_JSON, verdesConflitantes.getOrigem().getIdJson().toString());
        }

        if (verdesConflitantes.getDestino() != null && verdesConflitantes.getDestino().getIdJson() != null) {
            objectJson.putObject("destino").put(ID_JSON, verdesConflitantes.getDestino().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getTransicao(Transicao transicao) {
        ObjectNode transicaoJson = Json.newObject();

        if (transicao.getId() != null) {
            transicaoJson.put("id", transicao.getId().toString());
        }

        if (transicao.getIdJson() != null) {
            transicaoJson.put(ID_JSON, transicao.getIdJson().toString());
        }

        if (transicao.getOrigem() != null && transicao.getOrigem().getIdJson() != null) {
            transicaoJson.putObject("origem").put(ID_JSON, transicao.getOrigem().getIdJson().toString());
        }

        if (transicao.getDestino() != null && transicao.getDestino().getIdJson() != null) {
            transicaoJson.putObject("destino").put(ID_JSON, transicao.getDestino().getIdJson().toString());
        }
        refEntreVerdesTransicoes("tabelaEntreVerdesTransicoes", transicao.getTabelaEntreVerdesTransicoes(), transicaoJson);

        if (transicao.getGrupoSemaforico() != null && transicao.getGrupoSemaforico().getIdJson() != null) {
            transicaoJson.putObject("grupoSemaforico").put(ID_JSON, transicao.getGrupoSemaforico().getIdJson().toString());
        }

        if (transicao.getTipo() != null) {
            transicaoJson.put("tipo", transicao.getTipo().toString());
        }

        if (transicao.getAtrasoDeGrupo() != null) {
            transicaoJson.put("tempoAtrasoGrupo", transicao.getTempoAtrasoGrupo().toString());
            refAtrasoDeGrupo("atrasoDeGrupo", transicao.getAtrasoDeGrupo(), transicaoJson);
        }

        transicaoJson.put("modoIntermitenteOuApagado", transicao.isModoIntermitenteOuApagado());

        return transicaoJson;
    }

    private JsonNode getTabelaEntreVerde(TabelaEntreVerdes tabelaEntreVerdes) {
        ObjectNode objectJson = Json.newObject();

        if (tabelaEntreVerdes.getId() != null) {
            objectJson.put("id", tabelaEntreVerdes.getId().toString());
        }

        if (tabelaEntreVerdes.getIdJson() != null) {
            objectJson.put(ID_JSON, tabelaEntreVerdes.getIdJson().toString());
        }

        if (tabelaEntreVerdes.getDescricao() != null) {
            objectJson.put("descricao", tabelaEntreVerdes.getDescricao());
        }
        if (tabelaEntreVerdes.getPosicao() != null) {
            objectJson.put("posicao", tabelaEntreVerdes.getPosicao());
        }

        if (tabelaEntreVerdes.getGrupoSemaforico() != null && tabelaEntreVerdes.getGrupoSemaforico().getIdJson() != null) {
            objectJson.putObject("grupoSemaforico").put(ID_JSON, tabelaEntreVerdes.getGrupoSemaforico().getIdJson().toString());
        }

        refEntreVerdesTransicoes("tabelaEntreVerdesTransicoes", tabelaEntreVerdes.getTabelaEntreVerdesTransicoes(), objectJson);

        return objectJson;
    }

    private JsonNode getTabelaEntreVerdeTransicoes(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        ObjectNode objectJson = Json.newObject();


        if (tabelaEntreVerdesTransicao.getId() != null) {
            objectJson.put("id", tabelaEntreVerdesTransicao.getId().toString());
        }

        if (tabelaEntreVerdesTransicao.getIdJson() != null) {
            objectJson.put(ID_JSON, tabelaEntreVerdesTransicao.getIdJson().toString());
        }

        if (tabelaEntreVerdesTransicao.getTempoAmarelo() != null) {
            objectJson.put("tempoAmarelo", tabelaEntreVerdesTransicao.getTempoAmarelo().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoVermelhoIntermitente() != null) {
            objectJson.put("tempoVermelhoIntermitente", tabelaEntreVerdesTransicao.getTempoVermelhoIntermitente().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoVermelhoLimpeza() != null) {
            objectJson.put("tempoVermelhoLimpeza", tabelaEntreVerdesTransicao.getTempoVermelhoLimpeza().toString());
        }
        if (tabelaEntreVerdesTransicao.getTempoAtrasoGrupo() != null) {
            objectJson.put("tempoAtrasoGrupo", tabelaEntreVerdesTransicao.getTempoAtrasoGrupo().toString());
        }

        if (tabelaEntreVerdesTransicao.getTabelaEntreVerdes() != null && tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getIdJson() != null) {
            objectJson.putObject("tabelaEntreVerdes").put(ID_JSON, tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getIdJson().toString());
        }

        if (tabelaEntreVerdesTransicao.getTransicao() != null && tabelaEntreVerdesTransicao.getTransicao().getIdJson() != null) {
            objectJson.putObject("transicao").put(ID_JSON, tabelaEntreVerdesTransicao.getTransicao().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getEnderecoJson(Endereco endereco) {
        ObjectNode enderecoJson = Json.newObject();
        if (endereco.getId() != null) {
            enderecoJson.put("id", endereco.getId().toString());
        }

        if (endereco.getIdJson() != null) {
            enderecoJson.put(ID_JSON, endereco.getIdJson().toString());
        }

        if (endereco.getLocalizacao() != null) {
            enderecoJson.put("localizacao", endereco.getLocalizacao());
        }
        if (endereco.getLatitude() != null) {
            enderecoJson.put("latitude", endereco.getLatitude());
        }
        if (endereco.getLongitude() != null) {
            enderecoJson.put("longitude", endereco.getLongitude());
        }
        if (endereco.getLocalizacao2() != null) {
            enderecoJson.put("localizacao2", endereco.getLocalizacao2());
        }
        if (endereco.getAlturaNumerica() != null) {
            enderecoJson.put("alturaNumerica", endereco.getAlturaNumerica());
        }
        if (endereco.getReferencia() != null) {
            enderecoJson.put("referencia", endereco.getReferencia());
        }

        return enderecoJson;
    }

    private JsonNode getAtrasoDeGrupoJson(AtrasoDeGrupo atrasoDeGrupo) {
        ObjectNode atrasoDeGrupoJson = Json.newObject();
        if (atrasoDeGrupo.getId() != null) {
            atrasoDeGrupoJson.put("id", atrasoDeGrupo.getId().toString());
        }

        if (atrasoDeGrupo.getIdJson() != null) {
            atrasoDeGrupoJson.put(ID_JSON, atrasoDeGrupo.getIdJson().toString());
        }

        if (atrasoDeGrupo.getAtrasoDeGrupo() != null) {
            atrasoDeGrupoJson.put("atrasoDeGrupo", atrasoDeGrupo.getAtrasoDeGrupo());
        }
        return atrasoDeGrupoJson;
    }


    private JsonNode putAnel(Anel anel) {
        ObjectNode anelJson = Json.newObject();
        if (anel.getId() != null) {
            anelJson.put("id", anel.getId().toString());
        }
        if (anel.getIdJson() != null) {
            anelJson.put(ID_JSON, anel.getIdJson().toString());
        }
        if (anel.getNumeroSMEE() != null) {
            anelJson.put("numeroSMEE", anel.getNumeroSMEE().toString());
        }
        if (anel.getDescricao() != null) {
            anelJson.put("descricao", anel.getDescricao());
        }
        if (anel.isAtivo() != null) {
            anelJson.put("ativo", anel.isAtivo());
        }

        anelJson.put("aceitaModoManual", anel.isAceitaModoManual());

        if (anel.getPosicao() != null) {
            anelJson.put("posicao", anel.getPosicao());
        }

        if (anel.getCLA() != null) {
            anelJson.put("CLA", anel.getCLA());
        }


        if (anel.getCroqui() != null) {
            ObjectNode croquiJson = Json.newObject();
            Imagem croqui = anel.getCroqui();

            if (croqui.getId() != null) {
                croquiJson.put("id", croqui.getId().toString());
            }

            if (croqui.getIdJson() != null) {
                croquiJson.put(ID_JSON, croqui.getIdJson().toString());
            }

            anelJson.set("croqui", croquiJson);
            imagensMap.put(croqui.getIdJson(), croqui);
        }

        refVersoesPlanos("versaoPlano", anel.getVersaoPlano(), anelJson);
        refEstagios(anel.getEstagios(), anelJson);
        refGruposSemaforicos(anel.getGruposSemaforicos(), anelJson);
        refDetectores(anel.getDetectores(), anelJson);
        refPlanos("planos", anel.getPlanos(), anelJson);

        refEndereco("endereco", anel.getEndereco(), anelJson);

        return anelJson;
    }

    private void refEndereco(String name, Endereco endereco, ObjectNode parentJson) {
        if (endereco != null && endereco.getIdJson() != null) {
            enderecosMap.put(endereco.getIdJson().toString(), endereco);
            ObjectNode enderecoJson = Json.newObject();
            enderecoJson.put(ID_JSON, endereco.getIdJson().toString());
            parentJson.set(name, enderecoJson);
        }
    }

    private void refEntreVerdesTransicoes(String name, List<TabelaEntreVerdesTransicao> entreVerdesTransicoes, ObjectNode parentJson) {
        ArrayNode entreVerdesTransicaoJson = Json.newArray();
        for (TabelaEntreVerdesTransicao entreVerdesTransicao : entreVerdesTransicoes) {
            if (entreVerdesTransicao.getIdJson() != null) {
                entreVerdesTransicoesMap.put(entreVerdesTransicao.getIdJson().toString(), entreVerdesTransicao);
                ObjectNode entreVerdeJson = Json.newObject();
                entreVerdeJson.put(ID_JSON, entreVerdesTransicao.getIdJson().toString());
                entreVerdesTransicaoJson.add(entreVerdeJson);
            }
        }
        parentJson.set(name, entreVerdesTransicaoJson);
    }

    private void refEntreVerdes(String name, List<TabelaEntreVerdes> entreVerdes, ObjectNode parentJson) {
        ArrayNode entreVerdesJson = Json.newArray();
        for (TabelaEntreVerdes entreVerde : entreVerdes) {
            if (entreVerde.getIdJson() != null) {
                entreVerdesMap.put(entreVerde.getIdJson().toString(), entreVerde);
                ObjectNode entreVerdeJson = Json.newObject();
                entreVerdeJson.put(ID_JSON, entreVerde.getIdJson().toString());
                entreVerdesJson.add(entreVerdeJson);
            }
        }
        parentJson.set(name, entreVerdesJson);
    }

    private void refTransicoes(String name, List<Transicao> transicoes, ObjectNode parentJson) {
        ArrayNode transicoesJson = Json.newArray();
        for (Transicao transicao : transicoes) {
            if (transicao.getIdJson() != null) {
                transicoesMap.put(transicao.getIdJson().toString(), transicao);
                ObjectNode transicaoJson = Json.newObject();
                transicaoJson.put(ID_JSON, transicao.getIdJson().toString());
                transicoesJson.add(transicaoJson);
            }
        }
        parentJson.set(name, transicoesJson);
    }

    private void refTransicoesComGanhoDePassagem(String name, List<Transicao> transicoes, ObjectNode parentJson) {
        ArrayNode transicoesJson = Json.newArray();
        for (Transicao transicao : transicoes) {
            if (transicao.getIdJson() != null) {
                transicoesComPerdaDePassagemMap.put(transicao.getIdJson().toString(), transicao);
                ObjectNode transicaoJson = Json.newObject();
                transicaoJson.put(ID_JSON, transicao.getIdJson());
                transicoesJson.add(transicaoJson);
            }
        }
        parentJson.set(name, transicoesJson);
    }

    private void refAtrasoDeGrupo(String name, AtrasoDeGrupo atrasoDeGrupo, ObjectNode parentJson) {
        if (atrasoDeGrupo.getIdJson() != null) {
            atrasosDeGrupoMap.put(atrasoDeGrupo.getIdJson().toString(), atrasoDeGrupo);
            ObjectNode atrasoDeGrupoJson = Json.newObject();
            atrasoDeGrupoJson.put(ID_JSON, atrasoDeGrupo.getIdJson());
            parentJson.set(name, atrasoDeGrupoJson);
        }
    }


    private void refVerdesConflitantes(String name, List<VerdesConflitantes> verdesConflitantes, ObjectNode parentJson) {
        ArrayNode verdesConflitantesJson = Json.newArray();
        for (VerdesConflitantes verdeConflitante : verdesConflitantes) {
            if (verdeConflitante != null && verdeConflitante.getIdJson() != null) {
                verdesConflitantesMap.put(verdeConflitante.getIdJson().toString(), verdeConflitante);
                ObjectNode verdeConflitanteJson = Json.newObject();
                verdeConflitanteJson.put(ID_JSON, verdeConflitante.getIdJson().toString());
                verdesConflitantesJson.add(verdeConflitanteJson);
            }
        }
        parentJson.set(name, verdesConflitantesJson);
    }

    private void refEstagiosGruposSemaforicos(String name, List<EstagioGrupoSemaforico> estagioGrupoSemaforicos, ObjectNode parentJson) {
        ArrayNode estagiosGruposSemaforicosJson = Json.newArray();
        for (EstagioGrupoSemaforico estagioGrupoSemaforico : estagioGrupoSemaforicos) {
            if (estagioGrupoSemaforico != null && estagioGrupoSemaforico.getIdJson() != null) {
                estagiosGruposSemaforicosMap.put(estagioGrupoSemaforico.getIdJson().toString(), estagioGrupoSemaforico);
                ObjectNode estagioGrupoSemaforicoJson = Json.newObject();
                estagioGrupoSemaforicoJson.put(ID_JSON, estagioGrupoSemaforico.getIdJson().toString());
                estagiosGruposSemaforicosJson.add(estagioGrupoSemaforicoJson);
            }
        }
        parentJson.set(name, estagiosGruposSemaforicosJson);

    }

    private void refTransicoesProibidas(String name, List<TransicaoProibida> transicaoProibidas, ObjectNode parentJson) {
        ArrayNode transicoesProibidasJson = Json.newArray();
        for (TransicaoProibida transicaoProibida : transicaoProibidas) {
            if (transicaoProibida != null && transicaoProibida.getIdJson() != null) {
                transicoesProibidasMap.put(transicaoProibida.getIdJson().toString(), transicaoProibida);
                ObjectNode transicaoProibidaJson = Json.newObject();
                transicaoProibidaJson.put(ID_JSON, transicaoProibida.getIdJson().toString());
                transicoesProibidasJson.add(transicaoProibidaJson);
            }
        }
        parentJson.set(name, transicoesProibidasJson);

    }

    private void refAneis(String name, List<Anel> aneis, ObjectNode parentJson) {
        ArrayNode aneisJson = Json.newArray();
        for (Anel anel : aneis) {
            if (anel.getIdJson() != null) {
                ObjectNode anelJson = Json.newObject();
                anelJson.put(ID_JSON, anel.getIdJson().toString());
                aneisJson.add(anelJson);
            }
        }
        parentJson.set(name, aneisJson);
    }

    private void refDetectores(List<Detector> detectores, ObjectNode parentJson) {
        ArrayNode detectoresJson = Json.newArray();
        for (Detector detector : detectores) {
            if (detector.getIdJson() != null) {
                detectoresMap.put(detector.getIdJson().toString(), detector);
                ObjectNode detecttorJson = Json.newObject();
                detecttorJson.put(ID_JSON, detector.getIdJson().toString());
                detectoresJson.add(detecttorJson);
            }
        }
        parentJson.set("detectores", detectoresJson);

    }

    private void refGruposSemaforicos(List<GrupoSemaforico> grupoSemaforicos, ObjectNode parentJson) {
        ArrayNode gruposSemaforicosJson = Json.newArray();
        for (GrupoSemaforico grupoSemaforico : grupoSemaforicos) {
            if (grupoSemaforico != null && grupoSemaforico.getIdJson() != null) {
                gruposSemaforicosMap.put(grupoSemaforico.getIdJson().toString(), grupoSemaforico);
                ObjectNode grupoSemaforicoJson = Json.newObject();
                grupoSemaforicoJson.put(ID_JSON, grupoSemaforico.getIdJson().toString());
                gruposSemaforicosJson.add(grupoSemaforicoJson);
            }
        }
        parentJson.set("gruposSemaforicos", gruposSemaforicosJson);

    }


    private void refEstagios(List<Estagio> estagios, ObjectNode parentJson) {
        ArrayNode estagiosJson = Json.newArray();
        for (Estagio estagio : estagios) {
            if (estagio != null && estagio.getIdJson() != null) {
                estagiosMap.put(estagio.getIdJson().toString(), estagio);
                ObjectNode estagioJson = Json.newObject();
                estagioJson.put(ID_JSON, estagio.getIdJson().toString());
                estagiosJson.add(estagioJson);
            }
        }
        parentJson.set("estagios", estagiosJson);

    }

    private void refVersoesTabelasHorarias(String name, List<VersaoTabelaHoraria> versoesTabelaHorarias, ObjectNode parentJson) {
        ArrayNode versoesTabelaHorariasJson = Json.newArray();
        for (VersaoTabelaHoraria versaoTabelaHoraria : versoesTabelaHorarias) {
            if (versaoTabelaHoraria != null && versaoTabelaHoraria.getIdJson() != null) {
                versoesTabelasHorariasMap.put(versaoTabelaHoraria.getIdJson().toString(), versaoTabelaHoraria);
                ObjectNode versaoTabelaHorariaJson = Json.newObject();
                versaoTabelaHorariaJson.put(ID_JSON, versaoTabelaHoraria.getIdJson().toString());
                versoesTabelaHorariasJson.add(versaoTabelaHorariaJson);
            }
        }
        parentJson.set(name, versoesTabelaHorariasJson);
    }

    private void refVersoesPlanos(String name, VersaoPlano versaoPlano, ObjectNode parentJson) {
        if (versaoPlano != null && versaoPlano.getIdJson() != null) {
            versoesPlanosMap.put(versaoPlano.getIdJson().toString(), versaoPlano);
            ObjectNode versaoPlanoJson = Json.newObject();
            versaoPlanoJson.put(ID_JSON, versaoPlano.getIdJson().toString());
            parentJson.set(name, versaoPlanoJson);
        }
    }

    private void refPlanos(String name, List<Plano> planos, ObjectNode parentJson) {
        ArrayNode planosJson = Json.newArray();
        for (Plano plano : planos) {
            if (plano != null && plano.getIdJson() != null) {
                planosMap.put(plano.getIdJson(), plano);
                ObjectNode planoJson = Json.newObject();
                planoJson.put(ID_JSON, plano.getIdJson());
                planosJson.add(planoJson);
            }
        }
        parentJson.set(name, planosJson);

    }

    private void refEstagiosPlanos(String name, List<EstagioPlano> estagioPlanos, ObjectNode parentJson) {
        ArrayNode estagioPlanosJson = Json.newArray();
        for (EstagioPlano estagioPlano : estagioPlanos) {
            if (estagioPlano != null && estagioPlano.getIdJson() != null) {
                estagioPlanoMap.put(estagioPlano.getIdJson(), estagioPlano);
                ObjectNode estagioJson = Json.newObject();
                estagioJson.put(ID_JSON, estagioPlano.getIdJson());
                estagioPlanosJson.add(estagioJson);
            }
        }
        parentJson.set(name, estagioPlanosJson);
    }

    private void refGruposSemaforicosPlanos(String name, List<GrupoSemaforicoPlano> grupoSemaforicoPlanos, ObjectNode parentJson) {
        ArrayNode grupoSemaforicoPlanosJson = Json.newArray();
        for (GrupoSemaforicoPlano grupoSemaforicoPlano : grupoSemaforicoPlanos) {
            if (grupoSemaforicoPlano != null && grupoSemaforicoPlano.getIdJson() != null) {
                grupoSemaforicoPlanoMap.put(grupoSemaforicoPlano.getIdJson().toString(), grupoSemaforicoPlano);
                ObjectNode grupoSemaforicoPlanoJson = Json.newObject();
                grupoSemaforicoPlanoJson.put(ID_JSON, grupoSemaforicoPlano.getIdJson().toString());
                grupoSemaforicoPlanosJson.add(grupoSemaforicoPlanoJson);
            }
        }
        parentJson.set(name, grupoSemaforicoPlanosJson);
    }

    private void refEventos(String name, List<Evento> eventos, ObjectNode parentJson) {
        ArrayNode eventosJson = Json.newArray();
        for (Evento evento : eventos) {
            if (evento != null && evento.getIdJson() != null) {
                eventosMap.put(evento.getIdJson().toString(), evento);
                ObjectNode eventoJson = Json.newObject();
                eventoJson.put(ID_JSON, evento.getIdJson().toString());
                eventosJson.add(eventoJson);
            }
        }
        parentJson.set(name, eventosJson);
    }

    private void refAreas(String name, List<Area> areas, ObjectNode parentJson) {
        ArrayNode areasJson = Json.newArray();
        for (Area area : areas) {
            if (area != null && area.getIdJson() != null) {
                areasMap.put(area.getIdJson().toString(), area);
                ObjectNode areaJson = Json.newObject();
                areaJson.put(ID_JSON, area.getIdJson().toString());
                areasJson.add(areaJson);
            }
        }
        parentJson.set(name, areasJson);

    }

    private void refLimites(String name, List<LimiteArea> limites, ObjectNode parentJson) {
        ArrayNode limitesJson = Json.newArray();
        for (LimiteArea limite : limites) {
            if (limite != null && limite.getIdJson() != null) {
                limitesMap.put(limite.getIdJson(), limite);
                ObjectNode limiteJson = Json.newObject();
                limiteJson.put(ID_JSON, limite.getIdJson());
                limitesJson.add(limiteJson);
            }
        }

        parentJson.set(name, limitesJson);
    }

    private void refSubareas(String name, List<Subarea> subareas, ObjectNode parentJson) {
        ArrayNode subareasJson = Json.newArray();
        for (Subarea subarea : subareas) {
            if (subarea != null && subarea.getIdJson() != null) {
                ObjectNode subareaJson = Json.newObject();
                subareaJson.put("id", subarea.getId().toString());
                subareaJson.put(ID_JSON, subarea.getIdJson());
                subareaJson.put(NOME, subarea.getNome());
                subareaJson.put("numero", subarea.getNumero().toString());
                subareasJson.add(subareaJson);
            }
        }

        parentJson.set(name, subareasJson);
    }
}
