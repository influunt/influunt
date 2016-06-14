import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;
import services.*;

public class ControladoresControllerTest extends WithApplication {
    @Inject
    JPAApi jpaApi;

    CidadeCrudService cidadeCrudService;
    AreaCrudService areaCrudService;
    FabricanteCrudService fabricanteCrudService;
    ConfiguracaoControladorCrudService configuracaoControladorCrudService;
    ModeloControladorCrudService modeloControladorCrudService;
    CoordenadaGeograficaCrudService coordenadaGeograficaCrudService;

    private Cidade cidade;
    private Area area;
    private Fabricante fabricante;
    private ConfiguracaoControlador configuracao;
    private ModeloControlador modeloControlador;
    private CoordenadaGeografica coordenadaGeografica;


    @Before
    public void setUp() {

        cidadeCrudService = app.injector().instanceOf(CidadeCrudService.class);
        areaCrudService = app.injector().instanceOf(AreaCrudService.class);
        fabricanteCrudService = app.injector().instanceOf(FabricanteCrudService.class);
        configuracaoControladorCrudService = app.injector().instanceOf(ConfiguracaoControladorCrudService.class);
        modeloControladorCrudService = app.injector().instanceOf(ModeloControladorCrudService.class);
        coordenadaGeograficaCrudService = app.injector().instanceOf(CoordenadaGeograficaCrudService.class);


        this.cidade = new Cidade();
        this.cidade.setNome("Belo Horizonte");

        this.cidadeCrudService.save(cidade);
        assertNotNull(cidade.getId());

        this.area =  new Area();
        this.area.setDescricao("CTA-1");
        this.area.setCidade(cidade);
        this.area = areaCrudService.save(this.area);

        this.fabricante = new Fabricante();
        this.fabricante.setNome("Raro Labs");
        this.fabricante = fabricanteCrudService.save(this.fabricante);

        this.configuracao = new ConfiguracaoControlador();
        this.configuracao.setLimiteAnel(4);
        this.configuracao.setLimiteGrupoSemaforico(16);
        this.configuracao = configuracaoControladorCrudService.save(this.configuracao);

        this.modeloControlador = new ModeloControlador();
        this.modeloControlador.setConfiguracao(configuracao);
        this.modeloControlador.setFabricante(fabricante);
        this.modeloControlador = modeloControladorCrudService.save(this.modeloControlador);

        this.coordenadaGeografica = new CoordenadaGeografica();
        this.coordenadaGeografica.setLatitude(1.0);
        this.coordenadaGeografica.setLongitude(2.0);
        this.coordenadaGeografica = coordenadaGeograficaCrudService.save(coordenadaGeografica);



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

        Controlador controlador = new Controlador();
        controlador.setDescricao("Teste");
        controlador.setNumeroSMEE("1234");
        controlador.setArea(area);
        controlador.setCoordenada(coordenadaGeografica);
        controlador.setIdControlador("10.02.122");
        controlador.setModelo(modeloControlador);
        controlador.setFirmware("1.0.0");
        List<Anel> aneis = new ArrayList<Anel>();
        aneis.add(new Anel());
        controlador.setAneis(aneis);

        List<GrupoSemaforico> grupoSemaforicos = new ArrayList<GrupoSemaforico>();
        grupoSemaforicos.add(new GrupoSemaforico());
        controlador.setGruposSemaforicos(grupoSemaforicos);


        play.Logger.info("JSON ENVIADO:" + Json.toJson(controlador).toString());
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.create().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);
        assertEquals(200, postResult.status());
        //JsonNode json = Json.parse(Helpers.contentAsString(postResult));
    }

//    @Test
//    public void testAtualizarCidadeExistente() {
//        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
//        Cidade cidade = new Cidade();
//        cidade.setNome("Teste");
//        cidadeService.save(cidade);
//
//        String cidadeId = cidade.getId();
//        cidade.setNome("Teste atualizar");
//        
//        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
//                .uri(routes.CidadesController.update(cidadeId).url())
//                .bodyJson(Json.toJson(cidade));
//        Result putResult = route(putRequest);
//        JsonNode json = Json.parse(Helpers.contentAsString(putResult));
//        Cidade cidadeRetornada = Json.fromJson(json, Cidade.class);
//        
//        assertEquals(200, putResult.status());
//        assertEquals("Teste atualizar", cidadeRetornada.getNome());
//        assertNotNull(cidadeRetornada.getId());
//    }
//    
//    @Test
//    public void testAtualizarCidadeNaoExistente() {
//        Cidade cidade = new Cidade();
//        cidade.setNome("Teste");
//        
//        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
//                .uri(routes.CidadesController.update("1234").url())
//                .bodyJson(Json.toJson(cidade));
//        Result putResult = route(putRequest);
//        assertEquals(404, putResult.status());
//    }
//
//    @Test
//    public void testApagarCidadeExistente() {
//        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
//        Cidade cidade = new Cidade();
//        cidade.setNome("Teste");
//        cidadeService.save(cidade);
//        String cidadeId = cidade.getId();
//
//        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
//                .uri(routes.CidadesController.delete(cidadeId).url());
//        Result result = route(deleteRequest);
//           
//        assertEquals(200, result.status());
//        cidade = cidadeService.findOne(cidadeId);
//        assertNull(cidade);
//    }
//    
//    @Test
//    public void testApagarCidadeNaoExistente() {
//        Cidade cidade = new Cidade();
//        cidade.setNome("Teste");
//
//        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
//                .uri(routes.CidadesController.delete("1234").url());
//        Result result = route(deleteRequest);
//        assertEquals(404, result.status());
//    }
//    
//    @Test
//    public void testListarCidades() {
//        CidadeCrudService cidadeService = app.injector().instanceOf(CidadeCrudService.class);
//        
//        Cidade cidade = new Cidade();
//        cidade.setNome("Cidade 1");
//        cidadeService.save(cidade);
//        cidade.setNome("Cidade 2");
//        cidade.setId(null);
//        cidadeService.save(cidade);
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
//                .uri(routes.CidadesController.findAll().url());
//        Result result = route(request);
//        JsonNode json = Json.parse(Helpers.contentAsString(result));
//        List<Cidade> cidades = Json.fromJson(json, List.class);  
//        
//        assertEquals(200, result.status());
//        assertEquals(2, cidades.size());
//    }

}
