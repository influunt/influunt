package engine;

import org.joda.time.DateTime;

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

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
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


}
