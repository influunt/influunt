package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class RemoverApagadoHandle extends GerenciadorDeEventos {
    private final long contadorIntervalo;

    private RangeMap<Long, IntervaloEstagio> intervalos;

    public RemoverApagadoHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Plano plano = (Plano) eventoMotor.getParams()[1];
        this.plano.setImpostoPorFalha(false);
        terminaTempoEstagio(this.intervalos, this.contadorIntervalo);
        gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp(), true));
    }
}
