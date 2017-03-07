package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class EntrarEmModoManualHandle extends GerenciadorDeEventos {

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public EntrarEmModoManualHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        terminaTempoEstagio(this.intervalos, contadorIntervalo);
    }
}
