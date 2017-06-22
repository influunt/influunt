package os72c.client.virtual;

import javax.swing.*;
import java.awt.event.ActionListener;

public class JOptionPaneDetectorPedestre extends GenericJOptionPane implements ActionListener {


    protected JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecione o Detector de Pedestre");

        String[] aneis = {"1", "2", "3", "4"};

        JComboBox aneisList = new JComboBox(aneis);
        aneisList.setSelectedIndex(0);
        aneisList.addActionListener(this);
        panel.add(label);
        panel.add(aneisList);
        super.param = "1";
        return panel;
    }


}
