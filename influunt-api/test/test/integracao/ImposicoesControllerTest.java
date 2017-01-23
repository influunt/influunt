package test.integracao;

import checks.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.api.routes;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicoesControllerTest extends BasicMQTTTest {

    @Test
    public void imposicaoPacotePlanosTestOk() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        ObjectNode json = Json.newObject();
        ArrayNode aneis = json.putArray("aneisIds");

        aneisIds.stream().forEach(a -> {
            aneis.add(a.toString());
        });

        json.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.pacotePlano().url())
            .bodyJson(json);
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());

        assertTransacaoOk();
    }

    @Test
    public void imposicaoPacotePlanosTestErro() throws IOException {
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        ObjectNode json = Json.newObject();
        ArrayNode aneis = json.putArray("aneisIds");

        aneisIds.stream().forEach(a -> {
            aneis.add(a.toString());
        });

        json.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.pacotePlano().url())
            .bodyJson(json);
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());

        assertTransacaoErro();
    }

    @Test
    public void envioConfiguracaoCompletaTestOk() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        ObjectNode json = Json.newObject();
        ArrayNode aneis = json.putArray("aneisIds");

        aneisIds.stream().forEach(a -> {
            aneis.add(a.toString());
        });

        json.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracaoCompleta().url())
            .bodyJson(json);
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());

        assertTransacaoOk();
    }

    @Test
    public void envioConfiguracaoCompletaTestErro() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().orElse(null);
        anel.setAtivo(true);
        controlador.update();

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());

        ObjectNode json = Json.newObject();
        ArrayNode aneis = json.putArray("aneisIds");

        aneisIds.stream().forEach(a -> {
            aneis.add(a.toString());
        });

        json.put("timeout", 60);


        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracaoCompleta().url())
            .bodyJson(json);
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());

        assertTransacaoErro();
    }

    @Test
    public void imposicaoModoOperacaoTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
        List<String> aneisIds = new ArrayList<>();
        aneisIds.add(aneis.get(0).getId().toString());

        Map<String, Object> params = new HashMap<>();
        params.put("aneisIds", aneisIds.toArray());
        params.put("horarioEntrada", new DateTime().plusSeconds(10).getMillis());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("duracao", 30);
        params.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 9 + 4);

        // 7 -> configuração inicial
        // 4 * aneis -> 4 mensagens para cada transaçao
        // 2 -> Mensagem da APP
        assertEquals(9 + 4, onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
    }

    @Test
    public void imposicaoModoOperacaoTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<String> aneisIds = controlador.getAneis().stream().filter(Anel::isAtivo).map(anel -> anel.getId().toString()).collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("aneisIds", aneisIds.toArray());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("horarioEntrada", new DateTime().plusSeconds(10).getMillis());
        params.put("duracao", -1);
        params.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 9 + 4);

        // 7 -> configuração inicial
        // 4 * aneis -> 4 mensagens para cada transaçao
        // 2 -> Mensagem da APP
        assertTrue(onPublishFutureList.size() >= 9 + 4);

    }

    @Test
    public void imposicaoPlanoTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
        List<String> aneisIds = aneis.stream().map(anel -> anel.getId().toString()).collect(Collectors.toList());

        Integer posicaoPlano = 1;
        boolean planoConfiguradoEmTodosOsAneis = aneis.stream()
            .filter(Anel::isAtivo)
            .allMatch(anel -> anel.getPlanos().stream().anyMatch(plano -> posicaoPlano.equals(plano.getPosicao())));
        assertTrue(planoConfiguradoEmTodosOsAneis);


        Map<String, Object> params = new HashMap<>();
        params.put("aneisIds", aneisIds.toArray());
        params.put("posicaoPlano", posicaoPlano);
        params.put("horarioEntrada", new DateTime().plusSeconds(1).getMillis());
        params.put("duracao", 30);
        params.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        // 7 -> configuração inicial
        // 4 * aneis -> 4 mensagens para cada transaçao
        // 2 -> Mensagem da APP
        await().atMost(20, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() >= 9 + 4);

        assertTrue(onPublishFutureList.size() >= 9 + 4);

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
    }

    @Test
    public void imposicaoPlanoTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
        List<String> aneisIds = aneis.stream().map(anel -> anel.getId().toString()).collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("aneisIds", aneisIds.toArray());
        params.put("horarioEntrada", new DateTime().plusSeconds(1).getMillis());
        params.put("posicaoPlano", -1);
        params.put("duracao", 30);
        params.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 9 + 4);

        // 7 -> configuração inicial
        // 4 * aneis -> 4 mensagens para cada transaçao
        // 2 -> Mensagem da APP
        assertTrue(onPublishFutureList.size() >= 9 + 4);

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
    }

    @Test
    public void liberarImposicaoTestOk() throws IOException {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        Anel anel = controlador.getAneis().stream().filter(Anel::isAtivo).findFirst().orElse(null);
        Map<String, Object> params = new HashMap<>();
        params.put("aneisIds", Arrays.asList(anel.getId().toString()));
        params.put("timeout", 60);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.liberar().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 9 + 4);

        // 7 -> configuração inicial
        // 4 * aneis -> 4 mensagens para cada transaçao
        // 2 -> Mensagem da APP
        assertEquals(9 + 4, onPublishFutureList.size());

        assertTransacaoOk();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, PlanosCentralCheck.class,
            TabelaHorariosCheck.class);
    }

}
