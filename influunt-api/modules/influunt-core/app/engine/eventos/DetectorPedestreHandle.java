package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.TipoEvento;
import models.Detector;
import models.EstagioPlano;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 10/24/16.
 */
public class DetectorPedestreHandle extends GerenciadorDeEventos {
    private final List<EstagioPlano> estagiosProximoCiclo;

    public DetectorPedestreHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
        this.estagiosProximoCiclo = gerenciadorDeEstagios.getEstagiosProximoCiclo();
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Pair<Integer, TipoDetector> key = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];

        Detector detector = gerenciadorDeEstagios.getDetector(key.getFirst(), key.getSecond());

        if (detector.isComFalha()) {
            gerenciadorDeEstagios.onEvento(new EventoMotor(gerenciadorDeEstagios.getTimestamp(),
                TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE,
                key,
                detector.getAnel().getPosicao()));
        }

        final boolean[] adicionarNoCicloAtual = {false};
        final boolean[] adicionarNoProximoCiclo = {false};
        final EstagioPlano[] estagioPlanoASerAdicionado = new EstagioPlano[1];

        final List<EstagioPlano> listaEstagios = plano.getEstagiosOrdenados()
            .stream()
            .filter(EstagioPlano::isDispensavel)
            .filter(estagioPlano -> estagioPlano.getEstagio().equals(detector.getEstagio()))
            .collect(Collectors.toList());

        for (int i = 0; i < listaEstagios.size(); i++) {
            EstagioPlano estagioPlano = listaEstagios.get(i);
            if (estagioPlano != null) {
                if (listaEstagioPlanos.stream().anyMatch(ep -> ep.getEstagio().isDemandaPrioritaria())) {
                    adicionarNoProximoCiclo[0] = true;
                    estagioPlanoASerAdicionado[0] = estagioPlano;
                    break;
                }

                int compare = estagioPlano.getPosicao().compareTo(estagioPlanoAtual.getPosicao());
                if (compare < 0) {
                    if (!estagiosProximoCiclo.contains(estagioPlano) && !adicionarNoProximoCiclo[0]) {
                        adicionarNoProximoCiclo[0] = true;
                        estagioPlanoASerAdicionado[0] = estagioPlano;
                    }
                } else if (compare > 0) {
                    if (plano.isTempoFixoCoordenado() &&
                        estagioPlano.estagioQueRecebeEstagioDispensavelEAnterior()) {
                        compare = estagioPlano.getEstagioQueRecebeEstagioDispensavel().getPosicao().compareTo(estagioPlanoAtual.getPosicao());

                        if (compare < 0 || (compare == 0 && gerenciadorDeEstagios.isTempoDispensavelJaAdicionado())) {
                            if (!adicionarNoProximoCiclo[0]) {
                                adicionarNoProximoCiclo[0] = true;
                                estagioPlanoASerAdicionado[0] = estagioPlano;
                            }
                        } else {
                            adicionarNoCicloAtual[0] = true;
                            estagioPlanoASerAdicionado[0] = estagioPlano;
                            break;
                        }

                    } else if (!listaEstagioPlanos.contains(estagioPlano)) {
                        adicionarNoCicloAtual[0] = true;
                        estagioPlanoASerAdicionado[0] = estagioPlano;
                        break;
                    }
                } else {
                    adicionarNoProximoCiclo[0] = false;
                    break;
                }
            }
        }

        if (adicionarNoCicloAtual[0]) {
            gerenciadorDeEstagios.atualizaEstagiosCicloAtual(estagioPlanoASerAdicionado[0]);
        } else if (adicionarNoProximoCiclo[0]) {
            gerenciadorDeEstagios.getEstagiosProximoCiclo().add(estagioPlanoASerAdicionado[0]);
        }

    }
}
