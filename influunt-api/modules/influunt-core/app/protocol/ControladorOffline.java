package protocol;

import org.fusesource.mqtt.client.QoS;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ControladorOffline {
    public long dataHora;


    private ControladorOffline() {
    }

    public static Envelope getMensagem(String idControlador) {
        return new Envelope(TipoMensagem.CONTROLADOR_OFFLINE,
            idControlador,
            DestinoControlador.offline(),
            QoS.AT_LEAST_ONCE,
            null,
            null);
    }

}
