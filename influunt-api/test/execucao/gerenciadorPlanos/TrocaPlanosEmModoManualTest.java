package execucao.gerenciadorPlanos;

import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaPlanosEmModoManualTest extends GerenciadorDeTrocasTest {

    @Test
    public void entrada() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 22, 0, 0);
        inicioExecucao = inicioControlador;
        instante = inicioControlador;
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 10);
        acionarModoManual(motor);
        avancarSegundos(motor, 70);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        desativarModoManual(motor);
        avancarSegundos(motor, 100);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao.plusSeconds(120)).getPosicaoPlano().intValue());

        assertEquals("Plano Atual Anel 1 - Manual", 1, getPlanoTrocaEfetiva(1, 52).getPosicao().intValue());
        assertEquals("Plano Atual Anel 1 - Manual", ModoOperacaoPlano.MANUAL, getPlanoTrocaEfetiva(1, 52).getModoOperacao());

        assertEquals("Plano Atual Anel 3 - Manual", 1, getPlanoTrocaEfetiva(3, 53).getPosicao().intValue());
        assertEquals("Plano Atual Anel 3 - Manual", ModoOperacaoPlano.MANUAL, getPlanoTrocaEfetiva(3, 53).getModoOperacao());

        assertEquals("Plano Atual Anel 2 - Normal", 6, getPlanoTrocaEfetiva(2, 177).getPosicao().intValue());
        assertEquals("Plano Atual Anel 2 - Normal", ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, getPlanoTrocaEfetiva(2, 177).getModoOperacao());

        assertEquals("Plano Atual Anel 1 - Normal", 6, getPlanoTrocaEfetiva(1, 240).getPosicao().intValue());
        assertEquals("Plano Atual Anel 1 - Normal", ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, getPlanoTrocaEfetiva(1, 240).getModoOperacao());

        assertEquals("Plano Atual Anel 3 - Normal", 6, getPlanoTrocaEfetiva(3, 240).getPosicao().intValue());
        assertEquals("Plano Atual Anel 3 - Normal", ModoOperacaoPlano.APAGADO, getPlanoTrocaEfetiva(3, 240).getModoOperacao());
    }


    @Test
    public void entradaDepoisDeIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 11, 24, 23, 0, 0);
        inicioExecucao = inicioControlador;
        instante = inicioControlador;
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 10);
        acionarModoManual(motor);
        avancarSegundos(motor, 70);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        trocarEstagioModoManual(motor);
        avancarSegundos(motor, 40);
        desativarModoManual(motor);
        avancarSegundos(motor, 100);

        assertEquals("Plano Atual", 16, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        assertTrue("Não tem troca de plano", listaTrocaPlanoEfetiva.isEmpty());
    }


}
