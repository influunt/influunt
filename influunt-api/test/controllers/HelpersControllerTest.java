package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.Area;
import models.Cidade;
import models.Fabricante;
import models.ModeloControlador;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class HelpersControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void testControladorHelper() {
        Cidade bh = new Cidade();
        bh.setNome("Belo Horizonte");
        bh.save();

        Area sul = new Area();
        sul.setDescricao(2);
        sul.setCidade(bh);
        sul.save();

        Area norte = new Area();
        norte.setDescricao(2);
        norte.setCidade(bh);
        norte.save();

        Cidade rio = new Cidade();
        rio.setNome("Rio de Janeiro");
        rio.save();

        Fabricante raro = new Fabricante();
        raro.setNome("Raro Labs");
        raro.save();

        ModeloControlador m1 = new ModeloControlador();
        m1.setFabricante(raro);
        m1.setDescricao("Raro Labs");
        m1.save();

        Fabricante labs = new Fabricante();
        labs.setNome("Labs");
        labs.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.HelpersController.controladorHelper().url());
        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));

        assertEquals(2, json.get("cidades").size());
        assertEquals(2, json.get("cidades").get(0).get("areas").size());
        assertEquals(2, json.get("fabricantes").size());
        assertNotNull(json.get("fabricantes").get(0).get("modelos"));
    }
}
