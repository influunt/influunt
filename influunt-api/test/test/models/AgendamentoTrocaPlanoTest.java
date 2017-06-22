package test.models;

import com.fasterxml.jackson.databind.JsonNode;
import engine.AgendamentoTrocaPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.joda.time.DateTime;
import org.junit.Test;
import play.libs.Json;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class AgendamentoTrocaPlanoTest {

    @Test
    public void testJson() {
        DateTime data = new DateTime(2016, 11, 16, 0, 0, 0);
        Plano plano = new Plano();
        plano.setPosicao(14);
        plano.setDescricao("Madrugada");
        plano.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO);

        AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, data);

        JsonNode json = Json.toJson(agendamentoTrocaPlano);

        assertEquals(ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, ModoOperacaoPlano.valueOf(json.get("plano").get("modoOperacao").asText()));
    }


}
