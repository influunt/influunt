package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import engine.GerenciadorDeEstagios;
import engine.IntervaloGrupoSemaforico;
import engine.Motor;
import engine.MotorCallback;
import integracao.ControladorHelper;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest extends WithInfluuntApplicationNoAuthentication implements MotorCallback {

    private Controlador controlador;
    HashMap<DateTime, Evento> listaTrocaPlano;
    HashMap<DateTime, Evento> listaTrocaPlanoEfetiva;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();

    }


    @Test
    public void motorTest() throws IOException {

        DateTime inicioControlador = new DateTime(2016, 10, 20, 18, 0, 0);
        DateTime inicioExecucao = new DateTime(2016, 10, 20, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarMinutos(motor, 60);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlano.get(inicioExecucao.plusSeconds(60)).getPosicaoPlano().intValue());
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual) {
        listaTrocaPlano.put(timestamp, eventoAtual);
    }

    @Override
    public void onTrocaDePlanoEfetiva(DateTime timestamp, DateTime origem, int anel, Evento eventoAtual) {
        listaTrocaPlanoEfetiva.put(timestamp, eventoAtual);
    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }


    private void avancarSegundos(Motor motor, long i) {
        i *= 10;
        while (i-- > 0) {
            motor.tick();
        }
    }

    private void avancarMilis(Motor motor, long i) {
        while (i-- > 0) {
            motor.tick();
        }
    }

    private void avancarHoras(Motor motor, long i) {
        i *= 36000;
        while (i-- > 0) {
            motor.tick();
        }
    }

    private void avancarMinutos(Motor motor, long i) {
        i *= 600;
        while (i-- > 0) {
            motor.tick();
        }
    }

    private void avancarDias(Motor motor, long i) {
        i *= 864.000;
        while (i-- > 0) {
            motor.tick();
        }
    }

}