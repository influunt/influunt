package protocol;

import models.StatusDevice;
import org.fusesource.mqtt.client.QoS;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MudancaStatusControlador {
    public StatusDevice status;

    private MudancaStatusControlador(StatusDevice status) {
        this.status = status;
    }

    public static Envelope getMensagem(String idControlador, StatusDevice status) {
        MudancaStatusControlador mudancaStatusControlador = new MudancaStatusControlador(status);
        return new Envelope(TipoMensagem.MUDANCA_STATUS_CONTROLADOR,
            idControlador,
            DestinoCentral.envioDeStatus(),
            QoS.AT_LEAST_ONCE,
            mudancaStatusControlador,
            null);
    }

}
