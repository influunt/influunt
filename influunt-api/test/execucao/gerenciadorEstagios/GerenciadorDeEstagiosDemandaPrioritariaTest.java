package execucao.gerenciadorEstagios;

import engine.EventoMotor;
import engine.TipoEvento;
import execucao.GerenciadorDeEstagiosTest;
import integracao.ControladorHelper;
import models.Anel;
import models.EstadoGrupoSemaforico;
import models.Plano;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosDemandaPrioritariaTest extends GerenciadorDeEstagiosTest {
    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaSemExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 101);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(50)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(78)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(10), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 210);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(220), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(240), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 44);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(284), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 42);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(326), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(57)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(87)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(109)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(137)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(159)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(187)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(227)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(266)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(286)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(325)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(345)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaComTransicaoProibida() {
        ControladorHelper controladorHelper = new ControladorHelper(controlador);
        Anel anel = getAnel(2);
        controladorHelper.criarTransicaoProibida(anel, 1, 3, 2);
        controlador = controladorHelper.getControlador();

        Plano plano = getPlanoDemandaPrioritaria(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(10), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 210);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioModoIntermitenteComDemandaPrioritaria() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 5);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 4);

        avancar(gerenciadorDeEstagios, 100);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);


        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(100, new GrupoCheck(11, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(11, 3000, 33000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12, 3000, 33000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13, 3000, 33000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14, 3000, 33000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15, 3000, 33000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16, 3000, 33000, EstadoGrupoSemaforico.VERDE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(133)).getEstagio().getPosicao());
        verificaGruposSemaforicos(133, new GrupoCheck(11, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(11, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(11, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(133, new GrupoCheck(12, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(12, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(12, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(133, new GrupoCheck(13, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(13, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(13, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(133, new GrupoCheck(14, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(14, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(14, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(133, new GrupoCheck(15, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(15, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(15, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(133, new GrupoCheck(16, 0, 4000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(133, new GrupoCheck(16, 4000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(133, new GrupoCheck(16, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(133, new GrupoCheck(16, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
    }

    @Test
    public void repeticaoDeEstagioModoApagadoComDemandaPrioritaria() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 6);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 4);

        avancar(gerenciadorDeEstagios, 100);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);


        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(100, new GrupoCheck(11, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(100, new GrupoCheck(11, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(11, 8000, 38000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(100, new GrupoCheck(12, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(100, new GrupoCheck(12, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12, 8000, 38000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(100, new GrupoCheck(13, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(100, new GrupoCheck(13, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13, 8000, 38000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(100, new GrupoCheck(14, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(100, new GrupoCheck(14, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14, 8000, 38000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(100, new GrupoCheck(15, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(100, new GrupoCheck(15, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15, 8000, 38000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(100, new GrupoCheck(16, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(100, new GrupoCheck(16, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16, 8000, 38000, EstadoGrupoSemaforico.VERDE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(138)).getEstagio().getPosicao());
        verificaGruposSemaforicos(138, new GrupoCheck(11, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(11, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(11, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(138, new GrupoCheck(12, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(12, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(12, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(138, new GrupoCheck(13, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(13, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(13, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(138, new GrupoCheck(14, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(14, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(14, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(138, new GrupoCheck(15, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(15, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(15, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(138, new GrupoCheck(16, 0, 4000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(138, new GrupoCheck(16, 4000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(138, new GrupoCheck(16, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(138, new GrupoCheck(16, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void repeticaoDeEstagioManualComDemandaPrioritariaComExecucao() {
        Anel anel = getAnel(2);
        anel.setAceitaModoManual(true);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        gerenciadorDeEstagios = getGerenciadorDeEstagiosComMotor(2, plano, new DateTime(2016, 10, 10, 0, 0, 0));

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 90);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 5);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 55);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 20);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 20);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 50);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 30);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 40);
        trocarEstagioModoManual(gerenciadorDeEstagios);
        avancar(gerenciadorDeEstagios, 200);

        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(50)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(117)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(156)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(176)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(193)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(211)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(250)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(270)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(300)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(340)).getEstagio().getPosicao().intValue());
    }
}
