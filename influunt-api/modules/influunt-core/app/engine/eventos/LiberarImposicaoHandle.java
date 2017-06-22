package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.*;
import models.EstagioPlano;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class LiberarImposicaoHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public LiberarImposicaoHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        if (this.plano.isImposto()) {
            Plano plano = (Plano) eventoMotor.getParams()[1];
            reduzirTempoEstagio(estagioPlanoAnterior, intervalos, contadorIntervalo, contadorDeCiclos);
            this.plano.setImposto(false);
            AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
            agendamentoTrocaPlano.setSaidaImposicao(true);
            gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);

            //Verifica se tem agendamento
            gerenciadorDeEstagios.limparAgendamentos(TipoEvento.LIBERAR_IMPOSICAO);
        }
    }
}
