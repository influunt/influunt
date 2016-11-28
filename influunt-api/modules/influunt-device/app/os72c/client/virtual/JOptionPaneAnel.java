package os72c.client.virtual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.imageio.ImageIO;

public class JOptionPaneAnel extends GenericJOptionPane implements ActionListener {

    protected JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecione o Anel");

        String[] aneis = { "1", "2", "3", "4"};

        JComboBox aneisList = new JComboBox(aneis);
        aneisList.setSelectedIndex(0);
        aneisList.addActionListener(this);
        panel.add(label);
        panel.add(aneisList);
        super.param = "1";

        return panel;
    }


}
