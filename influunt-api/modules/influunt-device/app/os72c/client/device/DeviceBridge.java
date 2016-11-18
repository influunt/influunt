package os72c.client.device;

import engine.IntervaloGrupoSemaforico;

/**
 * Created by rodrigosol on 11/4/16.
 */
public interface DeviceBridge {
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico);
    public void start(DeviceBridgeCallback callback);
}
