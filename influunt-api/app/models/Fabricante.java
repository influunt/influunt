package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.FabricanteDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.FabricanteSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Fabricante} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "fabricantes")
@JsonSerialize(using = FabricanteSerializer.class)
@JsonDeserialize(using = FabricanteDeserializer.class)
public class Fabricante extends Model {

    private static final long serialVersionUID = 7365610316754360728L;

    public static Finder<UUID, Fabricante> find = new Finder<UUID, Fabricante>(Fabricante.class);

    @Id
    private UUID id;

    @Column
    private String idJson;
    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String nome;
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    @Valid
    private List<ModeloControlador> modelos;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ModeloControlador> getModelos() {
        return modelos;
    }

    public void setModelos(List<ModeloControlador> modelos) {
        this.modelos = modelos;
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
}
