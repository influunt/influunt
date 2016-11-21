package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import engine.TipoEvento;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.junit.Before;
import org.junit.Test;
import os72c.client.conf.DeviceConfig;
import protocol.TipoMensagem;

import javax.validation.groups.Default;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class AlarmeEFalhaTest extends BasicMQTTTest {


    @Before
    public void setup() throws IOException, InterruptedException {
        controlador = new ControladorHelper().getControlador(true);
        controlador = new ControladorHelper().setPlanos(controlador);
        idControlador = controlador.getId().toString();
        provideApp.injector().instanceOf(DeviceConfig.class).setDeviceId(controlador.getId().toString());

        setConfig();
    }

    @Test
    public void recebendo() throws InterruptedException, ExecutionException, TimeoutException {
        startClient();

        await().until(() -> onPublishFutureList.size() > 6);

        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(5)));
        JsonNode jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.ALARME_FALHA.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(TipoEvento.FALHA_VERDES_CONFLITANTES.toString(), jsonConteudo.get("tipoEvento").get("tipo").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(6)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TROCA_DE_PLANO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(ModoOperacaoPlano.INTERMITENTE.toString(), jsonConteudo.get("plano").get("modoOperacao").asText());
        assertTrue("Imposto por falha", jsonConteudo.get("impostoPorFalha").asBoolean());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class,
            PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
