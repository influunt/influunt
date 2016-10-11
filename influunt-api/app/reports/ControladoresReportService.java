package reports;

import com.google.inject.Inject;
import models.Controlador;
import models.StatusDevice;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import status.StatusControladorFisico;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by lesiopinheiro on 10/10/16.
 */
public class ControladoresReportService extends ReportService<Controlador> {

    private static final Logger LOG = LoggerFactory.getLogger(AuditoriaReportService.class);

    @Inject
    private BaseJasperReport baseJasperReport;

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Controlador> lista, ReportType reportType) {
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
     * Retorna um PDF dos {@link models.Controlador} por {@link models.StatusDevice}
     *
     * @return {@link InputStream} do pdf
     */
    private InputStream generatePDFReport() {
        return baseJasperReport.generateReport("controladoresStatus", getBasicReportMetadata(), getControladoresPorStatus());
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por {@link StatusDevice}
     * <p>
     * FORMATO: STATUS | TOTAL
     *
     * @return {@link InputStream} do csv
     */
    private InputStream generateCSVReport() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Status").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("Status").append(COMMA_DELIMITER)
                .append("Total").append(NEW_LINE_SEPARATOR);

        for (ControladorStatusVO controladorStatusVO : getControladoresPorStatus()) {
            buffer.append(StringUtils.defaultIfBlank(controladorStatusVO.getStatus(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(controladorStatusVO.getTotal().toString(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
        }


        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

    private ArrayList<ControladorStatusVO> getControladoresPorStatus() {
        HashMap<String, StatusDevice> resultado = StatusControladorFisico.ultimoStatusDosControladores();
        ArrayList<ControladorStatusVO> controladores = new ArrayList<>();
        for(StatusDevice status : StatusDevice.values()) {
            controladores.add(new ControladorStatusVO(status.toString(), Collections.frequency(resultado.values(), status)));
        }
        return controladores;
    }
}

