package engine;

import models.Evento;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by rodrigosol on 9/26/16.
 */
public interface MotorCallback extends GerenciadorDeEstagiosCallback {
    void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos);

    void onAlarme(DateTime timestamp, EventoMotor eventoMotor);

    void onFalha(DateTime timestamp, EventoMotor eventoMotor);

    void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor);

    void modoManualAtivo(DateTime timestamp);

    void modoManualDesativado(DateTime timestamp);

    void trocaEstagioManualLiberada(DateTime timestamp);

    void trocaEstagioManualBloqueada(DateTime timestamp);
}
