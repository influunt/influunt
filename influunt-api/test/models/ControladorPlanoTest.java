package models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

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

        Plano plano1Anel2 = new Plano();
        plano1Anel2.setAnel(anelCom2Estagios);
        anelCom2Estagios.setPlanos(Arrays.asList(plano1Anel2));

        Plano plano1Anel4 = new Plano();
        plano1Anel4.setAnel(anelCom4Estagios);
        anelCom4Estagios.setPlanos(Arrays.asList(plano1Anel4));

        erros = getErros(controlador);
        assertEquals(12, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.ATUADO);

        erros = getErros(controlador);
        assertEquals(12, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[1].planos[0].modoOperacaoValido"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.APAGADO);

        erros = getErros(controlador);
        assertEquals(11, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        erros = getErros(controlador);
        assertEquals(12, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        plano1Anel2.setTempoCiclo(60);
        plano1Anel2.setDefasagem(100);

        erros = getErros(controlador);
        assertEquals(12, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "deve estar entre 0 e o tempo de ciclo", "aneis[1].planos[0].defasagem"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].modoOperacao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].descricao"),
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[1].planos[0].posicaoTabelaEntreVerde"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
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
            plano1Anel2.addGruposSemaforicos(grupoPlano);
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
        plano1Anel4.addGruposSemaforicos(grupoPlano);

        erros = getErros(controlador);
        assertEquals(6, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].planos[0].modoOperacaoValido"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[0].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.", "aneis[1].planos[0].quantidadeGrupoSemaforicoIgualQuantidadeAnel")
        ));

        criarGrupoSemaforicoPlano(anelCom2Estagios, plano1Anel2);
        criarGrupoSemaforicoPlano(anelCom4Estagios, plano1Anel4);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].planos[0].modoOperacaoValido"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[0].planos[0].quantidadeEstagioIgualQuantidadeAnel"),
                new Erro(CONTROLADOR, "Deve possuir pelo menos 2 estágios configurados.", "aneis[1].planos[0].quantidadeEstagioIgualQuantidadeAnel")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 1, 1, 1});

        erros = getErros(controlador);
        assertEquals(22, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "Configure um detector veicular para cada estágio no modo atuado.", "aneis[0].planos[0].modoOperacaoValido"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[0].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[1].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[1].planos[0].posicaoUnicaEstagio")
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
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[0].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[1].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[1].planos[0].posicaoUnicaEstagio")
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
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[0].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[1].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[1].planos[0].posicaoUnicaEstagio")
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
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[0].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 30 e 255", "aneis[1].planos[0].tempoCiclo"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 255", "aneis[1].planos[0].estagiosPlanos[1].tempoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[0].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[1].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[1].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[2].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[2].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMinimo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeMaximo"),
                new Erro(CONTROLADOR, "deve estar entre 10 e 255", "aneis[0].planos[0].estagiosPlanos[3].tempoVerdeIntermediario"),
                new Erro(CONTROLADOR, "deve estar entre 1 e 10", "aneis[0].planos[0].estagiosPlanos[3].tempoExtensaoVerde"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[1].planos[0].posicaoUnicaEstagio"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[1].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[1].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{1, 2});

        estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        estagioPlano1Anel2.getEstagio().setTempoMaximoPermanenciaAtivado(true);
        estagioPlano1Anel2.getEstagio().setTempoMaximoPermanencia(60);
        estagioPlano1Anel2.setTempoVerde(70);
        plano1Anel2.setTempoCiclo(40);
        estagioPlano2Anel2.setTempoVerde(20);

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(40);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertEquals(4, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O tempo de estagio ultrapassa o tempo maximo de permanencia.", "aneis[1].planos[0].estagiosPlanos[0].ultrapassaTempoMaximoPermanencia"),
                new Erro(CONTROLADOR, "O tempo de verde intermediaria deve estar entre o valor de verde minimo e verde maximo.", "aneis[0].planos[0].estagiosPlanos[0].tempoVerdeIntermediarioFieldEntreMinimoMaximo"),
                new Erro(CONTROLADOR, "A soma dos tempos dos estágios ultrapassa o tempo de ciclo.", "aneis[1].planos[0].ultrapassaTempoCiclo"),
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio")
        ));

        estagioPlano1Anel2.setTempoVerde(21);
        plano1Anel2.setTempoCiclo(60);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeIntermediario(20);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].posicaoUnicaEstagio")
        ));

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
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].sequenciaInvalida")
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
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano1Anel2.setDispensavel(true);

        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "A sequência de estagio não é válida.", "aneis[0].planos[0].sequenciaInvalida")
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
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());

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
                new Erro(CONTROLADOR, "não pode ficar em branco.", "aneis[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavel")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano1Anel4);
        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "deve ser o estágio anterior ou posterior ao estágio dispensável.", "aneis[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano2Anel4);
        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano3Anel4);
        erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "deve ser o estágio anterior ou posterior ao estágio dispensável.", "aneis[0].planos[0].estagiosPlanos[0].estagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido")
        ));

        estagioPlano1Anel4.setEstagioQueRecebeEstagioDispensavel(estagioPlano4Anel4);
        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorPlanos();
        controlador.save();
        List<Erro> erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        List<Erro> erros = getErros(controlador);

        assertNotNull(controlador.getId());
        assertThat(erros, Matchers.empty());

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        assertEquals("Anel 2 estágios possui 1 plano", 1, anelCom2Estagios.getPlanos().size());
        assertEquals("Anel 2 estágios possui 1 plano com 2 estagios", 2, anelCom2Estagios.getPlanos().get(0).getEstagiosPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano", 1, anelCom4Estagios.getPlanos().size());
        assertEquals("Anel 4 estágios possui 1 plano com 4 estagios", 4, anelCom4Estagios.getPlanos().get(0).getEstagiosPlanos().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador));
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
                .uri(controllers.routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
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
                .uri(controllers.routes.PlanosController.create().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador));
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
    public void testAgrupamento() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        Agrupamento agrupamento = new Agrupamento();
        agrupamento.setTipo(TipoAgrupamento.CORREDOR);
        agrupamento.setNome("Corredor Afonso Pena");
        agrupamento.setNumero("1204");
        agrupamento.save();

        controlador.addAgrupamento(agrupamento);
        controlador.save();

        assertEquals("Controlador dentro de 1 agrupamento", 1, controlador.getAgrupamentos().size());

        Anel anel1 = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anel2 = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        Plano planoAnel1 = anel1.getPlanos().stream().filter(plano -> plano.getPosicao().equals(1)).findFirst().get();
        EstagioPlano estagio1Anel1 = planoAnel1.getEstagiosPlanos().get(0);
        EstagioPlano estagio2Anel1 = planoAnel1.getEstagiosPlanos().get(1);

        Plano planoAnel2 = anel2.getPlanos().stream().filter(plano -> plano.getPosicao().equals(1)).findFirst().get();
        EstagioPlano estagio1Anel2 = planoAnel2.getEstagiosPlanos().get(0);
        EstagioPlano estagio2Anel2 = planoAnel2.getEstagiosPlanos().get(1);
        EstagioPlano estagio3Anel2 = planoAnel2.getEstagiosPlanos().get(2);
        EstagioPlano estagio4Anel2 = planoAnel2.getEstagiosPlanos().get(3);

        planoAnel1.setAgrupamento(agrupamento);
        agrupamento.addPlano(planoAnel1);

        List<Erro> erros = getErros(controlador);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O Plano só poderá pertencer a um agrupamento se estiver em modo Coordenado.", "aneis[1].planos[0].agrupamento")
        ));

        planoAnel1.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);
        planoAnel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        planoAnel1.setTempoCiclo(60);
        estagio1Anel1.setTempoVerde(21);
        estagio2Anel1.setTempoVerde(21);

        planoAnel2.setTempoCiclo(128);
        estagio1Anel2.setTempoVerde(10);
        estagio2Anel2.setTempoVerde(10);
        estagio3Anel2.setTempoVerde(10);
        estagio4Anel2.setTempoVerde(10);

        planoAnel2.setAgrupamento(agrupamento);
        agrupamento.addPlano(planoAnel2);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[0].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento"),
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[1].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento")
        ));

        planoAnel1.setTempoCiclo(180);
        estagio1Anel1.setTempoVerde(81);
        estagio2Anel1.setTempoVerde(81);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[0].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento"),
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[1].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento")
        ));

        planoAnel2.setTempoCiclo(140);
        estagio1Anel2.setTempoVerde(12);
        estagio2Anel2.setTempoVerde(17);
        estagio3Anel2.setTempoVerde(13);
        estagio4Anel2.setTempoVerde(10);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[0].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento"),
                new Erro(CONTROLADOR, "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.", "aneis[1].planos[0].tempoCicloIgualOuMultiploDeTodoAgrupamento")
        ));

        planoAnel1.setTempoCiclo(70);
        estagio1Anel1.setTempoVerde(26);
        estagio2Anel1.setTempoVerde(26);

        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Test
    public void testVerdeSeguranca() {
        Controlador controlador = getControladorPlanos();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Plano plano = new Plano();
        plano.setAnel(anelCom4Estagios);
        anelCom4Estagios.setPlanos(Arrays.asList(plano));

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

        //List<Erro> erros = getErros(controlador);

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
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //3 - 1 - 4 - 2
        estagioPlano1.setPosicao(2);
        estagioPlano2.setPosicao(4);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(3);

        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());

        //3 - 2 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(2);
        estagioPlano3.setPosicao(1);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[0].respeitaVerdesDeSeguranca"),
                new Erro(CONTROLADOR, "O tempo de verde está menor que o tempo de segurança configurado.", "aneis[0].planos[0].gruposSemaforicosPlanos[1].respeitaVerdesDeSeguranca")
        ));

        //2 - 3 - 1 - 4
        estagioPlano1.setPosicao(3);
        estagioPlano2.setPosicao(1);
        estagioPlano3.setPosicao(2);
        estagioPlano4.setPosicao(4);

        erros = getErros(controlador);
        assertThat(erros, Matchers.empty());
    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador, Default.class, PlanosCheck.class);
    }

}
