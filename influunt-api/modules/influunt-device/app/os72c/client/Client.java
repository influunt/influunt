package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import logger.InfluuntLogger;
import logger.TipoLog;
import org.slf4j.LoggerFactory;
import os72c.client.conf.DeviceConfig;
import os72c.client.conn.ClientActor;
import os72c.client.device.DeviceBridge;
import os72c.client.storage.*;
import play.Application;
import play.api.Play;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.HashMap;
import java.util.Map;

import static play.inject.Bindings.bind;


public class Client {


    private static Config config72c;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger("Client");

    private final ActorSystem system;

    private final String host;

    private final String port;

    private final String id;

    private final String login;

    private final String senha;

    private final String centralPublicKey;

    private final String privateKey;

    private ActorRef servidor;

    private Storage storage = Play.current().injector().instanceOf(Storage.class);

    private DeviceBridge device;


    public Client(DeviceConfig deviceConfig) {

        Config configuration = ConfigFactory.load();
        config72c = configuration.getConfig("72c");
        setupLog();

        InfluuntLogger.log(TipoLog.INICIALIZACAO,"Iniciando O 72C");

        this.system = ActorSystem.create("InfluuntSystem", configuration);

        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("Subsistema Akka:%s", this.system.name()));

        if (deviceConfig != null) {
            InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("Configuração Baseada em Classe:%s", deviceConfig.getClass().getName()));
            host = deviceConfig.getHost();
            port = deviceConfig.getPort();
            login = deviceConfig.getLogin();
            senha = deviceConfig.getSenha();
            id = deviceConfig.getDeviceId();
            centralPublicKey = deviceConfig.getCentralPublicKey();
            privateKey = deviceConfig.getPrivateKey();
            device = deviceConfig.getDeviceBridge();
        } else {
            host = config72c.getConfig("mqtt").getString("host");
            port = config72c.getConfig("mqtt").getString("port");
            login = config72c.getConfig("mqtt").getString("login");
            senha = config72c.getConfig("mqtt").getString("senha");
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

        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("ID CONTROLADOR  :%s", id));
        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("MQTT HOST       :%s", host));
        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("MQTT PORT       :%s", port));
        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("MQTT LOGIN       :%s", login));
        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("MQTT PWD       :%s", senha));
        InfluuntLogger.log(TipoLog.INICIALIZACAO,String.format("DEVICE BRIDGE   :%s", device.getClass().getName()));

        servidor = system.actorOf(Props.create(ClientActor.class, id, host, port, login, senha, centralPublicKey, privateKey, storage, device), id);

    }

    public static Config getConfig() {
        return config72c;
    }

    public static void main(String args[]) {
        Application app = createApplication(new HashMap(),false);
        Play.start(app.getWrappedApplication());
        Materializer mat = app.getWrappedApplication().materializer();
        new Client(null);
    }

    public static Application createApplication(Map configuration,boolean recreate) {
        Class klass = recreate ? RecreateDiskStorageConf.class : DiskStorageConf.class;
        return new GuiceApplicationBuilder().configure(configuration)
            .overrides(bind(StorageConf.class).to(klass).in(Singleton.class))
            .overrides(bind(Storage.class).to(MapStorage.class).in(Singleton.class)).build();
    }

    private void setupLog() {
        Config configLog = config72c.getConfig("log");
        if (configLog != null) {
            InfluuntLogger.configureLog(configLog.getString("caminho"),
                configLog.getString("arquivo"), configLog.getInt("tamanho"), configLog.getBoolean("compacto"),
                configLog.getAnyRefList("tipoEvento"));
        }

    }

    public void finish() {
        system.terminate();
    }
}
