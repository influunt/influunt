package execucao.gerenciadorEstagios;

import engine.EventoMotor;
import engine.TipoEvento;
import execucao.GerenciadorDeEstagiosTest;
import integracao.ControladorHelper;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosGeralTest extends GerenciadorDeEstagiosTest {

    @Test
    public void repeticaoDeEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(76)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComGrupoApagado() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 1);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);
        GrupoSemaforicoPlano grupoSemaforicoPlano4 = getGrupoSemaforicoPlano(plano, 4);
        grupoSemaforicoPlano4.setAtivado(false);
        GrupoSemaforicoPlano grupoSemaforicoPlano5 = getGrupoSemaforicoPlano(plano, 5);
        grupoSemaforicoPlano5.setAtivado(false);

        avancar(gerenciadorDeEstagios, 105);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 5000, 8000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(0, new GrupoCheck(5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 5000, 8000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4, 0, 6000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(18, new GrupoCheck(4, 6000, 16000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(18, new GrupoCheck(5, 0, 6000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(18, new GrupoCheck(5, 6000, 16000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(104)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaEDispensavelComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritariaEDispensavel(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);
        Pair<Integer, TipoDetector> detector2 = getDetector(anel, 2);

        avancar(gerenciadorDeEstagios, 70);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(70), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(80), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 164);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(244), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector, anel.getPosicao()));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(244), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector, anel.getPosicao()));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plusSeconds(244), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 500);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(6, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 8000, 28000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(7, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(8, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(8, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(8, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(9, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(10, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 8000, 28000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(28, new GrupoCheck(6, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(28, new GrupoCheck(6, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(28, new GrupoCheck(6, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(28, new GrupoCheck(7, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(28, new GrupoCheck(7, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(28, new GrupoCheck(8, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(28, new GrupoCheck(8, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(28, new GrupoCheck(9, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(28, new GrupoCheck(9, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(28, new GrupoCheck(10, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(28, new GrupoCheck(10, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(28, new GrupoCheck(10, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(50)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(50, new GrupoCheck(6, 0, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(50, new GrupoCheck(7, 0, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(50, new GrupoCheck(8, 0, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(50, new GrupoCheck(9, 0, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(50, new GrupoCheck(10, 0, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(68)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(68, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(6, 8000, 28000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(68, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(68, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(68, new GrupoCheck(7, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(68, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(8, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(68, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(68, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(68, new GrupoCheck(9, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(68, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(10, 8000, 28000, EstadoGrupoSemaforico.VERDE));

        //Fim
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(6, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(7, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(8, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(9, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(86, new GrupoCheck(10, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(86, new GrupoCheck(6, 0, 4000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(86, new GrupoCheck(6, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(86, new GrupoCheck(6, 9000, 39000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(86, new GrupoCheck(7, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(86, new GrupoCheck(7, 9000, 39000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(86, new GrupoCheck(8, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(86, new GrupoCheck(8, 9000, 39000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(86, new GrupoCheck(9, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(86, new GrupoCheck(9, 9000, 39000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(86, new GrupoCheck(10, 0, 4000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(86, new GrupoCheck(10, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(86, new GrupoCheck(10, 9000, 39000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(125)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(155)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(177)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(195)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(223)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(244)).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(283)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(313)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(335)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(353)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(378)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(406)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(428)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(446)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioAtuadoComDemandaPrioritariaEDispensavelSemExecucao() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 1);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Detector detector1 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();
        Detector detector2 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(2)).findFirst().get();
        Detector detector3 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(3)).findFirst().get();
        Detector detector4 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(4)).findFirst().get();

        Detector detector5 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

        avancar(gerenciadorDeEstagios, 92);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector2.getPosicao(), detector2.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector3.getPosicao(), detector3.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector2.getPosicao(), detector2.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 20);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(11, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(11, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(11, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(12, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(13, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(14, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(15, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(16, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(11, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(11, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(11, 7000, 17000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(12, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(12, 7000, 17000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(13, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(13, 7000, 17000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(14, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(14, 7000, 17000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(15, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(15, 7000, 17000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(16, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(16, 7000, 17000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(35, new GrupoCheck(11, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(35, new GrupoCheck(11, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(35, new GrupoCheck(12, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(35, new GrupoCheck(12, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(35, new GrupoCheck(12, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(35, new GrupoCheck(13, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(35, new GrupoCheck(13, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(35, new GrupoCheck(14, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(35, new GrupoCheck(14, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(35, new GrupoCheck(15, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(35, new GrupoCheck(15, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(35, new GrupoCheck(16, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(35, new GrupoCheck(16, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(53)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(53, new GrupoCheck(11, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(53, new GrupoCheck(11, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(53, new GrupoCheck(12, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(53, new GrupoCheck(12, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(53, new GrupoCheck(13, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(53, new GrupoCheck(13, 3000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(53, new GrupoCheck(13, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(53, new GrupoCheck(14, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(53, new GrupoCheck(14, 9000, 19000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(53, new GrupoCheck(15, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(53, new GrupoCheck(15, 9000, 19000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(53, new GrupoCheck(16, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(53, new GrupoCheck(16, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(72)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(72, new GrupoCheck(11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(72, new GrupoCheck(12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        //No Final
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(11, 11000, 23100, EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(12, 11000, 23100, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(13, 11000, 23100, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(14, 11000, 23100, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(15, 11000, 23100, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(95, 100, new GrupoCheck(16, 11000, 23100, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(95).plus(100)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioAtuadoComDemandaPrioritariaEDispensavelComExecucao() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 1);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Detector detector1 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();
        Detector detector2 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(2)).findFirst().get();
        Detector detector3 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(3)).findFirst().get();
        Detector detector4 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(4)).findFirst().get();

        Detector detector5 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

        avancar(gerenciadorDeEstagios, 92);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector2.getPosicao(), detector2.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector3.getPosicao(), detector3.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector4.getPosicao(), detector4.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector1.getPosicao(), detector1.getTipo()),
            3));
        avancar(gerenciadorDeEstagios, 20);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(53)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(72)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(72, new GrupoCheck(11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(72, new GrupoCheck(12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        //No Final
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(93, new GrupoCheck(16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(93)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void planoComAtrasoDeGrupoGanhoEPerdaDePassagem() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 1);
        ControladorHelper controladorHelper = new ControladorHelper(controlador);
        controladorHelper.setAtrasoDeGrupo(anel, 1, 3, 1, 2);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 105);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(4, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(5, 0, 6000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(18, new GrupoCheck(5, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(34, new GrupoCheck(1, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(2, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(3, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(4, 0, 8000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(34, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(5, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(52, new GrupoCheck(1, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(1, 6000, 8000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(2, 0, 2000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(2, 2000, 5000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(52, new GrupoCheck(2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(3, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(4, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(52, new GrupoCheck(4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(5, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(104)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void planoComAtrasoDeGrupoGanhoEPerdaDePassagemComEstagioDispensavel() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControladorSemTransicaoProibida());
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 1);

        EstagioPlano estagioPlano = getEstagioPlano(plano, 3);
        estagioPlano.setDispensavel(true);
        Detector detector = new Detector();
        detector.setAnel(anel);
        detector.setControlador(anel.getControlador());
        detector.setTipo(TipoDetector.PEDESTRE);
        detector.setPosicao(1);
        detector.setMonitorado(false);
        Estagio estagio = estagioPlano.getEstagio();
        detector.setEstagio(estagio);
        estagio.setDetector(detector);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 105);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(4, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(5, 0, 6000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(18, new GrupoCheck(5, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(34, new GrupoCheck(1, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(2, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(3, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(4, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(5, 0, 8000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(34, new GrupoCheck(5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(52, new GrupoCheck(1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(52, new GrupoCheck(1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(1, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(2, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(2, 6000, 16000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(3, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(3, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(4, 0, 6000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(4, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(5, 0, 6000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(5, 6000, 16000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(68)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioModoIntermitente() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 5);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);

        avancar(gerenciadorDeEstagios, 300);

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(255)).getEstagio().getPosicao());
        verificaGruposSemaforicos(255, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
    }

    @Test
    public void repeticaoDeEstagioModoApagado() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 6);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);

        avancar(gerenciadorDeEstagios, 300);

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(255)).getEstagio().getPosicao());
        verificaGruposSemaforicos(255, new GrupoCheck(11, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(12, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(13, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(14, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(15, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(16, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void reducaoDeEstagioComGrupoSemaforicoVeicularEPedestre() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 2);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR,
            new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo()),
            2));
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
    }
}
