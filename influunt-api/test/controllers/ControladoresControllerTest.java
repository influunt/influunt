package controllers;

import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import fixtures.ControladorFixture;
import models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.Bindings;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;

import java.util.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class ControladoresControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration).in(Mode.TEST).build();
    }

    @Test
    public void testCriarNovoControladorVazio() {
        Controlador controlador = new Controlador();
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(7,json.size());

    }
    @Test
    public void testCriarNovoControladorDadosBasicos() {
        Controlador controlador = ControladorFixture.getControladorComDadosBasicos();
        Logger.debug("IDA:" + Json.toJson(controlador).toString());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        Logger.debug("Volta:" + json.toString());
        Assert.assertNotNull(controladorRetornado.getId());

    }

    @Test
    public void testCriarNovoControladorAneisVazio() {
        Controlador controlador = ControladorFixture.getControladorComDadosBasicos();
        controlador.save();
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.aneis().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        assertEquals(2,json.size());
    }
    @Test
    public void testCriarNovoControladorAneis() {
        Controlador controlador = ControladorFixture.getControladorComAneis();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.aneis().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        Assert.assertNotNull(controladorRetornado.getId());
    }

    @Test
    public void testCriarNovoControladorAssociacaoVazia() {
        Controlador controlador = ControladorFixture.getControladorComAneis();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        assertEquals(6,json.size());
    }
    @Test
    public void testCriarNovoControladorAssociacao() {
        Controlador controlador = ControladorFixture.getControladorComAssociacao();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        Logger.debug(json.toString());
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        Assert.assertNotNull(controladorRetornado.getId());
    }

    @Test
    public void testCriarNovoControladorVerdesConflitantesVazio() {
        Controlador controlador = ControladorFixture.getControladorComAssociacao();

        Logger.debug("IDA:" + Json.toJson(controlador).toString());

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.verdesConflitantes().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        System.out.println(json.toString());
        assertEquals(4,json.size());
    }
    @Test
    public void testCriarNovoControladorVerdesConflitantes() {
        Controlador controlador = ControladorFixture.getControladorComVerdesConflitantes();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.verdesConflitantes().url()).bodyJson(Json.toJson(controlador));

        Result result = route(request);
//        assertEquals(OK, result.status());
        JsonNode json  = Json.parse(Helpers.contentAsString(result));
        System.out.println(json.toString());
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        Assert.assertNotNull(controladorRetornado.getId());
    }

    @Test
    public void testApagarControladorExistente() {
        final Controlador controlador = ControladorFixture.getControladorComDadosBasicos();
        controlador.save();
        long controladorId = controlador.getId();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ControladoresController.delete(controladorId).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        Controlador controlador1 = Controlador.find.byId(controladorId);
        assertNull(controlador1);

    }



    @Test
    public void testApagarControladorNaoExistente() {

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ControladoresController.delete(12345).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListarControladores() {
        ControladorFixture.getControladorComDadosBasicos().save();
        ControladorFixture.getControladorComDadosBasicos().save();
        ControladorFixture.getControladorComDadosBasicos().save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Controlador> controladores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(3, controladores.size());
    }


}
