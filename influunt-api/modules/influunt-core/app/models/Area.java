package models;

import checks.AreasCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.nashorn.internal.ir.annotations.Ignore;
import json.deserializers.AreaDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.AreaSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa a {@link Area} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "areas")
@ChangeLog
@JsonSerialize(using = AreaSerializer.class)
@JsonDeserialize(using = AreaDeserializer.class)
public class Area extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = 3282755453785165923L;

    public static Finder<UUID, Area> find = new Finder<UUID, Area>(Area.class);

    @Id
    private UUID id;

    @Ignore
    @Column
    private String idJson;

    @Column
    @Min(value = 1, message = "deve ser maior que zero")
    @NotNull(message = "não pode ficar em branco")
    private Integer descricao;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Cidade cidade;

    @OneToMany(mappedBy = "area")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "area")
    private List<Subarea> subareas;

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

    public Area() {
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

    public List<Subarea> getSubareas() {
        return subareas;
    }

    public void setSubareas(List<Subarea> subareas) {
        this.subareas = subareas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @AssertTrue(groups = AreasCheck.class,
        message = "Já existe uma Área cadastrada com essa descrição.")
    public boolean isDescricaoUnique() {
        if (Objects.nonNull(getDescricao())) {
            Area areaAux = Area.find.where().eq("cidade_id", getCidade().getId().toString()).ieq("descricao", getDescricao().toString()).findUnique();

            return areaAux == null || (this.getId() != null && areaAux.getId().equals(this.getId()));
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Area area = (Area) o;

        return id != null ? id.equals(area.id) : area.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

