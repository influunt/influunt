package models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.hamcrest.Matchers;
import org.joda.time.LocalTime;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
public class ControladorTabelaHorarioTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "O controlador deve ter tabela horária configurada.", "possuiTabelaHoraria")
        ));

        TabelaHorario tabelaHoraria = new TabelaHorario();
        tabelaHoraria.setControlador(controlador);
        controlador.setTabelaHoraria(tabelaHoraria);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "A tabela horária deve ter pelo menos 1 evento configurado.", "tabelaHoraria.aoMenosUmEvento")
        ));

        Evento evento = new Evento();
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEventos(evento);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicao"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].tipo"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].horario"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.ESPECIAL_RECORRENTE);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicao"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].data"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].horario"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.ESPECIAL_NAO_RECORRENTE);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicao"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].data"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].horario"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.NORMAL);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicao"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].diaDaSemana"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].horario"),
                new Erro("Controlador", "não pode ficar em branco", "tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setPosicao(1);
        evento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento.setHorario(LocalTime.parse("08:00:00"));
        evento.setPosicaoPlano(1);

        Evento evento2 = new Evento();
        evento2.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEventos(evento2);
        evento2.setTipo(TipoEvento.NORMAL);
        evento2.setPosicao(2);
        evento2.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento2.setHorario(LocalTime.parse("08:00:00"));
        evento2.setPosicaoPlano(2);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "Existem eventos configurados no mesmo dia e horário.", "tabelaHoraria.eventos[0].eventosMesmoDiaEHora"),
                new Erro("Controlador", "Existem eventos configurados no mesmo dia e horário.", "tabelaHoraria.eventos[1].eventosMesmoDiaEHora")
        ));

        evento2.setHorario(LocalTime.parse("00:00:00"));

        erros = getErros(controlador);
        assertTrue(erros.isEmpty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertNotNull(controlador.getId());
        assertThat(erros, Matchers.empty());


        assertNotNull("Controlador possui tabela horário", controlador.getTabelaHoraria());

        assertEquals("A tabela horário possui 3 eventos", 3, controlador.getTabelaHoraria().getEventos().size());
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controlador.getTabelaHoraria().getEventos().stream().filter(evento -> evento
        .isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controlador.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controlador.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialNaoRecorrente()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));
        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        assertEquals("A tabela horário possui 3 eventos", 3, controladorJson.getTabelaHoraria().getEventos().size());
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controladorJson.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controladorJson.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controladorJson.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialNaoRecorrente()).count());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(controllers.routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(2, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorTabelaHorario();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(controllers.routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(OK, postResult.status());


        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals(controlador.getId(), controladorRetornado.getId());
        assertNotNull(controladorRetornado.getId());

        assertEquals("A tabela horário possui 3 eventos", 3, controladorRetornado.getTabelaHoraria().getEventos().size());
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controladorRetornado.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controladorRetornado.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controladorRetornado.getTabelaHoraria().getEventos().stream().filter(evento -> evento
                .isEventoEspecialNaoRecorrente()).count());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class, TabelaHorariosCheck.class);
    }

}
