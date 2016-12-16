package server.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.pattern.Backoff;
import akka.pattern.BackoffSupervisor;
import akka.routing.RoundRobinPool;
import handlers.PacoteTransacaoManagerActorHandler;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


/**
 * Created by rodrigosol on 7/7/16.
 */
public class ServerActor extends UntypedActor {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger("CENTRAL");

    private static OneForOneStrategy strategy =
        new OneForOneStrategy(-1, Duration.Inf(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (t instanceof org.eclipse.paho.client.mqttv3.MqttException && t.getCause() instanceof java.net.ConnectException) {
                        logger.info("MQTT perdeu a conexão com o broker. Restartando ator.");
                        return SupervisorStrategy.stop();
                    } else {
                        logger.error("Ocorreceu um erro no processamento de mensagens. a mensagem será desprezada");
                        return SupervisorStrategy.resume();
                    }
                }
            }, false);

    private final String mqttHost;

    private final String mqttPort;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqttCentral;

    private ActorRef router;

    private ActorRef actorPacoteTrasancaoManager;

    private Props mqttProps;

    public ServerActor(final String mqttHost, final String mqttPort) {
        this.mqttHost = mqttHost;
        this.mqttPort = mqttPort;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        setup();
    }

    private void setup() {

        actorPacoteTrasancaoManager = getContext().actorOf(Props.create(PacoteTransacaoManagerActorHandler.class), "actorPacoteTransacaoManager");

        router = getContext().actorOf(new RoundRobinPool(5).props(Props.create(CentralMessageBroker.class, actorPacoteTrasancaoManager)), "centralMessageBroker");

        mqttProps = BackoffSupervisor.props(Backoff.onFailure(
            Props.create(MQTTServerActor.class, mqttHost, mqttPort, router),
            "CentralMQTT",
            Duration.create(1, TimeUnit.SECONDS),
            Duration.create(10, TimeUnit.SECONDS),
            0).withSupervisorStrategy(strategy));

        startMQTT();
    }

    private void startMQTT() {
        mqttCentral = getContext().actorOf(mqttProps, "CentralMQTT");
        this.getContext().watch(mqttCentral);
        mqttCentral.tell("CONNECT", getSelf());
    }

    @Override
    public void postStop() throws Exception {
        getContext().stop(actorPacoteTrasancaoManager);
        getContext().stop(router);
        getContext().stop(mqttCentral);
        super.postStop();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            getContext().system().scheduler().scheduleOnce(Duration.create(30, TimeUnit.SECONDS), getSelf(), "RESTART", getContext().system().dispatcher(), getSelf());
        } else if ("RESTART".equals(message)) {
            startMQTT();
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


}


