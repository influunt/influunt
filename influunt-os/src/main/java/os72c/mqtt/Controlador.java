package os72c.mqtt;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.fusesource.mqtt.client.MQTTException;
import os72c.central.CentralActor;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 7/7/16.
 */
public class Controlador extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef mqqtControlador;

    private static SupervisorStrategy strategy =
        new OneForOneStrategy(1000, Duration.Undefined(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    System.out.println("ERRO!!******************");
                    return SupervisorStrategy.stop();
                }
        },false);


    @Override
    public void preStart() throws Exception {
        super.preStart();
        setup();
    }

    private void setup() {
        mqqtControlador = getContext().actorOf(Props.create(MQTTControladorActor.class),"ControladorMQTT");
        this.getContext().watch(mqqtControlador);
        mqqtControlador.tell("CONNECT",getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("---------------------------");
        log.info(message.toString());
        log.info("---------------------------");
        if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            log.info("Ele morreu!");
            getContext().system().scheduler().scheduleOnce(Duration.create(30,TimeUnit.SECONDS),getSelf(),"RESTART",getContext().system().dispatcher(),getSelf());
        }else if("RESTART".equals(message)){
            log.info("Devo restartar ele!");
            setup();
        }

    }

    public static void main(String args[]){
        ActorSystem system = ActorSystem.create("InfluuntControlador", ConfigFactory.load());
        system.actorOf(Props.create(Controlador.class),"Controlador");
        system.awaitTermination();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}


