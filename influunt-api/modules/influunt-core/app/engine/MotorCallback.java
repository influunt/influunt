package engine;

import models.Evento;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 9/26/16.
 */
public interface MotorCallback extends GerenciadorDeEstagiosCallback {
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual);
    public void onTrocaDePlanoEfetiva(DateTime timestamp,DateTime origem, int anel, Evento eventoAtual);


}
