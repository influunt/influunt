package reports;

import com.avaje.ebean.ExpressionList;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import json.serializers.InfluuntDateTimeSerializer;
import models.*;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import status.AlarmesFalhasControlador;
import status.LogControlador;
import status.TrocaDePlanoControlador;
import utils.InfluuntQueryBuilder;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 10/10/16.
 */

//TODO: Verificar se pode ser excluida essa clase
public class ControladoresReportService extends ReportService<Controlador> {

    @Inject
    private BaseJasperReport baseJasperReport;

    private String ENDERECO = "endereco";

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Controlador> lista, ReportType reportType) {
        throw new NotImplementedException("Metodo nao implentado. Favor utilizar outro metodo gerador.");
    }

    public ObjectNode getControladoresStatusReportData(Map<String, String[]> params, Area area) {
        List<String> aneisIds = new ArrayList<>();

        Map<String, String[]> paramsAux = new HashMap<>();
        paramsAux.putAll(params);
        paramsAux.remove("tipoRelatorio");

        int page = 0;
        int perPage = 30;
        if (paramsAux.containsKey("page")) {
            page = Integer.parseInt(paramsAux.get("page")[0]);
            paramsAux.remove("page");
        }
        if (paramsAux.containsKey("per_page")) {
            perPage = Integer.parseInt(paramsAux.get("per_page")[0]);
            paramsAux.remove("per_page");
        }

        if (params.containsKey("filtrarPor_eq")) {
            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("subarea.nome", params.get("subareaAgrupamento"));
                    paramsAux.remove("subareaAgrupamento");
                    paramsAux.remove("filtrarPor_eq");
                }

                List<Controlador> controladores = (List<Controlador>) new InfluuntQueryBuilder(Controlador.class, paramsAux).fetch(Arrays.asList("subarea", "aneis")).query().getResult();
                controladores.stream().forEach(c -> c.getAneis().forEach(a -> aneisIds.add(a.getId().toString())));
            } else if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("agrupamentos.nome", new String[]{params.get("subareaAgrupamento")[0]});
                    paramsAux.remove("subareaAgrupamento");
                    paramsAux.remove("filtrarPor_eq");
                }

                List<Anel> aneis = (List<Anel>) new InfluuntQueryBuilder(Anel.class, paramsAux).fetch(Arrays.asList("agrupamentos")).query().getResult();
                aneis.stream().forEach(a -> aneisIds.add(a.getId().toString()));
            }
        }

        List<TrocaDePlanoControlador> trocas = TrocaDePlanoControlador.ultimasTrocasDePlanosDosControladores();
        ExpressionList<Anel> query = Anel.find
            .select("id, descricao, posicao, endereco")
            .fetch("controlador.subarea")
            .where().in("id", trocas.stream().map(TrocaDePlanoControlador::getIdAnel).collect(Collectors.toList()));

        List<Anel> aneis;
        if (area != null) {
            aneis = query.where().eq("controlador.area", area).findList();
        } else {
            aneis = query.findList();
        }

        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        aneis.forEach(anel -> {
            if (aneisIds.isEmpty() || aneisIds.contains(anel.getId().toString())) {
                TrocaDePlanoControlador troca = trocas.stream().filter(t -> t.getIdAnel().equals(anel.getId().toString())).findFirst().get();
                final String estado;
                final String descricaoPlano;
                if (troca.getConteudo().get("impostoPorFalha").booleanValue()) {
                    estado = "Em falha";
                    descricaoPlano = "";
                } else if (troca.getConteudo().get("imposicaoDePlano").booleanValue()) {
                    estado = "Sob Imposição de Plano";
                    descricaoPlano = troca.getConteudo().get("plano").get("descricao").asText();
                } else if (troca.getConteudo().get("plano").get("modoOperacao").asText().equalsIgnoreCase("MANUAL")) {
                    estado = "Em Operação Manual";
                    descricaoPlano = troca.getConteudo().get("plano").get("descricao").asText();
                } else {
                    estado = "Sob plano vigente";
                    descricaoPlano = troca.getConteudo().get("plano").get("descricao").asText();
                }
                itens.addObject()
                    .put("cla", anel.getCLA())
                    .put(ENDERECO, anel.getEndereco().nomeEndereco())
                    .put("estado", estado)
                    .put("plano", descricaoPlano)
                    .put("modo", troca.getConteudo().get("plano").get("modoOperacao").asText());
            }
        });

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        ArrayNode data = retorno.putArray("data");
        int skip = page * perPage;
        for (int i = skip; i < skip + perPage; i++) {
            if (i < itens.size()) {
                data.add(itens.get(i));
            } else {
                break;
            }
        }
        retorno.put("total", itens.size());
        return retorno;
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por {@link StatusDevice}
     * <p>
     * FORMATO: CLA | ENDERECO | ESTADO | PLANO | MODO
     *
     * @return {@link InputStream} do csv
     */
    public InputStream generateControladoresStatusCSVReport(Map<String, String[]> params, Area area) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Status").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("CLA").append(COMMA_DELIMITER).append("ENDEREÇO").append(COMMA_DELIMITER)
            .append("ESTADO").append(COMMA_DELIMITER).append("PLANO").append(COMMA_DELIMITER)
            .append("MODO").append(NEW_LINE_SEPARATOR);

        ObjectNode retorno = getControladoresStatusReportData(params, area);
        retorno.get("data").forEach(jsonNode -> {
            buffer.append(StringUtils.defaultIfBlank(jsonNode.get("cla").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get(ENDERECO).asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("estado").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("plano").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("modo").asText(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        });

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

    public ObjectNode getControladoresFalhasReportData(Map<String, String[]> params, Area area) {
        Map<String, String[]> paramsAux = new HashMap<>();
        paramsAux.putAll(params);
        paramsAux.remove("tipoRelatorio");

        if (params.containsKey("filtrarPor_eq")) {
            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("subarea.nome", params.get("subareaAgrupamento"));
                }
            } else if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("aneis.agrupamentos.nome", params.get("subareaAgrupamento"));
                }
            }

            paramsAux.remove("subareaAgrupamento");
            paramsAux.remove("filtrarPor_eq");
        }

        String tipoFalhaQuery = null;
        if (paramsAux.containsKey("tipoFalha")) {
            tipoFalhaQuery = paramsAux.get("tipoFalha")[0];
            paramsAux.remove("tipoFalha");
        }

        if (area != null) {
            String[] areaId = {area.getId().toString()};
            paramsAux.put("area.id", areaId);
        }

        int page = 0;
        int perPage = 30;
        if (paramsAux.containsKey("page")) {
            page = Integer.parseInt(paramsAux.get("page")[0]);
            paramsAux.remove("page");
        }
        if (paramsAux.containsKey("per_page")) {
            perPage = Integer.parseInt(paramsAux.get("per_page")[0]);
            paramsAux.remove("per_page");
        }

        List<Controlador> controladores = (List<Controlador>) new InfluuntQueryBuilder(Controlador.class, paramsAux).fetch(Arrays.asList("subarea", "aneis")).query().getResult();
        List<String> controladoresPermitidos = ControladorFisico.getControladoresSincronizadosPorArea(area).stream().map(controladorFisico -> controladorFisico.getId().toString()).collect(Collectors.toList());

        List<AlarmesFalhasControlador> falhas = AlarmesFalhasControlador.ultimosAlarmesFalhasControladores(null, tipoFalhaQuery, controladoresPermitidos);
        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        falhas.forEach(falha -> {
            String idControladorFisico = falha.getIdControlador();
            Controlador controlador;
            Anel anel = null;
            controlador = controladores.stream().filter(c -> c.getControladorFisicoId().equals(idControladorFisico)).findFirst().orElse(null);
            if (controlador != null) {
                if (falha.getIdAnel() != null && StringUtils.isNotEmpty(falha.getIdAnel())) {
                    String idAnel = falha.getIdAnel();
                    anel = controlador.getAneis().stream().filter(a -> (a.isAtivo() && a.getId().toString().equals(idAnel))).findFirst().orElse(null);
                }

                Endereco endereco = (anel != null) ? anel.getEndereco() : controlador.getEndereco();
                itens.addObject()
                    .put("clc", controlador.getCLC())
                    .put("cla", anel != null ? anel.getCLA() : "TODOS OS ANÉIS APRESENTAM FALHAS")
                    .putPOJO(ENDERECO, endereco.nomeEndereco())
                    .put("falha", anel != null ? "Falha no Anel" : "Falha no Controlador")
                    .put("tipo", falha.getConteudo().get("tipoEvento").get("descricao").asText());
            }
        });


        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        ArrayNode data = retorno.putArray("data");
        int skip = page * perPage;
        for (int i = skip; i < skip + perPage; i++) {
            if (i < itens.size()) {
                data.add(itens.get(i));
            } else {
                break;
            }
        }
        retorno.put("total", itens.size());
        return retorno;
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por Falhas
     * <p>
     * FORMATO: CLA | ENDERECO | FALHA | TIPO FALHA
     *
     * @return {@link InputStream} do csv
     */
    public InputStream generateControladoresFalhasCSVReport(Map<String, String[]> params, Area area) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Falhas").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("CLA").append(COMMA_DELIMITER).append("ENDEREÇO").append(COMMA_DELIMITER)
            .append("FALHA").append(COMMA_DELIMITER).append("TIPO FALHA").append(NEW_LINE_SEPARATOR);


        ObjectNode retorno = getControladoresFalhasReportData(params, area);
        retorno.get("data").forEach(jsonNode -> {
            buffer.append(StringUtils.defaultIfBlank(jsonNode.get("clc").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("cla").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get(ENDERECO).asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("falha").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("tipo").asText(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        });

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

    private Map<String, Object> controladoresFalhasReportParams(Map<String, String[]> queryStringParams) {
        Map<String, Object> params = getBasicReportMetadata();
        params.put("relatorioPor", "Fabricante");
        return params;
    }

    public ObjectNode getLogsReportData(Map<String, String[]> params, Area area) {
        Map<String, String[]> paramsAux = new HashMap<>();
        paramsAux.putAll(params);
        paramsAux.remove("tipoRelatorio");

        final String tipoLog = (params.containsKey("tipoLog_eq")) ? String.valueOf(params.get("tipoLog_eq")[0]) : "";
        if (params.containsKey("tipoLog_eq")) {
            paramsAux.remove("tipoLog_eq");
        }

        if (area != null) {
            String[] areaId = {area.getId().toString()};
            paramsAux.put("area.id", areaId);
        }

        if (params.containsKey("filtrarPor_eq")) {
            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("subarea.nome", params.get("subareaAgrupamento"));
                }
            } else if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                if (params.containsKey("subareaAgrupamento")) {
                    paramsAux.put("aneis.agrupamentos.nome", new String[]{params.get("subareaAgrupamento")[0]});
                }
            }
            paramsAux.remove("subareaAgrupamento");
            paramsAux.remove("filtrarPor_eq");
        }

        int page = 0;
        int perPage = 30;
        if (paramsAux.containsKey("page")) {
            page = Integer.parseInt(paramsAux.get("page")[0]);
            paramsAux.remove("page");
        }
        if (paramsAux.containsKey("per_page")) {
            perPage = Integer.parseInt(paramsAux.get("per_page")[0]);
            paramsAux.remove("per_page");
        }

        List<Controlador> controladores = (List<Controlador>) new InfluuntQueryBuilder(Controlador.class, paramsAux).fetch(Arrays.asList("subarea", "aneis")).query().getResult();
        List<LogControlador> logs = LogControlador.ultimosLogsControladores(controladores.stream().map(Controlador::getControladorFisicoId).collect(Collectors.toList()));

        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        logs.forEach(log -> {
            if (tipoLog.isEmpty() || log.getTipoLogControlador().toString().equalsIgnoreCase(tipoLog)) {
                Controlador controlador = controladores.stream().filter((c -> c.getControladorFisicoId().equals(log.getIdControlador()))).findFirst().get();
                String date = InfluuntDateTimeSerializer.parse(new DateTime(log.getTimestamp()));
                itens.addObject().put("horario", date).put("clc", controlador.getCLC()).put("endereco", controlador.getEndereco().nomeEndereco())
                    .put("tipo", log.getTipoLogControlador().toString()).put("mensagem", log.getMensagem());
            }
        });

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        ArrayNode data = retorno.putArray("data");
        int skip = page * perPage;
        for (int i = skip; i < skip + perPage; i++) {
            if (i < itens.size()) {
                data.add(itens.get(i));
            } else {
                break;
            }
        }
        retorno.put("total", itens.size());
        return retorno;
    }

    /**
     * Retorna um CSV com dados dos {@link LogControlador}
     * <p>
     * FORMATO: HORARIO | CLC | ENDERECO | MENSAGEM | TIPO FALHA
     *
     * @return {@link InputStream} do csv
     */
    public InputStream generateLogCSVReport(Map<String, String[]> params, Area area) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Falhas").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("HORARIO").append(COMMA_DELIMITER).append("CLC").append(COMMA_DELIMITER).append("ENDEREÇO").append(COMMA_DELIMITER)
            .append("MENSAGEM").append(COMMA_DELIMITER).append("TIPO FALHA").append(NEW_LINE_SEPARATOR);


        ObjectNode retorno = getLogsReportData(params, area);
        retorno.get("data").forEach(jsonNode -> {
            buffer.append(StringUtils.defaultIfBlank(jsonNode.get("horario").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("clc").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("endereco").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("mensagem").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("tipo").asText(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        });

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

}
