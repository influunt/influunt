package os72c.client.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class ClientActor extends UntypedActor {

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(-1, Duration.Undefined(),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable t) {
                            System.out.println("ERRO!!******************");
                            return SupervisorStrategy.stop();
                        }
                    }, false);

    private final String id;

    private final String host;

    private final String port;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef mqqtControlador;

    public ClientActor(final String id, final String host, final String port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public static void main(String args[]) {
        ActorSystem system = ActorSystem.create("InfluuntControlador", ConfigFactory.load());
        system.actorOf(Props.create(ClientActor.class), "ClientActor");
        system.awaitTermination();
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        setup();
    }

    private void setup() {
        mqqtControlador = getContext().actorOf(Props.create(MQTTClientActor.class, id, host, port), "ControladorMQTT");
        this.getContext().watch(mqqtControlador);
        mqqtControlador.tell("CONNECT", getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("---------------------------");
        log.info(message.toString());
        log.info("---------------------------");
        if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            log.info("Ele morreu!");
            getContext().system().scheduler().scheduleOnce(Duration.create(30, TimeUnit.SECONDS), getSelf(), "RESTART", getContext().system().dispatcher(), getSelf());
        } else if ("RESTART".equals(message)) {
            log.info("Devo restartar ele!");
            setup();
        }

    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}


