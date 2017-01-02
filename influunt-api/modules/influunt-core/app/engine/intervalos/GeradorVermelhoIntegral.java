package engine.intervalos;

import com.google.common.collect.RangeMap;
import engine.IntervaloEstagio;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class GeradorVermelhoIntegral extends GeradorDeIntervalos {
    public GeradorVermelhoIntegral(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                                   ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                                   EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
    }

    @Override
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);
        geraIntervaloEstagio(estagioPlano, 3000L, estagioPlano.getTempoVerdeEstagio() * 1000L);
        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(0, this.intervalos);
    }

    @Override
    public Long getTempoAbatimentoCoordenado() {
        return null;
    }

    @Override
    public Long getTempoAbatidoNoCiclo() {
        return 0L;
    }
}
