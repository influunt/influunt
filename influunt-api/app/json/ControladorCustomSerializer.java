package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import org.apache.commons.lang3.ObjectUtils;
import play.libs.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 7/29/16.
 */
public class ControladorCustomSerializer {

    private Map<String,Estagio> estagiosMap = new HashMap<String, Estagio>();
    private Map<String,GrupoSemaforico> gruposSemaforicosMap = new HashMap<String, GrupoSemaforico>();
    private Map<String,Detector> detectoresMap = new HashMap<String, Detector>();
    private Map<String,Imagem> imagensMap = new HashMap<String, Imagem>();
    private Map<String,TransicaoProibida> transicoesProibidasMap = new HashMap<String, TransicaoProibida>();
    private Map<String,EstagioGrupoSemaforico> estagiosGruposSemaforicosMap = new HashMap<String, EstagioGrupoSemaforico>();
    private Map<String,VerdesConflitantes> verdesConflitantesMap = new HashMap<String, VerdesConflitantes>();
    private Map<String,Transicao> transicoesMap = new HashMap<String, Transicao>();
    private Map<String,TabelaEntreVerdes> entreVerdesMap = new HashMap<String, TabelaEntreVerdes>();
    private Map<String,TabelaEntreVerdesTransicao> entreVerdesTransicoesMap = new HashMap<String, TabelaEntreVerdesTransicao>();

    public JsonNode getControladorJson(Controlador controlador){
        ObjectNode root = Json.newObject();

        putControladorPrimitivos(controlador, root);
        putControladorArea(controlador.getArea(), root);
        putControladorModelo(controlador.getModelo(),root);
        putControladorAneis(controlador.getAneis(),root);
        putControladorEstagios(root);
        putControladorGruposSemaforicos(root);
        putControladorDetector(root);
        //Planos???

        putControladorTransicoesProibidas(root);
        putControladorEstagiosGruposSemaforicos(root);
        putControladorVerdesConflitantes(root);
        putControladorTransicoes(root);
        putControladorTabelaEntreVerdes(root);
        putControladorTabelaEntreVerdesTransicoes(root);

        putControladorImagens(root);

        return root;
    }
    private void putControladorPrimitivos(Controlador controlador, ObjectNode root) {
        if (controlador.getId() != null) {
            root.put("id", controlador.getId().toString());
        }
        if (controlador.getLocalizacao() != null) {
            root.put("localizacao", controlador.getLocalizacao());
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
        if (controlador.getLatitude() != null) {
            root.put("latitude", controlador.getLatitude());
        }
        if (controlador.getLongitude() != null) {
            root.put("longitude", controlador.getLongitude());
        }
        if (controlador.getDataCriacao() != null) {
            root.put("dataCriacao", InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));
        }
        if (controlador.getDataAtualizacao() != null) {
            root.put("dataAtualizacao", InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));
        }
        if (controlador.getCLC() != null) {
            root.put("CLC", controlador.getCLC());
        }
        if (controlador.getStatusControlador() != null) {
            root.put("statusControlador", controlador.getStatusControlador().toString());
        }
    }

    private void putControladorArea(Area area, ObjectNode root) {
        ObjectNode areaJson = Json.newObject();
        if (area.getId() != null) {
            areaJson.put("id", area.getId().toString());
        }else{
            areaJson.putNull("id");
        }
        areaJson.put("descricao", area.getDescricao());
        Cidade cidade = area.getCidade();
        if (cidade != null) {
            ObjectNode cidadeJson = Json.newObject();
            if(cidade.getId() != null) {
                cidadeJson.put("id", cidade.getId().toString());
            }else{
                cidadeJson.putNull("id");
            }
            cidadeJson.put("nome",cidade.getNome());
            areaJson.set("cidade",cidadeJson);
        }
        root.set("area",areaJson);
    }

    private void putControladorModelo(ModeloControlador modeloControlador, ObjectNode root) {
        ObjectNode modeloJson = Json.newObject();
        if (modeloControlador.getId() == null) {
            modeloJson.putNull("id");
        } else {
            modeloJson.put("id", modeloControlador.getId().toString());
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
        }
        ConfiguracaoControlador configuracaoControlador = modeloControlador.getConfiguracao();
        if (configuracaoControlador != null) {
            ObjectNode configuracaoJson = Json.newObject();

            configuracaoJson.put("descricao",configuracaoControlador.getDescricao());
            configuracaoJson.put("limiteEstagio",configuracaoControlador.getLimiteEstagio());
            configuracaoJson.put("limiteGrupoSemaforico",configuracaoControlador.getLimiteGrupoSemaforico());
            configuracaoJson.put("limiteAnel",configuracaoControlador.getLimiteAnel());
            configuracaoJson.put("limiteDetectorPedestre",configuracaoControlador.getLimiteDetectorPedestre());
            configuracaoJson.put("limiteDetectorVeicular",configuracaoControlador.getLimiteDetectorVeicular());
            configuracaoJson.put("limiteDetectorVeicular",configuracaoControlador.getLimiteDetectorVeicular());

            modeloJson.set("configuracao",configuracaoJson);
        }
        root.set("modelo",modeloJson);
    }

    private void putControladorAneis(List<Anel> aneis, ObjectNode root) {
        ArrayNode aneisJson = Json.newArray();
        for(Anel anel : aneis){
            aneisJson.add(putAnel(anel));
        }
        root.set("aneis",aneisJson);
    }

    private void putControladorEstagios(ObjectNode root) {
        ArrayNode estagiosJson = Json.newArray();
        estagiosMap.values().stream().forEach(estagio -> {
            estagiosJson.add(getEstagioJson(estagio));
        });
        root.set("estagios",estagiosJson);

    }

    private void putControladorGruposSemaforicos(ObjectNode root) {
        ArrayNode grupoSemaforicoJson = Json.newArray();
        gruposSemaforicosMap.values().stream().forEach(grupoSemaforico -> {
            grupoSemaforicoJson.add(getGrupoSemaforicoJson(grupoSemaforico));
        });
        root.set("gruposSemaforicos",grupoSemaforicoJson);

    }

    private void putControladorDetector(ObjectNode root) {
        ArrayNode detectoresJson = Json.newArray();
        detectoresMap.values().stream().forEach(detector -> {
            detectoresJson.add(getDetectorJson(detector));
        });
        root.set("detectores",detectoresJson);

    }

    private void putControladorTransicoesProibidas(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        transicoesProibidasMap.values().stream().forEach(transicaoProibida -> {
            arrayJson.add(getTransicaoProibidaJson(transicaoProibida));
        });
        root.set("transicoesProibidas",arrayJson);

    }

    private void putControladorEstagiosGruposSemaforicos(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        estagiosGruposSemaforicosMap.values().stream().forEach(estagioGrupoSemaforico -> {
            arrayJson.add(getEstagioGrupoSemaforicoJson(estagioGrupoSemaforico));
        });
        root.set("estagiosGruposSemaforicos",arrayJson);

    }

    private void putControladorVerdesConflitantes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        verdesConflitantesMap.values().stream().forEach(verdesConflitantes -> {
            arrayJson.add(getVerdeConflitante(verdesConflitantes));
        });
        root.set("verdesConflitantes",arrayJson);

    }

    private void putControladorTransicoes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        transicoesMap.values().stream().forEach(transicao -> {
            arrayJson.add(getTransicao(transicao));
        });
        root.set("transicoes",arrayJson);

    }

    private void putControladorImagens(ObjectNode root) {
        ArrayNode imagensJson = Json.newArray();
        imagensMap.values().stream().forEach(imagem -> {
            imagensJson.add(getImagemJson(imagem));
        });
        root.set("imagens",imagensJson);

    }

    private void putControladorTabelaEntreVerdes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        entreVerdesMap.values().stream().forEach(entreVerdes -> {
            arrayJson.add(getTabelaEntreVerde(entreVerdes));
        });
        root.set("tabelasEntreVerdes",arrayJson);

    }

    private void putControladorTabelaEntreVerdesTransicoes(ObjectNode root) {
        ArrayNode arrayJson = Json.newArray();
        entreVerdesTransicoesMap.values().stream().forEach(entreVerdes -> {
            arrayJson.add(getTabelaEntreVerdeTransicoes(entreVerdes));
        });
        root.set("tabelasEntreVerdesTransicoes",arrayJson);

    }


    private JsonNode getGrupoSemaforicoJson(GrupoSemaforico grupoSemaforico) {
        ObjectNode grupoSemaforicoJson = Json.newObject();

        if(grupoSemaforico.getId()!=null) {
            grupoSemaforicoJson.put("id",grupoSemaforico.getId().toString());
        }else{
            grupoSemaforicoJson.putNull("id");
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

        if (grupoSemaforico.getAnel() != null && grupoSemaforico.getAnel().getId() != null) {
            grupoSemaforicoJson.putObject("anel").put("id",grupoSemaforico.getAnel().toString());
        }

        refVerdesConflitantes("verdesConflitantesOrigem",grupoSemaforico.getVerdesConflitantesOrigem(),grupoSemaforicoJson);
        refVerdesConflitantes("verdesConflitantesDestino",grupoSemaforico.getVerdesConflitantesOrigem(),grupoSemaforicoJson);
        refEstagiosGruposSemaforicos("estagioGrupoSemaforicos",grupoSemaforico.getEstagioGrupoSemaforicos(),grupoSemaforicoJson);
        refTransicoes("transicoes",grupoSemaforico.getTransicoes(),grupoSemaforicoJson);
        refEntreVerdes("tabelasEntreVerdes",grupoSemaforico.getTabelasEntreVerdes(),grupoSemaforicoJson);

        return grupoSemaforicoJson;
    }

    private JsonNode getEstagioJson(Estagio estagio) {
        ObjectNode estagioJson = Json.newObject();
        if(estagio.getId()!=null) {
            estagioJson.put("id",estagio.getId().toString());
        }else{
            estagioJson.putNull("id");
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
        if (estagio.getImagem() != null) {
            estagioJson.putObject("imagem").put("id",estagio.getImagem().getId().toString());
            imagensMap.put(estagio.getImagem().getId().toString(),estagio.getImagem());
        }
        refTransicoesProibidas("origemDeTransicoesProibidas", estagio.getOrigemDeTransicoesProibidas(),estagioJson);
        refTransicoesProibidas("destinoDeTransicoesProibidas", estagio.getDestinoDeTransicoesProibidas(),estagioJson);
        refTransicoesProibidas("alternativaDeTransicoesProibidas", estagio.getAlternativaDeTransicoesProibidas(),estagioJson);
        refEstagiosGruposSemaforicos("estagiosGruposSemaforicos", estagio.getEstagiosGruposSemaforicos(),estagioJson);
        if (estagio.getDetector() != null && estagio.getDetector().getId() != null) {
            estagioJson.putObject("detector").put("id",estagio.getDetector().getId().toString());
            detectoresMap.put(estagio.getDetector().getId().toString(),estagio.getDetector());
        }

        return estagioJson;
    }

    private JsonNode getDetectorJson(Detector detector) {
        ObjectNode detectorJson = Json.newObject();

        if (detector.getId() != null) {
            detectorJson.put("id", detector.getId().toString());
        }else{
            detectorJson.putNull("id");
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

        if (detector.getAnel() != null && detector.getAnel().getId() != null) {
            detectorJson.putObject("anel").put("id",detector.getAnel().toString());
        }

        if (detector.getEstagio() != null && detector.getEstagio().getId() != null) {
            detectorJson.putObject("estagio").put("id",detector.getEstagio().toString());
        }

        return detectorJson;
    }

    private JsonNode getImagemJson(Imagem imagem) {
        ObjectNode imagemJson = Json.newObject();

        if (imagem.getId() != null) {
            imagemJson.put("id", imagem.getId().toString());
        }else{
            imagemJson.putNull("id");
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

        if (transicaoProibida.getId() == null) {
            objectJson.putNull("id");
        } else {
            objectJson.put("id", transicaoProibida.getId().toString());
        }

        if (transicaoProibida.getOrigem() != null && transicaoProibida.getOrigem().getId() != null) {
            objectJson.putObject("origem").put("id",transicaoProibida.getOrigem().getId().toString());
        }

        if (transicaoProibida.getDestino() != null && transicaoProibida.getDestino().getId() != null) {
            objectJson.putObject("destino").put("id",transicaoProibida.getDestino().getId().toString());
        }

        if (transicaoProibida.getAlternativo() != null && transicaoProibida.getAlternativo().getId() != null) {
            objectJson.putObject("alternativo").put("id",transicaoProibida.getAlternativo().getId().toString());
        }

        return objectJson;
    }

    private JsonNode getEstagioGrupoSemaforicoJson(EstagioGrupoSemaforico estagioGrupo) {
        ObjectNode objectJson = Json.newObject();

        if (estagioGrupo.getId() != null) {
            objectJson.put("id", estagioGrupo.getId().toString());
        }else{
            objectJson.putNull("id");
        }

        if (estagioGrupo.getAtivo() != null) {
            objectJson.put("ativo", estagioGrupo.getAtivo());
        }

        if (estagioGrupo.getEstagio() != null && estagioGrupo.getEstagio().getId() != null) {
            objectJson.putObject("estagio").put("id",estagioGrupo.getEstagio().getId().toString());
        }

        if (estagioGrupo.getGrupoSemaforico() != null && estagioGrupo.getGrupoSemaforico().getId() != null) {
            objectJson.putObject("grupoSemaforico").put("id",estagioGrupo.getGrupoSemaforico().getId().toString());
        }

        return objectJson;
    }

    private JsonNode getVerdeConflitante(VerdesConflitantes verdesConflitantes) {
        ObjectNode objectJson = Json.newObject();

        if (verdesConflitantes.getId() != null) {
            objectJson.put("id", verdesConflitantes.getId().toString());
        }else{
            objectJson.putNull("id");
        }

        if (verdesConflitantes.getOrigem() != null && verdesConflitantes.getOrigem().getId() != null) {
            objectJson.putObject("origem").put("id",verdesConflitantes.getOrigem().getId().toString());
        }

        if (verdesConflitantes.getDestino() != null && verdesConflitantes.getDestino().getId() != null) {
            objectJson.putObject("destino").put("id",verdesConflitantes.getDestino().getId().toString());
        }

        return objectJson;
    }

    private JsonNode getTransicao(Transicao transicao) {
        ObjectNode objectJson = Json.newObject();

        if (transicao.getId() != null) {
            objectJson.put("id", transicao.getId().toString());
        }else{
            objectJson.putNull("id");
        }

        if (transicao.getOrigem() != null && transicao.getOrigem().getId() != null) {
            objectJson.putObject("origem").put("id",transicao.getOrigem().getId().toString());
        }

        if (transicao.getDestino() != null && transicao.getDestino().getId() != null) {
            objectJson.putObject("destino").put("id",transicao.getDestino().getId().toString());
        }
        refEntreVerdesTransicoes("tabelaEntreVerdesTransicoes",transicao.getTabelaEntreVerdesTransicoes(),objectJson);

        if (transicao.getGrupoSemaforico() != null && transicao.getGrupoSemaforico().getId() != null) {
            objectJson.putObject("grupoSemaforico").put("id",transicao.getGrupoSemaforico().getId().toString());
        }

        return objectJson;
    }

    private JsonNode getTabelaEntreVerde(TabelaEntreVerdes tabelaEntreVerdes) {
        ObjectNode objectJson = Json.newObject();

        if (tabelaEntreVerdes.getId() != null) {
            objectJson.put("id", tabelaEntreVerdes.getId().toString());
        }else{
            objectJson.putNull("id");
        }

        if (tabelaEntreVerdes.getDescricao() != null) {
            objectJson.put("descricao", tabelaEntreVerdes.getDescricao());
        }
        if (tabelaEntreVerdes.getPosicao() != null) {
            objectJson.put("posicao", tabelaEntreVerdes.getPosicao());
        }

        if (tabelaEntreVerdes.getGrupoSemaforico() != null && tabelaEntreVerdes.getGrupoSemaforico().getId() != null) {
            objectJson.putObject("grupoSemaforico").put("id",tabelaEntreVerdes.getGrupoSemaforico().getId().toString());
        }

        refEntreVerdesTransicoes("tabelaEntreVerdesTransicoes",tabelaEntreVerdes.getTabelaEntreVerdesTransicoes(),objectJson);

        return objectJson;
    }

    private JsonNode getTabelaEntreVerdeTransicoes(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        ObjectNode objectJson = Json.newObject();


        if (tabelaEntreVerdesTransicao.getId() != null) {
            objectJson.put("id", tabelaEntreVerdesTransicao.getId().toString());
        }else{
            objectJson.putNull("id");
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

        if (tabelaEntreVerdesTransicao.getTabelaEntreVerdes() != null && tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getId() != null) {
            objectJson.putObject("tabelaEntreVerdes").put("id",tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getId().toString());
        }

        if (tabelaEntreVerdesTransicao.getTransicao() != null && tabelaEntreVerdesTransicao.getTransicao().getId() != null) {
            objectJson.putObject("transicao").put("id",tabelaEntreVerdesTransicao.getTransicao().getId().toString());
        }

        return objectJson;
    }

    private JsonNode putAnel(Anel anel) {
        ObjectNode anelJson = Json.newObject();
        if (anel.getId() != null) {
            anelJson.put("id", anel.getId().toString());
        }else{
            anelJson.putNull("id");
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

        refEstagios(anel.getEstagios(),anelJson);
        refGruposSemaforicos(anel.getGruposSemaforicos(),anelJson);
        refDetectores(anel.getDetectores(),anelJson);

        return anelJson;
    }

    private void refEntreVerdesTransicoes(String name,List<TabelaEntreVerdesTransicao> entreVerdesTransicoes, ObjectNode parentJson) {
        ArrayNode entreVerdesTransicaoJson = Json.newArray();
        for(TabelaEntreVerdesTransicao entreVerdesTransicao : entreVerdesTransicoes){
            if(entreVerdesTransicao.getId()!=null) {
                entreVerdesTransicoesMap.put(entreVerdesTransicao.getId().toString(),entreVerdesTransicao);
                ObjectNode entreVerdeJson = Json.newObject();
                entreVerdeJson.put("id", entreVerdesTransicao.getId().toString());
                entreVerdesTransicaoJson.add(entreVerdeJson);
            }
        }
        parentJson.set(name,entreVerdesTransicaoJson);
    }

    private void refEntreVerdes(String name,List<TabelaEntreVerdes> entreVerdes, ObjectNode parentJson) {
        ArrayNode entreVerdesJson = Json.newArray();
        for(TabelaEntreVerdes entreVerde : entreVerdes){
            if(entreVerde.getId()!=null) {
                entreVerdesMap.put(entreVerde.getId().toString(),entreVerde);
                ObjectNode entreVerdeJson = Json.newObject();
                entreVerdeJson.put("id", entreVerde.getId().toString());
                entreVerdesJson.add(entreVerdeJson);
            }
        }
        parentJson.set(name,entreVerdesJson);
    }

    private void refTransicoes(String name,List<Transicao> transicoes, ObjectNode parentJson) {
        ArrayNode transicoesJson = Json.newArray();
        for(Transicao transicao : transicoes){
            if(transicao.getId()!=null) {
                transicoesMap.put(transicao.getId().toString(),transicao);
                ObjectNode transicaoJson = Json.newObject();
                transicaoJson.put("id", transicao.getId().toString());
                transicoesJson.add(transicaoJson);
            }
        }
        parentJson.set(name,transicoesJson);
    }


    private void refVerdesConflitantes(String name,List<VerdesConflitantes> verdesConflitantes, ObjectNode parentJson) {
        ArrayNode verdesConflitantesJson = Json.newArray();
        for(VerdesConflitantes verdeConflitante : verdesConflitantes){
            if(verdeConflitante.getId()!=null) {
                verdesConflitantesMap.put(verdeConflitante.getId().toString(),verdeConflitante);
                ObjectNode verdeConflitanteJson = Json.newObject();
                verdeConflitanteJson.put("id", verdeConflitante.getId().toString());
                verdesConflitantesJson.add(verdeConflitanteJson);
            }
        }
        parentJson.set(name,verdesConflitantesJson);
    }

    private void refEstagiosGruposSemaforicos(String name,List<EstagioGrupoSemaforico> estagioGrupoSemaforicos, ObjectNode parentJson) {
        ArrayNode estagiosGruposSemaforicosJson = Json.newArray();
        for(EstagioGrupoSemaforico estagioGrupoSemaforico : estagioGrupoSemaforicos){
            if(estagioGrupoSemaforico != null  && estagioGrupoSemaforico.getId()!=null) {
                estagiosGruposSemaforicosMap.put(estagioGrupoSemaforico.getId().toString(),estagioGrupoSemaforico);
                ObjectNode estagioGrupoSemaforicoJson = Json.newObject();
                estagioGrupoSemaforicoJson.put("id", estagioGrupoSemaforico.getId().toString());
                estagiosGruposSemaforicosJson.add(estagioGrupoSemaforicoJson);
            }
        }
        parentJson.set(name,estagiosGruposSemaforicosJson);

    }

    private void refTransicoesProibidas(String name,List<TransicaoProibida> transicaoProibidas, ObjectNode parentJson) {
        ArrayNode transicoesProibidasJson = Json.newArray();
        for(TransicaoProibida transicaoProibida : transicaoProibidas){
            if(transicaoProibida != null && transicaoProibida.getId()!=null) {
                transicoesProibidasMap.put(transicaoProibida.getId().toString(),transicaoProibida);
                ObjectNode transicaoProibidaJson = Json.newObject();
                transicaoProibidaJson.put("id", transicaoProibida.getId().toString());
                transicoesProibidasJson.add(transicaoProibidaJson);
            }
        }
        parentJson.set(name,transicoesProibidasJson);

    }

    private void refAneis(String name,List<Anel> aneis, ObjectNode parentJson) {
        ArrayNode aneisJson = Json.newArray();
        for(Anel anel : aneis){
            if(anel.getId() != null ) {
                ObjectNode anelJson = Json.newObject();
                anelJson.put("id", anel.getId().toString());
                aneisJson.add(anelJson);
            }
        }
        parentJson.set(name,aneisJson);
    }

    private void refDetectores(List<Detector> detectores, ObjectNode parentJson) {
        ArrayNode detectoresJson = Json.newArray();
        for(Detector detector : detectores){
            if(detector.getId()!=null) {
                detectoresMap.put(detector.getId().toString(),detector);
                ObjectNode detecttorJson = Json.newObject();
                detecttorJson.put("id", detector.getId().toString());
                detectoresJson.add(detecttorJson);
            }
        }
        parentJson.set("detectores",detectoresJson);

    }

    private void refGruposSemaforicos(List<GrupoSemaforico> grupoSemaforicos, ObjectNode parentJson) {
        ArrayNode gruposSemaforicosJson = Json.newArray();
        for(GrupoSemaforico grupoSemaforico : grupoSemaforicos){
            if(grupoSemaforico != null && grupoSemaforico.getId() != null) {
                gruposSemaforicosMap.put(grupoSemaforico.getId().toString(),grupoSemaforico);
                ObjectNode grupoSemaforicoJson = Json.newObject();
                grupoSemaforicoJson.put("id", grupoSemaforico.getId().toString());
                gruposSemaforicosJson.add(grupoSemaforicoJson);
            }
        }
        parentJson.set("gruposSemaforicos",gruposSemaforicosJson);

    }

    private void refEstagios(List<Estagio> estagios, ObjectNode parentJson) {
        ArrayNode estagiosJson = Json.newArray();
        for(Estagio estagio : estagios){
            if(estagio != null && estagio.getId()!=null) {
                estagiosMap.put(estagio.getId().toString(),estagio);
                ObjectNode estagioJson = Json.newObject();
                estagioJson.put("id", estagio.getId().toString());
                estagiosJson.add(estagioJson);
            }
        }
        parentJson.set("estagios",estagiosJson);

    }


}
