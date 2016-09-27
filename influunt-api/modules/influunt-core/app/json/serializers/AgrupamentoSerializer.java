package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.*;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class AgrupamentoSerializer extends JsonSerializer<Agrupamento> {

    @Override
    public void serialize(Agrupamento agrupamento, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if (agrupamento.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeStringField("id", agrupamento.getId().toString());
        }

        if (agrupamento.getNome() != null) {
            jgen.writeStringField("nome", agrupamento.getNome());
        }

        if (agrupamento.getNumero() != null) {
            jgen.writeStringField("numero", agrupamento.getNumero());
        }

        if (agrupamento.getDescricao() != null) {
            jgen.writeStringField("descricao", agrupamento.getDescricao());
        }

        if (agrupamento.getTipo() != null) {
            jgen.writeStringField("tipo", agrupamento.getTipo().toString());
        }

        if (agrupamento.getDiaDaSemana() != null) {
            jgen.writeStringField("diaDaSemana", agrupamento.getDiaDaSemana().name());
        }

        if (agrupamento.getHorario() != null) {
            jgen.writeStringField("horario", agrupamento.getHorario().toString());
        }

        if (agrupamento.getPosicaoPlano() != null) {
            jgen.writeStringField("posicaoPlano", agrupamento.getPosicaoPlano().toString());
        }

        if (agrupamento.getAneis() != null) {
            jgen.writeArrayFieldStart("aneis");
            for (Anel anel : agrupamento.getAneis()) {
                jgen.writeStartObject();
                jgen.writeStringField("id", anel.getId().toString());
                jgen.writeStringField("CLA", anel.getCLA());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }

        if (agrupamento.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(agrupamento.getDataCriacao()));
        }

        if (agrupamento.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(agrupamento.getDataAtualizacao()));
        }

        jgen.writeEndObject();
    }
}
