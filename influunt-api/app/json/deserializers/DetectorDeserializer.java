package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import models.Detector;
import models.Estagio;
import models.TipoDetector;
import play.libs.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/11/16.
 */
public class DetectorDeserializer extends JsonDeserializer<Detector> {

    @Override
    public Detector deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Detector detector = new Detector();

        if (node.has("id")) {
            detector.setId(UUID.fromString(node.get("id").asText()));
        }
        if (node.has("posicao")) {
            detector.setPosicao(node.get("posicao").asInt());
        }
        if (node.has("tipo")) {
            detector.setTipo(TipoDetector.valueOf(node.get("tipo").asText()));
        }
        if (node.has("descricao")) {
            detector.setDescricao(node.get("descricao").asText());
        }
        if (node.has("tempoAusenciaDeteccaoMinima")) {
            detector.setTempoAusenciaDeteccaoMinima(node.get("tempoAusenciaDeteccaoMinima").asInt());
        }
        if (node.has("tempoAusenciaDeteccaoMaxima")) {
            detector.setTempoAusenciaDeteccaoMaxima(node.get("tempoAusenciaDeteccaoMaxima").asInt());
        }
        if (node.has("tempoDeteccaoPermanenteMinima")) {
            detector.setTempoDeteccaoPermanenteMinima(node.get("tempoDeteccaoPermanenteMinima").asInt());
        }
        if (node.has("tempoDeteccaoPermanenteMaxima")) {
            detector.setTempoDeteccaoPermanenteMaxima(node.get("tempoDeteccaoPermanenteMaxima").asInt());
        }
        if (node.has("anel")) {
            detector.setAnel(Json.fromJson(node.get("anel"), Anel.class));
        }
        if (node.has("estagio")) {
            detector.setEstagio(Json.fromJson(node.get("estagio"), Estagio.class));
        }

        return detector;
    }
}
