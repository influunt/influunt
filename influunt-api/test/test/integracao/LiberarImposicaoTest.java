package test.integracao;

import helpers.TransacaoHelperApi;
import models.Anel;
import org.junit.Test;
import utils.TransacaoHelperCentral;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class LiberarImposicaoTest extends BasicMQTTTest {

    @Test
    public void liberarImposicaoOK() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        liberarImposicao(getAnel(1));
        assertTransacaoOk();
    }

    @Test
    public void imporPlanoComErro() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        liberarImposicao(getAnel(4));
        assertTransacaoErro();
    }

    private void liberarImposicao(Anel anel) {
        TransacaoHelperApi transacaoHelper = provideApp.injector().instanceOf(TransacaoHelperApi.class);
        transacaoHelper.liberarImposicao(Collections.singletonList(anel), 60000L, "authToken");
    }
}
