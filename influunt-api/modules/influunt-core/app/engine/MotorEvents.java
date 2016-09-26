package engine;

import models.Evento;

import static com.sun.tools.doclint.Entity.pi;

/**
 * Created by rodrigosol on 9/26/16.
 */
public interface MotorEvents {
    public void onError();
    public void imporPlano(Evento evento);
    public void desimporPlano();
    public void ativarOperacaoManual();
    public void desativarOperacaoManual();

}
