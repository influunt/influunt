package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Configuration;
import play.api.Play;
import server.conn.ServerActor;

@Singleton
public class Central {


    private final Configuration mqttSettings;

    private final ActorSystem system;

    private ActorRef servidor;

    private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    @Inject
    public Central(ActorSystem system) {
        this.system = system;
        System.out.println("Iniciando a central");
        mqttSettings = configuration.getConfig("central").getConfig("mqtt");
        servidor = system.actorOf(Props.create(ServerActor.class,
            mqttSettings.getString("host"),
            mqttSettings.getString("port")), "servidor");

    }


    public Configuration getMqttSettings() {
        return mqttSettings;
    }

    public void finish() {
        system.terminate();
    }
}
