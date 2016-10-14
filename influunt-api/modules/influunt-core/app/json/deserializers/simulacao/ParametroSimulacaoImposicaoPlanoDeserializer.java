package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.simulador.parametros.ParametroSimulacaoImposicaoPlano;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoImposicaoPlanoDeserializer extends JsonDeserializer<ParametroSimulacaoImposicaoPlano> {

    @Override
    public ParametroSimulacaoImposicaoPlano deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoImposicaoPlano imposicaoParams = new ParametroSimulacaoImposicaoPlano();

        if (node.has("plano") && node.get("plano").has("posicao")) {
            imposicaoParams.setPlano(node.get("plano").get("posicao").asInt());
        }

        if (node.has("disparo")) {
            imposicaoParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        return imposicaoParams;
    }
}


