package reports;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Anel;
import models.Plano;
import models.StatusDevice;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import utils.InfluuntQueryBuilder;
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
        throw new NotImplementedException("Metodo nao implementado. Favor utilizar outro metodo gerador.");
    }

    public ObjectNode getPlanosReportData(Map<String, String[]> params) {

        Map<String, String[]> paramsAux = new HashMap<>();
        String[] sort = new String[1];
        sort[0] = "versaoPlano.anel.controlador.area.descricao asc, versaoPlano.anel.controlador.sequencia asc, versaoPlano.anel.posicao";
        String[] sortType = new String[1];
        sortType[0] = "asc";

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

        paramsAux.put("sort", sort);
        paramsAux.put("sort_type", sortType);

        List<Plano> planos = (List<Plano>) new InfluuntQueryBuilder(Plano.class, paramsAux).fetch(Arrays.asList("versaoPlano.anel")).query().getResult();

        ArrayNode itens = JsonNodeFactory.instance.arrayNode();
        planos.stream().filter(plano -> !plano.isManual()).forEach(plano -> {
            ArrayNode estagios = JsonNodeFactory.instance.arrayNode();
            Anel anel = plano.getAnel();
            plano.getEstagiosOrdenados().forEach(estagio -> {
                estagios.addObject().put("estagio", estagio.getEstagio().toString())
                    .put("inicio", estagio.getInicio().toString())
                    .put("verde", estagio.getTempoVerdeEstagio().toString())
                    .put("total", estagio.getDuracaoEstagio().toString());
            });
            itens.addObject()
                .put("numero", plano.getPosicao())
                .put("cla", anel.getCLA())
                .put("endereco", anel.getEndereco().nomeEndereco())
                .put("ciclo", plano.getTempoCiclo().toString())
                .put("defasagem", plano.getDefasagem().toString())
                .putPOJO("estagios", estagios);

        });

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.putArray("data").addAll(itens);

        return retorno;
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por {@link StatusDevice}
     * FORMATO: CLA | ENDERECO | ESTADO | PLANO | MODO
     *
     * @return {@link InputStream} do csv
     **/
    public InputStream generatePlanosCSVReport(Map<String, String[]> params) {

        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Falhas").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        ObjectNode retorno = getPlanosReportData(params);

        // Write the CSV file header
        buffer.append("PLANO").append(COMMA_DELIMITER).append("CONTROLADOR").append(COMMA_DELIMITER)
            .append("LOCAL").append(COMMA_DELIMITER).append("CICLO").append(COMMA_DELIMITER)
            .append("DEFASAGEM").append(NEW_LINE_SEPARATOR);

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
