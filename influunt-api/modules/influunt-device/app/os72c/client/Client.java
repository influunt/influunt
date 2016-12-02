package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.LoggerFactory;
import os72c.client.conf.DeviceConfig;
import os72c.client.conn.ClientActor;
import os72c.client.device.DeviceBridge;
import logger.InfluuntLogger;
import os72c.client.storage.MapStorage;
import os72c.client.storage.Storage;
import os72c.client.storage.StorageConf;
import os72c.client.storage.TestStorageConf;
import play.Application;
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
    private static org.slf4j.Logger logger = LoggerFactory.getLogger("Client");


    public Client(DeviceConfig deviceConfig) {

        Config configuration = ConfigFactory.load();
        config72c = configuration.getConfig("72c");
        setupLog();

        InfluuntLogger.log("Iniciando O 72C");

        this.system = ActorSystem.create("InfluuntSystem", configuration);

        InfluuntLogger.log(String.format("Subsistema Akka:%s", this.system.name()));

        if (deviceConfig != null) {
            InfluuntLogger.log(String.format("Configuração Baseada em Classe:%s", deviceConfig.getClass().getName()));
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

        InfluuntLogger.log(String.format("ID CONTROLADOR  :%s", id));
        InfluuntLogger.log(String.format("MQTT HOST       :%s", host));
        InfluuntLogger.log(String.format("MQTT PORT       :%s", port));
        InfluuntLogger.log(String.format("CHAVE PUBLICA   :%s...%s", centralPublicKey.substring(0, 5), centralPublicKey.substring(centralPublicKey.length() - 5, centralPublicKey.length())));
        InfluuntLogger.log(String.format("CHAVE PRIVADA   :%s...%s", privateKey.substring(0, 5), privateKey.substring(centralPublicKey.length() - 5, centralPublicKey.length())));
        InfluuntLogger.log(String.format("DEVICE BRIDGE   :%s", device.getClass().getName()));

        servidor = system.actorOf(Props.create(ClientActor.class, id, host, port, centralPublicKey, privateKey, storage, device), id);

    }

    private void setupLog() {
        Config configLog = config72c.getConfig("log");
        if(configLog != null){
            InfluuntLogger.configureLog(configLog.getString("caminho"),
                configLog.getString("arquivo"), configLog.getInt("tamanho"), configLog.getBoolean("compacto"),
                configLog.getAnyRefList("tipoEvento"));
        }

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
