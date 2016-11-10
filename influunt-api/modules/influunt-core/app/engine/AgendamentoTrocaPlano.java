package engine;

import models.Evento;
import models.Plano;
import org.joda.time.DateTime;

public class AgendamentoTrocaPlano {
    private Evento evento;

    private Plano plano;

    private Integer anel;

    private DateTime momentoDaTroca;

    private DateTime momentoOriginal;

    private long momentoPedidoTroca = 0L;

    private boolean impostoPorFalha = false;

    private boolean saidaDoModoManual = false;

    public AgendamentoTrocaPlano(Evento evento, Plano plano, DateTime momentoOriginal) {
        this.evento = evento;
        this.plano = plano;
        this.momentoOriginal = momentoOriginal;
    }

    public AgendamentoTrocaPlano(Evento evento, Plano plano, DateTime momentoOriginal, boolean impostoPorFalha) {
        this(evento, plano, momentoOriginal);
        this.impostoPorFalha = impostoPorFalha;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public DateTime getMomentoDaTroca() {
        return momentoDaTroca;
    }

    public void setMomentoDaTroca(long tempoDecorrido) {
        this.momentoDaTroca = this.momentoOriginal.plus(tempoDecorrido - momentoPedidoTroca);
    }

    public DateTime getMomentoOriginal() {
        return momentoOriginal;
    }

    public void setMomentoOriginal(DateTime momentoOriginal) {
        this.momentoOriginal = momentoOriginal;
    }

    public long getMomentoPedidoTroca() {
        return momentoPedidoTroca;
    }

    public void setMomentoPedidoTroca(long momentoPedidoTroca) {
        this.momentoPedidoTroca = momentoPedidoTroca;
    }

    public Integer getAnel() {
        return anel;
    }

    public void setAnel(Integer anel) {
        this.anel = anel;
    }

    public void setImpostoPorFalha(boolean impostoPorFalha) {
        this.impostoPorFalha = impostoPorFalha;
    }

    public boolean isImpostoPorFalha() {
        return impostoPorFalha;
    }

    public void setSaidaDoModoManual(boolean saidaDoModoManual) {
        this.saidaDoModoManual = saidaDoModoManual;
    }

    public boolean isSaidaDoModoManual() {
        return this.saidaDoModoManual;
    }
}
