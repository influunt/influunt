package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Configuration;
import play.Environment;
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
