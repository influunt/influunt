package reports;

import com.google.inject.Inject;
import net.sf.jasperreports.engine.JRParameter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import play.Logger;
import play.api.Environment;
import security.Auditoria;
import utils.InfluuntUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lesiopinheiro on 06/10/16.
 */
public abstract class ReportService<T> {


    @Inject
    private Environment env;

    private final String LOGO_REPORT_FILE = "public/images/logo-escura.png";
    private final String LOGO_REPORT_PARAM_NAME = "logoPath";
    private final String DATE_REPORT_PARAM_NAME = "date";
    protected final String FORMAT_DATE_HOUR_COMPLETE = "dd/MM/yyyy HH:mm:ss";

    // Delimiter used in CSV file
    protected final String COMMA_DELIMITER = ";";
    protected final String NEW_LINE_SEPARATOR = "\n";

    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    protected DateTimeZone currentTimeZone;

    public Map<String, Object> getBasicReportMetadata() {
        Map<String, Object> reportMetadata = new HashMap<>();
        reportMetadata.put(JRParameter.REPORT_LOCALE, LOCALE_PT_BR);
        reportMetadata.put(JRParameter.REPORT_TIME_ZONE, java.util.TimeZone.getTimeZone("America/Sao_Paulo"));
        reportMetadata.put(DATE_REPORT_PARAM_NAME, InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE));

        try {
            InputStream image = new FileInputStream(env.getFile(LOGO_REPORT_FILE));
            reportMetadata.put(LOGO_REPORT_PARAM_NAME, image);
        } catch (FileNotFoundException e) {
            Logger.error("ERRO AO CARREGAR LOGO: ".concat(e.getMessage()), e);
        }

        return reportMetadata;
    }

    public abstract InputStream generateReport(Map<String, String[]> stringMap, List<Auditoria> lista, ReportType reportType);
}
