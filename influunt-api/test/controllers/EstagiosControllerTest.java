package controllers;

import config.WithInfluuntApplicationNoAuthentication;
import models.Anel;
import models.Estagio;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

public class EstagiosControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void testApagarEstagioExistente() {
        Anel anel = new Anel();
        Estagio estagio = new Estagio();
        anel.addEstagio(estagio);
        anel.save();
        estagio.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.EstagiosController.delete(estagio.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Estagio.find.byId(estagio.getId()));
    }

    @Test
    public void testApagarEstagioNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.EstagiosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

}
