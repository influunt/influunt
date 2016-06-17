package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import utils.InfluuntDateTimeDeserializer;
import utils.InfluuntDateTimeSerializer;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa a {@link Aera} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "areas")
public class Area extends Model {

    private static final long serialVersionUID = 3282755453785165923L;
    public static Finder<UUID, Area> find = new Finder<UUID, Area>(Area.class);

    @Id
    private UUID id;

    @Column
    private Integer descricao;

    @ManyToOne
    @JsonBackReference
    private Cidade cidade;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private List<LimiteArea> limitesGeograficos;


    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Controlador> controladores;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
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

}
