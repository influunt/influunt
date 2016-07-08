package os72c;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.central.CentralActor;

import os72c.supervisores.Supervisor;

public class Application {
    public static Config conf72c;
    public static void main(String[] args) {
        conf72c = ConfigFactory.load().getConfig("72c");


        // Create an Akka system
        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());

        // Create an actor that handles cluster domain eOvents
        system.actorOf(Props.create(CentralActor.class),
                "clusterListener");
        system.awaitTermination();
    }

}
