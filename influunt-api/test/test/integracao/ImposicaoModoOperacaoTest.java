package test.integracao;

import models.Anel;
import models.ModoOperacaoPlano;
import org.junit.Test;
import utils.TransacaoHelper;

import java.io.IOException;
import java.util.Collections;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicaoModoOperacaoTest extends BasicMQTTTest {

    @Test
    public void imporModoOK() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        long horarioEntrada = System.currentTimeMillis() + 60000L;
        imporModoOperacao(getAnel(1), horarioEntrada, 30);
        assertTransacaoOk();
    }

    @Test
    public void imporModoComErro() throws IOException {
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        long horarioEntrada = System.currentTimeMillis() + 60000L;
        imporModoOperacao(getAnel(1), horarioEntrada, -1);
        assertTransacaoErro();
    }

    private void imporModoOperacao(Anel anel, Long horarioEntrada, int duracao) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.imporModoOperacao(Collections.singletonList(anel), ModoOperacaoPlano.INTERMITENTE, horarioEntrada, duracao, 60000L);
    }
}
