package execucao.gerenciadorPlanos;

import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeTrocasTest;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaPlanosComEstagioDispensavelTest extends GerenciadorDeTrocasTest {

    @Test
    public void trocaParaPlanoIsoladoComDispensavel() throws IOException {
        inicioControlador = new DateTime(2016, 11, 11, 1, 0, 0);
        inicioExecucao = inicioControlador;
        instante = inicioControlador;
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(2);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancarSegundos(motor, 20);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(20), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, 2));
        avancarSegundos(motor, 150);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlano.get(inicioExecucao.plusSeconds(30)).getPosicaoPlano().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, getPlanoTrocaEfetiva(2, 37).getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(55)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(77)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(118)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(140)).get(2).getEstagio().getPosicao().intValue());
    }

    @Test
    public void trocaParaPlanoIsoladoSemDispensavel() throws IOException {
        inicioControlador = new DateTime(2016, 11, 11, 12, 59, 30);
        inicioExecucao = inicioControlador;
        instante = inicioControlador;
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(2);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancarSegundos(motor, 20);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(20), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, 2));
        avancarSegundos(motor, 150);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusSeconds(30)).getPosicaoPlano().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 1, getPlanoTrocaEfetiva(2, 37).getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(55)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(76)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(96)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(114)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(135)).get(2).getEstagio().getPosicao().intValue());

    }


}
