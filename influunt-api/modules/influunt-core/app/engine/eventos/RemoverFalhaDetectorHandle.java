package engine.eventos;

import engine.EventoMotor;
import engine.GerenciadorDeEstagios;
import models.Detector;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;

/**
 * Created by rodrigosol on 11/8/16.
 */
public class RemoverFalhaDetectorHandle extends GerenciadorDeEventos {
    public RemoverFalhaDetectorHandle(GerenciadorDeEstagios gerenciadorDeEstagios) {
        super(gerenciadorDeEstagios);
    }

    @Override
    protected void processar(EventoMotor eventoMotor) {
        Pair<Integer, TipoDetector> key = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];

        Detector detector = gerenciadorDeEstagios.getDetector(key.getFirst(), key.getSecond());
        detector.setComFalha(false);
    }
}
