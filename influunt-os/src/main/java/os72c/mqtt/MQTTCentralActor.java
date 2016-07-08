package os72c.mqtt;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.eclipse.paho.client.mqttv3.*;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTCentralActor extends UntypedActor implements MqttCallback {

    private Map<String,Long> contador = new HashMap<String, Long>();

    private MqttClient client;
    private MqttConnectOptions opts;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef central;
    private Cancellable tick;

    @Override
    public void preStart() throws Exception {
        log.info("preStart()");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop()");
        super.postStop();
        client.close();
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
            central = getSender();
            connect();
            getSender().tell("CONNECTED",getSelf());
        }else if("Tick".equals(message)){
            log.info("Connection Status:" + client.isConnected());
            if(!client.isConnected()){
                throw new Exception("Conexao morreu");
            }
        }
    }

    private void proximo(String topic, MqttMessage message) throws MqttException {
        String controladorId = topic.split("/")[2];
        System.out.println("Proximo:" + controladorId);
        Long ultimoValor = Long.valueOf(message.toString());
        System.out.println(controladorId + "--->" + ultimoValor);
        if(!contador.containsKey(controladorId)){
            contador.put(controladorId,ultimoValor);
        }
        imprimir(controladorId);
    }

    private void registrar(String topic, MqttMessage message) throws MqttException {
        String controladorId = topic.split("/")[2];
        System.out.println("Registrar:" + controladorId);
        if(!contador.containsKey(controladorId)){
            contador.put(controladorId,0l);
        }
        imprimir(controladorId);
    }

    private void imprimir(String controladorId) throws MqttException {
        System.out.println("Imprimir:" + controladorId);
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        Long newValue = contador.get(controladorId) + 1;
        contador.put(controladorId,newValue);
        message.setPayload(newValue.toString().getBytes());
        client.publish("controlador/" + controladorId + "/imprimir",message);
    }

    private void desconectar(String topic, MqttMessage message) throws MqttException {
        String controladorId = topic.split("/")[2];
        System.out.println("Desconectar:" + controladorId);
        if(!contador.containsKey(controladorId)){
            contador.put(controladorId,0l);
        }
        imprimir(controladorId);
    }

    private void connect() throws MqttException {
        log.info("Iniciando MQTTCentral");
        client = new MqttClient("tcp://192.168.0.130:1883", "central_a");
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(10);
        opts.setWill("central/morreu","1".getBytes(),1,true);
        client.setCallback(this);
        client.connect(opts);

        if(tick != null){
            tick.cancel();
        }else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                    Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        log.info("Status: {}",client.isConnected());


        client.subscribe("central/desconectar/+", 1, (topic, message) -> {
            desconectar(topic, message);
        });
        client.subscribe("central/registrar/+", 1, (topic, message) -> {
            registrar(topic, message);
        });
        client.subscribe("central/proxima/+", 1, (topic, message) -> {
            proximo(topic, message);
        });
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
