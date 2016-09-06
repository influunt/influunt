package os72c.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import os72c.client.conn.ClientActor;
import os72c.client.supervisores.Supervisor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Client {
    public static Config conf72c;
    LoggingAdapter log;

    public Client() throws InterruptedException {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());
        log = Logging.getLogger(system, this);
        ActorRef supervidor = system.actorOf(Supervisor.props(), "supervisor");

        //Aguarda a conexao de internet durante o boot;

        while (!netIsAvailable()){
            log.info("INTERNET NÃO DISPONIVEL");
            Thread.sleep(1000);
        }

        ActorRef cliente = system.actorOf(Props.create(ClientActor.class), "cliente");
        system.awaitTermination();

    }

    public static void main(String[] args) throws InterruptedException {
        conf72c = ConfigFactory.load().getConfig("72c");
        new Client();
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

}
