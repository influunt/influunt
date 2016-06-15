package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link TipoDetector} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "tipos_detectores")
public class TipoDetector extends Model {

    private static final long serialVersionUID = -5589722928829006871L;
    public static Finder<UUID, TipoDetector> find = new Finder<UUID, TipoDetector>(TipoDetector.class);

    @Id
    private UUID id;

    @Column
    private String descricao;

    @OneToMany(mappedBy = "tipo", fetch = FetchType.EAGER)
    private List<Detector> detectores;

    @Column
    private Date dataCriacao;

    @Column
    private Date dataAtualizacao;

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
}
