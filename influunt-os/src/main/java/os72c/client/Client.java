package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.client.conn.ClientActor;
import os72c.client.supervisores.Supervisor;

public class Client {
    public static Config conf72c;
    public static void main(String[] args) {
        conf72c = ConfigFactory.load().getConfig("72c");


        // Create an Akka system
        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());

        ActorRef supervidor = system.actorOf(Supervisor.props(), "supervisor");
        ActorRef cliente = system.actorOf(Props.create(ClientActor.class), "cliente");
        system.awaitTermination();
    }

}
