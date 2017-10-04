package reports;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import utils.InfluuntQueryBuilder;
import utils.InfluuntQueryResult;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lesiopinheiro on 25/11/16.
 */
public class PlanosReportService extends ReportService<Plano> {

    @Override
    public InputStream generateReport(Map<String, String[]> stringMap, List<Plano> lista, ReportType reportType) {
        throw new NotImplementedException("Metodo não implementado. Favor utilizar outro método gerador.");
    }


    public ObjectNode getPlanosReportData(Map<String, String[]> params, Area area) {
        Map<String, String[]> paramsAux = new HashMap<>();
        String[] sort = new String[1];
        sort[0] = "versaoPlano.anel.controlador.area.descricao asc, versaoPlano.anel.controlador.sequencia asc, versaoPlano.anel.posicao";
        String[] sortType = new String[1];
        sortType[0] = "asc";

        paramsAux.putAll(params);
        paramsAux.remove("tipoRelatorio");

        if (params.containsKey("filtrarPor_eq")) {
            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0]) && params.containsKey("subareaAgrupamento_eq")) {
                paramsAux.put("versaoPlano.anel.controlador.subarea.nome_eq", params.get("subareaAgrupamento_eq"));
            }

            if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0]) && params.containsKey("subareaAgrupamento")) {
                paramsAux.put("versaoPlano.anel.controlador.subarea.nome", params.get("subareaAgrupamento"));
            }

            if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0]) && params.containsKey("subareaAgrupamento_eq")) {
                paramsAux.put("versaoPlano.anel.agrupamentos.nome_eq", params.get("subareaAgrupamento_eq"));
            }

            if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0]) && params.containsKey("subareaAgrupamento")) {
                paramsAux.put("versaoPlano.anel.agrupamentos.nome", params.get("subareaAgrupamento"));
            }
        }

        paramsAux.remove("subareaAgrupamento");
        paramsAux.remove("subareaAgrupamento_eq");
        paramsAux.remove("filtrarPor_eq");

        paramsAux.put("sort", sort);
        paramsAux.put("sort_type", sortType);

        if (area != null) {
            String[] areaId = {area.getId().toString()};
            paramsAux.put("versaoPlano.anel.controlador.area.id", areaId);
        }

        if (params.containsKey("CLC") || params.containsKey("CLA")) {
            String clc = params.containsKey("CLC") ? params.get("CLC")[0] : params.get("CLA")[0];

            String[] parts = clc.split("\\.");
            String areaDescricao = parts.length >= 1 ? parts[0].replaceFirst("^0+(?!$)", "") : null;
            String subareaNumero = parts.length >= 2 ? parts[1].replaceFirst("^0+(?!$)", "") : null;
            String sequencia = parts.length >= 3 ? parts[2].replaceFirst("^0+(?!$)", "") : null;
            String anel = params.containsKey("CLA") ? parts[3].replaceFirst("^0+(?!$)", "") : null;

            paramsAux.put("versaoPlano.anel.controlador.area.descricao", new String[]{areaDescricao});
            paramsAux.put("versaoPlano.anel.controlador.subarea.numero", new String[]{subareaNumero});
            paramsAux.put("versaoPlano.anel.controlador.sequencia", new String[]{sequencia});

            if (anel != null) {
                paramsAux.put("versaoPlano.anel.posicao", new String[]{anel});
            }
        }

        paramsAux.remove("CLC");
        paramsAux.remove("CLA");

        String[] statusArquivado = {"ARQUIVADO"};
        paramsAux.put("versaoPlano.statusVersao_ne", statusArquivado);

        String[] modoOperacao = {Integer.toString(ModoOperacaoPlano.MANUAL.ordinal())};
        paramsAux.put("modo_operacao_ne", modoOperacao);

        List<String> fetches = Arrays.asList("versaoPlano.anel", "versaoPlano.anel.controlador.area", "versaoPlano.anel.controlador");
        InfluuntQueryResult result = new InfluuntQueryBuilder(Plano.class, paramsAux).fetch(fetches).query();
        List<Plano> planos = (List<Plano>) result.getResult();

        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        planos.stream().forEach(plano -> {
            ArrayNode estagios = JsonNodeFactory.instance.arrayNode();
            Anel anel = plano.getAnel();
            plano.getEstagiosOrdenados().forEach(estagio -> {

                estagios.addObject()
                    .put("estagio", estagio.getEstagio().toString())
                    .put("inicio", estagio.getInicio().toString())
                    .put("isEstagioDispensavel", estagio.isDispensavel())
                    .put("verde", estagio.getTempoVerdeEstagio().toString())
                    .put("total", estagio.getDuracaoEstagio().toString());
            });
            itens.addObject()
                .put("numero", plano.getPosicao())
                .put("cla", anel.getCLA())
                .put("endereco", anel.getEndereco().nomeEndereco())
                .put("ciclo", plano.getTempoCiclo().toString())
                .put("defasagem", plano.getDefasagem().toString())
                .put("modoOperacao", plano.getModoOperacao().getName())
                .put("controlador_id", anel.getControlador().getId().toString())
                .set("estagios", estagios);
        });

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.putArray("data").addAll(itens);
        retorno.put("total", result.getTotal());

        return retorno;
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por {@link StatusDevice}
     * FORMATO: CLA | ENDERECO | ESTADO | PLANO | MODO
     *
     * @return {@link InputStream} do csv
     **/
    public InputStream generatePlanosCSVReport(Map<String, String[]> params, Area area) {

        StringBuilder buffer = new StringBuilder(82);

        buffer.append("Relatório de Planos").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        ObjectNode retorno = getPlanosReportData(params, area);

        // Write the CSV file header
        buffer.append("PLANO").append(COMMA_DELIMITER).append("CONTROLADOR").append(COMMA_DELIMITER)
            .append("LOCAL").append(COMMA_DELIMITER).append("CICLO").append(COMMA_DELIMITER)
            .append("DEFASAGEM").append(COMMA_DELIMITER).append("MODO OPERACAO").append(NEW_LINE_SEPARATOR);

        retorno.get("data").forEach(jsonNode -> {
            buffer.append(StringUtils.defaultIfBlank(jsonNode.get("numero").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("cla").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("endereco").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("ciclo").asText(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                .append(StringUtils.defaultIfBlank(jsonNode.get("defasagem").asText(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        });

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }
}
