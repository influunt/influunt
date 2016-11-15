package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import engine.TipoEvento;
import models.simulador.parametros.ParametroSimulacaoFalha;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoFalhaDeserializer extends JsonDeserializer<ParametroSimulacaoFalha> {

    @Override
    public ParametroSimulacaoFalha deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoFalha falhaParams = new ParametroSimulacaoFalha();

        if (node.has("falha") && node.get("falha").has("codigo")) {
            falhaParams.setFalha(TipoEvento.getFalha(node.get("falha").get("codigo").asInt()));
        }

        if (node.has("disparo")) {
            falhaParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        if (node.has("parametro") && node.get("parametro").has("id")) {
            falhaParams.setParametro(node.get("parametro").get("id").asText());
        }

        return falhaParams;
    }
}


