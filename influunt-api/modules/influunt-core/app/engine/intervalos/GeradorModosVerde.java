package engine.intervalos;

import com.google.common.collect.RangeMap;
import engine.IntervaloEstagio;
import helpers.GerenciadorEstagiosHelper;
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

    private final boolean inicio;
    private final HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerdeComAtraso;
    private Long tempoAbatimentoCoordenado = 0L;

    public GeradorModosVerde(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                             ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                             EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde,
                             Long tempoAbatimentoCoordenado, boolean inicio) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
        this.tempoAbatimentoCoordenado = tempoAbatimentoCoordenado;
        this.inicio = inicio;
        this.tabelaDeTemposEntreVerdeComAtraso = plano.tabelaEntreVerdeComAtraso();
    }

    @Override
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);

        if (!estagioPlano.getPlano().equals(estagioPlanoAtual.getPlano())) {
            estagioPlano = atualizaListaEstagiosComTransicoesProibidas(estagioPlanoAtual, estagioPlano);
        }

        final Estagio estagioAtual = estagioPlano.getEstagio();
        final Estagio estagioAnterior = estagioPlanoAtual.getEstagio();

        Long tempoEntreVerde;
        final Long tempoEntreVerdeComAtraso;

        if (inicio) {
            tempoEntreVerde = GerenciadorEstagiosHelper.TEMPO_SEQUENCIA_DE_PARTIDA;
            tempoEntreVerdeComAtraso = 0L;
        } else {
            tempoEntreVerde = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));
            tempoEntreVerdeComAtraso = tabelaDeTemposEntreVerdeComAtraso.get(new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));
        }

        final int verde = estagioPlano.getTempoVerdeEstagioComTempoDoEstagioDispensavel(tabelaDeTemposEntreVerde, listaEstagioPlanos);
        long tempoVerde = verde * 1000L;

        final long diffEntreVerdes;
        if (tempoEntreVerdeComAtraso > tempoEntreVerde) {
            diffEntreVerdes = tempoEntreVerdeComAtraso - tempoEntreVerde;
            tempoEntreVerde += diffEntreVerdes;
            tempoVerde -= diffEntreVerdes;
        } else {
            diffEntreVerdes = 0L;
        }

        if (tempoAbatimentoCoordenado != null && (tempoAbatimentoCoordenado > 0L || inicio)) {
            //Compensação de diferença entre entreverdes
            final Long tempoEntreVerdeDoPlano = tabelaDeTemposEntreVerde.get(
                new Pair<Integer, Integer>(this.plano.getEstagioAnterior(estagioPlano).getPosicao(), estagioAtual.getPosicao()));

            tempoAbatimentoCoordenado += (tempoEntreVerde - tempoEntreVerdeDoPlano);

            final long verdeSeguranca;
            if (inicio) {
                verdeSeguranca = estagioPlano.getTempoVerdeSeguranca() * 1000L;
            } else {
                verdeSeguranca = estagioPlano.getTempoVerdeSegurancaFaltante(estagioPlanoAtual);
            }

            final long abatimento = Math.min(tempoVerde - verdeSeguranca, tempoAbatimentoCoordenado);

            tempoVerde -= abatimento;

            tempoAbatimentoCoordenado -= abatimento;
        }

        if (estagioPlano.getPlano().isManual()) {
            estagioPlano.setTempoVerde(verde);
        }

        geraIntervaloEstagio(estagioPlano, tempoEntreVerde, tempoVerde, diffEntreVerdes, inicio);

        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(listaEstagioPlanos.indexOf(estagioPlano) - index, this.intervalos);
    }

    private EstagioPlano atualizaListaEstagiosComTransicoesProibidas(EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlano) {
        final Estagio origem = estagioPlanoAnterior.getEstagio();
        final Estagio destino = estagioPlano.getEstagio();
        if (origem.temTransicaoProibidaParaEstagio(destino)) {
            estagioPlano = GerenciadorEstagiosHelper.getEstagioPlanoAlternativoDaTransicaoProibida(origem, destino, listaEstagioPlanos);
        }
        return estagioPlano;
    }

    @Override
    public Long getTempoAbatimentoCoordenado() {
        return tempoAbatimentoCoordenado;
    }
}
