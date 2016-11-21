package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.services.PlanoService;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class LiberarImposicaoModoHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public LiberarImposicaoModoHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Plano plano = (Plano) eventoMotor.getParams()[1];
        reduzirTempoEstagio(estagioPlanoAnterior, intervalos, contadorIntervalo);
        this.plano.setImposto(false);
        AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
        agendamentoTrocaPlano.setSaidaImposicao(true);
        gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);
    }
}
