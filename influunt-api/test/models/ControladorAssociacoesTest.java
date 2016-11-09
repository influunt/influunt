package models;

import checks.*;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
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

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {

        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertEquals(6, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[3].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[2].aoMenosUmEstagioGrupoSemaforico")
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
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        erros = getErros(controlador);
        assertEquals(6, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[2].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Existem grupos semafóricos conflitantes associados a esse estágio.", "aneis[0].estagios[0].naoDevePossuirGruposSemaforicosConflitantes"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[3].aoMenosUmEstagioGrupoSemaforico")
        ));

        estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);
        EstagioGrupoSemaforico estagioGrupoSemaforico3 = new EstagioGrupoSemaforico(estagio3, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico4 = new EstagioGrupoSemaforico(estagio4, grupoSemaforico2);

        estagio1.setEstagiosGruposSemaforicos(null);
        estagio2.setEstagiosGruposSemaforicos(null);
        estagio3.setEstagiosGruposSemaforicos(null);
        estagio4.setEstagiosGruposSemaforicos(null);

        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        estagio3.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        estagio4.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);

        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);


        Estagio estagioNovo = anelCom2Estagios.getEstagios().get(0);
        Estagio estagioNovo2 = anelCom2Estagios.getEstagios().get(1);
        estagioNovo.setDemandaPrioritaria(true);

        GrupoSemaforico grupoSemaforicoNovo = anelCom2Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoNovo2 = anelCom2Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo = new EstagioGrupoSemaforico(estagioNovo, grupoSemaforicoNovo);
        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo2 = new EstagioGrupoSemaforico(estagioNovo2, grupoSemaforicoNovo2);
        estagioNovo.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo);
        estagioNovo2.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo2);

        grupoSemaforicoNovo.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo);
        grupoSemaforicoNovo2.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo2);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo de verde do estágio de demanda priortária deve estar entre {min} e {max}", "aneis[1].estagios[0].tempoVerdeDemandaPrioritaria")
        ));

        estagioNovo.setTempoVerdeDemandaPrioritaria(10);

        estagio1.setDemandaPrioritaria(true);
        estagio2.setDemandaPrioritaria(true);

        erros = getErros(controlador);
        assertEquals(6, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "O anel ativo deve ter somente um estágio de demanda prioritária.", "aneis[0].somenteUmEstagioDeDemandaPrioritaria"),
            new Erro(CONTROLADOR, "Esse grupo semafórico não pode estar associado a um estágio de demanda prioritária e a outro estágio ao mesmo tempo.", "aneis[0].gruposSemaforicos[0].naoEstaAssociadoAEstagioDemandaPrioritariaEOutroEstagio"),
            new Erro(CONTROLADOR, "Esse grupo semafórico não pode estar associado a um estágio de demanda prioritária e a outro estágio ao mesmo tempo.", "aneis[0].gruposSemaforicos[1].naoEstaAssociadoAEstagioDemandaPrioritariaEOutroEstagio"),
            new Erro(CONTROLADOR, "O Tempo de verde do estágio de demanda priortária deve estar entre {min} e {max}", "aneis[0].estagios[0].tempoVerdeDemandaPrioritaria"),
            new Erro(CONTROLADOR, "O Tempo de verde do estágio de demanda priortária deve estar entre {min} e {max}", "aneis[0].estagios[1].tempoVerdeDemandaPrioritaria"),
            new Erro(CONTROLADOR, "Estágio de demanda prioritária deve ser associado a um grupo semafórico veicular.", "aneis[0].estagios[0].umGrupoSemaforicoVeicularEmDemandaPrioritaria")
        ));

        estagio1.setDemandaPrioritaria(false);
        estagio2.setTempoVerdeDemandaPrioritaria(1);

        GrupoSemaforico grupoSemaforico5 = new GrupoSemaforico();
        grupoSemaforico5.setAnel(anelCom4Estagios);
        grupoSemaforico5.setControlador(controlador);
        grupoSemaforico5.setTipo(TipoGrupoSemaforico.VEICULAR);
        grupoSemaforico5.setTempoVerdeSeguranca(10);
        grupoSemaforico5.setDescricao("G3");
        grupoSemaforico5.setPosicao(3);
        grupoSemaforico5.setFaseVermelhaApagadaAmareloIntermitente(true);
        anelCom4Estagios.addGruposSemaforicos(grupoSemaforico5);
        grupoSemaforico5.setAnel(anelCom4Estagios);
        controlador.addGruposSemaforicos(grupoSemaforico5);

        grupoSemaforico5.addVerdeConflitante(grupoSemaforico1);

        EstagioGrupoSemaforico estagioGrupoSemaforico5 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico5);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico5);
        grupoSemaforico5.addEstagioGrupoSemaforico(estagioGrupoSemaforico5);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Estágio de demanda prioritária deve ser associado a somente 1 grupo semafórico.", "aneis[0].estagios[1].somenteUmEstagioGrupoSemaforicoEmDemandaPrioritaria"),
            new Erro(CONTROLADOR, "Esse grupo semafórico não pode estar associado a um estágio de demanda prioritária e a outro estágio ao mesmo tempo.", "aneis[0].gruposSemaforicos[1].naoEstaAssociadoAEstagioDemandaPrioritariaEOutroEstagio")
        ));

        estagio2.setEstagiosGruposSemaforicos(null);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        grupoSemaforico2.setEstagiosGruposSemaforicos(null);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        grupoSemaforico2.setTipo(TipoGrupoSemaforico.PEDESTRE);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Estágio de demanda prioritária deve ser associado a um grupo semafórico veicular.", "aneis[0].estagios[1].umGrupoSemaforicoVeicularEmDemandaPrioritaria")
        ));

        grupoSemaforico2.setTipo(TipoGrupoSemaforico.VEICULAR);

        estagioGrupoSemaforico4.setGrupoSemaforico(grupoSemaforico5);

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
        assertEquals(anelCom2Estagios.getEndereco().getLatitude(), anelCom2EstagiosJson.getEndereco().getLatitude());
        assertEquals(anelCom2Estagios.getEndereco().getLongitude(), anelCom2EstagiosJson.getEndereco().getLongitude());
        assertEquals(anelCom2Estagios.getGruposSemaforicos().size(), anelCom2EstagiosJson.getGruposSemaforicos().size());
        assertEquals(anelCom2Estagios.getDetectores().size(), anelCom2EstagiosJson.getDetectores().size());
        assertEquals(anelCom2Estagios.getNumeroSMEE(), anelCom2EstagiosJson.getNumeroSMEE());
        assertEquals(anelCom2Estagios.getEstagios().size(), anelCom2EstagiosJson.getEstagios().size());
        assertEquals(anelCom2Estagios.getEstagios().stream().filter(estagio -> estagio.getTempoMaximoPermanenciaAtivado()).count(),
            anelCom2EstagiosJson.getEstagios().stream().filter(estagio -> estagio.getTempoMaximoPermanenciaAtivado()).count());

        assertEquals(anelCom4Estagios.getDescricao(), anelCom4EstagiosJson.getDescricao());


        assertEquals(anelCom4Estagios.getEndereco().getLatitude(), anelCom4EstagiosJson.getEndereco().getLatitude());
        assertEquals(anelCom4Estagios.getEndereco().getLongitude(), anelCom4EstagiosJson.getEndereco().getLongitude());
        assertEquals(anelCom4Estagios.getGruposSemaforicos().size(), anelCom4EstagiosJson.getGruposSemaforicos().size());
        assertEquals(anelCom4Estagios.getDetectores().size(), anelCom4EstagiosJson.getDetectores().size());
        assertEquals(anelCom4Estagios.getNumeroSMEE(), anelCom4EstagiosJson.getNumeroSMEE());
        assertEquals(anelCom4Estagios.getEstagios().size(), anelCom4EstagiosJson.getEstagios().size());
        Estagio estagio1 = anelCom4EstagiosJson.getEstagios().stream().filter(anelAux -> anelAux.getDescricao().equals("estagio1")).findFirst().get();
        Estagio estagio2 = anelCom4EstagiosJson.getEstagios().stream().filter(anelAux -> anelAux.getDescricao().equals("estagio2")).findFirst().get();
        assertEquals(100, estagio1.getTempoMaximoPermanencia().intValue());
        assertEquals(200, estagio2.getTempoMaximoPermanencia().intValue());
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

    @Test
    public void testControllerAlterar() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1 = anelCom4Estagios.getEstagios().get(0);
        Estagio estagio2 = anelCom4Estagios.getEstagios().get(1);
        Estagio estagio3 = anelCom4Estagios.getEstagios().get(2);
        Estagio estagio4 = anelCom4Estagios.getEstagios().get(3);

        GrupoSemaforico grupoSemaforico1 = anelCom4Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforico2 = anelCom4Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforico11 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio1.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico22 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio2.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico31 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio3.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico42 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio4.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();

        assertNotNull("Associação Estágio1 x Grupo Semafórico1", estagioGrupoSemaforico11);
        assertNotNull("Associação Estágio2 x Grupo Semafórico2", estagioGrupoSemaforico22);
        assertNotNull("Associação Estágio3 x Grupo Semafórico1", estagioGrupoSemaforico31);
        assertNotNull("Associação Estágio4 x Grupo Semafórico2", estagioGrupoSemaforico42);

        grupoSemaforico1.setEstagiosGruposSemaforicos(null);
        grupoSemaforico2.setEstagiosGruposSemaforicos(null);

        estagioGrupoSemaforico11.setGrupoSemaforico(grupoSemaforico2); // 11 -> 12
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico11);

        estagioGrupoSemaforico22.setGrupoSemaforico(grupoSemaforico1); // 22 -> 21
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico22);

        estagioGrupoSemaforico31.setGrupoSemaforico(grupoSemaforico2); // 31 -> 32
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico31);

        estagioGrupoSemaforico42.setGrupoSemaforico(grupoSemaforico1); // 42 -> 41
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico42);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.associacaoGruposSemaforicos().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertControladorAnelAssociacao(controladorRetornado, controlador);


        anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        estagio1 = anelCom4Estagios.getEstagios().get(0);
        estagio2 = anelCom4Estagios.getEstagios().get(1);
        estagio3 = anelCom4Estagios.getEstagios().get(2);
        estagio4 = anelCom4Estagios.getEstagios().get(3);


        estagioGrupoSemaforico11 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio1.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();
        estagioGrupoSemaforico22 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio2.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();
        estagioGrupoSemaforico31 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio3.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();
        estagioGrupoSemaforico42 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio4.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();

        assertNull("Associação Estágio1 x Grupo Semafórico1", estagioGrupoSemaforico11);
        assertNull("Associação Estágio2 x Grupo Semafórico2", estagioGrupoSemaforico22);
        assertNull("Associação Estágio3 x Grupo Semafórico1", estagioGrupoSemaforico31);
        assertNull("Associação Estágio4 x Grupo Semafórico2", estagioGrupoSemaforico42);

        EstagioGrupoSemaforico estagioGrupoSemaforico12 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio1.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico21 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio2.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico32 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio3.getId()).eq("grupo_semaforico_id", grupoSemaforico2.getId()).findUnique();
        EstagioGrupoSemaforico estagioGrupoSemaforico41 = Ebean.find(EstagioGrupoSemaforico.class).where().eq("estagio_id", estagio4.getId()).eq("grupo_semaforico_id", grupoSemaforico1.getId()).findUnique();

        assertNotNull("Associação Estágio1 x Grupo Semafórico2", estagioGrupoSemaforico12);
        assertNotNull("Associação Estágio2 x Grupo Semafórico1", estagioGrupoSemaforico21);
        assertNotNull("Associação Estágio3 x Grupo Semafórico2", estagioGrupoSemaforico32);
        assertNotNull("Associação Estágio4 x Grupo Semafórico1", estagioGrupoSemaforico41);
    }

    @Test
    public void validaTempoMaximoPermanencia() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        FaixasDeValores faixaDeValores = FaixasDeValores.getInstance();
        faixaDeValores.setTempoMaximoPermanenciaEstagioMin(1);
        faixaDeValores.save();

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals(2, anelCom4Estagios.getGruposSemaforicos().size());

        Estagio estagio1 = (Estagio) anelCom4Estagios.getEstagios().toArray()[0];
        estagio1.setTempoMaximoPermanencia(5);

        GrupoSemaforico grupoSemaforico1 = anelCom4Estagios.getGruposSemaforicos().get(0);
        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);

        List<Erro> erros = getErros(controlador);

        assertEquals(7, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[3].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[1].estagios[0].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[1].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Este estágio deve ser associado a pelo menos 1 grupo semafórico", "aneis[0].estagios[2].aoMenosUmEstagioGrupoSemaforico"),
            new Erro(CONTROLADOR, "Esse grupo semafórico deve estar associado a pelo menos um estágio", "aneis[0].gruposSemaforicos[1].associadoAoMenosAUmEstágio"),
            new Erro(CONTROLADOR, "Tempo máximo de permanência deve ser maior que o verde de segurança dos grupos semafóricos associados ao estágio.", "aneis[0].estagios[0].tempoMaximoPermanenciaMaiorQueVerdeDeSeguranca")
        ));

    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

}
