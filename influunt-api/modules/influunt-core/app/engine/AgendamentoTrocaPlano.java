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


    public AgendamentoTrocaPlano(Evento evento, Plano plano, DateTime momentoOriginal) {
        this.evento = evento;
        this.plano = plano;
        this.momentoOriginal = momentoOriginal;
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
}