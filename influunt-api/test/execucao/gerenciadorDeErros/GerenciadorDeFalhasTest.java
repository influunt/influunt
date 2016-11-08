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


