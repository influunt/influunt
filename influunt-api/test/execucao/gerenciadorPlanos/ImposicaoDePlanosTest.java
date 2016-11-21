package execucao.gerenciadorPlanos;


import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeTrocasTest;
import integracao.ControladorHelper;
import models.EstadoGrupoSemaforico;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ImposicaoDePlanosTest extends GerenciadorDeTrocasTest {

    @Before
    @Override
    public void setup() {
        controlador = new ControladorHelper().setPlanosComTabelaHorariaMicro(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Test
    public void imporModoIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.INTERMITENTE.toString(), 1));
        avancarSegundos(motor, 60);

        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 0, 3000, EstadoGrupoSemaforico.AMARELO));
        verificaGruposSemaforicos(70, new GrupoCheck(1, 1, 3000, 8000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
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
    }


    @Test
    public void saidaModoIntermitente() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 60);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(60), TipoEvento.IMPOSICAO_MODO, ModoOperacaoPlano.INTERMITENTE.toString(), 1));

        avancarSegundos(motor, 20);
        assertEquals(ModoOperacaoPlano.INTERMITENTE, motor.getEstagios().get(0).getPlano().getModoOperacao());
        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, motor.getEstagios().get(1).getPlano().getModoOperacao());

        avancarSegundos(motor, 40);
        motor.onEvento(new EventoMotor(inicioExecucao.plus(120), TipoEvento.LIBERAR_IMPOSICAO_MODO, 1));

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
    }
}
