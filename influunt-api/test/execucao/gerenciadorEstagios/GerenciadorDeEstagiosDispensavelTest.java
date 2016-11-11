package execucao.gerenciadorEstagios;

import engine.EventoMotor;
import engine.TipoEvento;
import execucao.GerenciadorDeEstagiosTest;
import integracao.ControladorHelper;
import models.*;
import org.junit.Test;

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

        plano.imprimirTabelaEntreVerde();

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
    public void solicitacaoDoEstagioNoSeuEntreVerde() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        //Dois chamadas ignora uma
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));

        //Chamada durante o entreverde do estagio anterior para estágio dispensavel deve ignorar
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));

        //Chamada durante o verde do estágio dispensavel deve ignorar
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));

        //Chamada no seu entreverde deve executar no proximo ciclo
        avancar(gerenciadorDeEstagios, 3);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));

        avancar(gerenciadorDeEstagios, 100);

        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

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
        verificaGruposSemaforicos(0, new GrupoCheck(6, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(6, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(7, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(8, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(8, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(9, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(10, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(22)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(22, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(6, 8000, 40000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(22, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(22, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(22, new GrupoCheck(7, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(8, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(22, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(22, new GrupoCheck(9, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(22, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(22, new GrupoCheck(10, 8000, 40000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(62)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(62, new GrupoCheck(6, 0, 4000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(62, new GrupoCheck(6, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(62, new GrupoCheck(6, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(7, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(7, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(8, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(8, 9000, 19000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(62, new GrupoCheck(9, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(62, new GrupoCheck(9, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(62, new GrupoCheck(10, 0, 4000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(62, new GrupoCheck(10, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(62, new GrupoCheck(10, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(81)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(81, new GrupoCheck(6, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(6, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(81, new GrupoCheck(7, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(7, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(81, new GrupoCheck(8, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(81, new GrupoCheck(8, 5000, 10000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(81, new GrupoCheck(8, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(81, new GrupoCheck(9, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(9, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(81, new GrupoCheck(10, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(10, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(106)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(106, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(6, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(106, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(106, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(106, new GrupoCheck(7, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(8, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(106, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(106, new GrupoCheck(9, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(106, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(106, new GrupoCheck(10, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(124)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(124, new GrupoCheck(6, 0, 4000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(124, new GrupoCheck(6, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(124, new GrupoCheck(6, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(7, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(7, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(8, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(8, 9000, 19000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(124, new GrupoCheck(9, 0, 9000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(124, new GrupoCheck(9, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(124, new GrupoCheck(10, 0, 4000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(124, new GrupoCheck(10, 4000, 9000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(124, new GrupoCheck(10, 9000, 19000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(143)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(143, new GrupoCheck(6, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(6, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(7, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(7, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(8, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(143, new GrupoCheck(8, 5000, 10000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(143, new GrupoCheck(8, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(9, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(9, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(10, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(10, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(81, new GrupoCheck(6, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(6, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(7, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(7, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(8, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(143, new GrupoCheck(8, 5000, 10000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(143, new GrupoCheck(8, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(143, new GrupoCheck(9, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(9, 10000, 25000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(143, new GrupoCheck(10, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(143, new GrupoCheck(10, 10000, 25000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(168)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(168, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(6, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(168, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(168, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(168, new GrupoCheck(7, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(8, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(168, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(168, new GrupoCheck(9, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(168, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(168, new GrupoCheck(10, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(186)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(186, new GrupoCheck(6, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(186, new GrupoCheck(6, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(186, new GrupoCheck(6, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(186, new GrupoCheck(7, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(7, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(186, new GrupoCheck(8, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(8, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(186, new GrupoCheck(9, 0, 7000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(186, new GrupoCheck(9, 7000, 22000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(186, new GrupoCheck(10, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(186, new GrupoCheck(10, 3000, 7000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(186, new GrupoCheck(10, 7000, 22000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(208)).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(208, new GrupoCheck(6, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(6, 8000, 40000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(208, new GrupoCheck(7, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(208, new GrupoCheck(7, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(208, new GrupoCheck(7, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(8, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(8, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(9, 0, 3000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(208, new GrupoCheck(9, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(208, new GrupoCheck(9, 8000, 40000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(208, new GrupoCheck(10, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(208, new GrupoCheck(10, 8000, 40000, EstadoGrupoSemaforico.VERDE));

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
    public void estagioDuploSendoUmDispensavel() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 11);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

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

    @Test
    public void doisEstagiosSendoUmDispensavel() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 13);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);

        avancar(gerenciadorDeEstagios, 100);

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(6, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(10, 0, 10000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(7, 0, 10000, EstadoGrupoSemaforico.VERDE));
        verificaGruposSemaforicos(0, new GrupoCheck(9, 0, 10000, EstadoGrupoSemaforico.VERDE));
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(1)));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(10)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(20)).getEstagio().getPosicao().intValue());
    }

}