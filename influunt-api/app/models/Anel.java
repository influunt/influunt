package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que representa o {@link Anel} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
public class Anel extends BaseEntity<String> {

    private static final long serialVersionUID = 4919501406230732757L;

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    
    @Column
    private String descricao;
    
    @Column
    private String idAnel;
    
    @Column
    private String numeroSMEE;
    
    @OneToOne
    @JoinColumn(name = "coordenada_id")
    private CoordenadaGeografica coordenada;
    
    @ManyToOne
    @JoinColumn(name = "controlador_id")
    private Controlador controlador;
    
    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detector> detectores;
    
    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrupoSemaforico> gruposSemaforicos;
    
    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimento> movimentos;
    
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

    public String getIdAnel() {
        return idAnel;
    }

    public void setIdAnel(String idAnel) {
        this.idAnel = idAnel;
    }

    public String getNumeroSMEE() {
        return numeroSMEE;
    }

    public void setNumeroSMEE(String numeroSMEE) {
        this.numeroSMEE = numeroSMEE;
    }

    public CoordenadaGeografica getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(CoordenadaGeografica coordenada) {
        this.coordenada = coordenada;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public List<Detector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<Detector> detectores) {
        this.detectores = detectores;
    }

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
    }

    public List<Movimento> getMovimentos() {
        return movimentos;
    }

    public void setMovimentos(List<Movimento> movimentos) {
        this.movimentos = movimentos;
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
