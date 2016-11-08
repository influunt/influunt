package execucao.gerenciadorDeErros;

import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeEstagiosTest;
import execucao.MotorTest;
import models.*;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeFalhasTest extends MotorTest {

    @Test
    public void faseVermelhoGrupoSemaforicoApagado() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA, getGrupoSemaforico(1)));
        avancarSegundos(motor, 600);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(660), TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO, getGrupoSemaforico(1)));
        avancarSegundos(motor, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(68)).get(1).getEstagio().getPosicao());

        verificaGruposSemaforicos(68, new GrupoCheck(1, 1, 0, 3000,EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 1, 3000, 8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 1, 8000, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 1, 11000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(68, new GrupoCheck(1, 2, 0, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 2, 8000, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 2, 11000, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(68, new GrupoCheck(1, 3, 0, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 3, 8000, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 3, 11000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(68, new GrupoCheck(1, 4, 0, 8000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 4, 8000, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 4, 11000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(68, new GrupoCheck(1, 5, 0, 5000,EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 5, 5000, 8000,EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 5, 8000, 11000,EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(68, new GrupoCheck(1, 5, 11000, 255000,EstadoGrupoSemaforico.DESLIGADO));

        //Novo Ciclo
        verificaGruposSemaforicos(323, new GrupoCheck(1, 1, 0, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(323, new GrupoCheck(1, 2, 0, 255000,EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(323, new GrupoCheck(1, 3, 0, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(323, new GrupoCheck(1, 4, 0, 255000,EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(323, new GrupoCheck(1, 5, 0, 255000,EstadoGrupoSemaforico.DESLIGADO));

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
    public void focoVermelhoApagado() {

    }

    @Test
    public void detectorFaltaAcionamento() {
        inicioControlador = new DateTime(2016, 10, 18, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 18, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        Anel anel = getAnel(3);
        Detector detector = anel.getDetectores().stream().filter(det -> det.getTipo().equals(TipoDetector.VEICULAR) && det.getPosicao().equals(1)).findFirst().get();

        //Avancar
        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO, detector));
        avancarSegundos(motor, 100);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector, 3));
        avancarSegundos(motor, 600);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(0, new GrupoCheck(3, 16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(101)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(118)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(136)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(155)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(155, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 11, 11000, 26000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(155, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 12, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(155, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 13, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(155, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 14, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(155, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 15, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(155, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(155, new GrupoCheck(3, 16, 11000, 26000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(181)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(198)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(216)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(235)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(235, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 11, 11000, 21000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(235, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 12, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(235, new GrupoCheck(3, 13, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 13, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(235, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 14, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(235, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 15, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(235, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(235, new GrupoCheck(3, 16, 11000, 21000, EstadoGrupoSemaforico.VERMELHO));
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(256)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void detectorAcionamentoDireto() {

    }

    @Test
    public void desrespeitoAoTempoMaximoDePermanencia() {

    }

    @Test
    public void verdesConflitantes() {

    }

    @Test
    public void amareloIntermitente() {

    }

    @Test
    public void semaforoApagado() {

    }

    @Test
    public void acertoDeRelogio() {

    }


}


