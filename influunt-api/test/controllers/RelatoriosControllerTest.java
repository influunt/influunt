package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Controlador;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

public class RelatoriosControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Controlador controlador;

    private String GET = "GET";

    @Before
    public void setUp() {
        ControladorHelper helper = new ControladorHelper();
        controlador = helper.getControlador();
        helper.setPlanos(controlador);
    }

    @Test
    public void testRelatorioTabelaHorariaSemControladorId() {
        String queryString = "?data=" + new DateTime().toString();

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
            .uri(routes.RelatoriosController.gerarRelatorioTabelaHoraria().url() + queryString);
        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
    }

    @Test
    public void testRelatorioTabelaHorariaSemData() {
        String queryString = "?controladorId=" + controlador.getId().toString();

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
            .uri(routes.RelatoriosController.gerarRelatorioTabelaHoraria().url() + queryString);
        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
    }

    @Test
    public void testRelatorioTabelaHorariaOk() {
        String queryString = "?controladorId=" + controlador.getId().toString();
        queryString += "&data=" + new DateTime(2016, 11, 28, 0, 0, 0).toString(); // segunda-feira

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
            .uri(routes.RelatoriosController.gerarRelatorioTabelaHoraria().url() + queryString);
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Map> report = Json.fromJson(json, List.class);
        assertEquals(20, report.size());
        assertEquals(6, report.get(0).size());
    }

    @Test
    public void testRelatorioTabelaHorariaEventoEspecial() {
        String queryString = "?controladorId=" + controlador.getId().toString();
        queryString += "&data=" + new DateTime(2016, 12, 25, 0, 0, 0).toString(); // domingo (natal)

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
            .uri(routes.RelatoriosController.gerarRelatorioTabelaHoraria().url() + queryString);
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Map> report = Json.fromJson(json, List.class);
        assertEquals(5, report.size());
        assertEquals(6, report.get(0).size());

        boolean achouNatal = false;
        for (Map map : report) {
            if ("ESPECIAL_RECORRENTE".equals(map.get("tipoEvento"))) {
                achouNatal = true;
            }
        }
        assertTrue(achouNatal);
    }
}
