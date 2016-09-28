package simulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
