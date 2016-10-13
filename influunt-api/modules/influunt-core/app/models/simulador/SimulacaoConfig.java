package models.simulador;

import models.Anel;
import models.TipoGrupoSemaforico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimulacaoConfig {
    private String simulacaoId;
    private String controladorId;
    private List<Integer> tempoCicloAnel = new ArrayList<>();
    private List<AnelSimulacaoConfig> aneis;

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

    public List<Integer> getTempoCicloAnel() {
        return tempoCicloAnel;
    }

    public void setTempoCicloAnel(List<Integer> tempoCicloAnel) {
        this.tempoCicloAnel = tempoCicloAnel;
    }

    public List<AnelSimulacaoConfig> getAneis() {
        return aneis;
    }

    public void setAneis(List<AnelSimulacaoConfig> aneis) {
        this.aneis = aneis;
    }

    public static class AnelSimulacaoConfig{
            private int numero;
            private List<TipoGrupoSemaforico> tiposGruposSemaforicos = new ArrayList<>();

            public int getNumero() {
                return numero;
            }

            public void setNumero(int numero) {
                this.numero = numero;
            }

            public List<TipoGrupoSemaforico> getTiposGruposSemaforicos() {
                return tiposGruposSemaforicos;
            }

            public void setTiposGruposSemaforicos(List<TipoGrupoSemaforico> tiposGruposSemaforicos) {
                this.tiposGruposSemaforicos = tiposGruposSemaforicos;
            }
        }
}
