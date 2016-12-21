package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import engine.TipoEvento;
import models.Detector;
import models.EstagioPlano;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;

import java.util.List;

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

        plano.getEstagiosPlanos()
            .stream()
            .filter(EstagioPlano::isDispensavel)
            .filter(estagioPlano1 -> estagioPlano1.getEstagio().equals(detector.getEstagio()))
            .forEach(estagioPlano -> {
                if (estagioPlano != null) {
                    if (listaEstagioPlanos.stream().anyMatch(ep -> ep.getEstagio().isDemandaPrioritaria())) {
                        gerenciadorDeEstagios.getEstagiosProximoCiclo().add(estagioPlano);
                    }

                    int compare = estagioPlano.getPosicao().compareTo(estagioPlanoAtual.getPosicao());
                    if (compare < 0) {
                        if (!estagiosProximoCiclo.contains(estagioPlano)) {
                            gerenciadorDeEstagios.getEstagiosProximoCiclo().add(estagioPlano);
                        }
                    } else if (compare > 0) {
                        if (!listaEstagioPlanos.contains(estagioPlano)) {
                            gerenciadorDeEstagios.atualizaEstagiosCicloAtual(estagioPlano);
                        }
                    }
                }
            });
    }
}
