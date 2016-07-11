package models;

import checks.ControladorAneisCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 6/28/16.
 */
public class ControladorAssociacoesTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {

        Controlador controlador = getControladorAneis();
        controlador.save();

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);

        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
                new Erro("Controlador", "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
                new Erro("Controlador", "Quantidade de grupos semáforicos de pedestre diferente do definido no anel", "aneis[0].checkQuantidadeGruposSemaforicosDePedestre"),
                new Erro("Controlador", "Quantidade de grupos semáforicos veiculares diferente do definido no anel", "aneis[0].checkQuantidadeGruposSemaforicosVeiculares")
        ));

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals(2, anelAtivo.getGruposSemaforicos().size());

        Estagio estagio1 = (Estagio) anelAtivo.getEstagios().toArray()[0];
        Estagio estagio2 = (Estagio) anelAtivo.getEstagios().toArray()[1];
        Estagio estagio3 = (Estagio) anelAtivo.getEstagios().toArray()[2];
        Estagio estagio4 = (Estagio) anelAtivo.getEstagios().toArray()[3];

        GrupoSemaforico grupoSemaforico1 = anelAtivo.getGruposSemaforicos().get(0);
        grupoSemaforico1.setTipo(TipoGrupoSemaforico.PEDESTRE);
        GrupoSemaforico grupoSemaforico2 = anelAtivo.getGruposSemaforicos().get(1);
        grupoSemaforico2.setTipo(TipoGrupoSemaforico.VEICULAR);


        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);
        EstagioGrupoSemaforico estagioGrupoSemaforico3 = new EstagioGrupoSemaforico(estagio3, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico4 = new EstagioGrupoSemaforico(estagio4, grupoSemaforico2);

        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        estagio3.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        estagio4.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);

        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 2, controlador.getGruposSemaforicos().size());

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();

        Controlador controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControladorAnelAssociacao(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        Anel anelAtivo = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals("Criação de grupos semafóricos", 2, anelAtivo.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());

    }

    private void assertControladorAnelAssociacao(Controlador controlador, Controlador controladorJson) {
        Anel anel = controlador.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get();
        Anel anelJson = controladorJson.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get();

        assertEquals(anel.getDescricao(), anelJson.getDescricao());
        assertEquals(anel.getLatitude(), anelJson.getLatitude());
        assertEquals(anel.getLongitude(), anelJson.getLongitude());
        assertEquals(anel.getQuantidadeGrupoPedestre(), anelJson.getQuantidadeGrupoPedestre());
        assertEquals(anel.getQuantidadeGrupoVeicular(), anelJson.getQuantidadeGrupoVeicular());
        assertEquals(anel.getQuantidadeDetectorPedestre(), anelJson.getQuantidadeDetectorPedestre());
        assertEquals(anel.getQuantidadeDetectorVeicular(), anelJson.getQuantidadeDetectorVeicular());
        assertEquals(anel.getNumeroSMEE(), anelJson.getNumeroSMEE());
        assertEquals(anel.getEstagios().size(), anelJson.getEstagios().size());
        Estagio estagioDemanda = anel.getEstagios().stream().filter(anelAux -> anelAux.getDemandaPrioritaria() == Boolean.TRUE).findFirst().get();
        Estagio estagioSemDemanda = anel.getEstagios().stream().filter(anelAux -> anelAux.getDemandaPrioritaria() == Boolean.FALSE).findFirst().get();
        assertEquals(100, estagioDemanda.getTempoMaximoPermanencia().intValue());
        assertEquals(200, estagioSemDemanda.getTempoMaximoPermanencia().intValue());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorAneis();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(6, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorAssociacao();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));

        assertEquals(OK, postResult.status());

//        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json, Controlador.class);

        assertControladorAnelAssociacao(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Total de aneis ativos", 1, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 2, controladorRetornado.getAneis().stream().filter(anelInterno -> anelInterno.isAtivo()).findFirst().get().getGruposSemaforicos().size());
        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelAtivo.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }
}
