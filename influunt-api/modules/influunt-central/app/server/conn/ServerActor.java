package server.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.pattern.Backoff;
import akka.pattern.BackoffOptions;
import akka.pattern.BackoffSupervisor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import ch.qos.logback.classic.Logger;


import org.fusesource.mqtt.client.MQTTException;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
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
                    if(t instanceof org.eclipse.paho.client.mqttv3.MqttException && t.getCause() instanceof java.net.ConnectException){
                        logger.info("MQTT perdeu a conexão com o broker. Restartando ator.");
                        return SupervisorStrategy.stop();
                    }else {
                        logger.error("Ocorreceu um erro no processamento de mensagens. a mensagem será desprezada");
                        t.printStackTrace();
                        return SupervisorStrategy.resume();
                    }
                }
            }, false);

    private final String mqttHost;

    private final String mqttPort;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqttCentral;

    private Router router;


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

        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(CentralMessageBroker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }

        router = new Router(new RoundRobinRoutingLogic(), routees);


         Props mqttProps = BackoffSupervisor.props(Backoff.onFailure(
            Props.create(MQTTServerActor.class, mqttHost, mqttPort,router),
            "CentralMQTT",
            Duration.create(1, TimeUnit.SECONDS),
            Duration.create(10, TimeUnit.SECONDS),
            0).withSupervisorStrategy(strategy));



        mqttCentral = getContext().actorOf(mqttProps,"CentralMQTT");
        this.getContext().watch(mqttCentral);
        mqttCentral.tell("CONNECT", getSelf());
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


