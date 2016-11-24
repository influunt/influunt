package integracao;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import controllers.routes;
import models.Anel;
import models.Controlador;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
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
        await().until(() -> onPublishFutureList.size() > 4);

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

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}
