package execucao.gerenciadorPlanos;


import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeTrocasTest;
import models.EstadoGrupoSemaforico;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ImposicaoDePlanosTest extends GerenciadorDeTrocasTest {

    @Test
    public void imporModoIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.INTERMITENTE.toString(), 1, 240));
        avancarSegundos(motor, 60);

        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 6000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 11000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        avancarHoras(motor, 3);
        assertEquals(ModoOperacaoPlano.INTERMITENTE, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        verificaGruposSemaforicos(7465, new GrupoCheck(1, 1, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(7465, new GrupoCheck(1, 2, 0, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(7465, new GrupoCheck(1, 3, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(7465, new GrupoCheck(1, 4, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(7465, new GrupoCheck(1, 5, 0, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertFalse(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void liberarModoIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.INTERMITENTE.toString(), 1, 240));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.INTERMITENTE, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 40);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(120), TipoEvento.LIBERAR_IMPOSICAO, 1));

        avancarSegundos(motor, 10);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void saidaModoIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.INTERMITENTE.toString(), 1, 1));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.INTERMITENTE, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 40);
        avancarSegundos(motor, 10);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void imporModoApagado() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.APAGADO.toString(), 1, 240));
        avancarSegundos(motor, 60);

        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 3000, 6000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 6000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 2, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 3, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 0, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 4, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 0, 5000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 8000, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 5, 11000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals(ModoOperacaoPlano.APAGADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        assertFalse(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void liberarModoApagado() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.APAGADO.toString(), 1, 240));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.APAGADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 40);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(120), TipoEvento.LIBERAR_IMPOSICAO, 1));

        avancarSegundos(motor, 10);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void saidaModoApagado() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.APAGADO.toString(), 1, 1));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.APAGADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 40);
        avancarSegundos(motor, 10);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 1, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 2, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 3, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 4, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(1, 5, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void imporPlano() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(60), TipoEvento.IMPOSICAO_PLANO, 3, 1, 240));
        avancarSegundos(motor, 60);

        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarHoras(motor, 1);

        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        assertFalse(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void liberarImposicaoPlano() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_PLANO, 3, 1, 240));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 120);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(120), TipoEvento.LIBERAR_IMPOSICAO, 1));

        avancarSegundos(motor, 60);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());


        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }

    @Test
    public void saidaImposicaoPlano() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60), TipoEvento.IMPOSICAO_PLANO, 3, 1, 1));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 180);
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());


        assertTrue(motor.getEstagios().get(0).getEventosAgendados().isEmpty());
    }
}
