package execucao;

import models.DiaDaSemana;
import models.Evento;
import models.TabelaHorario;
import models.TipoEvento;
import org.junit.Test;
import os72c.client.v2.GerenciadorDeEventos;
import os72c.client.v2.GerenteProgramacao;
import utils.CustomCalendar;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;
import static com.sun.tools.doclint.Entity.rang;
import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenteProgramacaoTest {

    @Test
    public void planoAtualTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        Calendar instante = Calendar.getInstance();
        instante.set(2016,8,18,11,0,1);

        TabelaHorario th = getTabelaHoraria();
        List<Evento> eventoList = th.getEventos();
        g.addEventos(eventoList);
        g.imprimeTabelaHoraria();

        pergunta(1,instante);
        assertEquals(Integer.valueOf(1),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_MONTH, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(2),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(3),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(4),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(5),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(6),g.eventoAtual(instante).getPosicaoPlano());

        instante.add(Calendar.DAY_OF_WEEK, 1);
        pergunta(2,instante);
        assertEquals(Integer.valueOf(7),g.eventoAtual(instante).getPosicaoPlano());


    }

    private void pergunta(int i, Calendar instante) {
        SimpleDateFormat sdf = new SimpleDateFormat("E H:m:s");
        System.out.println("O plano " + i + " e o retornado em:" + sdf.format(instante.getTime()) + " -- "
                + (
                ((instante.get(Calendar.DAY_OF_WEEK) -1) * 84600) +
                (instante.get(Calendar.HOUR_OF_DAY) * 3600) +
                (instante.get(Calendar.MINUTE) * 60) +
                 instante.get(Calendar.SECOND)
                ));
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
    @Test
    public void sobrescritaDeEventosTest(){
        GerenciadorDeEventos g = new GerenciadorDeEventos();

        long instante = (37 * 60 * 60 + (20 * 60)) * 1000;
        long instanteAnterior = (37 * 60 * 60 + (19 * 60)) * 1000;
        long instantePosterior = (37 * 60 * 60 + (21 * 60)) * 1000;

        TabelaHorario th = getTabelaHoraria();
        List<Evento> eventoList = th.getEventos();

        g.addEventos(eventoList);
    }
    private TabelaHorario getTabelaHoraria() {

        TabelaHorario tabelaHorario = new TabelaHorario();
        ArrayList<Evento> eventos = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(2016,8,18,11,0,1);

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

//
        Evento sabadoDomingo = new Evento();
        sabadoDomingo.setTipo(TipoEvento.NORMAL);
        sabadoDomingo.setDiaDaSemana(DiaDaSemana.SABADO_A_DOMINGO);
        sabadoDomingo.setData(cal.getTime());
        sabadoDomingo.setPosicaoPlano(8);
        eventos.add(sabadoDomingo);
//
//        Evento segundaAsexta = new Evento();
//        segundaAsexta.setTipo(TipoEvento.NORMAL);
//        segundaAsexta.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SEXTA);
//        segundaAsexta.setData(cal.getTime());
//        segundaAsexta.setPosicaoPlano(9);
//        eventos.add(segundaAsexta);
//
//        Evento segundaASabado = new Evento();
//        segundaASabado.setTipo(TipoEvento.NORMAL);
//        segundaASabado.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SABADO);
//        segundaASabado.setData(cal.getTime());
//        segundaASabado.setPosicaoPlano(10);
//        eventos.add(segundaASabado);
//
//        Evento todos = new Evento();
//        todos.setTipo(TipoEvento.NORMAL);
//        todos.setDiaDaSemana(DiaDaSemana.TODOS_OS_DIAS);
//        todos.setData(cal.getTime());
//        todos.setPosicaoPlano(11);
//        eventos.add(todos);


        tabelaHorario.setEventos(eventos);
        return  tabelaHorario;
    }


}
