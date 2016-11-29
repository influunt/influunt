package os72c.client.virtual;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GenericJOptionPane implements ActionListener {

    protected String param;

    public void displayGUI(GenericPanelActionListener actionListener) {
        Object[] options;
        if(actionListener.getTipoRemocaoFalha()!=null) {
            options = new Object[]{"Causar","Remover"};
        }else{
            options = new Object[]{"Causar"};
        }
        int result = JOptionPane.showOptionDialog(null,
                        getPanel(),
                        "Configure a Falha : ",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
            null,options,options[0]);

        if(result==0){
            actionListener.onFalha(this.param);
        }else if(result == 1){
            actionListener.onRemocaoFalha(this.param);
        }

    }

    protected abstract JPanel getPanel();


    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        this.param  = (String)cb.getSelectedItem();
    }
}
