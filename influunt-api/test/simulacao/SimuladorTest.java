package simulacao;

import engine.EstadoGrupoBaixoNivel;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import simulador.Simulador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 10/10/16.
 */
public class SimuladorTest extends Simulador {

    public Map<DateTime, Pair<Evento, Evento>> listaEvento = new HashMap<>();
    public Map<DateTime, Pair<Integer, Integer>> listaEventoReal = new HashMap<>();
    public Map<DateTime, EstadoGrupoBaixoNivel> listaEstado = new HashMap<>();
    public Map<DateTime, List<EstadoGrupoSemaforico>> listaEstadoGrupoSemaforico = new HashMap<>();

    public SimuladorTest(DateTime inicioSimulado, Controlador controlador, ParametroSimulacao parametros) {
        super(inicioSimulado, controlador, parametros);
    }

    @Override
    public void onStart(DateTime timestamp) {

    }

    @Override
    public void onStop(DateTime timestamp) {

    }

    @Override
    public void onChangeEvento(DateTime timestamp, Evento eventoAntigo, Evento eventoNovo) {
        listaEvento.put(timestamp, Pair.create(eventoAntigo, eventoNovo));
    }

    @Override
    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {
        listaEstadoGrupoSemaforico.put(timestamp, estadoNovo);
    }

    @Override
    public void onAgendamentoTrocaDePlanos(DateTime timestamp, DateTime momentoAgendamento, int anel, int plano, int planoAnterior) {
        listaEventoReal.put(momentoAgendamento, Pair.create(planoAnterior, plano));
    }

    @Override
    public void onEstado(DateTime timestamp, EstadoGrupoBaixoNivel estadoGrupoBaixoNivel) {
        listaEstado.put(timestamp, estadoGrupoBaixoNivel);
    }

    public Map<DateTime, Pair<Evento, Evento>> getListaEvento() {
        return listaEvento;
    }

    public Map<DateTime, Pair<Integer, Integer>> getListaEventoReal() {
        return listaEventoReal;
    }

    public Map<DateTime, EstadoGrupoBaixoNivel> getListaEstado() {
        return listaEstado;
    }

    public Map<DateTime, List<EstadoGrupoSemaforico>> getListaEstadoGrupoSemaforico() {
        return listaEstadoGrupoSemaforico;
    }
}
