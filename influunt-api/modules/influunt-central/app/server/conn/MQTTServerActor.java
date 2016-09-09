package server.conn;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import protocol.Envelope;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTServerActor extends UntypedActor implements MqttCallback {

    private final String host;

    private final String port;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    Router router;

    private Map<String, Long> contador = new HashMap<String, Long>();

    private MqttClient client;

    private MqttConnectOptions opts;

    private ActorRef central;

    private Cancellable tick;

    private ActorRef messageBroker;

    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(MessageBroker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    public MQTTServerActor(final String host, final String port) {
        this.host = host;
        this.port = port;
    }

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
        if (message instanceof Exception) {
            throw (Exception) message;
        } else if (message.equals("CONNECT")) {
            central = getSender();
            connect();
            getSender().tell("CONNECTED", getSelf());
        } else if ("Tick".equals(message)) {
            log.info("Connection Status:" + client.isConnected());
            if (!client.isConnected()) {
                throw new Exception("Conexao morreu");
            }
        } else if (message instanceof Envelope) {
            sendMenssage((Envelope) message);
        }
    }

    private void sendMenssage(Envelope envelope) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(envelope.getQos());
        message.setRetained(true);
        message.setPayload(envelope.toJson().getBytes());
        client.publish(envelope.getDestino(), message);

    }

    private void sendToBroker(MqttMessage message) throws MqttException {

        String parsedBytes = new String(message.getPayload());
        Envelope envelope = new Gson().fromJson(parsedBytes, Envelope.class);
        log.info("Enviando mensagem para o broker: {}", envelope.getTipoMensagem());
        router.route(envelope, getSender());
    }

    private void offline(MqttMessage message) throws MqttException {
        log.info("Controlador Offline");

        log.info(message.getPayload().toString());
    }


    private void connect() throws MqttException {
        log.info("Iniciando MQTTCentral");
        log.info("Conectando no servidor:{}:{}", host, port);

        client = new MqttClient("tcp://" + host + ":" + port, "central");
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(10);
        opts.setWill("central/morreu", "1".getBytes(), 1, true);
        client.setCallback(this);
        client.connect(opts);

        if (tick != null) {
            tick.cancel();
        } else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                    Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        log.info("Status: {}", client.isConnected());


        client.subscribe("controladores/conn/online", 1, (topic, message) -> {
            sendToBroker(message);
        });

        client.subscribe("controladores/conn/offline", 1, (topic, message) -> {
            sendToBroker(message);
        });

        client.subscribe("central/echo", 1, (topic, message) -> {
            sendToBroker(message);
        });

    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Conexao morreu");
        try {
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
            getSelf().tell(e, getSelf());
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
