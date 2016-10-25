package engine;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by rodrigosol on 9/28/16.
 */
public class EventoMotor {
    private TipoEvento tipoEvento;
    private Object[] params;
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
}
