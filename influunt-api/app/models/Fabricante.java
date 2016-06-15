package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Fabricante} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "fabricantes")
public class Fabricante extends Model {

    private static final long serialVersionUID = 7365610316754360728L;

    @Id
    private UUID id;
    @Column
    private String nome;

    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    private List<ModeloControlador> modelos;

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
