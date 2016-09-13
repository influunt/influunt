package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioConfiguracaoTest extends BasicMQTTTest{


    @Test
    public void configuracaoValida() {
        centralDeveSeConectarAoServidorMQTT();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void configuracaoOK() throws InterruptedException, ExecutionException, TimeoutException {
        centralDeveSeConectarAoServidorMQTT();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();

        JsonNode json = play.libs.Json.parse(new String(resp));
        assertEquals("CONFIGURACAO_INICIAL", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();
        json = play.libs.Json.parse(new String(resp));
        assertEquals("CONFIGURACAO", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        idMensagem = json.get("idMensagem").asText();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();
        json = play.libs.Json.parse(new String(resp));
        assertEquals("OK", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());
    }

    @Test
    public void configuracaoErro() throws InterruptedException, ExecutionException, TimeoutException {
        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().get();
        anel.setAtivo(true);

        controlador.save();

        centralDeveSeConectarAoServidorMQTT();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();

        JsonNode json = play.libs.Json.parse(new String(resp));
        assertEquals("CONFIGURACAO_INICIAL", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();
        json = play.libs.Json.parse(new String(resp));
        assertEquals("CONFIGURACAO", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());

        idMensagem = json.get("idMensagem").asText();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();
        json = play.libs.Json.parse(new String(resp));
        assertEquals("ERRO", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());
    }

    @Test
    public void naoExisteConfiguracao() throws InterruptedException, ExecutionException, TimeoutException {
        centralDeveSeConectarAoServidorMQTT();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        onPublishFuture = new CompletableFuture<>();

        JsonNode json = play.libs.Json.parse(new String(resp));
        assertEquals("CONFIGURACAO_INICIAL", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());

        String idMensagem = json.get("idMensagem").asText();

        resp = onPublishFuture.get(TIMEOUT, TimeUnit.SECONDS);
        json = play.libs.Json.parse(new String(resp));
        assertEquals("ERRO", json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(idMensagem, json.get("emResposta").asText());
    }

    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
    }

}
