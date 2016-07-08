package os72c;

import org.eclipse.paho.client.mqttv3.*;

public class Master implements MqttCallback {

  MqttClient client;
    private IMqttMessageListener disconnectListerner = new DisconnectListener();

    public Master() {}

  public static void main(String[] args) {
    new Master().doDemo();
  }

  public void doDemo() {
    try {
      System.out.println("Rodando central");
      client = new MqttClient("tcp://10.0.0.9:1883", "rsol_central");
      client.setTimeToWait(5000);
      MqttConnectOptions opts = new MqttConnectOptions();
      opts.setAutomaticReconnect(true);
      client.setCallback(this);
      client.connect(opts);
      client.subscribe("rsol/java");
        client.subscribe("rsol/disconnect",2,disconnectListerner);

      MqttMessage message = new MqttMessage();
      message.setPayload("1".getBytes());
      message.setQos(2);
      message.setRetained(true);
      client.publish("rsol/pi", message);

    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void connectionLost(Throwable cause) {
    System.out.println("Connection Lost");
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.println("chegou!");
    Long l = Long.valueOf(message.toString());
    System.out.println("O que chegou:"+ l);
    l++;
    MqttMessage replayMsg = new MqttMessage();
    replayMsg.setQos(2);
    replayMsg.setRetained(true);
    message.setPayload(l.toString().getBytes());
    client.publish("rsol/pi",message);


  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.println("Delivery OK");
    System.out.println(token);
  }
}