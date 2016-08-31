package controllers;

import com.avaje.ebean.Ebean;
import com.google.inject.Singleton;
import models.*;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class PlanosControllerTest extends WithApplication {

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
    public void testApagarPlanoExistente() {
        VersaoPlano versao = new VersaoPlano();
        versao.save();

        Plano plano = new Plano();
        plano.setPosicao(1);
        plano.setDescricao("Plano 1");
        plano.setVersaoPlano(versao);
        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano.setPosicaoTabelaEntreVerde(1);
        plano.save();

        Estagio estagio = new Estagio();
        estagio.save();

        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setEstagio(estagio);
        estagio.addEstagioPlano(estagioPlano);
        estagioPlano.setPlano(plano);
        plano.addEstagios(estagioPlano);

        assertNotNull(plano.getId());

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.PlanosController.delete(plano.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Plano.find.byId(plano.getId()));
        assertNull(Ebean.find(EstagioPlano.class).where().eq("id", estagioPlano.getId()).findUnique());
        assertNotNull(Estagio.find.byId(estagio.getId()));
        assertNotNull(VersaoPlano.find.byId(versao.getId()));
    }

    @Test
    public void testApagarPlanoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.PlanosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }

}
