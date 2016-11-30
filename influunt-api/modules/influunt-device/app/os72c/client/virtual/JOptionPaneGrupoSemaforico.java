package os72c.client.virtual;

import javax.swing.*;

public class JOptionPaneGrupoSemaforico extends GenericJOptionPane {

    protected JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecione o Grupo Semáforico");

        String[] aneis = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};

        JComboBox aneisList = new JComboBox(aneis);
        aneisList.setSelectedIndex(0);
        aneisList.addActionListener(this);
        panel.add(label);
        panel.add(aneisList);
        super.param = "1";
        return panel;
    }

}
