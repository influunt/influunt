package simulador;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class LogSimulacao {
    private List<EventoLog> logSimulacaos = new ArrayList<>();

    public void log(EventoLog logSimulacao) {
        logSimulacaos.add(logSimulacao);
    }

    public void print(TipoEventoLog... filtro) {
        int i = 0;
        for (EventoLog e : logSimulacaos) {
            i++;
            TipoEventoLog contains = Arrays.stream(filtro).filter(tipoEventoLog -> e.getTipoEventoLog().equals(tipoEventoLog)).findAny().orElse(null);
            if (contains != null) {
                System.out.println(e.mensagem(i));
            }
        }
    }

    public EventoLog filter(TipoEventoLog tipoEventoLog, DateTime timestamp,Object... params) {
        return find(tipoEventoLog,timestamp).filter(eventoLog -> eventoLog.match(params)).findFirst().orElse(null);
    }

    public Stream<EventoLog> find(TipoEventoLog tipoEventoLog, DateTime timestamp){
        return logSimulacaos.stream().filter(eventoLog -> eventoLog.getTipoEventoLog().equals(tipoEventoLog))
                .filter(eventoLog -> eventoLog.timeStamp.equals(timestamp));
    }

    public Stream<EventoLog> find(TipoEventoLog tipoEventoLog) {
        return logSimulacaos.stream().filter(eventoLog -> eventoLog.getTipoEventoLog().equals(tipoEventoLog));

    }
}
