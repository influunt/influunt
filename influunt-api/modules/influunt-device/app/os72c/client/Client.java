package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.client.conf.DeviceConfig;
import os72c.client.conn.ClientActor;
import os72c.client.device.DeviceBridge;
import os72c.client.storage.MapStorage;
import os72c.client.storage.Storage;
import os72c.client.storage.StorageConf;
import os72c.client.storage.TestStorageConf;
import play.Application;
import play.Logger;
import play.api.Play;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.HashMap;
import java.util.Map;

import static play.inject.Bindings.bind;


public class Client {


    private static Config config72c;

    private final ActorSystem system;

    private final String host;

    private final String port;

    private final String id;

    private final String centralPublicKey;

    private final String privateKey;

    private ActorRef servidor;

    private Storage storage = Play.current().injector().instanceOf(Storage.class);

    private DeviceBridge device;


    public Client(DeviceConfig deviceConfig) {
        Logger.info("Iniciando O 72C");
        Config configuration = ConfigFactory.load();
        config72c = configuration.getConfig("72c");
        this.system = ActorSystem.create("InfluuntSystem", configuration);

        Logger.info(String.format("Subsistema Akka:%s", this.system.name()));

        if (deviceConfig != null) {
            Logger.info(String.format("Configuração Baseada em Classe:%s", deviceConfig.getClass().getName()));
            host = deviceConfig.getHost();
            port = deviceConfig.getPort();
            id = deviceConfig.getDeviceId();
            centralPublicKey = deviceConfig.getCentralPublicKey();
            privateKey = deviceConfig.getPrivateKey();
            device = deviceConfig.getDeviceBridge();
        } else {
            host = config72c.getConfig("mqtt").getString("host");
            port = config72c.getConfig("mqtt").getString("port");
            id = config72c.getString("id");
            centralPublicKey = config72c.getConfig("seguranca").getString("chavePublica");
            privateKey = config72c.getConfig("seguranca").getString("chavePrivada");

            try {
                Class<DeviceBridge> deviceClass = (Class<DeviceBridge>) Class.forName(config72c.getConfig("bridge").getString("type"));
                this.device = deviceClass.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        Logger.info(String.format("ID CONTROLADOR  :%s", id));
        Logger.info(String.format("MQTT HOST       :%s", host));
        Logger.info(String.format("MQTT PORT       :%s", port));
        Logger.info(String.format("CHAVE PUBLICA   :%s...%s", centralPublicKey.substring(0, 5), centralPublicKey.substring(centralPublicKey.length() - 5, centralPublicKey.length())));
        Logger.info(String.format("CHAVE PRIVADA   :%s...%s", privateKey.substring(0, 5), privateKey.substring(centralPublicKey.length() - 5, centralPublicKey.length())));
        Logger.info(String.format("DEVICE BRIDGE   :%s", device.getClass().getName()));

        servidor = system.actorOf(Props.create(ClientActor.class, id, host, port, centralPublicKey, privateKey, storage, device), id);

    }


    public static Config getConfig() {
        return config72c;
    }

    public static void main(String args[]) {
        Application app = createApplication(new HashMap());
        Play.start(app.getWrappedApplication());
        Materializer mat = app.getWrappedApplication().materializer();
        new Client(null);
    }

    public static Application createApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
            .overrides(bind(StorageConf.class).to(TestStorageConf.class).in(Singleton.class))
            .overrides(bind(Storage.class).to(MapStorage.class).in(Singleton.class))
            .build();
    }

    public void finish() {
        system.terminate();
    }
}
