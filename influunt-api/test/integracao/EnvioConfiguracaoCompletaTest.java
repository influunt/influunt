package integracao;

import checks.*;
import models.Anel;
import models.Controlador;
import org.junit.Test;
import utils.TransacaoHelper;

import javax.validation.groups.Default;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioConfiguracaoCompletaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void envioConfiguracaoCompletaOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        enviarConfiguracaoCompleta(controlador);
        assertTransacaoOk();
    }

    @Test
    public void envioConfiguracaoCompletaErro() throws InterruptedException, ExecutionException, TimeoutException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);

        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().orElse(null);
        anel.setAtivo(true);
        controlador.update();

        enviarConfiguracaoCompleta(controlador);
        assertTransacaoErro();
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class);
    }

    private void enviarConfiguracaoCompleta(Controlador controlador) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.enviarConfiguracaoCompleta(controlador);
    }
}
