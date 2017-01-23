package test.execucao;

import engine.GerenciadorDeEstagios;
import engine.Motor;
import test.integracao.ControladorHelper;
import models.Anel;
import models.ModoOperacaoPlano;
import models.Plano;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by rodrigosol on 12/13/16.
 */
public class LerDadosDoControladorTest extends MotorTest {

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanosComTabelaHorariaMicro(new ControladorHelper().getControlador());
        controlador.save();
    }

    @Test
    public void modoVerdeTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 13, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 10);
        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(0);

        Plano plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), plano.getModoOperacao().toString());
        assertEquals("1", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("E1", gerenciador.getEstagioAtual());
        assertEquals(8, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(42, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(10, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), plano.getModoOperacao().toString());
        assertEquals("1", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("E3", gerenciador.getEstagioAtual());
        assertEquals(2, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(2, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(50, gerenciador.getContadorTempoCicloEmSegundos());
    }


    @Test
    public void modoApagadoTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 13, 22, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 10);
        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(2);

        Plano plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.APAGADO.toString(), plano.getModoOperacao().toString());
        assertEquals("6", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("", gerenciador.getEstagioAtual());
        assertEquals(0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(0, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.APAGADO.toString(), plano.getModoOperacao().toString());
        assertEquals("6", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("", gerenciador.getEstagioAtual());
        assertEquals(0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(0, gerenciador.getContadorTempoCicloEmSegundos());
    }

    @Test
    public void modoManualTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 13, 22, 0, 0);
        Anel anel = getAnel(3);
        anel.setAceitaModoManual(false);

        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 1);
        acionarModoManual(motor);
        avancarSegundos(motor, 60);
        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(0);

        Plano plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.MANUAL.toString(), plano.getModoOperacao().toString());
        assertEquals("", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("E1", gerenciador.getEstagioAtual());
        assertEquals(0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(0, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals(ModoOperacaoPlano.MANUAL.toString(), plano.getModoOperacao().toString());
        assertEquals("", gerenciador.getPosicaoPlano());
        assertFalse(plano.isImpostoPorFalha());
        assertFalse(plano.isImposto());
        assertEquals("E1", gerenciador.getEstagioAtual());
        assertEquals(0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals(0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals(0, gerenciador.getContadorTempoCicloEmSegundos());
    }
}
