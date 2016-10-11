package simulacao;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Controlador;
import org.joda.time.DateTime;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import models.simulador.parametros.ParametroSimulacao;

import static play.test.Helpers.route;


public class SimuladorControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void simulacao() {
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();

        ParametroSimulacao params = new ParametroSimulacao();
        params.setControlador(controlador);
        params.setInicioSimulacao(new DateTime());

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(controllers.simulacao.routes.SimuladorController.simular(controlador.getId().toString()).url()).bodyJson(Json.toJson(params));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

//
//        assertEquals(200, postResult.status());
//        assertEquals("Teste", cidadeRetornada.getNome());
//        assertNotNull(cidadeRetornada.getId());
    }

}
