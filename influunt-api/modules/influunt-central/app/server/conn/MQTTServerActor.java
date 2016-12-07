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
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.eclipse.paho.client.mqttv3.*;
import org.fusesource.mqtt.client.QoS;
import protocol.Envelope;
import scala.concurrent.duration.Duration;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static tyrex.util.Configuration.console;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTServerActor extends UntypedActor implements MqttCallback, IMqttMessageListener {

    private final String host;

    private final String port;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Router router;

    private Map<String, Long> contador = new HashMap<String, Long>();

    private MqttClient client;

    private MqttConnectOptions opts;

    private ActorRef central;

    private Cancellable tick;

    private ActorRef messageBroker;

    public MQTTServerActor(final String host, final String port) {
        this.host = host;
        this.port = port;

        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(CentralMessageBroker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);

    }

    @Override
    public void preStart() throws Exception {
        log.info("preStart()");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        client.close();
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        connect();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Exception) {
            throw (Exception) message;
        } else if ("CONNECT".equals(message)) {
            central = getSender();
            connect();
            getSender().tell("CONNECTED", getSelf());
        } else if ("Tick".equals(message)) {
            if (!client.isConnected()) {
                throw new Exception("Conexao morreu");
            }
        } else if (message instanceof Envelope) {
            sendMessage((Envelope) message);
        }
    }

    private void sendMessage(Envelope envelope) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(envelope.getQos());
        message.setRetained(true);
        String publicKey = Controlador.find.byId(UUID.fromString(envelope.getIdControlador())).getControladorPublicKey();
        message.setPayload(envelope.toJsonCriptografado(publicKey).getBytes());
        client.publish(envelope.getDestino(), message);
    }

    private void sendToBroker(MqttMessage message) throws MqttException {
        try {
            String parsedBytes = new String(message.getPayload());
            Map msg = new Gson().fromJson(parsedBytes, Map.class);
            String privateKey = Controlador.find.byId(UUID.fromString(msg.get("idControlador").toString())).getCentralPrivateKey();
            Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(msg, privateKey), Envelope.class);

            router.route(envelope, getSelf());

        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (com.google.gson.JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    private void connect() throws MqttException {
        client = new MqttClient("tcp://" + host + ":" + port, "central");
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(0);
        client.setCallback(this);
        client.connect(opts);

        if (tick != null) {
            tick.cancel();
        } else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        subscribe("controladores/conn/online");
        subscribe("controladores/conn/offline");
        subscribe("central/transacoes/+");
        subscribe("central/alarmes_falhas/+");
        subscribe("central/troca_plano/+");
        subscribe("central/+");

    }

    public void subscribe(String route) throws MqttException {
//        client.subscribe(route, QoS.EXACTLY_ONCE.ordinal(), (topic, message) -> sendToBroker(message));
        client.subscribe(route, QoS.EXACTLY_ONCE.ordinal(), this);

    }

    @Override
    public void connectionLost(Throwable cause) {
        try {
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
            getSelf().tell(e, getSelf());
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("messageArrived" + topic);
        System.out.println("messageArrived" + message);
        sendToBroker(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery complete:" + token);
    }

}
