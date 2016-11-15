package protocol;

import com.google.gson.Gson;
import engine.EventoMotor;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusVersao;
import utils.RangeUtils;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class AlarmeFalha {

    public static Envelope getMensagem(String idControlador,EventoMotor eventoMotor) {
        return new Envelope(TipoMensagem.ALARME_FALHA,
            idControlador,
            DestinoCentral.alarmeFalhaConfiguracao(idControlador),
            2,
            new Gson().toJson(eventoMotor),
            null);
    }
}
