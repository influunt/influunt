package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.*;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class EntrarEmModoManualHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public EntrarEmModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);
    }
}
