package engine;

import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by rodrigosol on 9/26/16.
 */
public interface MotorCallback {
    public void onStart(DateTime timestamp);

    public void onStop(DateTime timestamp);

    public void onChangeEvento(DateTime timestamp, Evento eventoAntigo, Evento eventoNovo);

    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo);

    public void onAgendamentoTrocaDePlanos(DateTime timestamp,DateTime momentoAgendamento, int anel, int plano, int planoAnterior);

    public void onEstado(DateTime timestamp, EstadoGrupoBaixoNivel estadoGrupoBaixoNivel);

}
