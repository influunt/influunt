package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade que representa um {@link AtrasoDeGrupo} no sistema
 *
 * @author Pedro Pires
 */
@Entity
@Table(name = "atrasos_de_grupos")
@ChangeLog
public class AtrasoDeGrupo extends Model implements Cloneable, Serializable {

    public static Finder<UUID, AtrasoDeGrupo> find = new Finder<UUID, AtrasoDeGrupo>(AtrasoDeGrupo.class);

    private static final long serialVersionUID = -7392788716344603379L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @OneToOne
    private Transicao transicao;

    @Column
    @NotNull(message = "não pode ficar em branco")
    @Range(min = 0, max = 20, message = "deve estar entre {min} e {max}")
    private Integer atrasoDeGrupo = 0;

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

    public AtrasoDeGrupo() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public AtrasoDeGrupo(Integer atrasoDeGrupo) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.atrasoDeGrupo = atrasoDeGrupo;
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

    public Transicao getTransicao() {
        return transicao;
    }

    public void setTransicao(Transicao transicao) {
        this.transicao = transicao;
    }

    public Integer getAtrasoDeGrupo() {
        return atrasoDeGrupo;
    }

    public void setAtrasoDeGrupo(Integer atrasoDeGrupo) {
        this.atrasoDeGrupo = atrasoDeGrupo;
    }
}
