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

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTServerActor extends UntypedActor implements MqttCallback {

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

    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(CentralMessageBroker.class));
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

            router.route(envelope, getSender());

        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    private void connect() throws MqttException {
        client = new MqttClient("tcp://" + host + ":" + port, "central");
        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(0);
        opts.setWill("central/morreu", "1".getBytes(), 1, true);
        client.setCallback(this);
        client.connect(opts);

        if (tick != null) {
            tick.cancel();
        } else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }


        client.subscribe("controladores/conn/online", 1, (topic, message) -> sendToBroker(message));

        client.subscribe("controladores/conn/offline", 1, (topic, message) -> sendToBroker(message));

        client.subscribe("central/+", 1, (topic, message) -> sendToBroker(message));

        client.subscribe("central/transacoes/+", 1, (topic, message) -> sendToBroker(message));

        client.subscribe("central/alarmes_falhas/+", 1, (topic, message) -> sendToBroker(message));

        client.subscribe("central/troca_plano/+", 1, (topic, message) -> sendToBroker(message));
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

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
