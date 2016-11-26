package controllers;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.PageType;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.google.inject.Inject;
import models.Controlador;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;
import reports.ControladoresReportService;
import reports.PlanosReportService;
import reports.ReportType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 04/10/16.
 */
public class RelatoriosController extends Controller {

    @Inject
    public ControladoresReportService controladoresReportService;

    @Inject
    public PlanosReportService planosReportService;

    public CompletionStage<Result> gerarRelatorioControladoresStatus() {

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(controladoresReportService.getControladoresStatusReportData(request().queryString())));
        } else {
            InputStream input = controladoresReportService.generateControladoresStatusCSVReport(request().queryString());
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    public CompletionStage<Result> gerarRelatorioControladoresFalhas() {

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(controladoresReportService.getControladoresFalhasReportData(request().queryString())));
        } else {
            InputStream input = controladoresReportService.generateControladoresFalhasCSVReport(request().queryString());
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    public CompletionStage<Result> gerarRelatorioControlador(String id) {

        Content html = views.html.report.controlador.render(Controlador.find.byId(UUID.fromString(id)));

        Pdf pdf = new Pdf();
        pdf.addPage(html.toString(), PageType.htmlAsString);
        pdf.addParam(new Param("--no-footer-line"));
        pdf.addParam(new Param("--enable-javascript"));


        InputStream is = null;
        try {
            is = new ByteArrayInputStream(pdf.getPDF());
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(ok(is).as("application/pdf"));
    }

    public CompletionStage<Result> gerarRelatorioPlanos() {
        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(planosReportService.getPlanosReportData(request().queryString())));
        } else {
            InputStream input = planosReportService.generatePlanosCSVReport(request().queryString());
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

}
