package execucao.modoCoordenado;

import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import execucao.MotorTest;
import integracao.ControladorHelper;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends GerenciadorDeTrocasTest {

    @Test
    public void monentoCicloTroca() {
        controlador.getTabelaHoraria();
        Evento evento = controlador.getTabelaHoraria().getEventos().stream().filter(evento1 -> evento1.getPosicao().equals(13)).findFirst().get();

        assertEquals(20000L, evento.getMomentoEntrada(1).longValue());
        assertEquals(10000L, evento.getMomentoEntrada(2).longValue());

        evento.setHorario(new LocalTime(23, 0, 40));

        assertEquals(2000L, evento.getMomentoEntrada(1).longValue());
        assertEquals(50000L, evento.getMomentoEntrada(2).longValue());

    }
}
