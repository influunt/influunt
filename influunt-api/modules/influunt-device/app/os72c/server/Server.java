package os72c.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.server.conn.ServerActor;

public class Server {
    public static Config conf72c;
    public static void main(String[] args) {
        conf72c = ConfigFactory.load().getConfig("72c");


        // Create an Akka system
        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());
        ActorRef servidor = system.actorOf(Props.create(ServerActor.class), "servidor");

        system.awaitTermination();
    }

}
