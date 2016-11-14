package models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

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
        versaoPlanoAnel2Estagios.setStatusVersao(StatusVersao.ATIVO);
        anelCom2Estagios.addVersaoPlano(versaoPlanoAnel2Estagios);

        VersaoPlano versaoPlanoAnel4Estagios = new VersaoPlano(anelCom4Estagios, usuario);
        versaoPlanoAnel4Estagios.setStatusVersao(StatusVersao.ATIVO);
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
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.ATUADO);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[1].versoesPlanos[0].planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")

        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.APAGADO);

        erros = getErros(controlador);
        assertEquals(9, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setTempoCiclo(60);
        plano1Anel2.setDefasagem(100);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Defasagem deve estar entre {min} e o tempo de ciclo", "aneis[1].versoesPlanos[0].planos[0].defasagem"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].modoOperacao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].descricao"),
            new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].versoesPlanos[0].planos[0].posicaoTabelaEntreVerde"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
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
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versoesPlanos[0].planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].versoesPlanos[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[2].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[1].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado")
        ));

        criarGrupoSemaforicoPlano(anelCom2Estagios, plano1Anel2);
        criarGrupoSemaforicoPlano(anelCom4Estagios, plano1Anel4);

        erros = getErros(controlador);
        assertEquals(7, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versoesPlanos[0].planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].versoesPlanos[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado"),
            new Erro(CONTROLADOR, "Um grupo semafórico não associado a nenhum estágio da sequencia do plano deve estar apagado.", "aneis[1].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].grupoApagadoSeNaoAssociado")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 1, 1, 1});

        erros = getErros(controlador);
        assertEquals(22, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].versoesPlanos[0].planos[0].modoOperacaoValido"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versoesPlanos[0].planos[0].posicaoUnicaEstagio")
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
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versoesPlanos[0].planos[0].posicaoUnicaEstagio")
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
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versoesPlanos[0].planos[0].posicaoUnicaEstagio")
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
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de ciclo deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].tempoCiclo"),
            new Erro(CONTROLADOR, "Tempo de verde deve estar entre {min} e {max}", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "Tempo de verde mínimo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
            new Erro(CONTROLADOR, "Tempo de verde máximo deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
            new Erro(CONTROLADOR, "Tempo de verde intermediário deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
            new Erro(CONTROLADOR, "Tempo de extensão de verde deve estar entre {min} e {max}", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[1].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[1].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[1].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
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
            new Erro(CONTROLADOR, "O tempo de verde mínimo deve ser maior ou igual ao verde de segurança e menor que o verde máximo.", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeMinimoFieldMenorMaximo"),
            new Erro(CONTROLADOR, "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediarioFieldEntreMinimoMaximo"),
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio"),
            new Erro(CONTROLADOR, "O tempo de estagio ultrapassa o tempo máximo de permanência.", "aneis[1].versoesPlanos[0].planos[0].estagiosPlanos[0].ultrapassaTempoMaximoPermanencia"),
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[1].versoesPlanos[0].planos[0].ultrapassaTempoCiclo")
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
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].posicaoUnicaEstagio")
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
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].sequenciaValida")
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
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].sequenciaValida")
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
            new Erro(CONTROLADOR, "O estágio que recebe o tempo do estágio dispensável não pode ficar em branco.", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavel")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano1Anel4);
        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O estágio que recebe o tempo do estágio dispensável deve ser o estágio anterior ou posterior ao estágio dispensável.", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano2Anel4);
        erros = getErros(controlador);
        assertThat(erros, empty());

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano3Anel4);
        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O estágio que recebe o tempo do estágio dispensável deve ser o estágio anterior ou posterior ao estágio dispensável.", "aneis[0].versoesPlanos[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano4Anel4);
        erros = getErros(controlador);
        assertThat(erros, empty());

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.MANUAL);
        plano1Anel4.setModoOperacao(ModoOperacaoPlano.MANUAL);
        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[0].versoesPlanos[0].planos[0].numeroEstagiosEmModoManualOk"),
            new Erro(CONTROLADOR, "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo.", "aneis[1].versoesPlanos[0].planos[0].numeroEstagiosEmModoManualOk")
        ));

        plano1Anel4.getEstagiosPlanos().remove(0);
        plano1Anel4.getEstagiosPlanos().remove(1);

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
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //3 - 1 - 4 - 2
        estagioPlano1.setPosicao(2);
        estagioPlano2.setPosicao(4);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(3);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[0].versoesPlanos[0].planos[0].ultrapassaTempoCiclo")
        ));

        //3 - 2 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(2);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
            new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].versoesPlanos[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //2 - 3 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(1);
        estagioPlano3.setPosicao(2);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).", "aneis[0].versoesPlanos[0].planos[0].ultrapassaTempoCiclo")
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
            new Erro(CONTROLADOR, "A sequência de estágios não é válida.", "aneis[0].versoesPlanos[0].planos[0].sequenciaValida")
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
            new Erro(CONTROLADOR, "A sequência de estágios não é válida, pois existe uma transição proibida devido à não execução do estágio dispensável.", "aneis[0].versoesPlanos[0].planos[0].sequenciaInvalidaSeExisteEstagioDispensavel")
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
    public void testDeserializerGrupoSemaforicoPlano() {
        Controlador controladorOk = getControladorPlanos();
        String modeloId = controladorOk.getModelo().getId().toString();
        String payload = "{\"id\":\"1f44b9d3-080d-4393-8572-b2a77eedc914\",\"versoesTabelasHorarias\":[],\"sequencia\":6,\"limiteEstagio\":16,\"limiteGrupoSemaforico\":16,\"limiteAnel\":4,\"limiteDetectorPedestre\":4,\"limiteDetectorVeicular\":8,\"limiteTabelasEntreVerdes\":2,\"limitePlanos\":16,\"nomeEndereco\":\"R. Uelson de Freitas Ramos, nº 2345\",\"dataCriacao\":\"10/11/2016 17:03:35\",\"dataAtualizacao\":\"10/11/2016 17:04:27\",\"CLC\":\"1.000.0006\",\"bloqueado\":false,\"planosBloqueado\":false,\"verdeMin\":1,\"verdeMax\":255,\"verdeMinimoMin\":10,\"verdeMinimoMax\":255,\"verdeMaximoMin\":10,\"verdeMaximoMax\":255,\"extensaoVerdeMin\":1,\"extensaoVerdeMax\":10,\"verdeIntermediarioMin\":10,\"verdeIntermediarioMax\":255,\"defasagemMin\":0,\"defasagemMax\":\"255\",\"amareloMin\":3,\"amareloMax\":5,\"vermelhoIntermitenteMin\":3,\"vermelhoIntermitenteMax\":32,\"vermelhoLimpezaVeicularMin\":0,\"vermelhoLimpezaVeicularMax\":7,\"vermelhoLimpezaPedestreMin\":0,\"vermelhoLimpezaPedestreMax\":5,\"atrasoGrupoMin\":0,\"atrasoGrupoMax\":\"20\",\"verdeSegurancaVeicularMin\":10,\"verdeSegurancaVeicularMax\":30,\"verdeSegurancaPedestreMin\":4,\"verdeSegurancaPedestreMax\":10,\"maximoPermanenciaEstagioMin\":60,\"maximoPermanenciaEstagioMax\":255,\"defaultMaximoPermanenciaEstagioVeicular\":127,\"cicloMin\":30,\"cicloMax\":255,\"ausenciaDeteccaoMin\":\"0\",\"ausenciaDeteccaoMax\":\"4320\",\"deteccaoPermanenteMin\":\"0\",\"deteccaoPermanenteMax\":\"1440\",\"statusControlador\":\"EM_CONFIGURACAO\",\"statusControladorReal\":\"EM_CONFIGURACAO\",\"area\":{\"idJson\":\"66b6a0c4-a1c4-11e6-970d-0401fa9c1b01\"},\"endereco\":{\"idJson\":\"faacf046-021c-46cf-88e7-e227c5835431\"},\"modelo\":{\"id\":\"" + modeloId + "\",\"idJson\":\"66b6ba7e-a1c4-11e6-970d-0401fa9c1b01\",\"descricao\":\"Modelo Básico\",\"fabricante\":{\"id\":\"66b6a723-a1c4-11e6-970d-0401fa9c1b01\",\"nome\":\"Raro Labs\"}},\"aneis\":[{\"id\":\"49294221-89de-429e-bde6-487a88cf90d3\",\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\",\"numeroSMEE\":\"-\",\"ativo\":true,\"aceitaModoManual\":true,\"posicao\":1,\"CLA\":\"1.000.0006.1\",\"estagios\":[{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"}],\"gruposSemaforicos\":[{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"}],\"detectores\":[],\"planos\":[{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"},{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"},{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"},{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"},{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"},{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"},{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"},{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"},{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"},{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"},{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"},{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"},{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"},{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"},{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"},{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"},{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}],\"endereco\":{\"idJson\":\"14d5e671-e176-4ff3-be91-f8e68a1b8066\"},\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"},\"localizacao\":\"R. Uelson de Freitas Ramos, nº 2345\"},{\"id\":\"89819024-3752-49af-9ae3-9d5d75502d47\",\"idJson\":\"41204651-4ed6-4ede-8347-56fd28afd3e6\",\"ativo\":false,\"aceitaModoManual\":true,\"posicao\":2,\"CLA\":\"1.000.0006.2\",\"estagios\":[],\"gruposSemaforicos\":[],\"detectores\":[],\"planos\":[]},{\"id\":\"368b9c69-9e6c-42a4-a6b3-ee4d4021d513\",\"idJson\":\"c51234ce-111a-4e13-9fa6-38b728fc8cd0\",\"ativo\":false,\"aceitaModoManual\":true,\"posicao\":3,\"CLA\":\"1.000.0006.3\",\"estagios\":[],\"gruposSemaforicos\":[],\"detectores\":[],\"planos\":[]},{\"id\":\"db121b4a-ee6c-4fe0-b7a4-c45b0a28eff2\",\"idJson\":\"ad7af622-9400-47e7-a90e-2c030c1ed721\",\"ativo\":false,\"aceitaModoManual\":true,\"posicao\":4,\"CLA\":\"1.000.0006.4\",\"estagios\":[],\"gruposSemaforicos\":[],\"detectores\":[],\"planos\":[]}],\"estagios\":[{\"id\":\"91cdeeeb-9c45-4bb7-a887-a41f2d3b7828\",\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\",\"tempoMaximoPermanencia\":127,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":1,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"imagem\":{\"idJson\":\"8cd63847-2fb3-4d2f-9f42-989d21b45ac7\",\"id\":\"81796d64-ecfd-4c94-8112-26abe61637e7\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"20b5b460-f616-4b0a-8ca7-14b382336bbc\"}],\"estagiosPlanos\":[],\"verdeMinimoEstagio\":10,\"isVeicular\":true},{\"id\":\"a565e238-6602-40d6-a4a3-1d0831d11e06\",\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\",\"tempoMaximoPermanencia\":127,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":3,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"imagem\":{\"idJson\":\"9bf6bf33-3d23-465e-b14d-38809ce75096\",\"id\":\"cb0f2ac7-562d-42de-8451-e71524900c6e\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"4b624b92-59bb-4059-8d16-cf44ec425340\"},{\"idJson\":\"297c61a2-bc8f-4be8-87df-af6b660730a4\"}],\"estagiosPlanos\":[],\"verdeMinimoEstagio\":10,\"isVeicular\":true},{\"id\":\"90696f41-5882-4b35-815e-34a23b9a963f\",\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\",\"tempoMaximoPermanencia\":127,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":2,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"imagem\":{\"idJson\":\"caf7b770-7f41-416f-8243-0b52b42ebf23\",\"id\":\"f06ac1ee-882f-4449-900e-d4bb7f3e8064\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"c8227069-b607-4160-8d44-c9b2d996ab11\"}],\"estagiosPlanos\":[],\"verdeMinimoEstagio\":10,\"isVeicular\":true}],\"gruposSemaforicos\":[{\"id\":\"3e5ccf7b-ef8b-4e42-9b28-1c1800e83c0b\",\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\",\"tipo\":\"VEICULAR\",\"posicao\":3,\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"d96f3adc-edd3-4fad-a9e1-2f3c03f4f5b1\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"297c61a2-bc8f-4be8-87df-af6b660730a4\"}],\"transicoes\":[{\"idJson\":\"13d93cc9-f337-46b2-b4ab-e1ea229a8371\"},{\"idJson\":\"97fa6b31-a2f6-430d-b6fd-1a122a0034ce\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"2bdae528-b663-4901-8760-e0a0c04ff00d\"},{\"idJson\":\"b2d70ccc-8af2-400c-a52b-129b364a1713\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"5a07a48f-8289-457b-a42d-79997b180b79\"}]},{\"id\":\"40d1e0b7-287b-4b5e-a260-17d622f95538\",\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\",\"tipo\":\"VEICULAR\",\"posicao\":4,\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"e116a732-c39b-4981-9a32-aabab1d1deae\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"4b624b92-59bb-4059-8d16-cf44ec425340\"}],\"transicoes\":[{\"idJson\":\"d36bd3f7-5640-4067-af92-c49d9ba75de2\"},{\"idJson\":\"e71cf67d-bc0e-4150-a8ef-fc27a01ca6cd\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"7c38bf0e-7024-4213-b884-5604de23acb7\"},{\"idJson\":\"dc0839d7-58c1-46b1-9f67-df87d8dd8fba\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"2026cb3a-54b4-419a-9110-8715684abd29\"}]},{\"id\":\"2c41fc8d-0e2e-43fb-93f1-06431378c173\",\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\",\"tipo\":\"VEICULAR\",\"posicao\":2,\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"d24eca39-331f-4016-9b8c-2df4a2260975\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"c8227069-b607-4160-8d44-c9b2d996ab11\"}],\"transicoes\":[{\"idJson\":\"33c47212-2532-433e-bff0-10375355d4a5\"},{\"idJson\":\"341fb650-de88-4bd2-8c57-f3096dd01666\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"8d9c02ba-ad47-4d5a-a7c8-4067f4745263\"},{\"idJson\":\"f896f604-d59c-4807-ad57-58701c4bde14\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"0b028e94-4072-49b6-8e1a-6f54ba814f3a\"}]},{\"id\":\"51a416c0-8adb-4480-8581-b8858ba01c93\",\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\",\"tipo\":\"VEICULAR\",\"posicao\":1,\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"verdesConflitantesOrigem\":[{\"idJson\":\"d24eca39-331f-4016-9b8c-2df4a2260975\"},{\"idJson\":\"e116a732-c39b-4981-9a32-aabab1d1deae\"},{\"idJson\":\"d96f3adc-edd3-4fad-a9e1-2f3c03f4f5b1\"}],\"verdesConflitantesDestino\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"20b5b460-f616-4b0a-8ca7-14b382336bbc\"}],\"transicoes\":[{\"idJson\":\"15035225-1373-4a86-b72a-1614e978efce\"},{\"idJson\":\"d96726b0-2b73-414a-8c21-2b89d86d8dcb\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"c8d24890-b1ee-4661-ab08-ff9903aa0633\"},{\"idJson\":\"52b0b035-77e1-4c78-87cf-3d937f98addc\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"db5e0184-a0bb-46dd-96aa-6288d72f2fb3\"}]}],\"detectores\":[],\"transicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"id\":\"2a8ca1dd-fb26-45d5-91aa-f67e9d1c1084\",\"idJson\":\"c8227069-b607-4160-8d44-c9b2d996ab11\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"}},{\"id\":\"cbc61de4-9c0a-4501-a498-2f9ed69bbd7f\",\"idJson\":\"4b624b92-59bb-4059-8d16-cf44ec425340\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"}},{\"id\":\"de2a153e-9f67-4f42-b910-540d083fff4d\",\"idJson\":\"20b5b460-f616-4b0a-8ca7-14b382336bbc\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"}},{\"id\":\"d31acb07-fc90-464d-ad52-3a81a9eec798\",\"idJson\":\"297c61a2-bc8f-4be8-87df-af6b660730a4\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"}}],\"verdesConflitantes\":[{\"id\":\"c2a29a92-49ab-47b6-bc0f-c84f8becd301\",\"idJson\":\"d96f3adc-edd3-4fad-a9e1-2f3c03f4f5b1\",\"origem\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"destino\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"}},{\"id\":\"20c8c122-a5ea-4fbb-80fe-c4c01cfda456\",\"idJson\":\"d24eca39-331f-4016-9b8c-2df4a2260975\",\"origem\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"destino\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"}},{\"id\":\"7a5a2ba8-892e-44b9-927b-e0d6cdb26b93\",\"idJson\":\"e116a732-c39b-4981-9a32-aabab1d1deae\",\"origem\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"destino\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"}}],\"transicoes\":[{\"id\":\"6e82fe8c-62b0-46d6-97d5-6a6303befada\",\"idJson\":\"13d93cc9-f337-46b2-b4ab-e1ea229a8371\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"4b441528-afd5-4dda-955b-305ca6010eb9\"}],\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"0d3d2f45-7075-488a-86bd-30975a5e2dec\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"d7653e27-3df0-41e7-ba53-27ecc532961d\",\"idJson\":\"341fb650-de88-4bd2-8c57-f3096dd01666\",\"origem\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"destino\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"b3b5c45c-167d-4d78-aaf6-44f12ec0696e\"}],\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"3ec91c1a-ff42-47c0-ae00-dea81d79e1db\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"fdec3682-495e-4e57-a9c1-00d9120f0f9a\",\"idJson\":\"e71cf67d-bc0e-4150-a8ef-fc27a01ca6cd\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"8bddfc2d-0dd8-42ab-a2c8-c6877b24de17\"}],\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"c88d804b-4a43-40fc-97bb-0f5257b26e76\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"bb6f09a9-72f1-4486-b3a7-22d2e9cb3b69\",\"idJson\":\"15035225-1373-4a86-b72a-1614e978efce\",\"origem\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"79aa447a-20a0-42c3-bee2-e14c82aeab75\"}],\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"a9179d78-7e63-4088-8186-ddb0dfa807c0\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"fc13d41f-855b-4046-bfd1-e54df028798f\",\"idJson\":\"d36bd3f7-5640-4067-af92-c49d9ba75de2\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"46cadffc-00f4-4812-9e27-a416e1434102\"}],\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"7be99fea-0bd1-4972-a227-eb15ef7f3519\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"9dc5cfea-1985-4361-a51c-0644bafce8a2\",\"idJson\":\"97fa6b31-a2f6-430d-b6fd-1a122a0034ce\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"aa6818a0-6534-41b3-944e-350f9a110906\"}],\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"a076c266-c191-41b5-9787-873c5d344a60\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"9b4807ff-2b05-4539-91a8-efbac1b4474d\",\"idJson\":\"33c47212-2532-433e-bff0-10375355d4a5\",\"origem\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"65b8151b-7cf1-4b9c-ba4d-c9e12564f326\"}],\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"6ba34a91-7f20-407c-8202-93e3597ec2d2\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"c152b223-04c6-4242-9bb0-c19f1db18160\",\"idJson\":\"d96726b0-2b73-414a-8c21-2b89d86d8dcb\",\"origem\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"destino\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"9949cb11-2846-4625-b2f7-2e996411a638\"}],\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"efc3e838-a627-4a9c-9b0c-4211f33c3c82\"},\"modoIntermitenteOuApagado\":false}],\"transicoesComGanhoDePassagem\":[{\"id\":\"75efcd1a-9e51-4dc8-9e96-5d6433ad9e42\",\"idJson\":\"f896f604-d59c-4807-ad57-58701c4bde14\",\"origem\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"destino\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"7a7c4646-0adf-4e34-afd0-826f6b04ea56\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"49dbb9bf-8883-44ec-a9fe-711fad53f71c\",\"idJson\":\"8d9c02ba-ad47-4d5a-a7c8-4067f4745263\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"2eb93812-6a81-4c9c-8acf-b871e6f99f21\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"47cd7768-05c9-4bb4-9734-0ef7d6910a2d\",\"idJson\":\"c8d24890-b1ee-4661-ab08-ff9903aa0633\",\"origem\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"destino\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"c990887a-f59c-4b3d-8683-90e396f4f4f9\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"f18d0c86-dfb4-441e-a8d8-47e416fa3152\",\"idJson\":\"52b0b035-77e1-4c78-87cf-3d937f98addc\",\"origem\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"destino\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"7b330e93-6aa8-4358-9f05-6ff7cebe62f8\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"d4ccee9f-b244-4bb3-b04b-872961ddddb3\",\"idJson\":\"dc0839d7-58c1-46b1-9f67-df87d8dd8fba\",\"origem\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"4b6689f1-9358-4dc4-9af3-7bb6f4c8365d\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"40dd26d4-baf6-43e1-9b05-975e5d157f4f\",\"idJson\":\"7c38bf0e-7024-4213-b884-5604de23acb7\",\"origem\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"eab8382f-8e81-4315-836d-0310beb07470\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"1a3bf1dc-4341-43c5-bbad-abd525590f74\",\"idJson\":\"2bdae528-b663-4901-8760-e0a0c04ff00d\",\"origem\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"ada0d0a8-ebab-4dee-a64c-26711eb0bc5d\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"c95e3e7c-98de-4f8b-9033-575b8cfafa69\",\"idJson\":\"b2d70ccc-8af2-400c-a52b-129b364a1713\",\"origem\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"destino\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"4df0b9b9-dfed-4f19-98f9-85dbb8630f46\"},\"modoIntermitenteOuApagado\":false}],\"tabelasEntreVerdes\":[{\"id\":\"585f28f6-89a2-4d82-ae33-42d34bbaae84\",\"idJson\":\"2026cb3a-54b4-419a-9110-8715684abd29\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"46cadffc-00f4-4812-9e27-a416e1434102\"},{\"idJson\":\"8bddfc2d-0dd8-42ab-a2c8-c6877b24de17\"}]},{\"id\":\"2ee457d4-f733-4cf4-907d-f69f027aa530\",\"idJson\":\"0b028e94-4072-49b6-8e1a-6f54ba814f3a\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"b3b5c45c-167d-4d78-aaf6-44f12ec0696e\"},{\"idJson\":\"65b8151b-7cf1-4b9c-ba4d-c9e12564f326\"}]},{\"id\":\"725df2db-2fec-4ad2-84dd-570394cc0898\",\"idJson\":\"db5e0184-a0bb-46dd-96aa-6288d72f2fb3\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"79aa447a-20a0-42c3-bee2-e14c82aeab75\"},{\"idJson\":\"9949cb11-2846-4625-b2f7-2e996411a638\"}]},{\"id\":\"bba58229-f37b-4c6c-8d4c-136ea90bce2f\",\"idJson\":\"5a07a48f-8289-457b-a42d-79997b180b79\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"aa6818a0-6534-41b3-944e-350f9a110906\"},{\"idJson\":\"4b441528-afd5-4dda-955b-305ca6010eb9\"}]}],\"tabelasEntreVerdesTransicoes\":[{\"id\":\"46a8e745-5c44-4fb7-9208-9fe9a77d12be\",\"idJson\":\"79aa447a-20a0-42c3-bee2-e14c82aeab75\",\"tempoAmarelo\":\"4\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"db5e0184-a0bb-46dd-96aa-6288d72f2fb3\"},\"transicao\":{\"idJson\":\"15035225-1373-4a86-b72a-1614e978efce\"}},{\"id\":\"857350bc-0768-472e-9775-ccf9251358e0\",\"idJson\":\"65b8151b-7cf1-4b9c-ba4d-c9e12564f326\",\"tempoAmarelo\":\"4\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"0b028e94-4072-49b6-8e1a-6f54ba814f3a\"},\"transicao\":{\"idJson\":\"33c47212-2532-433e-bff0-10375355d4a5\"}},{\"id\":\"521cd04f-baba-4337-9e21-6b3244f3005d\",\"idJson\":\"46cadffc-00f4-4812-9e27-a416e1434102\",\"tempoAmarelo\":\"4\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"2026cb3a-54b4-419a-9110-8715684abd29\"},\"transicao\":{\"idJson\":\"d36bd3f7-5640-4067-af92-c49d9ba75de2\"}},{\"id\":\"73b057d7-ae55-40b7-ac83-ce94f8cb53cb\",\"idJson\":\"aa6818a0-6534-41b3-944e-350f9a110906\",\"tempoAmarelo\":\"4\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"5a07a48f-8289-457b-a42d-79997b180b79\"},\"transicao\":{\"idJson\":\"97fa6b31-a2f6-430d-b6fd-1a122a0034ce\"}},{\"id\":\"f908440e-e5e5-481f-8c07-9f64c4b2965b\",\"idJson\":\"4b441528-afd5-4dda-955b-305ca6010eb9\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"5a07a48f-8289-457b-a42d-79997b180b79\"},\"transicao\":{\"idJson\":\"13d93cc9-f337-46b2-b4ab-e1ea229a8371\"}},{\"id\":\"07af22a8-2fd1-43d2-a13c-0ac09c77e30a\",\"idJson\":\"b3b5c45c-167d-4d78-aaf6-44f12ec0696e\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"0b028e94-4072-49b6-8e1a-6f54ba814f3a\"},\"transicao\":{\"idJson\":\"341fb650-de88-4bd2-8c57-f3096dd01666\"}},{\"id\":\"cecaf8fa-4ec3-4a7d-8398-3d7e7459440e\",\"idJson\":\"9949cb11-2846-4625-b2f7-2e996411a638\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"db5e0184-a0bb-46dd-96aa-6288d72f2fb3\"},\"transicao\":{\"idJson\":\"d96726b0-2b73-414a-8c21-2b89d86d8dcb\"}},{\"id\":\"c662114d-fb46-4265-ac4c-add686475234\",\"idJson\":\"8bddfc2d-0dd8-42ab-a2c8-c6877b24de17\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"0\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"2026cb3a-54b4-419a-9110-8715684abd29\"},\"transicao\":{\"idJson\":\"e71cf67d-bc0e-4150-a8ef-fc27a01ca6cd\"}}],\"planos\":[{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 1\",\"posicao\":1,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"4efe9095-fc1a-4bfa-8c3a-8a2b0ecef568\"},{\"idJson\":\"0b5f5892-ecec-4de8-85cb-8a8f82446118\"},{\"idJson\":\"67da216a-44ca-4bcc-8382-949d0662071b\"},{\"idJson\":\"23f02200-a2b4-46fc-8e4b-8610305cb4cc\"}],\"estagiosPlanos\":[{\"idJson\":\"67d21b13-3e52-4a2b-8bf4-b76ce495e47f\"},{\"idJson\":\"5fda79ad-3416-4b18-8e54-c4812f50e624\"},{\"idJson\":\"0e95e9a3-7967-46de-8190-f2996a62cc6d\"}],\"tempoCiclo\":40,\"configurado\":true,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"},\"defasagem\":0},{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 2\",\"posicao\":2,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"0b96ced6-76fb-440d-84cf-ecdc8774114a\"},{\"idJson\":\"7009fb97-4d40-4def-8c7c-3692f9c63ef1\"},{\"idJson\":\"e0763225-8125-49ae-a670-d183263ff3cb\"},{\"idJson\":\"842483f4-2881-4e48-a1e1-eb9957f66b1a\"}],\"estagiosPlanos\":[{\"idJson\":\"7fa3bcbd-b74f-4611-a6d7-a25b21c38036\"},{\"idJson\":\"3ae3197a-e2bd-4138-8cec-7f28e93d294b\"},{\"idJson\":\"a4a79224-230a-428d-bc66-38323d16a956\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 3\",\"posicao\":3,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"6cc2dc6e-929a-419e-9a95-8d7262a987e6\"},{\"idJson\":\"7ea47d82-19d6-4fb7-bfa6-4abf563476d5\"},{\"idJson\":\"d47830d0-3fa4-4bb4-821a-e1a76d730c42\"},{\"idJson\":\"ae49d0be-9384-40a8-a0e2-4d7550c8b819\"}],\"estagiosPlanos\":[{\"idJson\":\"3e1d577e-95ff-4a04-82a4-a2596f830cc9\"},{\"idJson\":\"398c18ff-04b7-4ecc-91cf-0704a118bf65\"},{\"idJson\":\"86b8f72e-50de-4b21-9c20-4bfee42f3262\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 4\",\"posicao\":4,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"6e2f9a43-a23e-40d6-9a72-749895910480\"},{\"idJson\":\"90925c12-f9d6-4579-95bd-d9609b6a8cd6\"},{\"idJson\":\"6eb69fe7-7f96-4084-8fb3-7e27b36443b3\"},{\"idJson\":\"61aab191-5f49-4098-b46c-c523323bae81\"}],\"estagiosPlanos\":[{\"idJson\":\"c984de74-5cee-4089-8979-8320ec717759\"},{\"idJson\":\"1b489b32-34a1-4b79-a61d-4d321aeafb46\"},{\"idJson\":\"ea5ca3e6-7350-49da-881d-c573584cf86f\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 5\",\"posicao\":5,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"b30e18de-9d05-49fa-9583-9a5e954c9fcd\"},{\"idJson\":\"f13c81c3-d08b-4994-8043-8ced2f151cd6\"},{\"idJson\":\"e907b506-bddf-4c9b-9db5-da029fc6eca6\"},{\"idJson\":\"f38150cb-4ea0-41a1-bce9-7fd823de0991\"}],\"estagiosPlanos\":[{\"idJson\":\"efc81b9d-7482-4607-9766-b70ae170473c\"},{\"idJson\":\"59ae5fde-7e21-4df1-ae2f-71b12ae1b21a\"},{\"idJson\":\"312b4200-8687-439e-95cb-98c325ccc09c\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 6\",\"posicao\":6,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"b10e0a6e-6df0-46ad-8d34-2c35bd1adfe3\"},{\"idJson\":\"cb968e84-7375-401b-88c8-946360bf43b1\"},{\"idJson\":\"6051acb7-70b6-4d6c-8e05-7ae4cff15410\"},{\"idJson\":\"63752e67-8892-4431-955f-9e233a6e39d5\"}],\"estagiosPlanos\":[{\"idJson\":\"5ba506a6-d3f8-45c5-a33b-6298652779cf\"},{\"idJson\":\"7956a6fb-2f6b-4c56-b40a-a9bea08fb632\"},{\"idJson\":\"67710ea4-526a-483a-bea0-b464db08be03\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 7\",\"posicao\":7,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"a72ec0e3-c5fa-4e90-9983-81b74f686d5e\"},{\"idJson\":\"e683dad6-1caa-40fa-9048-ffb27e08465b\"},{\"idJson\":\"ceea01e1-d588-4de9-ac61-55969df033cd\"},{\"idJson\":\"67bea362-4f29-465d-86ce-d4764084f798\"}],\"estagiosPlanos\":[{\"idJson\":\"4c8c0cc1-935e-4f37-a4cc-34223733cb64\"},{\"idJson\":\"efaae49c-a02d-479b-bd96-eac7e9c4cd57\"},{\"idJson\":\"11f5b5fe-e552-4a79-b20f-8d1fd8444420\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 8\",\"posicao\":8,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"0e52e04e-b49c-4da4-9eac-f0150d06d1c6\"},{\"idJson\":\"0a1fffab-99ab-45d5-b300-464f0a60d160\"},{\"idJson\":\"481df101-e044-401a-8d1d-266358e74e37\"},{\"idJson\":\"102868bb-050f-4cfb-803a-56ce69d72042\"}],\"estagiosPlanos\":[{\"idJson\":\"c5eaf516-65bb-4161-bbf0-440c3c749102\"},{\"idJson\":\"bfda04ab-7f8b-4df7-8dd8-60ddb4a1763d\"},{\"idJson\":\"c17a6ee4-7460-4cd0-9636-8273adcb618c\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 9\",\"posicao\":9,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"2c887aa6-95f3-4986-8e96-1d9f6a3adb06\"},{\"idJson\":\"b4efff2e-4b71-493c-be5c-619f336a14d3\"},{\"idJson\":\"57556e74-e7b1-40e1-8557-5ba355f1c858\"},{\"idJson\":\"17b435b5-beed-4b1f-8224-bd59d926aef4\"}],\"estagiosPlanos\":[{\"idJson\":\"8490a86c-5c93-4c6b-b93e-057f88c61662\"},{\"idJson\":\"8e09faad-fa93-45e9-86d0-eb5fec13ca94\"},{\"idJson\":\"db0b8d57-0b33-41c4-9c15-66cdfe188d8c\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 10\",\"posicao\":10,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"336146be-d58a-4760-b0db-1317ac06c810\"},{\"idJson\":\"950a8776-6af4-4162-9f17-8f7db2577953\"},{\"idJson\":\"96acbca6-f035-4c82-ab8f-cd35f472d6f4\"},{\"idJson\":\"7da43200-106d-4773-bb42-8d1f799ae655\"}],\"estagiosPlanos\":[{\"idJson\":\"3b67f8db-737a-4923-8061-97b325e455ea\"},{\"idJson\":\"11046f1f-585e-4d31-890e-c2cae6ac89a3\"},{\"idJson\":\"c548ec84-3f36-46cf-97d0-2b0f48d76f74\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 11\",\"posicao\":11,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"0148ab29-7ea1-4655-a255-9bb97e2e3197\"},{\"idJson\":\"4821a2bd-c266-4016-8216-efe9cc294962\"},{\"idJson\":\"808d73dc-57d0-484e-ae72-5c85002533e3\"},{\"idJson\":\"c04569cb-68a8-42ee-a1b0-19088e035771\"}],\"estagiosPlanos\":[{\"idJson\":\"62f181a7-c8ce-496b-b33b-d9e8b68db872\"},{\"idJson\":\"d940a992-e89e-4c2b-b087-ccac4d73675b\"},{\"idJson\":\"35fb699e-ae20-4265-bc29-0c761234c2c0\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 12\",\"posicao\":12,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"c908b2fc-1602-48bb-a5d3-f6e586766e88\"},{\"idJson\":\"95d9f83d-5a8d-448f-aa65-5b1b51dc1273\"},{\"idJson\":\"d217041b-12e9-4679-89be-886f128f6cc2\"},{\"idJson\":\"e01c0b60-e208-400e-8595-9c18a6149740\"}],\"estagiosPlanos\":[{\"idJson\":\"2b6a4435-83bb-4a13-8047-bf8960f75df5\"},{\"idJson\":\"edfcf1be-c9c2-4d63-bedc-9bffb133c12e\"},{\"idJson\":\"a9ee5a3b-5db7-4a5c-b9f4-b84f16c7ac0d\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 13\",\"posicao\":13,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"392b678c-fd06-4dca-b602-e69b9e38253b\"},{\"idJson\":\"70ee363d-a5ea-4942-8719-95d27eea6546\"},{\"idJson\":\"e64fca80-0770-415f-880f-8ec459e878db\"},{\"idJson\":\"41e2d690-73de-453d-87a6-204ac1cf8025\"}],\"estagiosPlanos\":[{\"idJson\":\"217335a8-20b8-4c7f-b913-f58bed553d51\"},{\"idJson\":\"a327cadc-ce0d-4881-b63a-514f2e091374\"},{\"idJson\":\"f09ed48a-9ffd-4a05-b69a-60d6997a1118\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 14\",\"posicao\":14,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"01d16ef9-680b-438a-a025-1184e5d8db55\"},{\"idJson\":\"c5f34843-407f-4605-b276-9d8366b74f17\"},{\"idJson\":\"449e7118-fda0-4c29-975e-f154d51719dc\"},{\"idJson\":\"ea9b73fa-5c6f-4429-a4ab-d3a9b768243e\"}],\"estagiosPlanos\":[{\"idJson\":\"cd530f9f-297d-46de-b540-d08d3d23f0ca\"},{\"idJson\":\"db9dc3c9-033b-48f6-865a-efe294021d64\"},{\"idJson\":\"94dde215-12ec-4751-be3d-c3471edc227b\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 15\",\"posicao\":15,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"c089a125-ab0b-45c0-bbca-d8c9a1a89495\"},{\"idJson\":\"ca99c08d-1f16-495e-a819-21431c5f91eb\"},{\"idJson\":\"f56b8f0e-6929-4027-a1cb-75537b21cc34\"},{\"idJson\":\"9e76a3f7-f130-427d-aa5d-91a64b569e29\"}],\"estagiosPlanos\":[{\"idJson\":\"7fab89df-d4ac-4f18-955f-a08ad3763656\"},{\"idJson\":\"fdffe48c-0021-4745-a052-838655e48659\"},{\"idJson\":\"31419835-ded2-4530-b1c0-eb625f295305\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"PLANO 16\",\"posicao\":16,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"84414ad0-f854-43f4-a703-e1dd7640e5c3\"},{\"idJson\":\"4cc9e065-3e40-4a39-b990-e8b216609be9\"},{\"idJson\":\"84039b40-27d9-4fb7-a328-c1ce80a233c9\"},{\"idJson\":\"99ada0db-262c-4ad6-b532-caf2e3b5083d\"}],\"estagiosPlanos\":[{\"idJson\":\"5075c57f-35e8-4cf7-b666-f85f85600b09\"},{\"idJson\":\"c7a15320-b99b-4baf-815a-9dc2de1e57d4\"},{\"idJson\":\"069575fc-53fb-43bd-9b30-535f4311a715\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"}},{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"descricao\":\"Exclusivo\",\"posicao\":0,\"modoOperacao\":\"MANUAL\",\"posicaoTabelaEntreVerde\":1,\"gruposSemaforicosPlanos\":[{\"idJson\":\"7bde2f37-c01e-47df-9a54-bfc622004649\"},{\"idJson\":\"bce281e0-35e3-4d64-aed0-110a47cc0618\"},{\"idJson\":\"ef142e2c-ceac-4fa7-850b-9817652e82c2\"},{\"idJson\":\"54a2cfa5-cc82-4f81-9ea7-5cb8ee5a586a\"}],\"estagiosPlanos\":[{\"idJson\":\"df39c1f1-7440-497d-b1b8-3c9554c0782e\"},{\"idJson\":\"1df7a44b-c63d-403f-959c-3fc45b406110\"},{\"idJson\":\"c35f26a2-db19-4401-b25a-3077675f8cea\"}],\"tempoCiclo\":30,\"configurado\":false,\"versaoPlano\":{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\"},\"manualExclusivo\":true}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"9eb0cf87-469f-4222-857e-34bf884eb057\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"}},{\"idJson\":\"47923202-c75a-4ac4-8e8a-d82f9231cd3c\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"}},{\"idJson\":\"27253aa7-dd6d-4f42-9b5e-9732eaf58b58\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"}},{\"idJson\":\"c533ca58-4a41-48ed-95be-f31d2a73accf\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"}},{\"idJson\":\"4efe9095-fc1a-4bfa-8c3a-8a2b0ecef568\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"}},{\"idJson\":\"0b5f5892-ecec-4de8-85cb-8a8f82446118\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"}},{\"idJson\":\"67da216a-44ca-4bcc-8382-949d0662071b\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"}},{\"idJson\":\"23f02200-a2b4-46fc-8e4b-8610305cb4cc\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"}},{\"idJson\":\"0b96ced6-76fb-440d-84cf-ecdc8774114a\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"}},{\"idJson\":\"7009fb97-4d40-4def-8c7c-3692f9c63ef1\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"}},{\"idJson\":\"e0763225-8125-49ae-a670-d183263ff3cb\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"}},{\"idJson\":\"842483f4-2881-4e48-a1e1-eb9957f66b1a\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"}},{\"idJson\":\"6cc2dc6e-929a-419e-9a95-8d7262a987e6\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"}},{\"idJson\":\"7ea47d82-19d6-4fb7-bfa6-4abf563476d5\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"}},{\"idJson\":\"d47830d0-3fa4-4bb4-821a-e1a76d730c42\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"}},{\"idJson\":\"ae49d0be-9384-40a8-a0e2-4d7550c8b819\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"}},{\"idJson\":\"6e2f9a43-a23e-40d6-9a72-749895910480\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"}},{\"idJson\":\"90925c12-f9d6-4579-95bd-d9609b6a8cd6\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"}},{\"idJson\":\"6eb69fe7-7f96-4084-8fb3-7e27b36443b3\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"}},{\"idJson\":\"61aab191-5f49-4098-b46c-c523323bae81\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"}},{\"idJson\":\"b30e18de-9d05-49fa-9583-9a5e954c9fcd\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"}},{\"idJson\":\"f13c81c3-d08b-4994-8043-8ced2f151cd6\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"}},{\"idJson\":\"e907b506-bddf-4c9b-9db5-da029fc6eca6\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"}},{\"idJson\":\"f38150cb-4ea0-41a1-bce9-7fd823de0991\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"}},{\"idJson\":\"b10e0a6e-6df0-46ad-8d34-2c35bd1adfe3\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"}},{\"idJson\":\"cb968e84-7375-401b-88c8-946360bf43b1\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"}},{\"idJson\":\"6051acb7-70b6-4d6c-8e05-7ae4cff15410\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"}},{\"idJson\":\"63752e67-8892-4431-955f-9e233a6e39d5\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"}},{\"idJson\":\"a72ec0e3-c5fa-4e90-9983-81b74f686d5e\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"}},{\"idJson\":\"e683dad6-1caa-40fa-9048-ffb27e08465b\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"}},{\"idJson\":\"ceea01e1-d588-4de9-ac61-55969df033cd\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"}},{\"idJson\":\"67bea362-4f29-465d-86ce-d4764084f798\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"}},{\"idJson\":\"0e52e04e-b49c-4da4-9eac-f0150d06d1c6\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"}},{\"idJson\":\"0a1fffab-99ab-45d5-b300-464f0a60d160\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"}},{\"idJson\":\"481df101-e044-401a-8d1d-266358e74e37\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"}},{\"idJson\":\"102868bb-050f-4cfb-803a-56ce69d72042\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"}},{\"idJson\":\"2c887aa6-95f3-4986-8e96-1d9f6a3adb06\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"}},{\"idJson\":\"b4efff2e-4b71-493c-be5c-619f336a14d3\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"}},{\"idJson\":\"57556e74-e7b1-40e1-8557-5ba355f1c858\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"}},{\"idJson\":\"17b435b5-beed-4b1f-8224-bd59d926aef4\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"}},{\"idJson\":\"336146be-d58a-4760-b0db-1317ac06c810\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"}},{\"idJson\":\"950a8776-6af4-4162-9f17-8f7db2577953\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"}},{\"idJson\":\"96acbca6-f035-4c82-ab8f-cd35f472d6f4\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"}},{\"idJson\":\"7da43200-106d-4773-bb42-8d1f799ae655\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"}},{\"idJson\":\"0148ab29-7ea1-4655-a255-9bb97e2e3197\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"}},{\"idJson\":\"4821a2bd-c266-4016-8216-efe9cc294962\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"}},{\"idJson\":\"808d73dc-57d0-484e-ae72-5c85002533e3\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"}},{\"idJson\":\"c04569cb-68a8-42ee-a1b0-19088e035771\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"}},{\"idJson\":\"c908b2fc-1602-48bb-a5d3-f6e586766e88\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"}},{\"idJson\":\"95d9f83d-5a8d-448f-aa65-5b1b51dc1273\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"}},{\"idJson\":\"d217041b-12e9-4679-89be-886f128f6cc2\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"}},{\"idJson\":\"e01c0b60-e208-400e-8595-9c18a6149740\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"}},{\"idJson\":\"392b678c-fd06-4dca-b602-e69b9e38253b\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"}},{\"idJson\":\"70ee363d-a5ea-4942-8719-95d27eea6546\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"}},{\"idJson\":\"e64fca80-0770-415f-880f-8ec459e878db\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"}},{\"idJson\":\"41e2d690-73de-453d-87a6-204ac1cf8025\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"}},{\"idJson\":\"01d16ef9-680b-438a-a025-1184e5d8db55\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"}},{\"idJson\":\"c5f34843-407f-4605-b276-9d8366b74f17\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"}},{\"idJson\":\"449e7118-fda0-4c29-975e-f154d51719dc\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"}},{\"idJson\":\"ea9b73fa-5c6f-4429-a4ab-d3a9b768243e\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"}},{\"idJson\":\"c089a125-ab0b-45c0-bbca-d8c9a1a89495\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"}},{\"idJson\":\"ca99c08d-1f16-495e-a819-21431c5f91eb\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"}},{\"idJson\":\"f56b8f0e-6929-4027-a1cb-75537b21cc34\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"}},{\"idJson\":\"9e76a3f7-f130-427d-aa5d-91a64b569e29\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"}},{\"idJson\":\"84414ad0-f854-43f4-a703-e1dd7640e5c3\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}},{\"idJson\":\"4cc9e065-3e40-4a39-b990-e8b216609be9\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}},{\"idJson\":\"84039b40-27d9-4fb7-a328-c1ce80a233c9\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}},{\"idJson\":\"99ada0db-262c-4ad6-b532-caf2e3b5083d\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}},{\"idJson\":\"7bde2f37-c01e-47df-9a54-bfc622004649\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4d918b32-997e-47a2-85a9-3b761f0fb08f\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"}},{\"idJson\":\"bce281e0-35e3-4d64-aed0-110a47cc0618\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"4ebd97a6-8641-4b02-97b8-d2e3c32001ef\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"}},{\"idJson\":\"ef142e2c-ceac-4fa7-850b-9817652e82c2\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"ba0917f4-398d-414f-84f9-76b3dc859a55\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"}},{\"idJson\":\"54a2cfa5-cc82-4f81-9ea7-5cb8ee5a586a\",\"ativado\":true,\"grupoSemaforico\":{\"idJson\":\"a8d6bba6-d237-4774-9bc7-7e6f7446fa6d\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"}}],\"estagiosPlanos\":[{\"idJson\":\"9750ed04-788c-4ef5-bf5a-0e5e8dbf7b6d\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false},{\"idJson\":\"3410fa85-a2cc-4a04-8e4e-081591d37666\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false},{\"idJson\":\"3604008a-4ffa-4953-8eea-df78fa057c91\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"739d4223-65db-485b-aafb-8a9c383164db\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false},{\"idJson\":\"67d21b13-3e52-4a2b-8bf4-b76ce495e47f\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false,\"tempoEstagio\":13},{\"idJson\":\"5fda79ad-3416-4b18-8e54-c4812f50e624\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false,\"tempoEstagio\":13},{\"idJson\":\"0e95e9a3-7967-46de-8190-f2996a62cc6d\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false,\"origemTransicaoProibida\":false,\"destinoTransicaoProibida\":false,\"tempoEstagio\":14},{\"idJson\":\"7fa3bcbd-b74f-4611-a6d7-a25b21c38036\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"3ae3197a-e2bd-4138-8cec-7f28e93d294b\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"a4a79224-230a-428d-bc66-38323d16a956\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"3e1d577e-95ff-4a04-82a4-a2596f830cc9\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"398c18ff-04b7-4ecc-91cf-0704a118bf65\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"86b8f72e-50de-4b21-9c20-4bfee42f3262\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c984de74-5cee-4089-8979-8320ec717759\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"1b489b32-34a1-4b79-a61d-4d321aeafb46\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"ea5ca3e6-7350-49da-881d-c573584cf86f\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"efc81b9d-7482-4607-9766-b70ae170473c\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"59ae5fde-7e21-4df1-ae2f-71b12ae1b21a\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"312b4200-8687-439e-95cb-98c325ccc09c\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"5ba506a6-d3f8-45c5-a33b-6298652779cf\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"7956a6fb-2f6b-4c56-b40a-a9bea08fb632\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"67710ea4-526a-483a-bea0-b464db08be03\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"4c8c0cc1-935e-4f37-a4cc-34223733cb64\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"efaae49c-a02d-479b-bd96-eac7e9c4cd57\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"11f5b5fe-e552-4a79-b20f-8d1fd8444420\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c5eaf516-65bb-4161-bbf0-440c3c749102\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"bfda04ab-7f8b-4df7-8dd8-60ddb4a1763d\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c17a6ee4-7460-4cd0-9636-8273adcb618c\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"8490a86c-5c93-4c6b-b93e-057f88c61662\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"8e09faad-fa93-45e9-86d0-eb5fec13ca94\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"db0b8d57-0b33-41c4-9c15-66cdfe188d8c\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"3b67f8db-737a-4923-8061-97b325e455ea\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"11046f1f-585e-4d31-890e-c2cae6ac89a3\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c548ec84-3f36-46cf-97d0-2b0f48d76f74\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"62f181a7-c8ce-496b-b33b-d9e8b68db872\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"d940a992-e89e-4c2b-b087-ccac4d73675b\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"35fb699e-ae20-4265-bc29-0c761234c2c0\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"2b6a4435-83bb-4a13-8047-bf8960f75df5\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"edfcf1be-c9c2-4d63-bedc-9bffb133c12e\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"a9ee5a3b-5db7-4a5c-b9f4-b84f16c7ac0d\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"217335a8-20b8-4c7f-b913-f58bed553d51\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"a327cadc-ce0d-4881-b63a-514f2e091374\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"f09ed48a-9ffd-4a05-b69a-60d6997a1118\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"cd530f9f-297d-46de-b540-d08d3d23f0ca\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"db9dc3c9-033b-48f6-865a-efe294021d64\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"94dde215-12ec-4751-be3d-c3471edc227b\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"7fab89df-d4ac-4f18-955f-a08ad3763656\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"fdffe48c-0021-4745-a052-838655e48659\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"31419835-ded2-4530-b1c0-eb625f295305\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"5075c57f-35e8-4cf7-b666-f85f85600b09\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c7a15320-b99b-4baf-815a-9dc2de1e57d4\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"069575fc-53fb-43bd-9b30-535f4311a715\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"df39c1f1-7440-497d-b1b8-3c9554c0782e\",\"estagio\":{\"idJson\":\"9d2b58b4-e959-4673-b19e-eb7ba1a650b5\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"},\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"1df7a44b-c63d-403f-959c-3fc45b406110\",\"estagio\":{\"idJson\":\"80eedeb5-3f3a-41d6-9e08-50d82182b765\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"},\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false},{\"idJson\":\"c35f26a2-db19-4401-b25a-3077675f8cea\",\"estagio\":{\"idJson\":\"417566c8-a91d-4f86-bef1-47bb119a2455\"},\"plano\":{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"},\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false}],\"cidades\":[{\"id\":\"66b66819-a1c4-11e6-970d-0401fa9c1b01\",\"idJson\":\"66b6941e-a1c4-11e6-970d-0401fa9c1b01\",\"nome\":\"São Paulo\",\"areas\":[{\"idJson\":\"66b6a0c4-a1c4-11e6-970d-0401fa9c1b01\"}]}],\"areas\":[{\"id\":\"66b66a46-a1c4-11e6-970d-0401fa9c1b01\",\"idJson\":\"66b6a0c4-a1c4-11e6-970d-0401fa9c1b01\",\"descricao\":1,\"cidade\":{\"idJson\":\"66b6941e-a1c4-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[{\"id\":\"120d8195-d115-4257-a539-27e18fd2c787\",\"idJson\":\"9a334b0c-99a6-4454-99a6-7d746291da8e\",\"nome\":\"Subarea 1\",\"numero\":\"1\"}]}],\"limites\":[],\"todosEnderecos\":[{\"id\":\"5e417a71-9a2c-4815-859b-9eda3c0cc030\",\"idJson\":\"faacf046-021c-46cf-88e7-e227c5835431\",\"localizacao\":\"R. Uelson de Freitas Ramos\",\"latitude\":-22.9738541,\"longitude\":-45.46214140000001,\"localizacao2\":\"\",\"alturaNumerica\":2345},{\"id\":\"36ebefb7-e479-417f-8166-ec90adfc3e7b\",\"idJson\":\"14d5e671-e176-4ff3-be91-f8e68a1b8066\",\"localizacao\":\"R. Uelson de Freitas Ramos\",\"latitude\":-22.9738541,\"longitude\":-45.46214140000001,\"localizacao2\":\"\",\"alturaNumerica\":2345}],\"imagens\":[{\"id\":\"81796d64-ecfd-4c94-8112-26abe61637e7\",\"idJson\":\"8cd63847-2fb3-4d2f-9f42-989d21b45ac7\",\"fileName\":\"00-atletico_mineiro.gif\",\"contentType\":\"image/gif\"},{\"id\":\"f06ac1ee-882f-4449-900e-d4bb7f3e8064\",\"idJson\":\"caf7b770-7f41-416f-8243-0b52b42ebf23\",\"fileName\":\"00-carro.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"cb0f2ac7-562d-42de-8451-e71524900c6e\",\"idJson\":\"9bf6bf33-3d23-465e-b14d-38809ce75096\",\"fileName\":\"00-pessoas.jpg\",\"contentType\":\"image/jpeg\"}],\"atrasosDeGrupo\":[{\"id\":\"5a8a3974-8571-4e64-a8cb-610b588b6dc9\",\"idJson\":\"a9179d78-7e63-4088-8186-ddb0dfa807c0\",\"atrasoDeGrupo\":0},{\"id\":\"915180e1-b146-435c-a0f8-bbcc5e0060a8\",\"idJson\":\"c990887a-f59c-4b3d-8683-90e396f4f4f9\",\"atrasoDeGrupo\":0},{\"id\":\"79a314c0-1cf5-4651-8112-97e51c11c843\",\"idJson\":\"4b6689f1-9358-4dc4-9af3-7bb6f4c8365d\",\"atrasoDeGrupo\":0},{\"id\":\"f6ce74e3-b668-4d5f-8cfb-5bc504a309b8\",\"idJson\":\"a076c266-c191-41b5-9787-873c5d344a60\",\"atrasoDeGrupo\":0},{\"id\":\"b06c5022-1c87-4a98-aeac-7f097df5a5b9\",\"idJson\":\"0d3d2f45-7075-488a-86bd-30975a5e2dec\",\"atrasoDeGrupo\":0},{\"id\":\"046d1004-e930-499a-9b10-56bec1206ba6\",\"idJson\":\"6ba34a91-7f20-407c-8202-93e3597ec2d2\",\"atrasoDeGrupo\":0},{\"id\":\"7c42c20b-5f00-47bd-9b70-13d2936f0a23\",\"idJson\":\"efc3e838-a627-4a9c-9b0c-4211f33c3c82\",\"atrasoDeGrupo\":0},{\"id\":\"fd7ab028-3013-439e-851a-90ea26ade39d\",\"idJson\":\"7a7c4646-0adf-4e34-afd0-826f6b04ea56\",\"atrasoDeGrupo\":0},{\"id\":\"d70a0ccb-66f9-4d10-a1e8-1ef9a9e740ae\",\"idJson\":\"7b330e93-6aa8-4358-9f05-6ff7cebe62f8\",\"atrasoDeGrupo\":0},{\"id\":\"e0ffa7fb-72de-4ef3-8dc8-7df4b5d9c65f\",\"idJson\":\"2eb93812-6a81-4c9c-8acf-b871e6f99f21\",\"atrasoDeGrupo\":0},{\"id\":\"4ab9afaf-f8d7-4556-ae71-658068f071ae\",\"idJson\":\"ada0d0a8-ebab-4dee-a64c-26711eb0bc5d\",\"atrasoDeGrupo\":0},{\"id\":\"45e7e0df-4edf-45bc-a042-7971cc2c22de\",\"idJson\":\"4df0b9b9-dfed-4f19-98f9-85dbb8630f46\",\"atrasoDeGrupo\":0},{\"id\":\"91520f1a-ce88-4da2-91cd-5e215235d7ac\",\"idJson\":\"eab8382f-8e81-4315-836d-0310beb07470\",\"atrasoDeGrupo\":0},{\"id\":\"80692f9f-6609-4cc9-8d70-9d53229a9b4f\",\"idJson\":\"3ec91c1a-ff42-47c0-ae00-dea81d79e1db\",\"atrasoDeGrupo\":0},{\"id\":\"a6c7bd61-eed0-4c44-896c-573ea88c753d\",\"idJson\":\"7be99fea-0bd1-4972-a227-eb15ef7f3519\",\"atrasoDeGrupo\":0},{\"id\":\"9ce65e0d-596e-4fb9-b749-892556e7edc8\",\"idJson\":\"c88d804b-4a43-40fc-97bb-0f5257b26e76\",\"atrasoDeGrupo\":0}],\"statusVersao\":\"EM_CONFIGURACAO\",\"versaoControlador\":{\"id\":\"3cc7788e-1934-41b0-a813-e902f0f4496c\",\"idJson\":null,\"descricao\":\"Controlador criado pelo usuário: Administrador Geral\",\"usuario\":{\"id\":\"66b6c9ec-a1c4-11e6-970d-0401fa9c1b01\",\"nome\":\"Administrador Geral\",\"login\":\"root\",\"email\":\"root@influunt.com.br\"}},\"versoesPlanos\":[{\"idJson\":\"63a86932-4ee9-4229-b5bc-9b56a089bf28\",\"anel\":{\"idJson\":\"e3cc880e-5cd3-4ea1-a6f5-55e58204589f\"},\"planos\":[{\"idJson\":\"da10366f-4794-4a64-8e47-4983680e4d84\"},{\"idJson\":\"4a70366e-13d0-4554-8380-110cc19c71e4\"},{\"idJson\":\"b0dbf9be-d858-4beb-abe4-44889ca11764\"},{\"idJson\":\"7c1dc159-04b8-4de6-bdee-3b26eb0053b7\"},{\"idJson\":\"64e22352-a278-4f00-aaf1-1f25f84ef10f\"},{\"idJson\":\"9a488a7f-9f96-4482-9336-4cc491919022\"},{\"idJson\":\"60c5ba3d-82e5-43b1-9bb5-642780ee98e6\"},{\"idJson\":\"3390b441-f581-455b-82d2-df047df51fe1\"},{\"idJson\":\"291de9b3-e4da-4e85-9b4f-f86947ad5296\"},{\"idJson\":\"2adec08d-7ff8-4b74-83c8-a4aa8d170df0\"},{\"idJson\":\"95b4ad73-78d3-4a53-8bc3-744f43b61a41\"},{\"idJson\":\"971f12d9-eb4e-4895-aced-2e6ed508a99c\"},{\"idJson\":\"028986df-0355-46e3-accd-5400067c436a\"},{\"idJson\":\"32b87037-90eb-48df-85ed-6214dcf5cc7a\"},{\"idJson\":\"d1aff0b5-6879-4304-af4f-7114e5844b3a\"},{\"idJson\":\"0907ae55-7d54-42e0-9b9d-b6ea7f730d1e\"},{\"idJson\":\"148961a3-5b30-462e-a4ff-15e7d8361688\"}]}],\"tabelasHorarias\":[],\"eventos\":[]}";
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(Json.parse(payload));
        List<Erro> erros = getErros(controlador);
        assertThat(erros, empty());
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
            if (epNode.get("idJson").asText() == ep.getIdJson()) {
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

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class, PlanosCheck.class);
    }
}
