package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import models.Anel;
import models.EstagioPlano;

/**
 * Created by leonardo on 11/7/16.
 */
public class TrocaEstagioManualHandle extends GerenciadorDeEventos{
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Anel anel = gerenciadorDeEstagios.getPlano().getAnel();
        if (anel.isAceitaModoManual() && !estagioPlanoAtual.getEstagio().isDemandaPrioritaria()) {
            reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);
        }
    }

    public TrocaEstagioManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }
}
