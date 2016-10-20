package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.*;
import engine.TipoEvento;
import integracao.ControladorHelper;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends WithInfluuntApplicationNoAuthentication {

    DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    DateTime inicioExecucao = inicioControlador;

    Controlador controlador;

    HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios;

    HashMap<DateTime, IntervaloGrupoSemaforico> listaHistoricoEstagios;

    GerenciadorDeEstagios gerenciadorDeEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Test
    public void repeticaoDeEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 100);

        plano.imprimirTabelaEntreVerde();

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

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1,6000,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2,0,2000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(2,2000,5000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(0, new GrupoCheck(2,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4,0,8000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(0, new GrupoCheck(5,0,8000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1,3000,6000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4,0,6000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(18, new GrupoCheck(4,6000,16000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(18, new GrupoCheck(5,0,6000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(18, new GrupoCheck(5,6000,16000,EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(104)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelSemExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 100);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(55)).getEstagio().getPosicao().intValue());
    }

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
    public void repeticaoDeEstagioComDispensavelComExecucaoNoMeio() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 89);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 100);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

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
    public void repeticaoDeEstagioComDispensavelComExecucaoNoFim() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 10);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 90);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 100);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

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
    public void repeticaoDeEstagioComDispensavelComExecucaoNoInicio() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 12);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 90);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 100);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(22)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(40)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(59)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(84)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(102)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(121)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(146)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(164)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(186)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelComExecucaoNoInicioEmModoCoordenadoEstagioAnterior() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 12);
        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        EstagioPlano estagioPlano = plano.getEstagiosPlanos().stream().filter(EstagioPlano::isDispensavel).findFirst().get();
        EstagioPlano estagioPlano3 = plano.getEstagiosPlanos().stream().filter(ep -> ep.getEstagio().getPosicao().equals(1)).findFirst().get();
        estagioPlano.setEstagioQueRecebeEstagioDispensavel(estagioPlano3);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 90);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 150);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(6,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(0, new GrupoCheck(6,3000,7000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(6,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(7,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(7,7000,22000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(8,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(8,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(9,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(9,7000,22000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(10,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(10,3000,7000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(10,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(22)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(22, new GrupoCheck(6,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(6,8000,40000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(22, new GrupoCheck(7,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(22, new GrupoCheck(7,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(22, new GrupoCheck(7,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(8,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(8,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(9,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(22, new GrupoCheck(9,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(22, new GrupoCheck(9,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(10,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(10,8000,40000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(62)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(62, new GrupoCheck(6,0,4000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(62, new GrupoCheck(6,4000,9000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(62, new GrupoCheck(6,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(7,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(7,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(8,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(8,9000,19000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(62, new GrupoCheck(9,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(9,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(10,0,4000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(62, new GrupoCheck(10,4000,9000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(62, new GrupoCheck(10,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(81)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(81, new GrupoCheck(6,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(6,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(81, new GrupoCheck(7,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(7,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(81, new GrupoCheck(8,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(81, new GrupoCheck(8,5000,10000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(81, new GrupoCheck(8,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(81, new GrupoCheck(9,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(9,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(81, new GrupoCheck(10,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(10,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(106)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(106, new GrupoCheck(6,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(6,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(106, new GrupoCheck(7,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(106, new GrupoCheck(7,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(106, new GrupoCheck(7,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(8,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(8,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(9,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(106, new GrupoCheck(9,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(106, new GrupoCheck(9,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(10,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(10,8000,18000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(124)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(124, new GrupoCheck(6,0,4000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(124, new GrupoCheck(6,4000,9000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(124, new GrupoCheck(6,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(7,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(7,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(8,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(8,9000,19000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(124, new GrupoCheck(9,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(9,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(10,0,4000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(124, new GrupoCheck(10,4000,9000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(124, new GrupoCheck(10,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(143)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(143, new GrupoCheck(6,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(6,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(7,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(7,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(8,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(143, new GrupoCheck(8,5000,10000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(143, new GrupoCheck(8,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(9,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(9,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(10,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(10,10000,25000,EstadoGrupoSemaforico.VERMELHO));verificaGruposSemaforicos(81, new GrupoCheck(6,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(6,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(7,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(7,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(8,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(143, new GrupoCheck(8,5000,10000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(143, new GrupoCheck(8,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(9,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(9,10000,25000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(10,0,10000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(10,10000,25000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(168)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(168, new GrupoCheck(6,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(6,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(168, new GrupoCheck(7,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(168, new GrupoCheck(7,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(168, new GrupoCheck(7,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(8,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(8,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(9,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(168, new GrupoCheck(9,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(168, new GrupoCheck(9,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(10,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(10,8000,18000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(186)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(186, new GrupoCheck(6,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(186, new GrupoCheck(6,3000,7000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(186, new GrupoCheck(6,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(186, new GrupoCheck(7,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(7,7000,22000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(186, new GrupoCheck(8,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(8,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(186, new GrupoCheck(9,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(9,7000,22000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(186, new GrupoCheck(10,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(186, new GrupoCheck(10,3000,7000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(186, new GrupoCheck(10,7000,22000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(208)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(208, new GrupoCheck(6,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(6,8000,40000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(208, new GrupoCheck(7,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(208, new GrupoCheck(7,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(208, new GrupoCheck(7,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(8,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(8,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(9,0,3000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(208, new GrupoCheck(9,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(208, new GrupoCheck(9,8000,40000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(10,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(10,8000,40000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(248)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelComExecucaoNoInicioEmModoCoordenadoProximoEstagio() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 12);
        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        EstagioPlano estagioPlano = plano.getEstagiosPlanos().stream().filter(EstagioPlano::isDispensavel).findFirst().get();
        EstagioPlano estagioPlano3 = getEstagioPlano(plano, 2);
        estagioPlano.setEstagioQueRecebeEstagioDispensavel(estagioPlano3);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 90);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 150);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(44)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(62)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(81)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(106)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(124)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(143)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(168)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(186)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(230)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(248)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 210);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 44);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 500);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(57)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(82)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(132)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(160)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(182)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(210)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(227)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(266)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(286)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaEDispensavelComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritariaEDispensavel(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);
        Detector detector2 = getDetector(anel, 2);

        avancar(gerenciadorDeEstagios, 70);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 164);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
//TODO:Tratar eventos iguais e simuntaneos
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
        avancar(gerenciadorDeEstagios, 500);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 8000, 28000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(8, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 8000, 28000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
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

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(125)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(150)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(168)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(193)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(221)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(243)).getEstagio().getPosicao().intValue());
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

    private void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    private void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios,inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    private void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
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

        avancar(gerenciadorDeEstagios, 87);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector2));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector2));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancar(gerenciadorDeEstagios, 20);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(11,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(11,11000,21000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(12,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(12,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(13,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(13,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(14,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(14,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(14,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(15,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(15,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(15,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(16,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(16,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(21, new GrupoCheck(11,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(21, new GrupoCheck(11,3000,7000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(21, new GrupoCheck(11,7000,17000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(21, new GrupoCheck(12,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(21, new GrupoCheck(12,7000,17000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(21, new GrupoCheck(13,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(21, new GrupoCheck(13,7000,17000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(21, new GrupoCheck(14,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(21, new GrupoCheck(14,7000,17000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(21, new GrupoCheck(15,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(21, new GrupoCheck(15,7000,17000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(21, new GrupoCheck(16,0,7000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(21, new GrupoCheck(16,7000,17000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(38, new GrupoCheck(11,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(38, new GrupoCheck(11,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(38, new GrupoCheck(12,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(38, new GrupoCheck(12,3000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(38, new GrupoCheck(12,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(38, new GrupoCheck(13,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(38, new GrupoCheck(13,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(38, new GrupoCheck(14,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(38, new GrupoCheck(14,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(38, new GrupoCheck(15,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(38, new GrupoCheck(15,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(38, new GrupoCheck(16,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(38, new GrupoCheck(16,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(56, new GrupoCheck(11,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(56, new GrupoCheck(11,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(56, new GrupoCheck(12,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(56, new GrupoCheck(12,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(56, new GrupoCheck(13,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(56, new GrupoCheck(13,3000,9000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(56, new GrupoCheck(13,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(56, new GrupoCheck(14,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(56, new GrupoCheck(14,9000,19000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(56, new GrupoCheck(15,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(56, new GrupoCheck(15,9000,19000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(56, new GrupoCheck(16,0,9000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(56, new GrupoCheck(16,9000,19000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(75)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(75, new GrupoCheck(11,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(11,11000,21000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(75, new GrupoCheck(12,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(12,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(13,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(13,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(14,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(14,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(14,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(15,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(15,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(15,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(16,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(16,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        //No Final
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(11,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(11,11000,25400,EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(12,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(12,11000,25400,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(13,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(13,11000,25400,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(14,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(14,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(14,11000,25400,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(15,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(15,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(15,11000,25400,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(16,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(100, 400, new GrupoCheck(16,11000,25400,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(100).plus(400)).getEstagio().getPosicao().intValue());
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

        avancar(gerenciadorDeEstagios, 87);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector2));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector3));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector4));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancar(gerenciadorDeEstagios, 20);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(75)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(75, new GrupoCheck(11,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(11,11000,21000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(75, new GrupoCheck(12,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(12,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(13,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(13,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(14,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(14,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(14,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(15,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(15,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(15,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(16,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(16,11000,21000,EstadoGrupoSemaforico.VERMELHO));

        //No Final
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(11,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(11,11000,23200,EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(12,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(12,11000,23200,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(13,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(13,11000,23200,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(14,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(14,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(14,11000,23200,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(15,0,6000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(15,6000,11000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(15,11000,23200,EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(16,0,11000,EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(98, 200, new GrupoCheck(16,11000,23200,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(98).plus(200)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void planoComAtrasoDeGrupoGanhoEPerdaDePassagem() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 1);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 105);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1,6000,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2,0,2000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(2,2000,5000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(0, new GrupoCheck(2,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(4,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(5,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1,3000,6000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(4,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(5,0,6000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(18, new GrupoCheck(5,6000,16000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(34, new GrupoCheck(1,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(2,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(3,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(3,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(4,0,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(34, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(5,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(5,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(52, new GrupoCheck(1,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(1,6000,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(2,0,2000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(2,2000,5000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(52, new GrupoCheck(2,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(3,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(4,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(52, new GrupoCheck(4,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(5,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.VERDE));

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
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 105);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(1,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(2,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(4,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(4,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(5,0,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(18, new GrupoCheck(1,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(18, new GrupoCheck(1,3000,6000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(18, new GrupoCheck(1,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(2,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(2,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(18, new GrupoCheck(3,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(3,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(4,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(18, new GrupoCheck(4,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(18, new GrupoCheck(5,0,6000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(18, new GrupoCheck(5,6000,16000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(34)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(34, new GrupoCheck(1,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(1,8000,18000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(34, new GrupoCheck(2,0,8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(34, new GrupoCheck(2,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(3,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(3,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(3,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(4,0,5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(34, new GrupoCheck(4,5000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(34, new GrupoCheck(4,8000,18000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(34, new GrupoCheck(5,0,8000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(34, new GrupoCheck(5,8000,18000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(52)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(52, new GrupoCheck(1,0,3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(52, new GrupoCheck(1,3000,6000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(52, new GrupoCheck(1,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(2,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(2,6000,16000,EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(52, new GrupoCheck(3,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(3,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(4,0,6000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(52, new GrupoCheck(4,6000,16000,EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(52, new GrupoCheck(5,0,6000,EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(52, new GrupoCheck(5,6000,16000,EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(68)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioModoIntermitente() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 5);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);

        avancar(gerenciadorDeEstagios, 300);

        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(255)).getEstagio().getPosicao());
        verificaGruposSemaforicos(255, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(255, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
    }

    @Test
    public void repeticaoDeEstagioModoIntermitenteComDemandaPrioritaria() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 5);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Detector detector = getDetector(anel, 4);

        avancar(gerenciadorDeEstagios, 100);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);


        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(100, new GrupoCheck(11,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(11,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16,3000,33000,EstadoGrupoSemaforico.VERDE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(133)).getEstagio().getPosicao());
//        verificaGruposSemaforicos(133, new GrupoCheck(11,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(11,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
//        verificaGruposSemaforicos(133, new GrupoCheck(12,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(12,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
//        verificaGruposSemaforicos(133, new GrupoCheck(13,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(13,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
//        verificaGruposSemaforicos(133, new GrupoCheck(14,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(14,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
//        verificaGruposSemaforicos(133, new GrupoCheck(15,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(15,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,0,4000,EstadoGrupoSemaforico.AMARELO));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,4000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,8000,255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
    }

    @Test
    public void repeticaoDeEstagioModoApagado() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 6);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);

        avancar(gerenciadorDeEstagios, 300);

        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(255, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.DESLIGADO));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(255)).getEstagio().getPosicao());
        verificaGruposSemaforicos(255, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(255, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void repeticaoDeEstagioModoApagadoComDemandaPrioritaria() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 6);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(3, plano);
        Detector detector = getDetector(anel, 4);

        avancar(gerenciadorDeEstagios, 100);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);


        plano.imprimirTabelaEntreVerde();

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao).getEstagio().getPosicao());
        verificaGruposSemaforicos(0, new GrupoCheck(11,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(12,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(13,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(14,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(15,0,255000,EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(16,0,255000,EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Estagio atual", 5, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(100, new GrupoCheck(11,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(11,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(12,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(13,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(14,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(15,3000,33000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16,0,3000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(100, new GrupoCheck(16,3000,33000,EstadoGrupoSemaforico.VERDE));

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(133)).getEstagio().getPosicao());
//        verificaGruposSemaforicos(133, new GrupoCheck(11,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(11,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
//        verificaGruposSemaforicos(133, new GrupoCheck(12,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(12,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
//        verificaGruposSemaforicos(133, new GrupoCheck(13,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(13,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
//        verificaGruposSemaforicos(133, new GrupoCheck(14,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(14,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
//        verificaGruposSemaforicos(133, new GrupoCheck(15,0,8000,EstadoGrupoSemaforico.VERMELHO));
//        verificaGruposSemaforicos(133, new GrupoCheck(15,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,0,4000,EstadoGrupoSemaforico.AMARELO));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,4000,8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
//        verificaGruposSemaforicos(133, new GrupoCheck(16,8000,255000,EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void reducaoDeEstagioComGrupoSemaforicoVeicularEPedestre() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 2);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void estagioDuploSemUmDispensavel() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 10);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 2);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 100);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(23)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(33)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(37)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(65)).getEstagio().getPosicao().intValue());
    }

    @NotNull
    private GerenciadorDeEstagios getGerenciadorDeEstagios(int anel, Plano plano) {
        return new GerenciadorDeEstagios(anel, inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {

            @Override
            public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
                listaEstagios.put(timestamp, intervalos);
            }

            @Override
            public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
                listaHistoricoEstagios.put(timestamp, intervalos);
            }

            @Override
            public void onCicloEnds(int anel, Long numeroCiclos) {

            }

            @Override
            public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

            }

        });
    }

    private Plano getPlano(Anel anel, int posicao) {
        return anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    private Plano getPlanoDemandaPrioritaria(Anel anel) {
        Plano plano = new ControladorHelper().criarPlano(anel, 13, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 32);

        plano.setEstagiosPlanos(null);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(1);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(1));
        estagioPlano.setTempoVerde(20);
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(2);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(2));
        estagioPlano.setTempoVerde(15);
        plano.addEstagios(estagioPlano);

        Detector detector = anel.getDetectores().get(0);
        detector.setTipo(TipoDetector.VEICULAR);
        detector.setPosicao(1);
        detector.getEstagio().setDemandaPrioritaria(true);
        detector.getEstagio().setTempoVerdeDemandaPrioritaria(30);
        return plano;
    }

    private Plano getPlanoDemandaPrioritariaEDispensavel(Anel anel) {
        Plano plano = getPlanoDemandaPrioritaria(anel);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(3);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(1));
        estagioPlano.setTempoVerde(10);
        estagioPlano.setDispensavel(true);
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(4);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(2));
        estagioPlano.setTempoVerde(18);
        plano.addEstagios(estagioPlano);

        return plano;
    }

    private Anel getAnel(int posicao) {
        return controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    private Detector getDetector(Anel anel, int posicao) {
        return anel.getDetectores().stream().filter(det -> det.getPosicao().equals(posicao)).findFirst().get();
    }

    private EstagioPlano getEstagioPlano(Plano plano, int posicao) {
        return plano.getEstagiosPlanos().stream().filter(ep -> ep.getEstagio().getPosicao().equals(posicao)).findFirst().get();
    }

    private GrupoSemaforicoPlano getGrupoSemaforicoPlano(Plano plano, int posicao) {
        return plano.getGruposSemaforicosPlanos().stream().filter(ep -> ep.getGrupoSemaforico().getPosicao().equals(posicao)).findFirst().get();
    }

    private void imprimirListaEstagios(HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss:S");
        listaEstagios.entrySet().stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(entry -> System.out.println(sdf.print(entry.getKey()) + " - " + entry.getValue().getEstagio().getPosicao()));
    }

    private void avancar(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        i *= 10;
        while (i-- > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    private void avancarAtuado(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        while (i-- > 0) {
            gerenciadorDeEstagios.tick();
        }
    }


    private class GrupoCheck {

        private final int grupo;

        private final long inicio;

        private final long fim;

        private final EstadoGrupoSemaforico estado;

        public GrupoCheck(int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public void check(HashMap<DateTime, IntervaloGrupoSemaforico> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).getEstados().get(this.grupo).get(this.inicio));
        }
    }
}
