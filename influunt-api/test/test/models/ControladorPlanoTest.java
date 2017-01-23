package test.models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCentralCheck;
import checks.PlanosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
public class ControladorPlanoTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O anel ativo deve ter pelo menos 1 plano configurado.", "aneis[0].aoMenosUmPlanoConfigurado"),
            new Erro(CONTROLADOR, "O anel ativo deve ter pelo menos 1 plano configurado.", "aneis[1].aoMenosUmPlanoConfigurado")
        ));

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        VersaoPlano versaoPlanoAnel2Estagios = new VersaoPlano(anelCom2Estagios, usuario);
        versaoPlanoAnel2Estagios.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
        anelCom2Estagios.addVersaoPlano(versaoPlanoAnel2Estagios);

        VersaoPlano versaoPlanoAnel4Estagios = new VersaoPlano(anelCom4Estagios, usuario);
        versaoPlanoAnel4Estagios.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
        anelCom4Estagios.addVersaoPlano(versaoPlanoAnel4Estagios);

        Plano plano1Anel2 = new Plano();
        versaoPlanoAnel2Estagios.addPlano(plano1Anel2);
        plano1Anel2.setVersaoPlano(versaoPlanoAnel2Estagios);

        Plano plano1Anel4 = new Plano();
        versaoPlanoAnel4Estagios.addPlano(plano1Anel4);
        plano1Anel4.setVersaoPlano(versaoPlanoAnel4Estagios);


        erros = getErros(controlador);
        assertEquals(10, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.ATUADO);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[1].versaoPlano.planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")

        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.APAGADO);

        erros = getErros(controlador);
        assertEquals(9, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setTempoCiclo(60);
        plano1Anel2.setDefasagem(100);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Defasagem deve estar entre {min} e o tempo de ciclo", "aneis[1].versaoPlano.planos[0].defasagem"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versaoPlano.planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versaoPlano.planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setTempoCiclo(null);
        plano1Anel2.setDefasagem(null);

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano1Anel2.setPosicao(1);
        plano1Anel2.setDescricao("Principal");
        plano1Anel2.setPosicaoTabelaEntreVerde(1);

        for (int i = 1; i < anelCom2Estagios.getGruposSemaforicos().size(); i++) {
            GrupoSemaforico grupoSemaforico = anelCom2Estagios.getGruposSemaforicos().get(i);
            GrupoSemaforicoPlano grupoPlano = new GrupoSemaforicoPlano();
            grupoPlano.setAtivado(true);
            grupoPlano.setPlano(plano1Anel2);
            grupoPlano.setGrupoSemaforico(grupoSemaforico);
            plano1Anel2.addGruposSemaforicoPlano(grupoPlano);
        }

        plano1Anel4.setModoOperacao(ModoOperacaoPlano.ATUADO);
        plano1Anel4.setPosicao(1);
        plano1Anel4.setDescricao("Principal");
        plano1Anel4.setPosicaoTabelaEntreVerde(1);

        criarGrupoSemaforicoPlano(anelCom4Estagios, plano1Anel4);

        GrupoSemaforicoPlano grupoPlano = new GrupoSemaforicoPlano();
        grupoPlano.setAtivado(true);
        grupoPlano.setPlano(plano1Anel4);
        grupoPlano.setGrupoSemaforico(anelCom4Estagios.getGruposSemaforicos().get(0));
        plano1Anel4.addGruposSemaforicoPlano(grupoPlano);

        erros = getErros(controlador);
        assertEquals(10, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[2].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[1].versaoPlano.planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versaoPlano.planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versaoPlano.planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")

        ));

        criarGrupoSemaforicoPlano(anelCom2Estagios, plano1Anel2);
        criarGrupoSemaforicoPlano(anelCom4Estagios, plano1Anel4);

        erros = getErros(controlador);
        assertEquals(7, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versaoPlano.planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versaoPlano.planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.", "aneis[1].versaoPlano.planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 1, 1, 1});

        erros = getErros(controlador);
        assertEquals(22, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versaoPlano.planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versaoPlano.planos[0].posicaoUnicaEstagio")
        ));

        Estagio estagio = anelCom4Estagios.getEstagios().stream().filter(estagio1 -> estagio1.getPosicao().equals(4)).findFirst().get();
        criarDetector(anelCom4Estagios, TipoDetector.VEICULAR, 4, false);
        Detector detector = anelCom4Estagios.getDetectores().get(3);
        detector.setDescricao("D4");
        detector.setEstagio(estagio);
        estagio.setDetector(detector);

        erros = getErros(controlador);
        assertEquals(21, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versaoPlano.planos[0].posicaoUnicaEstagio")
        ));

        EstagioPlano estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        EstagioPlano estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        EstagioPlano estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().get(0);
        EstagioPlano estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().get(1);
        EstagioPlano estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().get(2);
        EstagioPlano estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().get(3);

        estagioPlano1Anel2.setTempoVerde(300);
        plano1Anel2.setTempoCiclo(300);
        estagioPlano2Anel2.setTempoVerde(300);

        estagioPlano1Anel4.setTempoVerdeMinimo(300);
        estagioPlano1Anel4.setTempoVerdeMaximo(300);
        estagioPlano1Anel4.setTempoVerdeIntermediario(300);
        estagioPlano1Anel4.setTempoExtensaoVerde(300.00);

        estagioPlano2Anel4.setTempoVerdeMinimo(300);
        estagioPlano2Anel4.setTempoVerdeMaximo(300);
        estagioPlano2Anel4.setTempoVerdeIntermediario(300);
        estagioPlano2Anel4.setTempoExtensaoVerde(300.00);

        estagioPlano3Anel4.setTempoVerdeMinimo(300);
        estagioPlano3Anel4.setTempoVerdeMaximo(300);
        estagioPlano3Anel4.setTempoVerdeIntermediario(300);
        estagioPlano3Anel4.setTempoExtensaoVerde(300.00);

        estagioPlano4Anel4.setTempoVerdeMinimo(300);
        estagioPlano4Anel4.setTempoVerdeMaximo(300);
        estagioPlano4Anel4.setTempoVerdeIntermediario(300);
        estagioPlano4Anel4.setTempoExtensaoVerde(300.00);


        erros = getErros(controlador);
        assertEquals(21, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versaoPlano.planos[0].posicaoUnicaEstagio")
        ));

        estagioPlano1Anel2.setTempoVerde(0);
        plano1Anel2.setTempoCiclo(0);
        estagioPlano2Anel2.setTempoVerde(0);

        estagioPlano1Anel4.setTempoVerdeMinimo(0);
        estagioPlano1Anel4.setTempoVerdeMaximo(0);
        estagioPlano1Anel4.setTempoVerdeIntermediario(0);
        estagioPlano1Anel4.setTempoExtensaoVerde(0.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(0);
        estagioPlano2Anel4.setTempoVerdeMaximo(0);
        estagioPlano2Anel4.setTempoVerdeIntermediario(0);
        estagioPlano2Anel4.setTempoExtensaoVerde(0.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(0);
        estagioPlano3Anel4.setTempoVerdeMaximo(0);
        estagioPlano3Anel4.setTempoVerdeIntermediario(0);
        estagioPlano3Anel4.setTempoExtensaoVerde(0.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(0);
        estagioPlano4Anel4.setTempoVerdeMaximo(0);
        estagioPlano4Anel4.setTempoVerdeIntermediario(0);
        estagioPlano4Anel4.setTempoExtensaoVerde(0.0);


        erros = getErros(controlador);
        assertEquals(25, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versaoPlano.planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versaoPlano.planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[1].versaoPlano.planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[1].versaoPlano.planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 2});

        estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        estagioPlano1Anel2.getEstagio().setTempoMaximoPermanenciaAtivado(true);
        estagioPlano1Anel2.getEstagio().setTempoMaximoPermanencia(60);
        estagioPlano1Anel2.setTempoVerde(70);
        plano1Anel2.setTempoCiclo(40);
        estagioPlano2Anel2.setTempoVerde(20);

        estagioPlano1Anel4.setTempoVerdeMinimo(30);
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(40);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(30);
        estagioPlano2Anel4.setTempoVerdeIntermediario(25);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(30);
        estagioPlano3Anel4.setTempoVerdeIntermediario(25);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(30);
        estagioPlano4Anel4.setTempoVerdeIntermediario(25);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertEquals(5, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de verde mínimo deve ser maior que o verde de segurança e menor que o verde máximo.", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeMinimoFieldMenorMaximo"),
            new Erro(CONTROLADOR, "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].tempoVerdeIntermediarioFieldEntreMinimoMaximo"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "O tempo de estagio ultrapassa o tempo máximo de permanência.", "aneis[1].versaoPlano.planos[0].estagiosPlanos[0].ultrapassaTempoMaximoPermanencia"),
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[1].versaoPlano.planos[0].ultrapassaTempoCiclo")
        ));

        estagioPlano1Anel2.setTempoVerde(21);
        plano1Anel2.setTempoCiclo(60);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(25);
        estagioPlano1Anel4.setTempoVerdeMaximo(30);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].posicaoUnicaEstagio")
        ));

        estagioPlano1Anel4.setTempoVerdeMinimo(20);

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 2});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 2, 3, 4});

        estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().get(0);
        estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().get(1);
        estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().get(2);
        estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().get(3);

        estagioPlano1Anel2.setTempoVerde(21);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeMaximo(21);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(21);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(21);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(21);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].sequenciaValida")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{2, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 3, 2, 4});

        estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().get(0);
        estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().get(1);
        estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().get(2);
        estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().get(3);

        estagioPlano1Anel2.setTempoVerde(21);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeMaximo(21);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(21);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(21);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(21);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano1Anel2.setDispensavel(true);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].sequenciaValida")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{2, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 4, 3, 2});

        estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().get(0);
        estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().get(1);
        estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().get(2);
        estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().get(3);

        estagioPlano1Anel2.setTempoVerde(21);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeMaximo(21);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(21);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(21);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(21);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertThat(erros, empty());

        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 4, 3, 2});
        estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(1)).findAny().get();
        estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(2)).findAny().get();
        estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(3)).findAny().get();
        estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(4)).findAny().get();

        plano1Anel4.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano1Anel4.setTempoCiclo(128);
        plano1Anel4.setDefasagem(10);
        estagioPlano1Anel4.setTempoVerde(10);
        estagioPlano2Anel4.setTempoVerde(10);
        estagioPlano3Anel4.setTempoVerde(10);
        estagioPlano4Anel4.setTempoVerde(10);
        estagioPlano1Anel4.setDispensavel(true);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O estágio dispensável não pode ser o primeiro estágio da sequência.", "aneis[0].versaoPlano.planos[0].estagiosPlanos[0].primeiroEstagioNaoDispensavel")
        ));


        estagioPlano1Anel4.setDispensavel(false);
        erros = getErros(controlador);
        assertThat(erros, empty());

        estagioPlano4Anel4.setDispensavel(true);
        estagioPlano4Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano1Anel4);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O estágio que recebe o tempo do estágio dispensável deve ser o anterior, pois esse estágio é o último da sequência.", "aneis[0].versaoPlano.planos[0].estagiosPlanos[1].estagioQueRecebeEstagioDispensavelEAnterior")
        ));

        estagioPlano4Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano3Anel4);

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.MANUAL);
        plano1Anel4.setModoOperacao(ModoOperacaoPlano.MANUAL);
        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[0].versaoPlano.planos[0].numeroEstagiosEmModoManualOk"),
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[1].versaoPlano.planos[0].numeroEstagiosEmModoManualOk")
        ));


        plano1Anel4.getEstagiosPlanos().remove(0);
        plano1Anel4.getEstagiosPlanos().remove(1);

        estagioPlano4Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano2Anel4);

        erros = getErros(controlador);
        assertThat(erros, empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorPlanos();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertNotNull(controlador.getId());
        assertThat(erros, empty());

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Plano plano = anelCom2Estagios.getPlanos().get(0);
        assertEquals("Anel 2 estágios possui 1 plano", 1, anelCom2Estagios.getPlanos().size());
        assertEquals("Anel 2 estágios possui 1 plano com 2 estagios", 2, plano.getEstagiosPlanos().size());
        assertEquals("Anel 2 estágios possui 1 plano com 2 grupos", 2, plano.getGruposSemaforicosPlanos().size());


        plano = anelCom4Estagios.getPlanos().get(0);
        assertEquals("Anel 2 estágios possui 1 plano", 1, anelCom4Estagios.getPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano com 4 estagios", 4, plano.getEstagiosPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano com 2 grupos", 2, plano.getGruposSemaforicosPlanos().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Anel 2 estágios possui 1 plano", 1, anelCom2Estagios.getPlanos().size());
        assertEquals("Anel 2 estágios possui 1 plano com 2 estagios", 2, anelCom2Estagios.getPlanos().get(0).getEstagiosPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano", 1, anelCom4Estagios.getPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano com 4 estagios", 4, anelCom4Estagios.getPlanos().get(0).getEstagiosPlanos().size());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(controllers.routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(2, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorPlanos();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(controllers.routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(OK, postResult.status());

        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);

        assertEquals(controlador.getId(), controladorRetornado.getId());
        assertNotNull(controladorRetornado.getId());

        Anel anelCom2Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Anel 2 estágios possui 1 plano", 1, anelCom2Estagios.getPlanos().size());
        assertEquals("Anel 2 estágios possui 1 plano com 2 estagios", 2, anelCom2Estagios.getPlanos().get(0).getEstagiosPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano", 1, anelCom4Estagios.getPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano com 4 estagios", 4, anelCom4Estagios.getPlanos().get(0).getEstagiosPlanos().size());
    }

    @Test
    public void testVerdeSeguranca() {
        Controlador controlador = getControladorPlanos();
        controlador.save();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        controlador.getAneis().stream().forEach(anel -> {
            if (!anel.getId().equals(anelCom4Estagios.getId())) {
                anel.setAtivo(false);
            }
        });

        Plano plano = new Plano();
        plano.setVersaoPlano(anelCom4Estagios.getVersaoPlanoAtivo());
        anelCom4Estagios.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano));

        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano.setPosicao(3);
        plano.setDescricao("Principal");
        plano.setPosicaoTabelaEntreVerde(1);
        plano.setTempoCiclo(110);

        criarGrupoSemaforicoPlano(anelCom4Estagios, plano);

        criarEstagioPlano(anelCom4Estagios, plano, new int[]{1, 2, 3, 4});

        EstagioPlano estagioPlano1 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(1)).findAny().get();
        EstagioPlano estagioPlano2 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(2)).findAny().get();
        EstagioPlano estagioPlano3 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(3)).findAny().get();
        EstagioPlano estagioPlano4 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(4)).findAny().get();

        //1 - 4 - 3 - 2
        estagioPlano1.setPosicao(1);
        estagioPlano2.setPosicao(4);
        estagioPlano3.setPosicao(3);
        estagioPlano4.setPosicao(2);

        estagioPlano1.setTempoVerde(5);
        estagioPlano2.setTempoVerde(6);
        estagioPlano3.setTempoVerde(5);
        estagioPlano4.setTempoVerde(6);

        List<Erro> erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //3 - 1 - 4 - 2
        estagioPlano1.setPosicao(2);
        estagioPlano2.setPosicao(4);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(3);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[0].versaoPlano.planos[0].ultrapassaTempoCiclo")
        ));

        //3 - 2 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(2);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde deve ser maior que o tempo de segurança configurado.", "aneis[0].versaoPlano.planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //2 - 3 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(1);
        estagioPlano3.setPosicao(2);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[0].versaoPlano.planos[0].ultrapassaTempoCiclo")
        ));
    }

    @Test
    public void sequenciaEstagio() {
        Controlador controlador = getControladorPlanos();
        controlador.save();
        Anel anel = controlador.getAneis().stream().filter(anel1 -> anel1.isAtivo() && anel1.getEstagios().size() == 4).findFirst().get();
        Plano plano = anel.getPlanos().get(0);

        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano.setTempoCiclo(128);

        criarEstagioPlano(anel, plano, new int[]{1, 3, 2, 4});
        EstagioPlano estagioPlano1 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(1)).findAny().get();
        EstagioPlano estagioPlano2 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(2)).findAny().get();
        EstagioPlano estagioPlano3 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(3)).findAny().get();
        EstagioPlano estagioPlano4 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(4)).findAny().get();

        estagioPlano1.setTempoVerde(10);
        estagioPlano2.setTempoVerde(10);
        estagioPlano3.setTempoVerde(10);
        estagioPlano4.setTempoVerde(10);

        List<Erro> erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versaoPlano.planos[0].sequenciaValida")
        ));

        criarEstagioPlano(anel, plano, new int[]{1, 4, 3, 2});
        estagioPlano1 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(1)).findAny().get();
        estagioPlano2 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(2)).findAny().get();
        estagioPlano3 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(3)).findAny().get();
        estagioPlano4 = plano.getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getPosicao().equals(4)).findAny().get();

        estagioPlano1.setTempoVerde(10);
        estagioPlano2.setTempoVerde(10);
        estagioPlano3.setTempoVerde(10);
        estagioPlano4.setTempoVerde(10);

        erros = getErros(controlador);
        assertThat(erros, empty());

        estagioPlano2.setDispensavel(true);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A sequência de estágios não é válida, pois existe uma transição proibida devido à não execução do estágio dispensável.", "aneis[0].versaoPlano.planos[0].sequenciaInvalidaSeExisteEstagioDispensavel")
        ));
    }

    @Test
    public void testCancelaEdicaoPlano() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.finalizar();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        VersaoPlano versaoPlano = anelCom2Estagios.getVersaoPlano();
        Plano plano = versaoPlano.getPlanos().get(0);

        int totalPlanos = Plano.find.findRowCount();
        int totalVersoesPlanos = VersaoPlano.find.findRowCount();
        int totalTabelasHorarias = TabelaHorario.find.findRowCount();
        int totalVersoesTabelaHoraria = VersaoTabelaHoraria.find.findRowCount();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
            .uri(routes.ControladoresController.editarPlanos(controlador.getId().toString()).url())
            .bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result result = route(request);

        assertEquals(200, result.status());
        assertEquals("Total Planos", totalPlanos * 2, Plano.find.findRowCount());
        assertEquals("Total Versões Planos", totalVersoesPlanos * 2, VersaoPlano.find.findRowCount());
        assertEquals("Total Tabelas Horárias", totalTabelasHorarias * 2, TabelaHorario.find.findRowCount());
        assertEquals("Total Versões Tabelas Horárias", totalVersoesTabelaHoraria * 2, VersaoTabelaHoraria.find.findRowCount());

        request = new Http.RequestBuilder().method("DELETE")
            .uri(routes.PlanosController.cancelarEdicao(plano.getId().toString()).url());
        result = route(request);

        assertEquals(200, result.status());
        assertEquals("Total Planos", totalPlanos, Plano.find.findRowCount());
        assertEquals("Total Versões Planos", totalVersoesPlanos, VersaoPlano.find.findRowCount());
        assertEquals("Total Tabelas Horárias", totalTabelasHorarias, TabelaHorario.find.findRowCount());
        assertEquals("Total Versões Tabelas Horárias", totalVersoesTabelaHoraria, VersaoTabelaHoraria.find.findRowCount());
    }


    @Test
    public void testControlerDestroyEstagioPlano() {
        Controlador controlador = getControladorPlanos();

        Anel anel1 = controlador.getAneis().stream().filter(Anel::isAtivo).sorted((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao())).findFirst().orElse(null);
        VersaoPlano versao1 = anel1.getVersoesPlanos().get(0);
        Plano plano1 = versao1.getPlanos().stream().sorted((p1, p2) -> p1.getPosicao().compareTo(p2.getPosicao())).findFirst().orElse(null);
        int totalEstagiosPlanos = plano1.getEstagiosPlanos().size();

        EstagioPlano ep = plano1.getEstagiosPlanos().get(1);

        JsonNode controladorJson = new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null));
        for (JsonNode epNode : controladorJson.get("estagiosPlanos")) {
            if (Objects.equals(epNode.get("idJson").asText(), ep.getIdJson())) {
                ((ObjectNode) epNode).put("destroy", true);
            }
        }

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
            .uri(routes.PlanosController.create().url())
            .bodyJson(controladorJson);
        Result result = route(request);
        assertEquals(200, result.status());

        JsonNode json = Json.parse(Helpers.contentAsString(result));
        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson(json);
        Anel anel2 = controladorRetornado.getAneis().stream().filter(Anel::isAtivo).sorted((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao())).findFirst().orElse(null);
        VersaoPlano versao2 = anel2.getVersoesPlanos().get(0);
        Plano plano2 = versao2.getPlanos().stream().sorted((p1, p2) -> p1.getPosicao().compareTo(p2.getPosicao())).findFirst().orElse(null);

        assertEquals(totalEstagiosPlanos - 1, plano2.getEstagiosPlanos().size());
    }

    @Test
    public void testDestroyEstagioPlanoModoManual() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        List<Erro> erros = getErros(controlador);
        assertNotNull(controlador.getId());
        assertThat(erros, empty());

        List<Anel> aneisManuais = controlador.getAneis().stream().filter(a -> a.isAtivo() && a.isAceitaModoManual()).collect(Collectors.toList());
        Anel anelManual1 = aneisManuais.get(0);
        Anel anelManual2 = aneisManuais.get(1);

        Plano planoManual1 = anelManual1.getPlanos().get(0);
        planoManual1.setModoOperacao(ModoOperacaoPlano.MANUAL);

        Plano planoManual2 = anelManual2.getPlanos().get(0);
        planoManual2.setModoOperacao(ModoOperacaoPlano.MANUAL);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[0].versaoPlano.planos[0].numeroEstagiosEmModoManualOk"),
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[1].versaoPlano.planos[0].numeroEstagiosEmModoManualOk")
        ));

        planoManual1.getEstagiosPlanos().get(0).setDestroy(true);
        planoManual1.getEstagiosPlanos().get(1).setDestroy(true);

        erros = getErros(controlador);
        assertEquals(0, erros.size());
    }

    @Test
    public void testCicloSimplesSimetricoOuAssimetrico() {
        EstagioPlano estagioPlano;
        Controlador controlador1 = getControladorPlanos();
        Controlador controlador2 = getControladorPlanos();

        Anel anel1 = controlador1.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Anel anel2 = controlador2.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        GrupoSemaforico g1 = anel1.findGrupoSemaforicoByPosicao(1);
        g1.setTempoVerdeSeguranca(4);

        Plano plano1 = new Plano();
        plano1.setVersaoPlano(anel1.getVersaoPlanoAtivo());
        anel1.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano1));

        plano1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano1.setPosicao(1);
        plano1.setDescricao("Principal");
        plano1.setPosicaoTabelaEntreVerde(1);
        plano1.setTempoCiclo(130);

        criarGrupoSemaforicoPlano(anel1, plano1);

        criarEstagioPlano(anel1, plano1, new int[]{1, 4, 3, 2}, new int[]{10, 12, 10, 10});

        Plano plano2 = new Plano();
        plano2.setVersaoPlano(anel2.getVersaoPlanoAtivo());
        anel2.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano2));

        plano2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano2.setPosicao(1);
        plano2.setDescricao("Principal");
        plano2.setPosicaoTabelaEntreVerde(1);
        plano2.setTempoCiclo(160);

        criarGrupoSemaforicoPlano(anel2, plano2);

        criarEstagioPlano(anel2, plano2, new int[]{1, 4, 3, 2}, new int[]{20, 12, 20, 20});

        controlador1.update();
        controlador2.update();

        List<Erro> erros = getErros(controlador1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico nessa subárea para todos os planos de mesma numeração.", "aneis[0].versaoPlano.planos[0].tempoCicloIgualOuMultiploDeTodoPlano")
        ));

        erros = getErros(controlador2);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico nessa subárea para todos os planos de mesma numeração.", "aneis[0].versaoPlano.planos[0].tempoCicloIgualOuMultiploDeTodoPlano")
        ));

        //Teste Json
        Controlador controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));
        Controlador controladorJson2 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador2, Cidade.find.all(), RangeUtils.getInstance(null)));

        erros = getErros(controladorJson1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico nessa subárea para todos os planos de mesma numeração.", "aneis[0].versaoPlano.planos[0].tempoCicloIgualOuMultiploDeTodoPlano")
        ));

        erros = getErros(controladorJson2);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico nessa subárea para todos os planos de mesma numeração.", "aneis[0].versaoPlano.planos[0].tempoCicloIgualOuMultiploDeTodoPlano")
        ));

        plano1.setTempoCiclo(124);
        estagioPlano = plano1.getEstagiosOrdenados().get(2);
        estagioPlano.setTempoVerde(4);

        plano2.setTempoCiclo(248);
        criarEstagioPlano(anel2, plano2, new int[]{1, 4, 3, 2}, new int[]{50, 20, 50, 40});

        controlador1.update();
        controlador2.update();

        erros = getErros(controlador1);
        assertTrue(erros.isEmpty());

        erros = getErros(controlador2);
        assertTrue(erros.isEmpty());

        //Teste Json
        controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));
        controladorJson2 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador2, Cidade.find.all(), RangeUtils.getInstance(null)));

        erros = getErros(controladorJson1);
        assertTrue(erros.isEmpty());

        erros = getErros(controladorJson2);
        assertTrue(erros.isEmpty());
    }

    @Test
    public void testCicloDuplo() {
        Controlador controlador1 = getControladorPlanos();
        Anel anel1 = controlador1.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Plano plano1 = new Plano();
        plano1.setVersaoPlano(anel1.getVersaoPlanoAtivo());
        anel1.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano1));
        plano1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano1.setPosicao(1);
        plano1.setDescricao("Principal");
        plano1.setPosicaoTabelaEntreVerde(1);
        plano1.setTempoCiclo(130);
        criarGrupoSemaforicoPlano(anel1, plano1);
        criarEstagioPlano(anel1, plano1, new int[]{1, 4, 3, 2}, new int[]{10, 12, 10, 10});

        Controlador controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));

        List<Erro> erros = getErros(controlador1);
        assertTrue(erros.isEmpty());

        erros = getErros(controladorJson1);
        assertTrue(erros.isEmpty());

        plano1.setCicloDuplo(true);
        erros = getErros(controlador1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo do ciclo duplo deve ser maior ou igual ao tempo de ciclo.", "aneis[0].versaoPlano.planos[0].tempoCicloDuploMaiorOuIgualAoCiclo")
        ));

        plano1.setTempoCicloDuplo(100);
        erros = getErros(controlador1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo do ciclo duplo deve ser maior ou igual ao tempo de ciclo.", "aneis[0].versaoPlano.planos[0].tempoCicloDuploMaiorOuIgualAoCiclo")
        ));

        plano1.setTempoCicloDuplo(256);
        erros = getErros(controlador1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo do ciclo duplo deve ser maior ou igual ao tempo de ciclo.", "aneis[0].versaoPlano.planos[0].tempoCicloDuploMaiorOuIgualAoCiclo")
        ));


        controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));
        erros = getErros(controladorJson1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O Tempo do ciclo duplo deve ser maior ou igual ao tempo de ciclo.", "aneis[0].versaoPlano.planos[0].tempoCicloDuploMaiorOuIgualAoCiclo")
        ));

        plano1.setTempoCicloDuplo(170);
        plano1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        erros = getErros(controlador1);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O ciclo duplo pode ser configurado somente no modo coordenado.", "aneis[0].versaoPlano.planos[0].cicloDuploValido")
        ));

        plano1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        erros = getErros(controlador1);
        assertTrue(erros.isEmpty());

        controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));
        erros = getErros(controladorJson1);
        assertTrue(erros.isEmpty());
    }

    @Test
    public void testCicloDuploSimetricoOuAssimetrico() {
        Controlador controlador1 = getControladorPlanos();
        Controlador controlador2 = getControladorPlanos();

        Anel anel1 = controlador1.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Anel anel2 = controlador2.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        GrupoSemaforico g1 = anel1.findGrupoSemaforicoByPosicao(1);
        g1.setTempoVerdeSeguranca(4);

        GrupoSemaforico g2 = anel2.findGrupoSemaforicoByPosicao(1);
        g2.setTempoVerdeSeguranca(4);

        Plano plano1 = new Plano();
        plano1.setVersaoPlano(anel1.getVersaoPlanoAtivo());
        anel1.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano1));
        plano1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano1.setPosicao(1);
        plano1.setDescricao("Principal");
        plano1.setPosicaoTabelaEntreVerde(1);

        Plano plano2 = new Plano();
        plano2.setVersaoPlano(anel2.getVersaoPlanoAtivo());
        anel2.getVersaoPlanoAtivo().setPlanos(Arrays.asList(plano2));
        plano2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        plano2.setPosicao(1);
        plano2.setDescricao("Principal");
        plano2.setPosicaoTabelaEntreVerde(1);

        plano1.setTempoCiclo(124);
        criarGrupoSemaforicoPlano(anel1, plano1);
        criarEstagioPlano(anel1, plano1, new int[]{1, 4, 3, 2}, new int[]{10, 12, 4, 10});

        plano2.setTempoCiclo(130);
        criarGrupoSemaforicoPlano(anel2, plano2);
        criarEstagioPlano(anel2, plano2, new int[]{1, 4, 3, 2}, new int[]{10, 12, 10, 10});
        plano2.setCicloDuplo(true);
        plano2.setTempoCicloDuplo(242);

        List<Erro> erros = getErros(controlador1);
        assertTrue(erros.isEmpty());

        erros = getErros(controlador2);
        assertTrue(erros.isEmpty());

        //Teste Json
        Controlador controladorJson1 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null)));
        Controlador controladorJson2 = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador2, Cidade.find.all(), RangeUtils.getInstance(null)));

        erros = getErros(controladorJson1);
        assertTrue(erros.isEmpty());

        erros = getErros(controladorJson2);
        assertTrue(erros.isEmpty());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class,
            PlanosCheck.class, PlanosCentralCheck.class);
    }
}
