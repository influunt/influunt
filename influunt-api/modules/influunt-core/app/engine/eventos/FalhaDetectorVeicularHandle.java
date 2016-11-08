package engine.eventos;

import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import models.Detector;
import models.Plano;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class FalhaDetectorVeicularHandle extends GerenciadorDeEventos{
    @Override
    protected void processar(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        detector.setComFalha(true);
    }

    public FalhaDetectorVeicularHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }
}
