package models;

import checks.ControladorAssociacaoDetectoresCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.DetectorDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.DetectorSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa o {@link Detector} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "detectores")
@JsonSerialize(using = DetectorSerializer.class)
@JsonDeserialize(using = DetectorDeserializer.class)
public class Detector extends Model implements Cloneable {

    private static final long serialVersionUID = 3752412658492551927L;

    public static Finder<UUID, Detector> find = new Finder<UUID, Detector>(Detector.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private TipoDetector tipo;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @OneToOne
    private Estagio estagio;

    @ManyToOne
    private Controlador controlador;

    @Column
    private Integer posicao;

    @Column
    private String descricao;

    @Column
    private Boolean monitorado = true;

    @Column
    private Integer tempoAusenciaDeteccaoMinima;

    @Column
    private Integer tempoAusenciaDeteccaoMaxima;

    @Column
    private Integer tempoDeteccaoPermanenteMinima;

    @Column
    private Integer tempoDeteccaoPermanenteMaxima;

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

    public Detector() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
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
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
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

    public Boolean getMonitorado() {
        return monitorado;
    }

    public void setMonitorado(Boolean monitorado) {
        this.monitorado = monitorado;
    }

    public Integer getTempoAusenciaDeteccaoMinima() {
        return tempoAusenciaDeteccaoMinima;
    }

    public void setTempoAusenciaDeteccaoMinima(Integer tempoAusenciaDeteccaoMinima) {
        this.tempoAusenciaDeteccaoMinima = tempoAusenciaDeteccaoMinima;
    }

    public Integer getTempoAusenciaDeteccaoMaxima() {
        return tempoAusenciaDeteccaoMaxima;
    }

    public void setTempoAusenciaDeteccaoMaxima(Integer tempoAusenciaDeteccaoMaxima) {
        this.tempoAusenciaDeteccaoMaxima = tempoAusenciaDeteccaoMaxima;
    }

    public Integer getTempoDeteccaoPermanenteMinima() {
        return tempoDeteccaoPermanenteMinima;
    }

    public void setTempoDeteccaoPermanenteMinima(Integer tempoDeteccaoPermanenteMinima) {
        this.tempoDeteccaoPermanenteMinima = tempoDeteccaoPermanenteMinima;
    }

    public Integer getTempoDeteccaoPermanenteMaxima() {
        return tempoDeteccaoPermanenteMaxima;
    }

    public void setTempoDeteccaoPermanenteMaxima(Integer tempoDeteccaoPermanenteMaxima) {
        this.tempoDeteccaoPermanenteMaxima = tempoDeteccaoPermanenteMaxima;
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
            message = "O detector deve estar associado a pelo menos um estagio.")
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
}
