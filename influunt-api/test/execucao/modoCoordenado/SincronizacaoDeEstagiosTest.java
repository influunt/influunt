package execucao.modoCoordenado;

import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class SincronizacaoDeEstagiosTest extends GerenciadorDeTrocasTest {

    @Test
    public void monentoCicloTroca() {
        controlador.getTabelaHoraria();
        Evento evento = controlador.getTabelaHoraria().getEventos().stream().filter(evento1 -> evento1.getPosicao().equals(18)).findFirst().get();

        //Rever os testes passando uma data e hora desejada
        DateTime dataHora = new DateTime(2016, 11, 15, 8, 0, 0, 0);

        assertEquals(14000L, evento.getMomentoEntrada(1, dataHora).longValue());
        assertEquals(4000L, evento.getMomentoEntrada(2, dataHora).longValue());

        dataHora = dataHora.minusSeconds(10);

        assertEquals(4000L, evento.getMomentoEntrada(1, dataHora).longValue());
        assertEquals(52000L, evento.getMomentoEntrada(2, dataHora).longValue());
    }

    @Test
    public void entradaCorretaDePlanos() {
        inicioExecucao = new DateTime(2016, 11, 15, 22, 59, 30, 0);
        instante = inicioExecucao;
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 150);

        assertEquals(6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals(3, listaTrocaPlano.get(inicioExecucao.plusSeconds(30)).getPosicaoPlano().intValue());

        assertEquals(3, getPlanoTrocaEfetiva(1, 47).getPosicao().intValue());
        assertEquals(3, getPlanoTrocaEfetiva(2, 31).getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(47)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(65)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(75)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(93)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(111)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(130)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(31)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(51)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(64)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(84)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(102)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(140)).get(2).getEstagio().getPosicao().intValue());
    }

    @Test
    public void iniciadoControladorEmModoCoordenado() {
        inicioExecucao = new DateTime(2016, 11, 15, 23, 30, 0, 0);
        instante = inicioExecucao;
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 150);

        assertEquals(3, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        //Anel 1
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(20)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(40)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(58)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(78)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(1).getEstagio().getPosicao().intValue());

        //Anel 2
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(31)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(51)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(69)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(88)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(108)).get(2).getEstagio().getPosicao().intValue());
    }

    @Test
    public void iniciadoControladorComDiferencaoNaSequenciaPartida() {
        inicioExecucao = new DateTime(2016, 11, 15, 23, 20, 0, 0);
        instante = inicioExecucao;
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 150);

        assertEquals(3, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        //Anel 1
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(12)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(30)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(48)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao().intValue());

        //Anel 2
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(36)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(50)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(70)).get(2).getEstagio().getPosicao().intValue());
    }

    @Test
    public void iniciadoControladorComDiferencaoNaSequenciaPartidaSemAbatimento() {
        inicioExecucao = new DateTime(2016, 11, 15, 23, 10, 2, 0);
        instante = inicioExecucao;
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 150);

        assertEquals(3, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        //Anel 1
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(20)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(38)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(58)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(78)).get(1).getEstagio().getPosicao().intValue());

        //Anel 2
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(0)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(12)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(32)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(50)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(68)).get(2).getEstagio().getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(88)).get(2).getEstagio().getPosicao().intValue());
    }
}
