package simulador.eventos;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class DefaultLog extends EventoLog {


    public DefaultLog(DateTime timeStamp, TipoEventoLog tipo) {
        super(timeStamp, tipo);
    }

    @Override
    public String mensagem() {
        return prefix();
    }

    @Override
    public boolean match(Object... params) {
        return false;
    }

    @Override
    public String toJson() {
        return "{\"timestamp\":" + timeStamp.getMillis() / 1000 + ",\"tipo\":\"" + this.tipoEventoLog + "\",\"msg\":\"" + this.mensagem() + "\"}";
    }
}
