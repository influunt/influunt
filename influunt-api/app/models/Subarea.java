package models;

import checks.SubareasCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.SubareaDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.SubareaSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa um {@link Subarea} no sistema
 *
 * @author Pedro Pires
 */
@Entity
@Table(name = "subareas")
@JsonSerialize(using = SubareaSerializer.class)
@JsonDeserialize(using = SubareaDeserializer.class)
public class Subarea extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -7310183724485834593L;

    public static Finder<UUID, Subarea> find = new Finder<UUID, Subarea>(Subarea.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String nome;

    @Column
    private Integer numero;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Area area;

    @OneToMany(mappedBy = "subarea")
    @PrivateOwned
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

    public Subarea() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public void addControlador(Controlador controlador) {
        if (this.getControladores() == null) {
            this.setControladores(new ArrayList<Controlador>());
        }
        this.getControladores().add(controlador);
    }

    @AssertTrue(groups = SubareasCheck.class,
            message = "Já existe uma Subarea cadastrada com esse número.")
    public boolean isNumeroUnique() {
        if (Objects.nonNull(getNumero())) {
            Subarea subareaAux = Subarea.find.where().eq("area_id", getArea().getId().toString()).ieq("numero", getNumero().toString()).findUnique();

            return subareaAux == null || (this.getId() != null && subareaAux.getId().equals(this.getId()));
        }
        return true;
    }

    @AssertTrue(groups = SubareasCheck.class,
            message = "Já existe uma Subarea cadastrada com esse nome.")
    public boolean isNomeUnique() {
        if (Objects.nonNull(getNome())) {
            Subarea subareaAux = Subarea.find.where().eq("area_id", getArea().getId().toString()).ieq("nome", getNome().toString()).findUnique();

            return subareaAux == null || (this.getId() != null && subareaAux.getId().equals(this.getId()));
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
