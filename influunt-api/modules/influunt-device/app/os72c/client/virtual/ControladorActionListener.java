package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class ControladorActionListener extends GenericPanelActionListener {

    public ControladorActionListener(ControladorForm callback, TipoEvento tipoFalha, TipoEvento tipoRemocaoFalha) {
        super(callback, tipoFalha, tipoRemocaoFalha);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final ControladorActionListener controladorActionListener = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneControlador().displayGUI(controladorActionListener);

            }
        });

    }

}
