package os72c.client.device;

import engine.EventoMotor;

import java.util.concurrent.CompletableFuture;

/**
 * Created by rodrigosol on 11/17/16.
 */
public interface DeviceBridgeCallback {
    void onReady();
    CompletableFuture<?> restart();

    void onEvento(EventoMotor eventoMotor);


    void onInfo(String fabricante, String modelo, String versao);
}
