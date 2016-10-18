package protocol;

import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusControlador;
import models.StatusVersao;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class PacotePlano {


    private PacotePlano() {
    }

    public static Object getPayload(Envelope envelope) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(envelope.getIdControlador()));
        if (controlador != null && !controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EM_CONFIGURACAO)) {
            return new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        } else {
            return null;
        }

    }
}
