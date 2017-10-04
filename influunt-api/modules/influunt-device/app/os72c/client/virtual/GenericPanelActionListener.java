package os72c.client.virtual;

import engine.EventoMotor;
import engine.TipoEvento;
import org.joda.time.DateTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rodrigosol on 11/26/16.
 */
public abstract class GenericPanelActionListener implements ActionListener {
    protected final ControladorForm controladorForm;

    protected final TipoEvento tipoFalha;

    private final TipoEvento tipoRemocaoFalha;


    public GenericPanelActionListener(ControladorForm callback, TipoEvento tipoFalha, TipoEvento tipoRemocaoFalha) {
        this.controladorForm = callback;
        this.tipoFalha = tipoFalha;
        this.tipoRemocaoFalha = tipoRemocaoFalha;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

    public void onFalha(String anel) {
        controladorForm.getCallback().onEvento(new EventoMotor(DateTime.now(), tipoFalha, Integer.valueOf(anel)));
    }

    public void onRemocaoFalha(String anel) {
        if (anel != null) {
            controladorForm.getCallback().onEvento(new EventoMotor(DateTime.now(), tipoRemocaoFalha, Integer.valueOf(anel)));
        } else {
            controladorForm.getCallback().onEvento(new EventoMotor(DateTime.now(), tipoRemocaoFalha));
        }
    }

    public TipoEvento getTipoRemocaoFalha() {
        return tipoRemocaoFalha;
    }
}
