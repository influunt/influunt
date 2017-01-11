package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import models.Anel;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by leonardo on 11/7/16.
 */
public class DesativaModoManualHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public DesativaModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Anel anel = gerenciadorDeEstagios.getPlano().getAnel();
        if (anel.isAceitaModoManual() && this.plano.isManual()) {
            reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo, contadorDeCiclos);
            Plano plano = (Plano) eventoMotor.getParams()[0];
            AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
            agendamentoTrocaPlano.setSaidaDoModoManual(true);
            gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);
        }
    }
}
