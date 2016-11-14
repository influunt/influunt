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

/**
 * Created by rodrigosol on 10/24/16.
 */
public class GeradorModosVerde extends GeradorDeIntervalos {

    private Long tempoAbatimentoCoordenado = 0L;
    public GeradorModosVerde(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                             ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                             EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde,
                             Long tempoAbatimentoCoordenado) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
        this.tempoAbatimentoCoordenado = tempoAbatimentoCoordenado;
    }

    @Override
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);

        if (!estagioPlano.getPlano().equals(estagioPlanoAtual.getPlano())) {
            estagioPlano = atualizaListaEstagiosComTransicoesProibidas(estagioPlanoAtual, estagioPlano);
        }

        final Estagio estagioAtual = estagioPlano.getEstagio();
        final Estagio estagioAnterior = estagioPlanoAtual.getEstagio();

        final long tempoEntreVerde = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));
        final int verde = estagioPlano.getTempoVerdeEstagioComTempoDoEstagioDispensavel(tabelaDeTemposEntreVerde, listaEstagioPlanos);
        long tempoVerde = verde * 1000L;

        if (tempoAbatimentoCoordenado != null && tempoAbatimentoCoordenado > 0L) {
            final long verdeSeguranca = estagioPlano.getTempoVerdeSegurancaFaltante(estagioPlanoAtual);
            final long abatimento = Math.min(tempoVerde - verdeSeguranca, tempoAbatimentoCoordenado);
            tempoVerde -= abatimento;
            tempoAbatimentoCoordenado -= abatimento;
        }

        if (estagioPlano.getPlano().isManual()) {
            estagioPlano.setTempoVerde(verde);
        }

        geraIntervaloEstagio(estagioPlano, tempoEntreVerde, tempoVerde);

        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(listaEstagioPlanos.indexOf(estagioPlano) - index, this.intervalos);
    }

    private EstagioPlano atualizaListaEstagiosComTransicoesProibidas(EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlano) {
        final Estagio estagioAnterior = estagioPlanoAnterior.getEstagio();
        if (estagioAnterior.temTransicaoProibidaParaEstagio(estagioPlano.getEstagio())) {
            final Estagio estagioAtual = estagioAnterior.getTransicaoProibidaPara(estagioPlano.getEstagio()).getAlternativo();
            estagioPlano = listaEstagioPlanos.stream().filter(ep -> ep.getEstagio().equals(estagioAtual)).findFirst().orElse(null);
        }
        return estagioPlano;
    }

    @Override
    public Long getTempoAbatimentoCoordenado() {
        return tempoAbatimentoCoordenado;
    }
}
