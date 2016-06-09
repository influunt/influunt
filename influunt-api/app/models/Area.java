package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que representa a {@link Aera} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "areas")
public class Area extends BaseEntity<String> {

    private static final long serialVersionUID = 3282755453785165923L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String descricao;

    @OneToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @Transient
    //@OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoordenadaGeografica> coordenadas;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Controlador> controladores;

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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    protected void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
