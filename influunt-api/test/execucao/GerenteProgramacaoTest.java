package execucao;

import models.DiaDaSemana;
import models.Evento;
import models.TabelaHorario;
import models.TipoEvento;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.junit.Test;
import os72c.client.v2.GerenciadorDeEventos;
import os72c.client.v2.GerenteProgramacao;
import utils.CustomCalendar;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;
import static com.sun.tools.doclint.Entity.rang;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenteProgramacaoTest {

    @Test
    public void sobreposicaoPrioridadeTest() {
        GerenciadorDeEventos g = new GerenciadorDeEventos();
        List<Evento> eventoList = new ArrayList<>();

        DateTime dt = new DateTime(2016, 9, 18, 0, 0, 0);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.TODOS_OS_DIAS, instante.getTime(), 1));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SABADO, instante.getTime(), 2));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SEXTA, instante.getTime(), 3));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SABADO_A_DOMINGO, instante.getTime(), 4));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SABADO, instante.getTime(), 5));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEXTA, instante.getTime(), 6));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.QUINTA, instante.getTime(), 7));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.QUARTA, instante.getTime(), 8));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.TERCA, instante.getTime(), 9));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA, instante.getTime(), 10));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.DOMINGO, instante.getTime(), 10));

        g.addEventos(eventoList);
        assertFalse(g.getIntervalos().asMapOfRanges()
                                     .values()
                                     .stream()
                                     .mapToInt(e -> e.getPosicaoPlano())
                                     .anyMatch(value -> value < 5));

        eventoList = new ArrayList<>();

        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.DOMINGO, instante.getTime(), 10));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA, instante.getTime(), 10));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.TERCA, instante.getTime(), 9));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.QUARTA, instante.getTime(), 8));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.QUINTA, instante.getTime(), 7));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEXTA, instante.getTime(), 6));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SABADO, instante.getTime(), 5));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SABADO_A_DOMINGO, instante.getTime(), 4));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SEXTA, instante.getTime(), 3));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SABADO, instante.getTime(), 2));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.TODOS_OS_DIAS, instante.getTime(), 1));

        g = new GerenciadorDeEventos();

        g.addEventos(eventoList);
        assertFalse(g.getIntervalos().asMapOfRanges()
                .values()
                .stream()
                .mapToInt(e -> e.getPosicaoPlano())
                .anyMatch(value -> value < 5));

    }


    @Test
    public void propagacaoTest() {
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        DateTime dt = new DateTime(2016, 9, 20, 10, 0, 0);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));

        List<Evento> eventoList = new ArrayList<>();

        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.TERCA, instante.getTime(), 1));

        dt = new DateTime(2016, 9, 22, 10, 0, 0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL, DiaDaSemana.QUINTA, instante.getTime(), 2));

        g.addEventos(eventoList);

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,9,59,59);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,10,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,9,59,59);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,10,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());


    }

    @Test
    public void sobreposicaoTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        DateTime dt = new DateTime(2016,9,18,0,0,0);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));

        List<Evento> eventoList = new ArrayList<>();

        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA,instante.getTime(),1));

        dt = new DateTime(2016,9,18,18,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO,instante.getTime(),2));

        dt = new DateTime(2016,9,18,18,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO,instante.getTime(),3));

        dt = new DateTime(2016,9,18,18,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO_A_DOMINGO,instante.getTime(),4));

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA_A_SEXTA,instante.getTime(),5));

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA_A_SABADO,instante.getTime(),6));

        g.addEventos(eventoList);
        g.imprimeTabelaHoraria();

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,18,18,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(4),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(6),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,18,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

    }

    @Test
    public void tabelaSabadoDomingoTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        DateTime dt = new DateTime(2016,9,18,0,0,0);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));

        List<Evento> eventoList = new ArrayList<>();

        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.TODOS_OS_DIAS,instante.getTime(),1));

        dt = new DateTime(2016,9,18,20,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO_A_DOMINGO,instante.getTime(),2));

        g.addEventos(eventoList);

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,18,20,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,20,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,25,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());


    }

    @Test
    public void tabelaComplicadaTest(){

        GerenciadorDeEventos g = new GerenciadorDeEventos();

        DateTime dt = new DateTime(2016,9,18,0,0,0);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));

        List<Evento> eventoList = new ArrayList<>();

        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.TODOS_OS_DIAS,instante.getTime(),1));

        dt = new DateTime(2016,9,18,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA_A_SEXTA,instante.getTime(),2));

        dt = new DateTime(2016,9,18,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA_A_SEXTA,instante.getTime(),3));

        dt = new DateTime(2016,9,18,14,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA,instante.getTime(),4));

        dt = new DateTime(2016,9,18,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.TERCA,instante.getTime(),5));

        dt = new DateTime(2016,9,18,5,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.QUINTA,instante.getTime(),6));

        dt = new DateTime(2016,9,18,5,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEXTA,instante.getTime(),7));

        dt = new DateTime(2016,9,18,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEXTA,instante.getTime(),8));

        dt = new DateTime(2016,9,18,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEXTA,instante.getTime(),9));

        dt = new DateTime(2016,9,18,20,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO_A_DOMINGO,instante.getTime(),10));

        dt = new DateTime(2016,9,18,10,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SABADO_A_DOMINGO,instante.getTime(),11));

        dt = new DateTime(2016,9,18,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        eventoList.add(criaEvento(TipoEvento.NORMAL,DiaDaSemana.SEGUNDA_A_SABADO,instante.getTime(),12));

        g.addEventos(eventoList);

        dt = new DateTime(2016,9,18,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,18,10,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(11),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,18,20,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(10),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,14,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(4),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,19,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,20,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,21,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,5,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(6),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,22,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,5,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(7),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,6,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(8),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,23,19,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(9),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,0,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,3,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(12),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,10,0,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(11),g.eventoAtual(instante).getPosicaoPlano());

        dt = new DateTime(2016,9,24,20,30,0);
        instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));
        assertEquals(Integer.valueOf(10),g.eventoAtual(instante).getPosicaoPlano());


        g.imprimeTabelaHoraria();

    }

    @NotNull
    private Evento criaEvento(TipoEvento tipoEvento,DiaDaSemana diaDaSemana,Date data,int posicao) {
        Evento evento = new Evento();
        evento.setTipo(tipoEvento);
        evento.setDiaDaSemana(diaDaSemana);
        evento.setData(data);
        evento.setPosicaoPlano(posicao);
        return evento;
    }

    @Test
    public void planoAtualTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        DateTime dt = new DateTime(2016,9,18,11,20,1);
        Calendar instante = dt.toCalendar(Locale.forLanguageTag("pt-BR"));

        TabelaHorario th = getTabelaHoraria();
        List<Evento> eventoList = th.getEventos();
        g.addEventos(eventoList);
        g.imprimeTabelaHoraria();

        assertEquals(8,g.getQuantidadeIntervalos());
        pergunta(1,instante);
        System.out.println(g.getMSNaSemana(instante));


        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(Integer.valueOf(4),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(Integer.valueOf(6),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(Integer.valueOf(7),g.eventoAtual(instante).getPosicaoPlano());
    }

    @Test
    public void eventoDePrioridadeMenorNaoDeveSobreporTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        Calendar instante = Calendar.getInstance();
        instante.set(2016,8,19,11,0,1);

        Evento domingo = new Evento();
        domingo.setTipo(TipoEvento.NORMAL);
        domingo.setDiaDaSemana(DiaDaSemana.DOMINGO);
        domingo.setData(instante.getTime());
        domingo.setPosicaoPlano(1);

        Evento sabadoDomingo = new Evento();
        sabadoDomingo.setTipo(TipoEvento.NORMAL);
        sabadoDomingo.setDiaDaSemana(DiaDaSemana.SABADO_A_DOMINGO);
        sabadoDomingo.setData(instante.getTime());
        sabadoDomingo.setPosicaoPlano(2);

        List<Evento> eventos = new ArrayList<>();
        eventos.add(domingo);
        eventos.add(sabadoDomingo);

        g.addEventos(eventos);
        g.imprimeTabelaHoraria();
        assertEquals(3,g.getQuantidadeIntervalos());
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());
        instante.add(Calendar.DAY_OF_WEEK,5);
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());
        instante.add(Calendar.HOUR,23);
        instante.add(Calendar.MINUTE,60);
        instante.add(Calendar.SECOND,-1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());
        instante.add(Calendar.SECOND,1);
        pergunta(1,instante);
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());
        instante.add(Calendar.MILLISECOND,-1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());
        instante.add(Calendar.MILLISECOND,1);
        pergunta(1,instante);
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());




    }

    @Test
    public void sobreposicaoNoMesmoDiaTes() {
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        Calendar instante = Calendar.getInstance();
        instante.set(2016,8,19,11,0,1);

        Calendar instanteAnterior = Calendar.getInstance();
        instanteAnterior.set(2016,8,19,10,0,1);

        Calendar instantePosterior = Calendar.getInstance();
        instantePosterior.set(2016,8,19,12,0,1);

        Calendar umDiaDepois = Calendar.getInstance();
        umDiaDepois.set(2016,8,20,12,0,1);


        Calendar umDiaAntes = Calendar.getInstance();
        umDiaAntes.set(2016,8,18,12,0,1);

        Evento eventoInstante = new Evento();
        eventoInstante.setTipo(TipoEvento.NORMAL);
        eventoInstante.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        eventoInstante.setData(instante.getTime());
        eventoInstante.setPosicaoPlano(1);

        Evento eventoInstanteAnterior = new Evento();
        eventoInstanteAnterior.setTipo(TipoEvento.NORMAL);
        eventoInstanteAnterior.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        eventoInstanteAnterior.setData(instanteAnterior.getTime());
        eventoInstanteAnterior.setPosicaoPlano(2);

        Evento eventoInstantePosterior = new Evento();
        eventoInstantePosterior.setTipo(TipoEvento.NORMAL);
        eventoInstantePosterior.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        eventoInstantePosterior.setData(instantePosterior.getTime());
        eventoInstantePosterior.setPosicaoPlano(3);


        List<Evento> eventos = new ArrayList<>();
        eventos.add(eventoInstante);
        eventos.add(eventoInstanteAnterior);
        eventos.add(eventoInstantePosterior);

        g.addEventos(eventos);
        assertEquals(4,g.getQuantidadeIntervalos());
        assertEquals(Integer.valueOf(2), g.eventoAtual(instanteAnterior).getPosicaoPlano());
        assertEquals(Integer.valueOf(1), g.eventoAtual(instante).getPosicaoPlano());
        assertEquals(Integer.valueOf(3), g.eventoAtual(instantePosterior).getPosicaoPlano());
        assertEquals(Integer.valueOf(3), g.eventoAtual(umDiaDepois).getPosicaoPlano());
        assertEquals(Integer.valueOf(3), g.eventoAtual(umDiaAntes).getPosicaoPlano());


    }

    private TabelaHorario getTabelaHoraria() {

        TabelaHorario tabelaHorario = new TabelaHorario();
        ArrayList<Evento> eventos = new ArrayList<>();

        DateTime dt = new DateTime(2016,9,18,11,20,1);
        Calendar cal = dt.toCalendar(Locale.forLanguageTag("pt-BR"));


        Evento domingo = new Evento();
        domingo.setTipo(TipoEvento.NORMAL);
        domingo.setDiaDaSemana(DiaDaSemana.DOMINGO);
        domingo.setData(cal.getTime());
        domingo.setPosicaoPlano(1);
        eventos.add(domingo);


        Evento segunda = new Evento();
        segunda.setTipo(TipoEvento.NORMAL);
        segunda.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        segunda.setData(cal.getTime());
        segunda.setPosicaoPlano(2);
        eventos.add(segunda);


        Evento terca = new Evento();
        terca.setTipo(TipoEvento.NORMAL);
        terca.setDiaDaSemana(DiaDaSemana.TERCA);
        terca.setData(cal.getTime());
        terca.setPosicaoPlano(3);
        eventos.add(terca);

        Evento quarta = new Evento();
        quarta.setTipo(TipoEvento.NORMAL);
        quarta.setDiaDaSemana(DiaDaSemana.QUARTA);
        quarta.setData(cal.getTime());
        quarta.setPosicaoPlano(4);
        eventos.add(quarta);

        Evento quinta = new Evento();
        quinta.setTipo(TipoEvento.NORMAL);
        quinta.setDiaDaSemana(DiaDaSemana.QUINTA);
        quinta.setData(cal.getTime());
        quinta.setPosicaoPlano(5);
        eventos.add(quinta);


        Evento sexta = new Evento();
        sexta.setTipo(TipoEvento.NORMAL);
        sexta.setDiaDaSemana(DiaDaSemana.SEXTA);
        sexta.setData(cal.getTime());
        sexta.setPosicaoPlano(6);
        eventos.add(sexta);

        Evento sabado = new Evento();
        sabado.setTipo(TipoEvento.NORMAL);
        sabado.setDiaDaSemana(DiaDaSemana.SABADO);
        sabado.setData(cal.getTime());
        sabado.setPosicaoPlano(7);
        eventos.add(sabado);

        Evento sabadoDomingo = new Evento();
        sabadoDomingo.setTipo(TipoEvento.NORMAL);
        sabadoDomingo.setDiaDaSemana(DiaDaSemana.SABADO_A_DOMINGO);
        sabadoDomingo.setData(cal.getTime());
        sabadoDomingo.setPosicaoPlano(8);
        eventos.add(sabadoDomingo);

        Evento segundaAsexta = new Evento();
        segundaAsexta.setTipo(TipoEvento.NORMAL);
        segundaAsexta.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SEXTA);
        segundaAsexta.setData(cal.getTime());
        segundaAsexta.setPosicaoPlano(9);
        eventos.add(segundaAsexta);

        Evento segundaASabado = new Evento();
        segundaASabado.setTipo(TipoEvento.NORMAL);
        segundaASabado.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SABADO);
        segundaASabado.setData(cal.getTime());
        segundaASabado.setPosicaoPlano(10);
        eventos.add(segundaASabado);

        Evento todos = new Evento();
        todos.setTipo(TipoEvento.NORMAL);
        todos.setDiaDaSemana(DiaDaSemana.TODOS_OS_DIAS);
        todos.setData(cal.getTime());
        todos.setPosicaoPlano(11);
        eventos.add(todos);

        tabelaHorario.setEventos(eventos);
        return  tabelaHorario;
    }

    private void pergunta(int i, Calendar instante) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY - EEE - HH:mm:ss");
        System.out.println("O plano " + i + " e o retornado em:" + sdf.format(instante.getTime()) + " -- "
                + ((
                ((instante.get(Calendar.DAY_OF_WEEK) -1) * 84600) +
                        (instante.get(Calendar.HOUR_OF_DAY) * 3600) +
                        (instante.get(Calendar.MINUTE) * 60) +
                        instante.get(Calendar.SECOND) + instante.get(Calendar.MILLISECOND)
        ))* 1000);
    }


}
