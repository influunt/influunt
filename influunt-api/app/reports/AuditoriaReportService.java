package reports;

import com.google.inject.Inject;
import models.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.Auditoria;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 06/10/16.
 */
public class AuditoriaReportService extends ReportService<Auditoria> {

    private static final Logger LOG = LoggerFactory.getLogger(AuditoriaReportService.class);

    private List<Auditoria> auditorias = new ArrayList<>();

    @Inject
    private BaseJasperReport baseJasperReport;

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Auditoria> lista, ReportType reportType) {
        this.auditorias = lista;
        switch (reportType) {
            case PDF:
                return generatePDFReport(params);
            case CSV:
                return generateCSVReport();
            default:
                return generateCSVReport();
        }
    }

    /**
     * Retorna um PDF com os dados da {@link Auditoria}
     *
     * @return {@link InputStream} do pdf
     */
    private InputStream generatePDFReport(Map<String, String[]> queryStringParams) {
        Map<String, Object> reportParams = getAuditoriaReportData(queryStringParams);
        return baseJasperReport.generateReport("auditorias", reportParams, auditorias);
    }

    /**
     * Retorna um CSV com dados da {@link Auditoria}
     * <p>
     * FORMATO: TABELA | ACAO | USUARIO | DATA ALTERACAO
     *
     * @return {@link InputStream} do csv
     */
    private InputStream generateCSVReport() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Auditoria").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("Tabela").append(COMMA_DELIMITER)
                .append("Ação").append(COMMA_DELIMITER)
                .append("Usuario").append(COMMA_DELIMITER)
                .append("Data Alteração").append(NEW_LINE_SEPARATOR);

        for (Auditoria auditoria : auditorias) {
            buffer.append(StringUtils.defaultIfBlank(auditoria.change.getTable(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(auditoria.change.getType().toString(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(auditoria.usuario.getNome(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(InfluuntUtils.formatDateToString(new DateTime(auditoria.change.getEventTime()), null), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        }


        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

    private Map<String, Object> getAuditoriaReportData(Map<String, String[]> queryStringParams) {
        Map<String, Object> reportParams = getBasicReportMetadata();
        String startDate = null;
        String endDate = null;
        if (queryStringParams.containsKey("change.eventTime_start")) {
            startDate = queryStringParams.get("change.eventTime_start")[0].substring(0, 11);
        }
        if (queryStringParams.containsKey("change.eventTime_end")) {
            endDate = queryStringParams.get("change.eventTime_end")[0].substring(0, 11);
        }
        String reportDateRange = "reportDateRange";
        if (startDate != null && endDate != null) {
            reportParams.put(reportDateRange, startDate.concat(" a ").concat(endDate));
        } else if (startDate != null) {
            reportParams.put(reportDateRange, "A partir de ".concat(startDate));
        } else if (endDate != null) {
            reportParams.put(reportDateRange, "Início até ".concat(endDate));
        } else {
            reportParams.put(reportDateRange, "Histórico completo");
        }
        if (queryStringParams.containsKey("usuario.id")) {
            Usuario usuario = Usuario.find.byId(UUID.fromString(queryStringParams.get("usuario.id")[0]));
            if (usuario != null) {
                reportParams.put("usuario", usuario.getNome().concat(" (").concat(usuario.getEmail()).concat(")"));
            } else {
                reportParams.put("usuario", "-");
            }
        }
        if (queryStringParams.containsKey("change.table")) {
            reportParams.put("classe", queryStringParams.get("change.table")[0]);
        }
        if (queryStringParams.containsKey("change.type")) {
            reportParams.put("tipoOperacao", queryStringParams.get("change.type")[0]);
        }

        reportParams.put("totalAuditorias", auditorias.size());


        return reportParams;
    }
}
