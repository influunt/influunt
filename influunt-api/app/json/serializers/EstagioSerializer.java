package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Estagio;
import models.TransicaoProibida;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class EstagioSerializer extends JsonSerializer<Estagio> {
    @Override
    public void serialize(Estagio estagio, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (estagio.getId() != null) {
            jgen.writeStringField("id", estagio.getId().toString());
        }
        if (estagio.getImagem() != null) {
            jgen.writeObjectField("imagem", estagio.getImagem());
        }
        if (estagio.getDescricao() != null) {
            jgen.writeStringField("descricao", estagio.getDescricao());
        }
        if (estagio.getTempoMaximoPermanencia() != null) {
            jgen.writeNumberField("tempoMaximoPermanencia", estagio.getTempoMaximoPermanencia());
        }
        if (estagio.getDemandaPrioritaria() != null) {
            jgen.writeBooleanField("demandaPrioritaria", estagio.getDemandaPrioritaria());
        }
        if (estagio.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(estagio.getDataAtualizacao()));
        }
        if (estagio.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(estagio.getDataAtualizacao()));
        }

        if (estagio.getOrigemDeTransicoesProibidas() != null) {
            jgen.writeArrayFieldStart("origemDeTransicoesProibidas");
            for (TransicaoProibida transicaoProibida : estagio.getOrigemDeTransicoesProibidas()) {
                jgen.writeObject(transicaoProibida);
            }
            jgen.writeEndArray();
        }

        if (estagio.getDestinoDeTransicoesProibidas() != null) {
            jgen.writeArrayFieldStart("destinoDeTransicoesProibidas");
            for (TransicaoProibida transicaoProibida : estagio.getDestinoDeTransicoesProibidas()) {
                jgen.writeObject(transicaoProibida);
            }
            jgen.writeEndArray();
        }

        if (estagio.getAlternativaDeTransicoesProibidas() != null) {
            jgen.writeArrayFieldStart("alternativaDeTransicoesProibidas");
            for (TransicaoProibida transicaoProibida : estagio.getAlternativaDeTransicoesProibidas()) {
                jgen.writeObject(transicaoProibida);
            }
            jgen.writeEndArray();
        }


//        jgen.writeArrayFieldStart("areas");
//        for (Area area : estagio.getAreas()) {
//            jgen.writeStartObject();
//            jgen.writeStringField("id", area.getId().toString());
//            jgen.writeNumberField("descricao", area.getLocalizacao());
//            jgen.writeEndObject();
//        }
//        jgen.writeEndArray();


        jgen.writeEndObject();
    }
}
