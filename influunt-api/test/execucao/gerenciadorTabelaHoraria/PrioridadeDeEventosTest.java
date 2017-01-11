package execucao.gerenciadorTabelaHoraria;

import engine.GerenciadorDeTabelaHoraria;
import models.DiaDaSemana;
import models.Evento;
import models.TipoEvento;
import org.joda.time.LocalTime;
import org.junit.Test;
import utils.CustomCalendar;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class PrioridadeDeEventosTest {


    @Test
    public void precedenciaPorTipoTest() {
        Evento especialNaoRecorrente = new Evento();
        especialNaoRecorrente.setTipo(TipoEvento.ESPECIAL_NAO_RECORRENTE);
        especialNaoRecorrente.setData(new Date());


        Evento especialRecorrente = new Evento();
        especialRecorrente.setTipo(TipoEvento.ESPECIAL_RECORRENTE);
        especialRecorrente.setData(new Date());

        Evento normal = new Evento();
        normal.setTipo(TipoEvento.NORMAL);
        normal.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        normal.setHorario(new LocalTime());

        assertEquals(0, especialNaoRecorrente.compareTo(especialNaoRecorrente));
        assertEquals(-1, especialNaoRecorrente.compareTo(especialRecorrente));
        assertEquals(-2, especialNaoRecorrente.compareTo(normal));

        assertEquals(1, especialRecorrente.compareTo(especialNaoRecorrente));
        assertEquals(0, especialRecorrente.compareTo(especialRecorrente));
        assertEquals(-1, especialRecorrente.compareTo(normal));

        assertEquals(2, normal.compareTo(especialNaoRecorrente));
        assertEquals(1, normal.compareTo(especialRecorrente));
        assertEquals(0, normal.compareTo(normal));

    }


    @Test
    public void precedenciaPorDiaSemanaTest() {
        Calendar calendarSegunda = CustomCalendar.getCalendar();
        calendarSegunda.add(Calendar.DAY_OF_MONTH, 1);
        calendarSegunda.add(Calendar.HOUR, 13);

        Calendar calendarTerca = CustomCalendar.getCalendar();
        calendarTerca.add(Calendar.DAY_OF_MONTH, 2);
        calendarTerca.add(Calendar.HOUR, 13);

        Calendar calendarQuarta = CustomCalendar.getCalendar();
        calendarTerca.add(Calendar.DAY_OF_MONTH, 3);
        calendarTerca.add(Calendar.HOUR, 13);

        Calendar calendarQuinta = CustomCalendar.getCalendar();
        calendarQuinta.set(2016, 8, 22, 13, 0, 0);

        Calendar calendarSexta = CustomCalendar.getCalendar();
        calendarSexta.set(2016, 8, 23, 13, 0, 0);

        Calendar calendarSabado = CustomCalendar.getCalendar();
        calendarSabado.set(2016, 8, 24, 13, 0, 0);

        Calendar calendarDomingo = CustomCalendar.getCalendar();
        calendarDomingo.set(2016, 8, 25, 13, 0, 0);

        Calendar calendarTodosDiasAm = CustomCalendar.getCalendar();
        calendarTodosDiasAm.set(2016, 8, 19, 9, 0, 0);

        Calendar calendarTodosDiasPm = CustomCalendar.getCalendar();
        calendarTodosDiasPm.set(2016, 8, 25, 16, 0, 0);


        Evento segunda = new Evento();
        segunda.setTipo(TipoEvento.NORMAL);
        segunda.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        segunda.setHorario(getHorario(calendarSegunda.getTime()));

        Evento terca = new Evento();
        terca.setTipo(TipoEvento.NORMAL);
        terca.setDiaDaSemana(DiaDaSemana.TERCA);
        terca.setHorario(getHorario(calendarTerca.getTime()));

        Evento quarta = new Evento();
        quarta.setTipo(TipoEvento.NORMAL);
        quarta.setDiaDaSemana(DiaDaSemana.QUARTA);
        quarta.setHorario(getHorario(calendarQuarta.getTime()));

        Evento quinta = new Evento();
        quinta.setTipo(TipoEvento.NORMAL);
        quinta.setDiaDaSemana(DiaDaSemana.QUINTA);
        quinta.setHorario(getHorario(calendarQuinta.getTime()));

        Evento sexta = new Evento();
        sexta.setTipo(TipoEvento.NORMAL);
        sexta.setDiaDaSemana(DiaDaSemana.SEXTA);
        sexta.setHorario(getHorario(calendarSexta.getTime()));

        Evento sabado = new Evento();
        sabado.setTipo(TipoEvento.NORMAL);
        sabado.setDiaDaSemana(DiaDaSemana.SABADO);
        sabado.setHorario(getHorario(calendarSabado.getTime()));

        Evento domingo = new Evento();
        domingo.setTipo(TipoEvento.NORMAL);
        domingo.setDiaDaSemana(DiaDaSemana.DOMINGO);
        domingo.setHorario(getHorario(calendarDomingo.getTime()));

        Evento todosOsDiasAM = new Evento();
        todosOsDiasAM.setTipo(TipoEvento.NORMAL);
        todosOsDiasAM.setDiaDaSemana(DiaDaSemana.TODOS_OS_DIAS);
        todosOsDiasAM.setHorario(getHorario(calendarDomingo.getTime()));

        Evento segundaASexta = new Evento();
        segundaASexta.setTipo(TipoEvento.NORMAL);
        segundaASexta.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SEXTA);
        segundaASexta.setHorario(getHorario(calendarQuinta.getTime()));

        Evento segundaASabado = new Evento();
        segundaASabado.setTipo(TipoEvento.NORMAL);
        segundaASabado.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SABADO);
        segundaASabado.setHorario(getHorario(calendarQuinta.getTime()));

        Evento sabadoADomingo = new Evento();
        sabadoADomingo.setTipo(TipoEvento.NORMAL);
        sabadoADomingo.setDiaDaSemana(DiaDaSemana.SABADO_A_DOMINGO);
        sabadoADomingo.setHorario(getHorario(calendarQuinta.getTime()));

        assertEquals(0, domingo.compareTo(domingo));
        assertEquals(-1, domingo.compareTo(segunda));
        assertEquals(-2, domingo.compareTo(terca));
        assertEquals(-3, domingo.compareTo(quarta));
        assertEquals(-4, domingo.compareTo(quinta));
        assertEquals(-5, domingo.compareTo(sexta));
        assertEquals(-6, domingo.compareTo(sabado));
        assertEquals(1, domingo.compareTo(sabadoADomingo));
        assertEquals(2, domingo.compareTo(segundaASexta));
        assertEquals(3, domingo.compareTo(segundaASabado));
        assertEquals(4, domingo.compareTo(todosOsDiasAM));

        assertEquals(1, segunda.compareTo(domingo));
        assertEquals(0, segunda.compareTo(segunda));
        assertEquals(-1, segunda.compareTo(terca));
        assertEquals(-2, segunda.compareTo(quarta));
        assertEquals(-3, segunda.compareTo(quinta));
        assertEquals(-4, segunda.compareTo(sexta));
        assertEquals(-5, segunda.compareTo(sabado));
        assertEquals(2, segunda.compareTo(sabadoADomingo));
        assertEquals(3, segunda.compareTo(segundaASexta));
        assertEquals(4, segunda.compareTo(segundaASabado));
        assertEquals(5, segunda.compareTo(todosOsDiasAM));

        assertEquals(2, terca.compareTo(domingo));
        assertEquals(1, terca.compareTo(segunda));
        assertEquals(0, terca.compareTo(terca));
        assertEquals(-1, terca.compareTo(quarta));
        assertEquals(-2, terca.compareTo(quinta));
        assertEquals(-3, terca.compareTo(sexta));
        assertEquals(-4, terca.compareTo(sabado));
        assertEquals(3, terca.compareTo(sabadoADomingo));
        assertEquals(4, terca.compareTo(segundaASexta));
        assertEquals(5, terca.compareTo(segundaASabado));
        assertEquals(6, terca.compareTo(todosOsDiasAM));

        assertEquals(3, quarta.compareTo(domingo));
        assertEquals(2, quarta.compareTo(segunda));
        assertEquals(1, quarta.compareTo(terca));
        assertEquals(0, quarta.compareTo(quarta));
        assertEquals(-1, quarta.compareTo(quinta));
        assertEquals(-2, quarta.compareTo(sexta));
        assertEquals(-3, quarta.compareTo(sabado));
        assertEquals(4, quarta.compareTo(sabadoADomingo));
        assertEquals(5, quarta.compareTo(segundaASexta));
        assertEquals(6, quarta.compareTo(segundaASabado));
        assertEquals(7, quarta.compareTo(todosOsDiasAM));

        assertEquals(4, quinta.compareTo(domingo));
        assertEquals(3, quinta.compareTo(segunda));
        assertEquals(2, quinta.compareTo(terca));
        assertEquals(1, quinta.compareTo(quarta));
        assertEquals(0, quinta.compareTo(quinta));
        assertEquals(-1, quinta.compareTo(sexta));
        assertEquals(-2, quinta.compareTo(sabado));
        assertEquals(5, quinta.compareTo(sabadoADomingo));
        assertEquals(6, quinta.compareTo(segundaASexta));
        assertEquals(7, quinta.compareTo(segundaASabado));
        assertEquals(8, quinta.compareTo(todosOsDiasAM));

        assertEquals(5, sexta.compareTo(domingo));
        assertEquals(4, sexta.compareTo(segunda));
        assertEquals(3, sexta.compareTo(terca));
        assertEquals(2, sexta.compareTo(quarta));
        assertEquals(1, sexta.compareTo(quinta));
        assertEquals(0, sexta.compareTo(sexta));
        assertEquals(-1, sexta.compareTo(sabado));
        assertEquals(6, sexta.compareTo(sabadoADomingo));
        assertEquals(7, sexta.compareTo(segundaASexta));
        assertEquals(8, sexta.compareTo(segundaASabado));
        assertEquals(9, sexta.compareTo(todosOsDiasAM));

        assertEquals(6, sabado.compareTo(domingo));
        assertEquals(5, sabado.compareTo(segunda));
        assertEquals(4, sabado.compareTo(terca));
        assertEquals(3, sabado.compareTo(quarta));
        assertEquals(2, sabado.compareTo(quinta));
        assertEquals(1, sabado.compareTo(sexta));
        assertEquals(0, sabado.compareTo(sabado));
        assertEquals(7, sabado.compareTo(sabadoADomingo));
        assertEquals(8, sabado.compareTo(segundaASexta));
        assertEquals(9, sabado.compareTo(segundaASabado));
        assertEquals(10, sabado.compareTo(todosOsDiasAM));

        assertEquals(-1, sabadoADomingo.compareTo(domingo));
        assertEquals(-2, sabadoADomingo.compareTo(segunda));
        assertEquals(-3, sabadoADomingo.compareTo(terca));
        assertEquals(-4, sabadoADomingo.compareTo(quarta));
        assertEquals(-5, sabadoADomingo.compareTo(quinta));
        assertEquals(-6, sabadoADomingo.compareTo(sexta));
        assertEquals(-7, sabadoADomingo.compareTo(sabado));
        assertEquals(0, sabadoADomingo.compareTo(sabadoADomingo));
        assertEquals(1, sabadoADomingo.compareTo(segundaASexta));
        assertEquals(2, sabadoADomingo.compareTo(segundaASabado));
        assertEquals(3, sabadoADomingo.compareTo(todosOsDiasAM));

        assertEquals(-2, segundaASexta.compareTo(domingo));
        assertEquals(-3, segundaASexta.compareTo(segunda));
        assertEquals(-4, segundaASexta.compareTo(terca));
        assertEquals(-5, segundaASexta.compareTo(quarta));
        assertEquals(-6, segundaASexta.compareTo(quinta));
        assertEquals(-7, segundaASexta.compareTo(sexta));
        assertEquals(-8, segundaASexta.compareTo(sabado));
        assertEquals(-1, segundaASexta.compareTo(sabadoADomingo));
        assertEquals(0, segundaASexta.compareTo(segundaASexta));
        assertEquals(1, segundaASexta.compareTo(segundaASabado));
        assertEquals(2, segundaASexta.compareTo(todosOsDiasAM));

        assertEquals(-3, segundaASabado.compareTo(domingo));
        assertEquals(-4, segundaASabado.compareTo(segunda));
        assertEquals(-5, segundaASabado.compareTo(terca));
        assertEquals(-6, segundaASabado.compareTo(quarta));
        assertEquals(-7, segundaASabado.compareTo(quinta));
        assertEquals(-8, segundaASabado.compareTo(sexta));
        assertEquals(-9, segundaASabado.compareTo(sabado));
        assertEquals(-2, segundaASabado.compareTo(sabadoADomingo));
        assertEquals(-1, segundaASabado.compareTo(segundaASexta));
        assertEquals(0, segundaASabado.compareTo(segundaASabado));
        assertEquals(1, segundaASabado.compareTo(todosOsDiasAM));

        assertEquals(-4, todosOsDiasAM.compareTo(domingo));
        assertEquals(-5, todosOsDiasAM.compareTo(segunda));
        assertEquals(-6, todosOsDiasAM.compareTo(terca));
        assertEquals(-7, todosOsDiasAM.compareTo(quarta));
        assertEquals(-8, todosOsDiasAM.compareTo(quinta));
        assertEquals(-9, todosOsDiasAM.compareTo(sexta));
        assertEquals(-10, todosOsDiasAM.compareTo(sabado));
        assertEquals(-3, todosOsDiasAM.compareTo(sabadoADomingo));
        assertEquals(-2, todosOsDiasAM.compareTo(segundaASexta));
        assertEquals(-1, todosOsDiasAM.compareTo(segundaASabado));
        assertEquals(0, todosOsDiasAM.compareTo(todosOsDiasAM));

    }

    private LocalTime getHorario(Date time) {
        return new LocalTime(time.getHours(), time.getMinutes(), time.getSeconds());
    }


    @Test
    public void precedenciaPorDataTest() {
        Evento segunda = new Evento();
        segunda.setTipo(TipoEvento.NORMAL);
        segunda.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        segunda.setHorario(new LocalTime(13, 24, 30));

        Evento segunda2 = new Evento();
        segunda2.setTipo(TipoEvento.NORMAL);
        segunda2.setDiaDaSemana(DiaDaSemana.SEGUNDA);
        segunda2.setHorario(new LocalTime(13, 24, 31));

        assertEquals(0, segunda.compareTo(segunda));
        assertEquals(-1, segunda.compareTo(segunda2));
        assertEquals(1, segunda2.compareTo(segunda));

    }


}
