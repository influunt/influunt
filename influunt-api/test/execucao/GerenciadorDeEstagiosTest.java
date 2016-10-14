package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.GerenciadorDeEstagiosCallback;
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


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends WithInfluuntApplicationNoAuthentication {

    DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    DateTime inicioExecucao = inicioControlador;

    Controlador controlador;

    HashMap<DateTime, EstagioPlano> listaEstagios;

    GerenciadorDeEstagios gerenciadorDeEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaEstagios = new HashMap<>();
    }

    @Test
    public void repeticaoDeEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);

        avancar(gerenciadorDeEstagios, 100);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(76)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDispensavelSemExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);

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

        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);

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
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

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
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

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
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

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
    public void repeticaoDeEstagioComDemandaPrioritariaComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

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
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();
        Detector detector2 = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(2)).findFirst().get();

        avancar(gerenciadorDeEstagios, 70);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 164);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
        avancar(gerenciadorDeEstagios, 500);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(50)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(68)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).getEstagio().getPosicao().intValue());
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


    @Test
    public void repeticaoDeEstagioAtuadoComDemandaPrioritariaEDispensavelComExecucao() {
        Anel anel = getAnel(3);
        Plano plano = getPlano(anel, 1);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);
        Detector detector1 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();
        Detector detector2 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(2)).findFirst().get();
        Detector detector3 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(3)).findFirst().get();
        Detector detector4 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(4)).findFirst().get();

        Detector detector5 = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();

//        avancar(gerenciadorDeEstagios, 70);
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
//        avancar(gerenciadorDeEstagios, 10);
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
//        avancar(gerenciadorDeEstagios, 164);
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector2));
        avancar(gerenciadorDeEstagios, 87);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancarAtuado(gerenciadorDeEstagios, 1);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancarAtuado(gerenciadorDeEstagios, 1);
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
        avancar(gerenciadorDeEstagios, 20);
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector1));
//        avancarAtuado(gerenciadorDeEstagios, 1000);
        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(75)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(96).plus(300)).getEstagio().getPosicao().intValue());
    }

    @NotNull
    private GerenciadorDeEstagios getGerenciadorDeEstagios(Plano plano) {
        return new GerenciadorDeEstagios(inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {
            @Override
            public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo) {
                listaEstagios.put(timestamp, estagioPlanoNovo);
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

    @Test
    public void estagioDispensavelParaTrasNoMeio() {
        Anel anel = getAnel(2);
        Plano plano = getPlano(anel, 11);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(plano);

        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

//        avancar(gerenciadorDeEstagios, 20);
//        System.out.println(gerenciadorDeEstagios.getIntervalos());
//        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
//        avancar(gerenciadorDeEstagios, 16);
//        System.out.println(gerenciadorDeEstagios.getIntervalos());
        avancar(gerenciadorDeEstagios, 100);

        imprimirListaEstagios(listaEstagios);

//        plano.imprimirTabelaEntreVerde();

//        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(35)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(55)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(88)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(106)).getEstagio().getPosicao().intValue());
//        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(124)).getEstagio().getPosicao().intValue());
    }

    @Ignore
    @Test
    public void estagioDispensavelParaFrenteNoMeio() {
        DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);
        DateTime inicioExecucao = inicioControlador;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        HashMap<DateTime, EstagioPlano> listaEstagios = new HashMap<>();

        Anel anel = controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(2))
                .findFirst()
                .get();
        Plano plano = anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(11))
                .findFirst()
                .get();

        GerenciadorDeEstagios gerenciadorDeEstagios = new GerenciadorDeEstagios(inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {
            @Override
            public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo) {
                listaEstagios.put(timestamp, estagioPlanoNovo);
            }
        });

        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

        avancar(gerenciadorDeEstagios, 10);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 180);

        imprimirListaEstagios(listaEstagios);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(31)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(49)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(67)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(85)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(103)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(121)).getEstagio().getPosicao().intValue());
    }

    @Ignore
    @Test
    public void estagioDispensavelParaFrenteNoFim() {
        DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);
        DateTime inicioExecucao = inicioControlador;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        HashMap<DateTime, EstagioPlano> listaEstagios = new HashMap<>();

        Anel anel = controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(2))
                .findFirst()
                .get();
        Plano plano = anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(10))
                .findFirst()
                .get();

        GerenciadorDeEstagios gerenciadorDeEstagios = new GerenciadorDeEstagios(inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {
            @Override
            public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo) {
                listaEstagios.put(timestamp, estagioPlanoNovo);
            }
        });

        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 180);

        imprimirListaEstagios(listaEstagios);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(31)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(49)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(67)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(80)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(98)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(111)).getEstagio().getPosicao().intValue());
    }

    @Ignore
    @Test
    public void estagioDispensavelParaTrasNoInicio() {
        DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);
        DateTime inicioExecucao = inicioControlador;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        HashMap<DateTime, EstagioPlano> listaEstagios = new HashMap<>();

        Anel anel = controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(2))
                .findFirst()
                .get();
        Plano plano = anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(12))
                .findFirst()
                .get();

        GerenciadorDeEstagios gerenciadorDeEstagios = new GerenciadorDeEstagios(inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {
            @Override
            public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo) {
                listaEstagios.put(timestamp, estagioPlanoNovo);
            }
        });

        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(1)).findFirst().get();

        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 180);

        imprimirListaEstagios(listaEstagios);

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(13)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(31)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(49)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(62)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(80)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(93)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(111)).getEstagio().getPosicao().intValue());
    }

    @Ignore
    @Test
    public void estagioDemandaPrioritaria() {
        DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);
        DateTime inicioExecucao = inicioControlador;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        HashMap<DateTime, EstagioPlano> listaEstagios = new HashMap<>();

        Anel anel = controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(2))
                .findFirst()
                .get();
        //Plano com estágio 2 dispensavel no inicio
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
        detector.getEstagio().setDemandaPrioritaria(true);
        detector.getEstagio().setTempoVerdeDemandaPrioritaria(30);

        GerenciadorDeEstagios gerenciadorDeEstagios = new GerenciadorDeEstagios(inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {
            @Override
            public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo) {
                listaEstagios.put(timestamp, estagioPlanoNovo);
            }
        });

        avancar(gerenciadorDeEstagios, 2);
        System.out.println(gerenciadorDeEstagios.getIntervalos());
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        System.out.println(gerenciadorDeEstagios.getIntervalos());
        avancar(gerenciadorDeEstagios, 179);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        avancar(gerenciadorDeEstagios, 100);

        imprimirListaEstagios(listaEstagios);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(56)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(79)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(107)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(130)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(158)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(181)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(219)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(247)).getEstagio().getPosicao().intValue());
    }

    private void imprimirListaEstagios(HashMap<DateTime, EstagioPlano> listaEstagios) {
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

}
