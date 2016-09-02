package models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.TabelaHorariosCheck;
import com.avaje.ebean.Ebean;
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
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
public class ControladorTabelaHorarioTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "O controlador deve ter tabela horário configurada.", "possuiTabelaHoraria")
        ));

        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, usuario);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "A tabela horário deve ter pelo menos 1 evento configurado.", "versoesTabelasHorarias[0].tabelaHoraria.aoMenosUmEvento")
        ));

        Evento evento = new Evento();
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEventos(evento);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].tipo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].horario"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.ESPECIAL_RECORRENTE);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].data"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].horario"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.ESPECIAL_NAO_RECORRENTE);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].data"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].horario"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicaoPlano")
        ));

        evento.setTipo(TipoEvento.NORMAL);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].diaDaSemana"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].horario"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].posicaoPlano")
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
                new Erro(CONTROLADOR, "Existem eventos configurados no mesmo dia e horário.", "versoesTabelasHorarias[0].tabelaHoraria.eventos[0].eventosMesmoDiaEHora"),
                new Erro(CONTROLADOR, "Existem eventos configurados no mesmo dia e horário.", "versoesTabelasHorarias[0].tabelaHoraria.eventos[1].eventosMesmoDiaEHora")
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
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controlador.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controlador.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controlador.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialNaoRecorrente()).count());
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
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controladorJson.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controladorJson.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controladorJson.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialNaoRecorrente()).count());
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
        assertEquals(1, json.size());

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
        assertEquals("A tabela horário possui 1 evento NORMAL", 1, controladorRetornado.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoNormal()).count());
        assertEquals("A tabela horário possui 1 evento RECORRENTE", 1, controladorRetornado.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialRecorrente()).count());
        assertEquals("A tabela horário possui 1 evento NAO RECORRENTE", 1, controladorRetornado.getTabelaHoraria().getEventos().stream()
                .filter(evento -> evento.isEventoEspecialNaoRecorrente()).count());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class, TabelaHorariosCheck.class);
    }

    @Test
    public void testControllerDestroyAnel() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals("Total de Erros", 0, erros.size());

        controlador = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        controlador.update();

        int totalEstagios = Estagio.find.findRowCount();
        int totalEstagiosAnel = anelCom4Estagios.getEstagios().size();
        int totalDetectores = Detector.find.findRowCount();
        int totalDetectoresAnel = anelCom4Estagios.getDetectores().size();
        int totalGruposSemaforicos = GrupoSemaforico.find.findRowCount();
        int totalGruposSemaforicosAnel = anelCom4Estagios.getGruposSemaforicos().size();
        int totalPlanos = Plano.find.findRowCount();
        int totalPlanosAnel = anelCom4Estagios.getPlanos().size();
        int totalTransicoes = Transicao.find.findRowCount();
        int totalTransicoesAnel = ((int) anelCom4Estagios.getGruposSemaforicos().stream().map(GrupoSemaforico::getTransicoes).flatMap(Collection::stream).count());
        int totalTransicoesProibidas = TransicaoProibida.find.findRowCount();
        int totalTransicoesProibidasAnel = Stream.of(
                anelCom4Estagios.getEstagios().stream().map(Estagio::getOrigemDeTransicoesProibidas).collect(Collectors.toSet()),
                anelCom4Estagios.getEstagios().stream().map(Estagio::getDestinoDeTransicoesProibidas).collect(Collectors.toSet()),
                anelCom4Estagios.getEstagios().stream().map(Estagio::getAlternativaDeTransicoesProibidas).collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()).size();
        int totalVerdesConflitantes = VerdesConflitantes.find.findRowCount();
        int totalVerdesConflitantesAnel = anelCom4Estagios.getGruposSemaforicos().stream()
                .map(GrupoSemaforico::getVerdesConflitantes)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()).size();
        int totalTabelaEntreVerdes = TabelaEntreVerdes.find.findRowCount();
        int totalTabelaEntreVerdesAnel = anelCom4Estagios.getGruposSemaforicos().stream()
                .map(GrupoSemaforico::getTabelasEntreVerdes)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()).size();

        anelCom4Estagios.setDestroy(true);
        controlador.deleteAnelSeNecessario();
        controlador.update();
        erros = getErros(controlador);

        assertEquals("Total de Erros", 0, erros.size());
        assertEquals("Quantidade de Aneis", 4, controlador.getAneis().size());
        assertEquals("Quantidade de estagios", totalEstagios - totalEstagiosAnel, Estagio.find.findRowCount());
        assertEquals("Quantidade de grupos semafóricos", totalGruposSemaforicos - totalGruposSemaforicosAnel, GrupoSemaforico.find.findRowCount());
        assertEquals("Quantidade de detectores", totalDetectores - totalDetectoresAnel, Detector.find.findRowCount());
        assertEquals("Quantidade de planos", totalPlanos - totalPlanosAnel, Plano.find.findRowCount());
        assertEquals("Quantidade de transições", totalTransicoes - totalTransicoesAnel, Transicao.find.findRowCount());
        assertEquals("Quantidade de transições proibidas", totalTransicoesProibidas - totalTransicoesProibidasAnel, TransicaoProibida.find.findRowCount());
        assertEquals("Quantidade de verdes conflitantes", totalVerdesConflitantes - totalVerdesConflitantesAnel, VerdesConflitantes.find.findRowCount());
        assertEquals("Quantidade de tabelas entre verdes", totalTabelaEntreVerdes - totalTabelaEntreVerdesAnel, TabelaEntreVerdes.find.findRowCount());
    }

    @Test
    public void testControllerDestroyEstagio() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals("Total de Erros", 0, erros.size());

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().get(0);

        int totalEstagios = Estagio.find.findRowCount();
        int totalDetectores = Detector.find.findRowCount();
        int totalDetectoresEstagio = estagio1AnelCom4Estagios.getDetector() != null ? 1 : 0;
        int totalEstagioGrupoSemaforicos = Ebean.find(EstagioGrupoSemaforico.class).findRowCount();
        int totalEstagioGrupoSemaforicosEstagio = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio1AnelCom4Estagios.getId()).findRowCount();
        int totalTransicoesProibidas = TransicaoProibida.find.findRowCount();
        int totalTransicoesProibidasEstagio = Stream.of(
                estagio1AnelCom4Estagios.getOrigemDeTransicoesProibidas(),
                estagio1AnelCom4Estagios.getDestinoDeTransicoesProibidas(),
                estagio1AnelCom4Estagios.getAlternativaDeTransicoesProibidas())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()).size();

        File appRootPath = app.path();
        estagio1AnelCom4Estagios.delete(appRootPath);

        assertEquals("Quantidade de estágios", totalEstagios - 1, Estagio.find.findRowCount());
        assertEquals("Quantidade de associações estágios x grupos semafóricos", totalEstagioGrupoSemaforicos - totalEstagioGrupoSemaforicosEstagio, Ebean.find(EstagioGrupoSemaforico.class).findRowCount());
        assertEquals("Quantidade de transições proibidas", totalTransicoesProibidas - totalTransicoesProibidasEstagio, TransicaoProibida.find.findRowCount());
        assertEquals("Quantidade de detectores", totalDetectores - totalDetectoresEstagio, Detector.find.findRowCount());
    }

}
