package execucao.gerenciadorDeErros;

import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeTrocasTest;
import integracao.ControladorHelper;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeFalhasTest extends GerenciadorDeTrocasTest {


    @Test
    public void faseVermelhoGrupoSemaforicoApagado() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA, 1, 1));
        avancarSegundos(motor, 600);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(660), TipoEvento.REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO, 1, 1));
        avancarSegundos(motor, 100);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(60, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(315, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Recuperando da Falha
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(660)).get(1).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(660, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(660, new GrupoCheck(1, 1, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(660, new GrupoCheck(1, 2, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(660, new GrupoCheck(1, 2, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(660, new GrupoCheck(1, 3, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(660, new GrupoCheck(1, 3, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(660, new GrupoCheck(1, 4, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(660, new GrupoCheck(1, 4, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(660, new GrupoCheck(1, 5, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(660, new GrupoCheck(1, 5, 3000, 13000, EstadoGrupoSemaforico.VERDE));
    }

    @Test
    public void detectorFaltaAcionamento() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(3);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();

        Pair<Integer, TipoDetector> dadosDetector = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO, dadosDetector, 3));
        avancarSegundos(motor, 100);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, dadosDetector, 3));
        avancarSegundos(motor, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(53)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(72)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(72, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(115)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(152)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(152, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(178)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(195)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(213)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(232)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(232, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(253)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void detectorAcionamentoDireto() {
        inicioControlador = new DateTime(2016, 11, 14, 23, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 14, 23, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(3);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();


        Pair<Integer, TipoDetector> dadosDetector = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
        //Avancar
        avancarSegundos(motor, 50);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(50), TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO, dadosDetector, 3));
        avancarSegundos(motor, 110);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(100), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, dadosDetector, 3));
        avancarSegundos(motor, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(53)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(72)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(89)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(107)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(126)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(147)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(164)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(182)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(201)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(222)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(239)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(257)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(276)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void detectorPedestreFaltaAcionamento() {
        inicioControlador = new DateTime(2016, 11, 14, 23, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 14, 23, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(3);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.PEDESTRE) && det.getPosicao().equals(1)).findFirst().get();


        Pair<Integer, TipoDetector> dadosDetector = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
        //Avancar
        avancarSegundos(motor, 50);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(50), TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO, dadosDetector, 3));
        avancarSegundos(motor, 110);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(100), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, dadosDetector, 3));
        avancarSegundos(motor, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(53)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(72)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(89)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(107)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(126)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(147)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(164)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(182)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(201)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(222)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(239)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(257)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(276)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void detectorPedestreAcionamentoDireto() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(3);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();

        Pair<Integer, TipoDetector> dadosDetector = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
        //Avancar
        avancarSegundos(motor, 57);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO, dadosDetector, 3));
        avancarSegundos(motor, 100);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, dadosDetector, 3));
        avancarSegundos(motor, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(35)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(53)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(72)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(72, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(72, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(72, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(115)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(152)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(152, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(152, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(152, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(178)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(195)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(213)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(232)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(232, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(232, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(232, new GrupoCheck(3, 16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(253)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void desrespeitoAoTempoMaximoDePermanencia() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO, 1));
        avancarSegundos(motor, 300);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(60, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(315, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void verdesConflitantes() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador(true));
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 300);

        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(52).plus(100)).get(1).getEstagio().getPosicao());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(62).plus(100)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(109).plus(200)).get(1).getEstagio().getPosicao());
    }

    @Test
    public void sequenciaDeCores() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_SEQUENCIA_DE_CORES, 1));
        avancarSegundos(motor, 300);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(60, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(315, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void verdeConflitanteForcado() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);


        //Avancar
        avancarSegundos(motor, 10);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(10), TipoEvento.FALHA_VERDES_CONFLITANTES, 1));
        avancarSegundos(motor, 28);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(38), TipoEvento.FALHA_VERDES_CONFLITANTES, 1));
        avancarSegundos(motor, 300);


        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 1, 8000, 10000, EstadoGrupoSemaforico.VERDE));

        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 2, 8000, 10000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 3, 8000, 10000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 4, 8000, 10000, EstadoGrupoSemaforico.VERMELHO));

        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaHistoricoGruposSemaforicos(10, new GrupoCheck(1, 5, 8000, 10000, EstadoGrupoSemaforico.VERDE));

        assertEquals("Plano Intermitente por Falha", ModoOperacaoPlano.INTERMITENTE, getPlanoTrocaEfetiva(1, 10).getModoOperacao());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(10)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(10, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(10, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(10, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(10, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(10, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Plano 1 - Isolado", ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, getPlanoTrocaEfetiva(1, 20).getModoOperacao());
        assertEquals("Plano 1 - Isolado", 1, getPlanoTrocaEfetiva(1, 20).getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(20)).get(1).getEstagio().getPosicao().intValue());

        verificaHistoricoGruposSemaforicos(20, new GrupoCheck(1, 1, 0, 10000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(20, new GrupoCheck(1, 2, 0, 10000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(20, new GrupoCheck(1, 3, 0, 10000, EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(20, new GrupoCheck(1, 4, 0, 10000, EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(20, new GrupoCheck(1, 5, 0, 10000, EstadoGrupoSemaforico.DESLIGADO));


        assertEquals("Plano Intermitente por Falha", true, getPlanoTrocaEfetiva(1, 38).isImpostoPorFalha());
        assertEquals("Plano Intermitente por Falha", ModoOperacaoPlano.INTERMITENTE, getPlanoTrocaEfetiva(1, 38).getModoOperacao());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(38)).get(1).getEstagio().getPosicao());

        verificaHistoricoGruposSemaforicos(293, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(293, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaHistoricoGruposSemaforicos(293, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(293, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaHistoricoGruposSemaforicos(293, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void falhaWatchDog() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_WATCH_DOG));
        avancarSegundos(motor, 300);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(60, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(315, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

    @Test
    public void falhaMemoria() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_MEMORIA));
        avancarSegundos(motor, 300);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(60)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(60, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(60, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(315, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(315, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
    }

}


