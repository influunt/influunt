package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.simulador.parametros.ParametroSimulacaoLiberacaoImposicao;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoLiberacaoImposicaoDeserializer extends JsonDeserializer<ParametroSimulacaoLiberacaoImposicao> {

    @Override
    public ParametroSimulacaoLiberacaoImposicao deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoLiberacaoImposicao liberacaoParams = new ParametroSimulacaoLiberacaoImposicao();

        if (node.has("anel") && node.get("anel").has("posicao")) {
            liberacaoParams.setAnel(node.get("anel").get("posicao").asInt());
        }

        if (node.has("disparo")) {
            liberacaoParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        return liberacaoParams;
    }
}


