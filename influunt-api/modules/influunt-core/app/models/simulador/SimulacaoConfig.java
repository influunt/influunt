package models.simulador;

import models.TipoDetector;
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

    private List<DetectorSimulacaoConfig> detectores;

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

    public List<DetectorSimulacaoConfig> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<DetectorSimulacaoConfig> detectores) {
        this.detectores = detectores;
    }

    public static class EstagioSimulacaoConfig {

        private final Integer posicao;

        private final String imagem;

        public EstagioSimulacaoConfig(Integer posicao, String imagem) {
            this.posicao = posicao;
            this.imagem = imagem;
        }

        public Integer getPosicao() {
            return posicao;
        }

        public String getImagem() {
            return imagem;
        }
    }

    public static class AnelSimulacaoConfig {
        public List<EstagioSimulacaoConfig> estagios = new ArrayList<>();

        private int numero;

        private boolean aceitaModoManual;

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

        public List<EstagioSimulacaoConfig> getEstagios() {
            return estagios;
        }

        public void setEstagios(List<EstagioSimulacaoConfig> estagios) {
            this.estagios = estagios;
        }

        public boolean isAceitaModoManual() {
            return aceitaModoManual;
        }

        public void setAceitaModoManual(boolean aceitaModoManual) {
            this.aceitaModoManual = aceitaModoManual;
        }
    }

    public static class DetectorSimulacaoConfig {
        private final String tipo;

        private final Integer anel;

        private final Integer posicao;

        public DetectorSimulacaoConfig(TipoDetector tipo, Integer anel, Integer posicao) {
            this.tipo = tipo.toString();
            this.anel = anel;
            this.posicao = posicao;
        }

        public String getTipo() {
            return tipo;
        }

        public Integer getAnel() {
            return anel;
        }

        public Integer getPosicao() {
            return posicao;
        }
    }
}
