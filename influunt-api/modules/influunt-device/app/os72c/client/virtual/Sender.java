package os72c.client.virtual;

import engine.EventoMotor;
import os72c.client.protocols.Mensagem;

/**
 * Created by rodrigosol on 11/26/16.
 */
public interface Sender {
    public void send(EventoMotor eventoMotor);
}
