package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.Configuration;
import server.conn.ServerActor;

@Singleton
public class Server {


    private ActorRef servidor;

    private Configuration configuration;



    @Inject
    public Server(ActorSystem system) {
        servidor = system.actorOf(Props.create(ServerActor.class), "servidor");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
