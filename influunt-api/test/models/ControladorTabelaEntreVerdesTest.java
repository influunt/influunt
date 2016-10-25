package models;

import checks.*;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/6/16.
 */
public class ControladorTabelaEntreVerdesTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        assertEquals("Quantidade de tabela entre-verdes", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTabelasEntreVerdes().size());
        assertEquals("Quantidade de tabela entre-verdes", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTabelasEntreVerdes().size());
        assertEquals("Quantidade de tabela entre-verdes", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTabelasEntreVerdes().size());
        assertEquals("Quantidade de tabela entre-verdes", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTabelasEntreVerdes().size());
        assertEquals("Quantidade de transicoes G1", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Quantidade de transicoes G2", 4, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());
        assertEquals("Quantidade de transicoes G3", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Quantidade de transicoes G4", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());


        Transicao transicao1Anel2EstagiosGS1 = anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(0);
        TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = transicao1Anel2EstagiosGS1.getTabelaEntreVerdesTransicoes().get(0);

        List<Erro> erros = getErros(controlador);
        assertEquals(12, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[3].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[1].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo")
        ));

        tabelaEntreVerdesTransicao.setTempoAmarelo(500);
        tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(500); // TEMPO PARA GRUPO SEMAFORICO VEICULAR

        erros = getErros(controlador);

        assertEquals(13, erros.size());
        Collections.sort(erros, (Erro e1, Erro e2) -> e1.path.compareTo(e2.path));
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[3].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmareloOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoLimpezaFieldVeicular"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[1].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo")
        ));

        GrupoSemaforico grupoSemaforicoAnel4EstagiosPedestre = anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).findFirst().get();
        Transicao transicao1Anel4EstagiosGS1 = grupoSemaforicoAnel4EstagiosPedestre.getTransicoesComPerdaDePassagem().get(0);
        tabelaEntreVerdesTransicao = transicao1Anel4EstagiosGS1.getTabelaEntreVerdesTransicoes().get(0);
        tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(500);
        tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(6); // TEMPO PARA GRUPO SEMAFORICO PEDESTRE

        erros = getErros(controlador);

        assertEquals(13, erros.size());
        Collections.sort(erros, (Erro e1, Erro e2) -> e1.path.compareTo(e2.path));
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoLimpezaFieldPedestre"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[3].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmareloOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoLimpezaFieldVeicular"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[1].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo")
        ));

        for (int i = 0; i < 2; i++) {
            TabelaEntreVerdes tabelaEntreVerdes = new TabelaEntreVerdes(grupoSemaforicoAnel4EstagiosPedestre, i + 2);
            grupoSemaforicoAnel4EstagiosPedestre.addTabelaEntreVerdes(tabelaEntreVerdes);
        }

        erros = getErros(controlador);

        assertEquals(14, erros.size());
        Collections.sort(erros, (Erro e1, Erro e2) -> e1.path.compareTo(e2.path));
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "Esse grupo semafórico deve ter no máximo o número de tabelas entre-verdes definido na configuração do controlador.", "aneis[0].gruposSemaforicos[0].numeroCorretoTabelasEntreVerdes"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoLimpezaFieldPedestre"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitente"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[0].gruposSemaforicos[0].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoVermelhoIntermitenteOk"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[1].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[2].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[0].gruposSemaforicos[1].transicoes[3].tabelaEntreVerdesTransicoes[0].tempoAmarelo"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmareloOk"),
                new Erro(CONTROLADOR, "deve estar entre {min} e {max}", "aneis[1].gruposSemaforicos[0].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoVermelhoLimpezaFieldVeicular"),
                new Erro(CONTROLADOR, "não pode ficar em branco", "aneis[1].gruposSemaforicos[1].transicoes[0].tabelaEntreVerdesTransicoes[0].tempoAmarelo")
        ));
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();
        assertNotNull(controlador.getId());

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de transicoes Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes Anel 4 Estagios - G1", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 4 Estagios - G2", 4, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(3).getTabelaEntreVerdesTransicoes().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Total de transicoes Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes Anel 4 Estagios - G1", 3, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 4 Estagios - G2", 4, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().size());

        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G1", 1, anelCom2Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G2", 1, anelCom2Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, anelCom4Estagios.getGruposSemaforicos().get(0).getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, anelCom4Estagios.getGruposSemaforicos().get(1).getTransicoesComPerdaDePassagem().get(3).getTabelaEntreVerdesTransicoes().size());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.entreVerdes().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(12, json.size());
    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.entreVerdes().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
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

        assertEquals("Total de transicoes Anel 2 Estagios - G3", 1, g3.getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 2 Estagios - G4", 1, g4.getTransicoesComPerdaDePassagem().size());

        assertEquals("Total de transicoes Anel 4 Estagios - G1", 3, g1.getTransicoesComPerdaDePassagem().size());
        assertEquals("Total de transicoes Anel 4 Estagios - G2", 4, g2.getTransicoesComPerdaDePassagem().size());

        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G3", 1, g3.getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 2 Estagios - G4", 1, g4.getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, g1.getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, g1.getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G1", 1, g1.getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());

        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, g2.getTransicoesComPerdaDePassagem().get(0).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, g2.getTransicoesComPerdaDePassagem().get(1).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, g2.getTransicoesComPerdaDePassagem().get(2).getTabelaEntreVerdesTransicoes().size());
        assertEquals("Total tabela EntreVerdes Anel 4 Estagios - G2", 1, g2.getTransicoesComPerdaDePassagem().get(3).getTabelaEntreVerdesTransicoes().size());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

}
