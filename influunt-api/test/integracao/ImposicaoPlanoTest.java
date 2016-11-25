package integracao;

import models.Anel;
import models.Plano;
import org.junit.Test;
import utils.TransacaoHelper;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicaoPlanoTest extends BasicMQTTTest {

    @Test
    public void imporPlanoOK() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        Anel anel = controlador.getAneis().stream()
            .filter(Anel::isAtivo)
            .findFirst().orElse(null);
        Plano plano = anel.getPlanos().get(0);

        imporPlano(plano.getPosicao(), anel.getPosicao(), 30);
        assertTransacaoOk();
    }

    @Test
    public void imporPlanoComErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        imporPlano(-1, 0, -1);
        assertTransacaoErro();
    }

    private void imporPlano(int posicaoPlano, int numeroAnel, int duracao) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.imporPlano(controlador, posicaoPlano, numeroAnel, duracao);
    }

}
