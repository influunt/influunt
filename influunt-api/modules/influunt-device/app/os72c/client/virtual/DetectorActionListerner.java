package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import os72c.client.device.DeviceBridgeCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class DetectorActionListerner implements ActionListener {
    private final DeviceBridgeCallback sender;

    public DetectorActionListerner(DeviceBridgeCallback sender) {
        this.sender = sender;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String name = jButton.getText();
        char tipo = name.charAt(1);
        int numero = Character.getNumericValue(name.charAt(3));

        if (tipo == 'V') {
            sender.onEvento(new EventoMotor(DateTime.now(), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, new Pair<Integer, TipoDetector>(numero, TipoDetector.VEICULAR)));
        } else {
            sender.onEvento(new EventoMotor(DateTime.now(), TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE, new Pair<Integer, TipoDetector>(numero, TipoDetector.PEDESTRE)));
        }

    }
}
