package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.PageType;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.google.inject.Inject;
import models.Area;
import models.Controlador;
import models.Usuario;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;
import reports.ControladoresReportService;
import reports.PlanosReportService;
import reports.ReportType;
import reports.TabelaHorariaReportService;
import security.InfluuntContextManager;
import security.Secured;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 04/10/16.
 */
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class RelatoriosController extends Controller {

    @Inject
    private ControladoresReportService controladoresReportService;

    @Inject
    private PlanosReportService planosReportService;

    @Inject
    private TabelaHorariaReportService tabelaHorariaReportService;

    @Inject
    private InfluuntContextManager contextManager;


    public CompletionStage<Result> gerarRelatorioControladoresStatus() {
        Usuario currentUsuario = getCurrentUsuario();
        if (currentUsuario == null) {
            return CompletableFuture.completedFuture(forbidden());
        }

        Area area = null;
        if (!currentUsuario.isRoot() && !currentUsuario.podeAcessarTodasAreas()) {
            area = currentUsuario.getArea();
        }

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            ObjectNode reportData = controladoresReportService.getControladoresStatusReportData(request().queryString(), area);
            return CompletableFuture.completedFuture(ok(reportData));
        } else {
            InputStream input = controladoresReportService.generateControladoresStatusCSVReport(request().queryString(), area);
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    public CompletionStage<Result> gerarRelatorioControladoresFalhas() {
        Usuario currentUsuario = getCurrentUsuario();
        if (currentUsuario == null) {
            return CompletableFuture.completedFuture(forbidden());
        }

        Area area = null;
        if (!currentUsuario.isRoot() && !currentUsuario.podeAcessarTodasAreas()) {
            area = currentUsuario.getArea();
        }

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(controladoresReportService.getControladoresFalhasReportData(request().queryString(), area)));
        } else {
            InputStream input = controladoresReportService.generateControladoresFalhasCSVReport(request().queryString(), area);
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

    @Transactional
    public CompletionStage<Result> gerarRelatorioPlanos() {
        Usuario currentUsuario = getCurrentUsuario();
        if (currentUsuario == null) {
            return CompletableFuture.completedFuture(forbidden());
        }

        Area area = null;
        if (!currentUsuario.isRoot() && !currentUsuario.podeAcessarTodasAreas()) {
            area = currentUsuario.getArea();
        }

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(planosReportService.getPlanosReportData(request().queryString(), area)));
        } else {
            InputStream input = planosReportService.generatePlanosCSVReport(request().queryString(), area);
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    @Transactional
    public CompletionStage<Result> gerarRelatorioTabelaHoraria() {
        String controladorId = null;
        String dataStr = null;
        if (request().queryString().containsKey("controladorId")) {
            controladorId = request().queryString().get("controladorId")[0];
        }
        if (request().queryString().containsKey("data")) {
            dataStr = request().queryString().get("data")[0];
        }

        if (controladorId == null || dataStr == null) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY));
        }

        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            List<Map<String, String>> reportData = tabelaHorariaReportService.reportData(controladorId, dataStr);
            return CompletableFuture.completedFuture(ok(Json.toJson(reportData)));
        } else {
            InputStream input = tabelaHorariaReportService.generateTabelaHorariaCSVReport(controladorId, dataStr);
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    @Transactional
    public CompletionStage<Result> gerarRelatorioLogControladores() {
        if (StringUtils.isEmpty(request().getQueryString("tipoRelatorio"))) {
            return CompletableFuture.completedFuture(ok(controladoresReportService.getLogsReportData(request().queryString())));
        } else {
            InputStream input = controladoresReportService.generateLogCSVReport(request().queryString());
            return CompletableFuture.completedFuture(ok(input).as(ReportType.CSV.getContentType()));
        }
    }

    private Usuario getCurrentUsuario() {
        Usuario currentUsuario = contextManager.getUsuario(Http.Context.current());
        if (!currentUsuario.isRoot() && !currentUsuario.podeAcessarTodasAreas() && currentUsuario.getArea() == null) {
            return null;
        }
        return currentUsuario;
    }
}
