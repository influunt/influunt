package models;

import checks.ControladorAssociacaoDetectoresCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa o {@link Detector} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "detectores")
@ChangeLog
public class Detector extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = 3752412658492551927L;

    public static Finder<UUID, Detector> find = new Finder<UUID, Detector>(Detector.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoDetector tipo;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @Column
    @OneToOne
    private Estagio estagio;

    @Column
    private Integer posicao;

    @Column
    private String descricao;

    @Column
    private Boolean monitorado = true;

    @Column
    private Integer tempoAusenciaDeteccao;

    @Column
    private Integer tempoDeteccaoPermanente;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    @Transient
    private boolean comFalha;

    public Detector() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoDetector getTipo() {
        return tipo;
    }

    public void setTipo(TipoDetector tipo) {
        this.tipo = tipo;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public Controlador getControlador() {
        return getAnel().getControlador();
    }

    public void setControlador(Controlador controlador) {
        getAnel().setControlador(controlador);
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    public Boolean isMonitorado() {
        return monitorado;
    }

    public void setMonitorado(Boolean monitorado) {
        this.monitorado = monitorado;
    }

    public Integer getTempoAusenciaDeteccao() {
        return tempoAusenciaDeteccao;
    }

    public void setTempoAusenciaDeteccao(Integer tempoAusenciaDeteccao) {
        this.tempoAusenciaDeteccao = tempoAusenciaDeteccao;
    }

    public Integer getTempoDeteccaoPermanente() {
        return tempoDeteccaoPermanente;
    }

    public void setTempoDeteccaoPermanente(Integer tempoDeteccaoPermanente) {
        this.tempoDeteccaoPermanente = tempoDeteccaoPermanente;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }


    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
        message = "O tempo de ausência de detecção deve estar entre {min} e {max}.")
    public boolean isTempoAusenciaDeteccaoEstaDentroDaFaixa() {
        if (isMonitorado()) {
            return getTempoAusenciaDeteccao() != null && getAnel().getControlador().getRangeUtils().TEMPO_AUSENCIA_DETECCAO.contains(getTempoAusenciaDeteccao());
        }
        return true;
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
        message = "O tempo de detecção permanente deve estar entre {min} e {max}.")
    public boolean isTempoDeteccaoPermanenteEstaDentroDaFaixa() {
        if (isMonitorado()) {
            return getTempoDeteccaoPermanente() != null && getAnel().getControlador().getRangeUtils().TEMPO_DETECCAO_PERMANENTE.contains(getTempoDeteccaoPermanente());
        }
        return true;
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
        message = "O detector veicular deve estar associado a um estágio com grupo semafórico veicular.")
    public boolean isAssociadoAoMenosUmEstagioVeicular() {
        if (this.isAssociadoAoMenosUmEstagio() && this.isVeicular()) {
            return getEstagio().getGruposSemaforicos().stream().anyMatch(grupoSemaforico -> grupoSemaforico.isVeicular());
        }
        return true;
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
        message = "O detector de pedestre deve estar associado a um estágio com grupo semafórico de pedestre.")
    public boolean isAssociadoAoMenosUmEstagioPedestre() {
        if (this.isAssociadoAoMenosUmEstagio() && this.isPedestre()) {
            return getEstagio().getGruposSemaforicos().stream().anyMatch(grupoSemaforico -> grupoSemaforico.isPedestre());
        }
        return true;
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
        message = "O detector deve estar associado a pelo menos um estágio.")
    public boolean isAssociadoAoMenosUmEstagio() {
        return !Objects.isNull(getEstagio());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detector detector = (Detector) o;

        return id != null ? id.equals(detector.id) : detector.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Transient
    public boolean isVeicular() {
        return TipoDetector.VEICULAR.equals(this.getTipo());
    }

    @Transient
    public boolean isPedestre() {
        return TipoDetector.PEDESTRE.equals(this.getTipo());
    }

    public boolean isComFalha() {
        return comFalha;
    }

    public void setComFalha(boolean comFalha) {
        this.comFalha = comFalha;
    }

    @Override
    public String toString() {
        return "D".concat(Character.toString(getTipo().toString().charAt(0))).concat(getPosicao().toString());
    }
}
