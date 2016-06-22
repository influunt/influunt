package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Detector;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;


public class DetectoresControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .in(Mode.TEST).build();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testListarDetectores() {
        Detector detector = new Detector();
        detector.save();

        Detector detector1 = new Detector();
        detector1.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.DetectoresController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Detector> detectores = Json.fromJson(json, List.class);

        assertEquals(200, result.status());
        assertEquals(2, detectores.size());
    }



    @Test
    public void testCriarNovoDetector() {
        Detector detector = new Detector();
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.DetectoresController.create().url()).bodyJson(Json.toJson(detector));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Detector detectorRetornado = Json.fromJson(json, Detector.class);

        assertEquals(200, result.status());
        assertNotNull(detectorRetornado.getId());
    }

    @Test
    public void testAtualizarDetectorExistente() {
        Detector detector = new Detector();
        detector.save();

        UUID fabricanteId = detector.getId();
        assertNotNull(fabricanteId);

        Detector detector1 = new Detector();

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.DetectoresController.update(fabricanteId.toString()).url())
                .bodyJson(Json.toJson(detector1));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Detector detectorRetornado = Json.fromJson(json, Detector.class);

        assertEquals(200, result.status());
        assertNotNull(detectorRetornado.getId());
    }

    @Test
    public void testAtualizarFabricanteNaoExistente() {
        Detector fabricante = new Detector();

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.FabricantesController.update(UUID.randomUUID().toString()).url())
                .bodyJson(Json.toJson(fabricante));
        Result result = route(request);
        assertEquals(404, result.status());
    }

    @Test
    public void testBuscarDadosFabricante() {
        Detector detector = new Detector();
        detector.save();
        UUID detectorId = detector.getId();
        assertNotNull(detectorId);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.DetectoresController.findOne(detectorId.toString()).url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Detector detectorRetornado = Json.fromJson(json, Detector.class);

        assertEquals(200, result.status());
        assertEquals(detectorId, detectorRetornado.getId());
    }

    @Test
    public void testApagarFabricanteExistente() {
        Detector fabricante = new Detector();
        fabricante.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.DetectoresController.delete(fabricante.getId().toString()).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        assertNull(Detector.find.byId(fabricante.getId()));
    }

    @Test
    public void testApagarFabricanteNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.DetectoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

}
