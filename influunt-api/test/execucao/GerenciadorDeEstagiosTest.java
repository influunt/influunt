package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.*;
import engine.TipoEvento;
import integracao.ControladorHelper;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static engine.TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR;
import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends WithInfluuntApplicationNoAuthentication{

    @Test
    public void estagioDispensavelParaTrasNoMeio() {
        DateTime inicioControlador = new DateTime(2016,10,10,0,0,0);
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

        avancar(gerenciadorDeEstagios, 20);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(50000L), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector));
        avancar(gerenciadorDeEstagios, 170);

        imprimirListaEstagios(listaEstagios);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(54)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(67)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(85)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(103)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(121)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void estagioDispensavelParaFrenteNoMeio() {
        DateTime inicioControlador = new DateTime(2016,10,10,0,0,0);
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

    @Test
    public void estagioDispensavelParaFrenteNoFim() {
        DateTime inicioControlador = new DateTime(2016,10,10,0,0,0);
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

    @Test
    public void estagioDispensavelParaTrasNoInicio() {
        DateTime inicioControlador = new DateTime(2016,10,10,0,0,0);
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

    @Test
    public void estagioDemandaPrioritaria() {
        DateTime inicioControlador = new DateTime(2016,10,10,0,0,0);
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
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss");
        listaEstagios.entrySet().stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(entry -> System.out.println(sdf.print(entry.getKey()) + " - " + entry.getValue().getEstagio().getPosicao()));
    }

    private void avancar(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        while (i-- > 0){
            gerenciadorDeEstagios.tick();
        }
    }


}
