package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.ControladorCustomSerializer;
import models.Controlador;
import play.libs.Json;

import java.util.List;

/**
 * Created by pedropires on 9/26/16.
 */
public class InfluuntResultBuilder {

    private InfluuntQueryResult result;

    public InfluuntResultBuilder(InfluuntQueryResult result) {
        this.result = result;
    }


    public JsonNode toJson() {
        return toJson("dadosBasicos");
    }

    public JsonNode toJson(String serializer) {
        JsonNode dataJson;
        if (result.getKlass().equals(Controlador.class)) {
            switch (serializer) {
                case "dadosBasicos":
                    dataJson = new ControladorCustomSerializer().getControladoresJson((List<Controlador>) result.getResult());
                    break;
                case "agrupamentos":
                    dataJson = new ControladorCustomSerializer().getControladoresAgrupamentos((List<Controlador>) result.getResult());
                    break;
                case "simulação":
                    dataJson = new ControladorCustomSerializer().getControladoresSimulacao((List<Controlador>) result.getResult());
                    break;
                default:
                    throw new RuntimeException("Serializer type not found: " + serializer);
            }
        } else {
            dataJson = Json.toJson(result.getResult());
        }

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        retorno.set("data", dataJson);
        retorno.put("total", result.getTotal());
        return retorno;
    }
}
