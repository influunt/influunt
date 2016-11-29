package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import org.joda.time.DateTime;
import os72c.client.device.DeviceBridgeCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class GrupoSemaforicoActionListener  extends  GenericPanelActionListener {


    public GrupoSemaforicoActionListener(ControladorForm callback, TipoEvento tipoFalha, TipoEvento tipoRemocaoFalha) {
        super(callback, tipoFalha, tipoRemocaoFalha);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final GrupoSemaforicoActionListener actionListener = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneGrupoSemaforico().displayGUI(actionListener);

            }
        });
    }

}
