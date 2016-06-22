package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import models.Usuario;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNAUTHORIZED;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class SecurityControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return getApplication(inMemoryDatabase());
    }

    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .in(Mode.TEST).build();
    }

    @Test
    public void testUnauthorizedLogin() throws InterruptedException, ExecutionException {
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url());
        Result postResult = route(postRequest);
        assertEquals(UNAUTHORIZED, postResult.status());

    }

    @Test
    public void testAuthorizedLogin() throws InterruptedException, ExecutionException {
        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .header("Authorization", "Basic YWRtaW46MTIzNA==").uri(routes.SecurityController.login().url());
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        Usuario usuario = Json.fromJson(json, Usuario.class);
        assertEquals("admin", usuario.getLogin());
        assertEquals("Administrator", usuario.getNome());

    }

}
