package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import org.junit.Test;
import utils.TransacaoHelper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.groups.Default;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void enviarPlanosOK() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        controlador = new ControladorHelper().setPlanos(controlador);

        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

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

        enviarPacotePlano();
        assertTransacaoOk();
    }

    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        enviarPacotePlano();
        assertTransacaoErro();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

    private void enviarPacotePlano() {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.enviarPacotePlanos(controlador);
    }

}
