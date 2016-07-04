package seguranca;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.SecurityController;
import controllers.routes;
import models.Perfil;
import models.Permissao;
import models.Usuario;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.Router;
import play.test.WithApplication;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/30/16.
 */
public class AutorizacaoTest extends WithApplication {

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
    public void testAutorizacao() {

        List<Permissao> permissoes = new ArrayList<Permissao>();


        List<Router.RouteDocumentation> myRoutes = app.getWrappedApplication().routes().asJava().documentation();
        for(Router.RouteDocumentation doc : myRoutes){
            String chave = doc.getHttpMethod() + " " + doc.getPathPattern();
            Logger.debug(chave);
            Permissao p = new Permissao();
            p.setChave(chave);
            p.setDescricao(chave);
            p.save();
            permissoes.add(p);
        }

        Perfil perfilComAcesso = new Perfil();
        perfilComAcesso.setNome("Deus");
        perfilComAcesso.setPermissoes(permissoes);
        perfilComAcesso.save();

        Perfil perfilSemAcesso = new Perfil();
        perfilSemAcesso.setNome("Odin");
        perfilSemAcesso.setPermissoes(new ArrayList<Permissao>());
        perfilSemAcesso.save();

        Usuario usuarioComAcesso = new Usuario();
        usuarioComAcesso.setNome("Admin");
        usuarioComAcesso.setLogin("admin");
        usuarioComAcesso.setSenha("1234");
        usuarioComAcesso.setRoot(false);
        usuarioComAcesso.setEmail("root@influunt.com.br");
        usuarioComAcesso.setPerfil(perfilComAcesso);
        usuarioComAcesso.save();

        Usuario usuarioSemAcesso = new Usuario();
        usuarioSemAcesso.setNome("Admin");
        usuarioSemAcesso.setLogin("admin1");
        usuarioSemAcesso.setSenha("1234");
        usuarioSemAcesso.setRoot(false);
        usuarioSemAcesso.setEmail("root@influunt.com.br");
        usuarioSemAcesso.setPerfil(perfilSemAcesso);
        usuarioSemAcesso.save();

        JsonNode jsonUsuarioComAcesso = Json.parse("{\"login\":\"admin\",\"senha\":\"1234\"}");
        JsonNode jsonUsuarioSemAcesso = Json.parse("{\"login\":\"admin1\",\"senha\":\"1234\"}");

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url()).bodyJson(jsonUsuarioComAcesso);
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        Optional<String> tokenComAcesso = postResult.header(SecurityController.AUTH_TOKEN);

        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.SecurityController.login().url()).bodyJson(jsonUsuarioSemAcesso);
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        Optional<String> tokenSemAcesso = postResult.header(SecurityController.AUTH_TOKEN);


        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findOne(UUID.randomUUID().toString()).url()).header(SecurityController.AUTH_TOKEN,tokenComAcesso.get());
        Result result = route(request);
        assertEquals(404, result.status());

        request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url()).header(SecurityController.AUTH_TOKEN,tokenSemAcesso.get());
        result = route(request);
        assertEquals(403, result.status());


    }





}
