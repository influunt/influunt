package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.fusesource.mqtt.client.QoS;
import org.junit.Test;
import utils.TransacaoHelper;

import javax.validation.groups.Default;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicaoModoOperacaoTest extends BasicMQTTTest {

    @Test
    public void imporModoOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        imporModoOperacao(1, 30, QoS.EXACTLY_ONCE);
        assertTransacaoOk();
    }

    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);

        imporModoOperacao(-1, -1, QoS.EXACTLY_ONCE);
        assertTransacaoErro();
    }

    private void imporModoOperacao(int numeroAnel, int duracao, QoS qos) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.imporModoOperacao(controlador, ModoOperacaoPlano.INTERMITENTE, numeroAnel, duracao, qos);
    }

}
