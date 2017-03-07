package test.integracao;

import helpers.transacao.TransacaoBuilder;
import models.Anel;
import org.fusesource.mqtt.client.QoS;
import org.junit.Test;
import status.PacoteTransacao;
import utils.TransacaoHelperCentral;

import java.io.IOException;
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
        PacoteTransacao liberar = TransacaoBuilder.liberarImposicao(Collections.singletonList(anel), 60000L);
        TransacaoHelperCentral transacaoHelper = provideApp.injector().instanceOf(TransacaoHelperCentral.class);
        transacaoHelper.sendTransaction(liberar.toJson(), QoS.EXACTLY_ONCE);
    }
}
