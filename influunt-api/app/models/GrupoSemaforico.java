package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que representa o {@link GrupoSemaforico} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "grupos_semaforicos")
public class GrupoSemaforico extends BaseEntity<String> {

    private static final long serialVersionUID = 7439393568357903233L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "tipo_detector_id")
    private TipoGrupoSemaforico tipo;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @ManyToOne
    @JoinColumn(name = "controlador_id")
    private Controlador controlador;

    @ManyToOne
    private GrupoSemaforico grupoConflito;

    @OneToMany(mappedBy = "grupoConflito")
    private List<GrupoSemaforico> verdesConflitantes;

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

    public TipoGrupoSemaforico getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrupoSemaforico tipo) {
        this.tipo = tipo;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public GrupoSemaforico getGrupoConflito() {
        return grupoConflito;
    }

    public void setGrupoConflito(GrupoSemaforico grupoConflito) {
        this.grupoConflito = grupoConflito;
    }

    public List<GrupoSemaforico> getVerdesConflitantes() {
        return verdesConflitantes;
    }

    public void setVerdesConflitantes(List<GrupoSemaforico> verdesConflitantes) {
        this.verdesConflitantes = verdesConflitantes;
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
