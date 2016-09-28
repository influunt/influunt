package simulacao;

import models.Evento;
import org.joda.time.DateTime;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by rodrigosol on 9/26/16.
 */
public class AlteracaoEventoLog extends EventoLog {

    private Evento anterior;
    private Evento atual;
    private int anel;

    public AlteracaoEventoLog(DateTime timeStamp, Evento anterior, Evento atual, int anel) {
        super(timeStamp, TipoEventoLog.ALTERACAO_EVENTO);
        this.anterior = anterior;
        this.atual = atual;
        this.anel = anel;
    }

    public Evento getAnterior() {
        return anterior;
    }

    public void setAnterior(Evento anterior) {
        this.anterior = anterior;
    }

    public Evento getAtual() {
        return atual;
    }

    public void setAtual(Evento atual) {
        this.atual = atual;
    }

    public int getAnel() {
        return anel;
    }

    public void setAnel(int anel) {
        this.anel = anel;
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

    @Override
    public boolean match(Object... params) {
        int posicaoEventoAnterior = (int) params[0];
        int posicaoEventoAtual = (int) params[1];
        int anel = (int) params[2];
        return this.anterior.getPosicaoPlano().equals(posicaoEventoAnterior) &&
               this.atual.getPosicaoPlano().equals(posicaoEventoAtual) &&
               this.anel == anel;
    }
}
