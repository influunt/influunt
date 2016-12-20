package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.*;
import models.EstagioPlano;
import models.Plano;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class ImporPlanoHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public ImporPlanoHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Integer key = (Integer) eventoMotor.getParams()[0];

        Plano plano = gerenciadorDeEstagios.getPlano(key);

        if (plano != null && !plano.equals(this.plano)) {
            plano.setImposto(true);

            reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);

            Long horarioEntrada = (Long) eventoMotor.getParams()[3];
            Integer duracao = (Integer) eventoMotor.getParams()[2];

            AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
            agendamentoTrocaPlano.setImposicaoPlano(true);
            agendamentoTrocaPlano.setDataSaidaImposicao(new DateTime(horarioEntrada).plusMinutes(duracao));
            gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);


            //Agendar liberação
            EventoMotor liberacao = new EventoMotor(new DateTime(horarioEntrada).plusMinutes(duracao),
                TipoEvento.LIBERAR_IMPOSICAO,
                eventoMotor.getParams()[1]);
            gerenciadorDeEstagios.agendarEvento(liberacao.getTimestamp(), liberacao);
        }
    }
}
