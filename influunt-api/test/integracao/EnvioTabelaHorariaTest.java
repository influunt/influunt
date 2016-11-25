package integracao;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import json.ControladorCustomSerializer;
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import protocol.TipoTransacao;
import server.conn.CentralMessageBroker;
import status.Transacao;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.groups.Default;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void enviarPlanosOK() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        controlador = new ControladorHelper().setPlanos(controlador);
        
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Transacao transacao = new Transacao(idControlador, pacotePlanosJson, TipoTransacao.PACOTE_PLANO);

        String transacaoJson = transacao.toJson().toString();
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, idControlador, null, 1, transacaoJson, null);

        ActorSystem context = provideApp.injector().instanceOf(ActorSystem.class);
        ActorRef actor = context.actorOf(Props.create(CentralMessageBroker.class), "MessageBrokerTeste");
        actor.tell(envelope, null);

        await().until(() -> onPublishFutureList.size() > 10);

        Map map = new Gson().fromJson(new String(onPublishFutureList.get(6)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getControladorPrivateKey()), Envelope.class);

        JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

        String idTransacao = jsonConteudo.get("transacaoId").asText();

        map = new Gson().fromJson(new String(onPublishFutureList.get(7)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.PREPARE_OK.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        map = new Gson().fromJson(new String(onPublishFutureList.get(8)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getControladorPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        map = new Gson().fromJson(new String(onPublishFutureList.get(9)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.COMMITED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        map = new Gson().fromJson(new String(onPublishFutureList.get(10)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getControladorPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.COMPLETED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());
    }

    @Test
    public void enviarPlanosNaoOK() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        startClient();
        //controlador = new ControladorHelper().getControlador();

        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Transacao transacao = new Transacao(idControlador, pacotePlanosJson, TipoTransacao.PACOTE_PLANO);

        String transacaoJson = transacao.toJson().toString();
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, idControlador, null, 1, transacaoJson, null);

        ActorSystem context = provideApp.injector().instanceOf(ActorSystem.class);
        ActorRef actor = context.actorOf(Props.create(CentralMessageBroker.class), "MessageBrokerTeste");
        actor.tell(envelope, null);

        await().until(() -> onPublishFutureList.size() > 8);


        Map map = new Gson().fromJson(new String(onPublishFutureList.get(6)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getControladorPrivateKey()), Envelope.class);

        JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

        String idTransacao = jsonConteudo.get("transacaoId").asText();

        map = new Gson().fromJson(new String(onPublishFutureList.get(7)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.PREPARE_FAIL.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        map = new Gson().fromJson(new String(onPublishFutureList.get(8)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getControladorPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TRANSACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(EtapaTransacao.FAILED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
