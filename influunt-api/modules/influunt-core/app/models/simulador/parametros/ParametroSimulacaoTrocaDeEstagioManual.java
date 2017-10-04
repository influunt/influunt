package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;


/**
 * Created by rodrigosol on 10/4/16.
 */
public class ParametroSimulacaoTrocaDeEstagioManual {

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime disparo;

    public ParametroSimulacaoTrocaDeEstagioManual(DateTime disparo) {
        this.disparo = disparo;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        return new EventoMotor(disparo, TipoEvento.TROCA_ESTAGIO_MANUAL);
    }
}
