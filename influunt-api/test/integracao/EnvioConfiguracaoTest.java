package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
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
public class EnvioConfiguracaoTest extends BasicMQTTTest {


    @Test
    public void configuracaoValida() {
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }
    @Test
    public void execucaoDevice() {
        startClient();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void configuracaoErro() throws InterruptedException, ExecutionException, TimeoutException {
        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().get();
        anel.setAtivo(true);
        controlador.save();
        startClient();

        startClient();

        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(1)));
        assertEquals(TipoMensagem.CONFIGURACAO_INICIAL.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(2)));
        assertEquals(TipoMensagem.CONFIGURACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        idMensagem = json.get("idMensagem").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(3)));
        assertEquals(TipoMensagem.ERRO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(4)));
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(TipoMensagem.MUDANCA_STATUS_CONTROLADOR.toString(), json.get("tipoMensagem").asText());
        assertEquals(StatusDevice.NOVO.toString(), json.get("conteudo").get("status").asText());
        assertEquals(StatusDevice.NOVO.toString(), StatusControladorFisico.ultimoStatus(idControlador).getStatusDevice().toString());
    }

    @Test
    public void configuracaoOK() throws InterruptedException, ExecutionException, TimeoutException {
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode json = play.libs.Json.parse(onPublishFutureList.get(1));
        assertEquals(TipoMensagem.CONFIGURACAO_INICIAL.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        json = play.libs.Json.parse(onPublishFutureList.get(2));
        assertEquals(TipoMensagem.CONFIGURACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        idMensagem = json.get("idMensagem").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(3)));
        assertEquals(TipoMensagem.OK.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(4)));
        assertEquals(TipoMensagem.MUDANCA_STATUS_CONTROLADOR.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(StatusDevice.CONFIGURADO.toString(), json.get("conteudo").get("status").asText());
        await().until(() -> StatusControladorFisico.ultimoStatus(idControlador) != null);
        assertEquals(StatusDevice.CONFIGURADO.toString(), StatusControladorFisico.ultimoStatus(idControlador).getStatusDevice().toString());
    }

    @Test
    public void naoExisteConfiguracao() throws InterruptedException, ExecutionException, TimeoutException {
        VersaoControlador versaoControlador = controlador.getVersaoControlador();
        versaoControlador.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
        versaoControlador.update();
        startClient();

        await().until(() -> onPublishFutureList.size() > 2);

        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(1)));
        assertEquals("CONFIGURACAO_INICIAL", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(2)));
        assertEquals("ERRO", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class);
    }

}
