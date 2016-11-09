package execucao.gerenciadorPlanos;


import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
import engine.IntervaloGrupoSemaforico;
import engine.Motor;
import engine.MotorCallback;
import execucao.MotorTest;
import integracao.ControladorHelper;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaDeModosOperacaoTest extends MotorTest {

    @Test
    public void motorTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlano.get(inicioExecucao.plusSeconds(60)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusSeconds(120)).getPosicaoPlano().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(104)).get(1).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(104)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(114)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(134)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(154)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(172)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(190)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(206)).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(39)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(59)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(77)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(118)).get(2).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(118)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(136)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(158)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).get(3).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaModoApagadoTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 19, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 19, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 6, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 0, 6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 6000, 11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 0, 6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 6000, 11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 0, 5000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 8000, 18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 0, 5000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 8000, 18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 0, 5000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 8000, 18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 0, 5000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 8000, 18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 0, 5000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 8000, 18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 0, 5000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 5000, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 8000, 18000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(138)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaModoIntermitenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 14000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 0, 6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 6000, 11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 0, 6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 6000, 11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 14000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 0, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 11000, 14000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 14000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 3000, 13000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 3000, 13000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 3000, 13000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 3000, 13000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 3000, 13000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 0, 3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 3000, 13000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void entradaModoIntermitenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 20, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 20, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(80)).get(2).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(3).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaESaidaModoIntermitenteNoEntreverdeTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 21, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 21, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(80)).get(2).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(3).getPosicaoPlano().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(69)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(60)).get(3).getPosicaoPlano().intValue());
    }
}
