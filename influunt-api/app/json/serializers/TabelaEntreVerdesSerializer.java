package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.TabelaEntreVerdes;

import java.io.IOException;

/**
 * Created by lesiopinheiro on 7/7/16.
 */
public class TabelaEntreVerdesSerializer extends JsonSerializer<TabelaEntreVerdes> {

    @Override
    public void serialize(TabelaEntreVerdes tabelaEntreVerdes, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (tabelaEntreVerdes.getId() != null) {
            jgen.writeStringField("id", tabelaEntreVerdes.getId().toString());
        }
        if (tabelaEntreVerdes.getDataCriacao() != null) {
            jgen.writeStringField("dataCriacao", InfluuntDateTimeSerializer.parse(tabelaEntreVerdes.getDataCriacao()));
        }
        if (tabelaEntreVerdes.getDataAtualizacao() != null) {
            jgen.writeStringField("dataAtualizacao", InfluuntDateTimeSerializer.parse(tabelaEntreVerdes.getDataAtualizacao()));
        }
        if (tabelaEntreVerdes.getDescricao() != null) {
            jgen.writeStringField("descricao", tabelaEntreVerdes.getDescricao());
        }

        jgen.writeEndObject();
    }
}
