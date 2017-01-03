package protocol;

import engine.EventoMotor;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class Info {

    public static Envelope getMensagem(String idControlador,String fabricante, String modelo, String versao) {

        Map<String,String> info = new HashMap<>();
        info.put("fabricante",fabricante);
        info.put("modelo",modelo);
        info.put("versao",versao);

        return new Envelope(TipoMensagem.INFO,
            idControlador,
            DestinoCentral.alarmeFalhaConfiguracao(),
            QoS.AT_LEAST_ONCE,
            Json.toJson(info).toString(),
            null);
    }
}
