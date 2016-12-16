package execucao.modoCoordenado;

import engine.Motor;
import execucao.GerenciadorDeTrocasTest;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ComEstagioDispensavelTest extends GerenciadorDeTrocasTest {


    @Test
    public void comEstagioDispensavelETransicaoProibida() {
        inicioExecucao = new DateTime(2016, 11, 16, 22, 59, 30, 0);
        instante = inicioExecucao;
        Motor motor = new Motor(controlador, inicioExecucao, this);

        avancarSegundos(motor, 500);

        assertEquals(6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals(4, listaTrocaPlano.get(inicioExecucao.plusSeconds(30)).getPosicaoPlano().intValue());

        assertEquals(4, getPlanoTrocaEfetiva(1, 47).getPosicao().intValue());
        assertEquals(4, getPlanoTrocaEfetiva(2, 31).getPosicao().intValue());

        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(47)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(65)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(75)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(93)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(111)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(121)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(139)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(157)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(167)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(185)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(203)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(213)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(231)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(249)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(266)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(284)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(304)).get(1).getEstagio().getPosicao().intValue());
        assertEquals(3, listaEstagios.get(inicioExecucao.plusSeconds(324)).get(1).getEstagio().getPosicao().intValue());

        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(31)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(51)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(85)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(102)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(143)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(160)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(201)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(218)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(259)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(276)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(2, listaEstagios.get(inicioExecucao.plusSeconds(317)).get(2).getEstagio().getPosicao().intValue());
        assertEquals(1, listaEstagios.get(inicioExecucao.plusSeconds(334)).get(2).getEstagio().getPosicao().intValue());
    }


}
