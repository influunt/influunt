package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import controllers.routes;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicoesControllerTest extends BasicMQTTTest {

    @Test
    public void imposicaoPacotePlanosTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.pacotePlano().url())
            .bodyJson(Json.toJson(aneisIds));
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());
        assertEquals(controlador.getId().toString(), ids.keySet().toArray()[0]);

        assertTransacaoOk();
    }

    @Test
    public void imposicaoPacotePlanosTestErro() {
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.pacotePlano().url())
            .bodyJson(Json.toJson(aneisIds));
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());
        assertEquals(controlador.getId().toString(), ids.keySet().toArray()[0]);

        assertTransacaoErro();
    }

    @Test
    public void envioConfiguracaoCompletaTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracaoCompleta().url())
            .bodyJson(Json.toJson(aneisIds));
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());
        assertEquals(controlador.getId().toString(), ids.keySet().toArray()[0]);

        assertTransacaoOk();
    }

    @Test
    public void envioConfiguracaoCompletaTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().orElse(null);
        anel.setAtivo(true);
        controlador.update();

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracaoCompleta().url())
            .bodyJson(Json.toJson(aneisIds));
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());
        assertEquals(controlador.getId().toString(), ids.keySet().toArray()[0]);

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
        params.put("aneis", aneisIds.toArray());
        params.put("horarioEntrada", new DateTime().plusSeconds(10).getMillis());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("duracao", 30);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 7 + 7 * aneisIds.size());
        assertEquals(7 + 7 * aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
    }

    @Test
    public void imposicaoModoOperacaoTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<String> aneisIds = controlador.getAneis().stream().filter(Anel::isAtivo).map(anel -> anel.getId().toString()).collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("aneis", aneisIds.toArray());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("horarioEntrada", new DateTime().plusSeconds(10).getMillis());
        params.put("duracao", -1);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() > 6 + 5 * aneisIds.size());

        assertEquals(7 + 5 * aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
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
        params.put("aneis", aneisIds.toArray());
        params.put("posicaoPlano", posicaoPlano);
        params.put("horarioEntrada", new DateTime().plusSeconds(1).getMillis());
        params.put("duracao", 30);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        // 7 -> configuração inicial
        // 5 * aneis -> 5 mensagens para cada transaçao
        // 1 -> mensagem de troca de plano
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6 + 7 * aneisIds.size());

        assertEquals(7 + 7 * aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
    }

    @Test
    public void imposicaoPlanoTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
        List<String> aneisIds = aneis.stream().map(anel -> anel.getId().toString()).collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("aneis", aneisIds.toArray());
        params.put("horarioEntrada", new DateTime().plusSeconds(10).getMillis());
        params.put("posicaoPlano", -1);
        params.put("duracao", 30);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 7 + 5 * aneisIds.size());

        // 7 - Mensagens iniciais
        // 5 para cada anel
        assertEquals(7 + 5 * aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
    }

    @Test
    public void liberarImposicaoTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        Anel anel = controlador.getAneis().stream().filter(Anel::isAtivo).findFirst().orElse(null);
        Map<String, Object> params = new HashMap<>();
        params.put("anelId", anel.getId().toString());

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.liberar().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        assertEquals(7, onPublishFutureList.size());

        assertTransacaoOk();
    }

    @Test
    public void liberarImposicaoTestAnelNaoExistente() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.SECONDS).until(() -> onPublishFutureList.size() > 6);

        Map<String, Object> params = new HashMap<>();
        params.put("anelId", "1234");

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.liberar().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(NOT_FOUND, result.status());

        assertEquals(7, onPublishFutureList.size());
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
