package engine.eventos;

import com.google.common.collect.RangeMap;
import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.IntervaloEstagio;
import engine.services.PlanoService;
import models.Detector;
import models.EstagioPlano;
import models.Plano;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;

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

        reduzirTempoEstagio(estagioPlanoAnterior, this.intervalos, contadorIntervalo);
        AgendamentoTrocaPlano agendamentoTrocaPlano = new AgendamentoTrocaPlano(null, plano, eventoMotor.getTimestamp());
        agendamentoTrocaPlano.setImposicaoPlano(true);
        gerenciadorDeEstagios.trocarPlano(agendamentoTrocaPlano);
    }
}
