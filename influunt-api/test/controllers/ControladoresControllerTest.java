package controllers;

import checks.ControladorFinalizaConfiguracaoCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
import utils.RangeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 8/10/16.
 */
public class ControladoresControllerTest extends AbstractInfluuntControladorTest {

    @Test
    public void naoDeveriaEditarControladorComOutroUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("abc");
        usuario.setNome("Usuario ABC");
        usuario.setRoot(false);
        usuario.setEmail("abc@influunt.com.br");
        usuario.save();

        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();

        VersaoControlador versaoControlador = controlador.getVersaoControlador();
        versaoControlador.setStatusVersao(StatusVersao.SINCRONIZADO);
        versaoControlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.TabelaHorariosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        controlador = new ControladorCustomDeserializer().getControladorFromJson(json);


        postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        postResult = route(postRequest);
        json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        VersaoControlador versao = VersaoControlador.findByControlador(controladorClonado);
        assertNotNull(versao);

        versao.setUsuario(usuario);
        versao.update();

        postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controladorClonado.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado, Cidade.find.all(), RangeUtils.getInstance(null)));
        postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(1, json.size());
    }

    @Test
    public void deveriaClonar() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();

        Anel anelOriginal = controlador.getAneis().stream().filter(Anel::isAtivo).findFirst().orElse(null);
        Plano planoOriginal = anelOriginal.getPlanos().get(0);
        planoOriginal.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        EstagioPlano epDispensavel = planoOriginal.getEstagiosPlanos().get(0);
        EstagioPlano epSeguinte = planoOriginal.getEstagiosPlanos().get(1);
        epDispensavel.setDispensavel(true);
        epDispensavel.setEstagioQueRecebeEstagioDispensavel(epSeguinte);

        controlador.update();

        VersaoControlador versaoControlador = controlador.getVersaoControlador();
        versaoControlador.setStatusVersao(StatusVersao.SINCRONIZADO);
        versaoControlador.update();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);
        assertEquals(200, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);
        controlador = Controlador.find.byId(controlador.getId());

        assertNotNull("ID Controldor Clonado", controladorClonado.getId());
        assertNotEquals("Teste de Id Diferentes", controlador.getId(), controladorClonado.getId());
        assertEquals("Teste de Aneis", controlador.getAneis().size(), controladorClonado.getAneis().size());
        assertEquals("Teste de Area", controlador.getArea(), controladorClonado.getArea());
        assertEquals("Teste de Subarea", controlador.getSubarea(), controladorClonado.getSubarea());
        assertEquals("Teste de Modelo", controlador.getModelo(), controladorClonado.getModelo());
        assertEquals("Teste de Controlador Fisico", controlador.getVersaoControlador().getControladorFisico(), controladorClonado.getVersaoControlador().getControladorFisico());
        versaoControlador = VersaoControlador.find.byId(controladorClonado.getVersaoControlador().getId());
        assertNotEquals(versaoControlador, null);
        assertEquals("Total de Versoes", 2, versaoControlador.getControladorFisico().getVersoes().size());

        // tabela horária
        VersaoTabelaHoraria versaoAntiga = controlador.getVersoesTabelasHorarias().get(0);
        assertEquals("Versao Tabela Horaria", 1, controladorClonado.getVersoesTabelasHorarias().size());
        assertEquals("Status versão tabela horária antiga", StatusVersao.ARQUIVADO, versaoAntiga.getStatusVersao());
        assertEquals("Número de eventos", versaoAntiga.getTabelaHoraria().getEventos().size(), controladorClonado.getTabelaHoraria().getEventos().size());
        versaoAntiga.getTabelaHoraria().getEventos().forEach(evento -> {
            Evento eventoClonado = controladorClonado.getTabelaHoraria().getEventos().stream().filter(eventoClone -> eventoClone.getTipo().equals(evento.getTipo()) && eventoClone.getPosicao().equals(evento.getPosicao())).findFirst().orElse(null);
            assertNotNull(eventoClonado);
            assertEquals(evento.getDiaDaSemana(), eventoClonado.getDiaDaSemana());
            assertEquals(evento.getHorario(), eventoClonado.getHorario());
        });

        assertFields(controlador, controladorClonado);

        controlador.getAneis().forEach(anel -> {
            Anel anelClonado = controladorClonado.getAneis().stream().filter(anelAux -> anelAux.getIdJson().equals(anel.getIdJson())).findFirst().orElse(null);
            assertFields(anel, anelClonado);
            assertEquals("Teste Anel | Estagio", anel.getEstagios().size(), anelClonado.getEstagios().size());
            assertEquals("Teste Anel | Detectores", anel.getDetectores().size(), anelClonado.getDetectores().size());
            assertEquals("Teste Anel | Grupo Semaforicos", anel.getGruposSemaforicos().size(), anelClonado.getGruposSemaforicos().size());
            if (anel.isAtivo()) {
                assertEquals("Teste anel | Planos", anel.getVersoesPlanos().get(0).getPlanos().size(), anelClonado.getPlanos().size());
                anel.getVersoesPlanos().get(0).getPlanos().forEach(plano -> {
                    Plano planoClonado = anelClonado.getPlanos().stream().filter(planoClone -> planoClone.getPosicao().equals(plano.getPosicao())).findFirst().orElse(null);
                    assertNotNull(planoClonado);
                    assertEquals("Teste anel | Planos: Descrição", plano.getDescricao(), planoClonado.getDescricao());
                    assertEquals("Teste anel | Planos: Tempo Ciclo", plano.getTempoCiclo(), planoClonado.getTempoCiclo());
                    assertEquals("Teste anel | Planos: Defasagem", plano.getDefasagem(), planoClonado.getDefasagem());
                    assertEquals("Teste anel | Planos: Modo Operação", plano.getModoOperacao(), planoClonado.getModoOperacao());
                    assertEquals("Teste anel | Planos | EstagioPlanos", plano.getEstagiosPlanos().size(), planoClonado.getEstagiosPlanos().size());
                    plano.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano epClonado = planoClonado.getEstagiosPlanos().stream().filter(ep -> ep.getPosicao().equals(estagioPlano.getPosicao())).findFirst().orElse(null);
                        assertNotNull(epClonado);
                        assertEquals("Teste anel | Planos | EstagioPlanos: tempo verde", estagioPlano.getTempoVerde(), epClonado.getTempoVerde());
                        assertEquals("Teste anel | Planos | EstagioPlanos: tempo verde mínimo", estagioPlano.getTempoVerdeMinimo(), epClonado.getTempoVerdeMinimo());
                        assertEquals("Teste anel | Planos | EstagioPlanos: tempo verde máximo", estagioPlano.getTempoVerdeMaximo(), epClonado.getTempoVerdeMaximo());
                        assertEquals("Teste anel | Planos | EstagioPlanos: tempo verde intermediário", estagioPlano.getTempoVerdeIntermediario(), epClonado.getTempoVerdeIntermediario());
                        assertEquals("Teste anel | Planos | EstagioPlanos: dispensável", estagioPlano.isDispensavel(), epClonado.isDispensavel());
                        if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                            EstagioPlano epQueRecebe = estagioPlano.getEstagioQueRecebeEstagioDispensavel();
                            EstagioPlano epClonadoQueRecebe = epClonado.getEstagioQueRecebeEstagioDispensavel();
                            assertNotNull(epClonadoQueRecebe);
                            assertEquals("Teste anel | Planos | EstagioPlanos: estagio que recebe dispensavel (posição)", epQueRecebe.getPosicao(), epClonadoQueRecebe.getPosicao());
                            assertEquals("Teste anel | Planos | EstagioPlanos: estagio que recebe dispensavel (tempo verde)", epQueRecebe.getTempoVerde(), epClonadoQueRecebe.getTempoVerde());
                            assertEquals("Teste anel | Planos | EstagioPlanos: estagio que recebe dispensavel (tempo verde mínimo)", epQueRecebe.getTempoVerdeMinimo(), epClonadoQueRecebe.getTempoVerdeMinimo());
                            assertEquals("Teste anel | Planos | EstagioPlanos: estagio que recebe dispensavel (tempo verde máximo)", epQueRecebe.getTempoVerdeMaximo(), epClonadoQueRecebe.getTempoVerdeMaximo());
                            assertEquals("Teste anel | Planos | EstagioPlanos: estagio que recebe dispensavel (tempo verde intermediário)", epQueRecebe.getTempoVerdeIntermediario(), epClonadoQueRecebe.getTempoVerdeIntermediario());
                        }
                    });
                });
            }

            if (anel.getEndereco() != null) {
                assertFields(anel.getEndereco(), anelClonado.getEndereco());
            }

            anel.getEstagios().forEach(origem -> {
                Estagio destino = anelClonado.getEstagios().stream().filter(e -> e.getPosicao().equals(origem.getPosicao())).findFirst().orElse(null);
                assertFields(origem, destino);
                assertEquals("Teste Anel | Estagio | Anel ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                if (origem.getDetector() != null) {
                    assertEquals("Teste Anel | Estagio | Detector (tipo)", origem.getDetector().getTipo(), destino.getDetector().getTipo());
                    assertEquals("Teste Anel | Estagio | Detector (posição)", origem.getDetector().getPosicao(), destino.getDetector().getPosicao());
                }

                origem.getEstagiosGruposSemaforicos().forEach(egs -> {
                    EstagioGrupoSemaforico egsClonado = destino.getEstagiosGruposSemaforicos().stream().filter(aux ->
                        aux.getEstagio().getPosicao().equals(egs.getEstagio().getPosicao()) &&
                            aux.getGrupoSemaforico().getTipo().equals(egs.getGrupoSemaforico().getTipo()) &&
                            aux.getGrupoSemaforico().getPosicao().equals(egs.getGrupoSemaforico().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Estagio: ", egs.getEstagio().getPosicao(), egsClonado.getEstagio().getPosicao());
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Grupo Semaforico: (tipo)", egs.getGrupoSemaforico().getTipo(), egsClonado.getGrupoSemaforico().getTipo());
                    assertEquals("Teste Anel | Estagio Grupo Smaforico | Grupo Semaforico: (posição)", egs.getGrupoSemaforico().getPosicao(), egsClonado.getGrupoSemaforico().getPosicao());
                    assertFields(egs, egsClonado);
                });

                origem.getAlternativaDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida tpClonada = destino.getAlternativaDeTransicoesProibidas().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(transicaoProibida.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(transicaoProibida.getDestino().getPosicao()) &&
                            aux.getAlternativo().getPosicao().equals(transicaoProibida.getAlternativo().getPosicao())
                    ).findFirst().orElse(null);

                    assertEquals("Teste Anel | Transicao Proibida | Alternativo: ", transicaoProibida.getAlternativo().getPosicao(), tpClonada.getAlternativo().getPosicao());
                    assertFields(transicaoProibida, tpClonada);
                });

                origem.getDestinoDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida tpClonada = destino.getDestinoDeTransicoesProibidas().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(transicaoProibida.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(transicaoProibida.getDestino().getPosicao()) &&
                            aux.getAlternativo().getPosicao().equals(transicaoProibida.getAlternativo().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Transicao Proibida | Destino: ", transicaoProibida.getDestino().getPosicao(), tpClonada.getDestino().getPosicao());
                    assertFields(transicaoProibida, tpClonada);
                });

                origem.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida tpClonada = destino.getOrigemDeTransicoesProibidas().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(transicaoProibida.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(transicaoProibida.getDestino().getPosicao()) &&
                            aux.getAlternativo().getPosicao().equals(transicaoProibida.getAlternativo().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Transicao Proibida | Origem: ", transicaoProibida.getOrigem().getPosicao(), tpClonada.getOrigem().getPosicao());
                    assertFields(transicaoProibida, tpClonada);
                });
            });

            anel.getDetectores().forEach(origem -> {
                Detector destino = anelClonado.getDetectores().stream().filter(aux ->
                    aux.getTipo().equals(origem.getTipo()) && aux.getPosicao().equals(origem.getPosicao())
                ).findFirst().orElse(null);
                assertEquals("Teste Anel | Detector | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                assertEquals("Teste Anel | Detector | Estagio: ", origem.getEstagio().getPosicao(), destino.getEstagio().getPosicao());
                assertFields(origem, destino);
            });

            anel.getGruposSemaforicos().forEach(origem -> {
                GrupoSemaforico destino = anelClonado.getGruposSemaforicos().stream().filter(aux -> aux.getPosicao().equals(origem.getPosicao())).findFirst().orElse(null);
                destino.refresh();
                assertEquals("Teste Anel | Grupo Semaforico | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes", origem.getVerdesConflitantes().size(), destino.getVerdesConflitantes().size());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem", origem.getVerdesConflitantesOrigem().size(), destino.getVerdesConflitantesOrigem().size());
                assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino", origem.getVerdesConflitantesDestino().size(), destino.getVerdesConflitantesDestino().size());
                assertEquals("Teste Anel | Grupo Semaforico | Transicao Ganho Passagem", origem.getTransicoesComGanhoDePassagem().size(), destino.getTransicoesComGanhoDePassagem().size());
                assertEquals("Teste Anel | Grupo Semaforico | Transicao Perda Passagem", origem.getTransicoesComPerdaDePassagem().size(), destino.getTransicoesComPerdaDePassagem().size());
                assertFields(origem, destino);

                origem.getVerdesConflitantesOrigem().forEach(vc -> {
                    VerdesConflitantes vcClonado = destino.getVerdesConflitantesOrigem().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(vc.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(vc.getDestino().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem | Grupo Semaforico: (tipo)", vc.getOrigem().getTipo(), vcClonado.getOrigem().getTipo());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem | Grupo Semaforico: (posição)", vc.getOrigem().getPosicao(), vcClonado.getOrigem().getPosicao());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino | Grupo Semaforico: (tipo)", vc.getDestino().getTipo(), vcClonado.getDestino().getTipo());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino | Grupo Semaforico: (posição)", vc.getDestino().getPosicao(), vcClonado.getDestino().getPosicao());
                    assertFields(vc, vcClonado);
                });

                origem.getVerdesConflitantesDestino().forEach(vc -> {
                    VerdesConflitantes vcClonado = destino.getVerdesConflitantesDestino().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(vc.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(vc.getDestino().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem | Grupo Semaforico: (tipo)", vc.getOrigem().getTipo(), vcClonado.getOrigem().getTipo());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Origem | Grupo Semaforico: (posição)", vc.getOrigem().getPosicao(), vcClonado.getOrigem().getPosicao());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino | Grupo Semaforico: (tipo)", vc.getDestino().getTipo(), vcClonado.getDestino().getTipo());
                    assertEquals("Teste Anel | Grupo Semaforico | Verdes Conflitantes Destino | Grupo Semaforico: (posição)", vc.getDestino().getPosicao(), vcClonado.getDestino().getPosicao());
                    assertFields(vc, vcClonado);
                });

                origem.getTabelasEntreVerdes().forEach(tev -> {
                    TabelaEntreVerdes tevClonada = destino.getTabelasEntreVerdes().stream().filter(aux -> aux.getPosicao().equals(tev.getPosicao())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Grupo Semaforico: (tipo)", tev.getGrupoSemaforico().getTipo(), tevClonada.getGrupoSemaforico().getTipo());
                    assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Grupo Semaforico: (posição)", tev.getGrupoSemaforico().getPosicao(), tevClonada.getGrupoSemaforico().getPosicao());
                    assertFields(tev, tevClonada);

                    tev.getTabelaEntreVerdesTransicoes().forEach(tevt -> {
                        TabelaEntreVerdesTransicao tevtClonada = tevClonada.getTabelaEntreVerdesTransicoes().stream().filter(aux ->
                            aux.getTransicao().getOrigem().getPosicao().equals(tevt.getTransicao().getOrigem().getPosicao()) &&
                                aux.getTransicao().getDestino().getPosicao().equals(tevt.getTransicao().getDestino().getPosicao())
                        ).findFirst().orElse(null);
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: (posição)", tevt.getTabelaEntreVerdes().getPosicao(), tevtClonada.getTabelaEntreVerdes().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: (grupo semaforico)", tevt.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao(), tevtClonada.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: (origem)", tevt.getTransicao().getOrigem().getPosicao(), tevtClonada.getTransicao().getOrigem().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: (destino)", tevt.getTransicao().getDestino().getPosicao(), tevtClonada.getTransicao().getDestino().getPosicao());
                        assertFields(tevt, tevtClonada);
                    });
                });

                origem.getTransicoes().forEach(transicao -> {
                    Transicao transicaoClonada = destino.getTransicoes().stream().filter(aux ->
                        aux.getOrigem().getPosicao().equals(transicao.getOrigem().getPosicao()) &&
                            aux.getDestino().getPosicao().equals(transicao.getDestino().getPosicao())
                    ).findFirst().orElse(null);
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Grupo Semaforico: ", transicao.getGrupoSemaforico().getPosicao(), transicaoClonada.getGrupoSemaforico().getPosicao());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Estagio Origem: ", transicao.getOrigem().getPosicao(), transicaoClonada.getOrigem().getPosicao());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Estagio Destino: ", transicao.getDestino().getPosicao(), transicaoClonada.getDestino().getPosicao());
                    assertEquals("Teste Anel | Grupo Smaforico | Transicoes |  Atraso de Grupo: ", transicao.getAtrasoDeGrupo().getAtrasoDeGrupo(), transicaoClonada.getAtrasoDeGrupo().getAtrasoDeGrupo());
                    assertFields(transicao, transicaoClonada);

                    transicao.getTabelaEntreVerdesTransicoes().forEach(tevt -> {
                        TabelaEntreVerdesTransicao tevtClonada = transicaoClonada.getTabelaEntreVerdesTransicoes().stream().filter(aux ->
                            aux.getTabelaEntreVerdes().getPosicao().equals(tevt.getTabelaEntreVerdes().getPosicao()) &&
                                aux.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao().equals(tevt.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao())
                        ).findFirst().orElse(null);
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: (posição)", tevt.getTabelaEntreVerdes().getPosicao(), tevtClonada.getTabelaEntreVerdes().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: (grupo semaforico)", tevt.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao(), tevtClonada.getTabelaEntreVerdes().getGrupoSemaforico().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: (origem)", tevt.getTransicao().getOrigem().getPosicao(), tevtClonada.getTransicao().getOrigem().getPosicao());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: (destino)", tevt.getTransicao().getDestino().getPosicao(), tevtClonada.getTransicao().getDestino().getPosicao());
                        assertFields(tevt, tevtClonada);
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
        assertEquals("StatusDevice Controlador", controladorRetornado.getVersaoControlador().getStatusVersao(), StatusVersao.SINCRONIZADO);
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
        int totalTabelaEntreVerdesTransicao = 9;
        int totalTransicoes = 18;
        int totalAtrasoDeGrupo = 18;
        int totalImagens = 0;

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        Result postResult = route(postRequest);
        assertEquals(200, postResult.status());
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        controlador.refresh();
        assertEquals("Status da Versao Controlador antiga", StatusVersao.ARQUIVADO, controlador.getVersaoControlador().getStatusVersao());

        assertNotNull("ID Controldor Clonado", controladorClonado.getId());
        assertNotEquals("Teste de Id Diferentes", controlador.getId(), controladorClonado.getId());
        assertEquals("Teste de Aneis", controlador.getAneis().size(), controladorClonado.getAneis().size());
        assertEquals("Teste de Area", controlador.getArea(), controladorClonado.getArea());
        assertEquals("Teste de Subarea", controlador.getSubarea(), controladorClonado.getSubarea());
        assertEquals("Teste de Modelo", controlador.getModelo(), controladorClonado.getModelo());
        assertEquals("Teste de Controlador Fisico", controlador.getVersaoControlador().getControladorFisico(), controladorClonado.getVersaoControlador().getControladorFisico());
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
        assertEquals("StatusDevice do Controlador", controlador.getVersaoControlador().getStatusVersao(), StatusVersao.CONFIGURADO);
    }

    @Test
    public void deveriaClonarPlanosAnelCom2Estagios() {
        int totalEstagiosPlanos = 6;
        int totalPlanos = 2;
        int totalGruposSemaforicosPlanos = 4;

        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();
        controlador.setStatusVersao(StatusVersao.SINCRONIZADO);

        int totalTabelasHorarias = TabelaHorario.find.findRowCount();
        int totalEventos = Evento.find.findRowCount();

        assertFalse(controlador.isBloqueado());

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);
        controladorClonado.refresh();

        assertTrue(controladorClonado.isBloqueado());

        controladorClonado.getAneis().forEach(anel -> {
            if (!CollectionUtils.isEmpty(anel.getVersoesPlanos())) {
                VersaoPlano versaoEdicao = anel.getVersaoPlanoEmEdicao();
                VersaoPlano versaoAnterior = versaoEdicao.getVersaoAnterior();

                versaoAnterior.getPlanos().forEach(origem -> {
                    Plano destino = versaoEdicao.getPlanos().stream().filter(aux -> aux.getPosicao().equals(origem.getPosicao())).findFirst().orElse(null);
                    assertEquals("Teste Anel | Plano | Anel: ", origem.getAnel().getIdJson(), destino.getAnel().getIdJson());
                    assertFields(origem, destino);

                    origem.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano estagioPlanoClonado = destino.getEstagiosPlanos().stream().filter(aux -> aux.getPosicao().equals(estagioPlano.getPosicao())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Plano | Estagio Plano |  Estagio: ", estagioPlano.getEstagio().getIdJson(), estagioPlanoClonado.getEstagio().getIdJson());
                        assertNotEquals("Teste Anel | Plano | Estagio Plano |  Plano: ", estagioPlano.getPlano().getIdJson(), estagioPlanoClonado.getPlano().getIdJson());
                        if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                            assertEquals("Teste Anel | Plano | Estagio Plano |  Estagio Dispensavel: ", estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson(), estagioPlanoClonado.getEstagioQueRecebeEstagioDispensavel().getIdJson());
                        }
                        assertFields(estagioPlano, estagioPlanoClonado);
                    });

                    origem.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                        GrupoSemaforicoPlano grupoSemaforicoPlanoClonado = destino.getGruposSemaforicosPlanos().stream().filter(aux -> aux.getGrupoSemaforico().getPosicao().equals(grupoSemaforicoPlano.getGrupoSemaforico().getPosicao())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Plano | Grupo Semaforico Plano |  Grupo Semaforico Plano: ", grupoSemaforicoPlano.getGrupoSemaforico().getIdJson(), grupoSemaforicoPlanoClonado.getGrupoSemaforico().getIdJson());
                        assertNotEquals("Teste Anel | Plano | Grupo Semaforico Plano |  Plano: ", grupoSemaforicoPlano.getPlano().getIdJson(), grupoSemaforicoPlanoClonado.getPlano().getIdJson());
                        assertFields(grupoSemaforicoPlano, grupoSemaforicoPlanoClonado);
                    });
                });
            }
        });

        assertEquals("Total de Estagios Planos", totalEstagiosPlanos * 2, Ebean.find(EstagioPlano.class).findRowCount());
        assertEquals("Total de Planos", totalPlanos * 2, Plano.find.findRowCount());
        assertEquals("Total de GrupoSemaforicoPlano", totalGruposSemaforicosPlanos * 2, Ebean.find(GrupoSemaforicoPlano.class).findRowCount());
        assertEquals("Total de tabelas horárias", totalTabelasHorarias * 2, Ebean.find(TabelaHorario.class).findRowCount());
        assertEquals("Total de Eventos", totalEventos * 2, Ebean.find(Evento.class).findRowCount());

        VersaoTabelaHoraria versaoAntiga = controladorClonado.getVersoesTabelasHorarias().stream().filter(vth -> StatusVersao.ARQUIVADO.equals(vth.getStatusVersao())).findFirst().orElse(null);
        VersaoTabelaHoraria noveVersao = controladorClonado.getVersaoTabelaHorariaEmEdicao();
        assertEquals("Total de eventos tabela horária", versaoAntiga.getTabelaHoraria().getEventos().size(), noveVersao.getTabelaHoraria().getEventos().size());
    }

    @Test
    public void deveriaClonar5VersoesPlano() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();
        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        int totalVersoesPlano = 2;
        int totalVersoesTabelaHoraria = 1;

        assertEquals("Total de Versão Plano", totalVersoesPlano, VersaoPlano.find.findRowCount());
        assertEquals("Total de Versão Tabela Horária", totalVersoesTabelaHoraria, VersaoTabelaHoraria.find.findRowCount());

        assertFalse("Controlador não deveria estar bloqueado para edição", controlador.isBloqueado());
        assertFalse("Planos não deveriam estar bloqueado para edição", controlador.isPlanosBloqueado());

        for (int i = 2; i < 7; i++) {
            Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url())
                .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

            Result result = route(request);
            JsonNode json = Json.parse(Helpers.contentAsString(result));
            Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertTrue("Controlador deveria estar bloqueado para edição", controladorClonado.isBloqueado());
            assertFalse("Planos não deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());

            assertEquals("Total de Versão Plano", totalVersoesPlano * i, VersaoPlano.find.findRowCount());
            assertEquals("Total de Versão Tabela Horária", totalVersoesTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());

            request = new Http.RequestBuilder().method("POST")
                .uri(routes.PlanosController.create().url())
                .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado, Cidade.find.all(), RangeUtils.getInstance(null)));

            result = route(request);
            json = Json.parse(Helpers.contentAsString(result));
            assertEquals(OK, result.status());
            controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Versão Plano", totalVersoesPlano * i, VersaoPlano.find.findRowCount());
            assertEquals("Total de Versão Tabela Horária", totalVersoesTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());

            controladorClonado.finalizar();
            assertFalse("Controlador não deveria estar bloqueado para edição", controladorClonado.isBloqueado());
            assertFalse("Planos não deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());
        }
    }

    @Test
    public void deveriaClonarTabelaHorariaEditarTabelaHoraria() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();

        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        int totalTabelaHoraria = 1;
        int totalEventos = 3;

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria, TabelaHorario.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos, Evento.find.findRowCount());
        assertFalse(controlador.isBloqueado());
        assertFalse(controlador.isPlanosBloqueado());

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        Result result = route(request);
        assertEquals(OK, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertTrue(controladorClonado.isBloqueado());
        assertTrue(controladorClonado.isPlanosBloqueado());

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * 2, TabelaHorario.find.findRowCount());
        assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * 2, VersaoTabelaHoraria.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos * 2, Evento.find.findRowCount());

        if (controlador.getTabelaHoraria() != null) {
            assertFields(controlador.getTabelaHoraria(), controladorClonado.getTabelaHoraria());
            for (Evento evento : controladorClonado.getTabelaHoraria().getEventos()) {
                Evento eventoClonado = controladorClonado.getTabelaHoraria().getEventos().stream().filter(aux -> aux.getPosicao().equals(evento.getPosicao())).findFirst().orElse(null);
                assertFields(evento, eventoClonado);
            }
        }

        controladorClonado.update();

        assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * 2, VersaoTabelaHoraria.find.findRowCount());
        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * 2, TabelaHorario.find.findRowCount());


        request = new Http.RequestBuilder().method("POST")
            .uri(routes.TabelaHorariosController.create().url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado, Cidade.find.all(), RangeUtils.getInstance(null)));

        result = route(request);
        assertEquals(OK, result.status());
        json = Json.parse(Helpers.contentAsString(result));
        controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        controladorClonado.finalizar();

        assertFalse(controladorClonado.isBloqueado());
        assertFalse(controladorClonado.isPlanosBloqueado());
    }

    @Test
    public void deveriaClonar5VersoesTabelaHoraria() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();

        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        int totalTabelaHoraria = 1;
        int totalEventos = 3;

        assertEquals("Total de Tabelas Horarias", totalTabelaHoraria, TabelaHorario.find.findRowCount());
        assertEquals("Total de Eventos", totalEventos, Evento.find.findRowCount());

        assertFalse("Controlador não deveria estar bloqueado para edição", controlador.isBloqueado());
        assertFalse("Planos não deveriam estar bloqueado para edição", controlador.isPlanosBloqueado());

        for (int i = 2; i < 7; i++) {
            Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url())
                .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

            Result result = route(request);
            assertEquals(OK, result.status());

            JsonNode json = Json.parse(Helpers.contentAsString(result));
            Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertTrue("Controlador deveria estar bloqueado para edição", controladorClonado.isBloqueado());
            assertTrue("Planos deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());

            assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * i, TabelaHorario.find.findRowCount());
            assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());
            assertEquals("Total de Eventos", totalEventos * i, Evento.find.findRowCount());

            request = new Http.RequestBuilder().method("POST")
                .uri(routes.TabelaHorariosController.create().url())
                .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado, Cidade.find.all(), RangeUtils.getInstance(null)));

            result = route(request);
            json = Json.parse(Helpers.contentAsString(result));
            assertEquals(OK, result.status());
            controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

            assertEquals("Total de Versão Tabelas Horarias", totalTabelaHoraria * i, VersaoTabelaHoraria.find.findRowCount());
            assertEquals("Total de Tabelas Horarias", totalTabelaHoraria * i, TabelaHorario.find.findRowCount());

            controladorClonado.finalizar();

            assertFalse("Controlador não deveria estar bloqueado para edição", controladorClonado.isBloqueado());
            assertFalse("Planos não deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());
        }
    }

    @Test
    public void naoDeveriaFinalizarControladorSemSMEE() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setNumeroSMEE(null);

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador,
            javax.validation.groups.Default.class, ControladorFinalizaConfiguracaoCheck.class);

        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("Controlador", "O controlador não pode ser finalizado sem o número do SMEE preenchido.", "numeroSmeePreenchido")
        ));

        controlador.setExclusivoParaTeste(true);
        erros = new InfluuntValidator<Controlador>().validate(controlador,
            javax.validation.groups.Default.class, ControladorFinalizaConfiguracaoCheck.class);
        assertEquals(0, erros.size());
    }

    @Test
    public void deveriaClonarPlanosAnelCom2EstagiosEAtualizarPlano() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.update();
        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        int totalEstagiosPlanos = 6;
        int totalPlanos = 2;
        int totalGruposSemaforicosPlanos = 4;
        int totalTabelasHorarias = TabelaHorario.find.findRowCount();
        int totalEventos = Evento.find.findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        Result result = route(request);
        assertEquals(OK, result.status());
        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        Anel anelCom2Estagios = controladorClonado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();

        Plano plano = anelCom2Estagios.getVersaoPlano().getPlanos().get(0);
        plano.setDescricao("Nova Descricao");

        request = new Http.RequestBuilder().method("POST")
            .uri(routes.PlanosController.create().url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controladorClonado, Cidade.find.all(), RangeUtils.getInstance(null)));

        result = route(request);
        assertEquals(OK, result.status());

        assertEquals("Total de Estagios Planos", totalEstagiosPlanos * 2, Ebean.find(EstagioPlano.class).findRowCount());
        assertEquals("Total de Planos", totalPlanos * 2, Plano.find.findRowCount());
        assertEquals("Total de GrupoSemaforicoPlano", totalGruposSemaforicosPlanos * 2, Ebean.find(GrupoSemaforicoPlano.class).findRowCount());
        assertEquals("Total Tabelas Horárias", totalTabelasHorarias * 2, TabelaHorario.find.findRowCount());
        assertEquals("Total Eventos", totalEventos * 2, Evento.find.findRowCount());
    }

    @Test
    public void deveriaCancelarTabelaHorariaClonada() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.ativar();

        int totalTabelaHorarias = Ebean.find(TabelaHorario.class).findRowCount();
        int totalVersoesTabelasHorarias = Ebean.find(VersaoTabelaHoraria.class).findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarTabelaHoraria(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals("Total Tabela Horarias", totalTabelaHorarias * 2, Ebean.find(TabelaHorario.class).findRowCount());
        assertEquals("Total Versoes Tabela Horarias", totalVersoesTabelasHorarias * 2, Ebean.find(VersaoTabelaHoraria.class).findRowCount());
        assertTrue("Controlador deveria estar bloqueado para edição", controladorClonado.isBloqueado());
        assertTrue("Planos deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());

        TabelaHorario tabela = controladorClonado.getTabelaHoraria();
        request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.TabelaHorariosController.cancelarEdicao(tabela.getId().toString()).url());
        result = route(request);
        assertEquals(200, result.status());

        controladorClonado.refresh();
        assertEquals("Total Tabela Horarias", totalTabelaHorarias, Ebean.find(TabelaHorario.class).findRowCount());
        assertEquals("Total Versoes Tabela Horarias", totalVersoesTabelasHorarias, Ebean.find(VersaoTabelaHoraria.class).findRowCount());
        assertFalse("Controlador não deveria estar bloqueado para edição", controladorClonado.isBloqueado());
        assertFalse("Planos não deveriam estar bloqueado para edição", controladorClonado.isPlanosBloqueado());
    }

    @Test
    public void deveriaAssociarAgrupamentoQuandoAtivar() {
        Controlador controlador = controladorTestUtils.getControladorAgrupamentos();
        controlador.update();


        // primeira ativação
        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
            .uri(routes.ControladoresController.ativar(controlador.getId().toString()).url());
        Result result = route(request);
        assertEquals(200, result.status());

        // criar nova versão do controlador
        request = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.edit(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        Agrupamento.find.findList().forEach(agrupamento -> {
            agrupamento.getAneis().forEach(anelAgrupamento -> {

                Anel anelControlador = controlador.getAneis().stream()
                    .filter(anel -> anel.getId().equals(anelAgrupamento.getId()))
                    .findFirst()
                    .orElse(null);
                // anel antigo deve continuar associado ao controlador
                assertNotNull(anelControlador);

                Anel anelClonado = controladorClonado.getAneis().stream()
                    .filter(anel -> anel.getId().equals(anelAgrupamento.getId()))
                    .findFirst()
                    .orElse(null);
                // anel clonado NÃO deve estar associado ao controlador
                // após o clone
                assertNull(anelClonado);

            });
        });

        // ativar nova versão
        request = new Http.RequestBuilder().method("PUT")
            .uri(routes.ControladoresController.ativar(controladorClonado.getId().toString()).url());
        result = route(request);
        assertEquals(200, result.status());

        Controlador controladorAtivo = Controlador.find.byId(controladorClonado.getId());

        Agrupamento.find.findList().forEach(agrupamento -> {
            agrupamento.getAneis().forEach(anelAgrupamento -> {

                Anel anelControlador = controlador.getAneis().stream()
                    .filter(anel -> anel.getId().equals(anelAgrupamento.getId()))
                    .findFirst()
                    .orElse(null);
                // anel antigo deve ser removido do agrupamento
                // ao ativar o controlador
                assertNull(anelControlador);

                Anel anelClonado = controladorAtivo.getAneis().stream()
                    .filter(anel -> anel.getId().equals(anelAgrupamento.getId()))
                    .findFirst()
                    .orElse(null);
                // anel clonado deve estar associado ao controlador
                // após a ativação
                assertNotNull(anelClonado);

            });
        });
    }

    private <T> void assertFields(T origem, T destino) {
        for (Field field : origem.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Ignore.class) == null) {
                field.setAccessible(true);
                try {
                    if (field.get(origem) == null || Modifier.isFinal(field.getModifiers()) || field.getType().equals(UUID.class)
                        || field.getType().equals(DateTime.class) || field.getType().equals(Fabricante.class) || field.getType().equals(Cidade.class)
                        || field.getType().equals(DiaDaSemana.class) || "idJson".equals(field.getName())) {
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
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return null;
    }
}
