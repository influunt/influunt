package seguranca;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationAuthenticated;
import controllers.SecurityController;
import controllers.routes;
import models.Area;
import models.Cidade;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import security.Auditoria;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class AuditoriaTest extends WithInfluuntApplicationAuthenticated {

    private Cidade cidade;

    private Optional<String> tokenComAcesso;

    @Before
    public void setUp() {

        PlayJongo jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        jongo.getCollection("auditorias").drop();

        Auditoria.jongo = jongo;

        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);

        cidade = new Cidade();
        cidade.setNome("BH");
        cidade.save();

        Usuario usuarioComAcesso = new Usuario();
        usuarioComAcesso.setNome("Admin");
        usuarioComAcesso.setLogin("admin");
        usuarioComAcesso.setSenha("1234");
        usuarioComAcesso.setRoot(true);
        usuarioComAcesso.setEmail("root@influunt.com.br");
        usuarioComAcesso.save();

        JsonNode jsonUsuarioComAcesso = Json.parse("{\"login\":\"admin\",\"senha\":\"1234\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url()).bodyJson(jsonUsuarioComAcesso);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        tokenComAcesso = postResult.header(SecurityController.AUTH_TOKEN);

        context = new Http.Context(postRequest);
        context.args.put("user", usuarioComAcesso);
        Http.Context.current.set(context);

        Auditoria.deleteAll();
    }

    @Test
    public void deveriaSalvarUmaAreaESalvarLog() {
        Area area = new Area();
        area.setDescricao(1);
        area.setCidade(cidade);
        area.save();

        UUID areaId = area.getId();
        assertNotNull(areaId);

        Area novaArea = new Area();
        novaArea.setCidade(cidade);
        novaArea.setDescricao(1);

        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
                .uri(routes.AreasController.update(areaId.toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
                .bodyJson(Json.toJson(novaArea));

        Result result = route(request);
        assertEquals(OK, result.status());

        // FIND BY LOGIN
        request = new Http.RequestBuilder().method(GET)
                .uri("/api/v1/auditorias/?login=admin").header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json, List.class);
        assertEquals(OK, result.status());
        assertEquals(2, auditorias.size());


        // FIND BY LOGIN AND TABLE WITHOUT DATA
        request = new Http.RequestBuilder().method(GET)
                .uri("/api/v1/auditorias/?login=admin&table=controladores").header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        json = Json.parse(Helpers.contentAsString(result));
        auditorias = Json.fromJson(json, List.class);
        assertEquals(OK, result.status());
        assertEquals(0, auditorias.size());

        // FIND BY LOGIN AND TABLE
        request = new Http.RequestBuilder().method(GET)
                .uri("/api/v1/auditorias/?login=admin&table=areas").header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        json = Json.parse(Helpers.contentAsString(result));
        auditorias = Json.fromJson(json, List.class);
        assertEquals(OK, result.status());
        assertEquals(2, auditorias.size());


        // FIND BY TABLE
        request = new Http.RequestBuilder().method(GET)
                .uri("/api/v1/auditorias/?table=areas").header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        json = Json.parse(Helpers.contentAsString(result));
        auditorias = Json.fromJson(json, List.class);
        assertEquals(OK, result.status());
        assertEquals(2, auditorias.size());

        // FIND BY TABLE WITHOUT DATA
        request = new Http.RequestBuilder().method(GET)
                .uri("/api/v1/auditorias/?table=controladores").header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        json = Json.parse(Helpers.contentAsString(result));
        auditorias = Json.fromJson(json, List.class);
        assertEquals(OK, result.status());
        assertEquals(0, auditorias.size());
    }

}
