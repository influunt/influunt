package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoAlarmeDeserializer;
import json.deserializers.simulacao.ParametroSimulacaoDeserializer;
import json.deserializers.simulacao.ParametroSimulacaoFalhaDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Anel;
import models.Detector;
import models.GrupoSemaforico;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoAlarmeDeserializer.class)
public class ParametroSimulacaoAlarme {

    @NotNull(message = "não pode ficar em branco")
    private TipoEvento alarme;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    public EventoMotor toEvento() {
        return null;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public TipoEvento getAlarme() {
        return alarme;
    }

    public void setAlarme(TipoEvento alarme) {
        this.alarme = alarme;
    }
}
