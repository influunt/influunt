package test.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import test.config.WithInfluuntApplicationNoAuthentication;
import test.integracao.ControladorHelper;
import models.*;
import org.joda.time.LocalTime;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import test.models.ControladorTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class AgrupamentosControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Controlador controlador;

    private Controlador getControlador() {
        Area area = getArea();
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

        return new ControladorTestUtil(area, subarea, fabricante, modeloControlador).getControladorTabelaHorario();
    }

    private void setControlador() {
        controlador = getControlador();
        controlador.save();
    }

    private Area getArea() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);

        return area;
    }

    private Agrupamento getAgrupamento() {
        Agrupamento agrupamento = new Agrupamento();
        agrupamento.setNome("Teste");
        agrupamento.setTipo(TipoAgrupamento.ROTA);
        agrupamento.setNumero("1");
        agrupamento.setDescricao("Agrupamento de Teste");

        // Plano 1 está configurado em todos os anéis
        agrupamento.setPosicaoPlano(1);
        agrupamento.setAneis(controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList()));
        return agrupamento;
    }

    @Test
    public void testCriarNovoAgrupamento() {
        setControlador();

        Agrupamento agrupamento = getAgrupamento();
        agrupamento.setPosicaoPlano(1);
        agrupamento.setAneis(new ArrayList<>());

        Controlador controlador2 = getControlador();
        Area area2 = getArea();
        area2.save();
        controlador2.setArea(area2);
        controlador2.save();
        controlador.getAneis().stream().filter(Anel::isAtivo).forEach(agrupamento::addAnel);
        controlador2.getAneis().stream().filter(Anel::isAtivo).forEach(agrupamento::addAnel);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(422, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(json.get(0).get("root").asText(), "Agrupamento");
        assertEquals(json.get(0).get("message").asText(), "Todos os aneis deste agrupamento devem pertencer à mesma área");
        assertEquals(json.get(0).get("path").asText(), "todosOsAneisDaMesmaArea");

        controlador2.setArea(controlador.getArea());
        controlador2.update();

        request = new Http.RequestBuilder().method("POST")
            .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        result = route(request);
        assertEquals(200, result.status());
        assertEquals(1, Agrupamento.find.findRowCount());

        json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);
        assertEquals(agrupamento.getTipo(), agrupamentoRetornado.getTipo());
        assertEquals(agrupamento.getNome(), agrupamentoRetornado.getNome());
        assertEquals(agrupamento.getNumero(), agrupamentoRetornado.getNumero());
        assertEquals(agrupamento.getDescricao(), agrupamentoRetornado.getDescricao());
        assertEquals(agrupamento.getDiaDaSemana(), agrupamentoRetornado.getDiaDaSemana());
        assertEquals(agrupamento.getHorario(), agrupamentoRetornado.getHorario());
        assertEquals(agrupamento.getPosicaoPlano(), agrupamentoRetornado.getPosicaoPlano());
    }

    @Test
    public void testAtualizarAgrupamentoNaoExistente() {
        Agrupamento agrupamento = new Agrupamento();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
            .uri(routes.AgrupamentosController.update(UUID.randomUUID().toString()).url())
            .bodyJson(Json.toJson(agrupamento));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testAtualizarAgrupamentoExistente() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();
        agrupamento.save();

        UUID agrupamentoId = agrupamento.getId();
        assertNotNull(agrupamentoId);
        assertEquals("Teste", agrupamento.getNome());
        assertEquals(TipoAgrupamento.ROTA, agrupamento.getTipo());
        assertEquals("1", agrupamento.getNumero());
        assertEquals("Agrupamento de Teste", agrupamento.getDescricao());
        assertEquals(2, agrupamento.getAneis().size());

        agrupamento.setTipo(TipoAgrupamento.CORREDOR);
        agrupamento.setNome("Teste 2");
        agrupamento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        agrupamento.setHorario(LocalTime.MIDNIGHT);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
            .uri(routes.AgrupamentosController.update(agrupamentoId.toString()).url())
            .bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        assertEquals(agrupamentoRetornado.getId(), agrupamentoId);
        assertEquals(TipoAgrupamento.CORREDOR, agrupamentoRetornado.getTipo());
        assertEquals("Teste 2", agrupamentoRetornado.getNome());
        assertEquals("1", agrupamentoRetornado.getNumero());
        assertEquals("Agrupamento de Teste", agrupamentoRetornado.getDescricao());
        assertEquals(2, agrupamentoRetornado.getAneis().size());
    }

    @Test
    public void testAtualizarAgrupamentoComEventos() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(200, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        agrupamentoRetornado.setDiaDaSemana(DiaDaSemana.SEXTA);
        agrupamentoRetornado.setHorario(LocalTime.parse("13:00:00"));

        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.AgrupamentosController.update(agrupamentoRetornado.getId().toString()).url() + "?criarEventos=true")
            .bodyJson(Json.toJson(agrupamentoRetornado));
        result = route(request);
        assertEquals(200, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        List<Evento> novosEventos = Evento.find.where().eq("agrupamento_id", agrupamentoRetornado.getId()).findList();
        assertEquals(agrupamentoRetornado.getControladores().size(), novosEventos.size());
        novosEventos.forEach(evento -> {
            assertEquals(DiaDaSemana.SEXTA, evento.getDiaDaSemana());
            assertEquals(LocalTime.parse("13:00:00"), evento.getHorario());
        });
    }

    @Test
    public void testAtualizarAgrupamentoComEventosComConflito() {
        ControladorHelper helper = new ControladorHelper();
        controlador = helper.getControlador();
        helper.setPlanosComTabelaHorariaMicro(controlador);

        Agrupamento agrupamento = getAgrupamento();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(200, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        agrupamentoRetornado.setDiaDaSemana(DiaDaSemana.DOMINGO);
        agrupamentoRetornado.setHorario(LocalTime.parse("17:00:00"));
        agrupamentoRetornado.setPosicaoPlano(2);

        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.AgrupamentosController.update(agrupamentoRetornado.getId().toString()).url() + "?criarEventos=true")
            .bodyJson(Json.toJson(agrupamentoRetornado));
        result = route(request);
        assertEquals(409, result.status());

        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.AgrupamentosController.update(agrupamentoRetornado.getId().toString()).url() + "?criarEventos=true&substituirEventos=true")
            .bodyJson(Json.toJson(agrupamentoRetornado));
        result = route(request);
        assertEquals(200, result.status());

        json = Json.parse(Helpers.contentAsString(result));
        agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        List<Evento> novosEventos = Evento.find.where().eq("agrupamento_id", agrupamentoRetornado.getId()).findList();
        assertEquals(agrupamentoRetornado.getControladores().size(), novosEventos.size());
        novosEventos.forEach(evento -> {
            assertEquals(DiaDaSemana.DOMINGO, evento.getDiaDaSemana());
            assertEquals(LocalTime.parse("17:00:00"), evento.getHorario());
        });
    }

    @Test
    public void testApagarAgrupamentoExistente() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();
        agrupamento.save();
        assertNotNull(agrupamento.getId());

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.AgrupamentosController.delete(agrupamento.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Agrupamento.find.byId(agrupamento.getId()));
    }

    @Test
    public void testApagarAgrupamentoComPlanos() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();
        agrupamento.getAneis().remove(0);
        agrupamento.save();
        assertNotNull(agrupamento.getId());

        int totalPlanos = Plano.find.findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.AgrupamentosController.delete(agrupamento.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Agrupamento.find.byId(agrupamento.getId()));
        assertEquals(totalPlanos - 2, Plano.find.findRowCount());
    }

    @Test
    public void testApagarAgrupamentoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.AgrupamentosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarAgrupamentos() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();
        agrupamento.setTipo(TipoAgrupamento.CORREDOR);
        agrupamento.save();

        Agrupamento agrupamento1 = getAgrupamento();
        agrupamento1.setTipo(TipoAgrupamento.ROTA);
        agrupamento1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.AgrupamentosController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Agrupamento> agrupamentos = Json.fromJson(json.get("data"), List.class);

        assertEquals(200, result.status());
        assertEquals(2, agrupamentos.size());
    }

    @Test
    public void testBuscarDadosAgrupamento() {
        setControlador();
        Agrupamento agrupamento = getAgrupamento();
        agrupamento.save();
        UUID agrupamentoId = agrupamento.getId();
        assertNotNull(agrupamentoId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.AgrupamentosController.findOne(agrupamentoId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        assertEquals(200, result.status());
        assertEquals(agrupamentoId, agrupamentoRetornado.getId());
        assertEquals(TipoAgrupamento.ROTA, agrupamentoRetornado.getTipo());
    }

}
