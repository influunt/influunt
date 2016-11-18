package os72c.client.device;

import engine.EventoMotor;

/**
 * Created by rodrigosol on 11/17/16.
 */
public interface DeviceBridgeCallback {
    void onReady();
    void onEvento(EventoMotor eventoMotor);
}
