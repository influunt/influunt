package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import engine.TipoEvento;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.apache.commons.codec.DecoderException;
import org.junit.Before;
import org.junit.Test;
import os72c.client.conf.DeviceConfig;
import protocol.Envelope;
import protocol.TipoMensagem;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.groups.Default;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class AlarmeEFalhaTest extends BasicMQTTTest {


    @Before
    public void setup() throws IOException, InterruptedException {
        controlador = new ControladorHelper().getControlador(true);
        controlador = new ControladorHelper().setPlanos(controlador);
        idControlador = controlador.getId().toString();
        provideApp.injector().instanceOf(DeviceConfig.class).setDeviceId(controlador.getId().toString());
        provideApp.injector().instanceOf(DeviceConfig.class).setCentralPublicKey(controlador.getCentralPublicKey());
        provideApp.injector().instanceOf(DeviceConfig.class).setPrivateKey(controlador.getControladorPrivateKey());

        setConfig();
    }

    @Test
    public void recebendo() throws InterruptedException, ExecutionException, TimeoutException, BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        startClient();
        await().until(() -> onPublishFutureList.size() > 7);

        Map map = new Gson().fromJson(new String(onPublishFutureList.get(6)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        JsonNode jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.ALARME_FALHA, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES.toString(), jsonConteudo.get("tipoEvento").get("tipo").asText());

        map = new Gson().fromJson(new String(onPublishFutureList.get(7)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        jsonConteudo = play.libs.Json.parse(envelope.getConteudo().toString());
        assertEquals(TipoMensagem.TROCA_DE_PLANO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(ModoOperacaoPlano.INTERMITENTE.toString(), jsonConteudo.get("plano").get("modoOperacao").asText());
        assertTrue("Imposto por falha", jsonConteudo.get("impostoPorFalha").asBoolean());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class,
            PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
