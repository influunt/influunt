package search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import controllers.routes;
import models.Cidade;
import models.Fabricante;
import models.ModeloControlador;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;
import utils.InfluuntQueryBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;


/**
 * Created by lesiopinheiro on 9/9/16.
 */
public class BuscasTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

    @Before
    public void setup() {
        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);
    }

    @Test
    public void deveriaBuscarCidadesComNomeContendoTexto() {
        criarCidades(4);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url() + "?nome=Teste&dataCriacao_start=07%2F09%2F2016%2011:34:55&sort=nome&sort_type=desc");
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get("data"), List.class);
        assertEquals(4, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComNomeIgualTexto() {
        criarCidades(15);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url() + "?nome=Teste1");
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get("data"), List.class);
        assertNotEquals("Nao deveria retornar a quantidade correta", 1, cidades.size());


        request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url() + "?nome_eq=Teste1");
        result = route(request);
        json = Json.parse(Helpers.contentAsString(result));
        cidades = Json.fromJson(json.get("data"), List.class);
        assertEquals(1, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComPaginacaoDefault() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url());
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get("data"), List.class);
        assertEquals("Paginacao Default", InfluuntQueryBuilder.PER_PAGE_DEFAULT, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesNaPaginaIgual2() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url().concat("?page=1"));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get("data"), List.class);
        assertEquals("Paginacao Default", 20, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesComPaginacaoIgual20() {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url().concat("?per_page=20"));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = Json.fromJson(json.get("data"), List.class);
        assertEquals("Paginacao Default", 20, cidades.size());
    }

    @Test
    public void deveriaBuscarCidadesOrdenadasPeloNomeASC() throws IOException {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url().concat("?per_page=15&sort=nome&sort_type=asc"));
        Result result = route(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get("data").toString(), new TypeReference<List<Cidade>>(){});

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
        assertEquals("Teste18",cidades.get(10).getNome());
        assertEquals("Teste19",cidades.get(11).getNome());
        assertEquals("Teste2",cidades.get(12).getNome());
        assertEquals("Teste20",cidades.get(13).getNome());
        assertEquals("Teste21",cidades.get(14).getNome());
    }

    @Test
    public void deveriaBuscarCidadesOrdenadasPeloNomeDESC() throws IOException {
        criarCidades(50);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url().concat("?per_page=15&sort=nome&sort_type=desc"));
        Result result = route(request);
        JsonNode json = Json.parse(Helpers.contentAsString(result));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get("data").toString(), new TypeReference<List<Cidade>>(){});

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
        assertEquals("Teste44",cidades.get(10).getNome());
        assertEquals("Teste43",cidades.get(11).getNome());
        assertEquals("Teste42",cidades.get(12).getNome());
        assertEquals("Teste41",cidades.get(13).getNome());
        assertEquals("Teste40",cidades.get(14).getNome());
    }


    @Test
    public void deveriaBuscarCidadesComPaginacao100() throws IOException {
        criarCidades(76);

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.CidadesController.findAll().url().concat("?per_page=100&sort_type=asc"));
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<Cidade> cidades = mapper.readValue(json.get("data").toString(), new TypeReference<List<Cidade>>(){});

        assertEquals("Paginacao com 76 itens", 76, cidades.size());
    }


    @Test
    public void deveriaRealizarFetchs() throws IOException {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Raro Labs");
        fabricante.save();

        ModeloControlador modelo = new ModeloControlador();
        modelo.setDescricao("Modelo Teste");
        modelo.setFabricante(fabricante);
        modelo.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ModelosControladoresController.findAll().url());
        Result result = route(request);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        List<ModeloControlador> modelos = mapper.readValue(json.get("data").toString(), new TypeReference<List<ModeloControlador>>() {
        });

        assertEquals(1, modelos.size());
        assertEquals("Raro Labs", modelos.get(0).getFabricante().getNome());

    }

    @Test
    public void naoDeveriaRealizarFetchs() throws IOException {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Raro Labs");
        fabricante.save();

        ModeloControlador modelo = new ModeloControlador();
        modelo.setDescricao("Modelo Teste");
        modelo.setFabricante(fabricante);
        modelo.save();


        ObjectMapper mapper = new ObjectMapper();
        List<ModeloControlador> modelos = mapper.readValue(new InfluuntQueryBuilder(ModeloControlador.class, null).query().get("data").toString(), new TypeReference<List<ModeloControlador>>(){});

        assertEquals(1, modelos.size());
        assertNull(modelos.get(0).getFabricante().getNome());

    }

    private void criarCidades(int quantidade) {
        for(int i = 0; i < quantidade; i++) {
            Cidade cidade = new Cidade();
            cidade.setNome("Teste" + i);
            cidade.save();
        }
    }

}
