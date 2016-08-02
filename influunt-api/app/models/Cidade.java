package models;

import checks.CidadesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.CidadeDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.CidadeSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa a {@link Cidade} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "cidades")
@JsonSerialize(using = CidadeSerializer.class)
@JsonDeserialize(using = CidadeDeserializer.class)
public class Cidade extends Model implements Cloneable {

    private static final long serialVersionUID = 6446144066408570296L;

    public static Finder<UUID, Cidade> find = new Finder<UUID, Cidade>(Cidade.class);

    @Id
    private UUID id;

    @Column
    private String idJson;
    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String nome;
    @OneToMany(mappedBy = "cidade", cascade = CascadeType.REMOVE)
    private List<Area> areas;
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

    @AssertTrue(groups = CidadesCheck.class,
            message = "Já existe uma Cidade cadastrada com esse nome.")
    public boolean isNomeUnique() {
        if (Objects.nonNull(getNome())) {
            Cidade cidadeAux = Cidade.find.where().ieq("nome", getNome()).findUnique();

            return cidadeAux == null || (this.getId() != null && cidadeAux.getId().equals(this.getId()));
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Cidade [id=" + id + ", nome=" + nome + ", areas=" + areas + ", dataCriacao=" + dataCriacao
                + ", dataAtualizacao=" + dataAtualizacao + "]";
    }

}
