package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import json.ControladorCustomSerializer;
import models.*;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
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

    @Test
    public void testAssociarControladoresASubarea() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("teste");
        fabricante.save();

        ModeloControlador modelo = new ModeloControlador();
        modelo.setDescricao("teste");
        modelo.setFabricante(fabricante);
        modelo.save();


        Controlador controlador = new ControladorTestUtil(area, fabricante, modelo).getControladorDadosBasicos();
        controlador.save();
        assertNull(controlador.getSubarea());

        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Consolação");
        subarea.setNumero(255);

        JsonNode json = Json.toJson(subarea);
        ArrayNode controladoresJson = ((ObjectNode) json).putArray("controladoresAssociados");
        controladoresJson.addObject().put("id", controlador.getId().toString());

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST").uri(routes.SubareasController.create().url()).bodyJson(json);
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());
        controlador.refresh();
        assertNotNull(controlador.getSubarea());
    }

    @Test
    public void testBuscarTabelaHorariaParaAssociacao() {
        Controlador c1 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.buscarTabelaHoraria(c1.getSubarea().getId().toString()).url());
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertTrue(json.has("versoesTabelasHorarias"));
        assertTrue(json.has("tabelasHorarias"));
        assertTrue(json.has("eventos"));
        json.get("versoesTabelasHorarias").forEach(vth -> assertFalse(vth.has("id")));
        json.get("tabelasHorarias").forEach(th -> assertFalse(th.has("id")));
        json.get("eventos").forEach(e -> assertFalse(e.has("id")));
        assertEquals(c1.getTabelaHoraria().getIdJson(), json.get("tabelasHorarias").get(0).get("idJson").asText());
    }

    @Test
    public void testBuscarTabelaHorariaParaAssociacaoVazia() {
        Controlador c1 = new ControladorHelper().getControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.buscarTabelaHoraria(c1.getSubarea().getId().toString()).url());
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertTrue(json.has("versoesTabelasHorarias"));
        assertTrue(json.has("tabelasHorarias"));
        assertTrue(json.has("eventos"));
        assertEquals(0, json.get("versoesTabelasHorarias").size());
        assertEquals(0, json.get("tabelasHorarias").size());
        assertEquals(0, json.get("eventos").size());
    }

    @Test
    public void testBuscarTabelaHorariaParaAssociacaoSemControlador() {
        Controlador c1 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        c1.setStatusVersao(StatusVersao.ARQUIVADO);
        c1.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.buscarTabelaHoraria(c1.getSubarea().getId().toString()).url());
        Result result = route(request);
        assertEquals(OK, result.status());
        assertTrue(Helpers.contentAsString(result).isEmpty());
    }

    @Test
    public void testSalvarTabelaHorariaParaSubarea() {
        Controlador dummy = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        ControladorHelper helper = new ControladorHelper();
        TabelaHorario tabela = dummy.getTabelaHoraria();
        tabela.setEventos(new ArrayList<>());
        helper.criarEvento(tabela, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("01:00:00"), 1);
        tabela.update();
        dummy.refresh();

        Controlador c1 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        Controlador c2 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        c2.setSubarea(c1.getSubarea());
        c2.update();
        String subareaId = c1.getSubarea().getId().toString();

        assertEquals(1, c1.getVersoesTabelasHorarias().size());
        assertEquals(1, c2.getVersoesTabelasHorarias().size());

        JsonNode bodyJson = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(dummy);
        bodyJson.get("versoesTabelasHorarias").forEach(vth -> {
            ((ObjectNode) vth).remove("id");
        });
        bodyJson.get("tabelasHorarias").forEach(th -> {
            ((ObjectNode) th).remove("id");
        });
        bodyJson.get("eventos").forEach(e -> {
            ((ObjectNode) e).remove("id");
        });

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.SubareasController.salvarTabelaHoraria(subareaId).url())
            .bodyJson(bodyJson);
        Result result = route(request);
        assertEquals(OK, result.status());

        c1.refresh();
        c2.refresh();

        assertEquals(2, c1.getVersoesTabelasHorarias().size());
        assertEquals(2, c2.getVersoesTabelasHorarias().size());
        assertEquals(1, c1.getTabelaHoraria().getEventos().size());
        assertEquals(1, c2.getTabelaHoraria().getEventos().size());
        assertEquals(DiaDaSemana.TODOS_OS_DIAS, c1.getTabelaHoraria().getEventos().get(0).getDiaDaSemana());
        assertEquals(DiaDaSemana.TODOS_OS_DIAS, c2.getTabelaHoraria().getEventos().get(0).getDiaDaSemana());
        assertEquals(LocalTime.parse("01:00:00").getMillisOfDay(), c1.getTabelaHoraria().getEventos().get(0).getHorario().getMillisOfDay());
        assertEquals(LocalTime.parse("01:00:00").getMillisOfDay(), c2.getTabelaHoraria().getEventos().get(0).getHorario().getMillisOfDay());
        assertEquals(1, c1.getTabelaHoraria().getEventos().get(0).getPosicaoPlano().intValue());
        assertEquals(1, c2.getTabelaHoraria().getEventos().get(0).getPosicaoPlano().intValue());
        assertEquals(1, c1.getTabelaHoraria().getEventos().get(0).getPosicao().intValue());
        assertEquals(1, c2.getTabelaHoraria().getEventos().get(0).getPosicao().intValue());
    }

    @Test
    public void testSalvarTabelaHorariaParaSubareaControladorInvalido() {
        Controlador dummy = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        ControladorHelper helper = new ControladorHelper();
        TabelaHorario tabela = dummy.getTabelaHoraria();
        tabela.setEventos(new ArrayList<>());
        helper.criarEvento(tabela, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("01:00:00"), 1);
        tabela.update();
        dummy.refresh();

        Controlador c1 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        Controlador c2 = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        c2.setSubarea(c1.getSubarea());
        c2.update();
        String subareaId = c1.getSubarea().getId().toString();

        assertEquals(1, c1.getVersoesTabelasHorarias().size());
        assertEquals(1, c2.getVersoesTabelasHorarias().size());

        JsonNode bodyJson = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(c1);
        bodyJson.get("versoesTabelasHorarias").forEach(vth -> {
            ((ObjectNode) vth).remove("id");
        });
        bodyJson.get("tabelasHorarias").forEach(th -> {
            ((ObjectNode) th).remove("id");
        });
        bodyJson.get("eventos").forEach(e -> {
            ((ObjectNode) e).remove("id");
            ((ObjectNode) e).put("posicaoPlano", 99);
        });

        int totalEventos = bodyJson.get("eventos").size();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.SubareasController.salvarTabelaHoraria(subareaId).url())
            .bodyJson(bodyJson);
        Result result = route(request);
        assertEquals(UNPROCESSABLE_ENTITY, result.status());
        JsonNode errosJson = Json.parse(Helpers.contentAsString(result));
        // o dobro pois são dois controladores
        assertEquals(totalEventos * 2, errosJson.size());
    }
}
