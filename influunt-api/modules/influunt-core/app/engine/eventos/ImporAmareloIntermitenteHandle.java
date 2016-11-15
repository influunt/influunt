package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.services.PlanoService;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class ImporAmareloIntermitenteHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public ImporAmareloIntermitenteHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Plano plano = PlanoService.gerarPlanoIntermitentePorFalha(gerenciadorDeEstagios.getPlano());
        reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);
        gerenciadorDeEstagios.trocarPlano(new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp(), true));
    }
}
