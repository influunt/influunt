package protocol;

import models.StatusAnel;
import models.StatusDevice;
import org.fusesource.mqtt.client.QoS;

import java.util.HashMap;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MudancaStatusControlador {
    public StatusDevice status;
    private HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();

    private MudancaStatusControlador(StatusDevice status, HashMap<Integer, StatusAnel> statusAneis) {
        this.status = status;
        this.statusAneis = statusAneis;
    }

    public static Envelope getMensagem(String idControlador, StatusDevice status,
                                       HashMap<Integer, StatusAnel> statusAneis) {
        MudancaStatusControlador mudancaStatusControlador = new MudancaStatusControlador(status, statusAneis);
        return new Envelope(TipoMensagem.MUDANCA_STATUS_CONTROLADOR,
            idControlador,
            DestinoCentral.envioDeStatus(),
            QoS.AT_LEAST_ONCE,
            mudancaStatusControlador,
            null);
    }

}
