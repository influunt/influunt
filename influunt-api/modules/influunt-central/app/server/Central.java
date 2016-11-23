package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Application;
import play.Configuration;
import play.Environment;
import play.Logger;
import server.conn.ServerActor;

@Singleton
public class Central {

    private final Configuration mqttSettings;

    private final ActorSystem system;

    private ActorRef servidor;


    @Inject
    public Central(Environment environment, ActorSystem system, Configuration configuration) {//, ActorSystem system,Configuration configuration ) {

        this.system = system;
        mqttSettings = configuration.getConfig("central").getConfig("mqtt");
        servidor = system.actorOf(Props.create(ServerActor.class,
            mqttSettings.getString("host"),
            mqttSettings.getString("port")), "servidor");
        System.out.println("\nConfiguração MQTT central:\n  host: " + mqttSettings.getString("host") + "\n  port: " + mqttSettings.getString("port") + "\n");

    }

    public void finish() {
        system.terminate();
    }
}
