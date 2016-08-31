package controllers;

import checks.Erro;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 8/10/16.
 */
public class ControladoresControllerTest  extends AbstractInfluuntControladorTest {
    @Test
    public void naoDeveriaClonarControladorComStatusConfigurado() {
        Controlador controlador = controladorTestUtils.getControladorDadosBasicos();
        controlador.setStatusControlador(StatusControlador.CONFIGURADO);
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(1, json.size());
    }

    @Test
    public void naoDeveriaEditarControladorComOutroUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("abc");
        usuario.setNome("Usuario ABC");
        usuario.setRoot(false);
        usuario.setEmail("abc@influunt.com.br");
        usuario.save();

        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        controlador = new ControladorCustomDeserializer().getControladorFromJson(json);


        postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
        postResult = route(postRequest);
        json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        VersaoControlador versao = VersaoControlador.findByControlador(controladorClonado);
        assertNotNull(versao);

        versao.setUsuario(usuario);
        versao.update();

        postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controladorClonado.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado));
        postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(1, json.size());
    }

    @Test
    public void deveriaClonar() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals(200, postResult.status());
        assertNotNull("ID Controldor Clonado", controladorClonado.getId());
        assertNotEquals("Teste de Id Diferentes", controlador.getId(), controladorClonado.getId());
        assertEquals("Teste de Aneis", controlador.getAneis().size(), controladorClonado.getAneis().size());
        assertEquals("Teste de Agrupamentos", controlador.getAgrupamentos().size(), controladorClonado.getAgrupamentos().size());
        assertEquals("Teste de Area", controlador.getArea(), controladorClonado.getArea());
        assertEquals("Teste de Modelo", controlador.getModelo(), controladorClonado.getModelo());
        assertEquals("Teste de Controlador Fisico", controlador.getVersaoControlador().getControladorFisico(), controladorClonado.getVersaoControlador().getControladorFisico());
        assertEquals("Total de Versoes", 2, controladorClonado.getVersaoControlador().getControladorFisico().getVersoes().size());
        assertTrue("Versao Tabela Horaria", controladorClonado.getVersoesTabelasHorarias().isEmpty());
        assertFields(controlador, controladorClonado);

        controlador.getAneis().forEach(anel -> {
            Anel anelClonado = controladorClonado.getAneis().stream().filter(anelAux -> anelAux.getIdJson().equals(anel.getIdJson())).findFirst().orElse(null);
            assertFields(anel, anelClonado);
            assertEquals("Teste Anel | Estagio", anel.getEstagios().size(), anelClonado.getEstagios().size());
            assertEquals("Teste Anel | Detectores", anel.getDetectores().size(), anelClonado.getDetectores().size());
            assertEquals("Teste Anel | Grupo Semaforicos", anel.getGruposSemaforicos().size(), anelClonado.getGruposSemaforicos().size());
            assertThat("Teste Anel | Plano", anelClonado.getPlanos().isEmpty(), is(true));

            if (anel.getEndereco() != null) {
                assertFields(anel.getEndereco(), anelClonado.getEndereco());
            }

            anel.getEstagios().forEach(origem -> {
                Estagio destino = anelClonado.getEstagios().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
                assertFields(origem, destino);
                assertEquals("Teste Anel | Estagio | Anel ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                if (origem.getDetector() != null) {
                    assertEquals("Teste Anel | Estagio | Detector ", origem.getDetector().getIdJson(), destino.getDetector().getIdJson());
                }

                origem.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> {
                    EstagioGrupoSemaforico estagioGrupoSemaforicoClonado = destino.getEstagiosGruposSemaforicos().stream().filter(aux -> aux.getIdJson().equals(estagioGrupoSemaforico.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Estagio: ", estagioGrupoSemaforico.getEstagio().getIdJson(), estagioGrupoSemaforicoClonado.getEstagio().getIdJson());
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Grupo Semaforico: ", estagioGrupoSemaforico.getGrupoSemaforico().getIdJson(), estagioGrupoSemaforicoClonado.getGrupoSemaforico().getIdJson());
                    assertFields(estagioGrupoSemaforico, estagioGrupoSemaforicoClonado);
                });

                origem.getAlternativaDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida transicaoClonada = destino.getAlternativaDeTransicoesProibidas().stream().filter(aux -> aux.getIdJson().equals(transicaoProibida.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Transicao Proibida | Alternativo: ", transicaoProibida.getAlternativo().getIdJson(), transicaoClonada.getAlternativo().getIdJson());
                    assertFields(transicaoProibida, transicaoClonada);
                });

                origem.getDestinoDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida transicaoClonada = destino.getDestinoDeTransicoesProibidas().stream().filter(aux -> aux.getIdJson().equals(transicaoProibida.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Transicao Proibida | Destino: ", transicaoProibida.getDestino().getIdJson(), transicaoClonada.getDestino().getIdJson());
                    assertFields(transicaoProibida, transicaoClonada);
                });

                origem.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida transicaoClonada = destino.getOrigemDeTransicoesProibidas().stream().filter(aux -> aux.getIdJson().equals(transicaoProibida.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Transicao Proibida | Origem: ", transicaoProibida.getOrigem().getIdJson(), transicaoClonada.getOrigem().getIdJson());
                    assertFields(transicaoProibida, transicaoClonada);
                });
            });

            anel.getDetectores().forEach(origem -> {
                Detector destino = anelClonado.getDetectores().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
                assertEquals("Teste Anel | Detector | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                assertEquals("Teste Anel | Detector | Estagio: ", origem.getEstagio().getIdJson(), destino.getEstagio().getIdJson());
                assertFields(origem, destino);
            });

            anel.getGruposSemaforicos().forEach(origem -> {
                GrupoSemaforico destino = anelClonado.getGruposSemaforicos().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
                assertEquals("Teste Anel | Grupo Semaforico | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes", origem.getVerdesConflitantes().size(), destino.getVerdesConflitantes().size());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem", origem.getVerdesConflitantesOrigem().size(), destino.getVerdesConflitantesOrigem().size());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino", origem.getVerdesConflitantesDestino().size(), destino.getVerdesConflitantesDestino().size());
                assertEquals("Teste Anel | Grupo Semaforico | Transicao Ganho Passagem", origem.getTransicoesComGanhoDePassagem().size(), destino.getTransicoesComGanhoDePassagem().size());
                assertEquals("Teste Anel | Grupo Semaforico | Transicao Perda Passagem", origem.getTransicoesComPerdaDePassagem().size(), destino.getTransicoesComPerdaDePassagem().size());
                assertFields(origem, destino);

                origem.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> {
                    EstagioGrupoSemaforico estagioGrupoSemaforicoClonado = destino.getEstagiosGruposSemaforicos().stream().filter(aux -> aux.getIdJson().equals(estagioGrupoSemaforico.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Estagio: ", estagioGrupoSemaforico.getEstagio().getIdJson(), estagioGrupoSemaforicoClonado.getEstagio().getIdJson());
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Grupo Semaforico: ", estagioGrupoSemaforico.getGrupoSemaforico().getIdJson(), estagioGrupoSemaforicoClonado.getGrupoSemaforico().getIdJson());
                    assertFields(estagioGrupoSemaforico, estagioGrupoSemaforicoClonado);
                });


                origem.getVerdesConflitantesOrigem().forEach(verdesConflitantes -> {
                    VerdesConflitantes verdesConflitantesClonado = destino.getVerdesConflitantesOrigem().stream().filter(aux -> aux.getIdJson().equals(verdesConflitantes.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Verdes Conflitantes Origem |  Estagio: ", verdesConflitantes.getOrigem().getIdJson(), verdesConflitantesClonado.getOrigem().getIdJson());
                    assertEquals("Teste Anel | Grupo Smaforico | Verdes Conflitantes Destino | Grupo Semaforico: ", verdesConflitantes.getDestino().getIdJson(), verdesConflitantesClonado.getDestino().getIdJson());
                    assertFields(verdesConflitantes, verdesConflitantesClonado);
                });

                origem.getVerdesConflitantesDestino().forEach(verdesConflitantes -> {
                    VerdesConflitantes verdesConflitantesClonado = destino.getVerdesConflitantesDestino().stream().filter(aux -> aux.getIdJson().equals(verdesConflitantes.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Verdes Conflitantes Origem |  Estagio: ", verdesConflitantes.getOrigem().getIdJson(), verdesConflitantesClonado.getOrigem().getIdJson());
                    assertEquals("Teste Anel | Grupo Smaforico | Verdes Conflitantes Destino | Grupo Semaforico: ", verdesConflitantes.getDestino().getIdJson(), verdesConflitantesClonado.getDestino().getIdJson());
                    assertFields(verdesConflitantes, verdesConflitantesClonado);
                });

                origem.getTabelasEntreVerdes().forEach(tabelaEntreVerdes -> {
                    TabelaEntreVerdes tabelaEntreVerdesClonado = destino.getTabelasEntreVerdes().stream().filter(aux -> aux.getIdJson().equals(tabelaEntreVerdes.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Grupo Semaforico: ", tabelaEntreVerdes.getGrupoSemaforico().getIdJson(), tabelaEntreVerdesClonado.getGrupoSemaforico().getIdJson());
                    assertFields(tabelaEntreVerdes, tabelaEntreVerdesClonado);

                    tabelaEntreVerdes.getTabelaEntreVerdesTransicoes().forEach(tvt -> {
                        TabelaEntreVerdesTransicao tvtClonada = tabelaEntreVerdesClonado.getTabelaEntreVerdesTransicoes().stream().filter(aux -> aux.getIdJson().equals(tvt.getIdJson())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: ", tvt.getTabelaEntreVerdes().getIdJson(), tvtClonada.getTabelaEntreVerdes().getIdJson());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: ", tvt.getTransicao().getIdJson(), tvtClonada.getTransicao().getIdJson());
                        assertFields(tvt, tvtClonada);
                    });
                });

                origem.getTransicoes().forEach(transicao -> {
                    Transicao transicaoClonada = destino.getTransicoes().stream().filter(aux -> aux.getIdJson().equals(transicao.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Grupo Semaforico: ", transicao.getGrupoSemaforico().getIdJson(), transicaoClonada.getGrupoSemaforico().getIdJson());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Estagio Origem: ", transicao.getOrigem().getIdJson(), transicaoClonada.getOrigem().getIdJson());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Estagio Destino: ", transicao.getDestino().getIdJson(), transicaoClonada.getDestino().getIdJson());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Atraso de Grupo: ", transicao.getAtrasoDeGrupo().getIdJson(), transicaoClonada.getAtrasoDeGrupo().getIdJson());
                    assertFields(transicao, transicaoClonada);

                    transicao.getTabelaEntreVerdesTransicoes().forEach(tvt -> {
                        TabelaEntreVerdesTransicao tvtClonada = transicaoClonada.getTabelaEntreVerdesTransicoes().stream().filter(aux -> aux.getIdJson().equals(tvt.getIdJson())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: ", tvt.getTabelaEntreVerdes().getIdJson(), tvtClonada.getTabelaEntreVerdes().getIdJson());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: ", tvt.getTransicao().getIdJson(), tvtClonada.getTransicao().getIdJson());
                        assertFields(tvt, tvtClonada);
                    });
                });
            });
        }); // FIM ANEIS

    }

    @Test
    public void deveriaAtivarControlador() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.ControladoresController.ativar(controlador.getId().toString()).url());

        Result postResult = route(postRequest);

        Controlador controladorRetornado = Controlador.find.byId(controlador.getId());

        assertEquals(200, postResult.status());
        assertNotNull("ID Controldor Clonado", controladorRetornado.getId());
        assertEquals("Status Controlador", controladorRetornado.getStatusControlador(), StatusControlador.ATIVO);
        assertFields(controlador, controladorRetornado);
    }

    @Test
    public void deveriaCancelarControladorClonadoEVoltarStatusControladorOrigemParaAtivo() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.ativar();

        int totalControlador = 1;
        int totalAneis = 4;
        int totalAgrupamentos = 0;
        int totalEnderecos = 3;
        int totalGruposSemaforicos = 4;
        int totalVersaoControlador = 1;
        int totalEstagios = 6;
        int totalTransicoesProibidas = 2;
        int totalEstagiosPlanos = 6;
        int totalPlanos = 2;
        int totalGruposSemaforicosPlanos = 4;
        int totalEstagioGruposSemaforicos = 6;
        int totalVerdesConflitantes = 4;
        int totalTabelaEntreVerdes = 4;
        int totalTabelaEntreVerdesTransicao = 12;
        int totalTransicoes = 24;
        int totalAtrasoDeGrupo = 24;
        int totalImagens = 0;

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        controlador.refresh();
        assertEquals("Status do Controlador", controlador.getStatusControlador(), StatusControlador.ATIVO);
        assertEquals("Status da Versao Controlador", controlador.getVersaoControlador().getStatusVersao(), StatusVersao.ARQUIVADO);

        assertEquals(200, postResult.status());
        assertNotNull("ID Controldor Clonado", controladorClonado.getId());
        assertNotEquals("Teste de Id Diferentes", controlador.getId(), controladorClonado.getId());
        assertEquals("Teste de Aneis", controlador.getAneis().size(), controladorClonado.getAneis().size());
        assertEquals("Teste de Agrupamentos", controlador.getAgrupamentos().size(), controladorClonado.getAgrupamentos().size());
        assertEquals("Teste de Area", controlador.getArea(), controladorClonado.getArea());
        assertEquals("Teste de Modelo", controlador.getModelo(), controladorClonado.getModelo());
        assertEquals("Teste de Controlador Fisico", controlador.getVersaoControlador().getControladorFisico(), controladorClonado.getVersaoControlador().getControladorFisico());
        assertEquals("Total de Versoes", 2, controladorClonado.getVersaoControlador().getControladorFisico().getVersoes().size());
        assertFields(controlador, controladorClonado);

        assertEquals("Total de Controladores", totalControlador * 2, Controlador.find.findRowCount());
        assertEquals("Total de Aneis", totalAneis * 2, Anel.find.findRowCount());
        assertEquals("Total de Agrupamentos", totalAgrupamentos * 2, Agrupamento.find.findRowCount());
        assertEquals("Total de Enderecos", totalEnderecos * 2, Endereco.find.findRowCount());
        assertEquals("Total de Grupos Semaforicos", totalGruposSemaforicos * 2, GrupoSemaforico.find.findRowCount());
        assertEquals("Total de Versões Controladores", totalVersaoControlador * 2, VersaoControlador.find.findRowCount());
        assertEquals("Total de Estagios", totalEstagios * 2, Estagio.find.findRowCount());
        assertEquals("Total de Transicoes Proibidas", totalTransicoesProibidas * 2, Ebean.find(TransicaoProibida.class).findRowCount());
        assertEquals("Total de Estagio Grupos Semaforicos", totalEstagioGruposSemaforicos * 2, Ebean.find(EstagioGrupoSemaforico.class).findRowCount());
        assertEquals("Total de Verdes Conflitantes", totalVerdesConflitantes * 2, Ebean.find(VerdesConflitantes.class).findRowCount());
        assertEquals("Total de Tabela EntreVerdes", totalTabelaEntreVerdes * 2, TabelaEntreVerdes.find.findRowCount());
        assertEquals("Total de Tabela EntreVerdes Transicao", totalTabelaEntreVerdesTransicao * 2, TabelaEntreVerdesTransicao.find.findRowCount());
        assertEquals("Total de Transicoes", totalTransicoes * 2, Transicao.find.findRowCount());
        assertEquals("Total de Atraso de Grupo", totalAtrasoDeGrupo * 2, AtrasoDeGrupo.find.findRowCount());
        assertEquals("Total de Imagens", totalImagens * 2, Imagem.find.findRowCount());


        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ControladoresController.cancelarEdicao(controladorClonado.getId().toString()).url());
        Result deleteResult = route(deleteRequest);
        assertEquals(200, deleteResult.status());

        assertEquals("Total de Controladores", totalControlador, Controlador.find.findRowCount());
        assertEquals("Total de Aneis", totalAneis, Anel.find.findRowCount());
        assertEquals("Total de Agrupamentos", totalAgrupamentos, Agrupamento.find.findRowCount());
        assertEquals("Total de Enderecos", totalEnderecos, Endereco.find.findRowCount());
        assertEquals("Total de Grupos Semaforicos", totalGruposSemaforicos, GrupoSemaforico.find.findRowCount());
        assertEquals("Total de Versões Controladores", totalVersaoControlador, VersaoControlador.find.findRowCount());
        assertEquals("Total de Estagios", totalEstagios, Estagio.find.findRowCount());
        assertEquals("Total de Transicoes Proibidas", totalTransicoesProibidas, Ebean.find(TransicaoProibida.class).findRowCount());
        assertEquals("Total de Estagios Planos", totalEstagiosPlanos, Ebean.find(EstagioPlano.class).findRowCount());
        assertEquals("Total de Planos", totalPlanos, Plano.find.findRowCount());
        assertEquals("Total de Estagio Grupos Semaforicos", totalEstagioGruposSemaforicos, Ebean.find(EstagioGrupoSemaforico.class).findRowCount());
        assertEquals("Total de Verdes Conflitantes", totalVerdesConflitantes, Ebean.find(VerdesConflitantes.class).findRowCount());
        assertEquals("Total de Tabela EntreVerdes", totalTabelaEntreVerdes, TabelaEntreVerdes.find.findRowCount());
        assertEquals("Total de Tabela EntreVerdes Transicao", totalTabelaEntreVerdesTransicao, TabelaEntreVerdesTransicao.find.findRowCount());
        assertEquals("Total de Transicoes", totalTransicoes, Transicao.find.findRowCount());
        assertEquals("Total de Atraso de Grupo", totalAtrasoDeGrupo, AtrasoDeGrupo.find.findRowCount());
        assertEquals("Total de GrupoSemaforicoPlano", totalGruposSemaforicosPlanos, Ebean.find(GrupoSemaforicoPlano.class).findRowCount());
        assertEquals("Total de Imagens", totalImagens, Imagem.find.findRowCount());

        controlador.refresh();
        assertEquals("Status do Controlador", controlador.getStatusControlador(), StatusControlador.ATIVO);
        assertEquals("Status da Versao Controlador", controlador.getVersaoControlador().getStatusVersao(), StatusVersao.ATIVO);

    }

    @Test
    public void deveriaClonarPlanosAnelCom2Estagios() {
        int totalEstagiosPlanos = 6;
        int totalPlanos = 2;
        int totalGruposSemaforicosPlanos = 4;

        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);
        controladorClonado.refresh();

        controladorClonado.getAneis().forEach(anel -> {

            if (!CollectionUtils.isEmpty(anel.getVersoesPlanos())) {
                VersaoPlano versaoEdicao = anel.getVersaoPlanoEmEdicao();
                VersaoPlano versaoAnterior = versaoEdicao.getVersaoAnterior();

                versaoAnterior.getPlanos().forEach(origem -> {
                    Plano destino = versaoEdicao.getPlanos().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Plano | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                    if (origem.getAgrupamento() != null) {
                        assertEquals("Teste Anel | Plano | Agrupamento: ", origem.getAgrupamento().getIdJson(), destino.getAgrupamento().getIdJson());
                    }
                    assertFields(origem, destino);

                    origem.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano estagioPlanoClonado = destino.getEstagiosPlanos().stream().filter(aux -> aux.getIdJson().equals(estagioPlano.getIdJson())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Plano | Estagio Plano |  Estagio: ", estagioPlano.getEstagio().getIdJson(), estagioPlanoClonado.getEstagio().getIdJson());
                        assertEquals("Teste Anel | Plano | Estagio Plano |  Plano: ", estagioPlano.getPlano().getIdJson(), estagioPlanoClonado.getPlano().getIdJson());
                        if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                            assertEquals("Teste Anel | Plano | Estagio Plano |  Estagio Dispensavel: ", estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson(), estagioPlanoClonado.getEstagioQueRecebeEstagioDispensavel().getIdJson());
                        }
                        assertFields(estagioPlano, estagioPlanoClonado);
                    });

                    origem.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                        GrupoSemaforicoPlano grupoSemaforicoPlanoClonado = destino.getGruposSemaforicosPlanos().stream().filter(aux -> aux.getIdJson().equals(grupoSemaforicoPlano.getIdJson())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Plano | Grupo Semaforico Plano |  Grupo Semaforico Plano: ", grupoSemaforicoPlano.getGrupoSemaforico().getIdJson(), grupoSemaforicoPlanoClonado.getGrupoSemaforico().getIdJson());
                        assertEquals("Teste Anel | Plano | Grupo Semaforico Plano |  Plano: ", grupoSemaforicoPlano.getPlano().getIdJson(), grupoSemaforicoPlanoClonado.getPlano().getIdJson());
                        assertFields(grupoSemaforicoPlano, grupoSemaforicoPlanoClonado);
                    });
                });
            }
        });


        assertEquals("Total de Estagios Planos", totalEstagiosPlanos * 2, Ebean.find(EstagioPlano.class).findRowCount());
        assertEquals("Total de Planos", totalPlanos * 2, Plano.find.findRowCount());
        assertEquals("Total de GrupoSemaforicoPlano", totalGruposSemaforicosPlanos * 2, Ebean.find(GrupoSemaforicoPlano.class).findRowCount());

    }

    @Test
    public void deveriaClonar5VersoesPlano() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        int totalVersoes = 2;

        assertEquals("Total de Versão Plano", totalVersoes, VersaoPlano.find.findRowCount());

        for (int i = 2; i < 7; i++) {
            Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                    .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));


            Result postResult = route(postRequest);
            JsonNode json = Json.parse(Helpers.contentAsString(postResult));
            Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Versão Plano", totalVersoes * i, VersaoPlano.find.findRowCount());

            postRequest = new Http.RequestBuilder().method("POST")
                    .uri(routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado));

            postResult = route(postRequest);
            json = Json.parse(Helpers.contentAsString(postResult));
            assertEquals(OK, postResult.status());
            controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Versão Plano", totalVersoes * i, VersaoPlano.find.findRowCount());

            controladorClonado.ativar();
        }

    }

    @Test
    public void deveriaClonarTabelaHorariaEditarTabelaHoraria() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();


        int totalTabelaHoraria = 1;
        int totalEventos = 3;

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria, TabelaHorario.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos, Evento.find.findRowCount());

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * 2, TabelaHorario.find.findRowCount());
        assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * 2, VersaoTabelaHoraria.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos * 2, Evento.find.findRowCount());

        if (controlador.getTabelaHoraria() != null) {
            assertFields(controlador.getTabelaHoraria(), controladorClonado.getTabelaHoraria());
            controlador.getTabelaHoraria().getEventos().forEach(evento -> {
                Evento eventoClonado = controladorClonado.getTabelaHoraria().getEventos().stream().filter(aux -> aux.getPosicao().equals(evento.getPosicao())).findFirst().orElse(null);
                assertFields(evento, eventoClonado);
            });
        }

        controladorClonado.update();

        assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * 2, VersaoTabelaHoraria.find.findRowCount());
        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * 2, TabelaHorario.find.findRowCount());


        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado));

        postResult = route(postRequest);
        json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(OK, postResult.status());
    }

    @Test
    public void deveriaClonar5VersoesTabelaHoraria() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        int totalTabelaHoraria = 1;
        int totalEventos = 3;

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria, TabelaHorario.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos, Evento.find.findRowCount());

        for (int i = 2; i < 7; i++) {
            Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                    .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

            Result postResult = route(postRequest);
            JsonNode json = Json.parse(Helpers.contentAsString(postResult));
            Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * i, TabelaHorario.find.findRowCount());
            assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());
            assertEquals("Total de Eventos", totalEventos * i, Evento.find.findRowCount());

            postRequest = new Http.RequestBuilder().method("POST")
                    .uri(routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado));

            postResult = route(postRequest);
            json = Json.parse(Helpers.contentAsString(postResult));
            assertEquals(OK, postResult.status());
            controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());
            assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * i, TabelaHorario.find.findRowCount());

            controladorClonado.ativar();
        }
    }

    @Test
    public void deveriaClonarPlanosAnelCom2EstagiosEAtualizarPlano() {
        int totalEstagiosPlanos = 6;
        int totalPlanos = 2;
        int totalGruposSemaforicosPlanos = 4;

        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        Anel anelCom2Estagios = controladorClonado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();

        Plano plano = anelCom2Estagios.getVersaoPlano().getPlanos().get(0);
        plano.setDescricao("Nova Descricao");

        postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado));


        postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        assertEquals("Total de Estagios Planos", totalEstagiosPlanos * 2, Ebean.find(EstagioPlano.class).findRowCount());
        assertEquals("Total de Planos", totalPlanos * 2, Plano.find.findRowCount());
        assertEquals("Total de GrupoSemaforicoPlano", totalGruposSemaforicosPlanos * 2, Ebean.find(GrupoSemaforicoPlano.class).findRowCount());

    }

    private <T> void assertFields(T origem, T destino) {
        for (Field field : origem.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(origem) == null || Modifier.isFinal(field.getModifiers()) || field.getType().equals(UUID.class)
                        || field.getType().equals(DateTime.class) || field.getType().equals(Fabricante.class) || field.getType().equals(Cidade.class)
                        || field.getType().equals(StatusControlador.class) || field.getType().equals(DiaDaSemana.class) || "idJson".equals(field.getName())) {
                    continue;
                }
                if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                    if (field.getType().isPrimitive() || field.getType().isEnum() ||
                            field.getType().equals(String.class)) {

                        Logger.debug("[" + origem.getClass().getName().toString() + "] - CAMPO: " + field.getName().toString());
                        assertEquals("Teste de " + field.getName().toString(), field.get(origem), field.get(destino));
                    }
                }
            } catch (IllegalAccessException | SecurityException e) {
                Logger.error("XXX ERRO NO CAMPO: " + field.getName().toString());
            }
        }
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return null;
    }
}
