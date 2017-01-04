package reports;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import play.Application;
import play.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class BaseJasperReport {

    private final String REPORT_DEFINITION_PATH = "/app/templates/reports/";

    @Inject
    private Provider<Application> provider;

    public InputStream generateReport(String reportDefFile, Map reportParams, List lista) {
        OutputStream os = new ByteArrayOutputStream();
        try {
            Logger.warn("Gerando o relatorio de [".concat(reportDefFile.toUpperCase()).concat("] ..."));
            String compiledFile = provider.get().path().toString().concat(REPORT_DEFINITION_PATH).concat(reportDefFile).concat(".jasper");

            JRPdfExporter pdfExporter = new JRPdfExporter();

            OutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(os);
            pdfExporter.setExporterOutput(output);

            ExporterInput inp = new SimpleExporterInput(JasperFillManager.fillReport(compiledFile, reportParams, new JRBeanCollectionDataSource(lista)));

            pdfExporter.setExporterInput(inp);
            pdfExporter.exportReport();
        } catch (Exception e) {
            Logger.error("ERRO AO GERAR O RELATORIO: " + e.getMessage(), e);
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
    }
}
