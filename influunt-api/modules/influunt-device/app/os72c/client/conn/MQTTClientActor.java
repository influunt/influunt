package os72c.client.conn;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import models.StatusControlador;
import org.eclipse.paho.client.mqttv3.*;
import os72c.client.Client;
import os72c.client.controladores.Controlador;
import os72c.client.procolos.MensagemControladorSupervisor;
import protocol.ControladorOffline;
import protocol.ControladorOnline;
import protocol.Envelope;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static protocol.ControladorOnline.getMensagem;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTClientActor extends UntypedActor implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions opts;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef controlador;
    private Cancellable tick;
    private String id;

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
        }else if(message instanceof MensagemControladorSupervisor){
            String json = new Gson().toJson(((MensagemControladorSupervisor) message));
            log.error("DIFERENÇA {}",json);
            MqttMessage status = new MqttMessage();
            status.setQos(0);
            status.setRetained(false);
            status.setPayload(json.getBytes());
            client.publish("central/status/" + id, status);


        }
    }

    private void connect() throws MqttException {
        log.info("Iniciando MQTTControlador");
//        id = Client.conf72c.getString("id");
//        Config conf = Client.conf72c.getConfig("mosquitto");
//        String host = conf.getString("host");
//        String port = conf.getString("port");
        String host = "mosquitto.rarolabs.com.br";//conf.getString("host");
        String port = "1883"; //conf.getString("port");

        log.info("Conectando no servidor:{}:{}",host,port);
        log.info("cliente id: {}",id);

        client = new MqttClient("tcp://"+host+":"+port, "foo");
        log.info("Cliente criado {}",client);
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(10);

        Envelope controladorOffline = ControladorOffline.getMensagem("1234");

        opts.setWill(controladorOffline.getDestino(),controladorOffline.toJson().getBytes(),1,true);
        log.info("OPTS {}",opts);

        client.setCallback(this);
        log.info("CALLBACK {}",opts);
        client.connect(opts);
        if(tick != null){
            tick.cancel();
        }else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                    Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        log.info("Status: {}",client.isConnected());


        Envelope controladorOnline = ControladorOnline.getMensagem("1234",System.currentTimeMillis(),"1.0", StatusControlador.ATIVO);
        MqttMessage message = new MqttMessage();
        message.setQos(2);
        message.setRetained(true);
        message.setPayload(controladorOnline.toJson().getBytes());
        client.publish(controladorOnline.getDestino(), message);

    }

    private void desconectar(String topic, MqttMessage message) {
        log.info("ServerActor morreu");
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
