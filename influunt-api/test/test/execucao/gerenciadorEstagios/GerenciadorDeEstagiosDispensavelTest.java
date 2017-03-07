package test.execucao.gerenciadorEstagios;

import engine.EventoMotor;
import engine.TipoEvento;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.junit.Test;
import test.execucao.GerenciadorDeEstagiosTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosDispensavelTest extends GerenciadorDeEstagiosTest {

    @Test
    public void repeticaoDeEstagioComDispensavelSemExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(55)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelComExecucaoNoMeio() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 89);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(32)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(54)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(72)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(91)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(109)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(128)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(146)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(160)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(182)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void solicitacaoDoEstagioNoSeuEntreVerde() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        //Dois chamadas ignora uma
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));

        //Chamada durante o entreverde do estagio anterior para estágio dispensavel deve ignorar
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));

        //Chamada durante o verde do estágio dispensavel deve ignorar
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));

        //Chamada no seu entreverde deve executar no proximo ciclo
        avancar(gerenciadorDeEstagios, 3);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));

        avancar(gerenciadorDeEstagios, 100);


        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(32)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(54)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(72)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(108)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(126)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelComExecucaoNoFim() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 10);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Pair<Integer, TipoDetector> detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 90);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel.getPosicao()));
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(40)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(61)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(81)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(103)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(124)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(144)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(166)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(184)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void estagioDuploSendoUmDispensavel() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 11);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 2);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L),
            TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE,
            new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo()),
            1));
        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(23)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(33)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(65)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void doisEstagiosSendoUmDispensavel() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 13);

        EstagioPlano estagioPlano = getEstagioPlano(plano, 1);
        estagioPlano.setDispensavel(true);

        Estagio estagio = anel.findEstagioByPosicao(2);
        estagio.setTempoMaximoPermanenciaAtivado(false);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(6, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(10, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(7, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(9, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 8000, 18000, EstadoGrupoSemaforico.VERDE));
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(1)));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
    }

}
