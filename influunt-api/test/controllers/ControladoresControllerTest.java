package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import scala.collection.script.End;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 8/10/16.
 */
public class ControladoresControllerTest extends WithApplication {

    private static ControladorTestUtil controladorTestUtils;

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
    public void setUpModels() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        controladorTestUtils = new ControladorTestUtil(cidade, area, fabricante, modeloControlador);
    }

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
    public void deveriaClonar() {
        Controlador controlador = controladorTestUtils.getControladorTabelaHorario();
        controlador.setStatusControlador(StatusControlador.ATIVO);
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.ControladoresController.edit(controlador.getId().toString()).url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));

        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorClonado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals(200, postResult.status());
        assertNotNull("ID Controldor Clonado", controladorClonado.getId());
        assertNotEquals("Teste de Id", controlador.getId(), controladorClonado.getId());
        assertEquals("Teste de Aneis", controlador.getAneis().size(), controladorClonado.getAneis().size());
        assertEquals("Teste de Agrupamentos", controlador.getAgrupamentos().size(), controladorClonado.getAgrupamentos().size());
        assertEquals("Teste de Area", controlador.getArea(), controladorClonado.getArea());
        assertEquals("Teste de Modelo", controlador.getModelo(), controladorClonado.getModelo());
        assertFields(controlador, controladorClonado);

        controlador.getAneis().forEach(anel -> {
            Anel anelClonado = controladorClonado.getAneis().stream().filter(anelAux -> anelAux.getIdJson().equals(anel.getIdJson())).findFirst().orElse(null);
            assertFields(anel, anelClonado);
            assertEquals("Teste Anel | Endereços", anel.getEnderecos().size(), anelClonado.getEnderecos().size());
            assertEquals("Teste Anel | Estagio", anel.getEstagios().size(), anelClonado.getEstagios().size());
            assertEquals("Teste Anel | Detectores", anel.getDetectores().size(), anelClonado.getDetectores().size());
            assertEquals("Teste Anel | Grupo Semaforicos", anel.getGruposSemaforicos().size(), anelClonado.getGruposSemaforicos().size());
            assertEquals("Teste Anel | Plano", anel.getPlanos().size(), anelClonado.getPlanos().size());

            anel.getEnderecos().forEach(origem -> {
                Endereco destino = anelClonado.getEnderecos().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
                assertFields(origem, destino);
            });

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
                    assertFields(transicao, transicaoClonada);

                    transicao.getTabelaEntreVerdesTransicoes().forEach(tvt -> {
                        TabelaEntreVerdesTransicao tvtClonada = transicaoClonada.getTabelaEntreVerdesTransicoes().stream().filter(aux -> aux.getIdJson().equals(tvt.getIdJson())).findFirst().orElse(null);
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Tabela Entre Verde: ", tvt.getTabelaEntreVerdes().getIdJson(), tvtClonada.getTabelaEntreVerdes().getIdJson());
                        assertEquals("Teste Anel | Grupo Smaforico | Tabela Entre Verdes | Tabela Entre Verde Transicao | Transicao: ", tvt.getTransicao().getIdJson(), tvtClonada.getTransicao().getIdJson());
                        assertFields(tvt, tvtClonada);
                    });
                });
            });

            anel.getPlanos().forEach(origem -> {
                Plano destino = anelClonado.getPlanos().stream().filter(aux -> aux.getIdJson().equals(origem.getIdJson())).findFirst().orElse(null);
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

            if(anel.getTabelaHorario() != null) {
                assertFields(anel.getTabelaHorario(), anelClonado.getTabelaHorario());
                anel.getTabelaHorario().getEventos().forEach(evento -> {
                    Evento eventoClonado = anelClonado.getTabelaHorario().getEventos().stream().filter(aux -> aux.getIdJson().equals(evento.getIdJson())).findFirst().orElse(null);
                    assertFields(evento, eventoClonado);
                });
            }
        });
    }


    private <T> void assertFields(T origem, T destino) {
        for (Field field : origem.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(origem) == null || Modifier.isFinal(field.getModifiers()) || field.getType().equals(UUID.class) || field.getType().equals(DateTime.class) || field.getType().equals(Fabricante.class) || field.getType().equals(Cidade.class)) {
                    continue;
                }
                if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                    if (field.getType().isPrimitive() || field.getType().isEnum() ||
                            field.getType().equals(String.class) || field.getType().equals(Integer.class)) {

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
