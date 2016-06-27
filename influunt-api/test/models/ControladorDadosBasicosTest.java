package models;

import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ControladorDadosBasicosTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(getControlador());

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "modelo"),
                new Erro("Controlador", "não pode ficar em branco", "localizacao"),
                new Erro("Controlador", "não pode ficar em branco", "area"),
                new Erro("Controlador", "não pode ficar em branco", "latitude"),
                new Erro("Controlador", "não pode ficar em branco", "longitude")
        ));

    }

    @Override
    @Test
    public void testNoValidationErro() {
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(getControladorDadosBasicos());
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorDadosBasicos();
        Controlador controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControlador(controlador, controladorJson);

        controlador.save();
        controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControlador(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());

    }

    private void assertControlador(Controlador controlador, Controlador controladorJson) {
        assertEquals(controlador.getArea().getId(), controladorJson.getArea().getId());
        assertEquals(controlador.getModelo().getId(), controladorJson.getModelo().getId());
        assertEquals(controlador.getNumeroSMEE(), controladorJson.getNumeroSMEE());
        assertEquals(controlador.getNumeroSMEEConjugado1(), controladorJson.getNumeroSMEEConjugado1());
        assertEquals(controlador.getNumeroSMEEConjugado2(), controladorJson.getNumeroSMEEConjugado2());
        assertEquals(controlador.getNumeroSMEEConjugado3(), controladorJson.getNumeroSMEEConjugado3());
        assertEquals(controlador.getLocalizacao(), controladorJson.getLocalizacao());
        assertEquals(controlador.getLatitude(), controladorJson.getLatitude());
        assertEquals(controlador.getLongitude(), controladorJson.getLongitude());
        assertEquals(controlador.getFirmware(), controladorJson.getFirmware());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControlador();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(5, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorDadosBasicos();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json, Controlador.class);

        assertControlador(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());

    }
}
