package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import json.deserializers.AreaDeserializer;
import json.serializers.AreaSerializer;
import org.joda.time.DateTime;

/**
 * Entidade que representa a {@link Area} no sistema
 *
 * @author lesiopinheiro
 */
@JsonSerialize(using = AreaSerializer.class)
@JsonDeserialize(using = AreaDeserializer.class)
@Entity
@Table(name = "areas")
public class Area extends Model implements Cloneable {

    private static final long serialVersionUID = 3282755453785165923L;
    public static Finder<UUID, Area> find = new Finder<UUID, Area>(Area.class);

    @Id
    private UUID id;

    @Column
    @Min(value = 1, message = "deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer descricao;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Cidade cidade;

    @OneToMany(mappedBy = "area")
    private List<Usuario> usuarios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private List<LimiteArea> limitesGeograficos;


    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Controlador> controladores;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getDescricao() {
        return descricao;
    }

    public void setDescricao(Integer descricao) {
        this.descricao = descricao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Controlador> getControladores() {
        return controladores;
    }

    public void setControladores(List<Controlador> controladores) {
        this.controladores = controladores;
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

    public List<LimiteArea> getLimitesGeograficos() {
        return limitesGeograficos;
    }

    public void setLimitesGeograficos(List<LimiteArea> limitesGeograficos) {
        this.limitesGeograficos = limitesGeograficos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
