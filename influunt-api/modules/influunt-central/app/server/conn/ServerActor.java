package server.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class ServerActor extends UntypedActor {

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(1000, Duration.Undefined(),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable t) {
                            return SupervisorStrategy.stop();
                        }
                    }, false);

    private final String mqttHost;

    private final String mqttPort;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqqtCentral;


    public ServerActor(final String mqttHost, final String mqttPort) {
        this.mqttHost = mqttHost;
        this.mqttPort = mqttPort;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("PreStart");
        setup();
    }

    private void setup() {
        mqqtCentral = getContext().actorOf(Props.create(MQTTServerActor.class, mqttHost, mqttPort), "CentralMQTT");
        this.getContext().watch(mqqtCentral);
        mqqtCentral.tell("CONNECT", getSelf());
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


