package engine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.AgendamentoTrocaPlanoDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.AgendamentoTrocaPlanoSerializer;
import json.serializers.EventoMotorSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Evento;
import models.Plano;
import org.joda.time.DateTime;

@JsonSerialize(using = AgendamentoTrocaPlanoSerializer.class)
@JsonDeserialize(using = AgendamentoTrocaPlanoDeserializer.class)
public class AgendamentoTrocaPlano {
    private Evento evento;

    private Plano plano;

    private Integer anel;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime momentoDaTroca;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime momentoOriginal;

    private long momentoPedidoTroca = 0L;

    private boolean impostoPorFalha = false;

    private boolean saidaDoModoManual = false;

    private boolean tempoDeEntradaCalculado = false;

    public AgendamentoTrocaPlano() {
        super();
    }

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

    public boolean isImpostoPorFalha() {
        return impostoPorFalha;
    }

    public void setImpostoPorFalha(boolean impostoPorFalha) {
        this.impostoPorFalha = impostoPorFalha;
    }

    public boolean isSaidaDoModoManual() {
        return this.saidaDoModoManual;
    }

    public void setSaidaDoModoManual(boolean saidaDoModoManual) {
        this.saidaDoModoManual = saidaDoModoManual;
    }

    public boolean isTempoDeEntradaCalculado() {
        return tempoDeEntradaCalculado;
    }

    public void setTempoDeEntradaCalculado(boolean tempoDeEntradaCalculado) {
        this.tempoDeEntradaCalculado = tempoDeEntradaCalculado;
    }

    public boolean isPlanoCoordenado() {
        return getPlano().isTempoFixoCoordenado();
    }
}
