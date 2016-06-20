package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Anel;
import models.GrupoSemaforico;
import models.Movimento;

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
        if (anel.getQuantidadeGrupoPedestre() != null) {
            jgen.writeNumberField("quantidadeGrupoPedestre", anel.getQuantidadeGrupoPedestre());
        }
        if (anel.getQuantidadeGrupoVeicular() != null) {
            jgen.writeNumberField("quantidadeGrupoVeicular", anel.getQuantidadeGrupoVeicular());
        }
        if (anel.getQuantidadeDetectorPedestre() != null) {
            jgen.writeNumberField("quantidadeDetectorPedestre", anel.getQuantidadeDetectorPedestre());
        }
        if (anel.getQuantidadeDetectorVeicular() != null) {
            jgen.writeNumberField("quantidadeDetectorVeicular", anel.getQuantidadeDetectorVeicular());
        }

        if (anel.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(anel.getDataCriacao()));
        }
        if (anel.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(anel.getDataAtualizacao()));
        }

        jgen.writeArrayFieldStart("movimentos");
        for (Movimento mov : anel.getMovimentos()) {
            jgen.writeObject(mov);
        }
        jgen.writeEndArray();

        jgen.writeArrayFieldStart("gruposSemaforicos");
        for (GrupoSemaforico grp : anel.getGruposSemaforicos()) {
            jgen.writeObject(grp);
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
