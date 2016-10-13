package integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.junit.Test;

import javax.validation.groups.Default;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class JsonTest extends WithInfluuntApplicationNoAuthentication {


    @Test
    public void jsonPacotePlanos() {
        List<Erro> erros;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());

        erros = getErros(controlador);
            assertThat(erros, org.hamcrest.Matchers.empty());

        validaIntervalos(controlador);

        JsonNode configuracaoControladorJson = new ControladorCustomSerializer().getPacoteConfiguracaoJson(controlador);
        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(configuracaoControladorJson);

        controlador.refresh();

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Controlador novoControlador = new ControladorCustomDeserializer().getPacotesFromJson(configuracaoControladorJson, pacotePlanosJson);

        assertEquals("Não pode ter planos", 0, controladorJson.getAneis().stream().mapToInt(anel -> anel.getPlanos().size()).sum());
        assertEquals("Não pode ter tabela horária", null, controladorJson.getTabelaHoraria());

        erros = getErros(novoControlador);
        assertThat(erros, org.hamcrest.Matchers.empty());

        assertEquals("Deve ter planos", 6, novoControlador.getAneis().stream().mapToInt(anel -> anel.getPlanos().size()).sum());
        assertNotNull("Deve ter 1 tabela horária", novoControlador.getTabelaHoraria());

        validaIntervalos(novoControlador);
    }

    private void validaIntervalos(Controlador controlador) {
        Plano plano = getPlano(1, 1, controlador);
        validaIntervalo(controlador, plano, 1, 1, EstadoGrupoSemaforico.VERMELHO, 6);
        validaIntervalo(controlador, plano, 1, 2, EstadoGrupoSemaforico.VERDE, 12);
        validaIntervalo(controlador, plano, 1, 3, EstadoGrupoSemaforico.AMARELO, 3);
        validaIntervalo(controlador, plano, 1, 4, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, 3);
        validaIntervalo(controlador, plano, 1, 5, EstadoGrupoSemaforico.VERMELHO, 10);
        validaIntervalo(controlador, plano, 1, 6, EstadoGrupoSemaforico.VERMELHO, 18);
        assertEquals("Total de intervalos", totalIntervalo(controlador, plano, 1), 6);

        validaIntervalo(controlador, plano, 2, 1, EstadoGrupoSemaforico.VERDE, 2);
        validaIntervalo(controlador, plano, 2, 2, EstadoGrupoSemaforico.AMARELO, 3);
        validaIntervalo(controlador, plano, 2, 3, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, 3);
        validaIntervalo(controlador, plano, 2, 4, EstadoGrupoSemaforico.VERMELHO, 10);
        validaIntervalo(controlador, plano, 2, 5, EstadoGrupoSemaforico.VERMELHO, 16);
        validaIntervalo(controlador, plano, 2, 6, EstadoGrupoSemaforico.VERMELHO, 8);
        validaIntervalo(controlador, plano, 2, 7, EstadoGrupoSemaforico.VERDE, 10);
        assertEquals("Total de intervalos", totalIntervalo(controlador, plano, 2), 7);

        validaIntervalo(controlador, plano, 3, 1, EstadoGrupoSemaforico.VERMELHO, 18);
        validaIntervalo(controlador, plano, 3, 2, EstadoGrupoSemaforico.VERMELHO, 6);
        validaIntervalo(controlador, plano, 3, 3, EstadoGrupoSemaforico.VERDE, 10);
        validaIntervalo(controlador, plano, 3, 4, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE, 5);
        validaIntervalo(controlador, plano, 3, 5, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, 3);
        validaIntervalo(controlador, plano, 3, 6, EstadoGrupoSemaforico.VERMELHO, 10);
        assertEquals("Total de intervalos", totalIntervalo(controlador, plano, 3), 6);

        validaIntervalo(controlador, plano, 4, 1, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE, 5);
        validaIntervalo(controlador, plano, 4, 2, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, 3);
        validaIntervalo(controlador, plano, 4, 3, EstadoGrupoSemaforico.VERMELHO, 10);
        validaIntervalo(controlador, plano, 4, 4, EstadoGrupoSemaforico.VERMELHO, 6);
        validaIntervalo(controlador, plano, 4, 5, EstadoGrupoSemaforico.VERDE, 10);
        validaIntervalo(controlador, plano, 4, 6, EstadoGrupoSemaforico.VERDE, 18);
        assertEquals("Total de intervalos", totalIntervalo(controlador, plano, 4), 6);

        validaIntervalo(controlador, plano, 5, 1, EstadoGrupoSemaforico.VERMELHO, 8);
        validaIntervalo(controlador, plano, 5, 2, EstadoGrupoSemaforico.VERDE, 10);
        validaIntervalo(controlador, plano, 5, 3, EstadoGrupoSemaforico.VERDE, 16);
        validaIntervalo(controlador, plano, 5, 4, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE, 5);
        validaIntervalo(controlador, plano, 5, 5, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, 3);
        validaIntervalo(controlador, plano, 5, 6, EstadoGrupoSemaforico.VERMELHO, 10);
        assertEquals("Total de intervalos", totalIntervalo(controlador, plano, 5), 6);
    }

    private int totalIntervalo(Controlador controlador, Plano plano, int posicaoGrupo) {
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGruposSemaforicosPlanos().stream().filter(gsp -> gsp.getGrupoSemaforico().getPosicao().equals(posicaoGrupo)).findFirst().get();
        return grupoSemaforicoPlano.getIntervalos().size();
    }

    private void validaIntervalo(Controlador controlador, Plano plano, int posicaoGrupo, int ordem, EstadoGrupoSemaforico estadoGrupoSemaforico, int tamanho) {
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGruposSemaforicosPlanos().stream().filter(gsp -> gsp.getGrupoSemaforico().getPosicao().equals(posicaoGrupo)).findFirst().get();
        Intervalo intervalo = grupoSemaforicoPlano.getIntervalos().stream().filter(intervalo1 -> intervalo1.getOrdem().equals(ordem)).findFirst().get();
        assertEquals("Estado Grupo Semaforico", estadoGrupoSemaforico.toString(), intervalo.getEstadoGrupoSemaforico().toString());
        assertEquals("Tamanho", tamanho, intervalo.getTamanho().intValue());

    }

    private Plano getPlano(int posicaoPlano, int posicaoAnel, Controlador controlador) {
        Anel anel = controlador.getAneis().stream().filter(anel1 -> anel1.getPosicao().equals(posicaoAnel)).findFirst().get();
        return anel.getPlanos().stream().filter(plano -> plano.getPosicao().equals(posicaoPlano)).findFirst().get();
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }
}
