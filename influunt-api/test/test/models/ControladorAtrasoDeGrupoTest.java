package test.models;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.api.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/6/16.
 */
public class ControladorAtrasoDeGrupoTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        assertEquals("Quantidade de transicoes com ganho de passagem", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Quantidade de transicoes com perda de passagem", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Quantidade de transicoes com ganho de passagem", 4, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Quantidade de transicoes com perda de passagem", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    assertNull(transicao.getAtrasoDeGrupo());
                }
            }
        }

        List<Erro> erros = getErros(controlador);
        assertEquals(36, erros.size());
        assertThat(erros, Matchers.hasItems(
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[0].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[0].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[1].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[1].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[2].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[2].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[3].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[3].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[4].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[4].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[5].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[5].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[0].transicoes[6].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[0].transicoes[6].tempoAtrasoDeGrupoDentroDaFaixa"),

            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[0].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[1].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[1].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[2].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[2].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[3].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[3].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[4].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[4].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[5].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[5].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[0].gruposSemaforicos[1].transicoes[6].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[0].gruposSemaforicos[1].transicoes[6].tempoAtrasoDeGrupoDentroDaFaixa"),

            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[1].gruposSemaforicos[0].transicoes[0].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[1].gruposSemaforicos[0].transicoes[0].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[1].gruposSemaforicos[0].transicoes[1].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[1].gruposSemaforicos[0].transicoes[1].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[1].gruposSemaforicos[1].transicoes[0].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[1].gruposSemaforicos[1].transicoes[0].tempoAtrasoDeGrupoDentroDaFaixa"),
            new Erro(CONTROLADOR, "Essa transição deve ter um atraso de grupo.", "aneis[1].gruposSemaforicos[1].transicoes[1].atrasoDeGrupoPresent"),
            new Erro(CONTROLADOR, "O tempo de atraso de grupo deve estar entre {min} e {max}.", "aneis[1].gruposSemaforicos[1].transicoes[1].tempoAtrasoDeGrupoDentroDaFaixa")
        ));
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();
        assertNotNull(controlador.getId());

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G1", 4, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 4 Estagios - G1", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G2", 3, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 4 Estagios - G2", 4, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    assertNotNull(transicao.getAtrasoDeGrupo());
                }
            }
        }
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G1", 4, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 4 Estagios - G1", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G2", 3, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 4 Estagios - G2", 4, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    assertNotNull(transicao.getAtrasoDeGrupo());
                }
            }
        }
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.atrasoDeGrupo().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(36, json.size());
    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorAtrasoDeGrupo();


        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.atrasoDeGrupo().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(json);


        assertEquals(OK, postResult.status());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        GrupoSemaforico g1 = anelCom4Estagios.findGrupoSemaforicoByDescricao("G1");
        GrupoSemaforico g2 = anelCom4Estagios.findGrupoSemaforicoByDescricao("G2");
        GrupoSemaforico g3 = anelCom2Estagios.findGrupoSemaforicoByDescricao("G3");
        GrupoSemaforico g4 = anelCom2Estagios.findGrupoSemaforicoByDescricao("G4");

        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G3", 1, g3.getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 2 Estagios - G4", 1, g4.getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G3", 1, g3.getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com perda de passagem Anel 2 Estagios - G4", 1, g4.getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G1", 4, g1.getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G2", 3, g2.getTransicoesComGanhoDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G1", 3, g1.getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes com ganho de passagem Anel 4 Estagios - G2", 4, g2.getTransicoesComPerdaDePassagem().size());

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    assertNotNull(transicao.getAtrasoDeGrupo());
                }
            }
        }
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAtrasoDeGrupoCheck.class);
    }

}
