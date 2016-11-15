package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoDetectorDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;


/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoDetectorDeserializer.class)
public class ParametroSimulacaoDetector {

    @NotNull(message = "não pode ficar em branco")
    private Pair<Integer, TipoDetector> detector;

    @NotNull(message = "não pode ficar em branco")
    private Integer anel;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime disparo;

    public Pair<Integer, TipoDetector> getDetector() {
        return detector;
    }

    public void setDetector(Pair<Integer, TipoDetector> detector) {
        this.detector = detector;
    }

    public Integer getAnel() {
        return anel;
    }

    public void setAnel(Integer anel) {
        this.anel = anel;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        if (TipoDetector.PEDESTRE.equals(detector.getSecond())) {
            return new EventoMotor(disparo, TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector, anel);
        } else {
            return new EventoMotor(disparo, TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector, anel);
        }
    }
}
