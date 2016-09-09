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
        ;
        System.out.println("Iniciando a central");
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


//package os72c.client;
//
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.actor.Props;
//import akka.event.Logging;
//import akka.event.LoggingAdapter;
//import com.google.inject.Inject;
//import com.google.inject.Singleton;
//import com.typesafe.config.Config;
//import com.typesafe.config.ConfigFactory;
//import os72c.client.conn.ClientActor;
//import os72c.client.supervisores.Supervisor;
//import play.Configuration;
//import play.api.Play;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//
//
//@Singleton
//public class Client {
//
//    private ActorRef cliente;
//    private  Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
//
//
//    @Inject
//    public Client(ActorSystem system) {
//        System.out.println("Iniciando a central");
//        Configuration mqttSettings = configuration.getConfig("central").getConfig("mqtt");
//        cliente = system.actorOf(Props.create(ClientActor.class,
//                mqttSettings.getConfig("device").getString("id"),
//                mqttSettings.getConfig("device").getConfig("mqtt").getString("host"),
//                mqttSettings.getConfig("device").getConfig("mqtt").getString("port")),"cliente");
//    }
//
//}
//
////public class Client {
////    public static play.Configuration conf72c;
////    LoggingAdapter log;
////
////    public Client(play.Configuration config) throws InterruptedException {
////        this.conf72c = config;
////        init();
////
////    }
////    public Client() throws InterruptedException {
////        // Create an Akka system
////    }
////
////    private void init() throws InterruptedException {
////        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());
////        log = Logging.getLogger(system, this);
////        ActorRef supervidor = system.actorOf(Supervisor.props(), "supervisor");
////
////        //Aguarda a conexao de internet durante o boot;
////
////        while (!netIsAvailable()){
////            log.info("INTERNET NÃO DISPONIVEL");
////            Thread.sleep(1000);
////        }
////
////        ActorRef cliente = system.actorOf(Props.create(ClientActor.class,
////                conf72c.getConfig("device").getString("id"),
////                conf72c.getConfig("device").getConfig("mqtt").getString("host"),
////                conf72c.getConfig("device").getConfig("mqtt").getString("port")), "cliente");
////        system.awaitTermination();
////
////    }
////
//////    public static void main(String[] args) throws InterruptedException {
////////        conf72c = ConfigFactory.load().getConfig("72c");
//////        new Client();
//////    }
////
////    private static boolean netIsAvailable() {
////        try {
////            final URL url = new URL("http://www.google.com");
////            final URLConnection conn = url.openConnection();
////            conn.connect();
////            return true;
////        } catch (MalformedURLException e) {
////            throw new RuntimeException(e);
////        } catch (IOException e) {
////            return false;
////        }
////    }
////
////}
