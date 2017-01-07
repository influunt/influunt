package execucao.gerenciadorEstagios;

import execucao.GerenciadorDeEstagiosTest;
import integracao.ControladorHelper;
import models.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosModoManualTest extends GerenciadorDeEstagiosTest {

    @Test
    public void repeticaoDeEstagio() {
        Anel anel = getAnel(1);
        anel.setAceitaModoManual(true);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(1, plano, new DateTime(2016, 10, 10, 2, 0, 0));

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 15);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(120)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(138)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void limitePermanenciaEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        anel.setAceitaModoManual(true);

        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(1, plano, new DateTime(2016, 10, 10, 2, 0, 0));

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(176)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(198)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(216)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(234)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void comPlanoExclusivo() {
        Anel anel = getAnel(1);
        anel.setAceitaModoManual(true);

        geraPlanoExclusivo(anel);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(1, plano, new DateTime(2016, 10, 10, 2, 0, 0));

        Estagio estagio = anel.findEstagioByPosicao(3);
        estagio.setTempoMaximoPermanencia(100);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);

        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 140);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(178)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(188)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(206)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(228)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(246)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioSemTroca() {
        Anel anel = getAnel(2);
        anel.setAceitaModoManual(true);
        Plano plano = getPlano(anel, 11);

        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(2, plano, new DateTime(2016, 10, 10, 8, 0, 0));

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 300);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(300)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavel() {
        Anel anel = getAnel(2);
        anel.setAceitaModoManual(true);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(2, plano, new DateTime(2016, 10, 10, 8, 0, 0));

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 85);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 48);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 63);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(95)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(108)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(148)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(166)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(216)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaDoModoManual() {
        Anel anel = getAnel(1);
        anel.setAceitaModoManual(true);

        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(1, plano, inicioExecucao);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        desativarModoManual(gerenciadorDeEstagios, plano);
        avancar(gerenciadorDeEstagios, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(120)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(142)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaModoManualComDispensavel() {
        Anel anel = getAnel(2);
        anel.setAceitaModoManual(true);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(2, plano, inicioExecucao);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 85);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        desativarModoManual(gerenciadorDeEstagios, plano);
        avancar(gerenciadorDeEstagios, 48);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(95)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(108)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(128)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(147)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(165)).getEstagio().getPosicao().intValue());
    }

    private void geraPlanoExclusivo(Anel anel) {
        Plano plano = new ControladorHelper().criarPlano(anel, 0, ModoOperacaoPlano.MANUAL, null);

        plano.setEstagiosPlanos(null);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(1);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(3));
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(2);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(1));
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(3);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(2));
        plano.addEstagios(estagioPlano);
    }
}
