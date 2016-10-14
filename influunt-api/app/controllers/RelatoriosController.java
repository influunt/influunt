package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import reports.AuditoriaReportService;
import reports.ControladoresReportService;
import reports.ReportType;
import security.Auditoria;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntQueryResult;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 04/10/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class RelatoriosController extends Controller {

    @Inject
    public AuditoriaReportService auditoriaReportService;

    @Inject
    public ControladoresReportService controladoresReportService;

    @Dynamic(value = "Influunt")
    public CompletionStage<Result> gerarRelatorioAuditoria() {
        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio").toString());

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        InfluuntQueryResult result = new InfluuntQueryBuilder(Auditoria.class, params).reportMode().auditoriaQuery();
        InputStream input = auditoriaReportService.generateReport(request().queryString(), result.getResult(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

    @Dynamic(value = "Influunt")
    public CompletionStage<Result> gerarRelatorioControladoresStatus() {

        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio").toString());

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        InputStream input = controladoresReportService.generateControladoresStatusReport(request().queryString(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

    @Dynamic(value = "Influunt")
    public CompletionStage<Result> gerarRelatorioControladoresFalhas() {
        ReportType reportType = ReportType.valueOf(request().getQueryString("tipoRelatorio").toString());

        Map<String, String[]> params = new HashMap<>();
        params.putAll(request().queryString());
        if (params.containsKey("tipoRelatorio")) {
            params.remove("tipoRelatorio");
        }
        InputStream input = controladoresReportService.generateControladoresFalhasReport(request().queryString(), reportType);
        return CompletableFuture.completedFuture(ok(input).as(reportType.getContentType()));
    }

}
