package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.ModeloControladorDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.ModeloControladorSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que repesenta o {@link ModeloControlador} do {@link Controlador} no
 * sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "modelo_controladores")
@ChangeLog
@JsonSerialize(using = ModeloControladorSerializer.class)
@JsonDeserialize(using = ModeloControladorDeserializer.class)
public class ModeloControlador extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -3153929481907380680L;

    public static Finder<UUID, ModeloControlador> find = new Finder<UUID, ModeloControlador>(ModeloControlador.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Fabricante fabricante;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private String descricao;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteEstagio = 16;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteGrupoSemaforico = 16;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteAnel = 4;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteDetectorPedestre = 4;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteDetectorVeicular = 8;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limiteTabelasEntreVerdes = 2;

    @Column
    @Min(value = 1, message = "Deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer limitePlanos = 16;

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

    public ModeloControlador() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public static ModeloControlador findById(UUID id) {
        return find.fetch("fabricante").where().eq("id", id).findUnique();
    }

    public static List<ModeloControlador> findAll() {
        return find.fetch("fabricante").findList();
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

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getLimiteEstagio() {
        return limiteEstagio;
    }

    public void setLimiteEstagio(Integer limiteEstagio) {
        this.limiteEstagio = limiteEstagio;
    }

    public Integer getLimiteGrupoSemaforico() {
        return limiteGrupoSemaforico;
    }

    public void setLimiteGrupoSemaforico(Integer limiteGrupoSemaforico) {
        this.limiteGrupoSemaforico = limiteGrupoSemaforico;
    }

    public Integer getLimiteAnel() {
        return limiteAnel;
    }

    public void setLimiteAnel(Integer limiteAnel) {
        this.limiteAnel = limiteAnel;
    }

    public Integer getLimiteDetectorPedestre() {
        return limiteDetectorPedestre;
    }

    public void setLimiteDetectorPedestre(Integer limiteDetectorPedestre) {
        this.limiteDetectorPedestre = limiteDetectorPedestre;
    }

    public Integer getLimiteDetectorVeicular() {
        return limiteDetectorVeicular;
    }

    public void setLimiteDetectorVeicular(Integer limiteDetectorVeicular) {
        this.limiteDetectorVeicular = limiteDetectorVeicular;
    }

    public Integer getLimiteTabelasEntreVerdes() {
        return limiteTabelasEntreVerdes;
    }

    public void setLimiteTabelasEntreVerdes(Integer limiteTabelasEntreVerdes) {
        this.limiteTabelasEntreVerdes = limiteTabelasEntreVerdes;
    }

    public Integer getLimitePlanos() {
        return limitePlanos;
    }

    public void setLimitePlanos(Integer limitePlanos) {
        this.limitePlanos = limitePlanos;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModeloControlador that = (ModeloControlador) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
