package models;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.Logger;
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

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {

        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertEquals(6, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[2].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[3].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[1].aoMenosUmEstagioGrupoSemaforico")
        ));

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals(2, anelCom4Estagios.getGruposSemaforicos().size());

        Estagio estagio1 = (Estagio) anelCom4Estagios.getEstagios().toArray()[0];
        Estagio estagio2 = (Estagio) anelCom4Estagios.getEstagios().toArray()[1];
        Estagio estagio3 = (Estagio) anelCom4Estagios.getEstagios().toArray()[2];
        Estagio estagio4 = (Estagio) anelCom4Estagios.getEstagios().toArray()[3];

        GrupoSemaforico grupoSemaforico1 = anelCom4Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforico2 = anelCom4Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico2);

        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        erros = getErros(controlador);
        assertEquals(6, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro(CONTROLADOR, "Existem grupos semafóricos conflitantes associados a esse estágio.", "aneis[0].estagios[0].naoDevePossuirGruposSemaforicosConflitantes"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[2].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[3].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
                new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[1].aoMenosUmEstagioGrupoSemaforico")
        ));

        estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);
        EstagioGrupoSemaforico estagioGrupoSemaforico3 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico4 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);

        estagio1.setEstagiosGruposSemaforicos(null);
        estagio2.setEstagiosGruposSemaforicos(null);
        estagio3.setEstagiosGruposSemaforicos(null);
        estagio4.setEstagiosGruposSemaforicos(null);

        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        estagio3.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        estagio4.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);

        Estagio estagioNovo = anelCom2Estagios.getEstagios().get(0);
        Estagio estagioNovo2 = anelCom2Estagios.getEstagios().get(1);
        estagioNovo.setDemandaPrioritaria(true);

        GrupoSemaforico grupoSemaforicoNovo = anelCom2Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoNovo2 = anelCom2Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo = new EstagioGrupoSemaforico(estagioNovo, grupoSemaforicoNovo);
        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo2 = new EstagioGrupoSemaforico(estagioNovo2, grupoSemaforicoNovo2);
        estagioNovo.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo);
        estagioNovo2.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo2);

        erros = getErros(controlador);

        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 4, controlador.getGruposSemaforicos().size());

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 2, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControladorAnelAssociacao(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Criação de grupos semafóricos", 2, anelCom2Estagios.getGruposSemaforicos().size());
        assertEquals("Criação de grupos semafóricos", 2, anelCom4Estagios.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 2, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }

    private void assertControladorAnelAssociacao(Controlador controlador, Controlador controladorJson) {
        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Anel anelCom2EstagiosJson = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4EstagiosJson = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals(anelCom2Estagios.getDescricao(), anelCom2EstagiosJson.getDescricao());
        assertEquals(anelCom2Estagios.getEnderecos().get(0).getLatitude(), anelCom2EstagiosJson.getEnderecos().get(0).getLatitude());
        assertEquals(anelCom2Estagios.getEnderecos().get(0).getLongitude(), anelCom2EstagiosJson.getEnderecos().get(0).getLongitude());
        assertEquals(anelCom2Estagios.getEnderecos().get(1).getLatitude(), anelCom2EstagiosJson.getEnderecos().get(1).getLatitude());
        assertEquals(anelCom2Estagios.getEnderecos().get(1).getLongitude(), anelCom2EstagiosJson.getEnderecos().get(1).getLongitude());
        assertEquals(anelCom2Estagios.getGruposSemaforicos().size(), anelCom2EstagiosJson.getGruposSemaforicos().size());
        assertEquals(anelCom2Estagios.getDetectores().size(), anelCom2EstagiosJson.getDetectores().size());
        assertEquals(anelCom2Estagios.getNumeroSMEE(), anelCom2EstagiosJson.getNumeroSMEE());
        assertEquals(anelCom2Estagios.getEstagios().size(), anelCom2EstagiosJson.getEstagios().size());
        assertEquals(anelCom2Estagios.getEstagios().stream().filter(estagio -> estagio.getTempoMaximoPermanenciaAtivado()).count(),
                anelCom2EstagiosJson.getEstagios().stream().filter(estagio -> estagio.getTempoMaximoPermanenciaAtivado()).count());

        assertEquals(anelCom4Estagios.getDescricao(), anelCom4EstagiosJson.getDescricao());


        assertEquals(anelCom4Estagios.getEnderecos().get(0).getLatitude(), anelCom4EstagiosJson.getEnderecos().get(0).getLatitude());
        assertEquals(anelCom4Estagios.getEnderecos().get(0).getLongitude(), anelCom4EstagiosJson.getEnderecos().get(0).getLongitude());
        assertEquals(anelCom4Estagios.getEnderecos().get(1).getLatitude(), anelCom4EstagiosJson.getEnderecos().get(1).getLatitude());
        assertEquals(anelCom4Estagios.getEnderecos().get(1).getLongitude(), anelCom4EstagiosJson.getEnderecos().get(1).getLongitude());
        assertEquals(anelCom4Estagios.getGruposSemaforicos().size(), anelCom4EstagiosJson.getGruposSemaforicos().size());
        assertEquals(anelCom4Estagios.getDetectores().size(), anelCom4EstagiosJson.getDetectores().size());
        assertEquals(anelCom4Estagios.getNumeroSMEE(), anelCom4EstagiosJson.getNumeroSMEE());
        assertEquals(anelCom4Estagios.getEstagios().size(), anelCom4EstagiosJson.getEstagios().size());
        Estagio estagioDemanda = anelCom4EstagiosJson.getEstagios().stream().filter(anelAux -> anelAux.getDemandaPrioritaria() == Boolean.TRUE).findFirst().get();
        Estagio estagioSemDemanda = anelCom4EstagiosJson.getEstagios().stream().filter(anelAux -> anelAux.getDemandaPrioritaria() == Boolean.FALSE).findFirst().get();
        assertEquals(100, estagioDemanda.getTempoMaximoPermanencia().intValue());
        assertEquals(200, estagioSemDemanda.getTempoMaximoPermanencia().intValue());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
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
                .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Logger.info(json.toString());
        assertEquals(OK, postResult.status());

        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertControladorAnelAssociacao(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());

        Anel anelCom2Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Criação de grupos semafóricos", 2, anelCom4Estagios.getGruposSemaforicos().size());
        assertEquals("Criação de grupos semafóricos", 2, anelCom2Estagios.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 2, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

}
