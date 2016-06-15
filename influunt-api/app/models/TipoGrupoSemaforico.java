package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link TipoGrupoSemaforico} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "tipo_grupo_semaforicos")
public class TipoGrupoSemaforico extends Model {

    private static final long serialVersionUID = -5973761973329033820L;

    @Id
    private UUID id;
    @Column
    private String descricao;

    @OneToMany(mappedBy = "tipo")
    private List<GrupoSemaforico> gruposSemaforicos;

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

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
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
