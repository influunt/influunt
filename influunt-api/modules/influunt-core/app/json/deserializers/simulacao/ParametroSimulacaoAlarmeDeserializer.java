package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import engine.TipoEvento;
import models.simulador.parametros.ParametroSimulacaoAlarme;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoAlarmeDeserializer extends JsonDeserializer<ParametroSimulacaoAlarme> {

    @Override
    public ParametroSimulacaoAlarme deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoAlarme alarmeParams = new ParametroSimulacaoAlarme();

        if (node.has("alarme") && node.get("alarme").has("codigo")) {
            alarmeParams.setAlarme(TipoEvento.getAlarme(node.get("alarme").get("codigo").asInt()));
        }

        if (node.has("disparo")) {
            alarmeParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        return alarmeParams;
    }
}


