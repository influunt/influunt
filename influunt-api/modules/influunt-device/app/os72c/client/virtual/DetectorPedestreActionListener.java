package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import os72c.client.device.DeviceBridgeCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class DetectorPedestreActionListener extends GenericPanelActionListener {

    public DetectorPedestreActionListener(ControladorForm callback, TipoEvento tipoFalha, TipoEvento tipoRemocaoFalha) {
        super(callback, tipoFalha, tipoRemocaoFalha);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final DetectorPedestreActionListener actionListener = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneDetectorPedestre().displayGUI(actionListener);

            }
        });

    }

    @Override
    public void onFalha(String param) {
        controladorForm.getCallback().onEvento(new EventoMotor(DateTime.now(),tipoFalha, new Pair<Integer, TipoDetector>(Integer.valueOf(param),TipoDetector.PEDESTRE)));
    }
}
