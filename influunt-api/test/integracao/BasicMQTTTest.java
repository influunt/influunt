package integracao;

import checks.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.dbmigration.DdlGenerator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplication;
import config.WithInfluuntApplicationNoAuthentication;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import models.Controlador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import os72c.client.Client;
import os72c.client.conf.DeviceConfig;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.test.WithApplication;
import server.Central;
import status.StatusConexaoControlador;
import uk.co.panaxiom.playjongo.PlayJongo;

import javax.validation.groups.Default;
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
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 9/8/16.
 */
public class BasicMQTTTest extends WithInfluuntApplicationNoAuthentication {

    protected Server mqttBroker;

    protected Central central;

    protected CompletableFuture onConnectFuture = new CompletableFuture<>();

    protected CompletableFuture onDisconectFuture = new CompletableFuture<>();

    protected CompletableFuture onSubscribeFuture = new CompletableFuture<>();

    protected CompletableFuture<byte[]> onPublishFuture = new CompletableFuture<>();

    protected Client client;

    protected PlayJongo jongo;

    protected Controlador controlador;

    protected Integer TIMEOUT = 10;

    protected String idControlador;

    protected byte[] resp;

//

    @Before
    public void setup() throws IOException, InterruptedException {
        controlador = new ControladorHelper().getControlador();
        idControlador = controlador.getId().toString();
        provideApp.injector().instanceOf(DeviceConfig.class).setDeviceId(controlador.getId().toString());

        Properties properties = new Properties();
        properties.put("persistence", false);
        final IConfig classPathConfig = new MemoryConfig(properties);
        List<? extends InterceptHandler> userHandlers = asList(new InterceptHandler() {
            @Override
            public void onConnect(InterceptConnectMessage interceptConnectMessage) {
                onConnectFuture.complete(interceptConnectMessage.getClientID());
                System.out.println("ON CONNECT");
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                onDisconectFuture.complete(interceptDisconnectMessage.getClientID());
                System.out.println("ON DISCONNECT");
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                onPublishFuture.complete(interceptPublishMessage.getPayload().array());
                System.out.println("ON PUBLISH");
            }

            @Override
            public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {
                onSubscribeFuture.complete(interceptSubscribeMessage.getTopicFilter());
                System.out.println("ON SUBSCRIBE");
            }

            @Override
            public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {
                System.out.println("ON UNSUBSCRIBE");
            }
        });

        jongo = provideApp.injector().instanceOf(PlayJongo.class);
        StatusConexaoControlador.jongo = jongo;

        jongo.getCollection("status_conexao_controladores").drop();

        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
        Thread.sleep(100);
        central = provideApp.injector().instanceOf(Central.class);
    }

    @After
    public void cleanUp() {
        mqttBroker.stopServer();
        central.finish();
        client.finish();
        mqttBroker = null;
        mqttBroker = null;
        onDisconectFuture = null;
        onSubscribeFuture = null;
        System.gc();
    }


    protected void centralDeveSeConectarAoServidorMQTT() {
        try {
            //A central ao se conectar no servidor deve se inscrever em diversos tópicos

            //A central conectou
            assertEquals("central", onConnectFuture.get(TIMEOUT, TimeUnit.SECONDS));

            //A central se increveu para receber informação de quando um controlador fica online
            Object respSubscribe = onSubscribeFuture.get(TIMEOUT, TimeUnit.SECONDS);
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("controladores/conn/online", respSubscribe);

            //A central se increveu para receber informação de quando um controlador fica offline
            respSubscribe = onSubscribeFuture.get(TIMEOUT, TimeUnit.SECONDS);
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("controladores/conn/offline", respSubscribe);

            //A central se increveu para receber informação de echo
            respSubscribe = onSubscribeFuture.get(TIMEOUT, TimeUnit.SECONDS);
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("central/+", respSubscribe);

            //O cliente foi instanciado
            client = app.injector().instanceOf(Client.class);
            onConnectFuture = new CompletableFuture();

            //A central se increveu para receber informação de echo
            respSubscribe = onSubscribeFuture.get(TIMEOUT, TimeUnit.SECONDS);
            onSubscribeFuture = new CompletableFuture<>();
            assertEquals("controlador/" + idControlador + "/+", respSubscribe);

            //O cliente se conectou
            assertEquals(idControlador, onConnectFuture.get(TIMEOUT, TimeUnit.SECONDS));

            //O cliente envio a CONTROLADOR_ONLINE
            resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
            onPublishFuture = new CompletableFuture<>();

            JsonNode json = play.libs.Json.parse(new String(resp));

            assertEquals(idControlador, json.get("idControlador").asText());
            assertEquals("CONTROLADOR_ONLINE", json.get("tipoMensagem").asText());
            assertEquals("controladores/conn/online", json.get("destino").asText());
            assertTrue(json.has("carimboDeTempo"));
            assertTrue(json.has("idMensagem"));
            assertTrue(json.has("conteudo"));

            assertTrue(json.get("conteudo").has("dataHora"));
            assertTrue(json.get("conteudo").has("versao72c"));
            assertTrue(json.get("conteudo").has("status"));
            assertEquals("NOVO", json.get("conteudo").get("status").asText());


            //Verificar se o registro da conexao foi salvo
            Thread.sleep(200);
            StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(idControlador);
            assertTrue(status.isConectado());
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
