import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

import java.util.*;

import com.google.inject.Inject;
import com.sun.media.jfxmedia.logging.Logger;
import models.*;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import play.Application;
import play.Mode;
import play.i18n.Messages;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.i18n.MessagesApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;
import services.*;

public class ControladoresControllerTest extends WithApplication {
    JPAApi jpaApi;

    private CidadeCrudService cidadeCrudService;
    private AreaCrudService areaCrudService;
    private FabricanteCrudService fabricanteCrudService;
    private ConfiguracaoControladorCrudService configuracaoControladorCrudService;
    private ModeloControladorCrudService modeloControladorCrudService;
    private CoordenadaGeograficaCrudService coordenadaGeograficaCrudService;
    private ControladorCrudService controladorCrudService;

    private Cidade cidade;
    private Area area;
    private Fabricante fabricante;
    private ConfiguracaoControlador configuracao;
    private ModeloControlador modeloControlador;
    private CoordenadaGeografica coordenadaGeografica;



    @Before
    public void setUp() {

        jpaApi = app.injector().instanceOf(JPAApi.class);
        cidadeCrudService = app.injector().instanceOf(CidadeCrudService.class);
        areaCrudService = app.injector().instanceOf(AreaCrudService.class);
        fabricanteCrudService = app.injector().instanceOf(FabricanteCrudService.class);
        configuracaoControladorCrudService = app.injector().instanceOf(ConfiguracaoControladorCrudService.class);
        modeloControladorCrudService = app.injector().instanceOf(ModeloControladorCrudService.class);
        controladorCrudService = app.injector().instanceOf(ControladorCrudService.class);


        this.cidade = new Cidade();
        this.cidade.setNome("Belo Horizonte");

        jpaApi.withTransaction(() -> { this.cidadeCrudService.save(cidade); });
        assertNotNull(cidade.getId());

        this.area =  new Area();
        this.area.setDescricao("CTA-1");
        this.area.setCidade(cidade);
        this.area = jpaApi.withTransaction(() -> { return areaCrudService.save(this.area); });

        this.fabricante = new Fabricante();
        this.fabricante.setNome("Raro Labs");
        this.fabricante = jpaApi.withTransaction(() -> { return fabricanteCrudService.save(this.fabricante); });

        this.configuracao = new ConfiguracaoControlador();
        this.configuracao.setLimiteAnel(4);
        this.configuracao.setLimiteGrupoSemaforico(16);
        this.configuracao = jpaApi.withTransaction(() -> { return configuracaoControladorCrudService.save(this.configuracao); });

        this.modeloControlador = new ModeloControlador();
        this.modeloControlador.setConfiguracao(configuracao);
        this.modeloControlador.setFabricante(fabricante);
        this.modeloControlador = jpaApi.withTransaction(() -> { return modeloControladorCrudService.save(this.modeloControlador); });

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
                .overrides(bind(Authenticator.class).to(TestAuthenticator.class)).in(Mode.TEST).build();

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

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.create().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);
        assertEquals(200, postResult.status());
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json,Controlador.class);
        assertNotNull(controladorRetornado.getId());
        assertNotNull(controladorRetornado.getCoordenada().getId());
        assertEquals(configuracao.getLimiteAnel(),Integer.valueOf(controlador.getAneis().size()));

    }

    @Test
    public void testAtualizarControladorExistenteComConfiguracaoInvalida() {
        Controlador controlador = getControlador();
        controladorCrudService.save(controlador);
        controlador.setAneis(null);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update(controlador.getId()).url())
                .bodyJson(Json.toJson(controlador));
        Result result = route(request);

        assertEquals(UNPROCESSABLE_ENTITY, result.status());
    }

    @Test
    public void testAtualizarControladorExistente() {
        Controlador controlador = getControlador();
        controladorCrudService.save(controlador);
        CoordenadaGeografica coordenadaGeografica = controlador.getCoordenada();
        controlador.setCoordenada(new CoordenadaGeografica(2.0,3.0));

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update(controlador.getId()).url())
                .bodyJson(Json.toJson(controlador));
        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorAtualizado =  Json.fromJson(json,Controlador.class);
        assertEquals(controlador.getId(),controladorAtualizado.getId());
        assertNotEquals(coordenadaGeografica.getId(),controladorAtualizado.getCoordenada().getId());
    }

    @Test
    public void testAtualizarControladorNaoExistente() {
        Controlador controlador = getControlador();

        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.update("xxxx").url())
                .bodyJson(Json.toJson(controlador));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarControladorExistente() {
        Controlador controlador = getControlador();
        String controladorId = controlador.getId();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ControladoresController.delete(controladorId).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        controlador = controladorCrudService.findOne(controladorId);
        assertNull(controlador);
    }

    @Test
    public void testApagarControladorNaoExistente() {

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.CidadesController.delete("1234").url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListarControladores() {

        controladorCrudService.save(getControlador());
        controladorCrudService.save(getControlador());
        controladorCrudService.save(getControlador());

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
        controlador.setCoordenada(new CoordenadaGeografica(1.0,2.0));
        controlador.setIdControlador("10.02.122");
        controlador.setModelo(modeloControlador);
        controlador.setFirmware("1.0.0");
        List<Anel> aneis = Arrays.asList(new Anel(), new Anel(), new Anel(), new Anel());
        controlador.setAneis(aneis);

        List<GrupoSemaforico> grupoSemaforicos = Arrays.asList(new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),
                new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),
                new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),
                new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico(),new GrupoSemaforico());

        controlador.setGruposSemaforicos(grupoSemaforicos);
        return controlador;
    }



}
