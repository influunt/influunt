package integracao;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import config.WithInfluuntApplicationNoAuthentication;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import models.Anel;
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import os72c.client.Client;
import os72c.client.conf.DeviceConfig;
import os72c.client.conf.TestDeviceConfig;
import os72c.client.storage.Storage;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import server.Central;
import status.*;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.EncryptionUtil;
import utils.GzipUtil;

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

    @Rule
    public JUnitRetry retry = new JUnitRetry(2);

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
        idControlador = controlador.getControladorFisicoId();
        this.deviceConfig = new TestDeviceConfig();
        this.deviceConfig.setDeviceId(idControlador);
        this.deviceConfig.setCentralPublicKey(controlador.getCentralPublicKey());
        this.deviceConfig.setPrivateKey(controlador.getControladorPrivateKey());

        setConfig();

    }

    @After
    public void cleanUp() throws InterruptedException {
        client.finish();
        central.finish();
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
                onConnectFutureList.add(interceptConnectMessage.getClientID());
            }

            @Override
            public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {
                onDisconectFutureList.add(interceptDisconnectMessage.getClientID());
            }

            @Override
            public void onPublish(InterceptPublishMessage interceptPublishMessage) {
                onPublishFutureList.add(interceptPublishMessage.getPayload().array());
                //System.out.println("\nonPublishFutureList.size() : " + (onPublishFutureList.size() - 1));
                //System.out.println("MSG : " + interceptPublishMessage.getTopicName());
                //System.out.println("BYTE : " + (new String(interceptPublishMessage.getPayload().array())).substring(0, 10));
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
        PacoteTransacao.jongo = jongo;
        LogControlador.jongo = jongo;

        jongo.getCollection(StatusConexaoControlador.COLLECTION).drop();
        jongo.getCollection(StatusControladorFisico.COLLECTION).drop();
        jongo.getCollection(PacoteTransacao.COLLECTION).drop();
        jongo.getCollection(LogControlador.COLLECTION).drop();

        mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig, userHandlers);
        Thread.sleep(100);
        central = provideApp.injector().instanceOf(Central.class);
        Thread.sleep(1500);
    }

    protected void startClient() {
        client = new Client(this.deviceConfig);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void assertTransacaoOk() throws IOException {
        Envelope envelope;
        JsonNode jsonConteudo;
        try {
            await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 12);

            Storage storage = app.injector().instanceOf(Storage.class);


            Map map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(7)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

            String idTransacao = jsonConteudo.get("transacaoId").asText();


            envelope = new Gson().fromJson(new String(onPublishFutureList.get(8)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.PACOTE_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(StatusPacoteTransacao.NEW.toString(), jsonConteudo.get("statusPacoteTransacao").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(9)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_OK.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(10)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(11)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.COMMITED.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            envelope = new Gson().fromJson(new String(onPublishFutureList.get(12)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.PACOTE_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(StatusPacoteTransacao.DONE.toString(), jsonConteudo.get("statusPacoteTransacao").asText());

        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    protected void assertTransacaoErro() throws IOException {
        Envelope envelope;
        JsonNode jsonConteudo;
        try {
            await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 12);

            Storage storage = app.injector().instanceOf(Storage.class);


            Map map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(7)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());
            String idTransacao = jsonConteudo.get("transacaoId").asText();

            envelope = new Gson().fromJson(new String(onPublishFutureList.get(8)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.PACOTE_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(StatusPacoteTransacao.NEW.toString(), jsonConteudo.get("statusPacoteTransacao").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(9)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.PREPARE_FAIL.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(10)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.ABORT.toString(), jsonConteudo.get("etapaTransacao").asText());


            map = new Gson().fromJson(GzipUtil.decompress(onPublishFutureList.get(11)), Map.class);
            envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
            assertEquals(idControlador, envelope.getIdControlador());
            assertEquals(EtapaTransacao.ABORTED.toString(), jsonConteudo.get("etapaTransacao").asText());
            assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

            envelope = new Gson().fromJson(new String(onPublishFutureList.get(12)), Envelope.class);

            jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
            assertEquals(TipoMensagem.PACOTE_TRANSACAO, envelope.getTipoMensagem());
            assertEquals(StatusPacoteTransacao.ABORTED.toString(), jsonConteudo.get("statusPacoteTransacao").asText());

        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    protected Anel getAnel(int posicao) {
        return controlador.getAneis().stream()
            .filter(anel -> anel.getPosicao().equals(posicao))
            .findFirst().orElse(null);
    }
}
