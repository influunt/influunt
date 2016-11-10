package execucao.gerenciadorPlanos;


import engine.Motor;
import execucao.MotorTest;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaPlanosTest extends MotorTest {
    @Test
    public void entradaPlanoNormalTest() throws IOException {
        inicioControlador = new DateTime(2016, 11, 5, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 5, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 6, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 6, 18, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 7, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 7, 18, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 3, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 3, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 14, listaTrocaPlano.size());
    }

    @Test
    public void entradaPlanoEspecialRecorrenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 12, 25, 7, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 7, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao.plusHours(1)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusHours(17)).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 12, 25, 19, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 19, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 12, 25, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 12, 25, 0, 0, 0);
        inicioExecucao = new DateTime(2017, 12, 25, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());
    }

    @Test
    public void entradaPlanoEspecialNaoRecorrenteTest() throws IOException {
        inicioControlador = new DateTime(2017, 3, 15, 7, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 7, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao.plusHours(1)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusHours(17)).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 3, 15, 19, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 19, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 3, 15, 0, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2018, 3, 15, 0, 0, 0);
        inicioExecucao = new DateTime(2018, 3, 15, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 14, listaTrocaPlano.size());
    }
}
