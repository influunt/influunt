import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import models.*;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.Bindings;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;

import java.util.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class ControladoresControllerTest extends WithApplication {
    private Cidade cidade;
    private Area area;
    private Fabricante fabricante;
    private ConfiguracaoControlador configuracao;
    private ModeloControlador modeloControlador;
    private TipoGrupoSemaforico tipoGrupoSemaforico;

    @Before
    public void setUp() {


        this.cidade = new Cidade();
        this.cidade.setNome("Belo Horizonte");
        this.cidade.save();

        this.area =  new Area();
        this.area.setDescricao("CTA-1");
        this.area.setCidade(cidade);
        this.area.save();

        this.fabricante = new Fabricante();
        this.fabricante.setNome("Raro Labs");
        this.fabricante.save();

        this.configuracao = new ConfiguracaoControlador();
        this.configuracao.setLimiteAnel(4);
        this.configuracao.setLimiteGrupoSemaforico(16);
        this.configuracao.save();

        this.modeloControlador = new ModeloControlador();
        this.modeloControlador.setConfiguracao(configuracao);
        this.modeloControlador.setFabricante(fabricante);
        this.modeloControlador.save();

        this.tipoGrupoSemaforico = new TipoGrupoSemaforico();
        this.tipoGrupoSemaforico.setDescricao("Veicular");
        this.tipoGrupoSemaforico.save();


    }

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(Bindings.bind(Authenticator.class).to(TestAuthenticator.class)).in(Mode.TEST).build();
    }

    @Test
    public void testCriarNovoControladorVazio() {
        Controlador controlador = new Controlador();

        play.Logger.info("JSON ENVIADO:" + Json.toJson(controlador).toString());
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.create().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);
        assertEquals(422, postResult.status());
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
    }

    @Test
    public void testCriarNovoComDadosBasicos() {
        Controlador controlador = getControlador();

        Logger.debug("JSON:" + Json.toJson(controlador));
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.create().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Logger.debug(json.toString());

        assertEquals(200, postResult.status());
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        assertNotNull(controladorRetornado.getId());
        assertNotNull(controladorRetornado.getArea().getId());
        assertEquals(configuracao.getLimiteAnel(),Integer.valueOf(controlador.getAneis().size()));

    }

    @Test
    public void testAtualizarControladorExistenteComConfiguracaoInvalida() {
        Controlador controlador = getControlador();
        controlador.save();
        controlador.setAneis(null);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update(controlador.getId().toString()).url())
                .bodyJson(Json.toJson(controlador));
        Result result = route(request);

        assertEquals(UNPROCESSABLE_ENTITY, result.status());
    }



    @Test
    public void testAtualizarAreaControladorExistente() {
        Controlador controlador = getControlador();
        controlador.save();

        assertEquals(4,controlador.getAneis().size());
        assertEquals(16,controlador.getGruposSemaforicos().size());

        Area novaArea = new Area();
        novaArea.setDescricao("Nova Area");
        novaArea.save();

        assertNotEquals(novaArea.getId(),controlador.getArea().getId());
        controlador.setArea(novaArea);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update(controlador.getId().toString()).url())
                .bodyJson(Json.toJson(controlador));
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorAtualizado =  Json.fromJson(json,Controlador.class);
        assertEquals(controlador.getId(),controladorAtualizado.getId());
        assertEquals(novaArea.getId(),controladorAtualizado.getArea().getId());
        assertEquals(4,controladorAtualizado.getAneis().size());
        assertEquals(16,controladorAtualizado.getGruposSemaforicos().size());
    }
    @Test
    public void testAtualizarControladorNaoExistente() {
        Controlador controlador = getControlador();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(controlador));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarControladorExistente() {
        final Controlador controlador = getControlador();
        controlador.save();
        UUID controladorId = controlador.getId();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ControladoresController.delete(controladorId.toString()).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        Controlador controlador1 = Controlador.find.byId(controladorId);
        assertNull(controlador1);

    }



    @Test
    public void testApagarControladorNaoExistente() {

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListarControladores() {
        getControlador().save();
        getControlador().save();
        getControlador().save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Controlador> controladores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(3, controladores.size());
    }

    private Controlador getControlador() {
        Controlador controlador = new Controlador();
        controlador.setDescricao("Teste");
        controlador.setNumeroSMEE("1234");
        controlador.setArea(area);
        controlador.setLatitude(1.0);
        controlador.setLongitude(2.0);
        controlador.setIdControlador("10.02.122");
        controlador.setModelo(modeloControlador);
        controlador.setFirmware("1.0.0");

        List<Anel> aneis = Arrays.asList(new Anel("Anel 1"),new Anel("Anel 2"),new Anel("Anel 3"),new Anel("Anel 4"));
        controlador.setAneis(aneis);
        for(Anel anel: controlador.getAneis()){
            anel.setControlador(controlador);
        }

        List<GrupoSemaforico> grupoSemaforicos = Arrays.asList(new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),
                new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),
                new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),
                new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico),new GrupoSemaforico(tipoGrupoSemaforico));

        controlador.setGruposSemaforicos(grupoSemaforicos);
        return controlador;
    }



}
