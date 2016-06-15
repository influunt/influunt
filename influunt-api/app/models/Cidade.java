package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
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
public class Cidade extends Model {

    private static final long serialVersionUID = 6446144066408570296L;

    public static Finder<UUID, Cidade> find = new Finder<UUID, Cidade>(Cidade.class);

    @Id
    private UUID id;

    @Column
    @Constraints.Required
    private String nome;

    @JsonManagedReference
    @OneToMany(mappedBy = "cidade", cascade = CascadeType.REMOVE)
    private List<Area> areas;

    @Column
    private DateTime dataCriacao;

    @Column
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
