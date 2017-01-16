package engine.intervalos;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
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
public abstract class GeradorDeIntervalos {
    protected final HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde;

    protected final ModoOperacaoPlano modoAnterior;

    protected final Plano plano;

    protected final EstagioPlano estagioPlanoAtual;

    protected final List<EstagioPlano> listaEstagioPlanos;

    protected RangeMap<Long, IntervaloEstagio> intervalos;

    public GeradorDeIntervalos(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                               ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                               EstagioPlano estagioPlanoAtual,
                               HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde) {
        this.intervalos = intervalos;
        this.modoAnterior = modoAnterior;
        this.plano = plano;
        this.listaEstagioPlanos = listaEstagioPlanos;
        this.estagioPlanoAtual = estagioPlanoAtual;
        this.tabelaDeTemposEntreVerde = tabelaDeTemposEntreVerde;
    }

    public static GeradorDeIntervalos getInstance(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                                                  ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                                                  EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde,
                                                  int index, Long tempoAbatimentoCoordenado, boolean inicio, long contadorTempoEstagio,
                                                  long tempoCicloDecorrido, int contadorDeCiclo, Long tempoAbatidoNoCiclo,
                                                  HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerdeComAtraso) {
        if (!plano.isModoOperacaoVerde() && (index == 0 || (!listaEstagioPlanos.isEmpty() && !listaEstagioPlanos.get(index).getEstagio().isDemandaPrioritaria()))) {
            return new GeradorIntermitente(intervalos, plano, modoAnterior,
                listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
        } else if (!isModoAnteriorVerde(modoAnterior)) {
            if (modoAnterior.equals(ModoOperacaoPlano.INTERMITENTE)) {
                return new GeradorVermelhoIntegral(intervalos, plano, modoAnterior,
                    listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde,
                    tempoAbatimentoCoordenado, tempoAbatidoNoCiclo, contadorDeCiclo);
            }
            return new GeradorSequenciaPartida(intervalos, plano, modoAnterior,
                listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde,
                tempoAbatimentoCoordenado, tempoAbatidoNoCiclo, contadorDeCiclo);
        } else {
            return new GeradorModosVerde(intervalos, plano, modoAnterior,
                listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde,
                tempoAbatimentoCoordenado, inicio, contadorTempoEstagio, tempoCicloDecorrido,
                contadorDeCiclo, tempoAbatidoNoCiclo, tabelaDeTemposEntreVerdeComAtraso);
        }
    }

    protected static boolean isModoAnteriorVerde(ModoOperacaoPlano modoAnterior) {
        return !ModoOperacaoPlano.APAGADO.equals(modoAnterior) && !ModoOperacaoPlano.INTERMITENTE.equals(modoAnterior);
    }

    public abstract Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(final int index);

    public abstract Long getTempoAbatimentoCoordenado();

    public abstract Long getTempoAbatidoNoCiclo();

    protected void geraIntervaloEstagio(EstagioPlano estagioPlano, long tempoEntreVerde, long tempoVerde) {
        geraIntervaloEstagio(estagioPlano, tempoEntreVerde, tempoVerde, 0L, false);
    }

    protected void geraIntervaloEstagio(EstagioPlano estagioPlano, long tempoEntreVerde, long tempoVerde, boolean inicio) {
        geraIntervaloEstagio(estagioPlano, tempoEntreVerde, tempoVerde, 0L, inicio);
    }

    protected void geraIntervaloEstagio(EstagioPlano estagioPlano, long tempoEntreVerde, long tempoVerde, long diffEntreVerdes, boolean inicio) {
        this.intervalos = TreeRangeMap.create();
        this.intervalos.put(Range.closedOpen(0L, tempoEntreVerde),
            new IntervaloEstagio(tempoEntreVerde, true, estagioPlano, estagioPlanoAtual, diffEntreVerdes, inicio));
        this.intervalos.put(Range.closedOpen(tempoEntreVerde, tempoEntreVerde + tempoVerde),
            new IntervaloEstagio(tempoVerde, false, estagioPlano, estagioPlanoAtual, diffEntreVerdes, inicio));
    }

    protected boolean deveFazerAbatimento(EstagioPlano origem, EstagioPlano destino, Long tempoAbatimentoCoordenado, boolean inicio) {
        return tempoAbatimentoCoordenado > 0L || inicio ||
            (trocaDePlano(origem, destino) && !origem.getEstagio().equals(this.plano.getEstagioAnterior(destino)));
    }

    protected boolean trocaDePlano(EstagioPlano origem, EstagioPlano destino) {
        return !origem.getPlano().equals(destino.getPlano());
    }
}
