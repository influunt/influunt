package integracao;

import com.fasterxml.jackson.databind.JsonNode;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import os72c.client.Client;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import server.Central;
import status.StatusConexaoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 9/8/16.
 */
public class ComunicacaoDispositivoCentralTest extends WithApplication {

    private Server mqttBroker;

    private Central central;

    private CompletableFuture onConnectFuture = new CompletableFuture<>();

    private CompletableFuture onDisconectFuture = new CompletableFuture<>();

    private CompletableFuture onSubscribeFuture = new CompletableFuture<>();

    private CompletableFuture<byte[]> onPublishFuture = new CompletableFuture<>();

    private Client client;

    private PlayJongo jongo;

    @Override
    protected Application provideApplication() {
        Map<String, String> dbOptions = new HashMap<String, String>();
        dbOptions.put("DATABASE_TO_UPPER", "FALSE");
        Map<String, String> abstractAppOptions = inMemoryDatabase("default", dbOptions);
        Map<String, String> appOptions = new HashMap<String, String>();
        appOptions.put("db.default.driver", abstractAppOptions.get("db.default.driver"));
        appOptions.put("db.default.url", abstractAppOptions.get("db.default.url"));
        appOptions.put("play.evolutions.db.default.enabled", "false");
        appOptions.put("central.mqtt.host", "127.0.0.1");
        appOptions.put("central.mqtt.port", "1883");
        appOptions.put("device.mqtt.host", "127.0.0.1");
        appOptions.put("device.mqtt.port", "1883");
        appOptions.put("device.id", "1234");

        return getApplication(appOptions);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .in(Mode.TEST).build();
    }

    @Before
    public void setup() throws IOException {

        final IConfig classPathConfig = new MemoryConfig(new Properties());
        List<? extends InterceptHandler> userHandlers = asList(new InterceptHandler() {
            @Override
            public void onConnect(InterceptConnectMessage interceptConnectMessage) {
                onConnectFuture.complete(interceptConnectMessage.getClientID());
                System.out.println("ON CONNECT");
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                System.out.println("ON DISCONNECT");
                onDisconectFuture.complete(interceptDisconnectMessage.getClientID());
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                System.out.println("ON PUBLISH");
                onPublishFuture.complete(interceptPublishMessage.getPayload().array());
            }

            @Override
            public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {
                System.out.println("ON SUBSCRIBE");
                onSubscribeFuture.complete(interceptSubscribeMessage.getTopicFilter());
            }

            @Override
            public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {
                System.out.println("ON UNSUBSCRIBE");
            }
        });

        jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        StatusConexaoControlador.jongo = jongo;

        jongo.getCollection("status_conexao_controladores").drop();

        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
        central = app.injector().instanceOf(Central.class);
    }

    @After
    public void cleanUp() {
        mqttBroker.stopServer();
        central.finish();
        mqttBroker = null;
        mqttBroker = null;
        onDisconectFuture = null;
        onSubscribeFuture = null;
        System.gc();
    }


    @Test
    public void centralDeveSeConectarAoServidorMQTT() {
        try {
            //A central ao se conectar no servidor deve se inscrever em diversos tópicos

            //A central conectou
            assertEquals("central", onConnectFuture.get(1, TimeUnit.SECONDS));

            //A central se increveu para receber informação de quando um controlador fica online
            assertEquals("controladores/conn/online", onSubscribeFuture.get(1, TimeUnit.SECONDS));

            //A central se increveu para receber informação de quando um controlador fica offline
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("controladores/conn/offline", onSubscribeFuture.get(1, TimeUnit.SECONDS));

            //A central se increveu para receber informação de echo
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("central/echo", onSubscribeFuture.get(1, TimeUnit.SECONDS));

            //O cliente foi intanciado
            client = app.injector().instanceOf(Client.class);
            onConnectFuture = new CompletableFuture();

            //O cliente se conectou
            assertEquals("1234", onConnectFuture.get(1, TimeUnit.SECONDS));

            //O cliente envio a CONTROLADOR_ONLINE
            JsonNode json = play.libs.Json.parse(new String(onPublishFuture.get(1, TimeUnit.SECONDS)));

            assertEquals("1234", json.get("idControlador").asText());
            assertEquals("1234", json.get("idControlador").asText());
            assertEquals("CONTROLADOR_ONLINE", json.get("tipoMensagem").asText());
            assertEquals("controladores/conn/online", json.get("destino").asText());
            assertTrue(json.has("carimboDeTempo"));
            assertTrue(json.has("idMensagem"));
            assertTrue(json.has("conteudo"));

            assertTrue(json.get("conteudo").has("dataHora"));
            assertTrue(json.get("conteudo").has("versao72c"));
            assertTrue(json.get("conteudo").has("status"));
            client.finish();


            //Verificar se o registro da conexao foi salvo
            Thread.sleep(200);
            StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus("1234");
            assertTrue(status.conectado);
            assertEquals(Long.valueOf(json.get("carimboDeTempo").asLong()), status.timestamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail();
        }


    }
}
