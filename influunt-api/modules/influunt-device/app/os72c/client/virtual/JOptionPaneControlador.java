package os72c.client.virtual;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JOptionPaneControlador extends GenericJOptionPane implements ActionListener {

    private String anel;

    protected JPanel getPanel() {
        JPanel panel = new JPanel();
        return panel;
    }

}
