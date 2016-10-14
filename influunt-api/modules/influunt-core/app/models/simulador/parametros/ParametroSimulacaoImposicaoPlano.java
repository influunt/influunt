package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoImposicaoPlanoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoImposicaoPlanoDeserializer.class)
public class ParametroSimulacaoImposicaoPlano {
    private int plano;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime disparo;

    public int getPlano() {
        return plano;
    }

    public void setPlano(int plano) {
        this.plano = plano;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        return new EventoMotor(disparo, TipoEvento.IMPOSICAO_PLANO, plano);
    }

}
