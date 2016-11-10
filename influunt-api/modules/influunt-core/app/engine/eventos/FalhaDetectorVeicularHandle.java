package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import models.Detector;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class FalhaDetectorVeicularHandle extends GerenciadorDeEventos {
    public FalhaDetectorVeicularHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        detector.setComFalha(true);
    }
}
