package protocol;

import org.fusesource.mqtt.client.QoS;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Sinal {


    private Sinal() {
    }

    public static Envelope getMensagem(TipoMensagem tipoMensagem, String idControlador, String destino) {
        return new Envelope(tipoMensagem, idControlador, destino, QoS.AT_LEAST_ONCE, null, null);
    }

}
