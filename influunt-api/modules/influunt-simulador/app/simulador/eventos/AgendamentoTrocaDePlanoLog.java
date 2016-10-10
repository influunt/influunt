package simulador.eventos;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class AgendamentoTrocaDePlanoLog extends DefaultLog {

    private int anel;
    private int plano;
    private int planoAnterior;
    private DateTime momentoDaTroca;

    public AgendamentoTrocaDePlanoLog(DateTime timeStamp, DateTime momentoDaTroca, int anel, int plano, int planoAnterior) {
        super(timeStamp, TipoEventoLog.AGENDAMENTO_TROCA_DE_PLANO);
        this.anel = anel;
        this.plano = plano;
        this.planoAnterior = planoAnterior;
        this.momentoDaTroca = momentoDaTroca;


    }

    public int getAnel() {
        return anel;
    }

    public void setAnel(int anel) {
        this.anel = anel;
    }

    public int getPlano() {
        return plano;
    }

    public void setPlano(int plano) {
        this.plano = plano;
    }

    public DateTime getMomentoDaTroca() {
        return momentoDaTroca;
    }

    public void setMomentoDaTroca(DateTime momentoDaTroca) {
        this.momentoDaTroca = momentoDaTroca;
    }

    @Override
    public String mensagem() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.forLanguageTag("pt-BR"));
        DateTimeFormatter sdf = DateTimeFormat.forPattern("HH:mm:ss, dd/MM/YYYY");

        return formatter.format("%s O plano %d foi agendado para entrar às %s no anel %d para substituir o plano %d", prefix(), plano,sdf.print(momentoDaTroca),anel,planoAnterior).toString();

    }

    @Override
    public boolean match(Object... params) {
        DateTime momentoTroca = (DateTime) params[0];
        int anel = (int) params[1];
        int plano = (int) params[2];

        return this.momentoDaTroca.equals(momentoTroca) &&
               this.anel == anel &&
               this.plano == plano;
    }

    @Override
    public String toJson(){

        StringBuffer sb =  new StringBuffer("{\"timestamp\":");
        sb.append(timeStamp.getMillis() / 1000);
        sb.append(",\"tipo\":\"");
        sb.append(this.tipoEventoLog);
        sb.append("\",\"anel\":");
        sb.append(this.anel);
        sb.append(",\"momentoTroca\":");
        sb.append(this.momentoDaTroca.getMillis() / 1000);
        sb.append(",\"msg\":\"");
        sb.append(this.mensagem());
        sb.append("\"}");

        return sb.toString();
    }

}
