package integracao;

import checks.*;
import com.google.gson.Gson;
import models.*;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import os72c.client.storage.Storage;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.StatusControladorFisico;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioConfiguracaoTest extends BasicMQTTTest {


    @Test
    public void configuracaoValida() {
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void configuracaoErro() throws InterruptedException, ExecutionException, TimeoutException, BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().get();
        anel.setAtivo(true);
        controlador.save();

        startClient();

        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 4);

        Map map = new Gson().fromJson(new String(onPublishFutureList.get(1)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        assertEquals(TipoMensagem.CONFIGURACAO_INICIAL, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());


        String idMensagem = envelope.getIdMensagem();
        Storage storage = app.injector().instanceOf(Storage.class);
        map = new Gson().fromJson(new String(onPublishFutureList.get(3)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

        assertEquals(TipoMensagem.CONFIGURACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(idMensagem, envelope.getEmResposta());

        idMensagem = envelope.getIdMensagem();

        map = new Gson().fromJson(new String(onPublishFutureList.get(4)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);
        assertEquals(TipoMensagem.ERRO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(idMensagem, envelope.getEmResposta());
    }

    @Test
    public void configuracaoOK() throws InterruptedException, ExecutionException, TimeoutException, BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 5);


        Map map = new Gson().fromJson(new String(onPublishFutureList.get(1)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        assertEquals(TipoMensagem.CONFIGURACAO_INICIAL, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());


        String idMensagem = envelope.getIdMensagem();
        Storage storage = app.injector().instanceOf(Storage.class);
        map = new Gson().fromJson(new String(onPublishFutureList.get(3)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);

        assertEquals(TipoMensagem.CONFIGURACAO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(idMensagem, envelope.getEmResposta());

        idMensagem = envelope.getIdMensagem();

        map = new Gson().fromJson(new String(onPublishFutureList.get(4)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);
        assertEquals(TipoMensagem.OK, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(idMensagem, envelope.getEmResposta());

        map = new Gson().fromJson(new String(onPublishFutureList.get(5)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);


        assertEquals(TipoMensagem.MUDANCA_STATUS_CONTROLADOR, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(StatusDevice.CONFIGURADO.toString(), new Gson().fromJson(envelope.getConteudo().toString(), Map.class).get("status"));
        await().until(() -> StatusControladorFisico.ultimoStatus(idControlador) != null);
        assertEquals(StatusDevice.CONFIGURADO.toString(), StatusControladorFisico.ultimoStatus(idControlador).getStatusDevice().toString());
    }

    @Test
    public void naoExisteConfiguracao() throws InterruptedException, ExecutionException, TimeoutException, BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        VersaoControlador versaoControlador = controlador.getVersaoControlador();
        versaoControlador.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
        versaoControlador.update();

        startClient();

        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 3);

        Map map = new Gson().fromJson(new String(onPublishFutureList.get(1)), Map.class);
        Envelope envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, controlador.getCentralPrivateKey()), Envelope.class);

        assertEquals(TipoMensagem.CONFIGURACAO_INICIAL, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());


        String idMensagem = envelope.getIdMensagem();
        Storage storage = app.injector().instanceOf(Storage.class);
        map = new Gson().fromJson(new String(onPublishFutureList.get(3)), Map.class);
        envelope = new Gson().fromJson(EncryptionUtil.decryptJson(map, storage.getPrivateKey()), Envelope.class);
        assertEquals(TipoMensagem.ERRO, envelope.getTipoMensagem());
        assertEquals(idControlador, envelope.getIdControlador());
        assertEquals(idMensagem, envelope.getEmResposta());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class);
    }

}
