package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import models.EstagioPlano;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigosol on 11/14/16.
 */
public class GerenciadorDeEstagiosHelper {
    public static long reduzirTempoEstagio(EstagioPlano estagioPlanoAnterior,
                                           RangeMap<Long, IntervaloEstagio> intervalos,
                                           long contadorIntervalo,
                                           EstagioPlano estagioPlanoAtual) {
        long upperEndpoint = contadorIntervalo;
        if (intervalos.get(contadorIntervalo) != null) {
            final long contador;
            Map.Entry<Range<Long>, IntervaloEstagio> range = intervalos.getEntry(contadorIntervalo);
            IntervaloEstagio intervalo = range.getValue();
            if (intervalo.isEntreverde()) {
                range = intervalos.getEntry(range.getKey().upperEndpoint() + 1);
                intervalo = range.getValue();
                contador = 0L;
            } else {
                contador = contadorIntervalo - range.getKey().lowerEndpoint();
            }
            long duracao = Math.max(estagioPlanoAtual.getTempoVerdeSegurancaFaltante(estagioPlanoAnterior), contador);
            intervalo.setDuracao(duracao);
            intervalos.remove(range.getKey());
            upperEndpoint = range.getKey().lowerEndpoint() + duracao;
            final Range<Long> novoRange = Range.closedOpen(range.getKey().lowerEndpoint(), upperEndpoint);
            intervalos.put(novoRange, intervalo);
        }
        return upperEndpoint - contadorIntervalo;
    }

    public static void terminaTempoEstagio(RangeMap<Long, IntervaloEstagio> intervalos,
                                           long contadorIntervalo) {
        if (intervalos.get(contadorIntervalo) != null) {
            Map.Entry<Range<Long>, IntervaloEstagio> range = intervalos.getEntry(contadorIntervalo);
            IntervaloEstagio intervalo = range.getValue();

            if (intervalo.isEntreverde()) {
                final Map.Entry<Range<Long>, IntervaloEstagio> rangeVerde = intervalos.getEntry(range.getKey().upperEndpoint() + 1);
                if (rangeVerde != null) {
                    intervalos.remove(rangeVerde.getKey());
                }
            }

            final long duracao = contadorIntervalo - range.getKey().lowerEndpoint();
            intervalo.setDuracao(duracao);
            intervalos.remove(range.getKey());
            final Range<Long> novoRange = Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + duracao);
            intervalos.put(novoRange, intervalo);
        }
    }

    public static void aumentarTempoEstagio(RangeMap<Long, IntervaloEstagio> intervalos,
                                            long contadorIntervalo, long tempo) {
        Map.Entry<Range<Long>, IntervaloEstagio> range = intervalos.getEntry(contadorIntervalo - 100L);
        final IntervaloEstagio intervalo = range.getValue();

        if (intervalo.getDuracao() < tempo) {
            long novaDuracao = tempo;

            intervalos.remove(range.getKey());
            intervalo.setDuracao(novaDuracao);
            intervalos.put(Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + novaDuracao),
                intervalo);
        }
    }

    public static boolean isCumpreTempoVerdeSeguranca(List<EstagioPlano> lista) {
        final EstagioPlano atual = lista.get(1);
        final long tempoFaltante;
        if (lista.size() > 2) {
            tempoFaltante = atual.getTempoVerdeSegurancaFaltante(lista.get(0), lista.get(2));
        } else {
            tempoFaltante = atual.getTempoVerdeSegurancaFaltante(lista.get(0));
        }
        return tempoFaltante == 0;
    }
}
