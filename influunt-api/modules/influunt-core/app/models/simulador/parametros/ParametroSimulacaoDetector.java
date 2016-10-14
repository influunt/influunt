package models.simulador.parametros;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.EventoMotor;
import engine.TipoEvento;
import json.deserializers.simulacao.ParametroSimulacaoDetectorDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Detector;
import models.TipoDetector;
import org.joda.time.DateTime;


/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoDetectorDeserializer.class)
public class ParametroSimulacaoDetector {
    private Detector detector;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime disparo;

    public Detector getDetector() {
        return detector;
    }

    public void setDetector(Detector detector) {
        this.detector = detector;
    }

    public DateTime getDisparo() {
        return disparo;
    }

    public void setDisparo(DateTime disparo) {
        this.disparo = disparo;
    }

    public EventoMotor toEvento() {
        if (detector.getTipo().equals(TipoDetector.PEDESTRE)) {
            return new EventoMotor(disparo, TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, detector);
        } else {
            return new EventoMotor(disparo, TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector);
        }
    }
}
