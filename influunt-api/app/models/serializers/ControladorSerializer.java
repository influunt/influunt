package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.Controlador;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ControladorSerializer extends JsonSerializer<Controlador> {

    @Override
    public void serialize(Controlador controlador, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", controlador.getId());
        if (controlador.getDescricao() != null) {
            jgen.writeStringField("descricao", controlador.getDescricao());
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
        if (controlador.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", controlador.getDataCriacao().toString());
        }
        if (controlador.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", controlador.getDataAtualizacao().toString());
        }
        jgen.writeStringField("idControlador", controlador.getIdControlador());

        jgen.writeObjectField("area", controlador.getArea());
        jgen.writeObjectField("modelo", controlador.getModelo());

        jgen.writeArrayFieldStart("aneis");
        for (Anel anel : controlador.getAneis()) {
            jgen.writeObject(anel);
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
