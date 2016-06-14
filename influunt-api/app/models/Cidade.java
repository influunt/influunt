package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;
import play.data.validation.Constraints.Required;

/**
 * Entidade que representa a {@link Cidade} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "cidades")
public class Cidade extends BaseEntity<String> {

    private static final long serialVersionUID = 6446144066408570296L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    @Required
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cidade", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Area> areas;

    @Column
    private Date dataCriacao;

    @Column
    private Date dataAtualizacao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Cidade [id=" + id + ", nome=" + nome + ", areas=" + areas + ", dataCriacao=" + dataCriacao
                + ", dataAtualizacao=" + dataAtualizacao + "]";
    }
    
    

}