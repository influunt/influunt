package simulador;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class DefaultLog extends EventoLog {


    public DefaultLog(DateTime timeStamp, TipoEventoLog tipo) {
        super(timeStamp, tipo);
    }

    @Override
    public String mensagem(int evento) {
        return prefix(evento);
    }

    @Override
    public boolean match(Object... params) {
        return false;
    }
}
