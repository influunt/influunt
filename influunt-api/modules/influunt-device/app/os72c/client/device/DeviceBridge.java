package os72c.client.device;

import engine.IntervaloGrupoSemaforico;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 11/4/16.
 */
public interface DeviceBridge {
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico);

    public void start(DeviceBridgeCallback callback);

    public void modoManualAtivo(DateTime timestamp);
    public void modoManualDesativo(DateTime timestamp);
}
