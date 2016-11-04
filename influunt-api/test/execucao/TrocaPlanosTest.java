package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
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

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class TrocaPlanosTest extends WithInfluuntApplicationNoAuthentication implements MotorCallback {

    private Controlador controlador;
    private DateTime inicioExecucao;
    private DateTime inicioControlador;
    private HashMap<DateTime, Evento> listaTrocaPlano;
    private HashMap<DateTime, HashMap<Integer, Evento>> listaTrocaPlanoEfetiva;
    private HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaEstagios;
    private HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaHistoricoEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Test
    public void entradaPlanoNormalTest() throws IOException {
        inicioControlador = new DateTime(2016, 11, 5, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 5, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 6, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 6, 18, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 6, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 7, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 7, 18, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 48);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 11, 3, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 11, 3, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 9, listaTrocaPlano.size());
    }

    @Test
    public void entradaPlanoEspecialRecorrenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 12, 25, 7, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 7, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao.plusHours(1)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusHours(17)).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 12, 25, 19, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 19, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 11, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2016, 12, 25, 0, 0, 0);
        inicioExecucao = new DateTime(2016, 12, 25, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 12, 25, 0, 0, 0);
        inicioExecucao = new DateTime(2017, 12, 25, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());
    }

    @Test
    public void entradaPlanoEspecialNaoRecorrenteTest() throws IOException {
        inicioControlador = new DateTime(2017, 3, 15, 7, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 7, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao.plusHours(1)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusHours(17)).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 3, 15, 19, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 19, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 24);

        assertEquals("Plano Atual", 12, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2017, 3, 15, 0, 0, 0);
        inicioExecucao = new DateTime(2017, 3, 15, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 3, listaTrocaPlano.size());

        listaTrocaPlano = new HashMap<>();
        inicioControlador = new DateTime(2018, 3, 15, 0, 0, 0);
        inicioExecucao = new DateTime(2018, 3, 15, 0, 0, 0);
        motor = new Motor(controlador, inicioControlador, inicioExecucao, this);
        avancarHoras(motor, 25);

        assertEquals("Total de trocas", 9, listaTrocaPlano.size());
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        listaTrocaPlano.put(timestamp, eventoAtual);
    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        if(!listaTrocaPlanoEfetiva.containsKey(agendamentoTrocaPlano.getMomentoDaTroca())){
            listaTrocaPlanoEfetiva.put(agendamentoTrocaPlano.getMomentoDaTroca(), new HashMap<>());
        }
        listaTrocaPlanoEfetiva.get(agendamentoTrocaPlano.getMomentoDaTroca()).put(agendamentoTrocaPlano.getAnel(), agendamentoTrocaPlano.getEvento());
    }


    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if(!listaEstagios.containsKey(timestamp)){
            listaEstagios.put(timestamp, new HashMap<>());
        }
        listaEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if(!listaHistoricoEstagios.containsKey(timestamp)){
            listaHistoricoEstagios.put(timestamp, new HashMap<>());
        }
        listaHistoricoEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }

    private void avancarHoras(Motor motor, long quantidade) {
        quantidade *= 36000;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }
}
