package engine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.EventoMotorSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by rodrigosol on 9/28/16.
 */
@JsonSerialize(using = EventoMotorSerializer.class)
public class EventoMotor {
    private TipoEvento tipoEvento;

    private Object[] params;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime timestamp;

    public EventoMotor(DateTime timestamp, TipoEvento tipoEvento, Object... params) {
        this.tipoEvento = tipoEvento;
        this.params = params;
        this.timestamp = timestamp;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventoMotor that = (EventoMotor) o;

        if (tipoEvento != that.tipoEvento) return false;

        if (!Arrays.equals(params, that.params)) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = tipoEvento != null ? tipoEvento.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(params);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    public String[] getStringParams() {
        String[] paramsStr = new String[params.length];
        if (getTipoEvento().getParamsDescriptor() != null) {
            switch (getTipoEvento().getParamsDescriptor().getTipo()) {
                case DETECTOR_VEICULAR:
                case DETECTOR_PEDESTRE:
                    paramsStr[0] = params[1].toString();
                    paramsStr[1] = ((Pair<Integer, TipoDetector>) params[0]).getFirst().toString();
                    break;
                case GRUPO_SEMAFORICO:
                    paramsStr[0] = params[1].toString();
                    paramsStr[1] = params[0].toString();
                    break;
                case ANEL:
                    paramsStr[0] = params[0].toString();
                    break;
            }
        }
        return paramsStr;

    }

    public Integer getAnel() {
        String[] paramsStr = getStringParams();
        if (paramsStr.length > 0) {
            return Integer.parseInt(paramsStr[0]);
        }
        return 0;
    }
}
