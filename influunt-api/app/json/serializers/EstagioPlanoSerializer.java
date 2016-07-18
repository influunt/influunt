package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.EstagioPlano;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/15/16.
 */
public class EstagioPlanoSerializer extends JsonSerializer<EstagioPlano> {

    @Override
    public void serialize(EstagioPlano estagioPlano, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (estagioPlano.getId() != null) {
            jgen.writeStringField("id", estagioPlano.getId().toString());
        }
        if (estagioPlano.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(estagioPlano.getDataCriacao()));
        }
        if (estagioPlano.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(estagioPlano.getDataAtualizacao()));
        }
        if (estagioPlano.getEstagio() != null) {
            jgen.writeObjectField("estagio", estagioPlano.getEstagio());
        }
        if (estagioPlano.getPlano() != null) {
            jgen.writeObjectField("plano", estagioPlano.getPlano());
        }
        if (estagioPlano.getPosicao() != null) {
            jgen.writeNumberField("posicao", estagioPlano.getPosicao());
        }
        if (estagioPlano.getTempoVerde() != null) {
            jgen.writeNumberField("tempoVerde", estagioPlano.getTempoVerde());
        }
        if (estagioPlano.getTempoVerdeMinimo() != null) {
            jgen.writeNumberField("tempoVerdeMinimo", estagioPlano.getTempoVerdeMinimo());
        }
        if (estagioPlano.getTempoVerdeMaximo() != null) {
            jgen.writeNumberField("tempoVerdeMaximo", estagioPlano.getTempoVerdeMaximo());
        }
        if (estagioPlano.getTempoVerdeIntermediario() != null) {
            jgen.writeNumberField("tempoVerdeIntermediario", estagioPlano.getTempoVerdeIntermediario());
        }
        if (estagioPlano.getTempoExtensaoVerde() != null) {
            jgen.writeNumberField("tempoExtensaoVerde", estagioPlano.getTempoExtensaoVerde());
        }

        jgen.writeBooleanField("dispensavel", estagioPlano.isDispensavel());


        jgen.writeEndObject();
    }
}
