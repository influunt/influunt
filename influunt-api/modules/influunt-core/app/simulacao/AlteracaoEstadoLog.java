package simulacao;

import models.EstadoGrupoSemaforico;
import org.joda.time.DateTime;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class AlteracaoEstadoLog extends EventoLog {

    private List<EstadoGrupoSemaforico> anterior;

    private List<EstadoGrupoSemaforico> atual;

    public AlteracaoEstadoLog(DateTime timeStamp, List<EstadoGrupoSemaforico> anterior, List<EstadoGrupoSemaforico> atual) {
        super(timeStamp, TipoEventoLog.ALTERACAO_ESTADO);
        this.anterior = anterior;
        this.atual = atual;
    }

    @Override
    public String mensagem(int evento) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.forLanguageTag("pt-BR"));
        if (anterior != null) {
            return formatter.format("%s DE:%s PARA: %s ", prefix(evento), anterior.toString(), atual.toString()).toString();
        } else {
            return formatter.format("%s %s", prefix(evento), atual.toString()).toString();
        }

    }
}
