//package server.conn;
//
//import akka.actor.ActorRef;
//import akka.actor.Cancellable;
//import akka.actor.UntypedActor;
//import akka.event.Logging;
//import akka.event.LoggingAdapter;
//import akka.routing.Router;
//import com.google.gson.Gson;
//import models.Controlador;
//import models.ControladorFisico;
//import org.eclipse.paho.client.mqttv3.*;
//import org.fusesource.mqtt.client.QoS;
//import protocol.Envelope;
//import scala.concurrent.duration.Duration;
//import utils.EncryptionUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by rodrigosol on 7/7/16.
// */
//public class MQTTAsyncServerActor extends UntypedActor implements MqttCallback, IMqttMessageListener {
//
//    private final String host;
//
//    private final String port;
//
//    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
//
//    private Router router;
//
//    private MqttAsyncClient client;
//
//    private MqttConnectOptions opts;
//
//    private Cancellable tick;
//
//    private ActorRef messageBroker;
//
//    public MQTTAsyncServerActor(final String host, final String port, final Router router) {
//        this.host = host;
//        this.port = port;
//        this.router = router;
//    }
//
//    @Override
//    public void preStart() throws Exception {
//        log.info("preStart()");
//        super.preStart();
//    }
//
//    @Override
//    public void postStop() throws Exception {
//        super.postStop();
//        client.close();
//    }
//
//    @Override
//    public void postRestart(Throwable reason) throws Exception {
//        super.postRestart(reason);
//        connect();
//    }
//
//    @Override
//    public void onReceive(Object message) throws Exception {
//        if (message instanceof Exception) {
//            throw (Exception) message;
//        } else if ("CONNECT".equals(message)) {
//            central = getSender();
//            connect();
//            getSender().tell("CONNECTED", getSelf());
//        } else if ("Tick".equals(message)) {
//            if (!client.isConnected()) {
//                throw new Exception("Conexao morreu");
//            }
//        } else if (message instanceof Envelope) {
//            sendMessage((Envelope) message);
//        }
//    }
//
//    private void sendMessage(Envelope envelope)  {
//        try {
//            MqttMessage message = new MqttMessage();
//            message.setQos(envelope.getQos());
//            message.setRetained(true);
//            //String publicKey = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador())).getControladorPublicKey();
//            String publicKey = Controlador.find.byId(UUID.fromString(envelope.getIdControlador())).getVersaoControlador().getControladorFisico().getControladorPublicKey();
//            message.setPayload(envelope.toJsonCriptografado(publicKey).getBytes());
//            client.publish(envelope.getDestino(), message);
//
//        }catch (Exception e){
//            getSelf().tell(e,getSelf());
//        }
//    }
//
//    private void sendToBroker(MqttMessage message) {
//        try {
//            String parsedBytes = new String(message.getPayload());
//            Map msg = new Gson().fromJson(parsedBytes, Map.class);
//            String privateKey = ControladorFisico.find.byId(UUID.fromString(msg.get("idControlador").toString())).getCentralPrivateKey();
//            Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(msg, privateKey), Envelope.class);
//
//            router.route(envelope, getSelf());
//
//        } catch (Exception e) {
//            getSelf().tell(e,getSelf());
//        }
//
//    }
//
//    private void connect() throws MqttException {
//        client = new MqttClient("tcp://" + host + ":" + port, "central");
//        opts = new MqttConnectOptions();
//        opts.setAutomaticReconnect(false);
//        opts.setConnectionTimeout(0);
//        client.setCallback(this);
//        client.connect(opts);
//
//        if (tick != null) {
//            tick.cancel();
//        } else {
//            tick = getContext().system().scheduler().schedule(Duration.Zero(),
//                Duration.create(5000, TimeUnit.MILLISECONDS), getSelf(), "Tick", getContext().dispatcher(), null);
//        }
//
//        subscribe("controladores/conn/online", QoS.AT_LEAST_ONCE.ordinal());
//        subscribe("controladores/conn/offline", QoS.AT_LEAST_ONCE.ordinal());
//        subscribe("central/transacoes/+", QoS.EXACTLY_ONCE.ordinal());
//        subscribe("central/alarmes_falhas", QoS.EXACTLY_ONCE.ordinal());
//        subscribe("central/troca_plano", QoS.EXACTLY_ONCE.ordinal());
//        subscribe("central/configuracao", QoS.EXACTLY_ONCE.ordinal());
//        subscribe("central/mudanca_status_controlador", QoS.AT_LEAST_ONCE.ordinal());
//        subscribe("central/info", QoS.AT_MOST_ONCE.ordinal());
//    }
//
//    public void subscribe(String route, int qos) throws MqttException {
//        client.subscribe(route, qos, (topic, message) -> sendToBroker(message));
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        try {
//            connect();
//        } catch (MqttException e) {
//            e.printStackTrace();
//            getSelf().tell(e, getSelf());
//        }
//
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        System.out.println("messageArrived: " + topic);
//        System.out.println("messageArrived: " + message);
//        sendToBroker(message);
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println("Delivery complete:" + token);
//    }
//
//}
