package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que representa o {@link TipoDetector} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "tipos_detectores")
public class TipoDetector extends BaseEntity<String> {

    private static final long serialVersionUID = -5589722928829006871L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String descricao;

    @OneToMany(mappedBy = "tipo", orphanRemoval = false, fetch = FetchType.EAGER)
    private List<Detector> detectores;

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

    public List<Detector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<Detector> detectores) {
        this.detectores = detectores;
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

}
