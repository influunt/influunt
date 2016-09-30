package controllers;

import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.route;

public class EstagiosControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void testApagarEstagioExistente() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Subarea 1");
        subarea.setNumero(1);
        subarea.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        Controlador controlador = new ControladorTestUtil(area, subarea, fabricante, modeloControlador).getControladorDadosBasicos();
        Anel anel = new Anel();
        anel.setControlador(controlador);
        Estagio estagio = new Estagio();
        anel.addEstagio(estagio);
        anel.save();
        estagio.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.EstagiosController.delete(estagio.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Estagio.find.byId(estagio.getId()));
    }

    @Test
    public void testApagarEstagioNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.EstagiosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

}
