package controllers;

import com.avaje.ebean.Ebean;
import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

public class PlanosControllerTest extends WithInfluuntApplicationNoAuthentication {

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

        Estagio estagio = new Estagio();
        estagio.save();

        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setEstagio(estagio);
        estagio.addEstagioPlano(estagioPlano);
        estagioPlano.setPlano(plano);
        plano.addEstagios(estagioPlano);

        GrupoSemaforico grupo = new GrupoSemaforico();
        GrupoSemaforicoPlano gsPlano = new GrupoSemaforicoPlano();
        gsPlano.setGrupoSemaforico(grupo);
        grupo.addGRupoSemaforicoPlano(gsPlano);
        gsPlano.setPlano(plano);
        plano.addGruposSemaforicos(gsPlano);
        grupo.save();
        plano.save();
        gsPlano.save();

        assertNotNull(plano.getId());

        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
                .uri(routes.PlanosController.delete(plano.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertNull(Plano.find.byId(plano.getId()));
        assertNull(Ebean.find(EstagioPlano.class).where().eq("id", estagioPlano.getId()).findUnique());
        assertNull(Ebean.find(GrupoSemaforicoPlano.class).where().eq("id", gsPlano.getId()).findUnique());
        assertNotNull(Estagio.find.byId(estagio.getId()));
        assertNotNull(VersaoPlano.find.byId(versao.getId()));
        assertNotNull(GrupoSemaforico.find.byId(grupo.getId()));
    }

    @Test
    public void testApagarPlanoNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.PlanosController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }
}
