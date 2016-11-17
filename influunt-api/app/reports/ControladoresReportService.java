package reports;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import models.Controlador;
import models.Fabricante;
import models.StatusDevice;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import status.StatusControladorFisico;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 10/10/16.
 */
public class ControladoresReportService extends ReportService<Controlador> {

    @Inject
    private BaseJasperReport baseJasperReport;

    public InputStream generateControladoresStatusReport(Map<String, String[]> params, ReportType reportType) {
        switch (reportType) {
            case PDF:
                return generateControladoresStatusPDFReport();
            case CSV:
                return generateControladoresStatusCSVReport();
            default:
                return generateControladoresStatusCSVReport();
        }
    }

    public InputStream generateControladoresFalhasReport(Map<String, String[]> params, ReportType reportType) {
        switch (reportType) {
            case PDF:
                return generateControladoresFalhasPDFReport(params);
            case CSV:
                return generateControladoresFalhasCSVReport();
            default:
                return generateControladoresFalhasCSVReport();
        }
    }

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Controlador> lista, ReportType reportType) {
        throw new NotImplementedException("Metodo nao implentado. Favor utilizar outro metodo gerador.");
    }

    /**
     * Retorna um PDF dos {@link models.Controlador} por {@link models.StatusDevice}
     *
     * @return {@link InputStream} do pdf
     */
    private InputStream generateControladoresStatusPDFReport() {
        return baseJasperReport.generateReport("controladoresStatus", getBasicReportMetadata(), getControladoresPorStatus());
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por {@link StatusDevice}
     * <p>
     * FORMATO: STATUS | TOTAL
     *
     * @return {@link InputStream} do csv
     */
    private InputStream generateControladoresStatusCSVReport() {
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
        for (StatusDevice status : StatusDevice.values()) {
            controladores.add(new ControladorStatusVO(status.toString(), Collections.frequency(resultado.values(), status)));
        }
        return controladores;
    }


    /**
     * Retorna um PDF dos {@link models.Controlador} por Falhas
     *
     * @return {@link InputStream} do pdf
     */
    private InputStream generateControladoresFalhasPDFReport(Map<String, String[]> queryStringParams) {
        Map<String, Object> params = controladoresFalhasReportParams(queryStringParams);
        return baseJasperReport.generateReport("controladoresFalhas", params, getControladoresPorFalhas());
    }

    /**
     * Retorna um CSV com dados dos {@link models.Controlador} por Falhas
     * <p>
     * FORMATO: STATUS | TOTAL
     *
     * @return {@link InputStream} do csv
     */
    private InputStream generateControladoresFalhasCSVReport() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Relatório de Controladores por Falhas").append(NEW_LINE_SEPARATOR);

        buffer.append("Gerado em:").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));
        buffer.append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR);

        // Write the CSV file header
        buffer.append("Status").append(COMMA_DELIMITER)
            .append("Total").append(NEW_LINE_SEPARATOR);

        for (ControladorFalhasVO controladorFalhasVO : getControladoresPorFalhas()) {
            buffer.append(StringUtils.defaultIfBlank(controladorFalhasVO.getNomeFabricante(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
            for (FalhaPorFabricanteVO falha : controladorFalhasVO.getFalhas()) {
                buffer.append(StringUtils.defaultIfBlank(falha.getFalha(), StringUtils.EMPTY)).append(COMMA_DELIMITER)
                    .append(StringUtils.defaultIfBlank(falha.getTotal().toString(), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR);
            }

        }


        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

    private List<ControladorFalhasVO> getControladoresPorFalhas() {
//        Gson gson = new Gson();
//        ArrayList<ControladorStatusVO> controladores = new ArrayList<>();
//        for (String erro : errosPorFabricantes) {
//            JsonObject jobj = gson.fromJson(erro, JsonObject.class);
//            JsonObject controladorJson = jobj.get("_id").getAsJsonObject();
//            ControladorStatusVO statusVO = new ControladorStatusVO(controladorJson.get("status").getAsString(), jobj.get("total").getAsInt());
//            statusVO.setIdFabricante(controladorJson.get("idFabricante").getAsString());
//            controladores.add(statusVO);
//        }
//
//        Map<String, List<ControladorStatusVO>> controladoresAux = controladores.stream().collect(Collectors.groupingBy(ControladorStatusVO::getIdFabricante));
//
//        ArrayList<ControladorFalhasVO> controladoresFalhas = new ArrayList<>();
//        for (Map.Entry<String, List<ControladorStatusVO>> entry : controladoresAux.entrySet()) {
//            Fabricante fabricante = Fabricante.find.byId(UUID.fromString(entry.getKey()));
//            ControladorFalhasVO controladorFalha = new ControladorFalhasVO();
//            controladorFalha.setIdFabricante(entry.getKey());
//            controladorFalha.setNomeFabricante(fabricante.getNome());
//            for (ControladorStatusVO statusAux : entry.getValue()) {
//                controladorFalha.addFalha(new FalhaPorFabricanteVO(statusAux.getStatus(), statusAux.getTotal()));
//            }
//
//            controladoresFalhas.add(controladorFalha);
//        }
//
//        return controladoresFalhas;
        return null;
    }

    private Map<String, Object> controladoresFalhasReportParams(Map<String, String[]> queryStringParams) {
        Map<String, Object> params = getBasicReportMetadata();
        params.put("relatorioPor", "Fabricante");
        return params;
    }
}
