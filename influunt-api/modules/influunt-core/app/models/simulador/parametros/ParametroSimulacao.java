package models.simulador.parametros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.simulacao.ParametroSimulacaoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Controlador;
import models.Detector;
import models.simulador.SimulacaoConfig;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
@JsonDeserialize(using = ParametroSimulacaoDeserializer.class)
public class ParametroSimulacao {

    private UUID id;

    @JsonIgnore
    @NotNull(message = "não pode ficar em branco")
    private Controlador controlador;

    @NotNull(message = "não pode ficar em branco")
    private Double velocidade;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime inicioControlador;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
    private DateTime inicioSimulacao;

    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @NotNull(message = "não pode ficar em branco")
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

    public Double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Double velocidade) {
        this.velocidade = velocidade;
    }

    public DateTime getInicioControlador() {
        return inicioControlador;
    }

    public void setInicioControlador(DateTime inicioControlador) {
        this.inicioControlador =  inicioControlador;
    }

    public DateTime getInicioSimulacao() {
        return inicioSimulacao;
    }

    public void setInicioSimulacao(DateTime inicioSimulacao) {
        this.inicioSimulacao = inicioSimulacao;
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
        this.controlador = Controlador.find.byId(idControlador);
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

    public void associa() {
        getDetectores().stream().forEach(parametroSimulacaoDetector -> {
            parametroSimulacaoDetector.setDetector(Detector.find.byId(parametroSimulacaoDetector.getDetector().getId()));
        });
    }
}
