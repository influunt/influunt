package os72c.client.observer;

import com.google.inject.Singleton;
import models.Plano;
import models.StatusAnel;
import models.StatusDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static os72c.client.Versao.versao;

/**
 * Created by rodrigosol on 12/16/16.
 */
@Singleton
public class EstadoDevice {

    private boolean conectado = false;

    private StatusDevice status;

    private HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();

    private Map<Integer, Plano> planos = new HashMap<>();

    private List<DeviceObserver> observers = new ArrayList<>();

    public void addObserver(DeviceObserver deviceObserver) {
        this.observers.add(deviceObserver);
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
        notifica();
    }

    public StatusDevice getStatus() {
        return status;
    }

    public void setStatus(StatusDevice status) {
        this.status = status;
        notifica();
    }

    public StatusAnel getStatusAnel(Integer anel) {
        return statusAneis.get(anel);
    }

    public void setStatusAnel(Integer anel, StatusAnel statusAnel) {
        this.statusAneis.put(anel, statusAnel);
    }

    public void setStatusAneis(StatusAnel statusAnel) {
        for (int i = 1; i <= this.statusAneis.size(); i++) {
            this.statusAneis.put(i, statusAnel);
        }
    }

    public Map<Integer, Plano> getPlanos() {
        return planos;
    }

    private void notifica() {
        this.observers.stream().forEach(observer -> observer.onEstadoDeviceChange());
    }

    public void putPlanos(Integer anel, Plano plano) {
        this.planos.put(anel, plano);
        notifica();
    }

    @Override
    public String toString() {
        return "EstadoDevice{" +
            "conectado=" + conectado +
            ", status=" + status +
            ", planos=" + planos +
            '}';
    }
}
