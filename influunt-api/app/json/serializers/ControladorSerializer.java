package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.Controlador;
import utils.RangeUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ControladorSerializer extends JsonSerializer<Controlador> {

    @Override
    public void serialize(Controlador controlador, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (controlador.getId() != null) {
            jgen.writeStringField("id", controlador.getId().toString());
        }
        if (controlador.getLocalizacao() != null) {
            jgen.writeStringField("localizacao", controlador.getLocalizacao());
        }
        if (controlador.getNumeroSMEE() != null) {
            jgen.writeStringField("numeroSMEE", controlador.getNumeroSMEE());
        }
        if (controlador.getNumeroSMEEConjugado1() != null) {
            jgen.writeStringField("numeroSMEEConjugado1", controlador.getNumeroSMEEConjugado1());
        }
        if (controlador.getNumeroSMEEConjugado2() != null) {
            jgen.writeStringField("numeroSMEEConjugado2", controlador.getNumeroSMEEConjugado2());
        }
        if (controlador.getNumeroSMEEConjugado3() != null) {
            jgen.writeStringField("numeroSMEEConjugado3", controlador.getNumeroSMEEConjugado3());
        }
        if (controlador.getFirmware() != null) {
            jgen.writeStringField("firmware", controlador.getFirmware());
        }
        if (controlador.getLatitude() != null) {
            jgen.writeNumberField("latitude", controlador.getLatitude());
        }
        if (controlador.getLongitude() != null) {
            jgen.writeNumberField("longitude", controlador.getLongitude());
        }
        if (controlador.getLimiteEstagio() != null) {
            jgen.writeNumberField("limiteEstagio", controlador.getLimiteEstagio());
        }
        if (controlador.getLimiteGrupoSemaforico() != null) {
            jgen.writeNumberField("limiteGrupoSemaforico", controlador.getLimiteGrupoSemaforico());
        }
        if (controlador.getLimiteAnel() != null) {
            jgen.writeNumberField("limiteAnel", controlador.getLimiteAnel());
        }
        if (controlador.getLimiteDetectorPedestre() != null) {
            jgen.writeNumberField("limiteDetectorPedestre", controlador.getLimiteDetectorPedestre());
        }
        if (controlador.getLimiteDetectorVeicular() != null) {
            jgen.writeNumberField("limiteDetectorVeicular", controlador.getLimiteDetectorVeicular());
        }
        if (controlador.getLimiteTabelasEntreVerdes() != null) {
            jgen.writeNumberField("limiteTabelasEntreVerdes", controlador.getLimiteTabelasEntreVerdes());
        }
        if (controlador.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));
        }
        if (controlador.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));
        }
        if (controlador.getCLC() != null) {
            jgen.writeStringField("CLC", controlador.getCLC());
        }
        if (controlador.getStatusControlador() != null) {
            jgen.writeStringField("statusControlador", controlador.getStatusControlador().toString());
        }

        if (controlador.getArea() != null) {
            jgen.writeObjectField("area", controlador.getArea());
        }
        if (controlador.getModelo() != null) {
            jgen.writeObjectField("modelo", controlador.getModelo());
        }
        jgen.writeArrayFieldStart("aneis");
        for (Anel anel : controlador.getAneis()) {
            jgen.writeObject(anel);
        }
        jgen.writeEndArray();

        // TODO - pensar melhor maneira para pegar faixa de valores
//        jgen.writeArrayFieldStart("faixaValores");
        jgen.writeStringField("verdeMin", RangeUtils.TEMPO_VERDE.getMin().toString());
        jgen.writeStringField("verdeMax", RangeUtils.TEMPO_VERDE.getMax().toString());
        jgen.writeStringField("verdeMinimoMin", RangeUtils.TEMPO_VERDE_MINIMO.getMin().toString());
        jgen.writeStringField("verdeMinimoMax", RangeUtils.TEMPO_VERDE_MINIMO.getMax().toString());
        jgen.writeStringField("verdeMaximoMin", RangeUtils.TEMPO_VERDE_MAXIMO.getMin().toString());
        jgen.writeStringField("verdeMaximoMax", RangeUtils.TEMPO_VERDE_MAXIMO.getMax().toString());
        jgen.writeStringField("extensaVerdeMin", RangeUtils.TEMPO_EXTENSAO_VERDE.getMin().toString());
        jgen.writeStringField("extensaVerdeMax", RangeUtils.TEMPO_EXTENSAO_VERDE.getMax().toString());
        jgen.writeStringField("verdeIntermediarioMin", RangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMin().toString());
        jgen.writeStringField("verdeIntermediarioMax", RangeUtils.TEMPO_VERDE_INTERMEDIARIO.getMax().toString());
        jgen.writeStringField("defasagemMin", RangeUtils.TEMPO_DEFASAGEM.getMin().toString());
        jgen.writeStringField("defasagemMax", RangeUtils.TEMPO_DEFASAGEM.getMax().toString());
        jgen.writeStringField("amareloMin", RangeUtils.TEMPO_AMARELO.getMin().toString());
        jgen.writeStringField("amareloMax", RangeUtils.TEMPO_AMARELO.getMax().toString());
        jgen.writeStringField("vermelhoIntermitenteMin", RangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMin().toString());
        jgen.writeStringField("vermelhoIntermitenteMax", RangeUtils.TEMPO_VERMELHO_INTERMITENTE.getMax().toString());
        jgen.writeStringField("vermelhoLimpezaVeicularMin", RangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMin().toString());
        jgen.writeStringField("vermelhoLimpezaVeicularMax", RangeUtils.TEMPO_VERMELHO_LIMPEZA_VEICULAR.getMax().toString());
        jgen.writeStringField("vermelhoLimpezaPedestreMin", RangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMin().toString());
        jgen.writeStringField("vermelhoLimpezaPedestreMax", RangeUtils.TEMPO_VERMELHO_LIMPEZA_PEDESTRE.getMax().toString());
        jgen.writeStringField("atrasoGrupoMin", RangeUtils.TEMPO_ATRASO_GRUPO.getMin().toString());
        jgen.writeStringField("atrasoGrupoMax", RangeUtils.TEMPO_ATRASO_GRUPO.getMax().toString());
        jgen.writeStringField("verdeSegurancaVeicularMin", RangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMin().toString());
        jgen.writeStringField("verdeSegurancaVeicularMax", RangeUtils.TEMPO_VERDE_SEGURANCA_VEICULAR.getMax().toString());
        jgen.writeStringField("verdeSegurancaPedestreMin", RangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMin().toString());
        jgen.writeStringField("verdeSegurancaPedestreMax", RangeUtils.TEMPO_VERDE_SEGURANCA_PEDESTRE.getMax().toString());
        jgen.writeStringField("maximoPermanenciaEstagioMin", RangeUtils.TEMPO_MAXIMO_PERMANECIA_ESTAGIO.getMin().toString());
        jgen.writeStringField("maximoPermanenciaEstagioMax", RangeUtils.TEMPO_MAXIMO_PERMANECIA_ESTAGIO.getMax().toString());
        jgen.writeStringField("cicloMin", RangeUtils.TEMPO_CICLO.getMin().toString());
        jgen.writeStringField("cicloMax", RangeUtils.TEMPO_CICLO.getMax().toString());

//        jgen.writeEndArray();


        jgen.writeEndObject();
    }
}
