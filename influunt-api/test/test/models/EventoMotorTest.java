package test.models;

import com.fasterxml.jackson.databind.JsonNode;
import engine.EventoMotor;
import engine.TipoEvento;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Test;
import play.libs.Json;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EventoMotorTest {

    @Test
    public void testJson() {
        DateTime data = new DateTime(2016, 11, 16, 0, 0, 0);
        EventoMotor eventoMotor = new EventoMotor(data,
            TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO,
            2);

        JsonNode json = Json.toJson(eventoMotor);

        assertEquals(TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO, TipoEvento.valueOf(json.get("tipoEvento").get("tipo").asText()));

        assertEquals("Anel 2: Desrespeito ao tempo máximo de permanência no estágio", json.get("descricaoEvento").asText());

        eventoMotor = new EventoMotor(data,
            TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,
            new Pair<Integer, TipoDetector>(1, TipoDetector.PEDESTRE),
            3);

        json = Json.toJson(eventoMotor);

        assertEquals("Anel 3: Falha no DP1 - Acionamento Direto", json.get("descricaoEvento").asText());


        eventoMotor = new EventoMotor(data,
            TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO,
            new Pair<Integer, TipoDetector>(2, TipoDetector.VEICULAR),
            3);

        json = Json.toJson(eventoMotor);

        assertEquals("Anel 3: Falha no DV2 - Falta de Acionamento", json.get("descricaoEvento").asText());


        eventoMotor = new EventoMotor(data,
            TipoEvento.IMPOSICAO_PLANO,
            15,
            2,
            60);
        json = Json.toJson(eventoMotor);

        assertEquals("Anel 2: Plano 15 foi imposto com duração de 60 minutos", json.get("descricaoEvento").asText());
    }


}
