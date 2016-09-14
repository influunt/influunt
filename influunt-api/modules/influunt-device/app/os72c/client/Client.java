package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import os72c.client.conf.DeviceConfig;
import os72c.client.conf.TestDeviceConfig;
import os72c.client.conn.ClientActor;
import os72c.client.storage.Storage;
import play.Configuration;
import play.api.Play;


@Singleton
public class Client {


    private final ActorSystem system;

    private final String host;

    private final String port;

    private final String id;

    private Configuration mqttSettings;

    private ActorRef servidor;

    private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    private DeviceConfig deviceConfig = Play.current().injector().instanceOf(DeviceConfig.class);

    private Storage storage = Play.current().injector().instanceOf(Storage.class);


    public Client() {
        this.system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());

        if (deviceConfig instanceof TestDeviceConfig) {
            host = deviceConfig.getHost();
            port = deviceConfig.getPort();
            id = deviceConfig.getDeviceId();
        } else {
            mqttSettings = configuration.getConfig("device");
            host = mqttSettings.getConfig("mqtt").getString("host");
            port = mqttSettings.getConfig("mqtt").getString("port");
            id = mqttSettings.getString("id");
        }

        servidor = system.actorOf(Props.create(ClientActor.class, id, host, port, storage), id);

    }


    public Configuration getMqttSettings() {
        return mqttSettings;
    }

    public void finish() {
        system.terminate();
    }
}