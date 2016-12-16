package protocol;

import org.fusesource.mqtt.client.QoS;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Echo {
    private Echo() {
    }

    public static Envelope getMensagem(String idControlador, String destino, String texto) {
        return new Envelope(TipoMensagem.ECHO,
            idControlador,
            destino,
            QoS.AT_LEAST_ONCE,
            texto,
            null);
    }

}
