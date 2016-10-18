package protocol;

import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusControlador;
import models.StatusVersao;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Configuracao {


    private Configuracao() {
    }

    public static Envelope getMensagem(Envelope envelope) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(envelope.getIdControlador()));
        if (controlador != null && !controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EM_CONFIGURACAO)) {
            return new Envelope(TipoMensagem.CONFIGURACAO,
                    envelope.getIdControlador(),
                    "controlador/".concat(envelope.getIdControlador()).concat("/configuracao"),
                    2,
                    new ControladorCustomSerializer().getControladorJson(controlador).toString(),
                    envelope.getIdMensagem());
        } else {
            return new Envelope(TipoMensagem.ERRO,
                    envelope.getIdControlador(),
                    "controlador/".concat(envelope.getIdControlador()).concat("/configuracao"),
                    2,
                    null,
                    envelope.getIdMensagem());
        }

    }
}
