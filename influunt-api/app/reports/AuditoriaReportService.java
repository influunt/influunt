package reports;

import com.google.inject.Inject;
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

/**
 * Created by lesiopinheiro on 06/10/16.
 */
public class AuditoriaReportService extends ReportService<Auditoria> {

    private static final Logger LOG = LoggerFactory.getLogger(AuditoriaReportService.class);

    private List<Auditoria> auditorias = new ArrayList<Auditoria>();

    @Inject
    private BaseJasperReport baseJasperReport;

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Auditoria> lista, ReportType reportType) {
        this.auditorias = lista;
        switch (reportType) {
            case PDF:
                return generatePDFReport();
            case CSV:
                return generateCSVReport();
            default:
                return generateCSVReport();
        }
    }

    /**
     * Retorna um PDF com os dados da {@link Auditoria}
     *
     * @return
     */
    private InputStream generatePDFReport() {
        return baseJasperReport.generateReport("auditorias", getBasicReportMetadata(), auditorias);
    }

    /**
     * Retorna um CSV com dados da {@link Auditoria}
     *
     * FORMATO: TABELA | ACAO | USUARIO | DATA ALTERACAO
     *
     * @return
     */
    private InputStream generateCSVReport() {
        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("Relatório de Auditoria");
            buffer.append(NEW_LINE_SEPARATOR);

            buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
            buffer.append(NEW_LINE_SEPARATOR);
            buffer.append(NEW_LINE_SEPARATOR);

            // Write the CSV file header
            buffer.append("Tabela").append(COMMA_DELIMITER)
                    .append("Ação").append(COMMA_DELIMITER)
                    .append("Usuario").append(COMMA_DELIMITER)
                    .append("Data Alteração").append(NEW_LINE_SEPARATOR);

            for (Auditoria auditoria : auditorias) {
                buffer.append(StringUtils.defaultIfBlank(auditoria.change.getTable(), "")).append(",")
                        .append(StringUtils.defaultIfBlank(auditoria.change.getType().toString(), "")).append(",")
                        .append(StringUtils.defaultIfBlank(auditoria.change.getType().toString(), "")).append(",")
                        .append(StringUtils.defaultIfBlank(auditoria.change.getType().toString(), "")).append(NEW_LINE_SEPARATOR);
            }


        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }


}
