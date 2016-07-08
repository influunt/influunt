package os72c.mqtt;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.eclipse.paho.client.mqttv3.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTControladorActor extends UntypedActor implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions opts;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef controlador;
    private Cancellable tick;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        log.info("preStart()");
    }

    @Override
    public void aroundPostStop() {
        log.info("aroundPostStop()");
        super.aroundPostStop();
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop()");
        super.postStop();
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        log.info("postRestar()");
        log.info("Restart foi chamado");
        connect();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("---------------------------");
        log.info(message.toString());
        log.info("---------------------------");
        if(message instanceof Exception) {
            throw (Exception) message;
        }else if(message.equals("CONNECT")){
            controlador = getSender();
            connect();
            getSender().tell("CONNECTED",getSelf());
        }else if("Tick".equals(message)){
            log.info("Connection Status:" + client.isConnected());
            if(!client.isConnected()){
                throw new Exception("Conexao morreu");
            }
        }
    }

    private void imprimir(String topic, MqttMessage message) throws MqttException {
        log.info("controlador ---> {}", message.toString());
        proxima(message);
    }

    private void proxima(MqttMessage message) throws MqttException {
        client.publish("central/proxima/1",message);
    }


    private void connect() throws MqttException {
        log.info("Iniciando MQTTControlador");
        client = new MqttClient("tcp://192.168.0.130:1883", "controlador_a");
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(10);
        opts.setWill("central/desconectar/1","1".getBytes(),1,true);
        client.setCallback(this);
        client.connect(opts);
        if(tick != null){
            tick.cancel();
        }else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                    Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        log.info("Status: {}",client.isConnected());

        client.subscribe("controlador/" + 1 + "/imprimir", 1, (topic, message) -> {
            imprimir(topic, message);
        });
        client.subscribe("central/morreu", 1, (topic, message) -> {
            desconectar(topic, message);
        });


        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        message.setPayload("1".getBytes());
        client.publish("central/registrar/1", message);

    }

    private void desconectar(String topic, MqttMessage message) {
        log.info("Central morreu");
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Conexao morreu");
        try {
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
            getSelf().tell(e,getSelf());
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
