package protocol;

import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusControlador;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Sinal {


    private Sinal() {
    }

    public static Envelope getMensagem(TipoMensagem tipoMensagem, String idControlador, String destino) {
        return new Envelope(tipoMensagem, idControlador, destino, 1, null, null);
    }

}
