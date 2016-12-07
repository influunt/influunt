package protocol;

import engine.EventoMotor;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class RemocaoFalha {

    public static Envelope getMensagem(String idControlador, EventoMotor eventoMotor) {

        return new Envelope(TipoMensagem.REMOCAO_FALHA,
            idControlador,
            DestinoCentral.alarmeFalhaConfiguracao(),
            2,
            Json.toJson(eventoMotor).toString(),
            null);
    }
}
