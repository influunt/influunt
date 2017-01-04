package os72c.client.device;

import engine.IntervaloGrupoSemaforico;

/**
 * Created by rodrigosol on 11/4/16.
 */
public interface DeviceBridge {
    void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico);

    void start(DeviceBridgeCallback callback);

    void modoManualAtivo();

    void modoManualDesativado();

    void sendAneis(int[] aneis);
}
