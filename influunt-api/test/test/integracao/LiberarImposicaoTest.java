package test.integracao;

import models.Anel;
import org.junit.Test;
import utils.TransacaoHelper;

import java.io.IOException;
import java.util.Arrays;

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
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.liberarImposicao(Arrays.asList(anel), 60000L);
    }
}
