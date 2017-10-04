package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.*;
import engine.services.PlanoService;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class ImporModoHandle extends GerenciadorDeEventos {
    private final EstagioPlano estagioPlanoAnterior;

    private final RangeMap<Long, IntervaloEstagio> intervalos;

    private final long contadorIntervalo;

    public ImporModoHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.contadorIntervalo = gerenciadorDeEstagios.getContadorIntervalo();
        this.estagioPlanoAnterior = gerenciadorDeEstagios.getEstagioPlanoAnterior();
        this.intervalos = gerenciadorDeEstagios.getIntervalos();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        String key = (String) eventoMotor.getParams()[0];

        Plano plano;
        switch (ModoOperacaoPlano.valueOf(key)) {
            case INTERMITENTE:
                plano = PlanoService.gerarPlanoIntermitente(gerenciadorDeEstagios.getPlano());
                break;
            case APAGADO:
                plano = PlanoService.gerarPlanoApagado(gerenciadorDeEstagios.getPlano());
                break;
            default:
                plano = null;
        }

        if (plano != null) {
            reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo, contadorDeCiclos);

            Long horarioEntrada = (Long) eventoMotor.getParams()[3];
            Integer duracao = (Integer) eventoMotor.getParams()[2];
            Integer anel = (Integer) eventoMotor.getParams()[1];

            AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
            agendamentoTrocaPlano.setImposicaoPlano(true);
            agendamentoTrocaPlano.setDataSaidaImposicao(new DateTime(horarioEntrada).plusMinutes(duracao));
            gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);

            EventoMotor liberacao = new EventoMotor(new DateTime(horarioEntrada).plusMinutes(duracao),
                TipoEvento.LIBERAR_IMPOSICAO,
                anel);
            gerenciadorDeEstagios.agendarEvento(liberacao.getTimestamp(), liberacao);
        }
    }
}
