package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.*;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AnelSerializer extends JsonSerializer<Anel> {

    @Override
    public void serialize(Anel anel, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (anel.getId() != null) {
            jgen.writeStringField("id", anel.getId().toString());
        }
        if (anel.getNumeroSMEE() != null) {
            jgen.writeStringField("numeroSMEE", anel.getNumeroSMEE().toString());
        }
        if (anel.getDescricao() != null) {
            jgen.writeStringField("descricao", anel.getDescricao());
        }
        if (anel.isAtivo() != null) {
            jgen.writeBooleanField("ativo", anel.isAtivo());
        }
        if (anel.getPosicao() != null) {
            jgen.writeNumberField("posicao", anel.getPosicao());
        }
        if (anel.getLatitude() != null) {
            jgen.writeNumberField("latitude", anel.getLatitude());
        }
        if (anel.getLongitude() != null) {
            jgen.writeNumberField("longitude", anel.getLongitude());
        }
        if (anel.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(anel.getDataCriacao()));
        }
        if (anel.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(anel.getDataAtualizacao()));
        }
        if (anel.getCLA() != null) {
            jgen.writeStringField("CLA", anel.getCLA());
        }
        if (anel.getCroqui() != null) {
            jgen.writeObjectField("croqui", anel.getCroqui());
        }
        if (anel.getEstagios() != null) {
            jgen.writeArrayFieldStart("estagios");
            for (Estagio estagio : anel.getEstagios()) {
                jgen.writeObject(estagio);
            }
            jgen.writeEndArray();
        }
        if (anel.getGruposSemaforicos() != null) {
            jgen.writeArrayFieldStart("gruposSemaforicos");
            for (GrupoSemaforico grp : anel.getGruposSemaforicos()) {
                jgen.writeObject(grp);
            }
            jgen.writeEndArray();
        }
        if (anel.getDetectores() != null) {
            jgen.writeArrayFieldStart("detectores");
            for (Detector detector : anel.getDetectores()) {
                jgen.writeObject(detector);
            }
            jgen.writeEndArray();
        }
        if (anel.getPlanos() != null) {
            jgen.writeArrayFieldStart("planos");
            for (Plano plano : anel.getPlanos()) {
                jgen.writeObject(plano);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
