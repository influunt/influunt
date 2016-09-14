package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import models.Controlador;
import models.StatusControlador;
import models.StatusDevice;
import org.junit.Test;
import protocol.TipoMensagem;
import status.StatusControladorFisico;

import javax.validation.groups.Default;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
