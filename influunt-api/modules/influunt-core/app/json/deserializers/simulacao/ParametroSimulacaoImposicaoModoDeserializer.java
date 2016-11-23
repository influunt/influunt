package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.ModoOperacaoPlano;
import models.simulador.parametros.ParametroSimulacaoImposicaoModo;
import models.simulador.parametros.ParametroSimulacaoImposicaoPlano;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoImposicaoModoDeserializer extends JsonDeserializer<ParametroSimulacaoImposicaoModo> {

    @Override
    public ParametroSimulacaoImposicaoModo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoImposicaoModo imposicaoParams = new ParametroSimulacaoImposicaoModo();

        if (node.has("modo")) {
            imposicaoParams.setModoOperacao(node.get("modo").asText());
        }

        if (node.has("anel") && node.get("anel").has("posicao")) {
            imposicaoParams.setAnel(node.get("anel").get("posicao").asInt());
        }

        if (node.has("disparo")) {
            imposicaoParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        if (node.has("duracao")) {
            imposicaoParams.setDuracao(node.get("duracao").asInt());
        }

        return imposicaoParams;
    }
}


