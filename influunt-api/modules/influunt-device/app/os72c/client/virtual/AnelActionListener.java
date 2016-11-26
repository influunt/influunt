package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import models.Controlador;
import org.joda.time.DateTime;
import os72c.client.device.DeviceBridgeCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static engine.TipoEventoParamsTipoDeDado.tipo;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class AnelActionListener extends GenericPanelActionListener {

    public AnelActionListener(ControladorForm callback, TipoEvento tipoFalha, TipoEvento tipoRemocaoFalha) {
        super(callback, tipoFalha, tipoRemocaoFalha);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AnelActionListener anelActionListener = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneAnel().displayGUI(anelActionListener);

            }
        });

    }

}
