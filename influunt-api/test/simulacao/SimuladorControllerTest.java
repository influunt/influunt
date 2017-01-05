package simulacao;

import checks.Erro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.BasicMQTTTest;
import integracao.ControladorHelper;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import io.moquette.server.Server;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.MemoryConfig;
import models.Controlador;
import org.eclipse.paho.client.mqttv3.*;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import os72c.client.Versao;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import protocol.ControladorOffline;
import protocol.ControladorOnline;
import protocol.Envelope;
import scala.concurrent.duration.Duration;
import utils.GzipUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;
import static play.test.Helpers.route;


public class SimuladorControllerTest extends SimuladorMQTTTest {


    @Test
    public void testSimularWithoutData() {
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(controllers.simulacao.routes.SimuladorController.simular().url());
        Result result = route(request);
        assertEquals(400, result.status());
    }

    @Test
    public void testValidation() {
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(controllers.simulacao.routes.SimuladorController.simular().url())
            .bodyJson(Json.parse("{}"));
        Result result = route(request);
        assertEquals(422, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Erro> erros = parseErrors((ArrayNode) json);

        assertEquals(3, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("ParametroSimulacao", "não pode ficar em branco", "inicioControlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "controlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "velocidade")
        ));

        request = new Http.RequestBuilder().method("POST")
            .uri(controllers.simulacao.routes.SimuladorController.simular().url())
            .bodyJson(Json.parse("{ \"disparoDetectores\": [{}], \"imposicaoPlanos\": [{}], \"imposicaoModos\": [{}], \"falhasControlador\": [{}], \"alarmesControlador\": [{}] }"));
        result = route(request);
        assertEquals(422, result.status());

        json = Json.parse(Helpers.contentAsString(result));
        erros = parseErrors((ArrayNode) json);

        assertEquals(18, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("ParametroSimulacao", "não pode ficar em branco", "inicioControlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "controlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "velocidade"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "detectores[0].disparo"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "detectores[0].detector"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "detectores[0].anel"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoes[0].disparo"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoes[0].plano"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoes[0].anel"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoes[0].duracao"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoesModos[0].disparo"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoesModos[0].modoOperacao"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoesModos[0].anel"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "imposicoesModos[0].duracao"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "falhas[0].disparo"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "falhas[0].falha"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "alarmes[0].disparo"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "alarmes[0].alarme")
        ));
    }

    @Test
    public void testSimular() throws MqttException, InterruptedException {

        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        String payload = "{" +
            "\"disparoDetectores\":[{\"detector\":{\"id\":\"e88e4ee0-190b-4b53-8f80-7b4f71bd64b0\",\"tipo\":\"VEICULAR\",\"posicao\":1,\"monitorado\":true,\"nome\":\"Anel 1 - DV1\", \"anel\": {\"id\":\"e88e4ee0-190b-4b53-8f80-7b4f71bd64b1\",\"posicao\":1}},\"disparo\":\"2016-02-01T02:10:00.000Z\"}]," +
            "\"imposicaoPlanos\":[{\"plano\":{\"id\":\"245995cd-97e4-494f-b7d8-7d2c18053071\",\"posicao\":1,\"descricao\":\"PLANO 1\",\"modoOperacao\":\"TEMPO_FIXO_ISOLADO\",\"nome\":\"Plano 1\"},\"anel\":{\"posicao\":1},\"disparo\":\"2016-02-01T02:20:00.000Z\",\"duracao\":2}]," +
            "\"imposicaoModos\":[{\"modo\":\"TEMPO_FIXO_ISOLADO\",\"anel\":{\"posicao\":1},\"disparo\":\"2016-02-01T02:20:00.000Z\",\"duracao\":2}]," +
            "\"idControlador\":\"" + controlador.getId().toString() + "\"," +
            "\"velocidade\":\"0.5\"," +
            "\"inicioControlador\":\"2016-02-01T02:00:00.000Z\"}";

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(controllers.simulacao.routes.SimuladorController.simular().url())
            .bodyJson(Json.parse(payload));
        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(5, json.size());
        assertTrue(json.has("simulacaoId"));
        assertTrue(json.has("controladorId"));
        assertEquals(controlador.getId().toString(), json.get("controladorId").asText());
        assertTrue(json.has("tempoCicloAnel"));
        assertTrue(json.has("aneis"));

        String idSimulacao = json.get("simulacaoId").asText();
        SimuladorClientHelper sim = new SimuladorClientHelper(idSimulacao);
        sim.buscarPagina(0);
        await().until(() -> sim.getEstados().size() > 0);

    }





    private List<Erro> parseErrors(ArrayNode errosJson) {
        List<Erro> erros = new ArrayList<>();
        for (JsonNode erroJson : errosJson) {
            erros.add(new Erro(erroJson.get("root").asText(), erroJson.get("message").asText(), erroJson.get("path").asText()));
        }
        return erros;
    }




}
