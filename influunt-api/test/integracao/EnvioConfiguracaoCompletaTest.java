package integracao;

import checks.*;
import models.Anel;
import models.Controlador;
import org.junit.Test;
import utils.TransacaoHelper;

import javax.validation.groups.Default;
import java.io.IOException;
import java.util.Arrays;
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
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void envioConfiguracaoCompletaOk() throws InterruptedException, IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 6);
        enviarConfiguracaoCompleta(controlador);
        assertTransacaoOk();
    }

    @Test
    public void envioConfiguracaoCompletaErro() throws InterruptedException, ExecutionException, TimeoutException, IOException {
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
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

    private void enviarConfiguracaoCompleta(Controlador controlador) {
        TransacaoHelper transacaoHelper = provideApp.injector().instanceOf(TransacaoHelper.class);
        transacaoHelper.enviarConfiguracaoCompleta(Arrays.asList(controlador), 10000L);
    }
}
