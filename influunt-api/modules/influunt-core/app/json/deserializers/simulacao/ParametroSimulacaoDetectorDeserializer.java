package json.deserializers.simulacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Detector;
import models.simulador.parametros.ParametroSimulacaoDetector;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import play.libs.Json;

import java.io.IOException;

/**
 * Created by pedropires on 6/19/16.
 */
public class ParametroSimulacaoDetectorDeserializer extends JsonDeserializer<ParametroSimulacaoDetector> {

    @Override
    public ParametroSimulacaoDetector deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ParametroSimulacaoDetector detectorParams = new ParametroSimulacaoDetector();

        if (node.has("detector")) {
            detectorParams.setDetector(Json.fromJson(node.get("detector"), Detector.class));
        }

        if (node.has("disparo")) {
            detectorParams.setDisparo(DateTime.parse(node.get("disparo").asText(), ISODateTimeFormat.dateTimeParser()));
        }

        return detectorParams;
    }
}


