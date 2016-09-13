package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.Area;
import models.Cidade;
import models.Subarea;
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

public class SubareasControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Area area;

    @Before
    public void setup() {
        Cidade cidade = new Cidade();
        cidade.setNome("SP");
        cidade.save();

        area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();
    }

    @Test
    public void testCriarNovaSubarea() {
        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Consolação");
        subarea.setNumero(254);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SubareasController.create().url()).bodyJson(Json.toJson(subarea));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Subarea subareaRetornada = Json.fromJson(json, Subarea.class);

        assertEquals(200, postResult.status());
        assertEquals(254L, subareaRetornada.getNumero().longValue());
        assertEquals("Consolação", subareaRetornada.getNome());
        assertNotNull(subareaRetornada.getId());
    }

    @Test
    public void testAtualizarSubareaNaoExistente() {
        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNumero(254);

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(subarea));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarSubareaExistente() {
        Subarea subarea = new Subarea();
        subarea.setNome("Consolação");
        subarea.setNumero(254);
        subarea.setArea(area);
        subarea.save();

        UUID subareaId = subarea.getId();
        assertNotNull(subareaId);

        Subarea novaSubarea = new Subarea();
        novaSubarea.setArea(area);
        novaSubarea.setNome("Consolação x Paulista");
        novaSubarea.setNumero(254);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.SubareasController.update(subareaId.toString()).url())
                .bodyJson(Json.toJson(novaSubarea));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Subarea subareaRetornada = Json.fromJson(json, Subarea.class);

        assertEquals(254, subareaRetornada.getNumero().longValue());
        assertEquals("Consolação x Paulista", subareaRetornada.getNome());
        assertNotNull(subareaRetornada.getId());
    }

    @Test
    public void testApagarSubareaExistente() {
        Subarea subarea = new Subarea();
        subarea.setNumero(243);
        subarea.setArea(area);
        subarea.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.SubareasController.delete(subarea.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Subarea.find.byId(subarea.getId()));
    }

    @Test
    public void testApagarSubareaNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.SubareasController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarSubareas() {
        Subarea subarea = new Subarea();
        subarea.setNumero(254);
        subarea.setArea(area);
        subarea.save();

        Subarea subarea1 = new Subarea();
        subarea1.setNumero(255);
        subarea1.setArea(area);
        subarea1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.SubareasController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Area> subareas = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, subareas.size());
    }

    @Test
    public void testBuscarDadosSubarea() {
        Subarea subarea = new Subarea();
        subarea.setNumero(254);
        subarea.setArea(area);
        subarea.save();
        UUID subareaId = subarea.getId();
        assertNotNull(subareaId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.SubareasController.findOne(subareaId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Subarea subareaRetornada = Json.fromJson(json, Subarea.class);

        assertEquals(200, result.status());
        assertEquals(subareaId, subareaRetornada.getId());
    }

    @Test
    public void testCriarSubareaNumeroDuplicado() {
        Subarea subarea = new Subarea();
        subarea.setNome("Consolação");
        subarea.setNumero(254);
        subarea.setArea(area);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SubareasController.create().url()).bodyJson(Json.toJson(subarea));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Subarea subareaRetornada = Json.fromJson(json, Subarea.class);

        assertEquals(OK, postResult.status());
        assertEquals(254L, subareaRetornada.getNumero().longValue());
        assertNotNull(subareaRetornada.getId());

        Subarea subareaDuplicada = new Subarea();
        subareaDuplicada.setArea(area);
        subareaDuplicada.setNumero(254);

        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(subareaDuplicada));
        postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

    }


    @Test
    public void testAtualizarSubareaMesmoNumero() {
        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Consolação");
        subarea.setNumero(254);
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SubareasController.create().url()).bodyJson(Json.toJson(subarea));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Subarea subareaRetornada = Json.fromJson(json, Subarea.class);

        assertEquals(OK, postResult.status());
        assertEquals(254, subareaRetornada.getNumero().longValue());
        assertNotNull(subareaRetornada.getId());

        subareaRetornada.setNumero(254);

        postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.SubareasController.update(subareaRetornada.getId().toString()).url()).bodyJson(Json.toJson(subareaRetornada));
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        assertEquals(254, subareaRetornada.getNumero().longValue());

    }

}
