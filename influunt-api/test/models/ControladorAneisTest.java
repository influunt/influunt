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

import static org.junit.Assert.*;
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
                new Erro("Controlador", "Ao menos um anel deve estar ativo", "")
        ));

        Anel anel1 = controlador.getAneis().get(0);
        anel1.setAtivo(true);

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Um anel ativo deve ter ao menos dois estágios e no máximo o limite do modelo do controlador", "aneis[0]")
        ));

        anel1.setEstagios(Arrays.asList(new Estagio(), new Estagio()));

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Esse anel deve ter no mínimo 2 grupos semáforicos", "aneis[0]")
        ));

        anel1.setQuantidadeGrupoPedestre(100);
        anel1.setQuantidadeGrupoVeicular(100);
        anel1.setQuantidadeDetectorPedestre(5);
        anel1.setQuantidadeDetectorVeicular(9);

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Numero total de grupos semáforicos informado individualmente nos aneis excede o limite do controlador", ""),
                new Erro("Controlador", "Latitude deve ser informada", "aneis[0].latitudeOk"),
                new Erro("Controlador", "Longitude deve ser informada", "aneis[0].longitudeOk"),
                new Erro("Controlador", "Numero total de detectores de pedestre informado individualmente nos aneis excede o limite do controlador", ""),
                new Erro("Controlador", "Numero total de detectores veiculares informado individualmente nos aneis excede o limite do controlador", "")
        ));

        anel1.setLatitude(1.0);
        anel1.setLongitude(1.0);
        anel1.setQuantidadeGrupoPedestre(1);
        anel1.setQuantidadeGrupoVeicular(1);
        anel1.setQuantidadeDetectorPedestre(4);
        anel1.setQuantidadeDetectorVeicular(8);

        controlador.save();

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "A quantidade de detectores não deve ultrapassar a quantidade de estágios definidas no modelo do controlador.", "aneis[0]")
        ));

        anel1.setEstagios(Arrays.asList(new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(),
                new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio(), new Estagio()));
        anel1.setDetectores(null);

        // Cria manualmente quantidade de detectores para validacao de numero de detectores diferente do  somatorio de detectores veicular e pedestre
        for (int i = 7; i > 0; i--) {
            Detector detector = new Detector();
            detector.setTipo(TipoDetector.PEDESTRE);
            detector.setAnel(anel1);
            detector.setControlador(anel1.getControlador());
            anel1.getDetectores().add(detector);
        }
        for (int i = 7; i > 0; i--) {
            Detector detector = new Detector();
            detector.setTipo(TipoDetector.VEICULAR);
            detector.setAnel(anel1);
            detector.setControlador(anel1.getControlador());
            anel1.getDetectores().add(detector);
        }

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Um anel ativo não deve ultrapassar o somatório das quantidades de detectores definidas no modelo do controlador", "aneis[0]")
        ));

        anel1.setDetectores(null);
        anel1.setQuantidadeDetectorPedestre(1);
        anel1.setQuantidadeDetectorVeicular(1);

        controlador.getModelo().getConfiguracao().getLimiteDetectorPedestre();
        controlador.getModelo().getConfiguracao().getLimiteDetectorVeicular();

        controlador.save();


        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class);

        assertThat(erros, org.hamcrest.Matchers.empty());


    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorAneis();
        controlador.save();
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorAneis();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 2, controlador.getGruposSemaforicos().size());
        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals("Detectores", 4, anelAtivo.getDetectores().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAneis();
        controlador.save();

        Controlador controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControladorAnel(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        Anel anelAtivo = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals("Criação de grupos semafóricos", 2, anelAtivo.getGruposSemaforicos().size());
        assertEquals("Detectores", 4, anelAtivo.getDetectores().size());

    }

    private void assertControladorAnel(Controlador controlador, Controlador controladorJson) {
        Anel anel = controlador.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get();
        Anel anelJson = controladorJson.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get();

        assertEquals(anel.getDescricao(), anelJson.getDescricao());
        assertEquals(anel.getLatitude(), anelJson.getLatitude());
        assertEquals(anel.getLongitude(), anelJson.getLongitude());
        assertEquals(anel.getQuantidadeGrupoPedestre(), anelJson.getQuantidadeGrupoPedestre());
        assertEquals(anel.getQuantidadeGrupoVeicular(), anelJson.getQuantidadeGrupoVeicular());
        assertEquals(anel.getQuantidadeDetectorPedestre(), anelJson.getQuantidadeDetectorPedestre());
        assertEquals(anel.getQuantidadeDetectorVeicular(), anelJson.getQuantidadeDetectorVeicular());
        assertEquals(anel.getNumeroSMEE(), anelJson.getNumeroSMEE());
        assertEquals(anel.getEstagios().size(), anelJson.getEstagios().size());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.aneis().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(1, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorAneis();


        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.aneis().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json, Controlador.class);

        assertControladorAnel(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 2, controladorRetornado.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get().getGruposSemaforicos().size());

    }
}
