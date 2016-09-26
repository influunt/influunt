package engine;

import models.EstadoGrupoSemaforico;
import models.Evento;

/**
 * Created by rodrigosol on 9/26/16.
 */
public interface MotorCallback {
    public void onStart();
    public void onStop();
    public void onChangeEvento(Evento eventoAntigo, Evento eventoNovo);
    public void onGrupoChange(EstadoGrupoSemaforico estadoAntigo, EstadoGrupoSemaforico estadoNovo);

}
