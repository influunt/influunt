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
import logger.NivelLog;
import logger.TipoLog;
import os72c.client.device.DeviceActor;
import os72c.client.device.DeviceBridge;
import os72c.client.handlers.TransacaoManagerActorHandler;
import os72c.client.observer.EstadoDevice;
import os72c.client.storage.Storage;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class ClientActor extends UntypedActor {

    private static OneForOneStrategy strategy =
        new OneForOneStrategy(-1, Duration.Inf(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (
                        (t.getMessage() != null && "Conexao morreu".equals(t.getMessage())) ||
                            (t.getMessage() != null && "Client is not connected".startsWith(t.getMessage())) ||
                            (t.getCause() != null && t.getCause().getClass() != null && t.getCause().getClass().equals(java.net.UnknownHostException.class)) ||
                            (t instanceof org.eclipse.paho.client.mqttv3.MqttException && t.getCause() instanceof java.net.ConnectException)) {
                        InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.COMUNICACAO, "MQTT perdeu a conexão com o broker. Restartando ator.");
                        return SupervisorStrategy.stop();
                    } else if (t instanceof RuntimeException && "RESTART".equals(t.getMessage())) {
                        return SupervisorStrategy.restart();
                    } else {
                        InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.COMUNICACAO, "Ocorreu um erro no processamento de mensagens. a mensagem será desprezada");
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

    private final ActorRef deadLetters;

    private final EstadoDevice estadoDevice;

    private ActorRef device;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqqtControlador;

    private Router router;

    private ActorRef actorTransacao;

    public ClientActor(final String id, final String host, final String port, final String login,
                       final String senha, final String centralPublicKey, final String controladorPrivateKey,
                       Storage storage, DeviceBridge deviceBridge, EstadoDevice estadoDevice, ActorSystem system) {
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

        this.estadoDevice = estadoDevice;

        InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.INICIALIZACAO, String.format("CHAVE PUBLICA   :%s...%s", storage.getCentralPublicKey().substring(0, 5), storage.getCentralPublicKey().substring(storage.getCentralPublicKey().length() - 5, storage.getCentralPublicKey().length())));
        InfluuntLogger.log(NivelLog.DETALHADO, TipoLog.INICIALIZACAO, String.format("CHAVE PRIVADA   :%s...%s", storage.getPrivateKey().substring(0, 5), storage.getPrivateKey().substring(storage.getPrivateKey().length() - 5, storage.getPrivateKey().length())));


        this.device = getContext().actorOf(Props.create(DeviceActor.class, storage, deviceBridge, id, estadoDevice, system), "motor");
        this.deadLetters = getContext().actorOf(Props.create(DeadLettersActor.class, storage), "DeadLettersActor");
        getContext().system().eventStream().subscribe(this.deadLetters, DeadLetter.class);
    }


    @Override
    public void preStart() throws Exception {
        super.preStart();
        setup();
    }

    private void setup() {
        actorTransacao = getContext().actorOf(Props.create(TransacaoManagerActorHandler.class, this.id, storage), "actorTransacao");
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(DeviceMessageBroker.class, this.id, this.storage, actorTransacao));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);

        startMQTT();
    }

    private void startMQTT() {
        mqqtControlador = getContext().actorOf(Props.create(MQTTClientActor.class, id, host, port,
            login, senha, storage, router, estadoDevice), "ControladorMQTT");
        this.getContext().watch(mqqtControlador);
        mqqtControlador.tell("CONNECT", getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Terminated) {
            getContext().system().scheduler().scheduleOnce(Duration.create(30, TimeUnit.SECONDS), getSelf(), "RESTART", getContext().system().dispatcher(), getSelf());
            estadoDevice.setConectado(false);
        } else if ("RESTART".equals(message)) {
            startMQTT();
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


    @Override
    public void postStop() throws Exception {
        super.postStop();
    }
}


