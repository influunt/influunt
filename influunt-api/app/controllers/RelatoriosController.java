package controllers;

import com.google.inject.Inject;
import models.TabelaEntreVerdes;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;
import reports.AuditoriaReportService;
import reports.ControladoresReportService;
import reports.ReportType;
import reports.TabelaEntreverdesReportService;
import security.Auditoria;
import utils.InfluuntQueryBuilder;
import utils.InfluuntQueryResult;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 04/10/16.
 */
public class RelatoriosController extends Controller {

    @Inject
    public AuditoriaReportService auditoriaReportService;

    @Inject
    public ControladoresReportService controladoresReportService;

    @Inject
    public TabelaEntreverdesReportService tabelaEntreverdesReportService;

    public CompletionStage<Result> gerarRelatorioAuditoria() {
        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio"));

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        InfluuntQueryResult result = new InfluuntQueryBuilder(Auditoria.class, params).reportMode().auditoriaQuery();
        InputStream input = auditoriaReportService.generateReport(request().queryString(), result.getResult(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

    public CompletionStage<Result> gerarRelatorioControladoresStatus() {

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(controladoresReportService.getControladoresStatusReportData(request().queryString())));
        } else {
            InputStream input = controladoresReportService.generateControladoresStatusCSVReport(request().queryString());
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    public CompletionStage<Result> gerarRelatorioControladoresFalhas() {
        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio"));

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        InputStream input = controladoresReportService.generateControladoresFalhasReport(request().queryString(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

    public CompletionStage<Result> gerarRelatorioControladoresEntreverdes() {
        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio"));

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        params.put("sort", new String[]{"dataAtualizacao"});
        params.put("sort_type", new String[]{"ASC"});
        InfluuntQueryResult result = new InfluuntQueryBuilder(TabelaEntreVerdes.class, params).fetch(Arrays.asList("grupoSemaforico", "grupoSemaforico.anel", "grupoSemaforico.anel.endereco")).query();
        InputStream input = tabelaEntreverdesReportService.generateReport(request().queryString(), result.getResult(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

}
