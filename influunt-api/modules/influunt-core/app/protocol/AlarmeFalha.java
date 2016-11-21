package protocol;

import engine.EventoMotor;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class AlarmeFalha {

    public static Envelope getMensagem(String idControlador, EventoMotor eventoMotor) {

        return new Envelope(TipoMensagem.ALARME_FALHA,
            idControlador,
            DestinoCentral.alarmeFalhaConfiguracao(idControlador),
            2,
            Json.toJson(eventoMotor).toString(),
            null);
    }
}
