package simulacao;

import checks.Erro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Controlador;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.route;


public class SimuladorControllerTest extends WithInfluuntApplicationNoAuthentication {

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

        assertEquals(5, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("ParametroSimulacao", "não pode ficar em branco", "inicioControlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "inicioSimulacao"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "fimSimulacao"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "controlador"),
            new Erro("ParametroSimulacao", "não pode ficar em branco", "velocidade")
        ));
    }

    @Test
    public void testSimular() {
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        String payload = "{\"disparoDetectores\":[{\"detector\":{\"id\":\"e88e4ee0-190b-4b53-8f80-7b4f71bd64b0\",\"tipo\":\"VEICULAR\",\"posicao\":1,\"monitorado\":true,\"nome\":\"Anel 1 - DV1\"},\"disparo\":\"2016-02-01T02:10:00.000Z\"}],\"imposicaoPlanos\":[{\"plano\":{\"id\":\"245995cd-97e4-494f-b7d8-7d2c18053071\",\"posicao\":1,\"descricao\":\"PLANO 1\",\"modoOperacao\":\"TEMPO_FIXO_ISOLADO\",\"nome\":\"Plano 1\"},\"disparo\":\"2016-02-01T02:20:00.000Z\"}],\"idControlador\":\"" + controlador.getId().toString() + "\",\"velocidade\":\"0.5\",\"inicioControlador\":\"2016-02-01T02:00:00.000Z\",\"inicioSimulacao\":\"2016-02-01T02:00:00.000Z\",\"fimSimulacao\":\"2016-02-01T03:00:00.000Z\"}";

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
    }


    private List<Erro> parseErrors(ArrayNode errosJson) {
        List<Erro> erros = new ArrayList<>();
        for (JsonNode erroJson : errosJson) {
            erros.add(new Erro(erroJson.get("root").asText(), erroJson.get("message").asText(), erroJson.get("path").asText()));
        }
        return erros;
    }

}
