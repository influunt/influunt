package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.Controlador;
import models.GrupoSemaforico;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ControladorSerializer extends JsonSerializer<Controlador> {

    @Override
    public void serialize(Controlador controlador, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if(controlador.getId() != null) {
            jgen.writeStringField("id", controlador.getId().toString());
        }
        if (controlador.getLocalizacao() != null) {
            jgen.writeStringField("descricao", controlador.getLocalizacao());
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
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(controlador.getDataCriacao()));
        }
        if (controlador.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(controlador.getDataAtualizacao()));
        }
        jgen.writeStringField("CLC", controlador.getCLC());

        jgen.writeObjectField("area", controlador.getArea());
        jgen.writeObjectField("modelo", controlador.getModelo());

        jgen.writeArrayFieldStart("aneis");
        for (Anel anel : controlador.getAneis()) {
            jgen.writeObject(anel);
        }
        jgen.writeEndArray();

        jgen.writeArrayFieldStart("gruposSemaforicos");
        for (GrupoSemaforico grupoSemaforico : controlador.getGruposSemaforicos()) {
            jgen.writeObject(grupoSemaforico);
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
