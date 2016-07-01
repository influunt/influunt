package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import json.deserializers.CidadeDeserializer;
import json.serializers.CidadeSerializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa a {@link Cidade} no sistema
 *
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "cidades")
@JsonSerialize(using = CidadeSerializer.class)
@JsonDeserialize(using = CidadeDeserializer.class)
public class Cidade extends Model {

    private static final long serialVersionUID = 6446144066408570296L;

    public static Finder<UUID, Cidade> find = new Finder<UUID, Cidade>(Cidade.class);

    @Id
    private UUID id;

    @Column
    @NotNull
    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.REMOVE)
    private List<Area> areas;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    @NotNull
    private DateTime dataAtualizacao;

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

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
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
    public String toString() {
        return "Cidade [id=" + id + ", nome=" + nome + ", areas=" + areas + ", dataCriacao=" + dataCriacao
                + ", dataAtualizacao=" + dataAtualizacao + "]";
    }

}
