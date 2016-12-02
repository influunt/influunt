package integracao;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import config.WithInfluuntApplicationNoAuthentication;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.junit.After;
import org.junit.Before;
import os72c.client.Client;
import os72c.client.conf.DeviceConfig;
import os72c.client.conf.TestDeviceConfig;
import os72c.client.storage.Storage;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.StatusTransacao;
import protocol.TipoMensagem;
import server.Central;
import status.StatusConexaoControlador;
import status.StatusControladorFisico;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class BasicMQTTTest extends WithInfluuntApplicationNoAuthentication {

    protected Server mqttBroker;

    protected Central central;

    protected List<String> onConnectFutureList = new ArrayList<>();

    protected List<String> onDisconectFutureList = new ArrayList<>();

    protected List<String> onSubscribeFutureList = new ArrayList<>();

    protected List<byte[]> onPublishFutureList = new ArrayList<>();

    protected Client client;

    protected PlayJongo jongo;

    protected Controlador controlador;

    protected String idControlador;

    protected DeviceConfig deviceConfig;


    @Before
    public void setup() throws IOException, InterruptedException {
        controlador = new ControladorHelper().getControlador();
        idControlador = controlador.getId().toString();
        this.deviceConfig = new TestDeviceConfig();
        this.deviceConfig.setDeviceId(controlador.getId().toString());
        this.deviceConfig.setCentralPublicKey(controlador.getCentralPublicKey());
        this.deviceConfig.setPrivateKey(controlador.getControladorPrivateKey());

        setConfig();
    }

    @After
    public void cleanUp() {
        client.finish();
        central.finish();
        mqttBroker.stopServer();
        mqttBroker = null;
        onConnectFutureList.clear();
        onDisconectFutureList.clear();
        onSubscribeFutureList.clear();
        onPublishFutureList.clear();
        System.gc();
    }

    protected void setConfig() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.put("persistent_store", "");

        final IConfig classPathConfig = new MemoryConfig(properties);
        List<? extends InterceptHandler> userHandlers = asList(new InterceptHandler() {
            @Override
            public void onConnect(InterceptConnectMessage interceptConnectMessage) {
                onConnectFutureList.add(interceptConnectMessage.getClientID());
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                onDisconectFutureList.add(interceptDisconnectMessage.getClientID());
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                onPublishFutureList.add(interceptPublishMessage.getPayload().array());
                System.out.println("\nonPublishFutureList.size() : " + onPublishFutureList.size());
                System.out.println("MSG : " + interceptPublishMessage.getTopicName());
            }

            @Override
            public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {
                onSubscribeFutureList.add(interceptSubscribeMessage.getTopicFilter());
            }

            @Override
            public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {
            }
        });

        jongo = provideApp.injector().instanceOf(PlayJongo.class);
        StatusConexaoControlador.jongo = jongo;
        StatusControladorFisico.jongo = jongo;

        jongo.getCollection(StatusConexaoControlador.COLLECTION).drop();
        jongo.getCollection(StatusControladorFisico.COLLECTION).drop();

        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
        Thread.sleep(100);
        central = provideApp.injector().instanceOf(Central.class);
        Thread.sleep(2000);
    }

    protected void startClient() {
        client = new Client(this.deviceConfig);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void assertTransacaoOk() {
        try {
            await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 15);

            Storage storage = app.injector().instanceOf(Storage.class);

            Envelope envelope = new Gson().fromJson(new String(onPublishFutureList.get(9)), Envelope.class);

            JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.STATUS_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(StatusTransacao.INICIADA.toString(), jsonConteudo.get("status").asText());


            Map map = new Gson().fromJson(new String(onPublishFutureList.get(10)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

            String idTransacao = jsonConteudo.get("transacaoId").asText();


            map = new Gson().fromJson(new String(onPublishFutureList.get(11)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_OK.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            map = new Gson().fromJson(new String(onPublishFutureList.get(12)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            map = new Gson().fromJson(new String(onPublishFutureList.get(13)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.COMMITED.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            envelope = new Gson().fromJson(new String(onPublishFutureList.get(14)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.STATUS_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(StatusTransacao.OK.toString(), jsonConteudo.get("status").asText());


            map = new Gson().fromJson(new String(onPublishFutureList.get(15)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.COMPLETED.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());
        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    protected void assertTransacaoErro() {
        try {
            await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 13);

            Storage storage = app.injector().instanceOf(Storage.class);


            Envelope envelope = new Gson().fromJson(new String(onPublishFutureList.get(9)), Envelope.class);

            JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.STATUS_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(StatusTransacao.INICIADA.toString(), jsonConteudo.get("status").asText());


            Map map = new Gson().fromJson(new String(onPublishFutureList.get(10)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

            String idTransacao = jsonConteudo.get("transacaoId").asText();


            map = new Gson().fromJson(new String(onPublishFutureList.get(11)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_FAIL.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            envelope = new Gson().fromJson(new String(onPublishFutureList.get(12)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.STATUS_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(StatusTransacao.ERRO.toString(), jsonConteudo.get("status").asText());


            map = new Gson().fromJson(new String(onPublishFutureList.get(13)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.FAILED.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
