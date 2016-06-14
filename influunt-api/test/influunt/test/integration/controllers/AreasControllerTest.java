package influunt.test.integration.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;
import models.Area;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.Authenticator;
import services.AreaCrudService;

public class AreasControllerTest extends WithApplication {

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
    public void testCriarNovaArea() {
        Area area = new Area();
        area.setDescricao("CTA 1");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.AreasController.create().url()).bodyJson(Json.toJson(area));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Area areaRetornada = Json.fromJson(json, Area.class);
        
        assertEquals(200, postResult.status());
        assertEquals("CTA 1", areaRetornada.getDescricao());
        assertNotNull(areaRetornada.getId());
    }

    @Test
    public void testAtualizarAreaExistente() {
        AreaCrudService areaService = app.injector().instanceOf(AreaCrudService.class);
        Area area = new Area();
        area.setDescricao("Area 1");
        areaService.save(area);
        
        String areaId = area.getId();
        
        area.setDescricao("Area atualizada");
        
        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(areaId).url())
                .bodyJson(Json.toJson(area));
        Result putResult = route(putRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(putResult));
        Area areaRetornada = Json.fromJson(json, Area.class);
        
        assertEquals(200, putResult.status());
        assertEquals("Area atualizada", areaRetornada.getDescricao());
        assertNotNull(areaRetornada.getId());
    }
    
    @Test
    public void testAtualizarAreaNaoExistente() {
        Area area = new Area();
        area.setDescricao("CTA 1");
        
        Http.RequestBuilder putRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update("xxxx").url())
                .bodyJson(Json.toJson(area));
        Result putResult = route(putRequest);
        assertEquals(404, putResult.status());
    }

    @Test
    public void testApagarAreaExistente() {
        AreaCrudService areaService = app.injector().instanceOf(AreaCrudService.class);
        Area area = new Area();
        area.setDescricao("CTA 1");
        areaService.save(area);
        String areaId = area.getId();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AreasController.delete(areaId).url());
        Result result = route(deleteRequest);
           
        assertEquals(200, result.status());
        area = areaService.findOne(areaId);
        assertNull(area);
    }
    
    @Test
    public void testApagarAreaNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.AreasController.delete("1234").url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testListarAreas() {
        AreaCrudService areaService = app.injector().instanceOf(AreaCrudService.class);
        
        Area area = new Area();
        area.setDescricao("CTA 1");
        areaService.save(area);
        area.setDescricao("CTA 2");
        area.setId(null);
        areaService.save(area);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.AreasController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Area> areas = Json.fromJson(json, List.class);  
        
        assertEquals(200, result.status());
        assertEquals(2, areas.size());
    }
    
}
