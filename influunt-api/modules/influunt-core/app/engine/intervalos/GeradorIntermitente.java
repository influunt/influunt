package engine.intervalos;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import engine.IntervaloEstagio;
import models.Estagio;
import models.EstagioPlano;
import models.ModoOperacaoPlano;
import models.Plano;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class GeradorIntermitente extends GeradorDeIntervalos {
    public GeradorIntermitente(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                               ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                               EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
    }

    @Override
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        Map.Entry<Range<Long>, IntervaloEstagio> entreverde = null;

        if (this.intervalos != null) {
            entreverde = this.intervalos.getEntry(0L);
        }

        this.intervalos = TreeRangeMap.create();
        if (isModoAnteriorVerde(modoAnterior) && entreverde != null && entreverde.getValue().isEntreverde()) {
            final IntervaloEstagio intervalo = entreverde.getValue();
            final Estagio estagio;
            if (intervalo.getEstagioPlanoAnterior().getEstagio().getId() != null) {
                estagio = intervalo.getEstagioPlanoAnterior().getEstagio();
            } else {
                estagio = intervalo.getEstagio();
            }

            long tempoEntreVerde = tabelaDeTemposEntreVerde.get(new Pair<Integer, Integer>(estagio.getPosicao(), null)) + 3000L;

            String idJsonNovoEstagio = UUID.randomUUID().toString();

            this.intervalos.put(Range.closedOpen(0L, tempoEntreVerde),
                    new IntervaloEstagio(tempoEntreVerde, true,
                            criaEstagioPlanoInterminteOuApagado(idJsonNovoEstagio), null));

            this.intervalos.put(Range.closedOpen(tempoEntreVerde, 255000L),
                    new IntervaloEstagio(255000L - tempoEntreVerde, false,
                            criaEstagioPlanoInterminteOuApagado(idJsonNovoEstagio), this.estagioPlanoAtual));
        } else {
            this.intervalos.put(Range.closedOpen(0L, 255000L),
                    new IntervaloEstagio(255000L, false, criaEstagioPlanoInterminteOuApagado(UUID.randomUUID().toString()), null));
        }

        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(0, this.intervalos);
    }

    private EstagioPlano criaEstagioPlanoInterminteOuApagado(String idJsonNovoEstagio) {
        Estagio estagio = new Estagio();
        estagio.setTempoMaximoPermanenciaAtivado(false);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setIdJson(idJsonNovoEstagio);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(estagio);
        listaEstagioPlanos.add(estagioPlano);
        return estagioPlano;
    }
}
