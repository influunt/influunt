package models;

import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplication;
import config.WithInfluuntApplicationAuthenticated;
import controllers.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import org.hamcrest.Matchers;
import org.junit.Before;
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
public class IntervalosTest extends WithInfluuntApplicationAuthenticated{

    private String CONTROLADOR = "Controlador";

    private static ControladorTestUtil controladorTestUtils;

    @Before
    public void setup() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Subarea 1");
        subarea.setNumero(1);
        subarea.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        Usuario usuario = new Usuario();
        usuario.setLogin("abc");
        usuario.setNome("Usuario ABC");
        usuario.setRoot(false);
        usuario.setEmail("abc@influunt.com.br");
        usuario.save();

        controladorTestUtils = new ControladorTestUtil(area, subarea, fabricante, modeloControlador);
    }

    @Test
    public void geraIntervalos() {
        Controlador controlador = controladorTestUtils.getControladorPlanos();
        Anel anel = controlador.getAneis().stream().filter(anel1 -> anel1.getPosicao().equals(1)).findFirst().get();
        Plano plano = anel.getPlanos().stream().filter(plano1 -> plano1.getPosicao().equals(1)).findFirst().get();

        validaIntervalo(plano, 1, 1, EstadoGrupoSemaforico.VERMELHO, 6);
    }

    private void validaIntervalo(Plano plano, int posicaoGrupo, int ordem, EstadoGrupoSemaforico estadoGrupoSemaforico, int tamanho) {
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGruposSemaforicosPlanos().stream().filter(gsp -> gsp.getGrupoSemaforico().getPosicao().equals(posicaoGrupo)).findFirst().get();
        Intervalo intervalo = grupoSemaforicoPlano.getIntervalos().stream().filter(intervalo1 -> intervalo1.getOrdem().equals(ordem)).findFirst().get();
        assertEquals("Estado Grupo Semaforico", estadoGrupoSemaforico.toString(), intervalo.getEstadoGrupoSemaforico().toString());
        assertEquals("Tamanho", tamanho, intervalo.getTamanho().intValue());
    }
}
