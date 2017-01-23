package test.simulacao;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import test.config.WithInfluuntApplicationNoAuthentication;
import test.integracao.JUnitRetry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class SimuladorMQTTTest extends WithInfluuntApplicationNoAuthentication {

    @Rule
    public JUnitRetry retry = new JUnitRetry(2);

    protected Server mqttBroker;


    protected List<String> onConnectFutureList = new ArrayList<>();

    protected List<String> onDisconectFutureList = new ArrayList<>();

    protected List<String> onSubscribeFutureList = new ArrayList<>();

    protected List<byte[]> onPublishFutureList = new ArrayList<>();


    @Before
    public void setup() throws IOException, InterruptedException {

        setConfig();

    }

    @After
    public void cleanUp() throws InterruptedException {
        mqttBroker.stopServer();
        mqttBroker = null;
        onConnectFutureList.clear();
        onDisconectFutureList.clear();
        onSubscribeFutureList.clear();
        onPublishFutureList.clear();
        Thread.sleep(100);

    }

    protected void setConfig() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.put("persistent_store", "");

        final IConfig classPathConfig = new MemoryConfig(properties);
        List<? extends InterceptHandler> userHandlers = asList(new InterceptHandler() {
            @Override
            public void onConnect(InterceptConnectMessage interceptConnectMessage) {
                System.out.println("Client conectado:" + interceptConnectMessage.getClientID());
                onConnectFutureList.add(interceptConnectMessage.getClientID());
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                System.out.println("Client desconectado:" + interceptDisconnectMessage.getClientID());
                onDisconectFutureList.add(interceptDisconnectMessage.getClientID());
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                onPublishFutureList.add(interceptPublishMessage.getPayload().array());
                System.out.println("\nonPublishFutureList.size() : " + (onPublishFutureList.size() - 1));
                System.out.println("MSG : " + interceptPublishMessage.getTopicName());
                System.out.println("BYTE : " + new String(interceptPublishMessage.getPayload().array()));
            }

            @Override
            public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {
                System.out.println("Subscribe: " + interceptSubscribeMessage.getTopicFilter());
                onSubscribeFutureList.add(interceptSubscribeMessage.getTopicFilter());
            }

            @Override
            public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {
            }
        });


        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
        Thread.sleep(100);
    }


}
