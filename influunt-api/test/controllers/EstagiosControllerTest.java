package controllers;

import com.avaje.ebean.Ebean;
import com.google.inject.Singleton;
import models.Anel;
import models.Cidade;
import models.Estagio;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.libs.Json;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class EstagiosControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        Application app = new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
        return app;
    }


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

    @Test
    public void testSave() {
        Cidade c = new Cidade();

        Ebean.beginTransaction();
        try {
            c.setNome("opa");
            c.save();
            Logger.error("1 - :" + Cidade.find.findRowCount());
            assertNotNull(c.getId());
            Ebean.commitTransaction();
        }finally {
            Ebean.endTransaction();
        }



        Ebean.beginTransaction();
        try {
//        c = Json.fromJson(Json.toJson(c), Cidade.class);
            Logger.warn("ID: " + c.getId());
            Cidade c1 = Cidade.find.byId(c.getId());
            c1.setId(null);
            c1.save();
            Logger.error("2 - :" + Cidade.find.findRowCount());
            assertEquals(2, Cidade.find.findRowCount());
            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
    }

}
