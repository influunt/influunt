package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Anel;
import models.ControladorFisico;
import models.StatusAnel;
import models.Usuario;
import play.libs.Json;
import status.StatusConexaoControlador;
import status.StatusControladorFisico;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pedropires on 1/12/17.
 */
public class ImposicaoHelper {

    public static JsonNode getControladoresForImposicao(Map<String, String[]> params, Usuario usuario) {
        if (!usuario.isRoot() && !usuario.podeAcessarTodasAreas() && usuario.getArea() == null) {
            return null;
        }

        // somente controladores sincronizados
        params.put("controlador_sincronizado_id_ne", new String[]{null});

        String[] statusParams = getParamsForStatus(params);
        if (statusParams != null) {
            params.put("id_in", statusParams);
        }

        // Dado que seja um usuário sob uma área.
        if (!usuario.isRoot() && !usuario.podeAcessarTodasAreas()) {
            String[] areaId = {usuario.getArea().getId().toString()};
            params.remove("area.descricao");
            params.put("controladorSincronizado.area.id", areaId);
        }

        final String nomeEndereco = params.containsKey("nomeDoEndereco") ? params.remove("nomeDoEndereco")[0] : null;
        final String nomeEnderecoEq = params.containsKey("nomeDoEndereco_eq") ? params.remove("nomeDoEndereco_eq")[0] : null;

        List<ControladorFisico> controladoresFisicos = getControladoresFisicos(params);
        List<String> aneisIds = controladoresFisicos.stream()
            .flatMap(cf -> cf.getControladorSincronizado().getAneis().stream())
            .filter(Anel::isAtivo)
            .map(anel -> anel.getId().toString())
            .collect(Collectors.toList());

        List<Anel> aneis = new ArrayList<>();
        if (!aneisIds.isEmpty()) {
            aneis = Anel.find.select("id, descricao, posicao, endereco").fetch("controlador.subarea").where().in("id", aneisIds).findList();
        }

        ArrayNode aneisJson = JsonNodeFactory.instance.arrayNode();
        aneis.forEach(anel -> {
            if (anel.isAtivo() && (aneisIds.isEmpty() || aneisIds.contains(anel.getId().toString()))) {
                if ((nomeEndereco == null || anel.getEndereco().nomeEndereco().toLowerCase().contains(nomeEndereco.toLowerCase())) &&
                    (nomeEnderecoEq == null || anel.getEndereco().nomeEndereco().toLowerCase().equals(nomeEnderecoEq.toLowerCase()))) {
                    aneisJson.add(getAnelJson(anel));
                }
            }
        });

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("data", aneisJson);
        return retorno;
    }

    private static Set<String> getIdsForStatusAneis(Map<String, String[]> params) {
        StatusAnel status = StatusAnel.valueOf(params.remove("status_eq")[0]);
        Map<String, Map> statusHash = StatusControladorFisico.getControladoresByStatusAnel(status);
        return statusHash.keySet();
    }

    private static Set<String> getIdsForStatusControlador(Map<String, String[]> params) {
        boolean online = Boolean.parseBoolean(params.remove("online_eq")[0]);
        List<StatusConexaoControlador> statuses = StatusConexaoControlador.ultimoStatusPorSituacao(online);
        return statuses.stream().map(status -> status.getIdControlador()).collect(Collectors.toSet());
    }

    private static String[] getParamsForStatus(Map<String, String[]> params) {
        Set<String> ids = null;
        if (params.containsKey("status_eq") && params.containsKey("online_eq")) {
            ids = getIdsForStatusAneis(params);
            Set<String> idsStatusControladores = getIdsForStatusControlador(params);
            ids.retainAll(idsStatusControladores);
        } else if (params.containsKey("status_eq")) {
            ids = getIdsForStatusAneis(params);
        } else if (params.containsKey("online_eq")) {
            ids = getIdsForStatusControlador(params);
        }
        return ids != null ? new String[]{"[" + String.join(",", ids) + "]"} : null;
    }

    private static List<ControladorFisico> getControladoresFisicos(Map<String, String[]> params) {
        List<ControladorFisico> controladoresFisicos = null;
        if (params.containsKey("filtrarPor_eq")) {
            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                params.remove("filtrarPor_eq");
                if (params.containsKey("subareaAgrupamento")) {
                    params.put("controladorSincronizado.subarea.nome", params.get("subareaAgrupamento"));
                    params.remove("subareaAgrupamento");
                    controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado", "controladorSincronizado.area", "controladorSincronizado.subarea", "controladorSincronizado.aneis")).query().getResult();
                }
            } else if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                params.remove("filtrarPor_eq");
                if (params.containsKey("subareaAgrupamento")) {
                    params.put("controladorSincronizado.aneis.agrupamentos.nome", new String[]{params.get("subareaAgrupamento")[0]});
                    params.remove("subareaAgrupamento");
                    controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado.aneis", "controladorSincronizado.aneis.agrupamentos", "controladorSincronizado.aneis.endereco")).query().getResult();
                }
            }
        }
        if (controladoresFisicos == null) {
            controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado.aneis")).query().getResult();
        }
        return controladoresFisicos;
    }

    private static JsonNode getAnelJson(Anel anel) {
        ObjectNode controlador = JsonNodeFactory.instance.objectNode();
        controlador.put("id", anel.getControlador().getControladorFisicoId());
        ObjectNode anelNode = Json.newObject()
            .put("id", anel.getId().toString())
            .put("CLA", anel.getCLA())
            .put("posicao", anel.getPosicao())
            .put("endereco", anel.getEndereco().nomeEndereco())
            .putPOJO("controlador", controlador)
            .put("fabricanteOS", anel.getControlador().getFabricanteOs())
            .put("modeloOS", anel.getControlador().getModeloOs())
            .put("versaoOS", anel.getControlador().getVersaoOs())
            .put("fabricanteHardware", anel.getControlador().getFabricanteHardware())
            .put("modeloHardware", anel.getControlador().getModeloHardware())
            .put("versaoHardware", anel.getControlador().getVersaoHardware())
            .put("controladorFisicoId", anel.getControlador().getControladorFisicoId())
            .put("controladorId", anel.getControlador().getId().toString())
            .put("status", anel.getControlador().getStatusControladorReal().toString())
            .put("online", anel.getControlador().isOnline());
        if (anel.getControlador().getAtualizacaoVersao() != null) {
            anelNode.put("atualizacaoVersao", anel.getControlador().getAtualizacaoVersao().toString());
        } else {
            anelNode.put("atualizacaoVersao", "");
        }
        return anelNode;
    }
}
