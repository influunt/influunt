package reports;

import com.google.inject.Inject;
import models.TabelaEntreVerdes;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by lesiopinheiro on 17/10/16.
 */
public class TabelaEntreverdesReportService extends ReportService<TabelaEntreVerdes> {

    @Inject
    private BaseJasperReport baseJasperReport;

    private List<TabelaEntreVerdes> tabelas;

    @Override
    public InputStream generateReport(Map<String, String[]> stringMap, List<TabelaEntreVerdes> lista, ReportType reportType) {
        this.tabelas = lista;
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
     * Retorna um PDF dos {@link models.Controlador} com {@link models.TabelaEntreVerdes} alteradas no período
     *
     * @return {@link InputStream} do pdf
     */
    private InputStream generatePDFReport() {
        Map<String, Object> params = getBasicReportMetadata();

        return baseJasperReport.generateReport("controladoresEntreverdes", getBasicReportMetadata(), tabelas);
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} com {@link models.TabelaEntreVerdes} alteradas no período
     * <p>
     * FORMATO: STATUS | TOTAL
     *
     * @return {@link InputStream} do csv
     */
    private InputStream generateCSVReport() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Tabelas Entreverdes alteradas no periodo").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("Controlador").append(COMMA_DELIMITER).append("Localização").append(COMMA_DELIMITER)
                .append("Status").append(COMMA_DELIMITER).append("Tabela Entreverdes").append(COMMA_DELIMITER)
                .append("Posição").append(NEW_LINE_SEPARATOR);

        for (TabelaEntreVerdes tabelaEntreVerdes : tabelas) {
            buffer.append(StringUtils.defaultIfBlank(tabelaEntreVerdes.getGrupoSemaforico().getAnel().getControlador().getCLC(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(tabelaEntreVerdes.getGrupoSemaforico().getAnel().getControlador().getNomeEndereco(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(tabelaEntreVerdes.getGrupoSemaforico().getAnel().getControlador().getStatusControladorReal(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(tabelaEntreVerdes.getDescricao(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(tabelaEntreVerdes.getPosicao().toString(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        }


        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

}
