package simulacao;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by rodrigosol on 9/26/16.
 */
public abstract class EventoLog {
    protected DateTime timeStamp;

    protected TipoEventoLog tipoEventoLog;

    public EventoLog(DateTime timeStamp, TipoEventoLog tipoEventoLog) {
        this.timeStamp = timeStamp;
        this.tipoEventoLog = tipoEventoLog;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public TipoEventoLog getTipoEventoLog() {
        return tipoEventoLog;
    }

    public void setTipoEventoLog(TipoEventoLog tipoEventoLog) {
        this.tipoEventoLog = tipoEventoLog;
    }

    public String prefix(int evento) {

        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(sb, Locale.forLanguageTag("pt-BR"));

        // Explicit argument indices may be used to re-order output.

        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss");
        String formatedDate = sdf.print(this.getTimeStamp());

        return formatter.format("[%5d] %-20s %s", evento, formatedDate, tipoEventoLog).toString();
    }

    public abstract String mensagem(int evento);
}
