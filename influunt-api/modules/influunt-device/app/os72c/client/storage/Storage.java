package os72c.client.storage;

import com.fasterxml.jackson.databind.JsonNode;
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

    public void setPlanos(JsonNode plano);

    public Controlador getControladorStaging();

    public void setControladorStaging(Controlador controlador);

    public void setPlanosStaging(JsonNode plano);

    public JsonNode getControladorJson();

    public JsonNode getControladorJsonStaging();

    public JsonNode getPlanos();

    public JsonNode getPlanosStaging();

}
