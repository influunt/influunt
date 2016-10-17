package models;

import checks.FaixaDeValoresCheck;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade que representa {@link FaixasDeValores} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "faixas_de_valores")
@ChangeLog
public class FaixasDeValores extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = 3282755453785165923L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoDefasagemMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoDefasagemMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAmareloMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAmareloMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoIntermitenteMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoIntermitenteMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoLimpezaVeicularMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoLimpezaVeicularMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoLimpezaPedestreMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVermelhoLimpezaPedestreMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAtrasoGrupoMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAtrasoGrupoMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeSegurancaVeicularMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeSegurancaVeicularMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeSegurancaPedestreMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeSegurancaPedestreMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoMaximoPermanenciaEstagioMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoMaximoPermanenciaEstagioMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer defaultTempoMaximoPermanenciaEstagioVeicular;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoCicloMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoCicloMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMinimoMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMinimoMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMaximoMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMaximoMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeIntermediarioMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeIntermediarioMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Double tempoExtensaoVerdeMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Double tempoExtensaoVerdeMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoVerdeMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAusenciaDeteccaoMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoAusenciaDeteccaoMax;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoDeteccaoPermanenteMin;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer tempoDeteccaoPermanenteMax;

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

    public FaixasDeValores() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public static FaixasDeValores getInstance() {
        FaixasDeValores valores = Ebean.find(FaixasDeValores.class).setMaxRows(1).findUnique();
        return valores != null ? valores : getDefault();
    }

    private static FaixasDeValores getDefault() {
        FaixasDeValores valores = new FaixasDeValores();
        valores.setTempoDefasagemMin(0);
        valores.setTempoDefasagemMax(255);
        valores.setTempoAmareloMin(3);
        valores.setTempoAmareloMax(5);
        valores.setTempoVermelhoIntermitenteMin(3);
        valores.setTempoVermelhoIntermitenteMax(32);
        valores.setTempoVermelhoLimpezaVeicularMin(0);
        valores.setTempoVermelhoLimpezaVeicularMax(7);
        valores.setTempoVermelhoLimpezaPedestreMin(0);
        valores.setTempoVermelhoLimpezaPedestreMax(5);
        valores.setTempoAtrasoGrupoMin(0);
        valores.setTempoAtrasoGrupoMax(20);
        valores.setTempoVerdeSegurancaVeicularMin(10);
        valores.setTempoVerdeSegurancaVeicularMax(30);
        valores.setTempoVerdeSegurancaPedestreMin(4);
        valores.setTempoVerdeSegurancaPedestreMax(10);
        valores.setTempoMaximoPermanenciaEstagioMin(60);
        valores.setTempoMaximoPermanenciaEstagioMax(255);
        valores.setDefaultTempoMaximoPermanenciaEstagioVeicular(127);
        valores.setTempoCicloMin(30);
        valores.setTempoCicloMax(255);
        valores.setTempoVerdeMinimoMin(10);
        valores.setTempoVerdeMinimoMax(255);
        valores.setTempoVerdeMaximoMin(10);
        valores.setTempoVerdeMaximoMax(255);
        valores.setTempoVerdeIntermediarioMin(10);
        valores.setTempoVerdeIntermediarioMax(255);
        valores.setTempoExtensaoVerdeMin(1d);
        valores.setTempoExtensaoVerdeMax(10d);
        valores.setTempoVerdeMin(1);
        valores.setTempoVerdeMax(255);
        valores.setTempoAusenciaDeteccaoMin(0);
        valores.setTempoAusenciaDeteccaoMax(4320);
        valores.setTempoDeteccaoPermanenteMin(0);
        valores.setTempoDeteccaoPermanenteMax(1440);
        return valores;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public Integer getTempoDefasagemMin() {
        return tempoDefasagemMin;
    }

    public void setTempoDefasagemMin(Integer tempoDefasagemMin) {
        this.tempoDefasagemMin = tempoDefasagemMin;
    }

    public Integer getTempoDefasagemMax() {
        return tempoDefasagemMax;
    }

    public void setTempoDefasagemMax(Integer tempoDefasagemMax) {
        this.tempoDefasagemMax = tempoDefasagemMax;
    }

    public Integer getTempoAmareloMin() {
        return tempoAmareloMin;
    }

    public void setTempoAmareloMin(Integer tempoAmareloMin) {
        this.tempoAmareloMin = tempoAmareloMin;
    }

    public Integer getTempoAmareloMax() {
        return tempoAmareloMax;
    }

    public void setTempoAmareloMax(Integer tempoAmareloMax) {
        this.tempoAmareloMax = tempoAmareloMax;
    }

    public Integer getTempoVermelhoIntermitenteMin() {
        return tempoVermelhoIntermitenteMin;
    }

    public void setTempoVermelhoIntermitenteMin(Integer tempoVermelhoIntermitenteMin) {
        this.tempoVermelhoIntermitenteMin = tempoVermelhoIntermitenteMin;
    }

    public Integer getTempoVermelhoIntermitenteMax() {
        return tempoVermelhoIntermitenteMax;
    }

    public void setTempoVermelhoIntermitenteMax(Integer tempoVermelhoIntermitenteMax) {
        this.tempoVermelhoIntermitenteMax = tempoVermelhoIntermitenteMax;
    }

    public Integer getTempoVermelhoLimpezaVeicularMin() {
        return tempoVermelhoLimpezaVeicularMin;
    }

    public void setTempoVermelhoLimpezaVeicularMin(Integer tempoVermelhoLimpezaVeicularMin) {
        this.tempoVermelhoLimpezaVeicularMin = tempoVermelhoLimpezaVeicularMin;
    }

    public Integer getTempoVermelhoLimpezaVeicularMax() {
        return tempoVermelhoLimpezaVeicularMax;
    }

    public void setTempoVermelhoLimpezaVeicularMax(Integer tempoVermelhoLimpezaVeicularMax) {
        this.tempoVermelhoLimpezaVeicularMax = tempoVermelhoLimpezaVeicularMax;
    }

    public Integer getTempoVermelhoLimpezaPedestreMin() {
        return tempoVermelhoLimpezaPedestreMin;
    }

    public void setTempoVermelhoLimpezaPedestreMin(Integer tempoVermelhoLimpezaPedestreMin) {
        this.tempoVermelhoLimpezaPedestreMin = tempoVermelhoLimpezaPedestreMin;
    }

    public Integer getTempoVermelhoLimpezaPedestreMax() {
        return tempoVermelhoLimpezaPedestreMax;
    }

    public void setTempoVermelhoLimpezaPedestreMax(Integer tempoVermelhoLimpezaPedestreMax) {
        this.tempoVermelhoLimpezaPedestreMax = tempoVermelhoLimpezaPedestreMax;
    }

    public Integer getTempoAtrasoGrupoMin() {
        return tempoAtrasoGrupoMin;
    }

    public void setTempoAtrasoGrupoMin(Integer tempoAtrasoGrupoMin) {
        this.tempoAtrasoGrupoMin = tempoAtrasoGrupoMin;
    }

    public Integer getTempoAtrasoGrupoMax() {
        return tempoAtrasoGrupoMax;
    }

    public void setTempoAtrasoGrupoMax(Integer tempoAtrasoGrupoMax) {
        this.tempoAtrasoGrupoMax = tempoAtrasoGrupoMax;
    }

    public Integer getTempoVerdeSegurancaVeicularMin() {
        return tempoVerdeSegurancaVeicularMin;
    }

    public void setTempoVerdeSegurancaVeicularMin(Integer tempoVerdeSegurancaVeicularMin) {
        this.tempoVerdeSegurancaVeicularMin = tempoVerdeSegurancaVeicularMin;
    }

    public Integer getTempoVerdeSegurancaVeicularMax() {
        return tempoVerdeSegurancaVeicularMax;
    }

    public void setTempoVerdeSegurancaVeicularMax(Integer tempoVerdeSegurancaVeicularMax) {
        this.tempoVerdeSegurancaVeicularMax = tempoVerdeSegurancaVeicularMax;
    }

    public Integer getTempoVerdeSegurancaPedestreMin() {
        return tempoVerdeSegurancaPedestreMin;
    }

    public void setTempoVerdeSegurancaPedestreMin(Integer tempoVerdeSegurancaPedestreMin) {
        this.tempoVerdeSegurancaPedestreMin = tempoVerdeSegurancaPedestreMin;
    }

    public Integer getTempoVerdeSegurancaPedestreMax() {
        return tempoVerdeSegurancaPedestreMax;
    }

    public void setTempoVerdeSegurancaPedestreMax(Integer tempoVerdeSegurancaPedestreMax) {
        this.tempoVerdeSegurancaPedestreMax = tempoVerdeSegurancaPedestreMax;
    }

    public Integer getTempoMaximoPermanenciaEstagioMin() {
        return tempoMaximoPermanenciaEstagioMin;
    }

    public void setTempoMaximoPermanenciaEstagioMin(Integer tempoMaximoPermanenciaEstagioMin) {
        this.tempoMaximoPermanenciaEstagioMin = tempoMaximoPermanenciaEstagioMin;
    }

    public Integer getTempoMaximoPermanenciaEstagioMax() {
        return tempoMaximoPermanenciaEstagioMax;
    }

    public void setTempoMaximoPermanenciaEstagioMax(Integer tempoMaximoPermanenciaEstagioMax) {
        this.tempoMaximoPermanenciaEstagioMax = tempoMaximoPermanenciaEstagioMax;
    }

    public Integer getDefaultTempoMaximoPermanenciaEstagioVeicular() {
        return defaultTempoMaximoPermanenciaEstagioVeicular;
    }

    public void setDefaultTempoMaximoPermanenciaEstagioVeicular(Integer defaultTempoMaximoPermanenciaEstagioVeicular) {
        this.defaultTempoMaximoPermanenciaEstagioVeicular = defaultTempoMaximoPermanenciaEstagioVeicular;
    }

    public Integer getTempoCicloMin() {
        return tempoCicloMin;
    }

    public void setTempoCicloMin(Integer tempoCicloMin) {
        this.tempoCicloMin = tempoCicloMin;
    }

    public Integer getTempoCicloMax() {
        return tempoCicloMax;
    }

    public void setTempoCicloMax(Integer tempoCicloMax) {
        this.tempoCicloMax = tempoCicloMax;
    }

    public Integer getTempoVerdeMinimoMin() {
        return tempoVerdeMinimoMin;
    }

    public void setTempoVerdeMinimoMin(Integer tempoVerdeMinimoMin) {
        this.tempoVerdeMinimoMin = tempoVerdeMinimoMin;
    }

    public Integer getTempoVerdeMinimoMax() {
        return tempoVerdeMinimoMax;
    }

    public void setTempoVerdeMinimoMax(Integer tempoVerdeMinimoMax) {
        this.tempoVerdeMinimoMax = tempoVerdeMinimoMax;
    }

    public Integer getTempoVerdeMaximoMin() {
        return tempoVerdeMaximoMin;
    }

    public void setTempoVerdeMaximoMin(Integer tempoVerdeMaximoMin) {
        this.tempoVerdeMaximoMin = tempoVerdeMaximoMin;
    }

    public Integer getTempoVerdeMaximoMax() {
        return tempoVerdeMaximoMax;
    }

    public void setTempoVerdeMaximoMax(Integer tempoVerdeMaximoMax) {
        this.tempoVerdeMaximoMax = tempoVerdeMaximoMax;
    }

    public Integer getTempoVerdeIntermediarioMin() {
        return tempoVerdeIntermediarioMin;
    }

    public void setTempoVerdeIntermediarioMin(Integer tempoVerdeIntermediarioMin) {
        this.tempoVerdeIntermediarioMin = tempoVerdeIntermediarioMin;
    }

    public Integer getTempoVerdeIntermediarioMax() {
        return tempoVerdeIntermediarioMax;
    }

    public void setTempoVerdeIntermediarioMax(Integer tempoVerdeIntermediarioMax) {
        this.tempoVerdeIntermediarioMax = tempoVerdeIntermediarioMax;
    }

    public Double getTempoExtensaoVerdeMin() {
        return tempoExtensaoVerdeMin;
    }

    public void setTempoExtensaoVerdeMin(Double tempoExtensaoVerdeMin) {
        this.tempoExtensaoVerdeMin = tempoExtensaoVerdeMin;
    }

    public Double getTempoExtensaoVerdeMax() {
        return tempoExtensaoVerdeMax;
    }

    public void setTempoExtensaoVerdeMax(Double tempoExtensaoVerdeMax) {
        this.tempoExtensaoVerdeMax = tempoExtensaoVerdeMax;
    }

    public Integer getTempoVerdeMin() {
        return tempoVerdeMin;
    }

    public void setTempoVerdeMin(Integer tempoVerdeMin) {
        this.tempoVerdeMin = tempoVerdeMin;
    }

    public Integer getTempoVerdeMax() {
        return tempoVerdeMax;
    }

    public void setTempoVerdeMax(Integer tempoVerdeMax) {
        this.tempoVerdeMax = tempoVerdeMax;
    }

    public Integer getTempoAusenciaDeteccaoMin() {
        return tempoAusenciaDeteccaoMin;
    }

    public void setTempoAusenciaDeteccaoMin(Integer tempoAusenciaDeteccaoMin) {
        this.tempoAusenciaDeteccaoMin = tempoAusenciaDeteccaoMin;
    }

    public Integer getTempoAusenciaDeteccaoMax() {
        return tempoAusenciaDeteccaoMax;
    }

    public void setTempoAusenciaDeteccaoMax(Integer tempoAusenciaDeteccaoMax) {
        this.tempoAusenciaDeteccaoMax = tempoAusenciaDeteccaoMax;
    }

    public Integer getTempoDeteccaoPermanenteMin() {
        return tempoDeteccaoPermanenteMin;
    }

    public void setTempoDeteccaoPermanenteMin(Integer tempoDeteccaoPermanenteMin) {
        this.tempoDeteccaoPermanenteMin = tempoDeteccaoPermanenteMin;
    }

    public Integer getTempoDeteccaoPermanenteMax() {
        return tempoDeteccaoPermanenteMax;
    }

    public void setTempoDeteccaoPermanenteMax(Integer tempoDeteccaoPermanenteMax) {
        this.tempoDeteccaoPermanenteMax = tempoDeteccaoPermanenteMax;
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

    @AssertTrue(groups = FaixaDeValoresCheck.class, message = "o valor padrão deve estar entre o minímo e o máximo.")
    public boolean isTempoMaximoPermanenciaEstagioVeicularValido() {
        return Range.between(getTempoMaximoPermanenciaEstagioMin(), getTempoMaximoPermanenciaEstagioMax()).contains(defaultTempoMaximoPermanenciaEstagioVeicular);
    }
}
