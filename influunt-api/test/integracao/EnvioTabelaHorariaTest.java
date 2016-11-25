package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import models.Controlador;
import org.junit.Test;
import utils.TransacaoHelper;

import javax.validation.groups.Default;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void enviarPlanosOK() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        enviarPacotePlano();
        assertTransacaoOk();
    }

    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 5);

        enviarPacotePlano();
        assertTransacaoErro();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

    private void enviarPacotePlano() {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.enviarPacotePlanos(controlador);
    }

}
