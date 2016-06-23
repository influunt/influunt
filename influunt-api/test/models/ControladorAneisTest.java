package models;

import checks.ControladorAneisCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.Arrays;
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
public class ControladorAneisTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {

        Controlador controlador = getControladorDadosBasicos();
        controlador.save();

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador","Ao menos um anel deve estar ativo","")
        ));

        Anel anel1 = controlador.getAneis().get(0);
        anel1.setAtivo(true);

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador","Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador","aneis[0]")
        ));

        anel1.setEstagios(Arrays.asList(new Estagio(),new Estagio()));

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador","Esse anel deve ter no mínimo 2 grupos semáforicos","aneis[0]")
        ));

        anel1.setQuantidadeGrupoPedestre(100);
        anel1.setQuantidadeGrupoVeicular(100);
        anel1.setQuantidadeDetectorPedestre(5);
        anel1.setQuantidadeDetectorVeicular(9);


        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Numero total de grupos semáforicos informado individualmente nos aneis excede o limite do controlador",""),
                new Erro("Controlador","Latitude deve ser informada","aneis[0].latitudeOk"),
                new Erro("Controlador","Longitude deve ser informada","aneis[0].longitudeOk"),
                new Erro("Controlador","Numero total de detectores de pedestre informado individualmente nos aneis excede o limite do controlador",""),
                new Erro("Controlador","Numero total de detectores veiculares informado individualmente nos aneis excede o limite do controlador","")

        ));


        anel1.setLatitude(1.0);
        anel1.setLongitude(1.0);
        anel1.setQuantidadeGrupoPedestre(1);
        anel1.setQuantidadeGrupoVeicular(1);
        anel1.setQuantidadeDetectorPedestre(4);
        anel1.setQuantidadeDetectorVeicular(8);


        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.empty());



    }

    @Override
    @Test
    public void testNoValidationErro() {
//        List<Erro> erros = new InfluuntValidator<Controlador>().validate(getControladorDadosBasicos());
//        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
//        Controlador controlador = getControladorDadosBasicos();
//        controlador.save();
//        assertNotNull(controlador.getId());
//        assertEquals("Criação de aneis",4,controlador.getAneis().size());
//        assertEquals("Todoas aneis inativos",0,controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
    }

    @Override
    @Test
    public void testJSON() {
//        Controlador controlador = getControladorDadosBasicos();
//        Controlador controladorJson = Json.fromJson(Json.toJson(controlador),Controlador.class);
//
//        assertEquals(controlador.getId(),controladorJson.getId());
//        assertControlador(controlador, controladorJson);
//
//        controlador.save();
//        controladorJson = Json.fromJson(Json.toJson(controlador),Controlador.class);
//
//        assertEquals(controlador.getId(),controladorJson.getId());
//        assertControlador(controlador, controladorJson);
//        assertNotNull(controladorJson.getId());
//        assertEquals("Criação de aneis",4,controladorJson.getAneis().size());
//        assertEquals("Todoas aneis inativos",0,controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());

    }

    private void assertControlador(Controlador controlador, Controlador controladorJson) {
//        assertEquals(controlador.getArea().getId(),controladorJson.getArea().getId());
//        assertEquals(controlador.getModelo().getId(),controladorJson.getModelo().getId());
//        assertEquals(controlador.getNumeroSMEE(),controladorJson.getNumeroSMEE());
//        assertEquals(controlador.getNumeroSMEEConjugado1(),controladorJson.getNumeroSMEEConjugado1());
//        assertEquals(controlador.getNumeroSMEEConjugado2(),controladorJson.getNumeroSMEEConjugado2());
//        assertEquals(controlador.getNumeroSMEEConjugado3(),controladorJson.getNumeroSMEEConjugado3());
//        assertEquals(controlador.getLocalizacao(),controladorJson.getLocalizacao());
//        assertEquals(controlador.getLatitude(),controladorJson.getLatitude());
//        assertEquals(controlador.getLongitude(),controladorJson.getLongitude());
//        assertEquals(controlador.getFirmware(),controladorJson.getFirmware());
    }

    @Override
    @Test
    public void testControllerValidacao() {
//        Controlador controlador = getControlador();
//
//        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
//                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));
//        Result postResult = route(postRequest);
//
//        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());
//
//        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
//        assertEquals(5,json.size());

    }

    @Override
    @Test
    public void testController() {
//        Controlador controlador = getControladorDadosBasicos();
//
//        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
//                .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(Json.toJson(controlador));
//        Result postResult = route(postRequest);
//
//        assertEquals(OK, postResult.status());
//
//        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
//        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
//
//        assertControlador(controlador, controladorRetornado);
//        assertNotNull(controladorRetornado.getId());
//        assertEquals("Criação de aneis",4,controladorRetornado.getAneis().size());
//        assertEquals("Todoas aneis inativos",0,controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());

    }
}
