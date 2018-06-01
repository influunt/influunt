package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.services.PlanoService;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class ImporAmareloIntermitentePorFalhaHandle extends GerenciadorDeEventos {
    private final long contadorIntervalo;

    private RangeMap<Long, IntervaloEstagio> intervalos;

    public ImporAmareloIntermitentePorFalhaHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Plano plano = PlanoService.gerarPlanoIntermitentePorFalha(gerenciadorDeEstagios.getPlano());
        terminaTempoEstagio(this.intervalos, contadorIntervalo);
        gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp(), true, false));
    }
}
