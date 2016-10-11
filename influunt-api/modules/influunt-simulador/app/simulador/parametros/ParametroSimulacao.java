package simulador.parametros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Controlador;
import org.joda.time.DateTime;
import simulador.SimulacaoConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class ParametroSimulacao {

    private UUID id;

    @JsonIgnore
    private Controlador controlador;

    private int velocidade;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime inicioControlador;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime inicioSimulacao;

    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime fimSimulacao;

    private List<ParametroSimulacaoDetector> detectores = new ArrayList<>();

    private List<ParametroSimulacaoImposicaoPlano> imposicoes = new ArrayList<>();

    private List<ParametroFalha> falhas = new ArrayList<>();

    private UUID idControlador;



    public ParametroSimulacao() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {

        this.controlador = controlador;
        this.idControlador = controlador.getId();
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public DateTime getInicioControlador() {
        return inicioControlador;
    }

    public void setInicioControlador(DateTime inicioControlador) {
        this.inicioControlador = inicioControlador;
    }

    public DateTime getInicioSimulacao() {
        return inicioSimulacao;
    }

    public void setInicioSimulacao(DateTime inicioSimulacao) {
        this.inicioSimulacao = inicioSimulacao;
        this.inicioControlador = inicioSimulacao;
    }

    public DateTime getFimSimulacao() {
        return fimSimulacao;
    }

    public void setFimSimulacao(DateTime fimSimulacao) {
        this.fimSimulacao = fimSimulacao;
    }

    public List<ParametroSimulacaoDetector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<ParametroSimulacaoDetector> detectores) {
        this.detectores = detectores;
    }

    public List<ParametroSimulacaoImposicaoPlano> getImposicoes() {
        return imposicoes;
    }

    public void setImposicoes(List<ParametroSimulacaoImposicaoPlano> imposicoes) {
        this.imposicoes = imposicoes;
    }

    public List<ParametroFalha> getFalhas() {
        return falhas;
    }

    public void setFalhas(List<ParametroFalha> falhas) {
        this.falhas = falhas;
    }

    public UUID getIdControlador() {
        return idControlador;
    }

    public void setIdControlador(UUID idControlador) {
        this.idControlador = idControlador;
    }

    public SimulacaoConfig getSimulacaoConfig() {
        SimulacaoConfig sc = new SimulacaoConfig();
        sc.setControladorId(getControlador().getId().toString());
        sc.setSimulacaoId(getId().toString());
        List<SimulacaoConfig.AnelSimulacaoConfig> aneis = new ArrayList<>();

        getControlador().getAneis().stream()
                                   .sorted((o1, o2) -> o1.getPosicao()
                                   .compareTo(o2.getPosicao())).forEach(anel -> {
            SimulacaoConfig.AnelSimulacaoConfig anelSimulacaoConfig = new SimulacaoConfig.AnelSimulacaoConfig();
            anelSimulacaoConfig.setNumero(anel.getPosicao());
            anel.getGruposSemaforicos().stream().sorted((o1, o2) -> o1.getPosicao().compareTo(o2.getPosicao()))
                    .forEach(grupoSemaforico -> anelSimulacaoConfig.getTiposGruposSemaforicos().add(grupoSemaforico.getTipo()));

            aneis.add(anelSimulacaoConfig);
        });

        sc.setAneis(aneis);

        return sc;
    }
}
