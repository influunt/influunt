package os72c;

import org.eclipse.paho.client.mqttv3.*;

public class Device implements MqttCallback {

  MqttClient client;

  public Device() {}

  public static void main(String[] args) {
    new Device().doDemo();
  }

  public void doDemo() {
    try {

      System.out.println("Rodando device");
      client = new MqttClient("tcp://10.0.0.9:1883", "rsol_pi");
      MqttConnectOptions opts = new MqttConnectOptions();
      opts.setAutomaticReconnect(true);
      opts.setWill("rsol/disconnect","1".getBytes(),2,true);
      client.setCallback(this);
      client.connect(opts);
      client.subscribe("rsol/pi");

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
    client.publish("rsol/java",message);


  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.println("Delivery OK");
    System.out.println(token);
  }
}