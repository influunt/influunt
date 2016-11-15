package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import engine.TipoEvento;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class TipoEventoSerializer extends JsonSerializer<TipoEvento> {

    @Override
    public void serialize(TipoEvento tipoEvento, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("tipo", tipoEvento.toString());
        jgen.writeNumberField("codigo", tipoEvento.getCodigo());
        jgen.writeStringField("descricao", tipoEvento.getDescricao());

        if (tipoEvento.getParamsDescriptor() != null) {
            jgen.writeStringField("descricaoParam", tipoEvento.getParamsDescriptor().getNome());
            jgen.writeStringField("tipoParam", tipoEvento.getParamsDescriptor().getTipo().toString());
        }

        jgen.writeEndObject();
    }
}
