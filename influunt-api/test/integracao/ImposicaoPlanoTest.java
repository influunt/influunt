package integracao;

import models.Anel;
import models.ModoOperacaoPlano;
import models.Plano;
import org.fusesource.mqtt.client.QoS;
import org.junit.Test;
import utils.TransacaoHelper;

import static org.awaitility.Awaitility.await;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicaoPlanoTest extends BasicMQTTTest {

    @Test
    public void imporPlanoOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        Anel anel = controlador.getAneis().stream()
            .filter(Anel::isAtivo)
            .findFirst().orElse(null);
        Plano plano = anel.getPlanos().get(0);

        imporPlano(plano.getPosicao(), anel.getPosicao(), 30, QoS.EXACTLY_ONCE);
        assertTransacaoOk();
    }

    @Test
    public void imporPlanoComErro() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        imporPlano(-1, 0, -1, QoS.EXACTLY_ONCE);
        assertTransacaoErro();
    }

    private void imporPlano(int posicaoPlano, int numeroAnel, int duracao, QoS qos) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.imporPlano(controlador, posicaoPlano, numeroAnel, duracao, qos);
    }

}
