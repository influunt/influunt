package simulador;

import models.TipoGrupoSemaforico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimulacaoConfig {
    private String simulacaoId;
    private String controladorId;
    private List<TipoGrupoSemaforico> tiposGruposSemaforicos = new ArrayList<>();
    private List<Integer> tempoCicloAnel = new ArrayList<>();

    public String getSimulacaoId() {
        return simulacaoId;
    }

    public void setSimulacaoId(String simulacaoId) {
        this.simulacaoId = simulacaoId;
    }

    public String getControladorId() {
        return controladorId;
    }

    public void setControladorId(String controladorId) {
        this.controladorId = controladorId;
    }

    public List<TipoGrupoSemaforico> getTiposGruposSemaforicos() {
        return tiposGruposSemaforicos;
    }

    public void setTiposGruposSemaforicos(List<TipoGrupoSemaforico> tiposGruposSemaforicos) {
        this.tiposGruposSemaforicos = tiposGruposSemaforicos;
    }

    public List<Integer> getTempoCicloAnel() {
        return tempoCicloAnel;
    }

    public void setTempoCicloAnel(List<Integer> tempoCicloAnel) {
        this.tempoCicloAnel = tempoCicloAnel;
    }
}
