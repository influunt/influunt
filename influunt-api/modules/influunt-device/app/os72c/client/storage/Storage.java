package os72c.client.storage;

import models.Controlador;
import models.StatusDevice;

/**
 * Created by leonardo on 9/13/16.
 */
public interface Storage {
    public StatusDevice getStatus();
    public void setStatus(StatusDevice statusDevice);
    public Controlador getControlador();
    public void setControlador(Controlador controlador);
    public Controlador getControladorStaging();
    public void setControladorStaging(Controlador controlador);
}
