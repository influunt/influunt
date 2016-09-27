package search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.WithInfluuntApplicationNoAuthentication;
import controllers.routes;
import models.*;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import security.Auditoria;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;
import static play.test.Helpers.*;


/**
 * Created by lesiopinheiro on 9/9/16.
 */
public class BuscasTest extends WithInfluuntApplicationNoAuthentication {

    private static final String PAGINACAO_DEFAULT = "Paginacao Default";

    private static final String DATA = "data";

    private static final String GET = "GET";


    @Before
    public void setUp() {
        PlayJongo jongo = provideApplication().injector().instanceOf(PlayJongo.class);
        jongo.getCollection("auditorias").drop();

        Auditoria.jongo = jongo;

        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);

        Usuario usuario = new Usuario();
        usuario.setNome("Admin");
        usuario.setLogin("admin");
        usuario.setSenha("1234");
        usuario.setRoot(true);
        usuario.setEmail("root@influunt.com.br");
        usuario.save();

        context.args.put("user", usuario);
        Http.Context.current.set(context);

        Auditoria.deleteAll();
    }

    @Test
    public void deveriaBuscarCidadesComNomeContendoTexto() {
        criarCidades(4);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?nome=Teste&dataCriacao_start=07%2F09%2F2016%2011:34:55&sort=nome&sort_type=desc"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get(DATA), List.class);
        assertEquals(4, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComNomeIgualTexto() {
        criarCidades(15);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url() + "?nome=Teste1");
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get(DATA), List.class);
        assertNotEquals("Nao deveria retornar a quantidade correta", 1, cidades.size());


        request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url() + "?nome_eq=Teste1");
        result = route(request);
        json = Json.parse(contentAsString(result));
        cidades = Json.fromJson(json.get(DATA), List.class);
        assertEquals(1, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComPaginacaoDefault() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, InfluuntQueryBuilder.PER_PAGE_DEFAULT, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesNaPaginaIgual2() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?page=1"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, 20, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComPaginacaoIgual20() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?per_page=20"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, 20, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesOrdenadasPeloNomeASC() throws IOException {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?per_page=15&sort=nome&sort_type=asc"));
        Result result = route(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Cidade>>() {
        });

        assertEquals("Paginacao 15 itens", 15, cidades.size());
        assertEquals("Teste0", cidades.get(0).getNome());
        assertEquals("Teste1", cidades.get(1).getNome());
        assertEquals("Teste10", cidades.get(2).getNome());
        assertEquals("Teste11", cidades.get(3).getNome());
        assertEquals("Teste12", cidades.get(4).getNome());
        assertEquals("Teste13", cidades.get(5).getNome());
        assertEquals("Teste14", cidades.get(6).getNome());
        assertEquals("Teste15", cidades.get(7).getNome());
        assertEquals("Teste16", cidades.get(8).getNome());
        assertEquals("Teste17", cidades.get(9).getNome());
        assertEquals("Teste18", cidades.get(10).getNome());
        assertEquals("Teste19", cidades.get(11).getNome());
        assertEquals("Teste2", cidades.get(12).getNome());
        assertEquals("Teste20", cidades.get(13).getNome());
        assertEquals("Teste21", cidades.get(14).getNome());
    }

    @Test
    public void deveriaBuscarCidadesOrdenadasPeloNomeDESC() throws IOException {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?per_page=15&sort=nome&sort_type=desc"));
        Result result = route(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Cidade>>() {
        });

        assertEquals("Paginacao 15 itens", 15, cidades.size());
        assertEquals("Teste9", cidades.get(0).getNome());
        assertEquals("Teste8", cidades.get(1).getNome());
        assertEquals("Teste7", cidades.get(2).getNome());
        assertEquals("Teste6", cidades.get(3).getNome());
        assertEquals("Teste5", cidades.get(4).getNome());
        assertEquals("Teste49", cidades.get(5).getNome());
        assertEquals("Teste48", cidades.get(6).getNome());
        assertEquals("Teste47", cidades.get(7).getNome());
        assertEquals("Teste46", cidades.get(8).getNome());
        assertEquals("Teste45", cidades.get(9).getNome());
        assertEquals("Teste44", cidades.get(10).getNome());
        assertEquals("Teste43", cidades.get(11).getNome());
        assertEquals("Teste42", cidades.get(12).getNome());
        assertEquals("Teste41", cidades.get(13).getNome());
        assertEquals("Teste40", cidades.get(14).getNome());
    }


    @Test
    public void deveriaBuscarCidadesComPaginacao100() throws IOException {
        criarCidades(76);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.CidadesController.findAll().url().concat("?per_page=100&sort_type=asc"));
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Cidade>>() {
        });

        assertEquals("Paginacao com 76 itens", 76, cidades.size());
    }


    @Test
    public void deveriaRealizarFetchs() throws IOException {
        criarFabricanteModeloControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.ModelosControladoresController.findAll().url());
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<ModeloControlador> modelos = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<ModeloControlador>>() {
        });

        assertEquals(1, modelos.size());
        assertEquals("Raro Labs", modelos.get(0).getFabricante().getNome());
    }

    @Test
    public void naoDeveriaRealizarFetchs() throws IOException {
        criarFabricanteModeloControlador();

        ObjectMapper mapper = new ObjectMapper();

        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(ModeloControlador.class, null).query());
        List<ModeloControlador> modelos = mapper.readValue(result.toJson().get(DATA).toString(), new TypeReference<List<ModeloControlador>>() {});

        assertEquals(1, modelos.size());
        assertNull(modelos.get(0).getFabricante().getNome());
    }


    @Test
    public void deveriaBuscarModeloPeloNomeDoFabricante() throws IOException {
        criarFabricanteModeloControlador();

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.ModelosControladoresController.findAll().url().concat("?fabricante.nome=Raro"));
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<ModeloControlador> modelos = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<ModeloControlador>>() {
        });

        assertEquals(1, modelos.size());
        assertEquals("Raro Labs", modelos.get(0).getFabricante().getNome());

    }

    @Test
    public void deveriaBuscarAreaPeloNomeDaCidade() throws IOException {
        criarCidadeArea("Belo Horizonte");
        criarCidadeArea("Belo Vale");
        criarCidadeArea("São Paulo");

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AreasController.findAll().url().concat("?cidade.nome=Belo"));
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(contentAsString(result));
        List<Area> areas = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Area>>() {
        });

        assertEquals(10, areas.size());

        request = new Http.RequestBuilder().method(GET)
                .uri(routes.AreasController.findAll().url().concat("?cidade.nome=Belo%20Horizonte"));
        result = route(request);
        json = Json.parse(contentAsString(result));
        areas = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Area>>() {
        });
        assertEquals(5, areas.size());
        for (Area area : areas) {
            assertEquals("Belo Horizonte", area.getCidade().getNome());
        }

        request = new Http.RequestBuilder().method(GET)
                .uri(routes.AreasController.findAll().url().concat("?cidade.nome=Belo%20Vale"));
        result = route(request);
        json = Json.parse(contentAsString(result));
        areas = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Area>>() {
        });
        assertEquals(5, areas.size());
        for (Area area : areas) {
            assertEquals("Belo Vale", area.getCidade().getNome());
        }

        request = new Http.RequestBuilder().method(GET)
                .uri(routes.AreasController.findAll().url().concat("?cidade.nome=São"));
        result = route(request);
        json = Json.parse(contentAsString(result));
        areas = mapper.readValue(json.get(DATA).toString(), new TypeReference<List<Area>>() {
        });
        assertEquals(5, areas.size());
        for (Area area : areas) {
            assertEquals("São Paulo", area.getCidade().getNome());
        }
    }

    @Test
    public void deveriaBuscarAuditoriasPeloLoginUsuario() {
        criarCidades(5);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AuditoriaController.findAll().url().concat("?usuario.login=admin"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json.get(DATA), List.class);
        assertEquals(OK, result.status());
        assertEquals(5, auditorias.size());
    }

    @Test
    public void naoDeveriaBuscarAuditoriasPeloLoginUsuarioExistente() {
        criarCidades(5);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AuditoriaController.findAll().url().concat("?usuario.login=teste"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json.get(DATA), List.class);
        assertEquals(OK, result.status());
        assertEquals(0, auditorias.size());
    }

    @Test
    public void deveriaBuscarAuditoriasPeloLoginUsuarioComPaginacaoDefault() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AuditoriaController.findAll().url().concat("?usuario.login=admin"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, InfluuntQueryBuilder.PER_PAGE_DEFAULT, auditorias.size());
    }

    @Test
    public void deveriaBuscarAuditoriasNaPaginaIgual2() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AuditoriaController.findAll().url().concat("?page=1"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, 20, auditorias.size());
    }

    @Test
    public void deveriaBuscarAuditoriasAPartirDe() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
                .uri(routes.AuditoriaController.findAll().url().concat("?timestamp_start=12%2F09%2F2016%2011:34:55"));
        Result result = route(request);
        JsonNode json = Json.parse(contentAsString(result));
        List<Auditoria> auditorias = Json.fromJson(json.get(DATA), List.class);
        assertEquals(PAGINACAO_DEFAULT, 30, auditorias.size());
    }

    private void criarFabricanteModeloControlador() {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Raro Labs");
        fabricante.save();

        ModeloControlador modelo = new ModeloControlador();
        modelo.setDescricao("Modelo Teste");
        modelo.setFabricante(fabricante);
        modelo.save();
    }

    private void criarCidades(int quantidade) {
        Cidade cidade;
        for (int i = 0; i < quantidade; i++) {
            cidade = new Cidade();
            cidade.setNome("Teste" + i);
            cidade.save();
        }
    }

    private void criarCidadeArea(String nomeCidade) {
        Cidade cidade = new Cidade();
        cidade.setNome(nomeCidade);
        cidade.save();

        Area area;
        for (int i = 1; i <= 5; i++) {
            area = new Area();
            area.setDescricao(i);
            area.setCidade(cidade);
            area.save();
        }
    }

}
