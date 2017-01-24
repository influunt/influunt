package test.integracao;

import checks.*;
import helpers.transacao.TransacaoBuilder;
import helpers.transacao.TransacaoHelperApi;
import models.Controlador;
import org.apache.commons.codec.DecoderException;
import org.fusesource.mqtt.client.QoS;
import org.junit.Test;
import status.PacoteTransacao;
import utils.TransacaoHelperCentral;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.groups.Default;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;

import static org.awaitility.Awaitility.await;
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
    public void enviarPlanosOK() throws BadPaddingException, DecoderException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        controlador = new ControladorHelper().setPlanos(controlador);

        startClient();

        await().until(() -> onPublishFutureList.size() > 6);

        enviarPacotePlano();
        assertTransacaoOk();
    }

    @Test
    public void enviarPlanosNaoOK() throws IOException {
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        enviarPacotePlano();
        assertTransacaoErro();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, PlanosCentralCheck.class,
            TabelaHorariosCheck.class);
    }

    private void enviarPacotePlano() {
        PacoteTransacao pacotePlano = TransacaoBuilder.pacotePlanos(Collections.singletonList(controlador), 60000L);
        TransacaoHelperCentral transacaoHelper = provideApp.injector().instanceOf(TransacaoHelperCentral.class);
        transacaoHelper.sendTransaction(pacotePlano.toJson(), QoS.EXACTLY_ONCE);
    }

}
