package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.Area;
import models.Cidade;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

public class AreasControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Cidade cidade;

    @Before
    public void setUp() {
        cidade = new Cidade();
        cidade.setNome("BH");
        cidade.save();
    }

    @Test
    public void testCriarNovaArea() {
        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(area));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Area areaRetornada = Json.fromJson(json, Area.class);

        assertEquals(200, postResult.status());
        assertEquals(Integer.valueOf(1), areaRetornada.getDescricao());
        assertNotNull(areaRetornada.getId());
    }

    @Test
    public void testAtualizarAreaNaoExistente() {
        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(area));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarAreaExistente() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        UUID areaId = area.getId();
        assertNotNull(areaId);

        Area novaArea = new Area();
        novaArea.setCidade(cidade);
        novaArea.setDescricao(1);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(areaId.toString()).url())
                .bodyJson(Json.toJson(novaArea));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Area areaRetornada = Json.fromJson(json, Area.class);

        assertEquals(Integer.valueOf(1), areaRetornada.getDescricao());
        assertNotNull(areaRetornada.getId());
    }

    @Test
    public void testApagarAreaExistente() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AreasController.delete(area.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Area.find.byId(area.getId()));
    }

    @Test
    public void testApagarAreaNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AreasController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarAreas() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        Area area1 = new Area();
        area1.setDescricao(1);
        area1.setCidade(cidade);
        area1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.AreasController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Area> areas = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, areas.size());
    }

    @Test
    public void testBuscarDadosArea() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();
        UUID areaId = area.getId();
        assertNotNull(areaId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.AreasController.findOne(areaId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Area areaRetornada = Json.fromJson(json, Area.class);

        assertEquals(200, result.status());
        assertEquals(areaId, areaRetornada.getId());
    }

    @Test
    public void testCriarAreaNomeDuplicado() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(area));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Area areaRetornada = Json.fromJson(json, Area.class);

        assertEquals(OK, postResult.status());
        assertEquals("1", areaRetornada.getDescricao().toString());
        assertNotNull(areaRetornada.getId());

        Area areaDuplicada = new Area();
        areaDuplicada.setCidade(cidade);
        areaDuplicada.setDescricao(1);

        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(areaDuplicada));
        postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

    }


    @Test
    public void testAtualizarAreaMesmoNome() {
        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(area));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Area areaRetornada = Json.fromJson(json, Area.class);

        assertEquals(OK, postResult.status());
        assertEquals("1", areaRetornada.getDescricao().toString());
        assertNotNull(areaRetornada.getId());

        areaRetornada.setDescricao(1);

        postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(areaRetornada.getId().toString()).url()).bodyJson(Json.toJson(areaRetornada));
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        assertEquals("1", areaRetornada.getDescricao().toString());

    }


    @Test
    public void testAtualizarAreaExistenteNew() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        area.setDescricao(22);
        area.update();
    }

}
