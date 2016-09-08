package integracao;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import os72c.client.Client;
import play.Application;
import play.Configuration;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import server.Central;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 9/8/16.
 */
public class ComunicacaoDispositivoCentralTest extends WithApplication{

    private static Server mqttBroker;
    private Central central;
    private static CompletableFuture centralConectadaFuture = new CompletableFuture<>();
    private static CompletableFuture topicosFuture = new CompletableFuture<>();


    @BeforeClass
    public static  void setupMQTT() throws IOException {
        final IConfig classPathConfig = new MemoryConfig(new Properties());
        List<? extends InterceptHandler> userHandlers = asList(new InterceptHandler() {
            @Override
            public void onConnect(InterceptConnectMessage interceptConnectMessage) {
                centralConectadaFuture.complete(interceptConnectMessage.getClientID());
                System.out.println("ON CONNECT");
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                System.out.println("ON DISCONNECT");
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                System.out.println("ON PUBLISH");
            }

            @Override
            public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {
                System.out.println("ON SUBSCRIBE");
                topicosFuture.complete(interceptSubscribeMessage.getTopicFilter());
            }

            @Override
            public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {
                System.out.println("ON UNSUBSCRIBE");
            }
        });

        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
    }

    @AfterClass
    public static void stopMQTT(){
        mqttBroker.stopServer();
    }

    @Override
    protected Application provideApplication() {
        Map<String, String> dbOptions = new HashMap<String, String>();
        dbOptions.put("DATABASE_TO_UPPER", "FALSE");
        Map<String, String> abstractAppOptions = inMemoryDatabase("default", dbOptions);
        Map<String, String> appOptions = new HashMap<String, String>();
        appOptions.put("db.default.driver", abstractAppOptions.get("db.default.driver"));
        appOptions.put("db.default.url", abstractAppOptions.get("db.default.url"));
        appOptions.put("play.evolutions.db.default.enabled", "false");
        appOptions.put("central.mqtt.host","127.0.0.1");
        appOptions.put("central.mqtt.port","1883");
        return getApplication(appOptions);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .in(Mode.TEST).build();
    }

    @Before
    public void setup(){
        central = app.injector().instanceOf(Central.class);
    }

    @Test

    public void centralDeveSeConectarAoServidorMQTT () throws ExecutionException, InterruptedException {
        //A central ao se conectar no servidor deve se inscrever em diversos tópicos

        //A central conectou
        assertEquals("central", centralConectadaFuture.get());

        //A central se increveu para receber informação de quando um controlador fica online
        assertEquals("controladores/conn/online", topicosFuture.get());

        //A central se increveu para receber informação de quando um controlador fica offline
        topicosFuture = new CompletableFuture<>();
        assertEquals("controladores/conn/offline", topicosFuture.get());

        //A central se increveu para receber informação de echo
        topicosFuture = new CompletableFuture<>();
        assertEquals("central/echo", topicosFuture.get());

        Map<String, Object> appOptions = new HashMap<String, Object>();
        appOptions.put("device.mqtt.host","127.0.0.1");
        appOptions.put("device.mqtt.port","1883");
        appOptions.put("device.id","1234");

        centralConectadaFuture = new CompletableFuture<>();
        Runnable task = () -> {
            try {
                Client c = new Client(new Configuration(appOptions));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //
        };

        assertEquals("1234", centralConectadaFuture.get());
        System.out.println("o cliente se conectou");



    }
}
