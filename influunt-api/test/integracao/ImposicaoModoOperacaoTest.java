package integracao;

import models.ModoOperacaoPlano;
import org.junit.Test;
import utils.TransacaoHelper;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicaoModoOperacaoTest extends BasicMQTTTest {

    @Test
    public void imporModoOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        imporModoOperacao(1, 30);
        assertTransacaoOk();
    }

    @Test
    public void imporModoComErro() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);

        imporModoOperacao(-1, -1);
        assertTransacaoErro();
    }

    private void imporModoOperacao(int numeroAnel, int duracao) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.imporModoOperacao(controlador, ModoOperacaoPlano.INTERMITENTE, numeroAnel, duracao);
    }

}
