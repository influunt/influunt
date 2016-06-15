package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

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
    private String descricao;

    @OneToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="area")
    private List<CoordenadaGeografica> coordenadas;

    @JsonIgnore
    @OneToMany(mappedBy = "area", fetch = FetchType.EAGER)
    private List<Controlador> controladores;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<CoordenadaGeografica> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<CoordenadaGeografica> coordenadas) {
        this.coordenadas = coordenadas;
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


}
