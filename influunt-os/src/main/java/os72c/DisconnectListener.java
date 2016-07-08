package os72c;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class DisconnectListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Desconectou:" + message);
    }
}
