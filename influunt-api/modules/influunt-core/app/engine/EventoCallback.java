package engine;

import models.Evento;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface EventoCallback {
    public void onEvento(EventoMotor eventoMotor);
}
