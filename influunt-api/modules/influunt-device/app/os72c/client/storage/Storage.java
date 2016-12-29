package os72c.client.storage;

import com.fasterxml.jackson.databind.JsonNode;
import engine.EventoMotor;
import engine.TipoEvento;
import models.Controlador;
import models.StatusAnel;
import models.StatusDevice;
import protocol.Envelope;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by leonardo on 9/13/16.
 */
public interface Storage {
    StatusDevice getStatus();

    void setStatus(StatusDevice statusDevice);

    Controlador getControlador();

    void setControlador(Controlador controlador);

    Controlador getControladorStaging();

    void setControladorStaging(Controlador controlador);

    JsonNode getControladorJson();

    JsonNode getControladorJsonStaging();

    JsonNode getPlanos();

    void setPlanos(JsonNode plano);

    JsonNode getPlanosStaging();

    void setPlanosStaging(JsonNode plano);

    String getPrivateKey();

    void setPrivateKey(String publicKey);

    String getCentralPublicKey();

    void setCentralPublicKey(String publicKey);

    long getHorarioEntradaTabelaHoraria();

    void setHorarioEntradaTabelaHoraria(long horarioEntrada);

    void addFalha(EventoMotor falha);

    void removeFalha(EventoMotor falha);

    boolean emFalha();

    void setTempData(String id, String key, String value);

    String getTempData(String id,String key);

    void clearTempData(String id);

    String getMarca();

    String getModelo();

    String getFirmware();

    void storeEnvelope(Envelope envelope);

    Collection getEnvelopes();

    void clearEnvelopes();

    HashMap<Integer, StatusAnel> getStatusAneis();

    StatusAnel getStatusAnel(Integer anel);

    void setStatusAnel(Integer anel, StatusAnel statusAnel);

    void setStatusAneis(StatusAnel statusAnel);
}
