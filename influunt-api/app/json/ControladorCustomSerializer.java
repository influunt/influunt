package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import play.libs.Json;
import scala.util.parsing.json.JSONArray;
import utils.RangeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 7/29/16.
 */
public class ControladorCustomSerializer {

    private Map<String, Estagio> estagiosMap = new HashMap<String, Estagio>();
    private Map<String, GrupoSemaforico> gruposSemaforicosMap = new HashMap<String, GrupoSemaforico>();
    private Map<String, Detector> detectoresMap = new HashMap<String, Detector>();
    private Map<String, Imagem> imagensMap = new HashMap<String, Imagem>();
    private Map<String, TransicaoProibida> transicoesProibidasMap = new HashMap<String, TransicaoProibida>();
    private Map<String, EstagioGrupoSemaforico> estagiosGruposSemaforicosMap = new HashMap<String, EstagioGrupoSemaforico>();
    private Map<String, VerdesConflitantes> verdesConflitantesMap = new HashMap<String, VerdesConflitantes>();
    private Map<String, Transicao> transicoesMap = new HashMap<String, Transicao>();
    private Map<String, TabelaEntreVerdes> entreVerdesMap = new HashMap<String, TabelaEntreVerdes>();
    private Map<String, TabelaEntreVerdesTransicao> entreVerdesTransicoesMap = new HashMap<String, TabelaEntreVerdesTransicao>();
    private Map<String, Plano> planosMap = new HashMap<String, Plano>();
    private Map<String, GrupoSemaforicoPlano> grupoSemaforicoPlanoMap = new HashMap<String, GrupoSemaforicoPlano>();
    private Map<String, EstagioPlano> estagioPlanoMap = new HashMap<String, EstagioPlano>();
    private Map<String, TabelaHorario> tabelasHorariosMap = new HashMap<String, TabelaHorario>();
    private Map<String, Evento> eventosMap = new HashMap<String, Evento>();
    private Map<String, Endereco> enderecosMap = new HashMap<String, Endereco>();
    private Map<String, Area> areasMap = new HashMap<String, Area>();

    public JsonNode getControladorJson(Controlador controlador) {
        ObjectNode root = Json.newObject();

        putControladorDadosBasicos(controlador, root);
        putControladorModelo(controlador.getModelo(), root);
        putControladorAneis(controlador.getAneis(), root);
        putControladorEstagios(root);
        putControladorGruposSemaforicos(root);
        putControladorDetector(root);

        putControladorTransicoesProibidas(root);
        putControladorEstagiosGruposSemaforicos(root);
        putControladorVerdesConflitantes(root);
        putControladorTransicoes(root);
        putControladorTabelaEntreVerdes(root);
        putControladorTabelaEntreVerdesTransicoes(root);

        putControladorPlano(root);
        putControladorGruposSemaforicosPlanos(root);
        putControladorEstagiosPlanos(root);

        putControladorTabelasHorarios(root);
        putControladorEventos(root);

        putControladorCidades(root);
        putControladorAreas(root);
        putControladorEnderecos(root);
        putControladorImagens(root);

        return root;
    }

    public JsonNode getControladoresJson(List<Controlador> controladores) {
        ArrayList<JsonNode> jsonControladores = new ArrayList<JsonNode>();

        ArrayNode controladoresJson = Json.newArray();
        for (Controlador controlador : controladores) {
            controladoresJson.add(getControladorJson(controlador));
        }

        return controladoresJson;
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

    private void putControladorTabelasHorarios(ObjectNode root) {
        ArrayNode tabelaHorarioJson = Json.newArray();
        tabelasHorariosMap.values().stream().forEach(tabelaHorario -> {
            tabelaHorarioJson.add(getTabelaHorarioJson(tabelaHorario));
        });
        root.set("tabelasHorarios", tabelaHorarioJson);
    }

    private void putControladorEventos(ObjectNode root) {
        ArrayNode eventoJson = Json.newArray();
        eventosMap.values().stream().forEach(evento -> {
            eventoJson.add(getEventoJson(evento));
        });
        root.set("eventos", eventoJson);
    }

    private void putControladorDadosBasicos(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
        }
        if (controlador.getIdJson() != null) {
            root.put("idJson", controlador.getIdJson().toString());
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
        if (controlador.getSequencia() != null) {
            root.put("sequencia", controlador.getSequencia());
        }
        if (controlador.getLimiteEstagio() != null) {
            root.put("limiteEstagio", controlador.getLimiteEstagio());
        }
        if (controlador.getLimiteGrupoSemaforico() != null) {
            root.put("limiteGrupoSemaforico", controlador.getLimiteGrupoSemaforico());
        }
        if (controlador.getLimiteAnel() != null) {
            root.put("limiteAnel", controlador.getLimiteAnel());
        }
        if (controlador.getLimiteDetectorPedestre() != null) {
            root.put("limiteDetectorPedestre", controlador.getLimiteDetectorPedestre());
        }
        if (controlador.getLimiteDetectorVeicular() != null) {
            root.put("limiteDetectorVeicular", controlador.getLimiteDetectorVeicular());
        }
        if (controlador.getLimiteTabelasEntreVerdes() != null) {
            root.put("limiteTabelasEntreVerdes", controlador.getLimiteTabelasEntreVerdes());
        }

        if (controlador.getNomeEndereco() != null) {
            root.put("nomeEndereco", controlador.getNomeEndereco());
        }


        root.put("dataCriacao", InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));

        root.put("dataAtualizacao", InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));

        root.put("CLC", controlador.getCLC());

        root.put("verdeMin", RangeUtils.TEMPO_VERDE.getMin().toString());
        root.put("verdeMax", RangeUtils.TEMPO_VERDE.getMax().toString());
        root.put("verdeMinimoMin", RangeUtils.TEMPO_VERDE_MINIMO.getMin().toString());
        root.put("verdeMinimoMax", RangeUtils.TEMPO_VERDE_MINIMO.getMax().toString());
        root.put("verdeMaximoMin", RangeUtils.TEMPO_VERDE_MAXIMO.getMin().toString());
        root.put("verdeMaximoMax", RangeUtils.TEMPO_VERDE_MAXIMO.getMax().toString());
        root.put("extensaVerdeMin", RangeUtils.TEMPO_EXTENSAO_VERDE.getMin().toString());
        root.put("extensaVerdeMax", RangeUtils.TEMPO_EXTENSAO_VERDE.getMax().toString());
        root.put("verdeIntermediarioMin", RangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMin().toString());
        root.put("verdeIntermediarioMax", RangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMax().toString());
        root.put("defasagemMin", RangeUtils.TEMPO_DEFASAGEM.getMin().toString());
        root.put("defasagemMax", RangeUtils.TEMPO_DEFASAGEM.getMax().toString());
        root.put("amareloMin", RangeUtils.TEMPO_AMARELO.getMin().toString());
        root.put("amareloMax", RangeUtils.TEMPO_AMARELO.getMax().toString());
        root.put("vermelhoIntermitenteMin", RangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMin().toString());
        root.put("vermelhoIntermitenteMax", RangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMax().toString());
        root.put("vermelhoLimpezaVeicularMin", RangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMin().toString());
        root.put("vermelhoLimpezaVeicularMax", RangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMax().toString());
        root.put("vermelhoLimpezaPedestreMin", RangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMin().toString());
        root.put("vermelhoLimpezaPedestreMax", RangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMax().toString());
        root.put("atrasoGrupoMin", RangeUtils.TEMPO_ATRASO_GRUPO.getMin().toString());
        root.put("atrasoGrupoMax", RangeUtils.TEMPO_ATRASO_GRUPO.getMax().toString());
        root.put("verdeSegurancaVeicularMin", RangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMin().toString());
        root.put("verdeSegurancaVeicularMax", RangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMax().toString());
        root.put("verdeSegurancaPedestreMin", RangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMin().toString());
        root.put("verdeSegurancaPedestreMax", RangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMax().toString());
        root.put("maximoPermanenciaEstagioMin", RangeUtils.TEMPO_MAXIMO_PERMANECIA_ESTAGIO.getMin().toString());
        root.put("maximoPermanenciaEstagioMax", RangeUtils.TEMPO_MAXIMO_PERMANECIA_ESTAGIO.getMax().toString());
        root.put("cicloMin", RangeUtils.TEMPO_CICLO.getMin().toString());
        root.put("cicloMax", RangeUtils.TEMPO_CICLO.getMax().toString());

        if (controlador.getStatusControlador() != null) {
            root.put("statusControlador", controlador.getStatusControlador().toString());
        }

        if (controlador.getArea() != null && controlador.getArea().getIdJson() != null) {
            root.putObject("area").put("idJson", controlador.getArea().getIdJson().toString());
        }

        refEnderecos("enderecos", controlador.getEnderecos(), root);

    }

    private void putControladorModelo(ModeloControlador modeloControlador, ObjectNode root) {
        if (modeloControlador == null) {
            return;
        }
        ObjectNode modeloJson = Json.newObject();
        if (modeloControlador.getId() == null) {
            modeloJson.putNull("id");
        } else {
            modeloJson.put("id", modeloControlador.getId().toString());
        }

        if (modeloControlador.getIdJson() == null) {
            modeloJson.putNull("idJson");
        } else {
            modeloJson.put("idJson", modeloControlador.getIdJson().toString());
        }
        if (modeloControlador.getDescricao() != null) {
            modeloJson.put("descricao", modeloControlador.getDescricao());
        }

        if (modeloControlador.getFabricante() != null) {

            ObjectNode fabricanteJson = Json.newObject();
            Fabricante fabricante = modeloControlador.getFabricante();

            if (fabricante.getId() == null) {
                fabricanteJson.putNull("id");
            } else {
                fabricanteJson.put("id", fabricante.getId().toString());
            }

            if (fabricante.getNome() != null) {
                fabricanteJson.put("nome", fabricante.getNome());
            }
            modeloJson.set("fabricante", fabricanteJson);

        }
        root.set("modelo", modeloJson);
    }

    private JsonNode getAreaJson(Area area) {
        ObjectNode areaJson = Json.newObject();
        if (area.getId() != null) {
            areaJson.put("id", area.getId().toString());
        }
        if (area.getIdJson() != null) {
            areaJson.put("idJson", area.getIdJson().toString());
        }
        areaJson.put("descricao", area.getDescricao());
        if (area.getCidade() != null && area.getCidade().getIdJson() != null) {
            areaJson.putObject("cidade").put("idJson", area.getCidade().getIdJson().toString());
        }

        return areaJson;
    }

    private JsonNode getCidadeJson(Cidade cidade) {
        ObjectNode cidadeJson = Json.newObject();

        if (cidade.getId() != null) {
            cidadeJson.put("id", cidade.getId().toString());
        }
        if (cidade.getIdJson() != null) {
            cidadeJson.put("idJson", cidade.getIdJson().toString());
        }
        cidadeJson.put("nome", cidade.getNome());

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

    private void putControladorCidades(ObjectNode root) {
        ArrayNode cidadesJson = Json.newArray();
        Cidade.find.all().stream().forEach(cidade -> {
            cidadesJson.add(getCidadeJson(cidade));
        });
        root.set("cidades", cidadesJson);
    }

    private void putControladorAreas(ObjectNode root) {
        ArrayNode areasJson = Json.newArray();
        areasMap.values().stream().forEach(area -> {
            areasJson.add(getAreaJson(area));
        });
        root.set("areas", areasJson);
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


    private JsonNode getPlanoJson(Plano plano) {
        ObjectNode planoJson = Json.newObject();

        if (plano.getId() != null) {
            planoJson.put("id", plano.getId().toString());
        }
        if (plano.getIdJson() != null) {
            planoJson.put("idJson", plano.getIdJson().toString());
        }
        if (plano.getPosicao() != null) {
            planoJson.put("posicao", plano.getPosicao());
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
        if (plano.getDataCriacao() != null) {
            planoJson.put("dataCriacao", InfluuntDateTimeSerializer.parse(plano.getDataCriacao()));
        }
        if (plano.getDataAtualizacao() != null) {
            planoJson.put("dataAtualizacao", InfluuntDateTimeSerializer.parse(plano.getDataAtualizacao()));
        }
        if (plano.getAnel() != null && plano.getAnel().getIdJson() != null) {
            planoJson.putObject("anel").put("idJson", plano.getAnel().getIdJson().toString());
        }
        if (plano.getAgrupamento() != null && plano.getAgrupamento().getIdJson() != null) {
            planoJson.putObject("agrupamento").put("idJson", plano.getAgrupamento().getIdJson().toString());
        }
        refEstagiosPlanos("estagiosPlanos", plano.getEstagiosPlanos(), planoJson);
        refGruposSemaforicosPlanos("gruposSemaforicosPlanos", plano.getGruposSemaforicosPlanos(), planoJson);

        return planoJson;
    }

    private JsonNode getGrupoSemaforicoPlanoJson(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        ObjectNode grupoSemaforicoPlanoJson = Json.newObject();

        if (grupoSemaforicoPlano.getId() != null) {
            grupoSemaforicoPlanoJson.put("id", grupoSemaforicoPlano.getId().toString());
        }
        if (grupoSemaforicoPlano.getIdJson() != null) {
            grupoSemaforicoPlanoJson.put("idJson", grupoSemaforicoPlano.getIdJson().toString());
        }

        if (grupoSemaforicoPlano.getPlano() != null && grupoSemaforicoPlano.getPlano().getIdJson() != null) {
            grupoSemaforicoPlanoJson.putObject("plano").put("idJson", grupoSemaforicoPlano.getPlano().getIdJson().toString());
        }

        if (grupoSemaforicoPlano.getGrupoSemaforico() != null && grupoSemaforicoPlano.getGrupoSemaforico().getIdJson() != null) {
            grupoSemaforicoPlanoJson.putObject("grupoSemaforico").put("idJson", grupoSemaforicoPlano.getGrupoSemaforico().getIdJson().toString());
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
            estagioPlanoJson.put("idJson", estagioPlano.getIdJson().toString());
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
            estagioPlanoJson.putObject("estagioQueRecebeEstagioDispensavel").put("idJson", estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson().toString());
        }

        if (estagioPlano.getPlano() != null && estagioPlano.getPlano().getIdJson() != null) {
            estagioPlanoJson.putObject("plano").put("idJson", estagioPlano.getPlano().getIdJson().toString());
        }

        if (estagioPlano.getEstagio() != null && estagioPlano.getEstagio().getIdJson() != null) {
            estagioPlanoJson.putObject("estagio").put("idJson", estagioPlano.getEstagio().getIdJson().toString());
        }

        return estagioPlanoJson;
    }

    private JsonNode getTabelaHorarioJson(TabelaHorario tabelaHorario) {
        ObjectNode tabelaHorarioJson = Json.newObject();
        if (tabelaHorario.getId() != null) {
            tabelaHorarioJson.put("id", tabelaHorario.getId().toString());
        }
        if (tabelaHorario.getIdJson() != null) {
            tabelaHorarioJson.put("idJson", tabelaHorario.getIdJson().toString());
        }
        if (tabelaHorario.getAnel() != null && tabelaHorario.getAnel().getIdJson() != null) {
            tabelaHorarioJson.putObject("anel").put("idJson", tabelaHorario.getAnel().getIdJson().toString());
        }
        refEventos("eventos", tabelaHorario.getEventos(), tabelaHorarioJson);
        return tabelaHorarioJson;
    }

    private JsonNode getEventoJson(Evento evento) {
        ObjectNode eventoJson = Json.newObject();
        if (evento.getId() != null) {
            eventoJson.put("id", evento.getId().toString());
        }
        if (evento.getIdJson() != null) {
            eventoJson.put("idJson", evento.getIdJson().toString());
        }
        if (evento.getNumero() != null) {
            eventoJson.put("numero", evento.getNumero().toString());
        }
        if (evento.getDiaDaSemana() != null) {
            eventoJson.put("diaDaSemana", evento.getDiaDaSemana().toString());
        }
        if (evento.getHorario() != null) {
            eventoJson.put("horario", evento.getHorario().toString());
        }
        if (evento.getPlano() != null && evento.getPlano().getIdJson() != null) {
            eventoJson.putObject("plano").put("idJson", evento.getPlano().getIdJson().toString());
        }
        if (evento.getTabelaHorario() != null && evento.getTabelaHorario().getIdJson() != null) {
            eventoJson.putObject("tabelaHorario").put("idJson", evento.getTabelaHorario().getIdJson().toString());
        }

        return eventoJson;
    }

    private JsonNode getGrupoSemaforicoJson(GrupoSemaforico grupoSemaforico) {
        ObjectNode grupoSemaforicoJson = Json.newObject();

        if (grupoSemaforico.getId() != null) {
            grupoSemaforicoJson.put("id", grupoSemaforico.getId().toString());
        }

        if (grupoSemaforico.getIdJson() != null) {
            grupoSemaforicoJson.put("idJson", grupoSemaforico.getIdJson().toString());
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

        if (grupoSemaforico.getFaseVermelhaApagadaAmareloIntermitente() != null) {
            grupoSemaforicoJson.put("faseVermelhaApagadaAmareloIntermitente", grupoSemaforico.getFaseVermelhaApagadaAmareloIntermitente());
        }

        if (grupoSemaforico.getAnel() != null && grupoSemaforico.getAnel().getIdJson() != null) {
            grupoSemaforicoJson.putObject("anel").put("idJson", grupoSemaforico.getAnel().getIdJson().toString());
        }

        refVerdesConflitantes("verdesConflitantesOrigem", grupoSemaforico.getVerdesConflitantesOrigem(), grupoSemaforicoJson);
        refVerdesConflitantes("verdesConflitantesDestino", grupoSemaforico.getVerdesConflitantesDestino(), grupoSemaforicoJson);
        refEstagiosGruposSemaforicos("estagiosGruposSemaforicos", grupoSemaforico.getEstagiosGruposSemaforicos(), grupoSemaforicoJson);
        refTransicoes("transicoes", grupoSemaforico.getTransicoes(), grupoSemaforicoJson);
        refEntreVerdes("tabelasEntreVerdes", grupoSemaforico.getTabelasEntreVerdes(), grupoSemaforicoJson);

        return grupoSemaforicoJson;
    }

    private JsonNode getEstagioJson(Estagio estagio) {
        ObjectNode estagioJson = Json.newObject();
        if (estagio.getId() != null) {
            estagioJson.put("id", estagio.getId().toString());
        }

        if (estagio.getIdJson() != null) {
            estagioJson.put("idJson", estagio.getIdJson().toString());
        }

        if (estagio.getDescricao() != null) {
            estagioJson.put("descricao", estagio.getDescricao());
        }
        if (estagio.getTempoMaximoPermanencia() != null) {
            estagioJson.put("tempoMaximoPermanencia", estagio.getTempoMaximoPermanencia());
        }
        if (estagio.getTempoMaximoPermanenciaAtivado() != null) {
            estagioJson.put("tempoMaximoPermanenciaAtivado", estagio.getTempoMaximoPermanenciaAtivado());
        }
        if (estagio.getDemandaPrioritaria() != null) {
            estagioJson.put("demandaPrioritaria", estagio.getDemandaPrioritaria());
        }
        if (estagio.getPosicao() != null) {
            estagioJson.put("posicao", estagio.getPosicao());
        }

        if (estagio.getAnel() != null && estagio.getAnel().getIdJson() != null) {
            estagioJson.putObject("anel").put("idJson", estagio.getAnel().getIdJson().toString());
        }

        if (estagio.getImagem() != null) {
            estagioJson.putObject("imagem").put("idJson", estagio.getImagem().getIdJson().toString());
            imagensMap.put(estagio.getImagem().getIdJson().toString(), estagio.getImagem());
        }
        refTransicoesProibidas("origemDeTransicoesProibidas", estagio.getOrigemDeTransicoesProibidas(), estagioJson);
        refTransicoesProibidas("destinoDeTransicoesProibidas", estagio.getDestinoDeTransicoesProibidas(), estagioJson);
        refTransicoesProibidas("alternativaDeTransicoesProibidas", estagio.getAlternativaDeTransicoesProibidas(), estagioJson);
        refEstagiosGruposSemaforicos("estagiosGruposSemaforicos", estagio.getEstagiosGruposSemaforicos(), estagioJson);
        if (estagio.getDetector() != null && estagio.getDetector().getIdJson() != null) {
            estagioJson.putObject("detector").put("idJson", estagio.getDetector().getIdJson().toString());
            detectoresMap.put(estagio.getDetector().getIdJson().toString(), estagio.getDetector());
        }

        return estagioJson;
    }

    private JsonNode getDetectorJson(Detector detector) {
        ObjectNode detectorJson = Json.newObject();

        if (detector.getId() != null) {
            detectorJson.put("id", detector.getId().toString());
        }

        if (detector.getIdJson() != null) {
            detectorJson.put("idJson", detector.getIdJson().toString());
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
        if (detector.getMonitorado() != null) {
            detectorJson.put("monitorado", detector.getMonitorado());
        }
        if (detector.getTempoAusenciaDeteccaoMinima() != null) {
            detectorJson.put("tempoAusenciaDeteccaoMinima", detector.getTempoAusenciaDeteccaoMinima());
        }
        if (detector.getTempoAusenciaDeteccaoMaxima() != null) {
            detectorJson.put("tempoAusenciaDeteccaoMaxima", detector.getTempoAusenciaDeteccaoMaxima());
        }
        if (detector.getTempoDeteccaoPermanenteMinima() != null) {
            detectorJson.put("tempoDeteccaoPermanenteMinima", detector.getTempoDeteccaoPermanenteMinima());
        }
        if (detector.getTempoDeteccaoPermanenteMaxima() != null) {
            detectorJson.put("tempoDeteccaoPermanenteMaxima", detector.getTempoDeteccaoPermanenteMaxima());
        }

        if (detector.getAnel() != null && detector.getAnel().getIdJson() != null) {
            detectorJson.putObject("anel").put("idJson", detector.getAnel().getIdJson().toString());
        }

        if (detector.getEstagio() != null && detector.getEstagio().getIdJson() != null) {
            detectorJson.putObject("estagio").put("idJson", detector.getEstagio().getIdJson().toString());
        }

        return detectorJson;
    }

    private JsonNode getImagemJson(Imagem imagem) {
        ObjectNode imagemJson = Json.newObject();

        if (imagem.getId() != null) {
            imagemJson.put("id", imagem.getId().toString());
        }

        if (imagem.getIdJson() != null) {
            imagemJson.put("idJson", imagem.getIdJson().toString());
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
            objectJson.put("idJson", transicaoProibida.getIdJson().toString());
        }

        if (transicaoProibida.getOrigem() != null && transicaoProibida.getOrigem().getIdJson() != null) {
            objectJson.putObject("origem").put("idJson", transicaoProibida.getOrigem().getIdJson().toString());
        }

        if (transicaoProibida.getDestino() != null && transicaoProibida.getDestino().getIdJson() != null) {
            objectJson.putObject("destino").put("idJson", transicaoProibida.getDestino().getIdJson().toString());
        }

        if (transicaoProibida.getAlternativo() != null && transicaoProibida.getAlternativo().getIdJson() != null) {
            objectJson.putObject("alternativo").put("idJson", transicaoProibida.getAlternativo().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getEstagioGrupoSemaforicoJson(EstagioGrupoSemaforico estagioGrupo) {
        ObjectNode objectJson = Json.newObject();

        if (estagioGrupo.getId() != null) {
            objectJson.put("id", estagioGrupo.getId().toString());
        }

        if (estagioGrupo.getIdJson() != null) {
            objectJson.put("idJson", estagioGrupo.getIdJson().toString());
        }

        if (estagioGrupo.getAtivo() != null) {
            objectJson.put("ativo", estagioGrupo.getAtivo());
        }

        if (estagioGrupo.getEstagio() != null && estagioGrupo.getEstagio().getIdJson() != null) {
            objectJson.putObject("estagio").put("idJson", estagioGrupo.getEstagio().getIdJson().toString());
        }

        if (estagioGrupo.getGrupoSemaforico() != null && estagioGrupo.getGrupoSemaforico().getIdJson() != null) {
            objectJson.putObject("grupoSemaforico").put("idJson", estagioGrupo.getGrupoSemaforico().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getVerdeConflitante(VerdesConflitantes verdesConflitantes) {
        ObjectNode objectJson = Json.newObject();

        if (verdesConflitantes.getId() != null) {
            objectJson.put("id", verdesConflitantes.getId().toString());
        }

        if (verdesConflitantes.getIdJson() != null) {
            objectJson.put("idJson", verdesConflitantes.getIdJson().toString());
        }

        if (verdesConflitantes.getOrigem() != null && verdesConflitantes.getOrigem().getIdJson() != null) {
            objectJson.putObject("origem").put("idJson", verdesConflitantes.getOrigem().getIdJson().toString());
        }

        if (verdesConflitantes.getDestino() != null && verdesConflitantes.getDestino().getIdJson() != null) {
            objectJson.putObject("destino").put("idJson", verdesConflitantes.getDestino().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getTransicao(Transicao transicao) {
        ObjectNode objectJson = Json.newObject();

        if (transicao.getId() != null) {
            objectJson.put("id", transicao.getId().toString());
        }

        if (transicao.getIdJson() != null) {
            objectJson.put("idJson", transicao.getIdJson().toString());
        }

        if (transicao.getOrigem() != null && transicao.getOrigem().getIdJson() != null) {
            objectJson.putObject("origem").put("idJson", transicao.getOrigem().getIdJson().toString());
        }

        if (transicao.getDestino() != null && transicao.getDestino().getIdJson() != null) {
            objectJson.putObject("destino").put("idJson", transicao.getDestino().getIdJson().toString());
        }
        refEntreVerdesTransicoes("tabelaEntreVerdesTransicoes", transicao.getTabelaEntreVerdesTransicoes(), objectJson);

        if (transicao.getGrupoSemaforico() != null && transicao.getGrupoSemaforico().getIdJson() != null) {
            objectJson.putObject("grupoSemaforico").put("idJson", transicao.getGrupoSemaforico().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getTabelaEntreVerde(TabelaEntreVerdes tabelaEntreVerdes) {
        ObjectNode objectJson = Json.newObject();

        if (tabelaEntreVerdes.getId() != null) {
            objectJson.put("id", tabelaEntreVerdes.getId().toString());
        }

        if (tabelaEntreVerdes.getIdJson() != null) {
            objectJson.put("idJson", tabelaEntreVerdes.getIdJson().toString());
        }

        if (tabelaEntreVerdes.getDescricao() != null) {
            objectJson.put("descricao", tabelaEntreVerdes.getDescricao());
        }
        if (tabelaEntreVerdes.getPosicao() != null) {
            objectJson.put("posicao", tabelaEntreVerdes.getPosicao());
        }

        if (tabelaEntreVerdes.getGrupoSemaforico() != null && tabelaEntreVerdes.getGrupoSemaforico().getIdJson() != null) {
            objectJson.putObject("grupoSemaforico").put("idJson", tabelaEntreVerdes.getGrupoSemaforico().getIdJson().toString());
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
            objectJson.put("idJson", tabelaEntreVerdesTransicao.getIdJson().toString());
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
            objectJson.putObject("tabelaEntreVerdes").put("idJson", tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getIdJson().toString());
        }

        if (tabelaEntreVerdesTransicao.getTransicao() != null && tabelaEntreVerdesTransicao.getTransicao().getIdJson() != null) {
            objectJson.putObject("transicao").put("idJson", tabelaEntreVerdesTransicao.getTransicao().getIdJson().toString());
        }

        return objectJson;
    }

    private JsonNode getEnderecoJson(Endereco endereco) {
        ObjectNode enderecoJson = Json.newObject();
        if (endereco.getId() != null) {
            enderecoJson.put("id", endereco.getId().toString());
        }

        if (endereco.getIdJson() != null) {
            enderecoJson.put("idJson", endereco.getIdJson().toString());
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
        return enderecoJson;
    }

    private JsonNode putAnel(Anel anel) {
        ObjectNode anelJson = Json.newObject();
        if (anel.getId() != null) {
            anelJson.put("id", anel.getId().toString());
        }
        if (anel.getIdJson() != null) {
            anelJson.put("idJson", anel.getIdJson().toString());
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
                croquiJson.put("idJson", croqui.getIdJson().toString());
            }
            anelJson.set("croqui", croquiJson);
        }

        if (anel.getTabelaHorario() != null && anel.getTabelaHorario().getIdJson() != null) {
            tabelasHorariosMap.put(anel.getTabelaHorario().getIdJson().toString(), anel.getTabelaHorario());
            anelJson.putObject("tabelaHorario").put("idJson", anel.getTabelaHorario().getIdJson().toString());
        }

        refEstagios(anel.getEstagios(), anelJson);
        refGruposSemaforicos(anel.getGruposSemaforicos(), anelJson);
        refDetectores(anel.getDetectores(), anelJson);
        refPlanos("planos", anel.getPlanos(), anelJson);
        refEnderecos("enderecos", anel.getEnderecos(), anelJson);


        return anelJson;
    }

    private void refEnderecos(String name, List<Endereco> enderecos, ObjectNode parentJson) {
        ArrayNode enderecosJson = Json.newArray();
        for (Endereco endereco : enderecos) {
            if (endereco != null && endereco.getIdJson() != null) {
                enderecosMap.put(endereco.getIdJson().toString(), endereco);
                ObjectNode planoJson = Json.newObject();
                planoJson.put("idJson", endereco.getIdJson().toString());
                enderecosJson.add(planoJson);
            }
        }
        parentJson.set(name, enderecosJson);

    }

    private void refEntreVerdesTransicoes(String name, List<TabelaEntreVerdesTransicao> entreVerdesTransicoes, ObjectNode parentJson) {
        ArrayNode entreVerdesTransicaoJson = Json.newArray();
        for (TabelaEntreVerdesTransicao entreVerdesTransicao : entreVerdesTransicoes) {
            if (entreVerdesTransicao.getIdJson() != null) {
                entreVerdesTransicoesMap.put(entreVerdesTransicao.getIdJson().toString(), entreVerdesTransicao);
                ObjectNode entreVerdeJson = Json.newObject();
                entreVerdeJson.put("idJson", entreVerdesTransicao.getIdJson().toString());
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
                entreVerdeJson.put("idJson", entreVerde.getIdJson().toString());
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
                transicaoJson.put("idJson", transicao.getIdJson().toString());
                transicoesJson.add(transicaoJson);
            }
        }
        parentJson.set(name, transicoesJson);
    }


    private void refVerdesConflitantes(String name, List<VerdesConflitantes> verdesConflitantes, ObjectNode parentJson) {
        ArrayNode verdesConflitantesJson = Json.newArray();
        for (VerdesConflitantes verdeConflitante : verdesConflitantes) {
            if (verdeConflitante != null && verdeConflitante.getIdJson() != null) {
                verdesConflitantesMap.put(verdeConflitante.getIdJson().toString(), verdeConflitante);
                ObjectNode verdeConflitanteJson = Json.newObject();
                verdeConflitanteJson.put("idJson", verdeConflitante.getIdJson().toString());
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
                estagioGrupoSemaforicoJson.put("idJson", estagioGrupoSemaforico.getIdJson().toString());
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
                transicaoProibidaJson.put("idJson", transicaoProibida.getIdJson().toString());
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
                anelJson.put("idJson", anel.getIdJson().toString());
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
                detecttorJson.put("idJson", detector.getIdJson().toString());
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
                grupoSemaforicoJson.put("idJson", grupoSemaforico.getIdJson().toString());
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
                estagioJson.put("idJson", estagio.getIdJson().toString());
                estagiosJson.add(estagioJson);
            }
        }
        parentJson.set("estagios", estagiosJson);

    }

    private void refPlanos(String name, List<Plano> planos, ObjectNode parentJson) {
        ArrayNode planosJson = Json.newArray();
        for (Plano plano : planos) {
            if (plano != null && plano.getIdJson() != null) {
                planosMap.put(plano.getIdJson().toString(), plano);
                ObjectNode planoJson = Json.newObject();
                planoJson.put("idJson", plano.getIdJson().toString());
                planosJson.add(planoJson);
            }
        }
        parentJson.set(name, planosJson);

    }

    private void refEstagiosPlanos(String name, List<EstagioPlano> estagioPlanos, ObjectNode parentJson) {
        ArrayNode estagioPlanosJson = Json.newArray();
        for (EstagioPlano estagioPlano : estagioPlanos) {
            if (estagioPlano != null && estagioPlano.getIdJson() != null) {
                estagioPlanoMap.put(estagioPlano.getIdJson().toString(), estagioPlano);
                ObjectNode estagioJson = Json.newObject();
                estagioJson.put("idJson", estagioPlano.getIdJson().toString());
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
                grupoSemaforicoPlanoJson.put("idJson", grupoSemaforicoPlano.getIdJson().toString());
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
                eventoJson.put("idJson", evento.getIdJson().toString());
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
                areaJson.put("idJson", area.getIdJson().toString());
                areasJson.add(areaJson);
            }
        }
        parentJson.set(name, areasJson);

    }


}
