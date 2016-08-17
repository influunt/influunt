package models;

import checks.ControladorAssociacaoGruposSemaforicosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade que representa o {@link EstagioGrupoSemaforico} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "estagios_grupos_semaforicos")
public class EstagioGrupoSemaforico extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = 5983122994022833262L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotNull
    private Boolean ativo = false;

    @ManyToOne
    @NotNull(groups = ControladorAssociacaoGruposSemaforicosCheck.class)
    private Estagio estagio;

    @ManyToOne
    @NotNull(groups = ControladorAssociacaoGruposSemaforicosCheck.class)
    private GrupoSemaforico grupoSemaforico;

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

    public EstagioGrupoSemaforico() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
    }

    public EstagioGrupoSemaforico(Estagio estagio, GrupoSemaforico grupoSemaforico) {
        super();
        this.setIdJson(UUID.randomUUID().toString());
        this.estagio = estagio;
        this.grupoSemaforico = grupoSemaforico;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    public GrupoSemaforico getGrupoSemaforico() {
        return grupoSemaforico;
    }

    public void setGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
        this.grupoSemaforico = grupoSemaforico;
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

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class)
    public boolean isTipoGrupoSemaforicoOk() {
        if (this.grupoSemaforico != null) {
            return this.grupoSemaforico.getTipo() != null;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
