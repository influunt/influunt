package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import models.Detector;
import models.EstagioPlano;

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
        Detector detector = (Detector) eventoMotor.getParams()[0];
        EstagioPlano estagioPlano = plano.getEstagiosPlanos()
            .stream()
            .filter(EstagioPlano::isDispensavel)
            .filter(estagioPlano1 -> estagioPlano1.getEstagio().equals(detector.getEstagio()))
            .findFirst()
            .orElse(null);
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
}
