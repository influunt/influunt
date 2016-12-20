package seguranca;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationAuthenticated;
import controllers.SecurityController;
import controllers.routes;
import models.*;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.Router;
import play.test.Helpers;

import java.util.*;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/30/16.
 */
public class AutorizacaoTest extends WithInfluuntApplicationAuthenticated {

    private List<Permissao> permissoes = new ArrayList<Permissao>();

    private Usuario usuarioComAcesso;

    private Optional<String> tokenComAcesso;

    private Optional<String> tokenSemAcesso;

    private Perfil perfilComAcesso;

    @Before
    public void setData() {
        List<Router.RouteDocumentation> myRoutes = app.getWrappedApplication().routes().asJava().documentation();
        for (Router.RouteDocumentation doc : myRoutes) {
            String chave = doc.getHttpMethod() + " " + doc.getPathPattern();
            Logger.debug(chave);
            Permissao p = new Permissao();
            p.setChave(chave);
            p.setDescricao(chave);
            p.save();
            permissoes.add(p);
        }

        perfilComAcesso = new Perfil();
        perfilComAcesso.setNome("Deus");
        perfilComAcesso.setPermissoes(permissoes);
        perfilComAcesso.save();

        Perfil perfilSemAcesso = new Perfil();
        perfilSemAcesso.setNome("Odin");
        perfilSemAcesso.setPermissoes(new ArrayList<Permissao>());
        perfilSemAcesso.save();

        usuarioComAcesso = new Usuario();
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
        tokenComAcesso = postResult.header(SecurityController.AUTH_TOKEN);

        postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.SecurityController.login().url()).bodyJson(jsonUsuarioSemAcesso);
        postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        tokenSemAcesso = postResult.header(SecurityController.AUTH_TOKEN);
    }

    private void addPermissaoVerAreas() {
        Permissao permissaoVerAreas = new Permissao();
        permissaoVerAreas.setChave("visualizarTodasAreas");
        permissaoVerAreas.setDescricao("ver todas as áreas");
        permissaoVerAreas.save();
        perfilComAcesso.addPermissao(permissaoVerAreas);
        perfilComAcesso.update();
    }

    @Test
    public void testAutorizacao() {
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.CidadesController.findOne(UUID.randomUUID().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(404, result.status());

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.CidadesController.findAll().url()).header(SecurityController.AUTH_TOKEN, tokenSemAcesso.get());
        result = route(request);
        assertEquals(403, result.status());
    }


    @Test
    public void testAutorizacaoControladorArea() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area1 = new Area();
        area1.setDescricao(1);
        area1.setCidade(cidade);
        area1.save();

        Subarea subarea1 = new Subarea();
        subarea1.setArea(area1);
        subarea1.setNome("Subarea 1");
        subarea1.setNumero(1);
        subarea1.save();

        Area area2 = new Area();
        area2.setDescricao(2);
        area2.setCidade(cidade);
        area2.save();

        Subarea subarea2 = new Subarea();
        subarea2.setArea(area2);
        subarea2.setNome("Subarea 2");
        subarea2.setNumero(2);
        subarea2.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        // area 1
        Controlador controlador1 = new ControladorTestUtil(area1, subarea1, fabricante, modeloControlador).getControladorPlanos();
        controlador1.save();
        // area 2
        Controlador controlador2 = new ControladorTestUtil(area2, subarea2, fabricante, modeloControlador).getControladorPlanos();
        controlador2.save();

        // area 1
        usuarioComAcesso.setArea(area1);
        usuarioComAcesso.update();


        testRequestsCrudControlador(controlador1, 200);
        testRequestsCrudControlador(controlador2, 403);

        testRequestsWizardControlador(controlador1, 422, area1.getId().toString());
        testRequestsWizardControlador(controlador2, 403, area2.getId().toString());


        // delete
        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.ControladoresController.delete(controlador2.getId().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(403, result.status());

        request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.ControladoresController.delete(controlador1.getId().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(200, result.status());
    }


    @Test
    public void testHelperControladorbyArea() {
        Cidade bh = new Cidade();
        bh.setNome("Belo Horizonte");
        bh.save();

        Area sul = new Area();
        sul.setDescricao(1);
        sul.setCidade(bh);
        sul.save();

        Area norte = new Area();
        norte.setDescricao(2);
        norte.setCidade(bh);
        norte.save();

        Cidade rio = new Cidade();
        rio.setNome("Rio de Janeiro");
        rio.save();

        Fabricante raro = new Fabricante();
        raro.setNome("Raro Labs");
        raro.save();

        ModeloControlador m1 = new ModeloControlador();
        m1.setFabricante(raro);
        m1.setDescricao("Raro Labs");
        m1.save();

        Fabricante labs = new Fabricante();
        labs.setNome("Labs");
        labs.save();


        usuarioComAcesso.setRoot(true);
        usuarioComAcesso.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.HelpersController.controladorHelper().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));

        assertEquals(2, json.get("cidades").size());
        assertEquals(2, json.get("cidades").get(0).get("areas").size());
        assertEquals(2, json.get("fabricantes").size());
        assertNotNull(json.get("fabricantes").get(0).get("modelos"));


        usuarioComAcesso.setRoot(false);
        usuarioComAcesso.setArea(sul);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.HelpersController.controladorHelper().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));

        assertEquals(1, json.get("cidades").size());
        assertEquals(1, json.get("cidades").get(0).get("areas").size());
        assertEquals(1, json.get("cidades").get(0).get("areas").get(0).get("descricao").asInt());
        assertEquals(2, json.get("fabricantes").size());
        assertNotNull(json.get("fabricantes").get(0).get("modelos"));

        addPermissaoVerAreas();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.HelpersController.controladorHelper().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));

        assertEquals(2, json.get("cidades").size());
        assertEquals(2, json.get("cidades").get(0).get("areas").size());
        assertEquals(2, json.get("fabricantes").size());
        assertNotNull(json.get("fabricantes").get(0).get("modelos"));
    }

    @Test
    public void testIndexControladoresByArea() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area1 = new Area();
        area1.setDescricao(1);
        area1.setCidade(cidade);
        area1.save();

        Subarea subarea1 = new Subarea();
        subarea1.setArea(area1);
        subarea1.setNome("Subarea 1");
        subarea1.setNumero(1);
        subarea1.save();

        Area area2 = new Area();
        area2.setDescricao(2);
        area2.setCidade(cidade);
        area2.save();

        Subarea subarea2 = new Subarea();
        subarea2.setArea(area2);
        subarea2.setNome("Subarea 2");
        subarea2.setNumero(2);
        subarea2.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        // area 1
        Controlador controlador1 = new ControladorTestUtil(area1, subarea1, fabricante, modeloControlador).getControladorPlanos();
        controlador1.save();
        // area 2
        Controlador controlador2 = new ControladorTestUtil(area2, subarea2, fabricante, modeloControlador).getControladorPlanos();
        controlador2.save();


        usuarioComAcesso.setRoot(true);
        usuarioComAcesso.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(200, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(2, json.get("data").size());

        usuarioComAcesso.setRoot(false);
        usuarioComAcesso.setArea(area1);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(200, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(1, json.get("data").size());

        addPermissaoVerAreas();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(200, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(2, json.get("data").size());
    }


    @Test
    public void testListarSubareasByArea() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area1 = new Area();
        area1.setDescricao(1);
        area1.setCidade(cidade);
        area1.save();

        Subarea subarea1 = new Subarea();
        subarea1.setArea(area1);
        subarea1.setNome("Subarea 1-1");
        subarea1.setNumero(1);
        subarea1.save();

        Subarea subarea2 = new Subarea();
        subarea2.setArea(area1);
        subarea2.setNome("Subarea 1-2");
        subarea2.setNumero(2);
        subarea2.save();

        Area area2 = new Area();
        area2.setDescricao(2);
        area2.setCidade(cidade);
        area2.save();

        Subarea subarea3 = new Subarea();
        subarea3.setArea(area2);
        subarea3.setNome("Subarea 2-1");
        subarea3.setNumero(1);
        subarea3.save();

        usuarioComAcesso.setRoot(true);
        usuarioComAcesso.setArea(null);
        usuarioComAcesso.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);

        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(3, json.get("total").asInt());
        assertEquals(3, json.get("data").size());

        usuarioComAcesso.setRoot(false);
        usuarioComAcesso.setArea(area1);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);

        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(2, json.get("total").asInt());
        assertEquals(2, json.get("data").size());

        usuarioComAcesso.setArea(area2);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.SubareasController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);

        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(1, json.get("total").asInt());
        assertEquals(1, json.get("data").size());
    }

    @Test
    public void testListarCidadesByArea() {
        Cidade sp = new Cidade();
        sp.setNome("São Paulo");
        sp.save();

        Area areaSP = new Area();
        areaSP.setDescricao(1);
        areaSP.setCidade(sp);
        areaSP.save();

        Cidade bh = new Cidade();
        bh.setNome("Belo Horizonte");
        bh.save();

        Area areaBH = new Area();
        areaBH.setDescricao(2);
        areaBH.setCidade(bh);
        areaBH.save();

        usuarioComAcesso.setRoot(true);
        usuarioComAcesso.setArea(null);
        usuarioComAcesso.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.CidadesController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);

        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        assertEquals(2, json.get("total").asInt());
        assertEquals(2, json.get("data").size());

        usuarioComAcesso.setRoot(false);
        usuarioComAcesso.setArea(areaSP);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.CidadesController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);

        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(1, json.get("total").asInt());
        assertEquals(1, json.get("data").size());
        assertEquals(sp.getNome(), json.get("data").get(0).get("nome").asText());

        usuarioComAcesso.setArea(areaBH);
        usuarioComAcesso.update();

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.CidadesController.findAll().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);

        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        assertEquals(1, json.get("total").asInt());
        assertEquals(1, json.get("data").size());
        assertEquals(bh.getNome(), json.get("data").get(0).get("nome").asText());
    }

    @Test
    public void testEditarProprioUsuario() {
        Iterator<Permissao> it = perfilComAcesso.getPermissoes().iterator();
        while (it.hasNext()) {
            Permissao p = it.next();
            if ("GET /api/v1/usuarios/$id<[^/]+>".equals(p.getChave()) || "PUT /api/v1/usuarios/$id<[^/]+>".equals(p.getChave())) {
                it.remove();
            }
        }
        perfilComAcesso.update();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.UsuariosController.findOne(usuarioComAcesso.getId().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(200, result.status());

        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.UsuariosController.update(usuarioComAcesso.getId().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.toJson(usuarioComAcesso));
        result = route(request);
        assertEquals(200, result.status());

        request = new Http.RequestBuilder().method("GET")
            .uri(routes.UsuariosController.findOne(UUID.randomUUID().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(403, result.status());

        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.UsuariosController.update(UUID.randomUUID().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.toJson(usuarioComAcesso));
        result = route(request);
        assertEquals(403, result.status());
    }

    private void testRequestsCrudControlador(Controlador controlador, int expectedResult) {
        // findOne
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.findOne(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        Result result = route(request);
        assertEquals(expectedResult, result.status());

        // edit
        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.ControladoresController.finalizar(controlador.getId().toString()).url()).bodyJson(Json.parse("{\"descricao\": \"Teste\"}")).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());


        controlador.setStatusVersao(StatusVersao.SINCRONIZADO);
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        if (expectedResult == 200) {
            controlador = Controlador.find.byId(UUID.fromString(Json.fromJson(Json.parse(Helpers.contentAsString(result)), Map.class).get("id").toString()));
        }

        // timeline
        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.timeline(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        // pode_editar
        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.podeEditar(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        // ativar
        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.ControladoresController.ativar(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        // editar_planos
        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        // editar_tabela_horaria
        request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        assertEquals(expectedResult, result.status());

        // atualizar_descricao
        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.ControladoresController.atualizarDescricao(controlador.getId().toString()).url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"descricao\": \"Teste descricao\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // cancelar_edicao
        request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.ControladoresController.cancelarEdicao(controlador.getId().toString()).url()).header(SecurityController.AUTH_TOKEN, tokenComAcesso.get());
        result = route(request);
        if (expectedResult == 200) {
            assertNotEquals(403, result.status());
        } else {
            assertEquals(expectedResult, result.status());
        }
    }

    private void testRequestsWizardControlador(Controlador controlador, int expectedResult, String requestAreaId) {
        // dados básicos
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.dadosBasicos().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"area\": {\"idJson\": \"1234\"}, \"areas\": [{\"idJson\": \"1234\", \"id\": \"" + requestAreaId + "\"}]}"));
        Result result = route(request);
        assertEquals(expectedResult, result.status());

        // aneis
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.aneis().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // grupos semafóricos
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.gruposSemaforicos().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // associação grupos semafóricos
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.associacaoGruposSemaforicos().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // verdes conflitantes
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.verdesConflitantes().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // transições proibidas
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.transicoesProibidas().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // atraso de grupo
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.atrasoDeGrupo().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // entreverdes
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.entreVerdes().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());

        // associação detectores
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.associacaoDetectores().url())
            .header(SecurityController.AUTH_TOKEN, tokenComAcesso.get())
            .bodyJson(Json.parse("{\"id\": \"" + controlador.getId().toString() + "\"}"));
        result = route(request);
        assertEquals(expectedResult, result.status());
    }
}
