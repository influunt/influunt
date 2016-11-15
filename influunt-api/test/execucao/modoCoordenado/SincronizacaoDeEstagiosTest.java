package execucao.modoCoordenado;

import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import models.Evento;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class SincronizacaoDeEstagiosTest extends GerenciadorDeTrocasTest {

    @Test
    public void monentoCicloTroca() {
        controlador.getTabelaHoraria();
        Evento evento = controlador.getTabelaHoraria().getEventos().stream().filter(evento1 -> evento1.getPosicao().equals(13)).findFirst().get();

        //Rever os testes passando uma data e hora desejada
        assertEquals(20000L, evento.getMomentoEntrada(1, evento.getDataHora()).longValue());
        assertEquals(10000L, evento.getMomentoEntrada(2, evento.getDataHora()).longValue());

        evento.setHorario(new LocalTime(23, 0, 40));

        assertEquals(2000L, evento.getMomentoEntrada(1, evento.getDataHora()).longValue());
        assertEquals(50000L, evento.getMomentoEntrada(2, evento.getDataHora()).longValue());
    }

    @Test
    public void entradaCorretaDePlanos() {
        inicioControlador = new DateTime(2016, 11, 15, 22, 59, 30);
        inicioExecucao = inicioControlador;
        instante = inicioControlador;
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 150);

        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 3, listaTrocaPlano.get(inicioExecucao.plusSeconds(30)).getPosicaoPlano().intValue());

        assertEquals("Plano Atual", 3, getPlanoTrocaEfetiva(1, 47).getPosicao().intValue());
        assertEquals("Plano Atual", 3, getPlanoTrocaEfetiva(2, 31).getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(47)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(65)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(75)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(93)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(111)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(130)).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(31)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(51)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(64)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(84)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(102)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(140)).get(2).getEstagio().getPosicao().intValue());
    }

}
