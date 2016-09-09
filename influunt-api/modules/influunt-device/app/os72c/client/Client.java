package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import os72c.client.conn.ClientActor;
import play.Configuration;
import play.api.Play;


@Singleton
public class Client {


    private final Configuration mqttSettings;

    private final ActorSystem system;

    private ActorRef servidor;

    private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);


    public Client() {
        this.system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());

        mqttSettings = configuration.getConfig("device");
        servidor = system.actorOf(Props.create(ClientActor.class,
                mqttSettings.getString("id"),
                mqttSettings.getConfig("mqtt").getString("host"),
                mqttSettings.getConfig("mqtt").getString("port")), mqttSettings.getString("id"));

    }


    public Configuration getMqttSettings() {
        return mqttSettings;
    }

    public void finish() {
        system.terminate();
    }
}