package models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.Movimento;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class MovimentoSerializer extends JsonSerializer<Movimento> {
    @Override
    public void serialize(Movimento movimento, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", movimento.getId().toString());
        if (movimento.getDescricao() != null) {
            jgen.writeStringField("desricao", movimento.getDescricao());
        }
        if (movimento.getImagem() != null) {
            jgen.writeObjectField("imagem", movimento.getImagem());
        }

        if (movimento.getEstagio() != null) {
            jgen.writeObjectField("estagio", movimento.getEstagio());
        }

        if (movimento.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", movimento.getDataCriacao().toString());
        }
        if (movimento.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", movimento.getDataAtualizacao().toString());
        }
        jgen.writeEndObject();
    }
}
