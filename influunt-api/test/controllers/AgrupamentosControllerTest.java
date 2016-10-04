package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.joda.time.LocalTime;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class AgrupamentosControllerTest extends WithInfluuntApplicationNoAuthentication {

    private Controlador getControlador() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
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

    private Agrupamento getAgrupamento() {
        Controlador controlador = getControlador();
        controlador.save();

        Agrupamento agrupamento = new Agrupamento();
        agrupamento.setNome("Teste");
        agrupamento.setTipo(TipoAgrupamento.ROTA);
        agrupamento.setNumero("1");
        agrupamento.setDescricao("Agrupamento de Teste");
        agrupamento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        agrupamento.setHorario(LocalTime.MIDNIGHT);
        // Plano 2 não está configurado em todos os anéis
        agrupamento.setPosicaoPlano(1);
        agrupamento.setAneis(controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList()));
        return agrupamento;
    }

    @Test
    public void testCriarNovoAgrupamento() {
        Controlador controlador = getControlador();
        controlador.save();

        Agrupamento agrupamento = getAgrupamento();
        // Plano 2 não está configurado em todos os anéis
        agrupamento.setPosicaoPlano(2);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(422, result.status());
        assertEquals(0, Agrupamento.find.findRowCount());

        // Plano 1 está configurado em todos os anéis
        agrupamento.setPosicaoPlano(1);
        List<Anel> aneis = agrupamento.getAneis();
        agrupamento.setAneis(new ArrayList<Anel>());

        request = new Http.RequestBuilder().method("POST")
                .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        result = route(request);
        assertEquals(422, result.status());
        assertEquals(0, Agrupamento.find.findRowCount());


        agrupamento.setAneis(aneis);

        request = new Http.RequestBuilder().method("POST")
                .uri(routes.AgrupamentosController.create().url()).bodyJson(Json.toJson(agrupamento));
        result = route(request);
        assertEquals(200, result.status());
        assertEquals(1, Agrupamento.find.findRowCount());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
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
    public void testCriarNovoAgrupamentoComEventos() {
        Controlador controlador = getControlador();
        controlador.save();
        Agrupamento agrupamento = getAgrupamento();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.AgrupamentosController.create().url() + "?criarEventos=true").bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(200, result.status());
        assertEquals(1, Agrupamento.find.findRowCount());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);
        assertEquals(agrupamento.getTipo(), agrupamentoRetornado.getTipo());
        assertEquals(agrupamento.getNome(), agrupamentoRetornado.getNome());
        assertEquals(agrupamento.getNumero(), agrupamentoRetornado.getNumero());
        assertEquals(agrupamento.getDescricao(), agrupamentoRetornado.getDescricao());
        assertEquals(agrupamento.getDiaDaSemana(), agrupamentoRetornado.getDiaDaSemana());
        assertEquals(agrupamento.getHorario(), agrupamentoRetornado.getHorario());
        assertEquals(agrupamento.getPosicaoPlano(), agrupamentoRetornado.getPosicaoPlano());

        List<Evento> eventos = Evento.find.where().eq("agrupamento_id", agrupamentoRetornado.getId()).findList();
        assertEquals(agrupamento.getAneis().size(), eventos.size());
        eventos.forEach(evento -> {
            assertEquals(agrupamento.getDiaDaSemana(), evento.getDiaDaSemana());
            assertEquals(agrupamento.getHorario(), evento.getHorario());
            assertEquals(agrupamento.getPosicaoPlano(), evento.getPosicaoPlano());
            assertEquals(TipoEvento.NORMAL, evento.getTipo());
        });
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
        Agrupamento agrupamento = getAgrupamento();

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.AgrupamentosController.create().url() + "?criarEventos=true").bodyJson(Json.toJson(agrupamento));
        Result result = route(request);
        assertEquals(200, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Agrupamento agrupamentoRetornado = Json.fromJson(json, Agrupamento.class);

        List<Evento> eventos = Evento.find.where().eq("agrupamento_id", agrupamentoRetornado.getId()).findList();
        assertEquals(agrupamentoRetornado.getAneis().size(), eventos.size());
        eventos.forEach(evento -> {
            assertEquals(DiaDaSemana.DOMINGO, evento.getDiaDaSemana());
            assertEquals(LocalTime.MIDNIGHT, evento.getHorario());
        });


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
        assertEquals(agrupamentoRetornado.getAneis().size(), novosEventos.size());
        novosEventos.forEach(evento -> {
            assertNotEquals(eventos.get(0).getId(), evento.getId());
            assertNotEquals(eventos.get(1).getId(), evento.getId());
            assertEquals(DiaDaSemana.SEXTA, evento.getDiaDaSemana());
            assertEquals(LocalTime.parse("13:00:00"), evento.getHorario());
        });
    }


    @Test
    public void testApagarAgrupamentoExistente() {
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
    public void testApagarAgrupamentoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AgrupamentosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    public void testListarAgrupamentos() {
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
