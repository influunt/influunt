package engine.intervalos;

import com.google.common.collect.RangeMap;
import engine.IntervaloEstagio;
import helpers.GerenciadorEstagiosHelper;
import models.*;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static helpers.GerenciadorEstagiosHelper.TEMPO_SEQUENCIA_DE_PARTIDA;
import static helpers.GerenciadorEstagiosHelper.TEMPO_VERMELHO_INTEGRAL;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class GeradorModosVerde extends GeradorDeIntervalos {

    private final boolean inicio;

    private final HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerdeComAtraso;

    private final long tempoCicloDecorrido;

    private final int contadorDeCiclo;

    private long contadorTempoEstagio;

    private Long tempoAbatimentoCoordenado = 0L;

    private Long tempoAbatidoNoCiclo;

    public GeradorModosVerde(RangeMap<Long, IntervaloEstagio> intervalos, Plano plano,
                             ModoOperacaoPlano modoAnterior, List<EstagioPlano> listaEstagioPlanos,
                             EstagioPlano estagioPlanoAtual, HashMap<Pair<Integer, Integer>, Long> tabelaDeTemposEntreVerde,
                             Long tempoAbatimentoCoordenado, boolean inicio, long contadorTempoEstagio, long tempoCicloDecorrido,
                             int contadorDeCiclo, Long tempoAbatidoNoCiclo) {
        super(intervalos, plano, modoAnterior, listaEstagioPlanos, estagioPlanoAtual, tabelaDeTemposEntreVerde);
        this.tempoAbatimentoCoordenado = tempoAbatimentoCoordenado;
        this.inicio = inicio;
        this.tabelaDeTemposEntreVerdeComAtraso = plano.tabelaEntreVerdeComAtraso();
        this.contadorTempoEstagio = contadorTempoEstagio;
        this.tempoCicloDecorrido = tempoCicloDecorrido;
        this.contadorDeCiclo = contadorDeCiclo;
        this.tempoAbatidoNoCiclo = tempoAbatidoNoCiclo;
    }

    @Override
    /**
     * Gera intervalos. Chupa codacy :-)
     */
    public Pair<Integer, RangeMap<Long, IntervaloEstagio>> gerar(int index) {
        EstagioPlano estagioPlano = listaEstagioPlanos.get(index);

        if (!estagioPlano.getPlano().equals(estagioPlanoAtual.getPlano())) {
            estagioPlano = atualizaListaEstagiosComTransicoesProibidas(estagioPlanoAtual, estagioPlano);
        }

        final Estagio estagioAtual = estagioPlano.getEstagio();
        final Estagio estagioAnterior = estagioPlanoAtual.getEstagio();

        if (!estagioAnterior.equals(estagioAtual)) {
            contadorTempoEstagio = 0L;
        }

        Long tempoEntreVerde;
        Long tempoEntreVerdeComAtraso;
        final int verde;

        if (inicio) {
            tempoEntreVerde = TEMPO_SEQUENCIA_DE_PARTIDA;
            tempoEntreVerdeComAtraso = 0L;
            verde = estagioPlano.getTempoVerdeEstagio(contadorDeCiclo);
        } else {
            tempoEntreVerde = tabelaDeTemposEntreVerde.get(
                new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));

            tempoEntreVerdeComAtraso = tabelaDeTemposEntreVerdeComAtraso.get(
                new Pair<Integer, Integer>(estagioAnterior.getPosicao(), estagioAtual.getPosicao()));

            if(desenergizacaoGrupo(estagioPlanoAtual, estagioPlano)) {
                ArrayList<Long> totalTempoEntreverdes = new ArrayList<Long>();
                ArrayList<Long> totalTempoEntreverdesComAtraso = new ArrayList<Long>();
                totalTempoEntreverdes.add(tempoEntreVerde);
                totalTempoEntreverdesComAtraso.add(tempoEntreVerdeComAtraso);

                plano.getGruposSemaforicosPlanos()
                    .stream()
                    .filter(gp -> !gp.isAtivado() &&
                        estagioAnterior.getGruposSemaforicos().contains(gp.getGrupoSemaforico()) &&
                        estagioPlanoAtual.getPlano().getGruposSemaforicosPlanos()
                            .stream()
                            .anyMatch(gp2 -> gp2.isAtivado() && gp2.getGrupoSemaforico().equals(gp.getGrupoSemaforico())))
                    .forEach(gp -> {
                        totalTempoEntreverdes
                            .add(plano.getTempoEntreVerdeGrupoSemaforico(null, estagioAnterior, gp.getGrupoSemaforico(), false) * 1000L);

                        totalTempoEntreverdesComAtraso
                            .add(plano.getTempoEntreVerdeGrupoSemaforico(null, estagioAnterior, gp.getGrupoSemaforico(), true) * 1000L);
                });
                tempoEntreVerde = Collections.max(totalTempoEntreverdes);
                tempoEntreVerdeComAtraso = Collections.max(totalTempoEntreverdesComAtraso);
            }

            //Adiciona tempo de energizacao do Grupo Semaforico no tempo de entreverde
            if (energizacaoGrupo(estagioPlanoAtual, estagioPlano) && tempoEntreVerde < TEMPO_SEQUENCIA_DE_PARTIDA) {
                tempoEntreVerde += TEMPO_SEQUENCIA_DE_PARTIDA - tempoEntreVerde;;
            }

            verde = estagioPlano.getTempoVerdeEstagioComTempoDoEstagioDispensavel(tabelaDeTemposEntreVerdeComAtraso,
                tempoCicloDecorrido + tempoAbatidoNoCiclo, listaEstagioPlanos, estagioPlanoAtual, contadorDeCiclo);
        }

        long tempoVerde = verde * 1000L;

        if (tempoAbatimentoCoordenado != null && plano.isTempoFixoCoordenado() && !estagioPlano.getEstagio().isDemandaPrioritaria()) {
            if (deveFazerAbatimento(estagioPlanoAtual, estagioPlano, tempoAbatimentoCoordenado, inicio) ||
                energizacaoGrupo(estagioPlanoAtual, estagioPlano)) {
                //Compensação de diferença entre entreverdes
                final Long tempoEntreVerdeDoPlano = this.plano.tabelaEntreVerde().get(
                    new Pair<Integer, Integer>(this.plano.getEstagioAnterior(estagioPlano).getPosicao(), estagioAtual.getPosicao()));

                tempoAbatimentoCoordenado += (tempoEntreVerde - tempoEntreVerdeDoPlano);

                final long verdeSeguranca;
                if (inicio) {
                    verdeSeguranca = estagioPlano.getTempoVerdeSeguranca() * 1000L;
                } else {
                    verdeSeguranca = estagioPlano.getTempoVerdeSegurancaFaltante(estagioPlanoAtual, contadorDeCiclo);
                }

                final long abatimento = Math.min(tempoVerde - verdeSeguranca, tempoAbatimentoCoordenado);

                tempoVerde -= abatimento;

                if (trocaDePlano(estagioPlanoAtual, estagioPlano) && estagioAnterior.equals(estagioAtual)) {
                    tempoVerde = ajustaTempoVerdeComTempoMaximoPermanencia(estagioAnterior, estagioAtual, tempoVerde);
                }

                tempoAbatidoNoCiclo += abatimento;
                tempoAbatimentoCoordenado -= abatimento;
            } else if (tempoAbatimentoCoordenado < 0) {
                tempoVerde -= tempoAbatimentoCoordenado;
                tempoAbatimentoCoordenado = 0L;

                tempoVerde = ajustaTempoVerdeComTempoMaximoPermanencia(estagioAnterior, estagioAtual, tempoVerde);
            }
        } else if ((!estagioPlano.getPlano().equals(estagioPlanoAtual.getPlano()) || inicio)) {
            if (tempoVerde < (estagioPlano.getTempoVerdeSeguranca() * 1000L)) {
                tempoVerde = estagioPlano.getTempoVerdeSeguranca() * 1000L;
            }

            if (!plano.isManual()) {
                tempoVerde = ajustaTempoVerdeComTempoMaximoPermanencia(estagioAnterior, estagioAtual, tempoVerde, false);
            }
        }

        final long diffEntreVerdes;
        if (tempoEntreVerdeComAtraso > tempoEntreVerde) {
            diffEntreVerdes = tempoEntreVerdeComAtraso - tempoEntreVerde;
            tempoEntreVerde += diffEntreVerdes;
            tempoVerde -= diffEntreVerdes;
        } else {
            diffEntreVerdes = 0L;
        }

        if (estagioPlano.getPlano().isManual()) {
            estagioPlano.setTempoVerde(verde);
        }

        //Adiciona tempo de desenergizacao do Grupo Semaforico no tempo de verde
        if (desenergizacaoGrupo(estagioPlanoAtual, estagioPlano) && tempoVerde < TEMPO_VERMELHO_INTEGRAL) {
            if (plano.isTempoFixoCoordenado()) {
                tempoAbatimentoCoordenado += (tempoVerde - TEMPO_VERMELHO_INTEGRAL);
            }

            tempoVerde = TEMPO_VERMELHO_INTEGRAL;
        }

        geraIntervaloEstagio(estagioPlano, tempoEntreVerde, tempoVerde, diffEntreVerdes, inicio);

        return new Pair<Integer, RangeMap<Long, IntervaloEstagio>>(listaEstagioPlanos.indexOf(estagioPlano) - index, this.intervalos);
    }

    private boolean energizacaoGrupo(EstagioPlano origem, EstagioPlano destino) {
        if (origem.getPlano().equals(destino.getPlano())) {
            return false;
        }

        return origem.getPlano()
            .getGruposSemaforicosPlanos()
            .stream()
            .anyMatch(gp -> !gp.isAtivado() && destino.getPlano()
                .getGruposSemaforicosPlanos()
                .stream()
                .anyMatch(gp2 -> gp2.isAtivado() && gp2.getGrupoSemaforico().equals(gp.getGrupoSemaforico())));
    }

    private boolean desenergizacaoGrupo(EstagioPlano origem, EstagioPlano destino) {
        if (origem.getPlano().equals(destino.getPlano())) {
            return false;
        }

        return origem.getPlano()
            .getGruposSemaforicosPlanos()
            .stream()
            .anyMatch(gp -> gp.isAtivado() && destino.getPlano()
                .getGruposSemaforicosPlanos()
                .stream()
                .anyMatch(gp2 -> !gp2.isAtivado() && gp2.getGrupoSemaforico().equals(gp.getGrupoSemaforico())));
    }

    private long ajustaTempoVerdeComTempoMaximoPermanencia(Estagio estagioAnterior, Estagio estagioAtual, long tempoVerde) {
        return ajustaTempoVerdeComTempoMaximoPermanencia(estagioAnterior, estagioAtual, tempoVerde, true);
    }

    private long ajustaTempoVerdeComTempoMaximoPermanencia(Estagio estagioAnterior, Estagio estagioAtual,
                                                           long tempoVerde, boolean comAbatimento) {
        final long tempoDecorridoNoEstagio = contadorTempoEstagio + tempoVerde;
        if (estagioAtual.isTempoMaximoPermanenciaAtivado() &&
            tempoDecorridoNoEstagio > (estagioAtual.getTempoMaximoPermanencia() * 1000L)) {
            final long novoTempoVerde = Math.max(0, (estagioAtual.getTempoMaximoPermanencia() * 1000L) - contadorTempoEstagio);
            if (comAbatimento) {
                tempoAbatimentoCoordenado -= (tempoVerde - novoTempoVerde);
            }
            tempoVerde = novoTempoVerde;
        }
        return tempoVerde;
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

    @Override
    public Long getTempoAbatidoNoCiclo() {
        return tempoAbatidoNoCiclo;
    }
}
