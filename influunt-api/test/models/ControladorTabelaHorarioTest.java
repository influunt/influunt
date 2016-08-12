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
        assertEquals(2, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "O anel ativo deve ter tabela horário configurada.", "aneis[0].possuiTabelaHorario"),
                new Erro("Controlador", "O anel ativo deve ter tabela horário configurada.", "aneis[1].possuiTabelaHorario")
        ));

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        TabelaHorario tabelaHorarioAnel2Estagios = new TabelaHorario();
        tabelaHorarioAnel2Estagios.setAnel(anelCom2Estagios);
        anelCom2Estagios.setTabelaHorario(tabelaHorarioAnel2Estagios);

        TabelaHorario tabelaHorarioAnel4Estagios = new TabelaHorario();
        tabelaHorarioAnel4Estagios.setAnel(anelCom4Estagios);
        anelCom4Estagios.setTabelaHorario(tabelaHorarioAnel4Estagios);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "A tabela horário deve ter pelo menos 1 evento configurado.", "aneis[0].tabelaHorario.aoMenosUmEvento"),
                new Erro("Controlador", "A tabela horário deve ter pelo menos 1 evento configurado.", "aneis[1].tabelaHorario.aoMenosUmEvento")
        ));

        Evento evento = new Evento();
        evento.setTabelaHorario(tabelaHorarioAnel2Estagios);
        tabelaHorarioAnel2Estagios.addEventos(evento);

        erros = getErros(controlador);
        assertEquals(5, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "A tabela horário deve ter pelo menos 1 evento configurado.", "aneis[0].tabelaHorario.aoMenosUmEvento"),
                new Erro("Controlador", "não pode ficar em branco", "aneis[1].tabelaHorario.eventos[0].posicao"),
                new Erro("Controlador", "não pode ficar em branco", "aneis[1].tabelaHorario.eventos[0].diaDaSemana"),
                new Erro("Controlador", "não pode ficar em branco", "aneis[1].tabelaHorario.eventos[0].horario"),
                new Erro("Controlador", "não pode ficar em branco", "aneis[1].tabelaHorario.eventos[0].plano")
        ));

        Plano plano1Anel2Estagios = anelCom2Estagios.getPlanos().stream().filter(plano -> plano.getModoOperacao().equals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO)).findFirst().get();
        evento.setPosicao(1);
        evento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento.setHorario(LocalTime.parse("08:00:00"));
        evento.setPlano(plano1Anel2Estagios);

        Evento evento2 = new Evento();
        evento2.setTabelaHorario(tabelaHorarioAnel2Estagios);
        tabelaHorarioAnel2Estagios.addEventos(evento2);
        evento2.setPosicao(2);
        evento2.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento2.setHorario(LocalTime.parse("08:00:00"));
        evento2.setPlano(plano1Anel2Estagios);

        erros = getErros(controlador);
        assertEquals(3, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "A tabela horário deve ter pelo menos 1 evento configurado.", "aneis[0].tabelaHorario.aoMenosUmEvento"),
                new Erro("Controlador", "Existem eventos configurados no mesmo dia e horário.", "aneis[1].tabelaHorario.eventos[0].eventosMesmoDiaEHora"),
                new Erro("Controlador", "Existem eventos configurados no mesmo dia e horário.", "aneis[1].tabelaHorario.eventos[1].eventosMesmoDiaEHora")
        ));

        evento2.setHorario(LocalTime.parse("00:00:00"));

        Plano plano1Anel4Estagios = anelCom4Estagios.getPlanos().stream().filter(plano -> plano.getModoOperacao().equals(ModoOperacaoPlano.ATUADO)).findFirst().get();
        Evento evento3 = new Evento();
        evento3.setTabelaHorario(tabelaHorarioAnel4Estagios);
        tabelaHorarioAnel4Estagios.addEventos(evento3);
        evento3.setPosicao(1);
        evento3.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento3.setHorario(LocalTime.parse("08:00:00"));
        evento3.setPlano(plano1Anel2Estagios);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "O Plano deve pertencer ao mesmo anel da tabela horário.", "aneis[0].tabelaHorario.eventos[0].planoDoMesmoAnel")
        ));

        evento3.setPlano(plano1Anel4Estagios);
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

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertNotNull("Anel 2 estágios possui tabela horário", anelCom2Estagios.getTabelaHorario());
        assertNotNull("Anel 4 estágios possui tabela horário", anelCom4Estagios.getTabelaHorario());

        assertEquals("A tabela horário do anel 2 estágios possui 2 eventos", 2, anelCom2Estagios.getTabelaHorario().getEventos().size());
        assertEquals("A tabela horário do anel 4 estágios possui 1 eventos", 1, anelCom4Estagios.getTabelaHorario().getEventos().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));
        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertNotNull("Anel 2 estágios possui tabela horário", anelCom2Estagios.getTabelaHorario());
        assertNotNull("Anel 4 estágios possui tabela horário", anelCom4Estagios.getTabelaHorario());

        assertEquals("A tabela horário do anel 2 estágios possui 2 eventos", 2, anelCom2Estagios.getTabelaHorario().getEventos().size());
        assertEquals("A tabela horário do anel 4 estágios possui 1 eventos", 1, anelCom4Estagios.getTabelaHorario().getEventos().size());
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

        Anel anelCom2Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertNotNull("Anel 2 estágios possui tabela horário", anelCom2Estagios.getTabelaHorario());
        assertNotNull("Anel 4 estágios possui tabela horário", anelCom4Estagios.getTabelaHorario());

        assertEquals("A tabela horário do anel 2 estágios possui 2 eventos", 2, anelCom2Estagios.getTabelaHorario().getEventos().size());
        assertEquals("A tabela horário do anel 4 estágios possui 1 eventos", 1, anelCom4Estagios.getTabelaHorario().getEventos().size());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class, TabelaHorariosCheck.class);
    }

}
