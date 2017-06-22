package protocol;

import models.StatusDevice;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ControladorOnline {
    public long dataHora;

    public String firmware;

    public StatusDevice status;

    public String marca;

    public String modelo;

    private ControladorOnline(long dataHora, String firmware, StatusDevice status, String marca, String modelo) {
        this.dataHora = dataHora;
        this.firmware = firmware;
        this.status = status;
        this.marca = marca;
        this.modelo = modelo;
    }

    public static Envelope getMensagem(String idControlador, Long dataHora, String firmware, StatusDevice status, String marca, String modelo) {
        ControladorOnline controladorOnline = new ControladorOnline(dataHora, firmware, status, marca, modelo);
        return new Envelope(TipoMensagem.CONTROLADOR_ONLINE,
            idControlador,
            DestinoControlador.online(),
            QoS.AT_LEAST_ONCE,
            Json.toJson(controladorOnline).toString(),
            null);
    }

}
