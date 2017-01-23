package test.controllers;

import models.Anel;
import models.Detector;
import models.Estagio;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import test.config.WithInfluuntApplicationNoAuthentication;

import java.util.UUID;

import static org.junit.Assert.*;
import static play.test.Helpers.route;

public class DetectoresControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void testApagarDetectorExistente() {
        Detector detector = new Detector();

        Anel anel = new Anel();
        detector.setAnel(anel);
        anel.addDetectores(detector);

        Estagio estagio = new Estagio();
        detector.setEstagio(estagio);
        estagio.setDetector(detector);

        anel.save();
        estagio.save();
        detector.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.DetectoresController.delete(detector.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Detector.find.byId(detector.getId()));
        assertNotNull("Anel não deve ser deletado", Anel.find.byId(anel.getId()));
        assertNotNull("Estágio não deve ser deletado", Estagio.find.byId(estagio.getId()));
    }

    @Test
    public void testApagarDetectorNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.DetectoresController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

}
