package engine.intervalos;

import com.google.common.collect.RangeMap;
import engine.IntervaloEstagio;
import models.Estagio;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.List;

import static helpers.GerenciadorEstagiosHelper.TEMPO_VERMELHO_INTEGRAL;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class GeradorVermelhoIntegral extends GeradorDeIntervalos {
    private Long tempoAbatidoNoCiclo = 0L;
    private Long tempoAbatimentoCoordenado = 0L;

    public GeradorVermelhoIntegral(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                                   ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                                   EstagioPlano estagioPlanoAtual,
                                   HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde,
                                   Long tempoAbatimentoCoordenado, Long tempoAbatidoNoCiclo) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
        this.tempoAbatimentoCoordenado = tempoAbatimentoCoordenado;
        this.tempoAbatidoNoCiclo = tempoAbatidoNoCiclo;
    }

    @Override
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);
        Long tempoVerde = estagioPlano.getTempoVerdeEstagio() * 1000L;
        if (tempoAbatimentoCoordenado != null && plano.isTempoFixoCoordenado()) {
            if (deveFazerAbatimento(estagioPlanoAtual, estagioPlano, tempoAbatimentoCoordenado, false)) {
                //Compensação de diferença entre entreverdes
                final Estagio estagioAtual = estagioPlano.getEstagio();

                final Long tempoEntreVerdeDoPlano = tabelaDeTemposEntreVerde.get(
                    new Pair<Integer, Integer>(this.plano.getEstagioAnterior(estagioPlano).getPosicao(),
                        estagioAtual.getPosicao()));

                tempoAbatimentoCoordenado += (TEMPO_VERMELHO_INTEGRAL - tempoEntreVerdeDoPlano);

                Long verdeSeguranca = estagioPlano.getTempoVerdeSeguranca() * 1000L;
                final long abatimento = Math.min(tempoVerde - verdeSeguranca, tempoAbatimentoCoordenado);

                tempoVerde -= abatimento;

                tempoAbatidoNoCiclo += abatimento;
                tempoAbatimentoCoordenado -= abatimento;
            }
        }
        geraIntervaloEstagio(estagioPlano, TEMPO_VERMELHO_INTEGRAL, tempoVerde);
        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(0, this.intervalos);
    }

    @Override
    public Long getTempoAbatimentoCoordenado() {
        return tempoAbatimentoCoordenado;
    }

    @Override
    public Long getTempoAbatidoNoCiclo() {
        return tempoAbatidoNoCiclo;
    }
}
