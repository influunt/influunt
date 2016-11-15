package engine;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import models.EstagioPlano;

import java.util.Map;

/**
 * Created by rodrigosol on 11/14/16.
 */
public class GerenciadorDeEstagiosHelper {
    public static long reduzirTempoEstagio(EstagioPlano estagioPlanoAnterior,
                                           RangeMap<Long, IntervaloEstagio> intervalos,
                                           long contadorIntervalo,
                                           EstagioPlano estagioPlanoAtual) {
        long upperEndpoint = 0L;
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
                intervalos.remove(rangeVerde.getKey());
            }

            final long duracao = contadorIntervalo - range.getKey().lowerEndpoint();
            intervalo.setDuracao(duracao);
            intervalos.remove(range.getKey());
            final Range<Long> novoRange = Range.closedOpen(range.getKey().lowerEndpoint(), range.getKey().lowerEndpoint() + duracao);
            intervalos.put(novoRange, intervalo);
        }
    }
}
