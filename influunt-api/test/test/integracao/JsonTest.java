package test.integracao;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Cidade;
import models.Controlador;
import org.junit.Test;
import test.config.WithInfluuntApplicationNoAuthentication;
import utils.RangeUtils;

import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class JsonTest extends WithInfluuntApplicationNoAuthentication {


    @Test
    public void jsonPacotePlanos() {
        List<Erro> erros;
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());

        erros = getErros(controlador).stream().sorted((e1, e2) -> e1.path.compareTo(e2.path)).collect(Collectors.toList());
        assertThat(erros, org.hamcrest.Matchers.empty());

        JsonNode configuracaoControladorJson = new ControladorCustomSerializer().getPacoteConfiguracaoJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null));
        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(configuracaoControladorJson);

        controlador.refresh();

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        JsonNode pacoteTabelaHorariaJson = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);


        Controlador novoControlador = new ControladorCustomDeserializer().getPacoteConfiguracaoCompletaFromJson(configuracaoControladorJson, pacotePlanosJson, pacoteTabelaHorariaJson);


        assertEquals("Não pode ter planos", 0, controladorJson.getAneis().stream().mapToInt(anel -> anel.getPlanos().size()).sum());
        assertEquals("Não pode ter tabela horária", null, controladorJson.getTabelaHoraria());

        erros = getErros(novoControlador);
        assertThat(erros, org.hamcrest.Matchers.empty());

        assertEquals("Deve ter planos", 36, novoControlador.getAneis().stream().mapToInt(anel -> anel.getPlanos().size()).sum());
        assertNotNull("Deve ter 1 tabela horária", novoControlador.getTabelaHoraria());

    }

    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, PlanosCentralCheck.class,
            TabelaHorariosCheck.class);
    }
}
