package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import controllers.routes;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
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
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ImposicoesControllerTest extends BasicMQTTTest {

    @Test
    public void imposicaoPlanoTestOk() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
            .bodyJson(Json.toJson(aneisIds));
        Result result = route(request);
        assertEquals(OK, result.status());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(1, ids.keySet().size());
        assertEquals(controlador.getId().toString(), ids.keySet().toArray()[0]);

        assertTransacaoOk();
    }

    @Test
    public void imposicaoPlanoTestErro() {
        startClient();
        await().until(() -> onPublishFutureList.size() > 4);

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.plano().url())
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
        await().until(() -> onPublishFutureList.size() > 4);


        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracao().url())
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
        await().until(() -> onPublishFutureList.size() > 5);

        Anel anel = controlador.getAneis().stream().filter(anel1 -> !anel1.isAtivo()).findAny().orElse(null);
        anel.setAtivo(true);
        controlador.update();

        List<UUID> aneisIds = controlador.getAneis().stream().map(Anel::getId).collect(Collectors.toList());
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.configuracao().url())
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
        await().atMost(10, TimeUnit.MINUTES).until(() -> onPublishFutureList.size() > 5);

        List<String> aneisIds = controlador.getAneis().stream().filter(Anel::isAtivo).map(anel -> anel.getId().toString()).collect(Collectors.toList());
        Map<String, Object> params = new HashMap<>();
        params.put("aneis", aneisIds.toArray());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("duracao", 30);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 6 + 7*aneisIds.size());
        assertEquals(6 + 7*aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
    }

    @Test
    public void imposicaoModoOperacaoTestErro() {
        controlador = new ControladorHelper().setPlanos(controlador);
        startClient();
        await().atMost(10, TimeUnit.MINUTES).until(() -> onPublishFutureList.size() > 5);

        List<String> aneisIds = controlador.getAneis().stream().filter(Anel::isAtivo).map(anel -> anel.getId().toString()).collect(Collectors.toList());
//        List<Anel> aneis = controlador.getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
//        List<String> aneisIds = new ArrayList<>();
//        aneisIds.add(aneis.get(0).getId().toString());

        Map<String, Object> params = new HashMap<>();
        params.put("aneis", aneisIds.toArray());
        params.put("modoOperacao", ModoOperacaoPlano.INTERMITENTE);
        params.put("duracao", -1);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ImposicoesController.modoOperacao().url())
            .bodyJson(Json.toJson(params));
        Result result = route(request);
        assertEquals(OK, result.status());

        await().until(() -> onPublishFutureList.size() >= 6 + 5*aneisIds.size());
        assertEquals(6 + 5*aneisIds.size(), onPublishFutureList.size());

        Map<String, String> ids = Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class);
        assertEquals(aneisIds.size(), ids.keySet().size());
        ids.forEach((anelId, transacaoId) -> assertTrue(aneisIds.contains(anelId)));
    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
