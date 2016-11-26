package models.simulador.parametros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.simulacao.ParametroSimulacaoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import models.Controlador;
import models.simulador.SimulacaoConfig;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Valid
    private List<ParametroSimulacaoDetector> detectores = new ArrayList<>();

    @Valid
    private List<ParametroSimulacaoImposicaoPlano> imposicoes = new ArrayList<>();

    @Valid
    private List<ParametroSimulacaoImposicaoModo> imposicoesModos = new ArrayList<>();

    @Valid
    private List<ParametroSimulacaoFalha> falhas = new ArrayList<>();

    @Valid
    private List<ParametroSimulacaoAlarme> alarmes = new ArrayList<>();

    private UUID idControlador;

    private List<ParametroSimulacaoManual> insercaoDePlugDeControleManual = new ArrayList<>();

    private List<ParametroSimulacaoTrocaDeEstagioManual> trocasEstagioModoManual = new ArrayList<>();

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
        this.inicioControlador = inicioControlador;
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

    public List<ParametroSimulacaoImposicaoModo> getImposicoesModos() {
        return imposicoesModos;
    }

    public void setImposicoesModos(List<ParametroSimulacaoImposicaoModo> imposicoesModos) {
        this.imposicoesModos = imposicoesModos;
    }

    public List<ParametroSimulacaoFalha> getFalhas() {
        return falhas;
    }

    public void setFalhas(List<ParametroSimulacaoFalha> falhas) {
        this.falhas = falhas;
    }

    public List<ParametroSimulacaoAlarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<ParametroSimulacaoAlarme> alarmes) {
        this.alarmes = alarmes;
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
        List<SimulacaoConfig.DetectorSimulacaoConfig> detectores = new ArrayList<>();

        getControlador().getAneis().stream()
            .sorted((o1, o2) -> o1.getPosicao()
                .compareTo(o2.getPosicao())).forEach(anel -> {
            SimulacaoConfig.AnelSimulacaoConfig anelSimulacaoConfig = new SimulacaoConfig.AnelSimulacaoConfig();
            anelSimulacaoConfig.setNumero(anel.getPosicao());
            anel.getGruposSemaforicos().stream().sorted((o1, o2) -> o1.getPosicao().compareTo(o2.getPosicao()))
                .forEach(grupoSemaforico -> anelSimulacaoConfig.getTiposGruposSemaforicos().add(grupoSemaforico.getTipo()));
            anel.getEstagios().forEach(estagio -> {
                anelSimulacaoConfig.getEstagios().add(new SimulacaoConfig.EstagioSimulacaoConfig(estagio.getPosicao(),
                    "api/v1/imagens/" + estagio.getImagem().getId() + "/thumb"));
            });
            aneis.add(anelSimulacaoConfig);

            List<SimulacaoConfig.DetectorSimulacaoConfig> detectoresConfig = anel.getDetectores().stream().map(detector ->
                new SimulacaoConfig.DetectorSimulacaoConfig(detector.getTipo(),
                    anel.getPosicao(),
                    detector.getPosicao())).collect(Collectors.toList());

            detectores.addAll(detectoresConfig);

        });

        sc.setAneis(aneis);


        sc.setDetectores(detectores);


        return sc;
    }

    public List<ParametroSimulacaoManual> getInsercaoDePlugDeControleManual() {
        return insercaoDePlugDeControleManual;
    }

    public List<ParametroSimulacaoTrocaDeEstagioManual> getTrocasEstagioModoManual() {
        return trocasEstagioModoManual;
    }
}
