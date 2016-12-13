package os72c.client.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import logger.InfluuntLogger;
import logger.TipoLog;
import os72c.client.device.DeviceActor;
import os72c.client.device.DeviceBridge;
import os72c.client.handlers.TransacaoManagerActorHandler;
import os72c.client.storage.Storage;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class ClientActor extends UntypedActor{

    private static OneForOneStrategy strategy =
        new OneForOneStrategy(-1, Duration.Inf(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (t instanceof org.eclipse.paho.client.mqttv3.MqttException && t.getCause() instanceof java.net.ConnectException) {
                        InfluuntLogger.log(TipoLog.COMUNICACAO,"MQTT perdeu a conexão com o broker. Restartando ator.");
                        return SupervisorStrategy.stop();
                    } else {
                        InfluuntLogger.log(TipoLog.COMUNICACAO,"Ocorreceu um erro no processamento de mensagens. a mensagem será desprezada");
                        return SupervisorStrategy.resume();
                    }
                }
            }, false);

    private final String id;

    private final String host;

    private final String port;

    private final Storage storage;

    private final String centralPublicKey;

    private final String controladorPrivateKey;

    private final String login;

    private final String senha;

    private ActorRef device;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqqtControlador;

    private Router router;

    private ActorRef actorTrasacao;

    public ClientActor(final String id, final String host, final String port, final String login, final String senha, final String centralPublicKey, final String controladorPrivateKey, Storage storage, DeviceBridge deviceBridge) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.login = login;
        this.senha = senha;
        this.storage = storage;
        this.centralPublicKey = centralPublicKey;
        this.controladorPrivateKey = controladorPrivateKey;
        if (storage.getCentralPublicKey() == null) {
            storage.setCentralPublicKey(centralPublicKey);
            storage.setPrivateKey(controladorPrivateKey);
        }

        this.device = getContext().actorOf(Props.create(DeviceActor.class, storage, deviceBridge, id), "motor");
    }


    @Override
    public void preStart() throws Exception {
        super.preStart();
        setup();
    }

    private void setup() {

        actorTrasacao = getContext().actorOf(Props.create(TransacaoManagerActorHandler.class, this.id, storage), "actorTransacao");
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(DeviceMessageBroker.class, this.id, this.storage, actorTrasacao));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);

        mqqtControlador = getContext().actorOf(Props.create(MQTTClientActor.class, id, host, port,login,senha, storage, router), "ControladorMQTT");
        this.getContext().watch(mqqtControlador);
        mqqtControlador.tell("CONNECT", getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            getContext().system().scheduler().scheduleOnce(Duration.create(30, TimeUnit.SECONDS), getSelf(), "RESTART", getContext().system().dispatcher(), getSelf());
        } else if ("RESTART".equals(message)) {
            setup();
        }

    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


}


