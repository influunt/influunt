package integracao;

import models.Anel;
import org.junit.Test;
import utils.TransacaoHelper;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class LiberarImposicaoTest extends BasicMQTTTest {

    @Test
    public void liberarImposicaoOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 5);

        Anel anel = controlador.getAneis().stream()
            .filter(Anel::isAtivo)
            .findFirst().orElse(null);

        liberarImposicao(anel.getPosicao());
        assertTransacaoOk();
    }

    @Test
    public void imporPlanoComErro() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 5);

        liberarImposicao(-1);
        assertTransacaoErro();
    }

    private void liberarImposicao(int numeroAnel) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.liberarImposicao(controlador, numeroAnel);
    }
}
