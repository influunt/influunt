package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import models.Anel;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by leonardo on 11/7/16.
 */
public class TrocaEstagioManualHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public TrocaEstagioManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        final Plano plano = gerenciadorDeEstagios.getPlano();
        final Anel anel = gerenciadorDeEstagios.getPlano().getAnel();

        if (plano.isManual() && gerenciadorDeEstagios.getMotor().isEmModoManual() &&
            anel.isAceitaModoManual() && !estagioPlanoAtual.getEstagio().isDemandaPrioritaria()) {
            reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);
        }
    }
}
