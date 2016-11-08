package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import engine.*;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest extends WithInfluuntApplicationNoAuthentication implements MotorCallback {

    protected Controlador controlador;
    protected DateTime inicioExecucao;
    protected DateTime inicioControlador;
    protected HashMap<DateTime, Evento> listaTrocaPlano;
    protected HashMap<DateTime, HashMap<Integer, Evento>> listaTrocaPlanoEfetiva;
    protected HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaEstagios;
    protected HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaHistoricoEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
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


    protected void avancarSegundos(Motor motor, long i) {
        long quantidade = i * 10L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    protected void avancarMilis(Motor motor, long i) {
        long quantidade = i;
        while (quantidade-- > 0) {
            motor.tick();
        }
    }

    protected void avancarHoras(Motor motor, long i) {
        long quantidade = i * 36000L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    protected void avancarMinutos(Motor motor, long i) {
        long quantidade = i * 600L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    protected void avancarDias(Motor motor, long i) {
        long quantidade = i * 864000L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios,inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    protected void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    public class GrupoCheck {

        private final int anel;

        private final int grupo;

        private final long inicio;

        private final long fim;

        private final EstadoGrupoSemaforico estado;

        public GrupoCheck(int anel, int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.anel = anel;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public void check(HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).get(anel).getEstados().get(this.grupo).get(this.inicio));
        }
    }

}
