package execucao.gerenciadorPlanos;


import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import integracao.ControladorHelper;
import models.Controlador;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaPlanosTest extends GerenciadorDeTrocasTest {

    @Before
    @Override
    public void setup() {
        controlador = new ControladorHelper().setPlanosComTabelaHorariaMicro(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Test
    public void entradaPlanoNormalSabadoTest() throws IOException {
        inicioExecucao = new DateTime(2016, 11, 5, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaPlanoNormalDomingoTest() throws IOException {
        listaTrocaPlano = new HashMap<>();
        inicioExecucao = new DateTime(2016, 11, 6, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 7, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaPlanoNormalSegundaTest() throws IOException {
        listaTrocaPlano = new HashMap<>();
        inicioExecucao = new DateTime(2016, 11, 7, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaPlanoEspecialRecorrenteNaDataTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 25, 7, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void saidaPlanoEspecialRecorrenteNaDataTest() throws IOException {
        inicioExecucao = new DateTime(2016, 12, 25, 23, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void planoEspecialRecorrenteNaDataEmOutroAnoTest() throws IOException {
        inicioExecucao = new DateTime(2017, 12, 25, 7, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaPlanoEspecialNaoRecorrenteTest() throws IOException {
        inicioExecucao = new DateTime(2017, 3, 15, 7, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void saidaPlanoEspecialNaoRecorrenteTest() throws IOException {
        inicioExecucao = new DateTime(2017, 3, 15, 23, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void planoEspecialNaoRecorrenteNaoDeveEntrarEmOutroAnoTest() throws IOException {
        listaTrocaPlano = new HashMap<>();
        inicioExecucao = new DateTime(2018, 3, 15, 7, 59, 59);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarMinutos(motor, 1);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 5, listaTrocaPlano.get(inicioExecucao.plusSeconds(1)).getPosicaoPlano().intValue());
    }

    @Test
    public void comAlteracaoTabelaHorariaImediata() throws IOException {
        Controlador controladorComMudanca = new ControladorHelper().setPlanosComTabelaHorariaMicro2(new ControladorHelper().getControlador());
        listaTrocaPlano = new HashMap<>();
        inicioExecucao = new DateTime(2018, 3, 15, 7, 59, 50);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarSegundos(motor, 5);
        motor.setControladorTemporario(controladorComMudanca);
        motor.onMudancaTabelaHoraria();
        avancarSegundos(motor, 10);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao.plusSeconds(5)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 7, listaTrocaPlano.get(inicioExecucao.plusSeconds(10)).getPosicaoPlano().intValue());

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());
    }

    @Test
    public void comAlteracaoTabelaHorariaNaProximaTroca() throws IOException {
        Controlador controladorComMudanca = new ControladorHelper().setPlanosComTabelaHorariaMicro2(new ControladorHelper().getControlador());
        listaTrocaPlano = new HashMap<>();
        inicioExecucao = new DateTime(2018, 3, 15, 7, 59, 50);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarSegundos(motor, 5);
        motor.setControladorTemporario(controladorComMudanca);
        avancarSegundos(motor, 10);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 7, listaTrocaPlano.get(inicioExecucao.plusSeconds(10)).getPosicaoPlano().intValue());

        assertEquals("Total de trocas", 2, listaTrocaPlano.size());
    }

    @Test
    @Ignore
    public void testPerfomanceDiaInteiro() throws IOException {
        inicioExecucao = new DateTime(2017, 11, 11, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        //Leonardo: 11/11 -> 6m 28s 964ms
    }
}
