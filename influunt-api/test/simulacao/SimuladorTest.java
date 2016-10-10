package simulacao;

import engine.EstadoGrupoBaixoNivel;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import simulador.Simulador;
import simulador.parametros.ParametroSimulacao;

import java.util.List;

/**
 * Created by rodrigosol on 10/10/16.
 */
public class SimuladorTest extends Simulador {

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

    }

    @Override
    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {

    }

    @Override
    public void onAgendamentoTrocaDePlanos(DateTime timestamp, DateTime momentoAgendamento, int anel, int plano, int planoAnterior) {

    }

    @Override
    public void onEstado(DateTime timestamp, EstadoGrupoBaixoNivel estadoGrupoBaixoNivel) {

    }
}
