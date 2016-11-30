package os72c.client.conn;

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
import org.apache.commons.codec.DecoderException;
import org.eclipse.paho.client.mqttv3.*;
import os72c.client.protocols.Mensagem;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import os72c.client.storage.Storage;
import play.Logger;
import protocol.ControladorOffline;
import protocol.ControladorOnline;
import protocol.Envelope;
import scala.concurrent.duration.Duration;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTClientActor extends UntypedActor implements MqttCallback {

    private final String host;

    private final String port;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Router router;

    private String id;

    private MqttClient client;

    private MqttConnectOptions opts;

    private ActorRef controlador;

    private Cancellable tick;

    private Storage storage;

    public MQTTClientActor(final String id, final String host, final String port, Storage storage) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.storage = storage;

        Logger.info("Iniciando a comunicação MQTT");
        Logger.info("Criando referência para o messagebroker");

        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(DeviceMessageBroker.class, this.id, this.storage));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void aroundPostStop() {
        super.aroundPostStop();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
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
            controlador = getSender();
            connect();
            getSender().tell("CONNECTED", getSelf());
        } else if ("Tick".equals(message)) {
            if (!client.isConnected()) {
                throw new Exception("Conexao morreu");
            }
        } else if (message instanceof Envelope) {
            sendMenssage((Envelope) message);
        }
    }

    private void connect() throws MqttException {
        try {
            client = new MqttClient("tcp://" + host + ":" + port, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setConnectionTimeout(0);

        Envelope controladorOffline = ControladorOffline.getMensagem(id);

        opts.setWill(controladorOffline.getDestino(), controladorOffline.toJsonCriptografado(storage.getCentralPublicKey()).getBytes(), 1, true);

        client.setCallback(this);
        client.connect(opts);
        if (tick != null) {
            tick.cancel();
        } else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        client.subscribe("controlador/" + id + "/+", 1, (topic, message) -> {
            sendToBroker(message);
        });

        Envelope controladorOnline = ControladorOnline.getMensagem(id, System.currentTimeMillis(), "1.0", storage.getStatus());
        sendMenssage(controladorOnline);
        sendToBroker(new MensagemVerificaConfiguracao());
    }


    private void sendToBroker(MqttMessage message) throws MqttException {
        String parsedBytes = new String(message.getPayload());

        Map msg = new Gson().fromJson(parsedBytes, Map.class);
        Logger.info("Mensagem recebida da central:");

        String privateKey = storage.getPrivateKey();
        try {
            Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(msg, privateKey), Envelope.class);
            Logger.info(envelope.toJson());
            router.route(envelope, getSender());
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    private void sendToBroker(Mensagem message) throws MqttException {
        router.route(message, getSender());
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

    private void sendMenssage(Envelope envelope) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(envelope.getQos());
        message.setRetained(true);
        String publicKey = storage.getCentralPublicKey();
        Logger.info("Enviando mensagem para a central:");
        Logger.info(envelope.toJson());
        message.setPayload(envelope.toJsonCriptografado(publicKey).getBytes());
        client.publish(envelope.getDestino(), message);
    }
}
