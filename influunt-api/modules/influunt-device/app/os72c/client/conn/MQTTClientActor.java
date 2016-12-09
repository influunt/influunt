package os72c.client.conn;


import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.routing.Router;
import com.google.gson.Gson;
import logger.InfluuntLogger;
import org.eclipse.paho.client.mqttv3.*;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;
import os72c.client.protocols.Mensagem;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import os72c.client.storage.Storage;
import protocol.ControladorOffline;
import protocol.ControladorOnline;
import protocol.Envelope;
import scala.concurrent.duration.Duration;
import utils.EncryptionUtil;
import utils.GzipUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class MQTTClientActor extends UntypedActor implements MqttCallback, IMqttMessageListener {

    private final String host;

    private final String port;


    private Router router;

    private String id;

    private MqttClient client;

    private MqttConnectOptions opts;

    private ActorRef controlador;

    private Cancellable tick;

    private Storage storage;

    public MQTTClientActor(final String id, final String host, final String port, Storage storage, Router router) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.storage = storage;
        this.router = router;

        InfluuntLogger.logger.info("Iniciando a comunicacao MQTT");
        InfluuntLogger.logger.info("Criando referencia para o messagebroker");
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
            sendMessage((Envelope) message);
        }
    }

    private void connect() throws MqttException, IOException {
        try {
            client = new MqttClient("tcp://" + host + ":" + port, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(false);
        opts.setCleanSession(false);
        opts.setConnectionTimeout(0);

        Envelope controladorOffline = ControladorOffline.getMensagem(id);

        opts.setWill(controladorOffline.getDestino(), GzipUtil.compress(controladorOffline.toJsonCriptografado(storage.getCentralPublicKey())), 1, false);


        client.setCallback(this);
        client.connect(opts);
        if (tick != null) {
            tick.cancel();
        } else {
            tick = getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
        }

        client.subscribe("controlador/" + id + "/+", QoS.EXACTLY_ONCE.ordinal(), this);

        Envelope controladorOnline = ControladorOnline.getMensagem(id, DateTime.now().getMillis(), "1.0", storage.getStatus());
        sendMessage(controladorOnline);
        sendToBroker(new MensagemVerificaConfiguracao());
    }


    private void sendToBroker(MqttMessage message) {
        try {
            String parsedBytes = GzipUtil.decompress(message.getPayload());

            Map msg = new Gson().fromJson(parsedBytes, Map.class);

            String privateKey = storage.getPrivateKey();

            Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(msg, privateKey), Envelope.class);
            router.route(envelope, getSender());
        } catch (Exception e) {
            getSelf().tell(e, getSelf());
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
        sendToBroker(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void sendMessage(Envelope envelope) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(envelope.getQos());
            message.setRetained(false);
            String publicKey = storage.getCentralPublicKey();
            message.setPayload(GzipUtil.compress(envelope.toJsonCriptografado(publicKey)));
            client.publish(envelope.getDestino(), message);
        } catch (Exception e) {
            getSelf().tell(e, getSelf());
        }
    }
}
