package test.integracao;

import helpers.transacao.TransacaoBuilder;
import models.Anel;
import models.ModoOperacaoPlano;
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
        PacoteTransacao imposicaoModo = TransacaoBuilder.modoOperacao(Collections.singletonList(anel), ModoOperacaoPlano.INTERMITENTE, horarioEntrada, duracao, 60000L);
        TransacaoHelperCentral transacaoHelper = provideApp.injector().instanceOf(TransacaoHelperCentral.class);
        transacaoHelper.sendTransaction(imposicaoModo.toJson(), QoS.EXACTLY_ONCE);
    }
}
