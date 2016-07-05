package os72c;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.supervisores.Supervisor;

public class Application {
    public static Config conf72c;
    public static void main(String[] args) {
        conf72c = ConfigFactory.load().getConfig("72c");
        ActorSystem system = ActorSystem.create("InfluuntSystem");
        ActorRef supervidor = system.actorOf(Supervisor.props(), "supervisor");

        system.awaitTermination();
    }

}
