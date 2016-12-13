package execucao;

import engine.GerenciadorDeEstagios;
import engine.Motor;
import integracao.ControladorHelper;
import models.ModoOperacaoPlano;
import models.Plano;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigosol on 12/13/16.
 */
public class lerDadosTest extends MotorTest{

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
        assertEquals("modoOperacao", ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "1", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "E1", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 8, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 42, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 10, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals("modoOperacao", ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "1", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "E3", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 2, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 2, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 50, gerenciador.getContadorTempoCicloEmSegundos());
    }


    @Test
    public void modoApagadoTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 13, 22, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 10);
        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(2);

        Plano plano = gerenciador.getPlano();
        assertEquals("modoOperacao", ModoOperacaoPlano.APAGADO.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "6", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 0, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals("modoOperacao", ModoOperacaoPlano.APAGADO.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "6", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 0, gerenciador.getContadorTempoCicloEmSegundos());
    }

    @Test
    public void modoManualTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 13, 22, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 1);
        acionarModoManual(motor);
        avancarSegundos(motor, 60);
        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(0);

        Plano plano = gerenciador.getPlano();
        assertEquals("modoOperacao", ModoOperacaoPlano.MANUAL.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "E1", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 0, gerenciador.getContadorTempoCicloEmSegundos());

        avancarSegundos(motor, 40);

        plano = gerenciador.getPlano();
        assertEquals("modoOperacao", ModoOperacaoPlano.MANUAL.toString(), plano.getModoOperacao().toString());
        assertEquals("posicaoPlano", "", gerenciador.getPosicaoPlano());
        assertEquals("impostoPorFalha", false, plano.isImpostoPorFalha());
        assertEquals("imposto", false, plano.isImposto());
        assertEquals("estagioAtual", "E1", gerenciador.getEstagioAtual());
        assertEquals("tempoRestanteDoEstagio", 0, gerenciador.getTempoRestanteDoEstagio());
        assertEquals("tempoRestanteDoCiclo", 0, gerenciador.getTempoRestanteDoCiclo());
        assertEquals("momentoCiclo", 0, gerenciador.getContadorTempoCicloEmSegundos());
    }
}
