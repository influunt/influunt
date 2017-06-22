package test.models;

import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.api.routes;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import utils.RangeUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class ControladorDadosBasicosTest extends ControladorTest {

    private String CONTROLADOR = "Controlador";

    @Override
    @Test
    public void testVazio() {

        List<Erro> erros = getErros(getControlador());

        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro(CONTROLADOR, "não pode ficar em branco", "modelo"),
            new Erro(CONTROLADOR, "não pode ficar em branco", "area")
        ));

    }

    @Override
    @Test
    public void testNoValidationErro() {
        List<Erro> erros = getErros(getControladorDadosBasicos());
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorDadosBasicos();
        Controlador controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControlador(controlador, controladorJson);

        controlador.save();

        controladorJson = new ControladorCustomDeserializer().getControladorFromJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControlador(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());

        assertFaixaValores(controlador);

    }

    private void assertFaixaValores(Controlador controlador) {
        JsonNode controladorJSON = new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null));
        RangeUtils rangeUtils = RangeUtils.getInstance(null);

        assertEquals("verdeMinimoMin", controladorJSON.get("verdeMinimoMin").asText(), rangeUtils.TEMPO_VERDE_MINIMO.getMin().toString());
        assertEquals("verdeMinimoMax", controladorJSON.get("verdeMinimoMax").asText(), rangeUtils.TEMPO_VERDE_MINIMO.getMax().toString());
        assertEquals("verdeMaximoMin", controladorJSON.get("verdeMaximoMin").asText(), rangeUtils.TEMPO_VERDE_MAXIMO.getMin().toString());
        assertEquals("verdeMaximoMax", controladorJSON.get("verdeMaximoMax").asText(), rangeUtils.TEMPO_VERDE_MAXIMO.getMax().toString());
        assertEquals("extensaoVerdeMin", controladorJSON.get("extensaoVerdeMin").asText(), rangeUtils.TEMPO_EXTENSAO_VERDE.getMin().toString());
        assertEquals("extensaoVerdeMax", controladorJSON.get("extensaoVerdeMax").asText(), rangeUtils.TEMPO_EXTENSAO_VERDE.getMax().toString());
        assertEquals("verdeIntermediarioMin", controladorJSON.get("verdeIntermediarioMin").asText(), rangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMin().toString());
        assertEquals("verdeIntermediarioMax", controladorJSON.get("verdeIntermediarioMax").asText(), rangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMax().toString());
        assertEquals("defasagemMin", controladorJSON.get("defasagemMin").asText(), rangeUtils.TEMPO_DEFASAGEM.getMin().toString());
        assertEquals("defasagemMax", controladorJSON.get("defasagemMax").asText(), rangeUtils.TEMPO_DEFASAGEM.getMax().toString());
        assertEquals("amareloMin", controladorJSON.get("amareloMin").asText(), rangeUtils.TEMPO_AMARELO.getMin().toString());
        assertEquals("amareloMax", controladorJSON.get("amareloMax").asText(), rangeUtils.TEMPO_AMARELO.getMax().toString());
        assertEquals("vermelhoIntermitenteMin", controladorJSON.get("vermelhoIntermitenteMin").asText(), rangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMin().toString());
        assertEquals("vermelhoIntermitenteMax", controladorJSON.get("vermelhoIntermitenteMax").asText(), rangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMax().toString());
        assertEquals("vermelhoLimpezaVeicularMin", controladorJSON.get("vermelhoLimpezaVeicularMin").asText(), rangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMin().toString());
        assertEquals("vermelhoLimpezaVeicularMax", controladorJSON.get("vermelhoLimpezaVeicularMax").asText(), rangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMax().toString());
        assertEquals("vermelhoLimpezaPedestreMin", controladorJSON.get("vermelhoLimpezaPedestreMin").asText(), rangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMin().toString());
        assertEquals("vermelhoLimpezaPedestreMax", controladorJSON.get("vermelhoLimpezaPedestreMax").asText(), rangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMax().toString());
        assertEquals("atrasoGrupoMin", controladorJSON.get("atrasoGrupoMin").asText(), rangeUtils.TEMPO_ATRASO_GRUPO.getMin().toString());
        assertEquals("atrasoGrupoMax", controladorJSON.get("atrasoGrupoMax").asText(), rangeUtils.TEMPO_ATRASO_GRUPO.getMax().toString());
        assertEquals("verdeSegurancaVeicularMin", controladorJSON.get("verdeSegurancaVeicularMin").asText(), rangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMin().toString());
        assertEquals("verdeSegurancaVeicularMax", controladorJSON.get("verdeSegurancaVeicularMax").asText(), rangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMax().toString());
        assertEquals("verdeSegurancaPedestreMin", controladorJSON.get("verdeSegurancaPedestreMin").asText(), rangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMin().toString());
        assertEquals("verdeSegurancaPedestreMax", controladorJSON.get("verdeSegurancaPedestreMax").asText(), rangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMax().toString());
        assertEquals("maximoPermanenciaEstagioMin", controladorJSON.get("maximoPermanenciaEstagioMin").asText(), rangeUtils.TEMPO_MAXIMO_PERMANENCIA_ESTAGIO.getMin().toString());
        assertEquals("maximoPermanenciaEstagioMax", controladorJSON.get("maximoPermanenciaEstagioMax").asText(), rangeUtils.TEMPO_MAXIMO_PERMANENCIA_ESTAGIO.getMax().toString());
        assertEquals("cicloMin", controladorJSON.get("cicloMin").asText(), rangeUtils.TEMPO_CICLO.getMin().toString());
        assertEquals("cicloMax", controladorJSON.get("cicloMax").asText(), rangeUtils.TEMPO_CICLO.getMax().toString());
    }

    private void assertControlador(Controlador controlador, Controlador controladorJson) {
        assertEquals(controlador.getArea().getId(), controladorJson.getArea().getId());
        assertEquals(controlador.getModelo().getId(), controladorJson.getModelo().getId());
        assertEquals(controlador.getNumeroSMEE(), controladorJson.getNumeroSMEE());
        assertEquals(controlador.getNumeroSMEEConjugado1(), controladorJson.getNumeroSMEEConjugado1());
        assertEquals(controlador.getNumeroSMEEConjugado2(), controladorJson.getNumeroSMEEConjugado2());
        assertEquals(controlador.getNumeroSMEEConjugado3(), controladorJson.getNumeroSMEEConjugado3());
        Endereco enderecoPaulista = controlador.getEndereco();
        Endereco enderecoPaulistaJson = controladorJson.getEndereco();

        assertEquals(enderecoPaulista.getLocalizacao(), enderecoPaulistaJson.getLocalizacao());
        assertEquals(enderecoPaulista.getLatitude(), enderecoPaulistaJson.getLatitude());
        assertEquals(enderecoPaulista.getLongitude(), enderecoPaulistaJson.getLongitude());
        assertEquals(enderecoPaulista.getLocalizacao2(), enderecoPaulistaJson.getLocalizacao2());
        assertEquals(enderecoPaulista.getAlturaNumerica(), enderecoPaulistaJson.getAlturaNumerica());
        assertEquals(enderecoPaulista.getReferencia(), enderecoPaulistaJson.getReferencia());
        assertEquals(controlador.getFirmware(), controladorJson.getFirmware());
    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControlador();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        List<LinkedHashMap<String, String>> errosJson = Json.fromJson(json, List.class);
        List<Erro> erros = errosJson.stream().map(erro -> new Erro(erro.get("root"), erro.get("message"), erro.get("path"))).collect(Collectors.toList());

        assertEquals(3, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("Controlador", "não pode ficar em branco", "modelo"),
            new Erro("Controlador", "não pode ficar em branco", "area"),
            new Erro("Controlador", "não pode ficar em branco", "nomeEndereco")
        ));
    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorDadosBasicos();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
            .uri(routes.ControladoresController.dadosBasicos().url()).bodyJson(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null)));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = new ControladorCustomDeserializer().getControladorFromJson((json));

        assertControlador(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertNotNull("Versao Controlador", controladorRetornado.getVersaoControlador());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Todoas aneis inativos", 0, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Versao Controlador", controlador.getVersaoControlador().getIdJson(), controladorRetornado.getVersaoControlador().getIdJson());

    }

    @Override
    public List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador);
    }

    @Test
    public void testStatusControlador() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();
        assertEquals(StatusVersao.EM_CONFIGURACAO, Controlador.find.byId(controlador.getId()).getVersaoControlador().getStatusVersao());
    }

    @Test
    public void testCLC() {
        Cidade cidade = new Cidade();
        cidade.setNome("BH");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(2);
        area.save();

        Controlador c1A1 = getControladorDadosBasicos();
        Controlador c2A1 = getControladorDadosBasicos();

        Controlador c1A2 = getControladorDadosBasicos();
        c1A2.setArea(area);
        c1A2.setSubarea(null);

        Controlador c2A2 = getControladorDadosBasicos();
        c2A2.setArea(area);
        c2A2.setSubarea(null);

        c1A1.save();
        c2A1.save();
        c1A2.save();
        c2A2.save();

        assertEquals(c1A1.getArea().getId().toString(), c2A1.getArea().getId().toString());
        assertEquals(c1A2.getArea().getId().toString(), c2A2.getArea().getId().toString());

        assertEquals("1.001.0001", c1A1.getCLC());
        assertEquals("1.001.0002", c2A1.getCLC());
        assertEquals("2.000.0001", c1A2.getCLC());
        assertEquals("2.000.0002", c2A2.getCLC());

        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNumero(234);
        subarea.save();

        c1A2.setSubarea(subarea);

        assertEquals("1.001.0001", c1A1.getCLC());
        assertEquals("1.001.0002", c2A1.getCLC());
        assertEquals("2.234.0001", c1A2.getCLC());
        assertEquals("2.000.0002", c2A2.getCLC());
    }

}
